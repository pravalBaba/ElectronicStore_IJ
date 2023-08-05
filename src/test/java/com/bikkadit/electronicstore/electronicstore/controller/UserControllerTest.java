package com.bikkadit.electronicstore.electronicstore.controller;


import com.bikkadit.electronicstore.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.electronicstore.entities.User;
import com.bikkadit.electronicstore.electronicstore.service.UserServiceI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest


   @AutoConfigureMockMvc

   public class UserControllerTest {

    @MockBean
    private UserServiceI userService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    private User user;

    @BeforeEach
    public void init(){
        user = User.builder()
                .name("Varun")
                .email("varun@gmail.com")
                .password("varun645")
                .gender("Male")
                .about("This is testing for create method")
                .imageName("varun.png")
                .build();

    }

    // create controller test
    @Test
    public void createUserTest() throws Exception {

        // users +POST+ user data as json
        // data as json+status created

        UserDto dto = mapper.map(user, UserDto.class);
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(dto);
        // actual request for url
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());
    }

    // update controller test
    @Test
    public void updateUserTest() throws Exception {
        //  /user/{userId} + PUT request + json
        String userId="123";
        UserDto dto = this.mapper.map(user, UserDto.class);
        Mockito.when(userService.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/users/"+userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    // delete user test
    @Test
    public void deleteUserTest() throws Exception {
        String userId="124";
        userService.deleteUser(userId);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUserTest() throws Exception {
        UserDto object1 = UserDto.builder().name("praval")
                .email("pps@gmail.com")
                .password("praval")
                .about("developer")
                .gender("male")
                .build();
        UserDto object2 = UserDto.builder().name("akash")
                .email("akash@gmail.com")
                .password("akash")
                .about("developer")
                .gender("male")
                .build();
        UserDto object3 = UserDto.builder().name("vivek")
                .email("vivek@gmail.com")
                .password("vivek")
                .about("developer")
                .gender("male")
                .build();
        PageableResponse<UserDto> pageableResponse =new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(object1,object2,object3));
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);

        Mockito.when(userService.getAllUser(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // get user by id test
    @Test
    public void getUserByIdTest() throws Exception {
        String userId="4445461dw";
        UserDto userDto = mapper.map(user, UserDto.class);
        Mockito.when(userService.getUserById(userId)).thenReturn(userDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/"+userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // get user by email
    @Test
    public void getUserByEmail() throws Exception {
        String emailId="varun@gmail.com";
        UserDto dto = mapper.map(user, UserDto.class);
        Mockito.when(userService.getUserByEmail(emailId)).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/email/"+emailId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    private String convertObjectToJsonString(Object user) {
        try{
            return new ObjectMapper().writeValueAsString(user);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
