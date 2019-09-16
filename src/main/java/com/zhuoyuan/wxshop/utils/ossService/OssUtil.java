package com.zhuoyuan.wxshop.utils.ossService;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Calendar;

/**
 * OSS工具类
 *
 * @author houQiang
 * @since 2018-07-08
 */
@Component
public class OssUtil {
    @Value("${oss.bucket}")
    private String bucketName ;
    @Value("${oss.endpoint}")
    private String endpoint ;
    @Value("${oss.accessKeyId}")
    private String accessKeyId ;
    @Value("${oss.secretAccessKey}")
    private String secretAccessKey ;

    /**
     * 通过文件路径+文件名获取key
     *
     * @Param: filePathName：文件路径+文件名
     */
    public URL getURL(String filePathName) {
        Calendar calendar = null;
        OSSClient ossClient = null;
        try {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 7 * 24 * 3600);
            ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
            URL url = ossClient.generatePresignedUrl(bucketName, filePathName, calendar.getTime());
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
            //throw new Exception("获取URL异常");
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 通过文件路径+文件名获取key
     *
     * @Param: filePathName：文件路径+文件名
     */
    public URL getURL(String filePathName,String bucketName) {
        Calendar calendar = null;
        OSSClient ossClient = null;
        try {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 7 * 24 * 3600);
            ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
            URL url = ossClient.generatePresignedUrl(bucketName, filePathName, calendar.getTime());
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
            //throw new Exception("获取URL异常");
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 创建文件夹
     *
     * @Param: filePath：文件路径("dir1/dir2/dir3/")
     */
    public void createFolderOSS(String filePath) {
        final String keySuffixWithSlash;
        OSSClient ossClient = null;
        try {
            keySuffixWithSlash = filePath;
            ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();// 关闭OSSClient。
        }
    }

    public boolean exists(String key) throws Exception {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
        try {
            return ossClient.doesObjectExist(bucketName, key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("文件查询失败");
        } finally {
            ossClient.shutdown();
        }
    }
}
