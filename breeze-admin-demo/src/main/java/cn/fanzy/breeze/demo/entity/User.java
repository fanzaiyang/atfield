package cn.fanzy.breeze.demo.entity;

import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends IBaseEntity {
    private String id;
    private String code;
    private String name;
    private Integer age;
}
