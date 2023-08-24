package me.chanjar.weixin.cp.api.impl;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.json.GsonHelper;
import me.chanjar.weixin.common.util.json.GsonParser;
import me.chanjar.weixin.cp.api.WxCpDepartmentService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpDepart;
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
public class WxCpDepartmentServiceImpl implements WxCpDepartmentService {
    private final WxCpService mainService;

    @Override
    public Long create(WxCpDepart depart) throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_CREATE);
        String responseContent = this.mainService.post(url, depart.toJson());
        JsonObject tmpJsonObject = GsonParser.parse(responseContent);
        return GsonHelper.getAsLong(tmpJsonObject.get("id"));
    }

    @Override
    public WxCpDepart get(Long id) throws WxErrorException {
        String url = String.format(this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_GET), id);
        String responseContent = this.mainService.get(url, null);
        JsonObject tmpJsonObject = GsonParser.parse(responseContent);
        return WxCpGsonBuilder.create()
                .fromJson(tmpJsonObject.get("department"),
                        new TypeToken<WxCpDepart>() {
                        }.getType()
                );
    }


    @Override
    public void update(WxCpDepart group) throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_UPDATE);
        this.mainService.post(url, group.toJson());
    }

    @Override
    public void delete(Long departId) throws WxErrorException {
        String url = String.format(this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_DELETE), departId);
        this.mainService.get(url, null);
    }

    @Override
    public List<WxCpDepart> list(Long id) throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_LIST);
        if (id != null) {
            url += "?id=" + id;
        }

        String responseContent = this.mainService.get(url, null);
        JsonObject tmpJsonObject = GsonParser.parse(responseContent);
        return WxCpGsonBuilder.create()
                .fromJson(tmpJsonObject.get("department"),
                        new TypeToken<List<WxCpDepart>>() {
                        }.getType()
                );
    }


    @Override
    public List<WxCpDepart> simpleList(Long id) throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_SIMPLE_LIST);
        if (id != null) {
            url += "?id=" + id;
        }

        String responseContent = this.mainService.get(url, null);
        JsonObject tmpJsonObject = GsonParser.parse(responseContent);
        return WxCpGsonBuilder.create()
                .fromJson(tmpJsonObject.get("department_id"),
                        new TypeToken<List<WxCpDepart>>() {
                        }.getType()
                );
    }
}
