package com.epam.resource.service.metadata;

import com.epam.resource.dto.ResourceMetadataDTO;

public interface ResourceMetadataExtractor {
  ResourceMetadataDTO extractMetadata(byte[] file);
}
