package ru.mirea.recipebook.controller.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

	private final ModelMapper mapper;

	public DtoMapper() {
		mapper = new ModelMapper();
	}

}
