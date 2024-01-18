package com.edu.flux.r2dbc;

import com.edu.flux.model.AuthWithBooks;
import com.edu.flux.model.Book;
import com.edu.flux.model.User;
import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration;
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class TestR2BC {


    /**
     * 原生 API 查询
     * @throws IOException
     */
    @Test
    public void testQuery() throws IOException {
        MySqlConnectionConfiguration options = MySqlConnectionConfiguration
                .builder()
                .host("localhost")
                .port(3306)
                .database("vlog")
                .user("root")
                .password("root")
                .build();

        // r2dbc 基于全异步的，响应式，消息驱动
        // 获取连接工厂
        MySqlConnectionFactory factory = MySqlConnectionFactory.from(options);

        // 3. 数据发布者
        Mono.from(factory.create())
                .flatMapMany(connection -> connection
                        .createStatement("select * from user where id = ?")
                        .bind(0, 2L) // 索引参数
                        // .bind("id", 2L) // 具名参数 (select * from user where id = ?id)
                        .execute())
                .flatMap(result -> result.map(readable -> {
                    Long id = readable.get("id", Long.class);
                    String username = readable.get("username", String.class);
                    return new User().setId(id).setUsername(username);
                }))
                .subscribe(user -> System.out.println("user" + user));

        System.in.read();
    }

    // SpringBoot 封装的  APi, 无法用于复杂的关联查询，可以进行简单的 CURD
    @Autowired
    private R2dbcEntityTemplate entityTemplate;

    @Test
    public void testEntityTemplate() {

        // 构建查询条件
        Criteria criteria = Criteria.empty()
                .and("id").is("2L")
                .and("username").is("alex");
        Query query = Query.query(criteria);

        // 查询数据
        entityTemplate
                .select(query, User.class)
                .subscribe(user -> System.out.println("user = " + user));

    }

    // 更贴近底层的 API, 复杂操作可以使用
    @Autowired
    private DatabaseClient databaseClient;

    @Test
    public void testDatabaseClient() throws IOException {
        databaseClient.sql("select * from user where id = ?")
                .bind(0, 2L)
                .fetch() // 抓取数据
                .all() // 返回所有
                .map( map -> {
                   // 从数据库抓取的数据都被封装成了一个个的map
                    System.out.println("map =" + map);
                    String id = map.get("id").toString();
                    String username = map.get("username").toString();
                    // 将 map 封装为我们需要的 POJO 对象
                    return new User().setId(Long.valueOf(id)).setUsername(username);
                })
                .subscribe(item -> System.out.println("item = " + item));
        System.in.read();
    }


    // 1-N 数据怎么查询（一个作者和它所有书）
    @Test
    public void testOneToNQuery() {


        databaseClient.sql("")
                .fetch()
                .all()
                .bufferUntilChanged(row -> Long.parseLong(row.get("aid").toString()))
                .map(list -> {
                    Map<String,Object> map = list.get(0);
                    AuthWithBooks user = new AuthWithBooks()
                            .setId(Long.parseLong(map.get("aid").toString()))
                            .setName(map.get("username").toString());

                    List<Book> books = list.stream()
                            .map(ele -> new Book()
                                .setId(Long.parseLong(ele.get("id").toString()))
                                .setName(ele.get("name").toString())
                            ).collect(Collectors.toList());

                    return user.setBooks(books);

                }).subscribe(item -> System.out.println("item = " + item));
    }




}
