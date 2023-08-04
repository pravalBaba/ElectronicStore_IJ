package com.bikkadit.electronicstore.electronicstore.service;

import java.util.List;

import com.bikkadit.electronicstore.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.electronicstore.dtos.UserDto;

public interface UserServiceI {
    //create
    UserDto createUser(UserDto userDto);

    //update

    UserDto updateUser(UserDto userDto,String userId);


     //delete
    void deleteUser(String userId);

    //get all Users
    PageableResponse<UserDto> getAllUser(int pageNumber,int pageSize,String sortBy,String sortDir);

    //get user by id

    UserDto getUserById(String userId);


    //get user by email
    UserDto getUserByEmail(String email);
    //search

   // List<UserDto> searchUser(String keyword);



}