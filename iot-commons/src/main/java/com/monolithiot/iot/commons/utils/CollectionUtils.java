package com.monolithiot.iot.commons.utils;

import lombok.val;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by 郭文梁 2019/7/25 18:45
 * CollectionUtils
 * 集合相关工具类
 *
 * @author 郭文梁
 * @data 2019/7/25 18:45
 */
public class CollectionUtils {
    /**
     * List 去重 保持原顺序
     *
     * @param list list
     * @param <T>  反省
     * @return 去重结果
     */
    public static <T> List<T> deDuplication(List<T> list) {
        val tmpSet = new HashSet<>();
        return list.stream().filter(tmpSet::add).collect(Collectors.toList());
    }
}
