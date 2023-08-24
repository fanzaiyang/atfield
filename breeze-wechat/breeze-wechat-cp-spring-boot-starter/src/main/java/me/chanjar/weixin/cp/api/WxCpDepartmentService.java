package me.chanjar.weixin.cp.api;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpDepartInfo;

import java.util.List;

/**
 * <pre>
 *  部门管理接口
 *  Created by BinaryWang on 2017/6/24.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public interface WxCpDepartmentService {

    /**
     * <pre>
     * 部门管理接口 - 创建部门.
     * 最多支持创建500个部门
     * 详情请见: <a href="https://work.weixin.qq.com/api/doc#90000/90135/90205">...</a>
     * </pre>
     *
     * @param depart 部门
     * @return 部门id long
     * @throws WxErrorException 异常
     */
    Long create(WxCpDepart depart) throws WxErrorException;

    /**
     * <pre>
     * 部门管理接口 - 创建部门.
     * 最多支持创建500个部门
     * 详情请见: <a href="https://work.weixin.qq.com/api/doc#90000/90135/90205">...</a>
     * </pre>
     *
     * @param depart 部门
     * @return 部门id long
     * @throws WxErrorException 异常
     */
    String create(WxCpDepartInfo depart) throws WxErrorException;

    /**
     * <pre>
     * 「已过时」请使用WxCpDepartInfo get(String id)
     * 部门管理接口 - 获取单个部门详情.
     * 详情请见: <a href="https://developer.work.weixin.qq.com/document/path/95351">...</a>
     * </pre>
     *
     * @param id 部门id
     * @return 部门信息 wx cp depart
     * @throws WxErrorException 异常
     */
    WxCpDepart get(Long id) throws WxErrorException;

    /**
     * <pre>
     * 部门管理接口 - 获取单个部门详情.
     * 详情请见: <a href="https://developer.work.weixin.qq.com/document/path/95351">...</a>
     * </pre>
     *
     * @param id 部门id
     * @return 部门信息 wx cp depart
     * @throws WxErrorException 异常
     */
    WxCpDepartInfo get(String id) throws WxErrorException;

    /**
     * <pre>
     * 部门管理接口 - 获取部门列表.
     * 详情请见: <a href="https://work.weixin.qq.com/api/doc#90000/90135/90208">...</a>
     * </pre>
     *
     * @param id 部门id。获取指定部门及其下的子部门。非必需，可为null
     * @return 获取的部门列表 list
     * @throws WxErrorException 异常
     */
    List<WxCpDepart> list(Long id) throws WxErrorException;
    /**
     * <pre>
     * 部门管理接口 - 获取部门列表.
     * 详情请见: <a href="https://work.weixin.qq.com/api/doc#90000/90135/90208">...</a>
     * </pre>
     *
     * @param id 部门id。获取指定部门及其下的子部门。非必需，可为null
     * @return 获取的部门列表 list
     * @throws WxErrorException 异常
     */
    List<WxCpDepartInfo> list(String id) throws WxErrorException;

    /**
     * <pre>
     * 部门管理接口 - 获取子部门ID列表.
     * 详情请见: <a href="https://developer.work.weixin.qq.com/document/path/95350">...</a>
     * </pre>
     *
     * @param id 部门id。获取指定部门及其下的子部门（以及子部门的子部门等等，递归）。 如果不填，默认获取全量组织架构
     * @return 子部门ID列表 list
     * @throws WxErrorException 异常
     */
    List<WxCpDepart> simpleList(Long id) throws WxErrorException;
    /**
     * <pre>
     * 部门管理接口 - 获取子部门ID列表.
     * 详情请见: <a href="https://developer.work.weixin.qq.com/document/path/95350">...</a>
     * </pre>
     *
     * @param id 部门id。获取指定部门及其下的子部门（以及子部门的子部门等等，递归）。 如果不填，默认获取全量组织架构
     * @return 子部门ID列表 list
     * @throws WxErrorException 异常
     */
    List<WxCpDepartInfo> simpleList(String id) throws WxErrorException;
    /**
     * <pre>
     * 部门管理接口 - 更新部门.
     * 详情请见: <a href="https://work.weixin.qq.com/api/doc#90000/90135/90206">...</a>
     * 如果id为0(未部门),1(黑名单),2(星标组)，或者不存在的id，微信会返回系统繁忙的错误
     * </pre>
     *
     * @param group 要更新的group，group的id,name必须设置
     * @throws WxErrorException 异常
     */
    void update(WxCpDepart group) throws WxErrorException;
    /**
     * <pre>
     * 部门管理接口 - 更新部门.
     * 详情请见: <a href="https://work.weixin.qq.com/api/doc#90000/90135/90206">...</a>
     * 如果id为0(未部门),1(黑名单),2(星标组)，或者不存在的id，微信会返回系统繁忙的错误
     * </pre>
     *
     * @param group 要更新的group，group的id,name必须设置
     * @throws WxErrorException 异常
     */
    void update(WxCpDepartInfo group) throws WxErrorException;
    /**
     * <pre>
     * 部门管理接口 - 删除部门.
     * 详情请见: <a href="https://work.weixin.qq.com/api/doc#90000/90135/90207">...</a>
     * 应用须拥有指定部门的管理权限
     * </pre>
     *
     * @param departId 部门id
     * @throws WxErrorException 异常
     */
    void delete(Long departId) throws WxErrorException;
    /**
     * <pre>
     * 部门管理接口 - 删除部门.
     * 详情请见: <a href="https://work.weixin.qq.com/api/doc#90000/90135/90207">...</a>
     * 应用须拥有指定部门的管理权限
     * </pre>
     *
     * @param departId 部门id
     * @throws WxErrorException 异常
     */
    void delete(String departId) throws WxErrorException;
}
