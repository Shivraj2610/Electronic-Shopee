package com.shiv.electronic.store.service.impl;

import com.shiv.electronic.store.exceptions.BadApiRequestException;
import com.shiv.electronic.store.service.FileService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        String originalFilename = file.getOriginalFilename();
        logger.info("Original File Name: {}", originalFilename);

        //Create the Unique File name to store in Database
        String uniqueFileName = UUID.randomUUID().toString();

        //Get the Extension of Original File name
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        //Unique File Name with Extension
        String fileNameWithExtension = uniqueFileName+extension;

        //Full Path with File Name
        String fullPathWithFileName = path+ File.separator +fileNameWithExtension;

        //Allow only some extension
        if(extension.equalsIgnoreCase(".png")
                || extension.equalsIgnoreCase(".jpg")
                || extension.equalsIgnoreCase(".jpeg")
        ){
            //Save the FileName into Database and the File store in server's Folder
            File folder = new File(path);

            //If folder not Exist then Create folder
            if(!folder.exists()){
                //Create The Folder
                folder.mkdirs();
            }

            //Now Upload the File
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));

            return fileNameWithExtension;

        }else{
                //Throw the BadApiRequestException
                throw new BadApiRequestException("File with this "+ extension+" extension not allowed");
        }


    }

    //Serve the Image or File
    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

        //Get the Full Path of File
        String fullPath = path+File.separator+fileName;

        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }


}
