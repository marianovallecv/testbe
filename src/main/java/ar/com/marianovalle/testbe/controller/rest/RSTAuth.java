package ar.com.marianovalle.testbe.controller.rest;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.marianovalle.testbe.domain.dto.DTOJwt;
import ar.com.marianovalle.testbe.domain.dto.DTOLoginUser;
import ar.com.marianovalle.testbe.domain.dto.DTOMessage;
import ar.com.marianovalle.testbe.jwt.JWTProvider;
import ar.com.marianovalle.testbe.service.interfaces.INTUser;

/**
 * Controller of auth.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@RestController
@RequestMapping(path = "/auth")
@CrossOrigin(origins = "http://localhost:4200", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
//@PropertySource("messages.properties")
public class RSTAuth {

	private static final Logger loggerIn = LoggerFactory.getLogger(RSTAuth.class);

	//@Value("${log.login}")
	String login = "login";

	@Autowired
	BCryptPasswordEncoder bcpe;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private INTUser iUserS;
	
	@Autowired
	JWTProvider jwtProvider;
	
	@RequestMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody DTOLoginUser loginUser, BindingResult bindingResult){
		loggerIn.info(login);

		if (bindingResult.hasErrors())
			return new ResponseEntity<>(new DTOMessage("campos mal puestos"), HttpStatus.EXPECTATION_FAILED);
		
		String userLoguin = "";
		boolean exist = false;

		if (iUserS.existsByUsername(loginUser.getUserName())){
			userLoguin = loginUser.getUserName();
			exist = true;
		}

		if (exist){
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoguin, loginUser.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtProvider.generateToken(authentication);
			UserDetails userDetails = (UserDetails)authentication.getPrincipal();
			DTOJwt jwtDto = new DTOJwt(jwt, userDetails.getUsername(), userDetails.getAuthorities());
			return new ResponseEntity<>(jwtDto, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(new DTOMessage("Usuario o contraseña incorrectos"), HttpStatus.BAD_REQUEST);
		}
	}

}
