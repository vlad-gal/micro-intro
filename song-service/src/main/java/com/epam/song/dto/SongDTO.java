package com.epam.song.dto;

import com.epam.song.annotation.DurationPattern;
import com.epam.song.annotation.YearRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SongDTO {
  @NotNull(message = "Resource id must be present")
  @Positive(message = "Resource id must be positive number")
  private Long id;

  @NotBlank(message = "Name must be present")
  @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
  private String name;

  @NotBlank(message = "Artist must be present")
  @Size(min = 1, max = 100, message = "Artist must be between 1 and 100 characters")
  private String artist;

  @NotBlank(message = "Album must be present")
  @Size(min = 1, max = 100, message = "Album must be between 1 and 100 characters")
  private String album;

  @NotBlank(message = "Duration must be present")
  @DurationPattern
  private String duration;

  @NotBlank(message = "Year must be present")
  @YearRange
  private String year;
}
