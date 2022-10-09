package com.example.breeze.web.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.sql.Types;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    private String id;
    private String code;
    private String name;
    private Integer age;
}
