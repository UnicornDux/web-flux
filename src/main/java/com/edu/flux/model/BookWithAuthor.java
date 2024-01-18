package com.edu.flux.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.Instant;

@Data
@Accessors(chain = true)
@Table("book")
public class BookWithAuthor implements Serializable {

    private Long id;
    private String name;
    private Long authorId;
    private User author;

    // webFlux 中的时间类型使用 java.time.Instant
    private Instant publishTime;

}
