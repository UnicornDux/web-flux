package com.edu.flux.respoistory;

import com.edu.flux.model.Book;
import com.edu.flux.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

/**
 * 继承 R2dbcRepository 之后基本的 CURD 都支持了
 */
@Repository
public interface UserRepository extends R2dbcRepository<User, Long> {

    // QBC  Query By Criteria
    // QBE  Query By Example

    // 复杂的条件查询（单表）, 按照一定的命名规则指定方法名称即可
    Flux<User> findByIdInAndUsername(Collection<Long> id, String username);


    // 更复杂的查询可以使用注解自定义 sql
    @Query("select * from user where id = ?")
    Flux<User> queryUserAccordingId(Long id);


    // 多表关联的用法, 这里 Book 内还有一个 author 表示作者
    // 这里因为我们关联查询的时候，自动映射的时候，框架不知道 Book 中
    // 不在 book 表中的字段应该要怎么填充字段，需要自定义转换器
    @Query("select b.*, u.username from user u left join book b on b.author = u.id where b.id = :id")
    Mono<Book> queryBookById(@Param("id")Long id);


}
