package com.dollop.app.dtos;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

	private String userId;  
	@Size(min=4,max=30,message="Invalid Name !!")
	@NotBlank(message="User Name is Required !!")
	private String name;
	@Email(message="Invalid User Email !!")
	@NotBlank(message="Email is Required !!")
    private String email;
	@NotBlank(message="Password is Required !!")
	@Size(min=6,max=6,message="Invalid Password !!")
	private String password; 
	@Size(min=4,max=6,message="Invalid Gender !!")
	@NotBlank(message="Gender is Required !!")
	private String gender;  
	@NotBlank(message="Write Something About You !!")
	@Size(min=4,max=300,message="Invalid About !!")
	private String about;
	
//	@ImageNameValid(message="Invalid Image Name !")
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="userDtoIdFk")
	private List<String> imageName;
	
}
