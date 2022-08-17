package cn.fanzy.breeze.web.code.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

/**
 * 验证码实体类
 *
 * @author fanzaiyang
 * @date 2022-08-16
 */
@Getter
@Setter
public class BreezeImageCode extends BreezeCode{
    @JsonIgnore
    private transient BufferedImage image;
    private transient String imageBase64;

    public BreezeImageCode(long expireTimeInSeconds, String code, int retryCount) {
        super(expireTimeInSeconds, code, retryCount);
    }


}
