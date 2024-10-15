package com.ra.book.model.service.UploadFile;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
    String uploadFile(MultipartFile file);
}
