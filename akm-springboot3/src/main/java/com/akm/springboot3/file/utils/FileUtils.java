package com.akm.springboot3.file.utils;

import cn.hutool.core.util.IdUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileUtils {

    private static final String SEPARATOR = "/";

    private FileUtils() {
        throw new IllegalStateException("FileUtils Utility class");
    }

    /**
     * 获取文件存储相对路径（objectKey）
     *
     * @param pathPrefix 路径前缀
     * @param fileType   文件类型
     */
    public static String getKey(String pathPrefix, String fileType) {
        String objectKey = pathPrefix + "/" + IdUtil.simpleUUID() + "." + fileType.toLowerCase();
        return formatKey(objectKey);
    }

    /**
     * 格式化objectKey
     *
     * @param key 文件相对路径
     */
    public static String formatKey(String key) {
        key = key.replace("\\", "/").replace("../", "/").replace("//", "/");
        if (key.startsWith(SEPARATOR)) {
            key = key.substring(1);
        }
        return key;
    }

    /**
     * 文件类型后缀是否有效
     *
     * @param fileType 文件类型
     * @return true无效；false有效
     */
    public static boolean isInvalidType(String allowType, String fileType) {
        // FileTypeUtil.getType获取.xls会返回null
        if (fileType == null) {
            return false;
        }
        if (fileType.isEmpty()) {
            return true;
        }
        String reg = "^(" + allowType + ")$";
        Pattern pattern = Pattern.compile(reg, Pattern.UNICODE_CASE);
        Matcher matcher = pattern.matcher(fileType.toLowerCase());
        return !matcher.matches();
    }


    /**
     * 判断objectKey是否有效
     *
     * @param objectKey 文件相对路径
     * @return true无效；false有效
     */
    public static boolean isInvalidKey(String allowType, String objectKey) {
        if (objectKey == null) {
            return true;
        }
        int i = objectKey.lastIndexOf('.');
        return i == -1 || isInvalidType(allowType, objectKey.substring(i + 1));
    }

}
