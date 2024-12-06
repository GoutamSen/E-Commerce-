package com.dollop.app.services;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.dtos.ProductDto;

public interface IProductService {

	//1. Create Product
	ProductDto createProduct(ProductDto productDto);
	
	//2. Update Product
	ProductDto update(ProductDto productDto,String productId);
	
	//3. Provide Category of Product
	ProductDto proviceCategoryOfProduct(String productId,String categoryId);
	
	//4. Update Category of Product
	ProductDto updateCategoryOfProduct(String productId,String oldCategoryId,String newCategoryId);
	
	//5. Get Product
	ProductDto getProduct(String productId);
	
	//6. Get All Product
	PageableResponse<ProductDto> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//7. Get All Product by Live
	PageableResponse<ProductDto> getAllProductByLive(Boolean live,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//8. Get All Product by Title
	PageableResponse<ProductDto> getAllProductByTitle(String title,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//9. Get All Product by Category
	PageableResponse<ProductDto> getAllProductByCategory(String category, Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//10. Delete Product
	void deleteProduct(String productId);

	//11. Delete Category in Products
    //void deleteProductWithCategory(String categoryId,String productId);
	
	//12. Is Live
	void isLiveProduct(String productId,Boolean b);
}
