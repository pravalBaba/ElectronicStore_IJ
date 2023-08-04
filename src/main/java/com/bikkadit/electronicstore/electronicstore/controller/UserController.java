package com.bikkadit.electronicstore.electronicstore.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.AssertFalse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bikkadit.electronicstore.electronicstore.dtos.ApiResponceMessage;
import com.bikkadit.electronicstore.electronicstore.dtos.ImageResponse;
import com.bikkadit.electronicstore.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.electronicstore.service.FileServiceI;
import com.bikkadit.electronicstore.electronicstore.service.UserServiceI;



@RestController
@RequestMapping("/users")
public class UserController {

	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
    @Autowired
    private UserServiceI userServiceI;
    
    
    @Autowired
    private FileServiceI fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;



    //create


    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
    	
    	logger.info("Entring the request for save user data");
        UserDto userDtoCreate = userServiceI.createUser(userDto);
        logger.info("Complete the request for user data ");
        
        return new ResponseEntity<>(userDtoCreate, HttpStatus.CREATED);

    }

    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
                           @PathVariable ("userId") String userId ) {
    	logger.info("Entering the request for upadate the user with userID{}:",userId);
        UserDto updatedUser = userServiceI.updateUser(userDto, userId);
        logger.info("Complete the request for upadate the user with userID{}:",userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    //delete
        @DeleteMapping("/{userId}")
        public ResponseEntity<ApiResponceMessage> deleteUser(@PathVariable("userId") String userId){

        	logger.info("Entering the request for delete the user with userID{}:",userId);
            userServiceI.deleteUser(userId);
            logger.info("Entering the request for upadate the user with userID{}:",userId);
            ApiResponceMessage message = ApiResponceMessage.builder()
                    .message("User deleted successfully")
                    .success(true)
                    .status(HttpStatus.OK)
                    .build();

            return new ResponseEntity<>(message,HttpStatus.OK);
        }


        //get all
        @GetMapping
        public ResponseEntity<PageableResponse<UserDto>> getAllUser(
        		@RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,
        		@RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
        		@RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
        		@RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
        		){
        	
        	
            logger.info("Entering the reequest for get all user ");
            PageableResponse<UserDto> allUser = userServiceI.getAllUser(pageNumber,pageSize,sortBy,sortDir);
            logger.info("Complete the request for get all user");
            return new ResponseEntity<>(allUser,HttpStatus.OK);
        }
        //get single
    @GetMapping("/{userId}")
    public  ResponseEntity<UserDto> getUserById(@PathVariable String userId){
        UserDto userById = userServiceI.getUserById(userId);
        return new ResponseEntity<>(userById,HttpStatus.OK);


    }

    //get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){

        UserDto userByEmail = userServiceI.getUserByEmail(email);
        return new ResponseEntity<>(userByEmail,HttpStatus.OK);
    }

    
    
    
	/*
	 * //upload user image
	 * 
	 * @PostMapping("/image/{userId}") public ResponseEntity<ImageResponse>
	 * uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable
	 * String userId) throws IOException { String imageName =
	 * fileService.uploadFile(image, imageUploadPath); UserDto user =
	 * userServiceI.getUserById(userId); user.setImageName(imageName); UserDto
	 * userDto = userServiceI.updateUser(user, userId); ImageResponse imageResponse
	 * = ImageResponse.builder().imageName(imageName).success(true).
	 * message("image is uploaded successfully ").status(HttpStatus.CREATED).build()
	 * ; return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
	 * 
	 * }
	 */

	/*
	 * //Search
	 * 
	 * @GetMapping("/search/{keyword}") public ResponseEntity<List<UserDto>>
	 * getUserBySearch(@PathVariable String keyword){
	 * 
	 * List<UserDto> userDtoList = userServiceI.searchUser(keyword); return new
	 * ResponseEntity<>(userDtoList,HttpStatus.OK); }
	 * 
	 * 
	 */


    }


