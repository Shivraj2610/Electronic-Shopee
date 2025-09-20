package com.shiv.electronic.store.controllers;

import com.shiv.electronic.store.custome.ApiResponseMessage;
import com.shiv.electronic.store.custome.FileUploadedResponse;
import com.shiv.electronic.store.custome.PageableResponse;
import com.shiv.electronic.store.dto.UserDto;
import com.shiv.electronic.store.service.FileService;
import com.shiv.electronic.store.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @Autowired
    private FileService fileService;


    @Value("${user.profile.image.path}")
    private String fileUploadPath;


    //Create
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto user = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }


    //Update
    @PutMapping("/update/{email}")
    public ResponseEntity<UserDto> updateUser(
            @RequestBody UserDto userDto,
            @PathVariable("email") String userId)
    {
        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUserDto);
    }


    //Delete
    @DeleteMapping("/delete/{userEmail}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userEmail") String userEmail){
        userService.deleteUser(userEmail);

        ApiResponseMessage apiResponseMessage=new ApiResponseMessage(
                "User Deleted Successfully",
                HttpStatus.OK,
                true
        );

        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }


    //Get All
    @GetMapping("/get/all")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        PageableResponse<UserDto> allUsers = userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir);

//        PagedModel<UserDto> pagedModel = new PagedModel<>(allUsers);
        return ResponseEntity.ok(allUsers);
    }


    //Get By id
    @GetMapping("/byId/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") String userId){
        UserDto userById = userService.getUserById(userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }


    //Get by Email
    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email){
        UserDto userByEmail = userService.getUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(userByEmail);
    }


    //Search User by Name
    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam("keyword") String keyword){
        List<UserDto> userDtoList = userService.searchUser(keyword);
        return new ResponseEntity<>(userDtoList,HttpStatus.OK);
    }


    //Upload File
    @PostMapping("/upload/file/{userId}")
    public ResponseEntity<FileUploadedResponse> uploadFile(
            @RequestParam("file")MultipartFile file,
            @PathVariable String userId
    ) throws IOException {

        //Upload the File on Server folder
        String fileNameWithExtension = fileService.uploadFile(file, fileUploadPath);

        //Update the File Name in Database
        UserDto userDto = userService.getUserById(userId);
        userDto.setUserProfileName(fileNameWithExtension);
        UserDto updatedUserDto = userService.updateUser(userDto, userDto.getEmail());



        //Make the FileUploadedResponse
        FileUploadedResponse fileUploadedSuccessfully = FileUploadedResponse.builder()
                .fileName(fileNameWithExtension)
                .message("File Uploaded Successfully")
                .success(true)
                .status(HttpStatus.CREATED)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(fileUploadedSuccessfully);
    }


    //Serve the Uploaded File
    @GetMapping("serve/file/{userId}")
    public void serveFile(
            @PathVariable String userId,
            HttpServletResponse httpServletResponse
    ) throws IOException {
        UserDto userDto = userService.getUserById(userId);
        String fileName = userDto.getUserProfileName();

        logger.info("User's File Name is: "+fileName);

        //In resource we get the InputStream means the Data of the File Now we want to store those data
        InputStream resource = fileService.getResource(fileUploadPath, fileName);

        //Set the Content Type
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);

        //Now Copy the resource Data to httpServletResponse.getOutputStream()
        StreamUtils.copy(resource,httpServletResponse.getOutputStream());

    }
}
