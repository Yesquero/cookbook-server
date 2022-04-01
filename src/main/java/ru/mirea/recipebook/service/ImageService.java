package ru.mirea.recipebook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mirea.recipebook.domain.Image;
import ru.mirea.recipebook.repository.ImageRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public UUID saveImage(String name, byte[] imageBytes) {
        Image toSave = new Image();
        toSave.setName(name);
        toSave.setContent(imageBytes);

        return imageRepository.saveAndFlush(toSave).getUuid();
    }

    public Image findByUuid(UUID uuid) {
        return imageRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException(Image.class, uuid));
    }
}
