package me.chanjar.weixin.cp.api.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.json.GsonHelper;
import me.chanjar.weixin.common.util.json.GsonParser;
import me.chanjar.weixin.cp.api.WxCpDepartmentInfoService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpDepartInfo;
import me.chanjar.weixin.cp.util.json.WxCpGsonBuilder;

import java.util.List;

import static me.chanjar.weixin.cp.constant.WxCpApiPathConsts.Department.*;

/**
 * <pre>
 *  部门管理接口
 *  Created by BinaryWang on 2017/6/24.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RequiredArgsConstructor
public class WxCpDepartmentInfoServiceImpl implements WxCpDepartmentInfoService {
    private final WxCpService mainService;


    @Override
    public String create(WxCpDepartInfo depart) throws WxErrorException {
        // check
        if (StrUtil.isNotBlank(depart.getId())) {
            Assert.isTrue(depart.getId().length() <= 32, "部门id必须小于32位整形");
            Assert.isTrue(NumberUtil.isNumber(depart.getId()), "部门id必须是小于32位整形");
        }
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_CREATE);
        String responseContent = this.mainService.post(url, depart.toJson());
        JsonObject tmpJsonObject = GsonParser.parse(responseContent);
        return GsonHelper.getAsString(tmpJsonObject.get("id"));
    }


    @Override
    public WxCpDepartInfo get(String id) throws WxErrorException {
        String url = String.format(this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_GET), id);
        String responseContent = this.mainService.get(url, null);
        JsonObject tmpJsonObject = GsonParser.parse(responseContent);
        return WxCpGsonBuilder.create()
                .fromJson(tmpJsonObject.get("department"),
                        new TypeToken<WxCpDepartInfo>() {
                        }.getType()
                );
    }


    @Override
    public void update(WxCpDepartInfo group) throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_UPDATE);
        this.mainService.post(url, group.toJson());
    }


    @Override
    public void delete(String departId) throws WxErrorException {
        String url = String.format(this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_DELETE), departId);
        this.mainService.get(url, null);
    }

    @Override
    public List<WxCpDepartInfo> list(String id) throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_LIST);
        if (id != null) {
            url += "?id=" + id;
        }
        String responseContent = this.mainService.get(url, null);
        JsonObject tmpJsonObject = GsonParser.parse(responseContent);
        return WxCpGsonBuilder.create()
                .fromJson(tmpJsonObject.get("department"),
                        new TypeToken<List<WxCpDepartInfo>>() {
                        }.getType()
                );
    }


    @Override
    public List<WxCpDepartInfo> simpleList(String id) throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_SIMPLE_LIST);
        if (id != null) {
            url += "?id=" + id;
        }

        String responseContent = this.mainService.get(url, null);
        JsonObject tmpJsonObject = GsonParser.parse(responseContent);
        return WxCpGsonBuilder.create()
                .fromJson(tmpJsonObject.get("department_id"),
                        new TypeToken<List<WxCpDepartInfo>>() {
                        }.getType()
                );
    }
}
