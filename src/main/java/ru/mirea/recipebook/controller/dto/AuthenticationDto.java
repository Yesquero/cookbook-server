package ru.mirea.recipebook.controller.dto;

import lombok.Data;

@Data
public class AuthenticationDto {

	private String login;
	private String password;

}
