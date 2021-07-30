package ar.com.marianovalle.testbe.domain.dto;

import java.io.Serializable;

/**
 * DTO of message.
 * @author Mariano Valle
 * @version 1.0
 *
 */
public class DTOMessage implements Serializable{

    private static final long serialVersionUID = 1L;

	private String message;

	public DTOMessage(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
