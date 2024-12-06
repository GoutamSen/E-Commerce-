package com.dollop.app.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dollop.app.entities.Category;
import com.dollop.app.entities.Product;

public interface CategoryRepository extends JpaRepository<Category, String> {

	Category getCategoryByProducts(Product product);
	Category getCategoryByTitle(String title);
}
