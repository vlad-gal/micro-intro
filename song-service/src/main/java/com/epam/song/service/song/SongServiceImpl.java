package com.epam.song.service.song;

import com.epam.song.dto.ResponseSongDTO;
import com.epam.song.dto.ResponseSongsDTO;
import com.epam.song.dto.SongDTO;
import com.epam.song.model.Song;
import com.epam.song.repository.SongRepository;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {
  private final SongRepository songRepository;

  @Override
  @Transactional
  public ResponseEntity<ResponseSongDTO> upload(SongDTO songDTO) {
    Long resourceId = songDTO.getId();
    songRepository
        .findById(resourceId)
        .ifPresent(
            song -> {
              throw new ResponseStatusException(
                  HttpStatus.CONFLICT,
                  "Metadata for resource ID=%d already exists".formatted(resourceId));
            });

    Song song =
        new Song()
            .setId(resourceId)
            .setName(songDTO.getName())
            .setAlbum(songDTO.getAlbum())
            .setArtist(songDTO.getArtist())
            .setDuration(songDTO.getDuration())
            .setYear(songDTO.getYear());
    songRepository.save(song);
    return ResponseEntity.ok(new ResponseSongDTO(song.getId()));
  }

  @Override
  public ResponseEntity<SongDTO> findById(long songId) {
    Song song =
        songRepository
            .findById(songId)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Song with ID=%d not found".formatted(songId)));
    SongDTO songDTO =
        new SongDTO()
            .setId(song.getId())
            .setName(song.getName())
            .setAlbum(song.getAlbum())
            .setArtist(song.getArtist())
            .setYear(song.getYear())
            .setDuration(song.getDuration());

    return ResponseEntity.ok(songDTO);
  }

  @Override
  @Transactional
  public ResponseEntity<ResponseSongsDTO> deleteByIds(String songIds) {
    if (songIds.length() > 200) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "CSV string is too long: received %d characters, maximum allowed is 200"
              .formatted(songIds.length()));
    }
    String[] ids = songIds.split(",");
    List<String> invalidIds = Arrays.stream(ids).filter(id -> !id.matches("\\d+")).toList();
    if (!invalidIds.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "Invalid ID format: '%s'. Only positive integers are allowed".formatted(invalidIds));
    }

    List<Long> validIds =
        Arrays.stream(ids).filter(id -> id.matches("\\d+")).map(Long::parseLong).toList();

    List<Song> songs = songRepository.findAllById(validIds);
    songRepository.deleteAll(songs);

    List<Long> deletedIds = songs.stream().map(Song::getId).toList();

    return ResponseEntity.ok().body(new ResponseSongsDTO(deletedIds));
  }
}
