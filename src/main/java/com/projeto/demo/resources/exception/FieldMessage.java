package com.projeto.demo.resources.exception;

import java.io.Serializable;

public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String fieldNome;
	private String fieldMessage;
	
	public FieldMessage() {
		
	}

	public FieldMessage(String fieldNome, String fieldMessage) {
		super();
		this.fieldNome = fieldNome;
		this.fieldMessage = fieldMessage;
	}

	public String getFieldNome() {
		return fieldNome;
	}

	public void setFieldNome(String fieldNome) {
		this.fieldNome = fieldNome;
	}

	public String getFieldMessage() {
		return fieldMessage;
	}

	public void setFieldMessage(String fieldMessage) {
		this.fieldMessage = fieldMessage;
	}
}
