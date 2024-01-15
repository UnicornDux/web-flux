package com.edu.flux.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Accessors(chain = true)
@Table("user")
public class User {

  private Long id;
  private String username;
  private String password;

}
