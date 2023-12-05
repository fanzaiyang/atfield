package cn.fanzy.breeze.web.code.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

/**
 * 验证码实体类
 *
 * @author fanzaiyang
 * @since 2022-08-16
 */
@Getter
@Setter
public class BreezeImageCode extends BreezeCode{
    private static final long serialVersionUID = 1204287637260727275L;
    @JsonIgnore
    private transient BufferedImage image;
    private transient String imageBase64;

    public BreezeImageCode(String code, int maxRetryCode, long expireTimeInSeconds) {
        super(code, maxRetryCode, expireTimeInSeconds);
    }


}
