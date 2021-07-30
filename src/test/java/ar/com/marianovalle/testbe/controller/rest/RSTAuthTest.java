package ar.com.marianovalle.testbe.controller.rest;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;

import ar.com.marianovalle.testbe.domain.dto.DTOLoginUser;
import ar.com.marianovalle.testbe.domain.model.MDLUser;
import ar.com.marianovalle.testbe.service.interfaces.INTUser;

/**
 * Test of login.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class RSTAuthTest {

	private static final Logger loggerIn = LoggerFactory.getLogger(RSTAuth.class);

	private static final String LINE = "------------------------------------------------------------";

	Optional<MDLUser> mdlLoginUser;
	
	ResponseEntity<Object> response;
	
	/**
	 * Interceptar objeto que se conecta a la DB con Mockito.
	 */
	INTUser iUserSMock = Mockito.mock(INTUser.class);

	@Autowired
	RSTAuth auth = new RSTAuth();

	@Mock
	private BindingResult mockBindingResult;
	
	
	@BeforeEach
	void setUp() throws Exception {
		debug("Before init test.");

		/**
		 * Simulación de respuesta desde la DB.
		 */
		mdlLoginUser = Optional.of(new MDLUser());
		mdlLoginUser.get().setUsername("admin");
		mdlLoginUser.get().setPassword("Pipo1234");

		Mockito.when(iUserSMock.findByUsername(mdlLoginUser.get().getUsername())).thenReturn(mdlLoginUser);


		debug("Before end test. OK");
	}

	/**
	 * Loguin exitoso, devuelve "OK"
	 */
	@Test
	void testLogin_OK() {
		debug("INIT testLogin_OK");

		response = auth.login(user("admin", "Pipo1234"),  result(false));
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

		debug("END testLogin_OK");
	}

	/**
	 * login fallido (credenciales incorrectas), devuelve "Bad Request"
	 */
	@Test
	void testLogin_BAD() {
		debug("INIT testLogin_BAD");

		response = auth.login(user("bad", "bad"), result(false));
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		debug("END testLogin_BAD");
	}

	/**
	 * Login inválido por argumentos malformados, devuelve "Expectation Failed"
	 */
	@Test
	void testLogin_ERROR() {
		debug("INIT testLogin_ERROR");

		response = auth.login(user(null, null), result(true));
		Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());

		debug(response.toString());
		
		debug("END testLogin_ERROR");
	}

	/**
	 * Simular un resultado segun el parámetro true o false.
	 * @param value
	 * @return
	 */
	private BindingResult result(Boolean value) {
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(value);
		return mockBindingResult;
	}

	private DTOLoginUser user(String userName, String password) {
		DTOLoginUser loginUser = new DTOLoginUser();
		loginUser.setUserName(userName);
		loginUser.setPassword(password);

		return loginUser;
	}

	private void debug(String text) {
		loggerIn.info(LINE);
		loggerIn.info(text);
		loggerIn.info(LINE);
	}

}
