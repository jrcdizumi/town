package com.goodtown.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class TCCOSConfig {

    @Value("${tengxun.cos.SecretId}")
    private String secretId;
    @Value("${tengxun.cos.SecretKey}")
    private String secretKey;

    @Value("${tengxun.cos.region}")
    private String region;

    @Value("${tengxun.cos.bucketName}")
    private String bucketName;


    @Bean
    // 创建 COSClient 实例
    public COSClient cosClient(){
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(this.secretId, this.secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照
        Region region = new Region(this.region);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

    public void shutdownTransferManager(TransferManager transferManager) {
        transferManager.shutdownNow(false);
    }
}