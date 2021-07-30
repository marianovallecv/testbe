package ar.com.marianovalle.testbe.controller.rest;

import java.util.List;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import ar.com.marianovalle.testbe.service.interfaces.INTCandidate;
import ar.com.marianovalle.testbe.controller.assambler.ASMCandidate;
import ar.com.marianovalle.testbe.domain.dto.DTOMessage;
import ar.com.marianovalle.testbe.domain.dto.DTOCandidate;
import ar.com.marianovalle.testbe.domain.model.MDLCandidate;
import ar.com.marianovalle.testbe.domain.projection.PRJCandidate;
import ar.com.marianovalle.testbe.error.ERRMessage;
import ar.com.marianovalle.testbe.error.ERRResourceNotFound;

/**
 * Controller of candidate.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@RestController
@RequestMapping(path = "/candidates")
@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE })
//@PreAuthorize("hasRole('ADMIN')")
public class RSTCandidate {
	private static final Logger loggerIn = LoggerFactory.getLogger(RSTCandidate.class);
	
	@Autowired
	private INTCandidate service;

	@Autowired
	private ASMCandidate assembler;

	@GetMapping(value = "/")
	public ResponseEntity<Page<DTOCandidate>> get(
		@RequestParam (required = false, defaultValue = "0") int page,
		@RequestParam (required = false, defaultValue = "10") int size,
		@RequestParam (required = false, defaultValue = "id") String column,
		@RequestParam (required = false, defaultValue = "true") boolean isAscending){
		loggerIn.info("pages");
		
		Page<MDLCandidate> pages = service.findAll(PageRequest.of(page, size, (isAscending)?Sort.by(column):Sort.by(column).descending()));
		Page<DTOCandidate> pagesDTO = assembler.convertListDTOLToPageDTO(pages);

		return ResponseEntity.ok(pagesDTO);
	}
	
//	@GetMapping(value = "/")
//	public ResponseEntity<Page<PRJCandidate>> getAll(
//		@RequestParam (required = false, defaultValue = "0") int page,
//		@RequestParam (required = false, defaultValue = "10") int size,
//		@RequestParam (required = false, defaultValue = "id") String column,
//		@RequestParam (required = false, defaultValue = "true") boolean isAscending){
//		loggerIn.info("pages");
//		
//		Page<PRJCandidate> pages = service.findAllCandidatesWithPagination(PageRequest.of(page, size, (isAscending)?Sort.by(column):Sort.by(column).descending()));
//		//Page<DTOCandidate> pagesDTO = assembler.convertListDTOLToPageDTO(pages);
//
//		return ResponseEntity.ok(pages);
//	}
	
	@GetMapping(value = "/{id}")
	public EntityModel<DTOCandidate> get(@PathVariable Integer id) {
		MDLCandidate candidate = service.findById(id).orElseThrow(() -> new ERRResourceNotFound("Candidate not found for this id :: " + id));
		return assembler.toModelDto(candidate);
	}

	@GetMapping(value = "/email/{email}")
	public EntityModel<DTOCandidate> getByEmail(@PathVariable(value = "email") String email) {
		MDLCandidate candidate = service.findByEmail(email).orElseThrow(() -> new ERRResourceNotFound("Candidate not found for this email :: " + email));
		return assembler.toModelDto(candidate);
	}

	@GetMapping(value = "/document/{document}")
	public EntityModel<DTOCandidate> getByDocument(@PathVariable(value = "document") Integer document) {
		MDLCandidate candidate = service.findByDocument(document).orElseThrow(() -> new ERRResourceNotFound("Candidate not found for this document :: " + document));
		return assembler.toModelDto(candidate);
	}
	
	@GetMapping(value = "/fullName/{fullName}")
	public EntityModel<DTOCandidate> getByFullName(@PathVariable(value = "fullName") String fullName) {
		MDLCandidate candidate = service.findByFullName(fullName).orElseThrow(() -> new ERRResourceNotFound("Candidate not found for this full name :: " + fullName));
		return assembler.toModelDto(candidate);
	}

	@GetMapping(value = "/all")
	public CollectionModel<EntityModel<DTOCandidate>> all() {
		List<EntityModel<DTOCandidate>> candidate = service.findAll().stream().map(assembler::toModelDto).collect(Collectors.toList());
		return CollectionModel.of(candidate, linkTo(methodOn(RSTCandidate.class).all()).withSelfRel());
	}

	@PostMapping(value = "/")
	public ResponseEntity<Object> post(@Valid @RequestBody DTOCandidate dtoCandidate, BindingResult result) {
		ERRMessage.controlError("dtoCandidate", result, dtoCandidate);

		if(result.hasErrors())
			return new ResponseEntity<>(new DTOMessage("Campos mal puestos o email invalido."), HttpStatus.BAD_REQUEST);
		if(service.existsByFullName(dtoCandidate.getFullName()))
			return new ResponseEntity<>(new DTOMessage("El nombre de candidato ya existe."), HttpStatus.BAD_REQUEST);
		if(service.existsByEmail(dtoCandidate.getEmail()))
			return new ResponseEntity<>(new DTOMessage("El email ya existe."), HttpStatus.BAD_REQUEST);	
	
		MDLCandidate candidate = assembler.convertToEntity(dtoCandidate);
	
		EntityModel<DTOCandidate> entityModel = assembler.toModelDto(service.save(candidate));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Object> put(@Valid @RequestBody DTOCandidate dtoCandidate, BindingResult result, @PathVariable Integer id) {
		ERRMessage.controlError("newCandidate", result, dtoCandidate);

		EntityModel<DTOCandidate> entityModel = service.findById(id).map( (MDLCandidate candidate) -> {
			candidate = assembler.convertToEntity(dtoCandidate);
			candidate.setId(id);
			return assembler.toModelDto(service.save(candidate));
		}).orElseThrow(() -> new ERRResourceNotFound("Candidate not found for this id :: " + id));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable Integer id) {
		service.deleteById(id);

		return ResponseEntity.noContent().build();
	}

}
