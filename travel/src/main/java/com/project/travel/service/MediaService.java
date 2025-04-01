package com.project.travel.service;

import com.project.travel.model.Media;
import com.project.travel.constants.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {
    List<Media> getAllMedia();
    Media getMediaById(Long id);
    List<Media> getMediaByEntryId(Long entryId);
    Long addMedia(Media media);
    void updateMedia(Media media);
    void deleteMedia(Long id);
    void deleteMediaByEntryId(Long entryId);
    String saveMediaFile(MultipartFile file);
    void deleteMediaFile(String url);
    MediaType determineMediaType(String contentType);
}