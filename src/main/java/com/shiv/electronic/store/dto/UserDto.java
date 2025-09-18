package com.shiv.electronic.store.dto;

import com.shiv.electronic.store.validation.ImageNameValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;



public class UserDto {
    private String userId;

    @Size(min = 3, max = 30)
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid Email")
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String gender;

    private String about;

//    @ImageNameValid(message = "Invalid name")
    private String userProfileName;

    public UserDto() {
    }

    public UserDto(String about, String email, String gender, String name, String password, String userProfileName) {
        this.about = about;
        this.email = email;
        this.gender = gender;
        this.name = name;
        this.password = password;
//        this.userId = userId;
        this.userProfileName = userProfileName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserProfileName() {
        return userProfileName;
    }

    public void setUserProfileName(String userProfileName) {
        this.userProfileName = userProfileName;
    }
}
