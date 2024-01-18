package com.edu.flux.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;

import java.util.List;

@Data
@Accessors(chain = true)
public class AuthWithBooks {
    private Long id;
    private String name;

    // 非数据库字段，操作数据库时会自动忽略
    @Transient
    private List<Book> books;


}
