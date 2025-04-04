package com.epam.resource.service.resource;

import com.epam.resource.dto.ResourceMetadataDTO;
import com.epam.resource.dto.ResponseResourceDTO;
import com.epam.resource.dto.ResponseResourcesDTO;
import com.epam.resource.model.Resource;
import com.epam.resource.repository.ResourceRepository;
import com.epam.resource.service.metadata.ResourceMetadataExtractor;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
  private final ResourceRepository resourceRepository;
  private final ResourceMetadataExtractor resourceMetadataExtractor;
  private final RestTemplate restTemplate;

  @Value("${song.host.url}")
  private String songUrl;

  @Override
  public ResponseEntity<ResponseResourceDTO> upload(byte[] file) {
    Resource resource = new Resource().setData(file);

    resourceRepository.save(resource);
    Long resourceId = resource.getId();
    ResourceMetadataDTO resourceMetadata =
        resourceMetadataExtractor.extractMetadata(file).setId(resourceId);

    restTemplate.postForEntity(songUrl, resourceMetadata, Void.class);

    return ResponseEntity.ok(new ResponseResourceDTO(resourceId));
  }

  @Override
  public ResponseEntity<byte[]> findById(long resourceId) {
    if (resourceId <= 0) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "Invalid value '%s' for ID. Must be a positive integer".formatted(resourceId));
    }
    Resource resource =
        resourceRepository
            .findById(resourceId)
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Resource with ID=%d not found".formatted(resourceId)));
    return ResponseEntity.ok()
        .contentType(MediaType.valueOf("audio/mpeg"))
        .body(resource.getData());
  }

  @Override
  @Transactional
  public ResponseEntity<ResponseResourcesDTO> deleteByIds(String resourceIds) {
    if (resourceIds.length() > 200) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "CSV string is too long: received %d characters, maximum allowed is 200"
              .formatted(resourceIds.length()));
    }
    String[] ids = resourceIds.split(",");
    List<String> invalidIds = Arrays.stream(ids).filter(id -> !id.matches("\\d+")).toList();
    if (!invalidIds.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "Invalid ID format: '%s'. Only positive integers are allowed".formatted(invalidIds));
    }

    List<Long> validIds =
        Arrays.stream(ids).filter(id -> id.matches("\\d+")).map(Long::parseLong).toList();

    List<Resource> resources = resourceRepository.findAllById(validIds);
    resourceRepository.deleteAll(resources);

    List<Long> deletedIds = resources.stream().map(Resource::getId).toList();

    String url =
        UriComponentsBuilder.fromUriString(songUrl).queryParam("id", resourceIds).toUriString();
    restTemplate.delete(url);

    return ResponseEntity.ok().body(new ResponseResourcesDTO(deletedIds));
  }
}
