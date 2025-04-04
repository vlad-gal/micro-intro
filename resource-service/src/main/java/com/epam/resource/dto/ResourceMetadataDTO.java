package com.epam.resource.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ResourceMetadataDTO {
  private Long id;
  private String name;
  private String artist;
  private String album;
  private String duration;
  private String year;
}
