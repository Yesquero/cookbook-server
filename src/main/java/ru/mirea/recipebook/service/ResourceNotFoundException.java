package ru.mirea.recipebook.service;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {
    private final static String MESSAGE = "%s with id: %s not found.";

    public <T> ResourceNotFoundException(Class<T> resource, UUID uuid) {
        super(String.format(MESSAGE, resource.getSimpleName(), uuid));
    }
}
