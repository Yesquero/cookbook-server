package ru.mirea.recipebook.controller.dto;

import lombok.Data;

@Data
public class ExceptionResponse {

	private Integer responseCode;
	private String responseMessage;

}
