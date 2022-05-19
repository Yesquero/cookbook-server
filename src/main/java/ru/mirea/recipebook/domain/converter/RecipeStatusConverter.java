package ru.mirea.recipebook.domain.converter;

import ru.mirea.recipebook.domain.enumeration.RecipeStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.util.stream.Stream;

@Convert(disableConversion = false)
public class RecipeStatusConverter implements AttributeConverter<RecipeStatus, String> {

	@Override
	public String convertToDatabaseColumn(RecipeStatus status) {
		if (status == null) {
			return null;
		}
		return status.toString();
	}

	@Override
	public RecipeStatus convertToEntityAttribute(String statusString) {
		if (statusString == null) {
			return null;
		}
		return Stream.of(RecipeStatus.values())
			.filter(it -> it.toString().equals(statusString))
			.findFirst()
			.orElseThrow(IllegalAccessError::new);
	}

}
