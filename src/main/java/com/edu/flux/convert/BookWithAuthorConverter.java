package com.edu.flux.convert;

import com.edu.flux.model.BookWithAuthor;
import com.edu.flux.model.User;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.Instant;


// 标注这是一个在读取数据记录的时候可以使用的 converter
// 同时将这个 converter 配置到 converter 容器中，
// 见配置类 R2dbcConfiguration
// ---------------------------------------------------
// 注意，这个converter 会影响所有返回 BookWithAuthor 的操作
@ReadingConverter
public class BookWithAuthorConverter implements Converter<Row, BookWithAuthor> {
    @Override
    public BookWithAuthor convert(Row source) {
        User user = new User()
                .setId(source.get("authorId", Long.class))
                .setUsername(source.get("username", String.class));
        return new BookWithAuthor()
                .setAuthorId(source.get("authorId", Long.class))
                .setName(source.get("name", String.class))
                .setPublishTime(source.get("publishTime", Instant.class))
                .setId(source.get("id", Long.class))
                .setAuthor(user);
    }
}
