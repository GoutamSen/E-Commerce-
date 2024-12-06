package com.dollop.app.controller;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.dollop.app.dtos.ApiResponseMessage;
import com.dollop.app.dtos.ImageResponse;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.dtos.UserDto;
import com.dollop.app.services.IFileService;
import com.dollop.app.services.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/users")
public class UserRestController {

	@Autowired
	private IUserService service;
	@Autowired
	private IFileService fileService;
	
	@Value("${user.profile.image.path}")
	private String imageUploadPath;
	
	@SuppressWarnings("unused")
	private Logger logger=LoggerFactory.getLogger(UserRestController.class);
	
	
	//1. Create User
	@PostMapping("/create")
	public ResponseEntity<Map<String , String>> createUser(@Valid @RequestBody UserDto userDto){
		service.createUser(userDto);
		Map<String, String> response=new HashMap<>();
		response.put("Status", "Success");
		response.put("message", "User Created Successfully");
		return new ResponseEntity<>(response,HttpStatus.CREATED);	
	}
			
	
	//2. Update User
	@PutMapping("/update/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @PathVariable String id,@RequestBody UserDto userDto){
		 UserDto updateUserDto=service.updateUser( userDto,id);
	     return	ResponseEntity.ok(updateUserDto); 
	}
	
	//3. Delete User
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("id") String Id) throws NoSuchFileException{
		service.deleteUser(Id);
		ApiResponseMessage response=ApiResponseMessage.builder()
				                    .message("User is Deleted")
				                    .success(true)
				                    .status(HttpStatus.OK)
				                    .build();
	    return new ResponseEntity<>(response,HttpStatus.OK);
	}	
	
	
	//4. Get All Users 
    //@GetMapping("/getall")
    //public ResponseEntity<List<UserDto>> getAllUsers(){
    //return ResponseEntity.ok(service.getAllUsers());
    //}
	
	
	@GetMapping("/getaAll")
	public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
		@RequestParam(value="pageNumber",defaultValue="0",required=false)int pageNumber,
		@RequestParam(value="pageSize",defaultValue="10",required=false)int pageSize,
		@RequestParam(value="sortBy",defaultValue="name",required=false)String sortBy,
		@RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir	){
		return new ResponseEntity<PageableResponse<UserDto>>(service.getAllUsers(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
	
	
	//5. Get User By Id	 	
	@GetMapping("/getUser/{id}")
	ResponseEntity<UserDto> getUser(@PathVariable String id){
		return ResponseEntity.ok(service.getOneUser(id));
	}
	
	
	//6. Search User By Keyword
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword){
		return ResponseEntity.ok(service.searchUser(keyword));
	}
	
	//7. Upload Image by User
//	@PostMapping("/image/{userId}")
//	public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile image,@PathVariable String userId) throws IOException{
//	
//	     String imageName=fileService.uploadFile(image, imageUploadPath);
//		 UserDto user=service.getOneUser(userId);
//		 user.setImageName(imageName);
//		 UserDto userDto=service.updateUser(user, userId);
//		 ImageResponse imageResponse=ImageResponse.builder()
//	                                  .imageName(imageName)
//	                                  .message("image is uploaded !")
//	                                  .success(true)
//	                                  .status(HttpStatus.CREATED).build();
//		 return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.OK);
//	}
	
	
	//8. Upload Multiple Images by User
	@PostMapping("/imageUpload/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImages(@RequestParam("userImage") MultipartFile[] images,@PathVariable String userId) throws IOException{
	
		List<String> imageNames=new ArrayList<>();		
		for(MultipartFile image:images) {
			String imageName=fileService.uploadFile(image, imageUploadPath);
			imageNames.add(imageName);
		}
		
		UserDto user=service.getOneUser(userId);
		user.setImageName(imageNames);
		service.updateUser(user, userId);		
		ImageResponse imageResponse=ImageResponse.builder()
		                            .imageName(imageNames)
		                            .message("Images are Uploaded")
		                            .success(true)
		                            .status(HttpStatus.CREATED).build();		
				return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);
	}
	
		
	//9. Get User Image
	@GetMapping("/image/{userId}")
	public void serveUserImage(@PathVariable String userId,HttpServletResponse response) throws IOException {
		UserDto user=service.getOneUser(userId);
		InputStream resource=fileService.getResourse(imageUploadPath, user.getImageName());
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
	//10. Get User by Order
	@GetMapping("/get/{orderId}")
	public ResponseEntity<UserDto> getUserByOrder(@RequestParam String orderId){
		return  new ResponseEntity<UserDto>(service.getUserByOrder(orderId),HttpStatus.OK);
	}
	
	@GetMapping("/login/{email}/{passwor}")
	public ResponseEntity<String> login(@RequestParam("email") String email,@RequestParam("password") String password){
        System.out.println("Start"+email);
		@SuppressWarnings("unused")
		UserDto userDto=service.userLogin(email, password);
        Map<String ,String> response = new HashMap<>();
        response.put("message", "User Login");
		return new ResponseEntity<String>("User Login",HttpStatus.OK);
	}
	
}