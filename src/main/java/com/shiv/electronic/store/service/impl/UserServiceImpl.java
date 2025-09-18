package com.shiv.electronic.store.service.impl;

import com.shiv.electronic.store.custome.PageableResponse;
import com.shiv.electronic.store.dto.UserDto;
import com.shiv.electronic.store.entities.User;
import com.shiv.electronic.store.exceptions.ResourceNotFoundException;
import com.shiv.electronic.store.helpers.Helper;
import com.shiv.electronic.store.repositories.UserRepository;
import com.shiv.electronic.store.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        //Generate unique UserId
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        //Convert dto object into entity
        User user=dtoToEntity(userDto);

        //Save the New User/ Create the User
        User savedUser = userRepository.save(user);

        //Convert entity to Dto
        UserDto savedUserDto=entityToDto(savedUser);

        return savedUserDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String email) {
        //Get the User by userId
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User Not found by Email: " + email)
        );

        //Update the fields of user if NOT Null with userDto
        if(userDto.getName()!=null){
            user.setName(userDto.getName());
        }
        if(userDto.getEmail()!=null){
            user.setEmail(userDto.getEmail());
        }
        if(userDto.getAbout()!=null){
            user.setAbout(userDto.getAbout());
        }
        if(userDto.getGender()!=null){
            user.setGender(userDto.getGender());
        }
        if(userDto.getPassword()!=null){
            user.setPassword(userDto.getPassword());
        }
        if(userDto.getUserProfileName()!=null){
            user.setUserProfileName(userDto.getUserProfileName());
        }

        User savedUser = userRepository.save(user);

        UserDto updatedUserDto = entityToDto(savedUser);

        return updatedUserDto;
    }

    @Override
    public void deleteUser(String userEmail) {

        User user = userRepository.findByEmail(userEmail).orElseThrow(
                ()->new ResourceNotFoundException("User Not found by Email: "+userEmail)
        );

        userRepository.delete(user);
    }

    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort =
                (sortDir.equalsIgnoreCase("desc"))
                        ?Sort.by(sortBy).descending()
                        :Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);

        Page<User> page = userRepository.findAll(pageable);


        PageableResponse<UserDto> pageableResponse
                = Helper.getPageableResponse(page, UserDto.class);

        return pageableResponse;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with Id: " + userId)
        );

        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String userEmail) {
        User byEmail = userRepository.findByEmail(userEmail).orElseThrow(
                ()->new ResourceNotFoundException("User Not found by Give Email: ")
        );
        return entityToDto(byEmail);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> byNameContaining = userRepository.findByNameContaining(keyword);
        //Convert the User type to UserDto
        return byNameContaining.stream().map(this::entityToDto).toList();
    }



    //Methods for Conversion of dto object and entity
    private User dtoToEntity(UserDto userDto){
//        User user = new User(
//                userDto.getAbout(),
//                userDto.getEmail(),
//                userDto.getGender(),
//                userDto.getName(),
//                userDto.getPassword(),
//                userDto.getUserProfileName()
//        );

        return mapper.map(userDto, User.class);
    }

    private UserDto entityToDto(User user){
//        UserDto userDto = new UserDto(
//                user.getAbout(),
//                user.getEmail(),
//                user.getGender(),
//                user.getName(),
//                user.getPassword(),
//                user.getUserProfileName()
//        );

        return mapper.map(user, UserDto.class);
    }
}
