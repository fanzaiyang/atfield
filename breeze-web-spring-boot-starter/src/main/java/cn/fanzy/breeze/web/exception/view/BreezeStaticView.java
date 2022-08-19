package cn.fanzy.breeze.web.exception.view;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.View;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Slf4j
public class BreezeStaticView implements View {

    private static final MediaType TEXT_HTML_UTF8 = new MediaType("text", "html", StandardCharsets.UTF_8);

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (response.isCommitted()) {
            String message = getMessage(model);
            log.error(message);
            return;
        }
        response.setContentType(TEXT_HTML_UTF8.toString());
        StringBuilder builder = new StringBuilder();
        Object trace = model.get("trace");
        if (response.getContentType() == null) {
            response.setContentType(getContentType());
        }
        builder.append("<html class=\"h-full bg-gray-50\"><head>")
                .append("<meta charset=\"UTF-8\" />")
                .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />")
                .append(StrUtil.format("<title>{}</title>", "默认错误页 - 微风组件"))
                .append("<link href=\"https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css\" rel=\"stylesheet\">")
                .append("</head><body class=\"h-full\">")
                .append("<div class=\"min-h-full flex justify-center py-12 px-4 sm:px-6 lg:px-8\">");
        builder.append("<div class=\"max-w-md w-full space-y-8\">");
        builder.append("<div>");
        builder.append("<div class=\"py-12\" style=\"width:80px;height:80px;margin:0px auto;\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 36 36\"><path fill=\"#FFCC4D\" d=\"M36 18c0 9.941-8.059 18-18 18-9.94 0-18-8.059-18-18C0 8.06 8.06 0 18 0c9.941 0 18 8.06 18 18\"></path><ellipse fill=\"#664500\" cx=\"18\" cy=\"27\" rx=\"5\" ry=\"6\"></ellipse><path fill=\"#664500\" d=\"M5.999 11c-.208 0-.419-.065-.599-.2-.442-.331-.531-.958-.2-1.4C8.462 5.05 12.816 5 13 5c.552 0 1 .448 1 1 0 .551-.445.998-.996 1-.155.002-3.568.086-6.204 3.6-.196.262-.497.4-.801.4zm24.002 0c-.305 0-.604-.138-.801-.4-2.64-3.521-6.061-3.598-6.206-3.6-.55-.006-.994-.456-.991-1.005C22.006 5.444 22.45 5 23 5c.184 0 4.537.05 7.8 4.4.332.442.242 1.069-.2 1.4-.18.135-.39.2-.599.2zm-16.087 4.5l1.793-1.793c.391-.391.391-1.023 0-1.414s-1.023-.391-1.414 0L12.5 14.086l-1.793-1.793c-.391-.391-1.023-.391-1.414 0s-.391 1.023 0 1.414l1.793 1.793-1.793 1.793c-.391.391-.391 1.023 0 1.414.195.195.451.293.707.293s.512-.098.707-.293l1.793-1.793 1.793 1.793c.195.195.451.293.707.293s.512-.098.707-.293c.391-.391.391-1.023 0-1.414L13.914 15.5zm11 0l1.793-1.793c.391-.391.391-1.023 0-1.414s-1.023-.391-1.414 0L23.5 14.086l-1.793-1.793c-.391-.391-1.023-.391-1.414 0s-.391 1.023 0 1.414l1.793 1.793-1.793 1.793c-.391.391-.391 1.023 0 1.414.195.195.451.293.707.293s.512-.098.707-.293l1.793-1.793 1.793 1.793c.195.195.451.293.707.293s.512-.098.707-.293c.391-.391.391-1.023 0-1.414L24.914 15.5z\"></path></svg></div>");
        String html = "<div class=\"bg-white shadow overflow-hidden sm:rounded-lg\">" +
                "  <div class=\"px-4 py-5 sm:px-6\">" +
                "    <h3 class=\"text-lg leading-6 font-medium text-gray-900\">系统错误</h3>" +
                "    <p class=\"mt-1 max-w-2xl text-sm text-gray-500\">这里展示具体的错误信息，以便排查。</p>" +
                "  </div>" +
                "  <div class=\"border-t border-gray-200\">" +
                "    <dl>" +
                "      <div class=\"bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6\">" +
                "        <dt class=\"text-sm font-medium text-gray-500\">错误码</dt>" +
                "        <dd class=\"mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2\">" + model.get("status") + "</dd>" +
                "      </div>" +
                "      <div class=\"bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6\">" +
                "        <dt class=\"text-sm font-medium text-gray-500\">错误路径</dt>" +
                "        <dd class=\"mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2\">" + model.get("path") + "</dd>" +
                "      </div>" +
                "      <div class=\"bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6\">" +
                "        <dt class=\"text-sm font-medium text-gray-500\">错误信息</dt>" +
                "        <dd class=\"mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2\">" + model.get("error") + "</dd>" +
                "      </div>" +
                "      <div class=\"bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6\">" +
                "        <dt class=\"text-sm font-medium text-gray-500\">Trace</dt>" +
                "        <dd class=\"mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2\">" + trace + "</dd>" +
                "      </div>" +
                "    </dl>" +
                "  </div>" +
                "</div>";
        builder.append(html);
        builder.append("</div>");
        builder.append("</div>");
        int year = DateUtil.year(new Date());
        builder.append("<div class=\"absolute bottom-0 w-full text-center pb-5 text-sm\" style=\"color:#999\">Copyright &copy; " + year + " Powered by <a target=\"_blank\" href=\"https://gitee.com/it-xiaofan\" class=\"underline\">小范同学</a> All Rights Reserved.</div>");
        builder.append("</body></html>");
        response.getWriter().append(builder.toString());
    }

    private String htmlEscape(Object input) {
        return (input != null) ? HtmlUtils.htmlEscape(input.toString()) : null;
    }

    private String getMessage(Map<String, ?> model) {
        Object path = model.get("path");
        String message = "Cannot render error page for request [" + path + "]";
        if (model.get("message") != null) {
            message += " and exception [" + model.get("message") + "]";
        }
        message += " as the response has already been committed.";
        message += " As a result, the response may have the wrong status code.";
        return message;
    }

    @Override
    public String getContentType() {
        return "text/html";
    }

}
