package com.dollop.app.service.impl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.dtos.UserDto;
import com.dollop.app.entities.Order;
import com.dollop.app.entities.User;
import com.dollop.app.exceptions.ResourceNotFoundException;
import com.dollop.app.helper.Helper;
import com.dollop.app.repositories.OrderRepository;
import com.dollop.app.repositories.UserRepository;
import com.dollop.app.services.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired 
	private UserRepository userRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ModelMapper mapper;	
	@Value("${user.profile.image.path}")
	private String imagePath;	
	@SuppressWarnings("unused")
	private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
	//1. create user
	@Override
	public UserDto createUser(UserDto userDto) {

		String userId=UUID.randomUUID().toString();   
		userDto.setUserId(userId);                     
		User users=dtoToEntity(userDto);
		User user=userRepository.getUserByEmail(userDto.getEmail());
		if(user!=null) {
			throw new ResourceNotFoundException(" Email ID is Already Exists for Another User !!");
		}
		User savedUser=userRepository.save(users);    
		UserDto newDto=entityToDto(savedUser);        
		return newDto;
	}

	//2. update user
	@Override
	public UserDto updateUser(UserDto userDto,String userId) {
		     User user=userRepository.findById(userId).orElseThrow(()->
		               new ResourceNotFoundException("User Not Found for Given "));
		               user.setName(userDto.getName());
		               User existingUser=userRepository.getUserByEmail(userDto.getEmail());
			       		if(existingUser!=null&&!existingUser.equals(user)) {
			       			throw new ResourceNotFoundException("Email ID is Already Exists for Another User  !!");
			       		}
		               user.setEmail(userDto.getEmail());
		               user.setAbout(userDto.getAbout());
		               user.setGender(userDto.getGender());
		               user.setPassword(userDto.getPassword());
		               user.setImageName(userDto.getImageName());
		               User updatedUser=userRepository.save(user);
		               UserDto updateDto=mapper.map(updatedUser, UserDto.class);
		return updateDto;
	}

	//3. delete user
	@Override
	public void deleteUser(String userId) throws NoSuchFileException{
	     User user=userRepository.findById(userId).orElseThrow(()->
	               new ResourceNotFoundException("User Not Found for Given "));
	     String fullPath=imagePath+user.getImageName();
	     Path path=Paths.get(fullPath);    	 
	     try {
			Files.delete(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     userRepository.delete(user);
	}

//    4. get all users 
//	  @Override public List<UserDto> getAllUsers() { 	
//	  List<User> users=userRepository.findAll(); 
//	  List<UserDto> usersDto=users.stream().
//	                         map((user)->mapper.map(user,UserDto.class))
//	                         .collect(Collectors.toList());
//	  return usersDto; 
//	  }
	
	
	
	@Override
	public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy,String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?
				  (Sort.by(sortBy).descending()):
				  (Sort.by(sortBy).ascending());
	    Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
	    Page<User> page=userRepository.findAll(pageable);
	    PageableResponse<UserDto> response=Helper.getPageableResponse(page,UserDto.class);
	    return response;
	} 
	
	
	
	//5. Get One User
	@Override
	public UserDto getOneUser(String Id) {
		User user=userRepository.findById(Id).orElseThrow(()->
                  new ResourceNotFoundException("User Not Found for Given "));
		return mapper.map(user, UserDto.class);
	}

	//6. search user
	@Override
	public List<UserDto> searchUser(String keyword) {
		List<User> users=userRepository.findByNameContaining(keyword);
				         List<UserDto> usersDto=users.stream().
   		                 map((user)->mapper.map(user,UserDto.class))
   		                 .collect(Collectors.toList());
        return usersDto;
	}
	   
	//7. Get User by OrderId
	@Override
	public UserDto getUserByOrder(String orderId) {
    Order order=orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order Does Not Exist !!"));
	User user=userRepository.getUserByOrders(order);
	UserDto userDto=entityToDto(user);
    return userDto;
	}
	
	//8. Convert Entity to Dto
	private UserDto entityToDto(User savedUser) {
//		UserDto userDto=UserDto.builder()
//				.userId(savedUser.getUserId())
//				.name(savedUser.getName())
//				.email(savedUser.getEmail())
//				.password(savedUser.getPassword())
//				.gender(savedUser.getGender())
//				.about(savedUser.getAbout())
//				.imageName(savedUser.getImageName())
//				.build();		
		return mapper.map(savedUser, UserDto.class);
	}
	
	//9. Convert Dto to Entity
	private User dtoToEntity(UserDto userDto) {
//		User user=User.builder()
//				.userId(userDto.getUserId())
//				.name(userDto.getName())
//				.email(userDto.getEmail())
//				.password(userDto.getPassword())
//				.gender(userDto.getGender())
//				.about(userDto.getAbout())
//				.imageName(userDto.getImageName())
//				.build();
		return mapper.map(userDto, User.class);
	}

	@Override
	public UserDto userLogin(String gmail, String password) {
       User user=userRepository.getUserByEmailAndPassword(gmail, password);
       if(user == null) {
    	   new ResourceNotFoundException("User does not exists !");
       }
       return entityToDto(user);
	}
}
