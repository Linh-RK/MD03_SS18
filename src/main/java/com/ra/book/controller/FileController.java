package com.ra.book.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@Controller
public class FileController {
    @Autowired
    private ServletContext servletContext;
    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }
    @PostMapping("/upload")
    public String handlerUpload(@RequestParam("image") MultipartFile image, Model model){
        String uploadPath = "/Users/phamlinh/Desktop/book/src/main/webapp/uploads";
        System.out.println(uploadPath);
        File file = new File(uploadPath);
        if(!file.exists()){
            file.mkdir();
        }
        String fileImage = image.getOriginalFilename();
        try {
            FileCopyUtils.copy(image.getBytes(),new File(uploadPath+File.separator+fileImage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("image",fileImage);
        return "home";
    }
}

