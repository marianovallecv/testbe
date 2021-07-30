package ar.com.marianovalle.testbe.controller.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.marianovalle.testbe.util.enums.ENURoleUserName;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import ar.com.marianovalle.testbe.service.interfaces.INTRoleUser;
import ar.com.marianovalle.testbe.service.interfaces.INTUser;
import ar.com.marianovalle.testbe.controller.assambler.ASMUser;
import ar.com.marianovalle.testbe.domain.dto.DTOMessage;
import ar.com.marianovalle.testbe.domain.dto.DTOUserDetails;
import ar.com.marianovalle.testbe.domain.model.MDLRoleUser;
import ar.com.marianovalle.testbe.domain.model.MDLUser;
import ar.com.marianovalle.testbe.error.ERRMessage;
import ar.com.marianovalle.testbe.error.ERRResourceNotFound;

/**
 * Controller of user.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@RestController
@RequestMapping(path = "/users")
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE })
@PreAuthorize("hasRole('ADMIN')")
public class RSTUser {
	private static final Logger loggerIn = LoggerFactory.getLogger(RSTUser.class);
	
	@Autowired
	private INTUser service;

	@Autowired
	private INTRoleUser iRoleS;

	@Autowired
	private BCryptPasswordEncoder bcpe;

	@Autowired
	private ASMUser assembler;

	@GetMapping(value = "/")
	public ResponseEntity<Page<DTOUserDetails>> get(
		@RequestParam (required = false, defaultValue = "0") int page,
		@RequestParam (required = false, defaultValue = "10") int size,
		@RequestParam (required = false, defaultValue = "id") String column,
		@RequestParam (required = false, defaultValue = "true") boolean isAscending){
		loggerIn.info("pages");
		
		Page<MDLUser> pages = service.findAll(PageRequest.of(page, size, (isAscending)?Sort.by(column):Sort.by(column).descending()));
		Page<DTOUserDetails> pagesDTO = assembler.convertListDTOLToPageDTO(pages);

		return ResponseEntity.ok(pagesDTO);
	}

	@GetMapping(value = "/{id}")
	public EntityModel<DTOUserDetails> get(@PathVariable Integer id) {
		MDLUser user = service.findById(id).orElseThrow(() -> new ERRResourceNotFound("User not found for this id :: " + id));
		return assembler.toModelDto(user);
	}

	@GetMapping(value = "/username/{username}")
	public EntityModel<DTOUserDetails> getByUsername(@PathVariable(value = "username") String username) {
		MDLUser user = service.findByUsername(username).orElseThrow(() -> new ERRResourceNotFound("User not found for this user name :: " + username));
		return assembler.toModelDto(user);
	}

	@GetMapping(value = "/all")
	public CollectionModel<EntityModel<DTOUserDetails>> all() {
		List<EntityModel<DTOUserDetails>> user = service.findAll().stream().map(assembler::toModelDto).collect(Collectors.toList());
		return CollectionModel.of(user, linkTo(methodOn(RSTUser.class).all()).withSelfRel());
	}

	@PostMapping(value = "/")
	public ResponseEntity<Object> post(@Valid @RequestBody DTOUserDetails dtoUser, BindingResult result) {
		ERRMessage.controlError("dtoUser", result, dtoUser);

		if(result.hasErrors())
			return new ResponseEntity<>(new DTOMessage("Campos mal puestos o email invalido."), HttpStatus.BAD_REQUEST);
		if(service.existsByUsername(dtoUser.getUsername()))
			return new ResponseEntity<>(new DTOMessage("El nombre de usuario ya existe."), HttpStatus.BAD_REQUEST);
	
		MDLUser user = assembler.convertToEntity(dtoUser);
		updateUser(true, user, dtoUser.getPassword(), getRoles(dtoUser.getRoles().toString()));		
	
		EntityModel<DTOUserDetails> entityModel = assembler.toModelDto(service.save(user));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> put(@Valid @RequestBody DTOUserDetails dtoUser, BindingResult result, @PathVariable Integer id) {
		ERRMessage.controlError("newUser", result, dtoUser);

		EntityModel<DTOUserDetails> entityModel = service.findById(id).map( (MDLUser user) -> {
			user = assembler.convertToEntity(dtoUser);
			updateUser(dtoUser.isUpdPass(), user, dtoUser.getPassword(), getRoles(dtoUser.getRoles().toString()));
			return assembler.toModelDto(service.save(user));
		}).orElseThrow(() -> new ERRResourceNotFound("User not found for this id :: " + id));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	private void updateUser(boolean update, MDLUser user, CharSequence rawPassword, Set<MDLRoleUser> roles) {
		if (update){
			user.setUpdPass(true);
			user.setPassword(bcpe.encode(rawPassword));
		}
		user.setRoles(roles);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable Integer id) {
		service.deleteById(id);

		return ResponseEntity.noContent().build();
	}

	@GetMapping (value = "/findBySelUser/{selUser}")
	public List<MDLUser> findBySelUser(@PathVariable(value = "selUser") String selUser) {
		MDLUser mmcUser = null;
		List <MDLUser> mmcUserList = new  ArrayList<>();

		if (service.existsByUsername(selUser)){
			mmcUser = service.findByUsername(selUser).orElseThrow(() -> new ERRResourceNotFound("User not found for this username :: " + selUser));
			mmcUserList.add(mmcUser);
		}

		return mmcUserList;
	}

	private Set<MDLRoleUser> getRoles(String type){
		Set<MDLRoleUser> roles = new HashSet<>();

		if (type.equals("Administrador"))
			roles.add(iRoleS.findByRoleName(ENURoleUserName.ROLE_ADMIN).orElse(null));
		if (type.equals("Organizador"))
			roles.add(iRoleS.findByRoleName(ENURoleUserName.ROLE_USER).orElse(null));
		
		return roles;
	}

}
