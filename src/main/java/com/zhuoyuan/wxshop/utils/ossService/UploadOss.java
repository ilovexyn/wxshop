package com.zhuoyuan.wxshop.utils.ossService;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;

/**
 * 上传至OSS文件服务器
 *
 * @author
 * @since 2018-07-08
 */
@Service
public class UploadOss {
    @Value("${oss.bucket}")
    private String bucketName;
    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.secretAccessKey}")
    private String secretAccessKey;

    /**
     * 上传生成的excel
     *
     * @Param: content:上传内容；fileName：上传文件名（上传到oss的文件名）
     */
    public void uploadHSSFWorkbookOSS(HSSFWorkbook hssfWorkbook, String fileName) {
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            hssfWorkbook.write(bout);
            ByteArrayInputStream Workbookinput = new ByteArrayInputStream(bout.toByteArray());
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, fileName, Workbookinput);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();// 关闭OSSClient。
        }
    }

    /**
     * 上传生成的excel(认领操作上传至oss-bj-bdy-private)
     *
     * @param hssfWorkbook
     * @param fileName
     * @param bucketName
     */
    public void uploadHSSFWorkbookOSS(HSSFWorkbook hssfWorkbook, String fileName, String bucketName) {
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            hssfWorkbook.write(bout);
            ByteArrayInputStream Workbookinput = new ByteArrayInputStream(bout.toByteArray());
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, fileName, Workbookinput);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();// 关闭OSSClient。
        }
    }

    /**
     * 上传输入流
     *
     * @Param: content:上传内容；fileName：上传文件名（上传到oss的文件名）
     */
    public void uploadInputStreamOSS(InputStream inputStream, String fileName) {
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, fileName, inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();// 关闭OSSClient。
        }
    }

    /**
     * 上传输入流
     *
     * @Param: content:上传内容；fileName：上传文件名（上传到oss的文件名）
     */
    public void uploadInputStreamOSS(InputStream inputStream, String fileName,String bucketName) {
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, fileName, inputStream);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();// 关闭OSSClient。
        }
    }

    /**
     * 上传字符串
     *
     * @Param: content:上传内容；fileName：上传文件名（上传到oss的文件名）
     */
    public void uploadStringOSS(String content, String fileName) {
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
            ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(content.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();// 关闭OSSClient。
        }
    }

    /**
     * 上传Byte数组
     *
     * @Param: byte[] content；fileName:文件名（上传到oss的文件名）
     */
    public void uploadByteOSS(byte[] content, String fileName) {
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
            ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(content));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();// 关闭OSSClient。
        }
    }

    /**
     * 上传网络流。
     *
     * @Param: url：https://www.aliyun.com/"；fileName:文件名（上传到oss的文件名）
     */
    public void uploadURLOSS(String url, String fileName) {
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
            InputStream inputStream = new URL(url).openStream();// 上传网络流。
            ossClient.putObject(bucketName, fileName, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();// 关闭OSSClient。
        }
    }

    /**
     * 上传文件流
     *
     * @Param: filePathName：文件路径+文件名；fileName:文件名（上传到oss的文件名）
     */
    public void uploadInputStreamOSS(String filePathName, String fileName) {
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
            InputStream inputStream = new FileInputStream(new File(filePathName));// 上传文件流。
            ossClient.putObject(bucketName, fileName, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();// 关闭OSSClient。
        }
    }

    /**
     * 上传本地文件
     *
     * @Param: filePathName：文件路径+文件名；fileName:文件名（上传到oss的文件名）
     */
    public void uploadLoaclFileOSS(String filePathName, String fileName) {
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
            ossClient.putObject(bucketName, fileName, new File(filePathName)); // 上传文件。
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();// 关闭OSSClient。
        }
    }
}
