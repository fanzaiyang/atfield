package cn.fanzy.breeze.web.code.sender;

import cn.fanzy.breeze.web.code.model.BreezeCode;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * 验证码发送器
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
public interface BreezeCodeSender<T extends BreezeCode> {

    /**
     * <p>
     * 发送验证码到指定的目标
     * </p>
     * 一般来说发送目标的含义如下:
     *
     * <ul>
     * <li>对于短信验证码，一般来说标识符为发送目标的手机号</li>
     * <li>对于邮件验证码，一般来说标识符为发送目标的邮箱地址</li>
     * <li>对于图形验证码，一般来说为与用户约定的字符</li>
     * </ul>
     *
     * @param code    验证码的类型
     * @param request 用户请求
     * @param target  验证码发送目标
     */
    void send(ServletWebRequest request, String target, T code);
}