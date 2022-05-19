package ru.mirea.recipebook.utility;

public class FileUploadException extends RuntimeException {

	private static final String MESSAGE = "I/O error when processing file: %s";

	public FileUploadException(String name) {
		super(String.format(MESSAGE, name));
	}

}
