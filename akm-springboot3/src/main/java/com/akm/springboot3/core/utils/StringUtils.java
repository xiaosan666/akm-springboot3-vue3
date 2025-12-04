package com.akm.springboot3.core.utils;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 字符串操作工具类
 *
 * @author xiaojun
 *
 */
public class StringUtils {

    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    private StringUtils() {
        throw new IllegalStateException("StringUtils Utility class");
    }

    public static boolean isEmpty(final String str) {
        return (str == null) || (str.isEmpty());
    }

    public static boolean isNotEmpty(final String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(final String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final String str) {
        return !isBlank(str);
    }

    /**
     * 判断多个字符串全部是否为空
     */
    public static boolean isAllEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String str : strings) {
            if (isNotEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个字符串其中任意一个是否为空
     */
    public static boolean isHasEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String str : strings) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 比较字符相等
     */
    public static boolean equals(String value, String equals) {
        if (isAllEmpty(value, equals)) {
            return true;
        }
        return value.equals(equals);
    }

    /**
     * 比较字符串不相等
     */
    public static boolean isNotEquals(String value, String equals) {
        return !equals(value, equals);
    }

    /**
     * 转换为字节数组
     */
    public static String toString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 转换为字节数组
     */
    public static byte[] getBytes(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 驼峰命名转下划线命名, 并将结果转换为大写/小写
     *
     * @param fieldName   字段名
     * @param toUpperCase true:转大写，false 转小写
     * @return 转换后的下划线分隔字符串
     */
    public static String hump2underline(String fieldName, boolean toUpperCase) {
        if (StringUtils.isBlank(fieldName)) {
            return fieldName;
        }
        char[] arr = fieldName.toCharArray();
        StringBuilder toReturn = new StringBuilder();
        for (char curChar : arr) {
            if (Character.isUpperCase(curChar)) {
                toReturn.append("_").append(curChar);
            } else {
                toReturn.append(curChar);
            }
        }
        if (toUpperCase) {
            return toReturn.toString().toUpperCase();
        } else {
            return toReturn.toString().toLowerCase();
        }
    }

    /**
     * 下划线命名转驼峰命名
     *
     * @param fieldName 字段名
     * @return 转换后的驼峰命名字符串
     */
    public static String underline2hump(String fieldName) {
        if (StringUtils.isBlank(fieldName)) {
            return fieldName;
        }
        char[] arr = fieldName.toCharArray();
        StringBuilder toReturn = new StringBuilder();
        boolean toUpperCase = false;
        for (char curChar : arr) {
            if (curChar == '_') {
                toUpperCase = true;
            } else {
                if (toUpperCase) {
                    toReturn.append(Character.toUpperCase(curChar));
                    toUpperCase = false;
                } else {
                    toReturn.append(Character.toLowerCase(curChar));
                }
            }
        }
        return toReturn.toString();
    }

    /**
     * 判断uri是否符合patternList中的任何一个
     *
     * @param patternList 路径模式列表
     * @param uri         URI路径
     * @return 如果URI匹配模式列表中的任意一个则返回true，否则返回false
     */
    public static boolean pathMatch(List<String> patternList, String uri) {
        return patternList != null && !patternList.isEmpty()
            && patternList.stream().anyMatch(urlPattern -> PATH_MATCHER.match(urlPattern, uri));
    }

}

