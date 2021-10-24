package com.t3h.buoi12.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Component
public class FirebaseStoreManager {
    private static final Logger LOG = LoggerFactory.getLogger(FirebaseStoreManager.class);

    public FirebaseStoreManager() {
        try {
            InputStream inputStream = new ClassPathResource("backendland2107.json").getInputStream();
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .setDatabaseUrl("https://backendland2107.appspot.com")
                    .setStorageBucket("backendland2107.appspot.com")
                    .build();
            FirebaseApp.initializeApp(options);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        LOG.info("uploadFile info bucket: " + bucket.getName());
        LOG.debug("uploadFile debug bucket: " + bucket.getName());
        String fileName = new Date().getTime() + "-" + multipartFile.getOriginalFilename();
        //upload image len firebase
        bucket.create(fileName, multipartFile.getInputStream(), multipartFile.getContentType());
        return fileName;
    }

    public byte[] getImage(String name) {
        Bucket bucket = StorageClient.getInstance().bucket();
        return bucket.get(name).getContent();
    }
}
