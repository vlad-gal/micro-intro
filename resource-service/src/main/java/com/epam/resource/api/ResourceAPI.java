package com.epam.resource.api;

import com.epam.resource.dto.ResponseResourceDTO;
import com.epam.resource.dto.ResponseResourcesDTO;
import com.epam.resource.service.resource.ResourceService;
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
@RequestMapping(value = "resources", produces = MediaType.APPLICATION_JSON_VALUE)
public class ResourceAPI {
  private final ResourceService resourceService;

  @PostMapping
  public ResponseEntity<ResponseResourceDTO> upload(@RequestBody byte[] file) {
    return resourceService.upload(file);
  }

  @GetMapping("{resource-id}")
  public ResponseEntity<byte[]> findById(@PathVariable("resource-id") long resourceId) {
    return resourceService.findById(resourceId);
  }

  @DeleteMapping
  public ResponseEntity<ResponseResourcesDTO> deleteByIds(@RequestParam("id") String resourceIds) {
    return resourceService.deleteByIds(resourceIds);
  }
}
