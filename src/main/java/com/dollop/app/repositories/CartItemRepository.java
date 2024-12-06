package com.dollop.app.repositories;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dollop.app.entities.Cart;
import com.dollop.app.entities.CartItem;
import com.dollop.app.entities.Product;

public interface CartItemRepository extends JpaRepository<CartItem, String> {

	Page<CartItem> getCartItemByCart(Cart cart,Pageable pageable);
	List<CartItem> getCartItemsByCart(Cart cart);
	CartItem getCartItemByCartAndProduct(Cart cart,Product product);
	
	
}
