package com.akm.springboot3.file;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import com.akm.springboot3.core.annotation.ApiFreqLimit;
import com.akm.springboot3.core.constant.AkmConstants;
import com.akm.springboot3.core.exception.BusinessException;
import com.akm.springboot3.core.utils.ThreadContext;
import com.akm.springboot3.core.utils.ZipUtils;
import com.akm.springboot3.file.utils.FileUtils;
import com.akm.springboot3.file.utils.MinioUtils;
import io.minio.errors.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Tag(name = "file-文件服务")
@RestController
@RequestMapping("/share/public/file")
@Slf4j
public class FileApi {
    /**
     * 允许上传的文件类型后缀
     */
    @Value("${minio.allowType}")
    private String allowType;

    @Operation(summary = "文件上传", description = "只支持单文件上传；上传成功返回文件相对路径(objectKey)")
    @PostMapping("/upload")
    @ApiFreqLimit(time = 600, limit = 20)
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("pathPrefix") String pathPrefix) throws IOException, ServerException, InsufficientDataException, NoSuchAlgorithmException, InternalException, InvalidResponseException, XmlParserException, InvalidKeyException, ErrorResponseException {
        // 检查文件是否为空
        if (file == null || file.isEmpty() || file.getOriginalFilename() == null) {
            throw new BusinessException("文件上传失败，文件不存在");
        }
        String key = null;
        try (InputStream inputStream = file.getInputStream(); InputStream inputStream2 = file.getInputStream()) {
            String suffix = FileUtil.getSuffix(file.getOriginalFilename());
            // 检查文件类型
            if (FileUtils.isInvalidType(allowType, suffix) || FileUtils.isInvalidType(allowType, FileTypeUtil.getType(inputStream))) {
                throw new BusinessException("上传失败，路径无效或文件类型不允许");
            }
            // 生成随机文件名
            key = FileUtils.getKey(pathPrefix, suffix);
            MinioUtils.putObject(key, inputStream2, file.getSize(), file.getContentType());
        } catch (IOException e) {
            log.error("文件上传失败", e);
        }
        return key;
    }

    @Operation(summary = "文件下载", description = "返回文件数据流")
    @PostMapping("/getObject")
    @ApiFreqLimit(time = 600, limit = 20)
    public void getObject(@RequestBody String objectKey, HttpServletResponse response) {
        if (FileUtils.isInvalidKey(allowType, objectKey)) {
            throw new BusinessException("文件下载失败，路径无效或文件类型不允许");
        }
        try (
            OutputStream out = response.getOutputStream();
            FilterInputStream in = MinioUtils.getObject(FileUtils.formatKey(objectKey))
        ) {
            byte[] buff = new byte[1024];
            int len;
            while ((len = in.read(buff)) != -1) {
                out.write(buff, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            throw new BusinessException("下文件载失败", e);
        }
    }

    @Operation(summary = "文件下载(打包成zip)", description = "返回文件数据流")
    @PostMapping("/getObjectToZip")
    @ApiFreqLimit(time = 600, limit = 20)
    public void getObjectToZip(@RequestBody String objectKey, HttpServletResponse response) {
        if (FileUtils.isInvalidKey(allowType, objectKey)) {
            throw new BusinessException("文件下载失败，路径无效或文件类型不允许");
        }
        try (
            OutputStream out = response.getOutputStream();
            //原始文件数据
            FilterInputStream in = MinioUtils.getObject(FileUtils.formatKey(objectKey))
        ) {
            //获取文件名
            int lastFileSpe = objectKey.lastIndexOf('/');
            if (lastFileSpe != -1) {
                objectKey = objectKey.substring(lastFileSpe);
            }
            //设置文件头
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(objectKey + ".zip", StandardCharsets.UTF_8));
            //打包 原始文件 成 zip 文件
            ZipUtils.packageToZip(objectKey, in, out, ThreadContext.get(AkmConstants.USERNAME));
        } catch (Exception e) {
            log.error("objectKey = {} ,download fail", objectKey, e);
            throw new BusinessException("文件下载失败:", e);
        }
    }

    @Operation(summary = "文件预览url", description = "有效期5分钟")
    @PostMapping("/getFileUrl")
    @ApiFreqLimit(time = 600, limit = 100)
    public String getFileUrl(@RequestBody String objectKey) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InsufficientDataException, InternalException {
        if (FileUtils.isInvalidKey(allowType, objectKey)) {
            throw new BusinessException("文件下载失败，路径无效或文件类型不允许");
        }
        return MinioUtils.getDownloadPresignedObjectUrl(FileUtils.formatKey(objectKey));
    }

    @Operation(summary = "删除文件")
    @PostMapping("/delFile")
    @ApiFreqLimit
    public void removeObject(@RequestBody String objectKey) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        MinioUtils.removeObject(FileUtils.formatKey(objectKey));
    }

}
