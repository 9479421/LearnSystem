package vip.wqby.learnserver.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoRequest;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoResponse;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import vip.wqby.learnserver.utils.logUtils.qLog;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TenCloudUtils {
    public String secretId;
    public String secretKey;
    public Region region;
    public String bucketName;

    public COSClient cosClient;
    public TransferManager transferManager;

    @Value("${storage.defaultCoverImg}")
    public String defaultCoverImg;
    @Value("${storage.defaultAvatarImg}")
    public String defaultAvatarImg;
    @Value("${storage.baseUrl}")
    public String baseUrl;

    TenCloudUtils(@Value("${storage.secretId}") String secretId,
                  @Value("${storage.secretKey}") String secretKey,
                  @Value("${storage.region}") Region region,
                  @Value("${storage.bucketName}") String bucketName) {
        this.secretId = secretId;
        this.secretKey = secretKey;
        this.region = region;
        this.bucketName = bucketName;
        qLog.info("腾讯云对象存储配置", secretId, secretKey, region.getRegionName(), bucketName);
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        cosClient = new COSClient(cred, clientConfig);

        //高级接口Transfer
        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        // 传入一个 threadpool, 若不传入线程池，默认 TransferManager 中会生成一个单线程的线程池。
        transferManager = new TransferManager(cosClient, threadPool);
        // 设置高级接口的配置项
        // 分块上传阈值和分块大小分别为 5MB 和 1MB
        TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
        transferManagerConfiguration.setMultipartUploadThreshold(5 * 1024 * 1024);
        transferManagerConfiguration.setMinimumUploadPartSize(1 * 1024 * 1024);
        transferManager.setConfiguration(transferManagerConfiguration);

    }

    public void uploadFile(String filePath, String keyPath) throws Exception {
        //判断是否是网络文件
        if (filePath.startsWith("http")) {
            //高级上传
            URL url = new URL(filePath);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            //不知道就可以不设置
            //objectMetadata.setContentLength();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyPath, inputStream, objectMetadata);
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
        } else {
            throw new Exception("上传文件链接格式异常");
        }
    }
    public void uploadPicture(String filePath, String keyPath) throws Exception {
        //判断是否是网络文件
        if (filePath.startsWith("http")) {
            //高级上传
            URL url = new URL(filePath);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            //不知道就可以不设置
            //objectMetadata.setContentLength();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyPath, inputStream, objectMetadata);
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();

        } else {
            throw new Exception("上传文件链接格式异常");
        }
    }

    public void uploadFile(InputStream inputStream, String keyPath) throws Exception {
        //高级上传
        ObjectMetadata objectMetadata = new ObjectMetadata();
        //不知道就可以不设置
        //objectMetadata.setContentLength();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyPath, inputStream, objectMetadata);
        Upload upload = transferManager.upload(putObjectRequest);
        UploadResult uploadResult = upload.waitForUploadResult();
    }


    public void deleteFolder(String keyPath) throws Exception {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(bucketName);
        listObjectsRequest.setPrefix(keyPath);
        listObjectsRequest.setMaxKeys(1000);
        ObjectListing objectListing = null;
        do {
            try {
                objectListing = cosClient.listObjects(listObjectsRequest);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("删除过程出现异常");
            }
            // 这里保存列出的对象列表
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            ArrayList<DeleteObjectsRequest.KeyVersion> delObjects = new ArrayList<DeleteObjectsRequest.KeyVersion>();
            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
                delObjects.add(new DeleteObjectsRequest.KeyVersion(cosObjectSummary.getKey()));
            }
            DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName);
            deleteObjectsRequest.setKeys(delObjects);
            try {
                DeleteObjectsResult deleteObjectsResult = cosClient.deleteObjects(deleteObjectsRequest);
                List<DeleteObjectsResult.DeletedObject> deleteObjectResultArray = deleteObjectsResult.getDeletedObjects();
            } catch (Exception e) {
                throw new Exception("删除过程出现异常");
            }
            // 标记下一次开始的位置
            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());
    }

    public void deleteFile(String keyPath) throws Exception{
        try {
            cosClient.deleteObject(bucketName, keyPath);
        } catch (Exception e) {
            throw new Exception("删除文件异常");
        }
    }

    public String getVideoDuration(String keyPath){
        MediaInfoRequest request = new MediaInfoRequest();
        request.setBucketName(bucketName);
        request.getInput().setObject(keyPath);
        MediaInfoResponse response = cosClient.generateMediainfo(request);
        return response.getMediaInfo().getFormat().getDuration();
    }
}
