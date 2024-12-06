package com.dollop.app.services;
import java.nio.file.NoSuchFileException;
import java.util.List;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.dtos.UserDto;

/***
 * 
 * @author WINDOWS 11
 * 
 */
public interface IUserService {

	//1. create user
	/***
	 * 
	 * @param userDto
	 * @return
	 */
	UserDto createUser(UserDto userDto);
	
	
	//2. update user
	/***
	 * 
	 * @param userDto
	 * @param userId
	 * @return
	 */
	UserDto updateUser(UserDto userDto,String id);
	
	
	//3. delete user
	/***
	 * 
	 * @param userId
	 * @throws NoSuchFileException 
	 */
	void deleteUser(String userId) throws NoSuchFileException;
	
	
	//4. get all users
	/***
	 * 
	 * @return
	 */
	//List<UserDto> getAllUsers();
	//List<UserDto> getAllUsers(String name,String email);
	PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	
	//5. get one user
	/***
	 * 
	 * @param Id
	 * @return
	 */
	UserDto getOneUser(String Id);
	
	
	//6. search user
	/***
	 * 
	 * @param keyword
	 * @return
	 */
	List<UserDto> searchUser(String keyword);
	
	
	//7. Get User by OrderId
	
	UserDto getUserByOrder(String orderId);
	
	
	//8. User login 
	UserDto userLogin(String gmail,String password);
	
}
