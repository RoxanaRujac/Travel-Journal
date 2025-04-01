package com.project.travel.model;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Entry {
    private Long id;
    private Long journalId;
    private String title;
    private String content;
    private String locationName;
    private String longitude;
    private String latitude;
    private String createdAt;
    private List<Media> mediaAttachments;
}
