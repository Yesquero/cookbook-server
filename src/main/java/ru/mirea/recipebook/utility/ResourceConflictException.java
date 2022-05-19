package ru.mirea.recipebook.utility;

import java.util.UUID;

public class ResourceConflictException extends RuntimeException {

	private final static String MESSAGE = "%s with identifier: %s already exists.";

	public <T> ResourceConflictException(Class<T> resource, UUID uuid) {
		super(String.format(MESSAGE, resource.getSimpleName(), uuid));
	}

	public <T> ResourceConflictException(Class<T> resource, String identifier) {
		super(String.format(MESSAGE, resource.getSimpleName(), identifier));
	}

}
