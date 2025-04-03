package com.epam.resource.model;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "RESOURCE")
@Data
@Accessors(chain = true)
public class Resource {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Lob private byte[] data;
}
