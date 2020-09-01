package com.wang.allservice.service.oss;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
