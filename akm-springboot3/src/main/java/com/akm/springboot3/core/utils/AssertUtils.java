package com.akm.springboot3.core.utils;

import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.exception.CodeMsg;

import java.util.Collection;

/**
 * 在方法或者任何地方对参数的有效性做校验。
 * 当不满足断言条件时，会抛出BusinessException异常
 *
 * @author xiaojun
 *
 */
public class AssertUtils {

    private static final String NOT_BLANK_MSG = "值为\"\"或null";

    private AssertUtils() {
        throw new IllegalStateException("AssertUtils Utility class");
    }

    /**
     * 判断一个布尔表达式, 若表达式为{@code false}则抛出指定错误信息的{@code BusinessException}.
     *
     * @param expression 布尔表达式
     * @param message    断言失败时的错误信息
     * @throws BusinessException
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new BusinessException(message);
        }
    }

    public static void isTrue(boolean expression, CodeMsg codeMsg) {
        if (!expression) {
            throw new BusinessException(codeMsg);
        }
    }

    /**
     * 如果对象为{@code null}, 则抛出异常
     *
     * @param object 要判断的对象
     * @throws BusinessException
     */
    public static void notNull(Object object) {
        notNull(object, "不能处理空对象");
    }

    /**
     * 如果对象为{@code null}, 则抛出异常
     *
     * @param object  要判断的对象
     * @param message 断言失败时的错误信息
     * @throws BusinessException
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new BusinessException(message);
        }
    }

    public static void notNull(Object object, CodeMsg codeMsg) {
        if (object == null) {
            throw new BusinessException(codeMsg);
        }
    }

    /**
     * 如果字符串为{@code null}、空字符串或仅包含空白字符, 则抛出异常
     *
     * @param text    要进行检查的字符串
     * @param message 断言失败时的错误信息
     * @throws BusinessException
     */
    public static void notBlank(String text, String message) {
        if (StringUtils.isBlank(text)) {
            throw new BusinessException(message);
        }
    }

    public static void notBlank(String text, CodeMsg codeMsg) {
        if (StringUtils.isBlank(text)) {
            throw new BusinessException(codeMsg);
        }
    }

    public static void notBlank(String text) {
        notBlank(text, NOT_BLANK_MSG);
    }

    /**
     * 如果集合为{@code null},或者不包含任何元素,则抛出异常
     *
     * @param collection 要进行检查的集合
     * @param message    断言失败时的错误信息
     * @throws BusinessException
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new BusinessException(message);
        }
    }

    public static void notEmpty(Collection<?> collection, CodeMsg codeMsg) {
        if (collection == null || collection.isEmpty()) {
            throw new BusinessException(codeMsg);
        }
    }

    /**
     * 如果对象不为{@code null}, 则抛出异常
     *
     * @param object  要判断的对象
     * @param message 断言失败时的错误信息
     * @throws BusinessException
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new BusinessException(message);
        }
    }

    /**
     * 如果字符串不为{@code null}、空字符串或仅包含空白字符, 则抛出异常
     *
     * @param text    要进行检查的字符串
     * @param message 断言失败时的错误信息
     * @throws BusinessException
     */
    public static void isBlank(String text, String message) {
        if (StringUtils.isNotBlank(text)) {
            throw new BusinessException(message);
        }
    }
}
