package com.dollop.app.service.impl;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.dollop.app.dtos.CategoryDto;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.entities.Category;
import com.dollop.app.entities.Product;
import com.dollop.app.exceptions.ResourceNotFoundException;
import com.dollop.app.helper.Helper;
import com.dollop.app.repositories.CategoryRepository;
import com.dollop.app.repositories.ProductRepository;
import com.dollop.app.services.ICategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ModelMapper mapper;
	
	//1. Create Category
	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		Category existingCategory=categoryRepository.getCategoryByTitle(categoryDto.getTitle());
		if(existingCategory!=null) {
			throw new  ResourceNotFoundException("Category Title All Ready Exists !!");
		}
		String categoryId=UUID.randomUUID().toString();
		categoryDto.setCategoryId(categoryId);
		Category category=dtoToEntity(categoryDto);
		Category savedCategory=categoryRepository.save(category);
		CategoryDto newDto=entityToDto(savedCategory);
		return newDto;
	}

	//2. Update Category
	@Override
	public CategoryDto update(CategoryDto categoryDto, String categoryId) {
		Category category= categoryRepository .findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Does Not Exists !!"));			
		category.setTitle(categoryDto.getTitle());
		Category existingCategory=categoryRepository.getCategoryByTitle(categoryDto.getTitle());
		if(existingCategory!=null&&!existingCategory.equals(category)) {
			throw new ResourceNotFoundException("Category Title All Ready Exists !!");
		}
		category.setDescription(categoryDto.getDescription());
		category.setCoverImage(categoryDto.getCoverImage());
		Category updatedCategory=categoryRepository.save(category);
		CategoryDto newDto=entityToDto(updatedCategory);
		return newDto;
	}

	//3. Get Category
	@Override
	public CategoryDto get(String categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category Does Not Exists !!"));
		return mapper.map(category, CategoryDto.class);
	}
	
	//4. Get All Category
	@Override
	public PageableResponse<CategoryDto> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?
				(Sort.by(sortBy).descending()):
					(Sort.by(sortBy).ascending());
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
		Page<Category> page=categoryRepository.findAll(pageable);
		PageableResponse<CategoryDto> response=Helper.getPageableResponse(page, CategoryDto.class);
		return response;
	}
	
	//6. Delete Category
	@Override
	public void delete(String categoryId) {
      Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException(" Category Does Not Exists !!"));
      categoryRepository.delete(category);
	}

	//7. Convert Entity to Dto
	private CategoryDto entityToDto(Category Category) {
		return mapper.map(Category,CategoryDto.class );
	}
	
	//8. Convert Dto to Entity
	private Category dtoToEntity(CategoryDto categoryDto) {
		return mapper.map(categoryDto, Category.class);
	}

	//9. Get Category by Product
	@Override
	public CategoryDto getCategoryByProductId(String productId) {
		Product product=productRepository.findById(productId).orElseThrow(()-> new  ResourceNotFoundException("Product Does Not Exists !!"));
	    if(product.getLive()==false) {
	    	throw new ResourceNotFoundException("Product is Exists but it is Not Live !!");
	    }
		Category category=categoryRepository.getCategoryByProducts(product);
		CategoryDto categoryDto=entityToDto(category);
		return categoryDto;
	}

}
