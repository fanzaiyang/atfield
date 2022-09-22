package cn.fanzy.breeze.core.calendar.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 一天
 *
 * @author fanzaiyang
 * @date 2021/09/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Day {
    /**
     * 公历年份
     */
    private int year;
    /**
     * 公历月份
     */
    private int month;
    /**
     * 公历日期
     */
    private int date;
    /**
     * 公历一年中的第几周，注意这里的年份是ISO-8601周编号年份，始终以周一至周日为一周。如需获取7天为一周直接使用年份中的天数除7即可。
     */
    private int yearweek;
    /**
     * 公历一年中的第几天
     */
    private int yearday;
    /**
     * 农历年份
     */
    private int lunarYear;
    /**
     * 农历月份
     */
    private int lunarMonth;
    /**
     * 农历日期
     */
    private int lunarDate;
    /**
     * 农历一年中的第几天
     */
    private int lunarYearday;
    /**
     * 星期几
     */
    private int week;
    /**
     * 是否为周末,1-周末,2-非周末
     */
    private int weekend;
    /**
     * 是否为工作日（包含调休在内需要上班的日子）
     */
    private int workday;
    /**
     * 节假日，这里使用两位数字枚举表示节假日，其中特殊数字10表示非节假日，特殊数字99表示全部节假日
     * <ul>
     *     <li>22 元旦</li>
     *     <li>12 腊八节</li>
     *     <li>13 小年</li>
     *     <li>14 中国人民警察节</li>
     *     <li>15 除夕</li>
     *     <li>11 春节</li>
     *     <li>16 元宵节</li>
     *     <li>17 情人节</li>
     *     <li>18 龙头节</li>
     *     <li>19 妇女节</li>
     *     <li>21 植树节</li>
     *     <li>23 愚人节</li>
     *     <li>44 清明节</li>
     *     <li>45 复活节</li>
     *     <li>55 劳动节</li>
     *     <li>56 青年节</li>
     *     <li>58 母亲节</li>
     *     <li>59 护士节</li>
     *     <li>61 儿童节</li>
     *     <li>62 父亲节</li>
     *     <li>66 端午节</li>
     *     <li>67 建党节</li>
     *     <li>68 建军节</li>
     *     <li>69 中国医师节</li>
     *     <li>70 七夕节</li>
     *     <li>71 中元节</li>
     *     <li>72 教师节</li>
     *     <li>77 中秋节</li><li>78 中国农民丰收节</li>
     *     <li>88 国庆节</li><li>89 重阳节</li>
     *     <li>90 老年节</li><li>91 记者节</li>
     *     <li>92 寒衣节</li><li>93 大学生节</li>
     *     <li>95 感恩节</li><li>96 下元节</li>
     *     <li>97 圣诞节</li>
     * </ul>
     */
    private int holiday;
    /**
     * 其他节假日，枚举与节假日相同，表示同一天中的另一个节日，如 2020-10-01
     */
    private int holidayOr;
    /**
     * 节假日调休，枚举与节假日相同
     */
    private int holidayOvertime;
    /**
     * 是否为节日当天,1-节日当天,2-非节日当天
     */
    private int holidayToday;
    /**
     * 是否为法定节假日（三倍工资）,1-法定节假日,2-非法定节假日
     */
    private int holidayLegal;
    /**
     * 是否为假期节假日（节日是否放假）,1-假期节假日,2-非假期节假日
     */
    private int holidayRecess;
    private String yearCn;
    private String monthCn;
    private String dateCn;
    private String yearweekCn;
    private String yeardayCn;
    private String lunarYearCn;
    private String lunarMonthCn;
    private String lunarDateCn;
    private String lunarYeardayCn;
    private String weekCn;
    private String weekendCn;
    private String workdayCn;
    private String holidayCn;
    private String holidayOrCn;
    private String holidayOvertimeCn;
    private String holidayTodayCn;
    private String holidayLegalCn;
    private String holidayRecessCn;
}
