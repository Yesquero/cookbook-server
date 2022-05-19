package ru.mirea.recipebook.controller.dto;

import lombok.Data;

@Data
public class UserInfoDto {

	private String login;

	private String password;

	private String nickname;

	private String userRole;

	private String status;

	private String firstName;

	private String lastName;

}
