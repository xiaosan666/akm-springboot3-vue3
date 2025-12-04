package com.akm.springboot3.file.utils;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * minio操作示例 https://github.com/minio/minio-java/tree/master/examples
 *
 * @author xiaojun
 *
 */
@Component
public class MinioUtils {
    /**
     * 常量
     */
    private static String endpoint;
    private static String publicEndpoint;
    private static String bucketName;
    private static String accessKey;
    private static String secretKey;
    /**
     * MinioClient对象 单例模式
     */
    private static volatile MinioClient minioClient;

    private MinioUtils() throws NoSuchAlgorithmException {
    }

    /**
     * 获取MinioClient(内网)
     *
     * @return MinioClient实例
     */
    public static MinioClient getClient() {
        if (minioClient == null) {
            minioClient = getMinioClient(endpoint);
        }
        return minioClient;
    }

    /**
     * 获取MinioClient，如果是https则忽略证书认证
     *
     * @param endpoint Minio服务器地址
     * @return MinioClient实例
     */
    private static MinioClient getMinioClient(String endpoint) {
        return MinioClient.builder()
            .endpoint(endpoint)
            .credentials(accessKey, secretKey)
            .build();
    }

    /**
     * 创建对象桶
     *
     * @param bucketName 桶名称
     * @return 如果创建成功或已存在返回true
     */
    public static boolean createBucket(String bucketName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        boolean found =
            getClient().bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            getClient().makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
        return true;
    }

    /**
     * 上传文件
     *
     * @param objectKey   对象键
     * @param stream      文件流
     * @param objectSize  文件大小
     * @param contentType 文件类型
     * @return 上传响应对象
     * @throws Exception 上传异常
     */
    public static ObjectWriteResponse putObject(String objectKey, InputStream stream, long objectSize, String contentType) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        PutObjectArgs build = PutObjectArgs.builder()
            .bucket(bucketName)
            .object(objectKey)
            .stream(stream, objectSize, -1)
            .contentType(contentType).build();
        return getClient().putObject(build);
    }

    /**
     * 获取文档对象
     *
     * @param objectKey 对象键
     * @return 文件对象响应
     * @throws Exception 获取异常
     */
    public static GetObjectResponse getObject(String objectKey) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        return getClient().getObject(
            GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectKey)
                .build());
    }

    /**
     * 获取文件下载预签名url
     *
     * @param objectKey 对象键
     * @return 预签名URL
     * @throws Exception 获取异常
     */
    public static String getDownloadPresignedObjectUrl(String objectKey) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        return getDownloadPresignedObjectUrl(objectKey, 300);
    }

    /**
     * 获取文件下载预签名url
     *
     * @param objectKey 对象键
     * @param duration  有效时长(秒)
     * @return 预签名URL
     * @throws Exception 获取异常
     */
    public static String getDownloadPresignedObjectUrl(String objectKey, int duration) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        String url = getClient().getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(objectKey)
                .expiry(duration, TimeUnit.SECONDS)
                .build());
        // 替换endpoint为publicEndpoint
        return url.replace(endpoint, publicEndpoint);
    }

    public static void removeObject(String objectKey) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        getClient().removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectKey).build());
    }

    @Value("${minio.endpoint}")
    public synchronized void setEndpoint(String val) {
        endpoint = val;
    }

    @Value("${minio.publicEndpoint}")
    public synchronized void setPublicEndpoint(String val) {
        publicEndpoint = val;
    }

    @Value("${minio.bucketName}")
    public synchronized void setBucketName(String val) {
        bucketName = val;
    }

    @Value("${minio.accessKey}")
    public synchronized void setAccessKey(String val) {
        accessKey = val;
    }

    @Value("${minio.secretKey}")
    public synchronized void setSecretKey(String val) {
        secretKey = val;
    }

}
