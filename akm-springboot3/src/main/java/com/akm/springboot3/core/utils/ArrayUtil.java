package com.akm.springboot3.core.utils;

import java.util.ArrayList;
import java.util.List;


public class ArrayUtil {

    private ArrayUtil() {
        throw new IllegalStateException("ArrayUtil Utility class");
    }

    public static List<String> merge(List<String> list1, List<String> list2) {
        List<String> result = new ArrayList<>();
        result.addAll(list1);
        result.addAll(list2);
        return result;
    }
}
