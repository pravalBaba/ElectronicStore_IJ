package com.bikkadit.electronicstore.electronicstore.service;

import com.bikkadit.electronicstore.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.electronicstore.entities.User;
import com.bikkadit.electronicstore.electronicstore.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private ModelMapper mapper;

    User user;

    @BeforeEach
    public void init(){
        user = User.builder()
                .name("Praval")
                .email("pravalps@gmail.com")
                .password("praval123")
                .gender("Male")
                .about("This is testing for create method")
                .imageName("praval.png")
                .build();
    }

    // create testing for user
    @Test
    public void createUserTest(){
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));
         //System.out.println(user1.getName());
        //   Assertions.assertNotNull(user1);
        Assertions.assertEquals("Praval",user1.getName());
    }



    // update testing for user
    @Test
    public void updateUserTest(){
        String userId="";
        UserDto userDto = UserDto.builder()
                .name("Praval Pratap Singh")
                .gender("Male")
                .about("This is updated user about details")
                .imageName("xyz.png")
                .build();
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto updateUser = userService.updateUser(userDto, userId);
        //   UserDto updateUser = mapper.map(user, UserDto.class);
        System.out.println(updateUser.getName());
        System.out.println(updateUser.getGender());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(userDto.getName(),updateUser.getName(),"Name is not valid !!");
    }


    // delete user test
    @Test
    public void deleteUserTest(){

        String userId="useridabd";
        Mockito.when(userRepository.findById("useridabd")).thenReturn(Optional.of(user));
        userService.deleteUser(userId);
        Mockito.verify(userRepository,Mockito.times(1)).delete(user);

    }



    // get all user test
    @Test
    public void getAllUserTest(){

        User  user1 = User.builder()
                .name("Shalini")
                .email("shalini@gmail.com")
                .password("shalini123")
                .gender("Female")
                .about("This is testing for get all method")
                .imageName("shalini.png")
                .build();

        User user2 = User.builder()
                .name("Varun")
                .email("varun@gmail.com")
                .password("varun54")
                .gender("Male")
                .about("This is testing for get all method")
                .imageName("varun.png")
                .build();

        List<User> userList= Arrays.asList(user,user1,user2);
        Page<User> page=new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        //   Sort sort=Sort.by("name").ascending();
        //     Pageable pageable = PageRequest.of(1, 2, sort);
        PageableResponse<UserDto> allUser = userService.getAllUser(1,2,"name","asc");
        System.out.println(allUser.getTotalElements());
        Assertions.assertEquals(3,allUser.getContent().size());

    }



    // get single users Test

    @Test
    public void getUserByIdTest(){

        String userId="userIdTest";
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        // actual call of service method
        UserDto userDto = userService.getUserById(userId);
        System.out.println(userDto.getName());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getName(),userDto.getName(),"Name is Not Match !!");
    }


    // get user by email id test
    @Test
    public void getUserByEmailTest(){

        String emailId="pravalps@gmail.com";
        Mockito.when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserByEmail(emailId);
        System.out.println(userDto.getName());
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getEmail(),userDto.getEmail(),"Email is not matched !!");


    }


    // search user
   /* @Test
    public void searchUserTest(){

        User  user1 = User.builder()
                .name("Shalini ")
                .email("shalini@gmail.com")
                .password("shalini123")
                .gender("Female")
                .about("This is testing for search method")
                .imageName("shalini.png")
                .build();

        User user2 = User.builder()
                .name("Varun Dhawan")
                .email("varun@gmail.com")
                .password("varun45")
                .gender("Male")
                .about("This is testing for search method")
                .imageName("varun.png")
                .build();

        User user3 = User.builder()
                .name("Ajay")
                .email("ajay@gmail.com")
                .password("ajay123")
                .gender("Male")
                .about("This is testing for search method")
                .imageName("ajay.png")
                .build();

        String keywords="varun";
        Mockito.when(userRepository.findByNameContaining(keywords)).thenReturn(Arrays.asList(user,user1,user2));

        List<UserDto> userDtos = userService.searchUser(keywords)@Test
        public void searchUserTest(){

            User  user1 = User.builder()
                    .name("Shalini ")
                    .email("shalini@gmail.com")
                    .password("shalini123")
                    .gender("Female")
                    .about("This is testing for search method")
                    .imageName("shalini.png")
                    .build();

            User user2 = User.builder()
                    .name("Varun Dhawan")
                    .email("varun@gmail.com")
                    .password("varun45")
                    .gender("Male")
                    .about("This is testing for search method");
        //  System.out.println(userDtos);
        Assertions.assertEquals(3,userDtos.size(),"Size Not Match !!");

    }

*/


}
