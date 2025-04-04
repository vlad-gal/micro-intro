package com.epam.resource.service.resource;

import com.epam.resource.dto.ResponseResourceDTO;
import com.epam.resource.dto.ResponseResourcesDTO;
import org.springframework.http.ResponseEntity;

public interface ResourceService {
  ResponseEntity<ResponseResourceDTO> upload(byte[] file);

  ResponseEntity<byte[]> findById(long resourceId);

  ResponseEntity<ResponseResourcesDTO> deleteByIds(String resourceIds);
}
