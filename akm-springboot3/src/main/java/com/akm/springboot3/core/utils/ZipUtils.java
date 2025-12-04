package com.akm.springboot3.core.utils;

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.io.outputstream.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 * 打包 originFile 成 zip 文件 工具类，支持设置解压密码
 */
@Slf4j
public class ZipUtils {

    /**
     * 打包 originFile 到 zipOutputStream 中
     *
     * @param originFile 原始文件名
     * @param zipFile    zip文件
     * @throws IOException 打包期间抛出的异常
     */
    public static void packageToZip(File originFile, File zipFile) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFile)) {
            packageToZip(originFile, fileOutputStream, null);
        } catch (IOException e) {
            log.error("originFile.path= {},zipFile.path = {},package to zip fail", originFile.getPath(), zipFile.getPath(), e);
        }
    }

    /**
     * 打包 originFile 到 zipOutputStream 中
     *
     * @param originFile 原始文件名
     * @param zipFile    zip文件
     * @param password   zip解压密码，可为null
     * @throws IOException 打包期间抛出的异常
     */
    public static void packageToZip(File originFile, File zipFile, String password) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFile)) {
            packageToZip(originFile, fileOutputStream, password);
        } catch (IOException e) {
            log.error("originFile.path= {},zipFile.path = {},package to zip fail", originFile.getPath(), zipFile.getPath(), e);
            throw e;
        }
    }

    /**
     * 打包 originFile 到 zipOutputStream 中
     *
     * @param originFile      原始文件名
     * @param zipOutputStream zip文件输出流，注意这里不用{@link ZipOutputStream},仅仅只需要普通的输出流，仅仅用于确定最终的zip文件的输出流向。
     * @param password        zip解压密码，可为null
     * @throws IOException 打包期间抛出的异常
     */
    public static void packageToZip(File originFile, OutputStream zipOutputStream, String password) throws IOException {
        try {
            packageToZip(originFile.getName(), new FileInputStream(originFile), zipOutputStream, password);
        } catch (IOException e) {
            log.error("originFile.path= {},package to zip fail", originFile.getPath(), e);
            throw e;
        }
    }

    /**
     * 打包 originFile 到 zipOutputStream 中
     *
     * @param originFile      原始文件名
     * @param zipOutputStream zip文件输出流，注意这里不用{@link ZipOutputStream},仅仅只需要普通的输出流，仅仅用于确定最终的zip文件的输出流向。
     * @throws IOException 打包期间抛出的异常
     */
    public static void packageToZip(File originFile, OutputStream zipOutputStream) throws IOException {
        try {
            packageToZip(originFile.getName(), new FileInputStream(originFile), zipOutputStream, null);
        } catch (IOException e) {
            log.error("originFile.path= {},package to zip fail", originFile.getPath(), e);
            throw e;
        }
    }

    /**
     * 打包 originFile 到 zipOutputStream 中
     *
     * @param originFileName        原始文件名，作用于zip里的文件名
     * @param originFileInputStream 原始文件输入流
     * @param zipOutputStream       zip文件输出流，注意这里不用{@link ZipOutputStream},仅仅只需要普通的输出流，仅仅用于确定最终的zip文件的输出流向。
     * @throws IOException 打包期间抛出的异常
     */
    public static void packageToZip(String originFileName, InputStream originFileInputStream, OutputStream zipOutputStream) throws IOException {
        packageToZip(originFileName, originFileInputStream, zipOutputStream, null);
    }

    /**
     * 打包 originFile 到 zipOutputStream 中
     *
     * @param originFileName        原始文件名，作用于zip里的文件名
     * @param originFileInputStream 原始文件输入流
     * @param zipFileOutputStream   zip文件输出流，注意这里不用{@link ZipOutputStream},仅仅只需要普通的输出流，仅仅用于确定最终的zip文件的输出流向。
     * @param password              zip解压密码，可为null
     * @throws IOException 打包期间抛出的异常
     */
    public static void packageToZip(String originFileName, InputStream originFileInputStream, OutputStream zipFileOutputStream, String password) throws IOException {
        ZipOutputStream zos = null;
        try {
            //空密码标记
            boolean emptyPasswordFlag = org.springframework.util.StringUtils.hasText(password);
            //设置Zip输出流
            zos = emptyPasswordFlag ? new ZipOutputStream(zipFileOutputStream) : new ZipOutputStream(zipFileOutputStream, password.toCharArray());
            //zip里面的文件的参数设置
            ZipParameters parameters = getZipParameters(originFileName, emptyPasswordFlag);
            zos.putNextEntry(parameters);
            //输出到 zipOutputStream
            byte[] buffer = new byte[1024 * 4];
            int n;
            while (-1 != (n = originFileInputStream.read(buffer))) {
                zos.write(buffer, 0, n);
            }
            zos.closeEntry();
        } catch (IOException e) {
            log.error("originFileName= {},package to zip fail", originFileName, e);
            throw e;
        } finally {
            if (originFileInputStream != null) {
                try {
                    originFileInputStream.close();
                } catch (IOException e) {
                    log.error("originFileName= {},originFileInputStream close fail", originFileName, e);
                }
            }
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    log.error("originFileName= {},zipOutputStream close fail", originFileName, e);
                }
            }
        }
    }

    @NotNull
    private static ZipParameters getZipParameters(String originFileName, boolean emptyPasswordFlag) {
        ZipParameters parameters = new ZipParameters();
        parameters.setFileNameInZip(originFileName);
        if (!emptyPasswordFlag) {
            parameters.setCompressionMethod(CompressionMethod.DEFLATE);
            parameters.setCompressionLevel(CompressionLevel.NORMAL);
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(EncryptionMethod.AES);
            parameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
        }
        return parameters;
    }

    /**
     * 从内存文件创建磁盘上的Zip文件: https://oomake.com/question/5919170
     *
     * @param args
     * @throws IOException
     */
    // public static void main(String[] args) throws IOException {
    //     packageToZip(new File("D:\\Project\\nsy\\nsy-akm-springcloud-tenant\\tmp\\18612341234_8998fde19aa04e6691d4fc327fedd879.xls"),
    //         new File("D:\\Project\\nsy\\nsy-akm-springcloud-tenant\\tmp\\18612341234.zip"),
    //         "123456");
    // }
}
