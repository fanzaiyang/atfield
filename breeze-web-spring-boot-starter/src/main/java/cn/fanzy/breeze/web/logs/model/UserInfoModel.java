package cn.fanzy.breeze.web.logs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 用户信息模型
 *
 * @author fanzaiyang
 * @date 2023-04-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoModel {
    private String userId;
    private String userName;

    private Map<String,Object> extra;
}
