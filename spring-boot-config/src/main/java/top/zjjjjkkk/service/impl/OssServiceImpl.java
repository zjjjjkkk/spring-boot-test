package top.zjjjjkkk.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.zjjjjkkk.config.OssConfig;
import top.zjjjjkkk.service.OssService;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;

@Service
@Slf4j
public class OssServiceImpl implements OssService {
    @Resource
    private OssConfig ossConfig;

    @Override
    public String upload(MultipartFile file) {
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID() + suffix;
            log.info("新文件名：{}", newFileName);
            String endpoint = ossConfig.getEndpoint();
            String bucket = ossConfig.getBucket();
            String accessKey = ossConfig.getAccessKey();
            String secretKey = ossConfig.getSecretKey();
            String dir = ossConfig.getDir();
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKey, secretKey);
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType("image/jpg");
            String uploadPath = dir + newFileName;
            InputStream inputStream;
            try {
                inputStream = file.getInputStream();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ossClient.putObject(bucket, uploadPath, inputStream, meta);
            ossClient.shutdown();
            return "https://" + bucket + "." + endpoint + "/" + uploadPath;
        }
        return"上传失败";
    }
}