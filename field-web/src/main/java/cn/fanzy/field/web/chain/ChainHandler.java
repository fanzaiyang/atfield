package cn.fanzy.field.web.chain;

import cn.fanzy.field.core.spring.SpringUtils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 链式处理
 *
 * @author fanzaiyang
 * @date 2023/12/13
 */
@Slf4j
@RequiredArgsConstructor
public class ChainHandler {

    private final List<Handler> handlerList;

    /**
     * 添加处理程序
     *
     * @param handler 处理程序
     */
    private void addHandler(Handler handler) {
        handlerList.add(handler);
        CollUtil.sort(handlerList, Comparator.comparingInt(Handler::getOrder));
    }

    /**
     * 过程
     *
     * @param request 要求
     * @return {@link Serializable}
     */
    public Serializable process(Serializable request) {
        return process(request, "");
    }

    /**
     * 过程
     *
     * @param request   要求
     * @param groupName 组名称
     * @return {@link Serializable}
     */
    public Serializable process(Serializable request, String groupName) {
        // 依次调用每个Handler:
        final String group = StrUtil.blankToDefault(groupName, "default");
        List<Handler> processList = handlerList.stream().filter(item -> StrUtil.equalsAnyIgnoreCase(item.getGroupName(), group))
                .sorted(Comparator.comparingInt(Handler::getOrder))
                .toList();
        Serializable processResult = null;
        for (Handler handler : processList) {
            processResult = handler.process(request);
        }
        return processResult;
    }

    /**
     * 执行任意继承Handler的类的组件
     *
     * @param request 要求
     * @param clazz   拍手
     * @return {@link Serializable}
     */
    public Serializable process(Serializable request, Class<? extends Handler> clazz) {
        // 依次调用每个Handler:
        Map<String, ? extends Handler> map = SpringUtils.getBeansOfType(clazz);
        List<Handler> processList =map.values().stream().sorted(Comparator.comparingInt(Handler::getOrder))
                .collect(Collectors.toList());
        Serializable processResult = null;
        for (Handler handler : processList) {
            processResult = handler.process(request);
        }
        return processResult;
    }
}
