package ar.com.marianovalle.testbe.controller.assambler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import ar.com.marianovalle.testbe.controller.rest.RSTUser;
import ar.com.marianovalle.testbe.domain.dto.DTOUserDetails;
import ar.com.marianovalle.testbe.domain.model.MDLUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

/**
 * Assembler of user.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Component
public class ASMUser implements RepresentationModelAssembler<MDLUser, EntityModel<MDLUser>> {
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public EntityModel<MDLUser> toModel(MDLUser mdl) {
		return EntityModel.of(mdl, linkTo(methodOn(RSTUser.class).get(mdl.getId())).withSelfRel(),
				linkTo(methodOn(RSTUser.class).all()).withRel("users"));
	}

	public EntityModel<DTOUserDetails> toModelDto(MDLUser mdl) {
		DTOUserDetails userDto = convertToDto(mdl);
		return EntityModel.of(userDto, linkTo(methodOn(RSTUser.class).get(mdl.getId())).withSelfRel(),
				linkTo(methodOn(RSTUser.class).all()).withRel("users"));
	}

	public DTOUserDetails convertToDto(MDLUser mdl) {
		return modelMapper.map(mdl, DTOUserDetails.class);
	}

	public MDLUser convertToEntity(DTOUserDetails dto) {
		return modelMapper.map(dto, MDLUser.class);
	}
	
	public List<DTOUserDetails> convertPageMDLToListDTO(Page<MDLUser> pageMdl) {
		return modelMapper.map(pageMdl.getContent(), new TypeToken<List<DTOUserDetails>>(){}.getType());
	}
	
	public Page<DTOUserDetails> convertListDTOLToPageDTO(Page<MDLUser> pageMdl) {
		return new PageImpl<>(convertPageMDLToListDTO(pageMdl), pageMdl.getPageable(), pageMdl.getTotalElements());
	}
}
