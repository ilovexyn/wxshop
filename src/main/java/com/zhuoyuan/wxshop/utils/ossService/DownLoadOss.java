package com.zhuoyuan.wxshop.utils.ossService;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DownloadFileRequest;
import com.aliyun.oss.model.DownloadFileResult;
import com.aliyun.oss.model.OSSObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * OSS下载和读取文件
 *
 * @author
 * @since 2018-07-08
 */
@Component
public class DownLoadOss {

    @Value("${oss.bucket}")
    private String bucketName;
    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.secretAccessKey}")
    private String secretAccessKey;

    /**
     * 下载
     * key：下载的文件名;filePath本地路径
     *
     * @Param: content:上传内容；fileName：上传文件名（上传到oss的文件名）
     */
    public void downOSSFile(String key, String filePath)throws Throwable {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
        try {
            // 下载请求，10个任务并发下载，启动断点续传
            DownloadFileRequest downloadFileRequest = new DownloadFileRequest(bucketName, key);
            downloadFileRequest.setDownloadFile(filePath);
            downloadFileRequest.setTaskNum(10);
            downloadFileRequest.setEnableCheckpoint(true);
            // 下载文件
            DownloadFileResult downloadRes = ossClient.downloadFile(downloadFileRequest);
            // 下载成功时，会返回文件的元信息
            downloadRes.getObjectMetadata();
        } catch (Exception e) {
            throw new Exception("断点续传下载异常");
        } finally {
            // 关闭client
            ossClient.shutdown();
        }
    }

    /**
     * 通过文件名返回输入流
     *
     * @Param: key：文件名
     */
   /* public InputStream readOSSFile(String key) {
        try {
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
            InputStream objectContent = ossClient.getObject(bucketName, key).getObjectContent();
            //ossClient.shutdown();
            return objectContent;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    /**
     * 通过文件名返回输入流
     *
     * @Param: key：文件名
     */
    public void getInputStream(String key, OSSStreamCallBack callBack) throws Exception {
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        InputStream inputStream = null;
        try {
            OSSObject ossObject = ossClient.getObject(bucketName, key);
            inputStream = ossObject.getObjectContent();
            callBack.callBack(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("oss callback exception");
        } finally {
            inputStream.close();
            ossClient.shutdown();
        }
    }
}
