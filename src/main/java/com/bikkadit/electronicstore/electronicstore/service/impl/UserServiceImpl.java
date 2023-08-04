package com.bikkadit.electronicstore.electronicstore.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bikkadit.electronicstore.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.electronicstore.entities.User;
import com.bikkadit.electronicstore.electronicstore.exception.ResourceNotFoundException;
import com.bikkadit.electronicstore.electronicstore.helper.Helper;
import com.bikkadit.electronicstore.electronicstore.repository.UserRepository;
import com.bikkadit.electronicstore.electronicstore.service.UserServiceI;

import lombok.extern.slf4j.Slf4j;
//import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

import org.springframework.data.domain.Sort;

@Slf4j
@Service
public class UserServiceImpl implements UserServiceI {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public UserDto createUser(UserDto userDto) {

    	log.info("Initiating the call for create user");

        //genrate unique id
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        //dto to entity
        User newUser =  dtoToEntity(userDto);
        User savedUser = userRepository.save(newUser);
        //entity to dto
        UserDto newUserDto=entityToDto(savedUser);
        
        log.info("Complete the call for create user");
        return newUserDto;
    }





    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

    	log.info("Initiating the call for update  user with user Id{}:",userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        //update the fields

        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());

        User savedUser = userRepository.save(user);

        UserDto updatedUserDto = entityToDto(savedUser);

        log.info("Completing the call for update  user with user Id{}:",userId);
        return updatedUserDto;
    }

    @Override
    public void deleteUser(String userId) {

    	log.info("Initiating the call for delete  user with user Id{}:",userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        userRepository.delete(user);
        log.info("Completing the call for delete user with user Id{}:",userId);
        
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber,int pageSize,String sortBy,String sortDir) {

   Sort sort = 	(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
    	
    	Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
    	
    	log.info("Initiating the call for get all user ");
    	Page<User> page = userRepository.findAll(pageable);
    	List<User> userList = page.getContent();
        //List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = userList.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
		/*
		 * PageableResponse<UserDto> response = new PageableResponse<>();
		 * response.setContent(userDtoList); response.setPageNumber(page.getNumber());
		 * response.setPageSize(page.getSize());
		 * response.setTotalElements(page.getTotalElements());
		 * response.setTotalPages(page.getTotalPages());
		 * response.setLastPage(page.isLast());
		 */
        
        PageableResponse<UserDto> response = Helper.getPageableResponse(page,UserDto.class);
        
        log.info("Completing the call for  get all user");
        return response;

    }

    @Override
    public UserDto getUserById(String userId) {

    	log.info("Initiating the call for get user with user Id{}:",userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        UserDto userDto = entityToDto(user);

        log.info("Completing  the call for get user with user Id{}:",userId);
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {

    	log.info("Initiating the call for get user with email{}:",email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with given email"));

        UserDto userDto = entityToDto(user);

        log.info("Initiating the call for get user with email{}:",email);

        return userDto;
    }

	/*
	 * @Override public List<UserDto> searchUser(String keyword) { List<User>
	 * userList = userRepository.findByNamecontaining(keyword); List<UserDto>
	 * userDtoList = userList.stream().map(user ->
	 * entityToDto(user)).collect(Collectors.toList());
	 * 
	 * 
	 * return userDtoList; }
	 * 
	 */
    private UserDto entityToDto(User savedUser) {

        /*UserDto userDto = UserDto.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .gender(savedUser.getGender())
                .about(savedUser.getAbout())
                .imageName(savedUser.getImageName()).build();
*/
        UserDto userDto = mapper.map(savedUser, UserDto.class);
        return userDto;
    }

    private User dtoToEntity(UserDto userDto) {

        /*User user = User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getUserId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .gender(userDto.getGender())
                .about(userDto.getAbout())
                .imageName(userDto.getImageName()).build();*/

        User user = mapper.map(userDto, User.class);
        return user;
    }
}
