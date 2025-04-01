package com.project.travel.repository;

import com.project.travel.model.Entry;
import com.project.travel.model.Media;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MediaRepository {
    private final List<Media> mediaList = new ArrayList<>();

    public Media save(Media media) {
        mediaList.add(media);
        return media;
    }

    public void saveAll(List<Media> newMediaList) {
        mediaList.addAll(newMediaList);
    }

    public List<Media> findAll() {
        return new ArrayList<>(mediaList);
    }

    public Media findById(Long id) {
        return mediaList.stream()
                .filter(media -> media.getId().equals(id))
                .findFirst().orElse(null);
    }

    public List<Media> findByEntryId(Long entryId) {
        return mediaList.stream()
                .filter(media -> media.getEntryId().equals(entryId))
                .toList();
    }

    public Media updateMedia(Media updatedMedia) {
        Media oldMedia = findById(updatedMedia.getId());
        if (oldMedia != null) {
            oldMedia.setUrl(updatedMedia.getUrl());
            return oldMedia;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        return mediaList.removeIf(media -> media.getId().equals(id));
    }
}
