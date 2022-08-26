package com.example.breeze.web.entity;

import cn.fanzy.breeze.sqltoy.core.config.annotation.*;
import lombok.*;
import cn.fanzy.breeze.sqltoy.core.plugins.id.impl.RedisIdGenerator;

import java.sql.Types;

@Secure(field = "")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "user")
public class User extends BaseEntity {
    @Id
    @Column(name = "id", type = Types.VARCHAR, length = 32)
    private String id;
    @BusinessId(generator = RedisIdGenerator.class,sequenceSize = 4)
    private String code;
    @Column(name = "name", type = Types.VARCHAR, length = 90)
    private String name;
    private Integer age;
}
