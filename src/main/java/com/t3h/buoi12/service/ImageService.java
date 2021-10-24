package com.t3h.buoi12.service;

import com.t3h.buoi12.firebase.FirebaseStoreManager;
import com.t3h.buoi12.model.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {
    @Autowired
    private FirebaseStoreManager firebaseStoreManager;

    public Object updateImage(MultipartFile multipartFile) {
        try {
            String imageName = firebaseStoreManager.uploadFile(multipartFile);
            BaseResponse response = new BaseResponse();
            response.setData(imageName);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            BaseResponse response = new BaseResponse();
            response.setCode(400);
            response.setMessage("FAIL");
            return response;
        }
    }

    public byte[] getImage(String imageName) {
        return firebaseStoreManager.getImage(imageName);
    }
}
