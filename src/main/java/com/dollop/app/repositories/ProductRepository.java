package com.dollop.app.repositories;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dollop.app.entities.Category;
import com.dollop.app.entities.Product;

public interface ProductRepository extends JpaRepository<Product, String>{

	Page<Product> getAllProductByLive(Boolean live,Pageable pageable);
	Page<Product> getAllProductByTitle(String title,Pageable pageable);
	List<Product> getAllProductByTitle(String title);
	Page<Product> getAllProductByCategory(Category category,Pageable pageable);
	
	Product getProductByCategory(Category category);
	Product getProductByTitle(String title);
}
