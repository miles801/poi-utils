package com.michael.poi.utils;

import junit.framework.Assert;
import org.junit.Test;

public class TypeUtilsTest {

    @Test
    public void testConvert() throws Exception {
        Object value = TypeUtils.convertValueType(15172338276d, String.class);
        Assert.assertTrue(value instanceof String);
        Assert.assertEquals("15172338276", value);
    }
}