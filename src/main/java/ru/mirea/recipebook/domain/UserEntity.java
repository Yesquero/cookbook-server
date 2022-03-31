package ru.mirea.recipebook.domain;

import lombok.Getter;
import lombok.Setter;
import ru.mirea.recipebook.domain.converter.UserRoleConverter;
import ru.mirea.recipebook.domain.converter.UserStatusConverter;
import ru.mirea.recipebook.domain.enumeration.UserRole;
import ru.mirea.recipebook.domain.enumeration.UserStatus;

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

    private String nickname;

    @Convert(converter = UserRoleConverter.class)
    private UserRole userRole;

    @Convert(converter = UserStatusConverter.class)
    private UserStatus status;

    private String firstName;

    private String lastName;
}
