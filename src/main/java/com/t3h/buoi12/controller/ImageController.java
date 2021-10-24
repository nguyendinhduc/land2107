package com.t3h.buoi12.controller;

import com.t3h.buoi12.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {
    @Autowired
    private ImageService service;

    @PostMapping("/image/upload")
    public Object updateImage(
            @RequestParam MultipartFile multipartFile
    ) {
        return service.updateImage(multipartFile);
    }


    @GetMapping(
            value = "/image/{imageName}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getImage(
            @PathVariable("imageName") String imageName
    ) {
        return service.getImage(imageName);
    }


}
