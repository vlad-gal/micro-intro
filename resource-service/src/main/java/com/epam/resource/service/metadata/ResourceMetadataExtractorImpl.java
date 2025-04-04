package com.epam.resource.service.metadata;

import com.epam.resource.dto.ResourceMetadataDTO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ResourceMetadataExtractorImpl implements ResourceMetadataExtractor {
  private final Tika tika;

  @Override
  public ResourceMetadataDTO extractMetadata(byte[] file) {
    try {
      Metadata metadata = new Metadata();
      tika.parse(new ByteArrayInputStream(file), metadata);

      MediaType mediaType = MediaType.valueOf(metadata.get("Content-Type"));
      if (!new MediaType("audio", "mpeg").isCompatibleWith(mediaType)) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Invalid file format: %s. Only MP3 files are allowed".formatted(mediaType));
      }
      String duration =
          LocalTime.MIN
              .plus(Duration.ofSeconds((long) Double.parseDouble(metadata.get("xmpDM:duration"))))
              .format(DateTimeFormatter.ofPattern("mm:ss"));

      return new ResourceMetadataDTO()
          .setAlbum(metadata.get("xmpDM:album"))
          .setName(metadata.get("dc:title"))
          .setArtist(metadata.get("xmpDM:artist"))
          .setDuration(duration)
          .setYear(metadata.get("xmpDM:releaseDate"));
    } catch (IOException e) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred on the server");
    }
  }
}
