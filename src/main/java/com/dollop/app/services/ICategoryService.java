package com.dollop.app.services;
import com.dollop.app.dtos.CategoryDto;
import com.dollop.app.dtos.PageableResponse;

public interface ICategoryService {

	//1. Create Category
	CategoryDto create(CategoryDto categoryDtoo);
	
	//2. Update Category
	CategoryDto update(CategoryDto categoryDto,String categoryId);
	
	//3. Get Category
	CategoryDto get(String categoryId);
	
	//4. Get All Categories
	PageableResponse<CategoryDto> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//5. Delete Category
	void delete(String categoryId);
	
	//6. Get Category by Product
	CategoryDto getCategoryByProductId(String productId);
	
	
}

