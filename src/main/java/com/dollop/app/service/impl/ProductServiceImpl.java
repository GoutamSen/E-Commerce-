package com.dollop.app.service.impl;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.dtos.ProductDto;
import com.dollop.app.entities.Category;
import com.dollop.app.entities.Product;
import com.dollop.app.exceptions.ResourceNotFoundException;
import com.dollop.app.helper.Helper;
import com.dollop.app.repositories.CategoryRepository;
import com.dollop.app.repositories.ProductRepository;
import com.dollop.app.services.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper mapper;
	
	//1. create Product
	@Override
	public ProductDto createProduct(ProductDto productDto) {
	   Product existingProduct=productRepository.getProductByTitle(productDto.getTitle());
	   if(existingProduct!=null){
		  throw new ResourceNotFoundException("Product Title Already Exists !!"); 
	   }
       String productId=UUID.randomUUID().toString();
       productDto.setProductId(productId);
       Product product=dtoToEntity(productDto);
       Product savedProduct=productRepository.save(product);
       ProductDto newDto=entityToDto(savedProduct);
	return newDto;
	}

	//2. Update Product
	@Override
	public ProductDto update(ProductDto productDto, String productId) {
	  Product product=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product Does Not Exists !!"));
	  Product existingProduct=productRepository.getProductByTitle(productDto.getTitle());
	   if(existingProduct!=null&&!existingProduct.equals(product)){
		  throw new ResourceNotFoundException("Product Title Already Exists !!"); 
	   }
	  product.setTitle(productDto.getTitle());	
	  product.setAddedDate(product.getAddedDate());
	  product.setQuantity(productDto.getQuantity());
	  product.setDiscountedPrice(productDto.getDiscountedPrice());
	  product.setDescription(productDto.getDescription());
	  product.setLive(true);
	  product.setPrice(productDto.getPrice());
	  product.setStock(true);
	  product.setProductImageName(productDto.getProductImageName());
	  product.setCategory(product.getCategory());
	  Product updatedProduct= productRepository.save(product);
	return mapper.map(updatedProduct, ProductDto.class);
	}

	//3. Provide Category of Product
	@Override
	public ProductDto proviceCategoryOfProduct(String productId, String categoryId) {
		Product existingProduct=productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException(" Product Does Not Exists !!"));
		if(existingProduct.getCategory()!=null) {
			throw new ResourceNotFoundException(" This Product Category Already Provided !! ");
		}
		Category existingCategory=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Does Not Exists !!"));
		existingProduct.setCategory(existingCategory);
		Product savedProduct=productRepository.save(existingProduct);
		return entityToDto(savedProduct);
	}
  
	//4. Update Category of Product
	@Override
	public ProductDto updateCategoryOfProduct(String productId, String oldCategoryId,String newCategoryId) {
	    Product existingProduct=productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Does Not Exists !!"));
		if(existingProduct.getCategory().getCategoryId()==oldCategoryId) {
			throw new ResourceNotFoundException("Product Does Not Exist  For This Category !!");
		}
		Category category=categoryRepository.findById(newCategoryId).orElseThrow(()-> new ResourceNotFoundException("New Category Id does not Exists for Any Category !!"));
		existingProduct.setCategory(category);
		Product savedProduct=productRepository.save(existingProduct);
	    return entityToDto(savedProduct);
	}

	//5. Get Product By Id
	@Override
	public ProductDto getProduct(String productId) {
		Product findedProduct=productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Does Not Exists !"));
		if(findedProduct.getLive()==false) {
			throw new ResourceNotFoundException("Product Is Exists But It Is Not Live !!");
		}
	return mapper.map(findedProduct, ProductDto.class);
	}
	
	//6. Get All Product
	@SuppressWarnings("null")
	@Override
	public PageableResponse<ProductDto> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?
				  (Sort.by(sortBy).descending()):
				  (Sort.by(sortBy).ascending());
	    Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
	    Page<Product> pages=productRepository.findAll(pageable);
	    
	    for(Product page:pages) {
	    	if(page!=null||page.getLive()==true) {
	    		PageableResponse<ProductDto> response=Helper.getPageableResponse(pages, ProductDto.class);
	    	    return response;
	    	}
	    }
	    return  null;	
	}
	
	//8. Get All Product by Live
	@SuppressWarnings("null")
	@Override
	public PageableResponse<ProductDto> getAllProductByLive(Boolean live,Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?
				  (Sort.by(sortBy).descending()):
				  (Sort.by(sortBy).ascending());
	    Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
	    Page<Product> pages= productRepository.getAllProductByLive(live, pageable);
	    for(Product page:pages) {
	    	if(page!=null||page.getLive()==true) {
	    		 PageableResponse<ProductDto> response=Helper.getPageableResponse(pages, ProductDto.class);    
	    			return response;
	    	}
	    }
	   return null;
	}

	//9. Get All Product by Title
	@SuppressWarnings("null")
	@Override
	public PageableResponse<ProductDto> getAllProductByTitle(String title,Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		
		Category category=categoryRepository.getCategoryByTitle(title);
		if(category==null) {
			throw new ResourceNotFoundException("Category Does Not Exists for This Title !!");
		}
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?
				  (Sort.by(sortBy).descending()):
				  (Sort.by(sortBy).ascending());
	    Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
	    Page<Product> pages=productRepository.getAllProductByCategory(category, pageable);
	    
	    for(Product page:pages) {
	    	if(page!=null||page.getLive()==true) {
	    		 PageableResponse<ProductDto> response=Helper.getPageableResponse(pages, ProductDto.class);
	    			return response;
	    	}
	    }
	return null;
	}

	//10. Get All Product by Category
	@SuppressWarnings("null")
	@Override
	public PageableResponse<ProductDto> getAllProductByCategory(String categoryId, Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Does Not Exists !!"));
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?
				  (Sort.by(sortBy).descending()):
				  (Sort.by(sortBy).ascending());
	    Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
	    Page<Product> pages=productRepository.getAllProductByCategory(category, pageable);
	    
	    for(Product page:pages) {
	    	if(page!=null||page.getLive()==true) {
	    		 PageableResponse<ProductDto> response=Helper.getPageableResponse(pages, ProductDto.class);
	    			return response;
	    	}
	    }
	   return null;
	}
	
	//10. Delete Product
	@Override
	public void deleteProduct(String productId) {
		Product product=productRepository.findById(productId).orElseThrow(()->
		new ResourceNotFoundException("Product Not Found"));
		productRepository.delete(product);
	}
	
	//12. Convert Entity to Dto
	private ProductDto entityToDto(Product product) {		
	return mapper.map(product, ProductDto.class);
	}

	//13. Convert Dto to Entity
	private Product dtoToEntity(ProductDto productDto) {		
	return mapper.map(productDto, Product.class);
	}

	//14. Product Is Live 
	@Override
	public void isLiveProduct(String productId, Boolean b) {
		Product product=productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Does Not Exists !!"));
	    if(product.getLive()==b) {
	    	if(b==true) {
	    	throw new ResourceNotFoundException(" Product is Already Live !!");
	    	}
	    	else if(b==false){
	    	throw new ResourceNotFoundException("Product is Already Not Live");
	    	}
	    }
		product.setLive(b);
	    productRepository.save(product);
	  }
}
