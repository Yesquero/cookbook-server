package ru.mirea.recipebook.utility;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {

	private final static String MESSAGE = "%s with identifier: %s not found.";

	public <T> ResourceNotFoundException(Class<T> resource, UUID uuid) {
		super(String.format(MESSAGE, resource.getSimpleName(), uuid));
	}

	public <T> ResourceNotFoundException(Class<T> resource, String identifier) {
		super(String.format(MESSAGE, resource.getSimpleName(), identifier));
	}

}
