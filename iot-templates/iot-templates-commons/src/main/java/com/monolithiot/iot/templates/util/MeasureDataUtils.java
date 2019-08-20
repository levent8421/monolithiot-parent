package com.monolithiot.iot.templates.util;

import com.monolithiot.iot.commons.exception.BadRequestException;
import com.monolithiot.iot.templates.entity.AbstractTemplate;
import lombok.val;

import static com.monolithiot.iot.commons.utils.ParamChecker.notEmpty;
import static com.monolithiot.iot.commons.utils.ParamChecker.notNull;

/**
 * Create by 郭文梁 2019/8/20 16:22
 * MeasureDataUtils
 * 测量数据相关工具类
 *
 * @author 郭文梁
 * @data 2019/8/20 16:22
 */
public class MeasureDataUtils {
    /**
     * 检查并拷贝创建参数
     *
     * @param param  参数
     * @param target 拷贝目标
     */
    public static void checkCreateParam(AbstractTemplate param, AbstractTemplate target) {
        val ex = BadRequestException.class;
        notNull(param, ex, "参数未传！");
        notEmpty(param.getTitle(), ex, "标题必填！");
        notEmpty(param.getFields(), ex, "请至少指定一个测量项！");

        for (val field : param.getFields()) {
            notNull(field, ex, "测量项不能为空！");
            notEmpty(field.getName(), ex, "测量项名称必填！");
            notEmpty(field.getDisplayText(), ex, "[" + field.getName() + "]的显示名称必填！");
        }

        target.setTitle(param.getTitle());
        target.setDescription(param.getDescription());
        target.setImages(param.getImages());
        target.setIndustry(param.getIndustry());
        target.setFields(param.getFields());
        target.setAnonymous(param.getAnonymous() == null ? false : param.getAnonymous());
    }
}
