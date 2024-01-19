package cn.fanzy.atfield.sqltoy.plus.conditions.toolkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlStringUtil {

    /**
     * 字符串去除空白内容
     *
     * <ul> <li>\n 回车</li> <li>\t 水平制表符</li> <li>\s 空格</li> <li>\r 换行</li> </ul>
     */
    private static final Pattern REPLACE_BLANK = Pattern.compile("\\s*|\t|\r|\n");

    /**
     * SQL 注入字符串去除空白内容：
     * <ul>
     *     <li>\n 回车</li>
     *     <li>\t 水平制表符</li>
     *     <li>\s 空格</li>
     *     <li>\r 换行</li>
     * </ul>
     *
     * @param str 字符串
     * @return string
     */
    public static String sqlInjectionReplaceBlank(String str) {
        if (SqlInjectionUtils.check(str)) {
            /**
             * 存在 SQL 注入，去除空白内容
             */
            Matcher matcher = REPLACE_BLANK.matcher(str);
            return matcher.replaceAll("");
        }
        return str;
    }
}
