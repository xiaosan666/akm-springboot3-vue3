package com.akm.springboot3.core.utils;

import cn.hutool.extra.pinyin.PinyinUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 密码复杂度、安全性检验
 */
public class PasswordComplexityValidateUtils {

    /**
     * 密码中包含至少一个特殊字符（如！、@、#、等）
     */
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#%^&+=]).{8,16}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    private static final Set<String> SENSITIVE_WORDS = new HashSet<>();

    static {
        SENSITIVE_WORDS.add("password");
        SENSITIVE_WORDS.add("123");
        SENSITIVE_WORDS.add("qwerty");
        SENSITIVE_WORDS.add("nfdw");
        SENSITIVE_WORDS.add("csg");
        SENSITIVE_WORDS.add("pwd");
        SENSITIVE_WORDS.add("dsj");
        SENSITIVE_WORDS.add("admin");
        SENSITIVE_WORDS.add("dwglpt");
        SENSITIVE_WORDS.add("dljypt");
        SENSITIVE_WORDS.add("zhangsan");
        SENSITIVE_WORDS.add("lisi");
        // 添加更多常见密码...
    }

    /**
     * 校验复杂度
     *
     * @param password 密码
     * @return 如果密码符合复杂度要求返回true，否则返回false
     */
    public static boolean validateComplexity(String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * 校验是否包含敏感字符
     *
     * @param password 密码
     * @return 如果不包含敏感字符返回true，否则返回false
     */
    public static boolean validateSensitive(String password) {
        // 将密码和黑名单中的密码都转换为小写形式
        String lowercasePassword = password.toLowerCase();
        for (String sensitiveWord : SENSITIVE_WORDS) {
            if (lowercasePassword.contains(sensitiveWord.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验是否包含用户名拼音
     *
     * @param password 密码
     * @param name     用户名
     * @return 如果不包含用户名拼音返回true，否则返回false
     */
    public static boolean validateContainsNamePinyin(String password, String name) {
        String namePinyin = PinyinUtil.getPinyin(name, "");
        String namePinyinFirstLetter = PinyinUtil.getFirstLetter(name, "");
        String lowercasePassword = password.toLowerCase();
        return !lowercasePassword.contains(namePinyin) && !lowercasePassword.contains(namePinyinFirstLetter);
    }

    /**
     * 校验是否包含关键字符
     *
     * @param password 密码
     * @param keyWords 关键字列表
     * @return 如果不包含关键字返回true，否则返回false
     */
    public static boolean validateContainsKeyWord(String password, String... keyWords) {
        // 将密码和黑名单中的密码都转换为小写形式
        String lowercasePassword = password.toLowerCase();
        for (String keyWord : keyWords) {
            if (lowercasePassword.contains(keyWord.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}
