package com.epam.song.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "SONG")
@Data
@Accessors(chain = true)
public class Song {
  @Id private Long id;
  private String name;
  private String artist;
  private String album;
  private String duration;
  private String year;
}
