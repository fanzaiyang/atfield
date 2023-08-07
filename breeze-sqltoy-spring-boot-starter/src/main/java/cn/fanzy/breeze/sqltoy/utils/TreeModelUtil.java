package cn.fanzy.breeze.sqltoy.utils;

import cn.fanzy.breeze.core.utils.BreezeConstants;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import org.sagacity.sqltoy.model.TreeTableModel;

import java.io.Serializable;

/**
 * 树模型工具类
 *
 * @author fanzaiyang
 * @date 2023-07-06
 */
public class TreeModelUtil {
    /**
     * 转换
     *
     * @param data 数据
     * @return {@link TreeTableModel}
     */
    public static TreeTableModel wrapTreeTable(Serializable data,String parentIdKey) {
        TreeTableModel treeModel = new TreeTableModel(data);
        treeModel.pidField(parentIdKey);
        return treeModel;
    }

    /**
     * 包树表
     *
     * @param data 数据
     * @return {@link TreeTableModel}
     */
    public static TreeTableModel wrapTreeTable(Serializable data) {
        return wrapTreeTable(data,BreezeConstants.TREE_PARENT_ID);
    }
    /**
     * 转换
     *
     * @param dao  刀
     * @param data 数据
     */
    public static void wrapTreeTableRoute(SqlToyHelperDao dao, Serializable data,String parentIdKey) {
        TreeTableModel treeModel = wrapTreeTable(data,parentIdKey);
        dao.wrapTreeTableRoute(treeModel);
    }
}
