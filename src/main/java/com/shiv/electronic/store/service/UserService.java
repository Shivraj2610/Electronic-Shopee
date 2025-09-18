package com.shiv.electronic.store.service;

import com.shiv.electronic.store.custome.PageableResponse;
import com.shiv.electronic.store.dto.UserDto;
import org.springframework.data.domain.Page;


import java.util.List;

public interface UserService {
    //Create User
    UserDto createUser(UserDto userDto);

    //Update User
    UserDto updateUser(UserDto userDto, String email);

    //Delete User
    void deleteUser(String userEmail);

    //Get All Users
    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    //Get Single User By id
    UserDto getUserById(String userId);

    //Get Single User By email
    UserDto getUserByEmail(String userEmail);

    //Search User
    List<UserDto> searchUser(String keyword);
}
