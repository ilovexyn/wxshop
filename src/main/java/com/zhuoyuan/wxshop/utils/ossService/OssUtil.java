package com.zhuoyuan.wxshop.utils.ossService;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * OSS工具类
 *
 * @author
 * @since 2018-07-08
 */
@Service
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

    /**
     * 通过文件路径+文件名获取key
     *
     * @Param: filePathName：文件路径+文件名
     */
    public List<String> getUrlList(String filePathName) {
        List<String> stringList = new ArrayList<>();
        Calendar calendar = null;
        OSSClient ossClient = null;
        try {
            calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 7 * 24 * 3600);
            ossClient = new OSSClient(endpoint, accessKeyId, secretAccessKey);
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
            //Delimiter 设置为 “/” 时，罗列该文件夹下的文件
           // listObjectsRequest.setDelimiter("/");
            //Prefix 设为某个文件夹名，罗列以此 Prefix 开头的文件
            listObjectsRequest.setPrefix(filePathName);
            ObjectListing listing = ossClient.listObjects(listObjectsRequest);
            // 遍历所有Object:目录下的文件
            for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
                //key：fun/like/001.avi等，即：Bucket中存储文件的路径
                String key = objectSummary.getKey();
                System.out.println("ket:"+key);
                if(!key.equals(filePathName)){
                    stringList.add( this.getURL(key).toString().replace("http","https"));
                }
            }
            return stringList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
            //throw new Exception("获取URL异常");
        } finally {
            ossClient.shutdown();
        }
    }
}
