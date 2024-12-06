package com.dollop.app.services;
import com.dollop.app.dtos.CartItemDto;
import com.dollop.app.dtos.PageableResponse;

public interface ICartItemService {
	
	   //1.  Add CartItem in Cart
	   CartItemDto addCartItemInCart(CartItemDto cartItemDto,String cartId,String productId); 

	   //2. Get CartItem by CartItemId
	   CartItemDto getCartItemByCartItemId(String cartItemId);
	   
	   //3. Get All CartItem 
	   PageableResponse<CartItemDto> getAllCartItem(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	   	   
	   //4. Get AllCartItem in Cart
	   PageableResponse<CartItemDto> getCartItemByCart(String cartId,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	   	   
	   //5. Get CartItem from Cart by product
	   CartItemDto getCartItemFromCartByProduct(String cartId,String productId);
	
	   //6. Remove CartItem from Cart
	   void removeCartItemFromCart(String cartItemId);
	   
	   //7. Remove CartItem from Cart by Product
	   void removeCartItemFromCartByProduct(String cartId,String productId);
}
