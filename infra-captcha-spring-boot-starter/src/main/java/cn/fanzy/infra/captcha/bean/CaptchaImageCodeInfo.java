package cn.fanzy.infra.captcha.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.awt.image.BufferedImage;
import java.io.Serial;

/**
 * captcha代码信息
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
public class CaptchaImageCodeInfo extends CaptchaCodeInfo {
    @Serial
    private static final long serialVersionUID = 1818762193843128112L;
    private transient BufferedImage image;
    private transient String imageBase64;
}
