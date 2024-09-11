package cn.fanzy.atfield.satoken.utils;

import cn.dev33.satoken.secure.BCrypt;

import java.util.regex.Pattern;

/**
 * bcrypt 实用程序
 *
 * @author fanzaiyang
 * @date 2024/09/11
 */
public class BCryptUtils extends BCrypt {
    private static final Pattern BCRYPT_PATTERN = Pattern
            .compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");

    /**
     * 被编码
     *
     * @param encodedPassword 编码密码
     * @return boolean
     */
    public static boolean isEncoded(String encodedPassword) {
        return BCRYPT_PATTERN.matcher(encodedPassword).matches();
    }

    /**
     * 生成密文，使用长度为10的加盐方式
     * <p>如果传入的密码已经是密文，则直接返回</p>
     *
     * @param password 需要加密的明文
     * @return {@link String }
     */
    public static String encode(String password) {
        if (isEncoded(password)) {
            return password;
        }
        return hashpw(password);
    }
}
