package com.dollop.app.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import com.dollop.app.dtos.ApiResponseMessage;
import com.dollop.app.dtos.CategoryDto;
import com.dollop.app.dtos.ImageResponse;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.services.ICategoryService;
import com.dollop.app.services.IFileService;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IFileService fileService;
	
	@Value("${user.profile.image.path}")
	private String imageUploadPath;
	
	//1. Create Category
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		return new ResponseEntity<CategoryDto>(categoryService.create(categoryDto),HttpStatus.CREATED);
	}
	
	//2. Update Category
	@PutMapping("/update/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @PathVariable String categoryId,@RequestBody CategoryDto categoryDto){
		CategoryDto updatedCategoryDto=categoryService.update(categoryDto, categoryId);
		return ResponseEntity.ok(updatedCategoryDto);
	}
	
	//3. Delete Category
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) throws NoSuchFieldException{
		categoryService.delete(categoryId);
		ApiResponseMessage response=ApiResponseMessage.builder()
				                    .message(" Category is Deleted !")
				                    .success(true)
				                    .status(HttpStatus.OK)
		                            .build();
		return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.OK);
	}
	
	//4. Get One Category
	@GetMapping("/get/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable String categoryId){
		return ResponseEntity.ok(categoryService.get(categoryId));
		
	}
	//.5 Get All Categories
	@GetMapping("/getAll")
	public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
		@RequestParam(value="pageNumber",defaultValue="0",required=false)int pageNumber, 
		@RequestParam(value="pageSize",defaultValue="10",required=false)int pageSize,
		@RequestParam(value="sortBy",defaultValue="name",required=false)String sortBy,
		@RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir	){
		return new ResponseEntity<PageableResponse<CategoryDto>>(categoryService.getAll(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
	}	
	
//	//6. Upload One Image
//	@PostMapping("/image/{productId}")
//	public ResponseEntity<ImageResponseSecondBuilder> uploadCategoryImage(@RequestParam("categoryImage")MultipartFile image,@PathVariable String categoryId) throws IOException{
//		String imageName=fileService.uploadFile(image, imageUploadPath);
//		CategoryDto categoryDto=categoryService.get(categoryId);
//		categoryDto.setCoverImage(imageName);
//		CategoryDto updateCategoryDto= categoryService.update(categoryDto, categoryId);
//		ImageResponseSecondBuilder response=ImageResponseSecond.builder()
//				                    .imageName(imageName)
//				                    .message("Image is Uploaded !!")
//				                    .success(true)
//				                    .status(HttpStatus.CREATED);
//		return  ResponseEntity.ok(response);
//	}
	
	//7. Upload  Multiple Image
	@PostMapping("/imageupload/{categoryId}")
	public ResponseEntity<ImageResponse> uploadCategoryImage(@RequestParam("categoryImage") MultipartFile[] images,@PathVariable String categoryId) throws IOException{
	 List<String> imageNames=new ArrayList<>();
	 for(MultipartFile image:images) {
		 String imageName=fileService.uploadFile(image, imageUploadPath);
		 imageNames.add(imageName);
	 }
	 CategoryDto categoryDto=categoryService.get(categoryId);
	 categoryDto.setCoverImage(imageNames);
	 categoryService.update(categoryDto, categoryId);
	 ImageResponse imageResponse=ImageResponse.builder()
			                     .imageName(imageNames)
			                     .message("Upload Image")
			                     .success(true)
			                     .status(HttpStatus.CREATED)
			                     .build();
	 return new ResponseEntity<ImageResponse>(imageResponse,HttpStatus.CREATED);
	}	
	
	//8. Get Category by Product
	@GetMapping("/gets/{productId}")
	public ResponseEntity<CategoryDto> getCategoryByProduct(@RequestParam String productId){
		return new ResponseEntity<CategoryDto>(categoryService.getCategoryByProductId(productId),HttpStatus.OK);
	}
}
	
	
	
	
