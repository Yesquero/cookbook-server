package ru.mirea.recipebook.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public class BaseEntityWithUuid {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID uuid;

}
