package com.epam.song.service.song;

import com.epam.song.dto.ResponseSongDTO;
import com.epam.song.dto.ResponseSongsDTO;
import com.epam.song.dto.SongDTO;
import org.springframework.http.ResponseEntity;

public interface SongService {
  ResponseEntity<ResponseSongDTO> upload(SongDTO songDTO);

  ResponseEntity<SongDTO> findById(long songId);

  ResponseEntity<ResponseSongsDTO> deleteByIds(String songIds);
}
