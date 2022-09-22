package cn.fanzy.breeze.core.calendar.utils;

import cn.fanzy.breeze.core.calendar.bean.Day;
import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * <h1>日历工具类</h1>
 * <pre>
 *     建议添加缓存到你的内容或数据库中。
 * </pre>
 *
 * @author fanzaiyang
 * @date 2021/09/22
 */
@Slf4j
public class CalendarUtil {

    public static final String API_URI = "https://api.apihubs.cn/holiday/get?year={}&order_by=1&cn=1&page={}&size={}";

    /**
     * 查询某年每一天详细信息
     *
     * @param year 年份，如2021、2022等
     * @return {@link List}<{@link Day}>
     */
    public static List<Day> queryDayList(int year) {
        String url = StrUtil.format(API_URI, year, 1, 31);
        log.debug("节假日请求地址：{}", url);
        String result = HttpUtil.get(url);
        log.debug("节假日查询结果：{}", result);
        if (!JSONUtil.isJson(result)) {
            throw new JSONException("查询结果不是JSON格式！");
        }
        JSONObject obj = JSONUtil.parseObj(result);
        if (!StrUtil.equals("0", obj.getStr("code"))) {
            throw new RuntimeException(obj.getStr("msg"));
        }
        JSONObject data = obj.getJSONObject("data");
        int page = data.getInt("page");
        int size = data.getInt("size");
        int total = data.getInt("total");
        int totalPage = PageUtil.totalPage(total, size);
        log.debug("当前页:{}/{}，每页个数:{},总个数:{}。", page, totalPage, size, total);
        List<Day> dayList = data.getJSONArray("list").toList(Day.class);
        for (int i = 2; i <= totalPage; i++) {
            url = StrUtil.format(API_URI, year, i, 31);
            log.debug("节假日请求地址：{}", url);
            log.debug("当前页:{}/{}，每页个数:{},总个数:{}。", i, totalPage, size, total);
            result = HttpUtil.get(url);
            log.debug("节假日查询结果：{}", result);
            if (!JSONUtil.isTypeJSONObject(result)) {
                throw new JSONException("查询结果不是JSON格式！");
            }
            obj = JSONUtil.parseObj(result);
            if (!StrUtil.equals("0", obj.getStr("code"))) {
                throw new RuntimeException(obj.getStr("msg"));
            }
            data = obj.getJSONObject("data");
            List<Day> days = data.getJSONArray("list").toList(Day.class);
            dayList.addAll(days);
        }
        log.debug("节假日查询成功！{}", dayList.size());
        return dayList;
    }
}
