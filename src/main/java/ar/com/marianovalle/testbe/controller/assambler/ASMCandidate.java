package ar.com.marianovalle.testbe.controller.assambler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

import ar.com.marianovalle.testbe.controller.rest.RSTCandidate;
import ar.com.marianovalle.testbe.domain.dto.DTOCandidate;
import ar.com.marianovalle.testbe.domain.model.MDLCandidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

/**
 * Assembler of candidate.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Component
public class ASMCandidate implements RepresentationModelAssembler<MDLCandidate, EntityModel<MDLCandidate>> {
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public EntityModel<MDLCandidate> toModel(MDLCandidate mdl) {
		return EntityModel.of(mdl, linkTo(methodOn(RSTCandidate.class).get(mdl.getId())).withSelfRel(),
				linkTo(methodOn(RSTCandidate.class).all()).withRel("candidates"));
	}

	public EntityModel<DTOCandidate> toModelDto(MDLCandidate mdl) {
		DTOCandidate userDto = convertToDto(mdl);
		return EntityModel.of(userDto, linkTo(methodOn(RSTCandidate.class).get(mdl.getId())).withSelfRel(),
				linkTo(methodOn(RSTCandidate.class).all()).withRel("candidates"));
	}

	public DTOCandidate convertToDto(MDLCandidate mdl) {
		return modelMapper.map(mdl, DTOCandidate.class);
	}

	public MDLCandidate convertToEntity(DTOCandidate dto) {
		return modelMapper.map(dto, MDLCandidate.class);
	}
	
	public List<DTOCandidate> convertPageMDLToListDTO(Page<MDLCandidate> pageMdl) {
		return modelMapper.map(pageMdl.getContent(), new TypeToken<List<DTOCandidate>>(){}.getType());
	}
	
	public Page<DTOCandidate> convertListDTOLToPageDTO(Page<MDLCandidate> pageMdl) {
		return new PageImpl<>(convertPageMDLToListDTO(pageMdl), pageMdl.getPageable(), pageMdl.getTotalElements());
	}
}
