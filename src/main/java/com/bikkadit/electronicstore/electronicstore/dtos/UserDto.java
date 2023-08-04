package com.bikkadit.electronicstore.electronicstore.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.bikkadit.electronicstore.electronicstore.validate.imageNameValid;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {


    private String userId;


    @Size(min = 2,max = 20,message = "Invalid name !!")
    private String name;


    //@Email(message = "Invalid Email !!")
    @Pattern(regexp = "[a-z0-9]+@[a-z]+\\.[a-z]{2,3}",message = "Invalid email !!")
    @NotBlank(message = "Email should not be blank !!")
    private String email;

    @NotBlank(message = "password is required !!")
    private String password;

    @Size(min = 4,max = 6)
    private String gender;

    
    @NotBlank(message = "write something about yourself!!")
    private String about;

    @imageNameValid
    private String imageName;

}
