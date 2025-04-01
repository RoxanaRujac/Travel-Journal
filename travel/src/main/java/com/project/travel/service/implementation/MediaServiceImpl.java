package com.project.travel.service.implementation;

import com.project.travel.constants.MediaType;
import com.project.travel.model.Media;
import com.project.travel.repository.MediaRepository;
import com.project.travel.service.MediaService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MediaServiceImpl implements MediaService {
    private final MediaRepository mediaRepository;
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Configure upload directory (you might want to make this configurable via application.properties)
    private final String uploadDir = "uploads/media";

    public MediaServiceImpl(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
        // Create upload directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    @Override
    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    @Override
    public Media getMediaById(Long id) {
        Media media = mediaRepository.findById(id);
        if (media == null) {
            throw new NoSuchElementException("Media not found with ID: " + id);
        }
        return media;
    }

    @Override
    public List<Media> getMediaByEntryId(Long entryId) {
        return mediaRepository.findByEntryId(entryId);
    }

    @Override
    public Long addMedia(Media media) {
        // Set new ID and timestamp
        if (media.getId() == null) {
            media.setId(idGenerator.getAndIncrement());
        }

        // Set creation timestamp if not present
        if (media.getCreatedAt() == null) {
            media.setCreatedAt(LocalDateTime.now().format(formatter));
        }

        mediaRepository.save(media);
        return media.getId();
    }

    @Override
    public void updateMedia(Media media) {
        // Check if media exists
        Media existingMedia = getMediaById(media.getId());

        // Update the media
        Media updatedMedia = mediaRepository.updateMedia(media);
        if (updatedMedia == null) {
            throw new NoSuchElementException("Failed to update media with ID: " + media.getId());
        }
    }

    @Override
    public void deleteMedia(Long id) {
        // Check if media exists
        Media media = getMediaById(id);

        // Delete the media file
        deleteMediaFile(media.getUrl());

        // Delete the media record
        boolean deleted = mediaRepository.deleteById(id);
        if (!deleted) {
            throw new NoSuchElementException("Failed to delete media with ID: " + id);
        }
    }

    @Override
    public void deleteMediaByEntryId(Long entryId) {
        List<Media> mediaList = getMediaByEntryId(entryId);

        // Delete all media files
        for (Media media : mediaList) {
            deleteMediaFile(media.getUrl());
            mediaRepository.deleteById(media.getId());
        }
    }

    @Override
    public String saveMediaFile(MultipartFile file) {
        try {
            // Generate a unique filename to avoid collisions
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null ?
                    originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // Create the file path
            Path path = Paths.get(uploadDir, uniqueFilename);

            // Save the file
            Files.copy(file.getInputStream(), path);

            // Return the relative URL
            return uploadDir + "/" + uniqueFilename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save media file", e);
        }
    }

    @Override
    public void deleteMediaFile(String url) {
        try {
            Path filePath = Paths.get(url);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete media file: " + url, e);
        }
    }

    @Override
    public MediaType determineMediaType(String contentType) {
        if (contentType == null) {
            return null;
        }

        if (contentType.startsWith("image/")) {
            return MediaType.PHOTO;
        } else if (contentType.startsWith("video/")) {
            return MediaType.VIDEO;
        } else {
            return null;
        }
    }
}