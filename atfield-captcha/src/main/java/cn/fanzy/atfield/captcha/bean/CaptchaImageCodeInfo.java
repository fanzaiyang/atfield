package cn.fanzy.atfield.captcha.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaImageCodeInfo extends CaptchaCodeInfo {
    @Serial
    private static final long serialVersionUID = 1818762193843128112L;
    /**
     * 形象
     */
    @JsonIgnore
    private transient BufferedImage image;
    /**
     * 基于图像64
     */
    @JsonIgnore
    private transient String imageBase64;

}
