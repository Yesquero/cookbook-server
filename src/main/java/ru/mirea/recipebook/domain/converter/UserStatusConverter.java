package ru.mirea.recipebook.domain.converter;

import ru.mirea.recipebook.domain.enumeration.UserStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = false)
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {

	@Override
	public String convertToDatabaseColumn(UserStatus status) {
		if (status == null) {
			return null;
		}
		return status.toString();
	}

	@Override
	public UserStatus convertToEntityAttribute(String statusString) {
		if (statusString == null) {
			return null;
		}
		return Stream.of(UserStatus.values())
			.filter(it -> it.toString().equals(statusString))
			.findFirst()
			.orElseThrow(IllegalAccessError::new);
	}

}
