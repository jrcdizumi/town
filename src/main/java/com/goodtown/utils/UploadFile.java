package com.goodtown.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
public class UploadFile {
    @Autowired
    COSClient cosClient;

    @Value("${tengxun.cos.bucketName}")
    private String bucketName;

    @Value("${tengxun.cos.url}")
    private String url;

    @Value("${tengxun.cos.path}")
    private String path;

    public Result<String> upload(MultipartFile file) throws IOException {
//        log.info("上传文件",file);
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        //将文件上传的腾讯云
        String fileName = UUID.randomUUID().toString() + extension;
//        File tempFile = File.createTempFile("temp", extension);
//        file.transferTo(tempFile);
        InputStream inputStream = file.getInputStream();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path+fileName, inputStream, null);
        PutObjectResult putObjectResult;
        putObjectResult = cosClient.putObject(putObjectRequest);
        if(putObjectResult!=null)
        {
            return Result.ok(url + path+fileName);
        }
        else {
            return Result.build("上传失败",404,"上传失败");
        }
    }
}
