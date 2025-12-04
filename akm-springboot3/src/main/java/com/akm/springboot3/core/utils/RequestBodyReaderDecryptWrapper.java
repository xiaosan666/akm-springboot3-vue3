package com.akm.springboot3.core.utils;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 主要用于获取RequestBody参数并支持解密
 * 参考：https://www.jianshu.com/p/69c6fba08c92
 *
 * @author xiaojun
 */
@Getter
public class RequestBodyReaderDecryptWrapper extends HttpServletRequestWrapper {
    private static final Logger log = LoggerFactory.getLogger(RequestBodyReaderDecryptWrapper.class);
    private final String body;

    public RequestBodyReaderDecryptWrapper(HttpServletRequest request, boolean isDecrypt, String aesKey) {
        super(request);
        String bodyStr = getBodyString(request);
        if (isDecrypt) {
            bodyStr = EncryptUtils.aesDecrypt(bodyStr, aesKey);
        }
        this.body = bodyStr;
    }

    private String getBodyString(final ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        try (
            InputStream inputStream = cloneInputStream(request.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("getBodyString", e);
        }
        return sb.toString();
    }


    private InputStream cloneInputStream(ServletInputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            log.error("cloneInputStream", e);
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));

        return new ServletInputStream() {

            @Override
            public int read() {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), StandardCharsets.UTF_8));
    }

}
