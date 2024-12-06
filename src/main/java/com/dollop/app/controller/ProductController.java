package com.dollop.app.controller;
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
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.dtos.ProductDto;
import com.dollop.app.services.IFileService;
import com.dollop.app.services.IProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

   @Autowired
   private IProductService productService;
   
   @SuppressWarnings("unused")
   @Autowired
   private IFileService fileService;
   @Value("${user.profile.image.path}")
   private String imageUploadPath;
   
   //1. Create Product
   @PostMapping("/create")
   public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto){
	   return new ResponseEntity<ProductDto>(productService.createProduct(productDto),HttpStatus.OK);
   }
     
   //2. Update  Product
   @PutMapping("/updates/{productId}")
   public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto,@PathVariable String productId){
	   ProductDto updateProductDto=productService.update(productDto, productId);
	   return ResponseEntity.ok(updateProductDto);		   
   }
   
   //3. Provide Category of Product
   @PutMapping("/update/providecategory")
   public ResponseEntity<ApiResponseMessage> provideCategoryByProduct(@RequestParam("productId")String productId,@RequestParam("categoryId") String categoryId){
	   productService.proviceCategoryOfProduct(productId, categoryId);
	   ApiResponseMessage response=ApiResponseMessage.builder()
                                  .message("Category has been provided to the Product !!")
                                  .status(HttpStatus.OK)
                                  .success(true)
                                  .build();
           return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.OK);
   }
   
   //4. Update Category of Product
   @PutMapping("/update/productCategory")
   public ResponseEntity<ApiResponseMessage> updateCategoryOfProduct(@RequestParam String productId,@RequestParam String oldCategoryId,@RequestParam String newCategoryId){
	   productService.updateCategoryOfProduct(productId, oldCategoryId, newCategoryId);
	   ApiResponseMessage response=ApiResponseMessage.builder()
                                   .message("Product Category has been Updated !!")
                                   .status(HttpStatus.OK)
                                   .success(true)
                                   .build();
         return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.OK);
         
   }
   //5. Get One Product
   @GetMapping("/get/{productId}")
   public ResponseEntity<ProductDto> getOneProduct(@RequestParam String productId){
	   return new ResponseEntity<ProductDto>(productService.getProduct(productId),HttpStatus.OK);
   }
    
   //6. Get All Product
   @GetMapping("/getAll")
	public ResponseEntity<PageableResponse<ProductDto>> getAllUsers(
		@RequestParam(value="pageNumber",defaultValue="0",required=false)Integer pageNumber,
		@RequestParam(value="pageSize",defaultValue="10",required=false)Integer pageSize,
		@RequestParam(value="sortBy",defaultValue="name",required=false)String sortBy,
		@RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir	){
		return new ResponseEntity<PageableResponse<ProductDto>>(productService.getAll(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
	}
   
   //7. Get All Product by Live 
   @GetMapping("/getAll1/{live}")
   public ResponseEntity<PageableResponse<ProductDto>> getAllUserByLive(
		 @RequestParam Boolean live,
		 @RequestParam(value="pageNumber",defaultValue="0",required=false)Integer pageNumber,
		 @RequestParam(value="pageSize",defaultValue="10",required=false)Integer pageSize,
		 @RequestParam(value="sortBy",defaultValue="name",required=false)String sortBy,
		 @RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir	
		   ){
	   return new ResponseEntity<PageableResponse<ProductDto>>(productService.getAllProductByLive(live, pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
   }
   
   //8. Get All Product by Titile
   @GetMapping("/getAll2/{title}")
   public ResponseEntity<PageableResponse<ProductDto>> getAllUserByTitle(
		 @RequestParam String title,
		 @RequestParam(value="pageNumber",defaultValue="0",required=false)Integer pageNumber,
		 @RequestParam(value="pageSize",defaultValue="10",required=false)Integer pageSize,
		 @RequestParam(value="sortBy",defaultValue="name",required=false)String sortBy,
		 @RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir	
		   ){
	   return new ResponseEntity<PageableResponse<ProductDto>>(productService.getAllProductByTitle(title, pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
   }
  
   //9. Get All Product by Category
   @GetMapping("/getAll3/{category}")
   public ResponseEntity<PageableResponse<ProductDto>> getAllUserByCategory(
		 @RequestParam String category,
		 @RequestParam(value="pageNumber",defaultValue="0",required=false)Integer pageNumber,
		 @RequestParam(value="pageSize",defaultValue="10",required=false)Integer pageSize,
		 @RequestParam(value="sortBy",defaultValue="name",required=false)String sortBy,
		 @RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir	
		   ){
	   return new ResponseEntity<PageableResponse<ProductDto>>(productService.getAllProductByCategory(category, pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
   }	
   
   //10. Delete Product
   @DeleteMapping("/delete/{productId}")
   public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId){
	   productService.deleteProduct(productId) ;
	   ApiResponseMessage response= ApiResponseMessage.builder()
			                        .message("Product is Deleted")
			                        .status(HttpStatus.OK)
			                        .success(true)
			                        .build();
	   return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.OK);
   }
   
   //11. Product is Live
   @PostMapping("status/{productId}")
   public ResponseEntity<String> isProductLive(@RequestParam String productId,@RequestParam Boolean b){
	   productService.isLiveProduct(productId, b);
	   if(b==false) {
		  return new ResponseEntity<String>("Product is Not Live !!",HttpStatus.OK);
	   }
	   return new ResponseEntity<String>("Product is Live !!",HttpStatus.OK);
   }
}
