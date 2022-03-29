package ru.mirea.recipebook.domain;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.recipebook.domain.converter.UserRoleConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "user_entity")
public class UserEntity extends BaseEntityWithUuid {
    private String login;
    private String password;
    @Convert(converter = UserRoleConverter.class)
    private UserRole userRole;
    private String firstName;
    private String lastName;
}
