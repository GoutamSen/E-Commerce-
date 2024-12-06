package com.dollop.app.services;
import com.dollop.app.dtos.CartDto;

public interface ICartService {

	//1. Create Cart
	CartDto createCart(CartDto cartDto,String userId);
	
	//2. Get Cart by CartId
	CartDto getCartByCartId(String cartId);
	
	//3. Get Cart by User
	CartDto getCartByUser(String userId);
	
	//4. Clear Cart by CartId
	void clearCartByCartId(String cartId);
	
	//5. Clear Cart by UserId
	void clearCartByUserId(String userId);
	
	//6. Get Cart by CartItem
	CartDto getCartByCartItem(String cartItemId);
	
}
