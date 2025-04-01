package com.project.travel.model;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Journal {
    private Long id;
    private Long userId;
    private String title;
    private MultipartFile coverImageFile;
    private String description;
    private String createdAt;
    private List<Entry> entries ;
}
