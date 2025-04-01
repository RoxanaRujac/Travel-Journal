package com.project.travel.model;
import com.project.travel.constants.MediaType;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Media {
    private Long id;
    private Long entryId;
    private String url;
    private MediaType type;
    private String caption;
    private String createdAt;
}
