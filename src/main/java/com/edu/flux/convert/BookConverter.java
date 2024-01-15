package com.edu.flux.convert;

import com.edu.flux.model.Book;
import com.edu.flux.model.User;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.Instant;


// 标注这是一个在读取数据记录的时候可以使用的 converter
// 同时将这个 converter 配置到 converter 容器中，
// 见配置类 R2dbcConfiguration
@ReadingConverter
public class BookConverter implements Converter<Row, Book> {
    @Override
    public Book convert(Row source) {
        User user = new User()
                .setId(source.get("authorId", Long.class))
                .setUsername(source.get("username", String.class));
        return new Book()
                .setAuthorId(source.get("authorId", Long.class))
                .setName(source.get("name", String.class))
                .setPublishTime(source.get("publishTime", Instant.class))
                .setId(source.get("id", Long.class))
                .setAuthor(user);
    }
}
