package com.epam.song.dto;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ErrorDTO {
  private String errorMessage;
  private Map<String, String> details;
  private int errorCode;
}
