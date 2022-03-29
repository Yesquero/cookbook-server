package ru.mirea.recipebook.domain.converter;

import ru.mirea.recipebook.domain.UserRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = false)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {
    @Override
    public String convertToDatabaseColumn(UserRole role) {
        if (role == null) {
            return null;
        }
        return role.toString();
    }

    @Override
    public UserRole convertToEntityAttribute(String roleString) {
        if (roleString == null) {
            return null;
        }
        return Stream.of(UserRole.values())
                .filter(it -> it.toString().equals(roleString))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
