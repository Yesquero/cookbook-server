package ru.mirea.recipebook.controller.dto;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.mirea.recipebook.domain.UserEntity;

@Component
public class DtoMapper {

	private final ModelMapper mapper;

	public DtoMapper() {
		mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
	}

	public UserInfoDto toDto(UserEntity source) {
		return mapper.map(source, UserInfoDto.class);
	}

}
