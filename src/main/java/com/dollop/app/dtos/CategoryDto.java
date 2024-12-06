package com.dollop.app.dtos;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
public class CategoryDto {

	private String categoryId;
	@Size(min=5,max=30,message="Invalid  product Title !!")
	@NotBlank(message="Title is Required !!")
	private String title;	
	@Size(min=5,max=500,message="Invalid Description !!")
	@NotBlank(message="Description is Required !!")
	private String description;
	
	//@ImageNameValid(message="Invalid coverImage Name !!")
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="categoryDtoIdFK")
	private List<String> coverImage;
}
