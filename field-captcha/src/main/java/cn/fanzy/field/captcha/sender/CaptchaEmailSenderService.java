package cn.fanzy.field.captcha.sender;

import cn.fanzy.field.captcha.bean.CaptchaCode;
import cn.fanzy.field.captcha.enums.CaptchaType;
import cn.fanzy.field.captcha.enums.ICaptchaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Slf4j
@RequiredArgsConstructor
public abstract class CaptchaEmailSenderService implements CaptchaSenderService {

    public abstract void sendCode(String target, CaptchaCode codeInfo);
    @Override
    public void send(String target, CaptchaCode codeInfo) {
        sendCode(target,codeInfo);
    }

    @Override
    public boolean isSupported(ICaptchaType type) {
        return CaptchaType.EMAIL.equals(type);
    }


    protected String buildContent(CaptchaCode emailCode) {
        String now = DateTimeFormatter.ofPattern("yyyy年MM月dd").format(LocalDateTime.now());
        return MessageFormat.format(HTML_TEMPLATE, emailCode.getCode(), emailCode.getExpireAt(), now);
    }

    /**
     * 内容模板
     */
    private final static String HTML_TEMPLATE = "<body tabindex='0' role='listitem'>"
            + "    <table border='0' cellspacing='0' cellpadding='0' width='800' bgcolor='#0092ff' height='66'>"
            + "        <tbody>"
            + "            <tr>"
            + "                <td width='50'></td>"
//            + "                <td width='750'><img style='WIDTH: 135px' src='http://static.yishuifengxiao.com/common/mail_logo.png'>"
//            + "                </td>"
            + "            </tr>"
            + "        </tbody>"
            + "    </table>"
            + "    <table style='FONT-FAMILY: 黑体; FONT-SIZE: 10pt' border='0' cellspacing='0' cellpadding='50' width='800'>"
            + "        <tbody>"
            + "            <tr>"
            + "                <td width='800'>"
            + "                    <div>"
            + "                        <div style='FONT-SIZE: 11pt'>"
            + "                            亲爱的用户，您好！"
            + "                        </div>"
            + "                        <br>"
            + "                        <div style='FONT-SIZE: 11pt'>"
            + "                            您本次请求创建的验证码为："
            + "                        </div>"
            + "                        <br>"
            + "                        <br>"
            + "                        <div>"
            + "                            <span style='COLOR:black;FONT-SIZE:40px;font-weight:bold;'>{0}</span><br/>"
            + "                            <span style='COLOR:red;FONT-SIZE:11px'>(为了保障您帐号的安全性，请在  {1}  之前完成验证。)</span>"
            + "                        </div>"
            + "                        <br>"
            + "                        <br>"
            + "                        <hr"
            + "                            style='BORDER-BOTTOM:#808080 0px dashed; BORDER-LEFT:#808080 0px dashed;HEIGHT:1px;BORDER-TOP:#808080 1px dashed;BORDER-RIGHT:#808080 0px dashed'>"
            + "                        <br>"
            + "                        <div style='COLOR: #808080'>"
            + "                            此邮件由系统自动发出，系统不接受回信，因此请勿直接回复。"
            + "                            <br> 注意事项："
            + "                            <br> 1、请不要在其他网站上使用相同的邮箱和密码进行注册。"
            + "                            <br> 2、请不要告知任何人您的密码信息，包括本网站相关的工作人员。"
            + "                            <br>"
            + "                            <br> 如果您错误的收到本电子邮件，请您忽略上述内容。"
            + "                        </div>"
            + "                        <br>"
            + "                        <hr style='BORDER-BOTTOM: #808080 0px dashed; BORDER-LEFT: #808080 0px dashed; HEIGHT: 1px; BORDER-TOP: #808080 1px dashed; BORDER-RIGHT: #808080 0px dashed'>"
            + "                        <div>"
            + "                            <br>"
            + "                        </div>"
            + "                        <div style='TEXT-ALIGN: right; FONT-SIZE: 11pt'>"
            + "                            银丰"
            + "                        </div>"
            + "                        <div style='TEXT-ALIGN: right; FONT-SIZE: 11pt'>"
            + "                            {2}"
            + "                        </div>"
            + "                    </div>"
            + "                </td>"
            + "            </tr>"
            + "        </tbody>"
            + "    </table>"
            + "" + "" + "" + ""
            + "    <style type='text/css'>"
            + "        body '{' font-size: 14px;"
            + "            font-family: arial, verdana, sans-serif;"
            + "            line-height: 1.666;"
            + "            padding: 0;"
            + "            margin: 0;"
            + "            overflow: auto;"
            + "            white-space: normal;"
            + "            word-wrap: break-word;"
            + "            min-height: 100px"
            + "        '}'" + ""
            + "        td,input,button,select,body '{'"
            + "            font-family: Helvetica, 'Microsoft Yahei', verdana" + "        '}'" + "" + "        pre '{'"
            + "            white-space: pre-wrap;" + "            white-space: -moz-pre-wrap;"
            + "            white-space: -pre-wrap;" + "            white-space: -o-pre-wrap;"
            + "            word-wrap: break-word;" + "            width: 95%" + "        '}'" + "" + "        th,"
            + "        td '{'" + "            font-family: arial, verdana, sans-serif;"
            + "            line-height: 1.666" + "        '}'" + "" + "        img '{'" + "            border: 0"
            + "        '}'" + "" + "        header," + "        footer," + "        section," + "        aside,"
            + "        article," + "        nav," + "        hgroup," + "        figure," + "        figcaption '{'"
            + "            display: block" + "        '}'" + "" + "        blockquote '{'"
            + "            margin-right: 0px" + "        '}'" + "    </style>" + "" + "" + ""
            + "    <style id='netease_mail_footer_style' type='text/css'>" + "        #netease_mail_footer '{'"
            + "            display: none;" + "        '}'" + "    </style>" + "" + ""
            + "    <style id='ntes_link_color' type='text/css'>" + "        a," + "        td a '{'"
            + "            color: #064977" + "        '}'" + "    </style>" + "" + "</body>";
}
