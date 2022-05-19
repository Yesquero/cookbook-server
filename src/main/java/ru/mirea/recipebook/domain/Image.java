package ru.mirea.recipebook.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "image_catalog")
public class Image extends BaseEntityWithUuid {

	private String name;

	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] content;

	@OneToOne(mappedBy = "recipeImage")
	private Recipe recipe;

}
