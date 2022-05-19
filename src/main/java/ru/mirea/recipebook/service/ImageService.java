package ru.mirea.recipebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.mirea.recipebook.domain.Image;
import ru.mirea.recipebook.repository.ImageRepository;
import ru.mirea.recipebook.utility.ResourceNotFoundException;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageService {

	private final ImageRepository imageRepository;

	public UUID saveImage(MultipartFile image) {
		try {
			Image toSave = new Image();
			toSave.setName(image.getOriginalFilename());
			toSave.setContent(image.getBytes());

			return imageRepository.saveAndFlush(toSave).getUuid();
		} catch (IOException exception) {
			throw new FileUploadException(image.getName());
		}
	}

	public Image findByUuid(UUID uuid) {
		return imageRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException(Image.class, uuid));
	}

}
