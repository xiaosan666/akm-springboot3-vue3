package com.akm.springboot3.file.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class FileUploadUtils {

    private static final String MULTIPART_FORM_DATA = "multipart/form-data";

    // 支持的文件上传方法
    private static final String POST_METHOD = "POST";
    private static final String PUT_METHOD = "PUT";
    private static final String PATCH_METHOD = "PATCH";

    /**
     * 判断请求是否为文件上传请求
     */
    public static boolean isFileUploadRequest(HttpServletRequest request) {
        // 1. 检查HTTP方法
        if (!isUploadMethod(request.getMethod())) {
            return false;
        }

        // 2. 检查Content-Type
        String contentType = request.getContentType();
        if (!StringUtils.hasText(contentType)) {
            return false;
        }

        // 3. 检查是否为multipart/form-data
        return contentType.toLowerCase().startsWith(MULTIPART_FORM_DATA);
    }

    /**
     * 检查HTTP方法是否支持文件上传
     */
    public static boolean isUploadMethod(String method) {
        return POST_METHOD.equalsIgnoreCase(method) ||
            PUT_METHOD.equalsIgnoreCase(method) ||
            PATCH_METHOD.equalsIgnoreCase(method);
    }

    /**
     * 判断是否为有效的文件上传请求（包含安全检查）
     */
    public static boolean isValidFileUploadRequest(HttpServletRequest request) {
        if (!isFileUploadRequest(request)) {
            return false;
        }

        // 安全检查
        return hasValidContentLength(request) &&
            hasValidContentType(request);
    }

    /**
     * 检查Content-Length是否有效
     */
    private static boolean hasValidContentLength(HttpServletRequest request) {
        long contentLength = request.getContentLengthLong();

        // 检查是否有Content-Length头
        if (contentLength < 0) {
            return false;
        }

        // 可选：检查文件大小限制（例如100MB）
        long maxFileSize = 100 * 1024 * 1024; // 100MB
        return contentLength <= maxFileSize;
    }

    /**
     * 检查Content-Type是否有效
     */
    private static boolean hasValidContentType(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (!StringUtils.hasText(contentType)) {
            return false;
        }

        // 检查是否包含boundary参数
        return contentType.toLowerCase().contains("boundary=");
    }

    /**
     * 获取multipart请求的boundary
     */
    public static String getBoundary(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (!StringUtils.hasText(contentType)) {
            return null;
        }

        String[] parts = contentType.split(";");
        for (String part : parts) {
            part = part.trim();
            if (part.startsWith("boundary=")) {
                return part.substring("boundary=".length()).trim();
            }
        }

        return null;
    }

    /**
     * 获取详细的文件上传信息
     */
    public static FileUploadInfo getFileUploadInfo(HttpServletRequest request) {
        FileUploadInfo info = new FileUploadInfo();
        info.setFileUploadRequest(isFileUploadRequest(request));
        info.setMethod(request.getMethod());
        info.setContentType(request.getContentType());
        info.setContentLength(request.getContentLengthLong());
        info.setBoundary(getBoundary(request));
        info.setCharacterEncoding(request.getCharacterEncoding());

        return info;
    }
}
