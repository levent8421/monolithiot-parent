package com.monolithiot.iot.templates.repository.es.mapper;

import lombok.val;
import lombok.var;
import org.junit.Test;

public class HighlightResultMapperTest {
    @Test
    public void testSubstring() {
        var fieldName = "abc.pinyin";
        val suffix = ".pinyin";
        System.out.println(fieldName.substring(0, fieldName.length() - suffix.length()));
    }
}
