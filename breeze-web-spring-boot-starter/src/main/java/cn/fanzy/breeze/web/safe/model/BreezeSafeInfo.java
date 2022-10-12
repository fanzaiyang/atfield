package cn.fanzy.breeze.web.safe.model;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BreezeSafeInfo implements Serializable {
    private String loginId;
    private String loginIp;
    private Date expireTime;
    private String deadTime;
    private int failNum;

    public String getDeadTime() {
        if (expireTime == null) {
            return "";
        }
        return DateUtil.format(expireTime, "MM月dd日HH点mm分");
    }
}
