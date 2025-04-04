package com.epam.song.api;

import com.epam.song.dto.ResponseSongDTO;
import com.epam.song.dto.ResponseSongsDTO;
import com.epam.song.dto.SongDTO;
import com.epam.song.service.song.SongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "songs", produces = MediaType.APPLICATION_JSON_VALUE)
public class SongAPI {
  private final SongService songService;

  @PostMapping
  public ResponseEntity<ResponseSongDTO> upload(@Valid @RequestBody SongDTO songDTO) {
    return songService.upload(songDTO);
  }

  @GetMapping("{song-id}")
  public ResponseEntity<SongDTO> findById(@PathVariable("song-id") long songId) {
    return songService.findById(songId);
  }

  @DeleteMapping
  public ResponseEntity<ResponseSongsDTO> deleteByIds(@RequestParam("id") String songIds) {
    return songService.deleteByIds(songIds);
  }
}
