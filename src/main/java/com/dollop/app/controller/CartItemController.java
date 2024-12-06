package com.dollop.app.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dollop.app.dtos.ApiResponseMessage;
import com.dollop.app.dtos.CartItemDto;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.services.ICartItemService;

@RestController
@RequestMapping("/cartItems")
class CartItemController {

	@Autowired
	private ICartItemService cartItemService;
	
	//1. Add CartItem in Cart 
	@PostMapping("/add")
	public ResponseEntity<CartItemDto> addCartItem(@RequestBody CartItemDto cartItemDto,@RequestParam String cartId,@RequestParam String productId){
		return new ResponseEntity<CartItemDto>(cartItemService.addCartItemInCart(cartItemDto,cartId,productId),HttpStatus.CREATED);
	}
	
	//2. Get CartItem by CartItemId
	@GetMapping("/get/{cartId}")
	public ResponseEntity<CartItemDto> getCartItemByCartItemId(@RequestParam String cartItemId){
		return new ResponseEntity<CartItemDto>(cartItemService.getCartItemByCartItemId(cartItemId),HttpStatus.OK);
	}
	
	//3. Get All CartItem
	@GetMapping("/getAll")
	public ResponseEntity<PageableResponse<CartItemDto>> getAllCartItem(
			@RequestParam(value="pageNumber",defaultValue="0",required=false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue="10",required=false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue="name",required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir	
			){
		return new ResponseEntity<PageableResponse<CartItemDto>>(cartItemService.getAllCartItem(pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
	}
	
	//4. Get All CartItem from Cart
	@GetMapping("/getAll/{cartId}")
	public ResponseEntity<PageableResponse<CartItemDto>> getAllCartItemInCart(
			@RequestParam String catId,
			@RequestParam(value="pageNumber",defaultValue="0",required=false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue="10",required=false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue="name",required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir	
			){
		return new ResponseEntity<PageableResponse<CartItemDto>>(cartItemService.getCartItemByCart(catId, pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
	}
		
	//5. Get CartItem by CartId And ProductId
	@GetMapping("/get/{cartId}/{productId}")
	public ResponseEntity<CartItemDto> getCartItemFromCartByProduct(@RequestParam String cartId,@RequestParam String productId){
		return new ResponseEntity<CartItemDto>(cartItemService.getCartItemFromCartByProduct(cartId, productId),HttpStatus.OK);
	}
	
	//6. Remove CartItem from Cart
	@DeleteMapping("remove/{cartItem}")
	public ResponseEntity<String> removeCartItem(@RequestParam String cartItem){
		cartItemService.removeCartItemFromCart(cartItem);
		return new ResponseEntity<String>("Remove CartItem form Cart !!",HttpStatus.OK);
	}
	
	//7. Remove CartItem from Cart by ProductId
	@DeleteMapping("/remove/{cartId}/{productId}")
	public ResponseEntity<ApiResponseMessage> removeCartItemFromCartByProduct(@RequestParam String cartId,@RequestParam String productId){
	    cartItemService.removeCartItemFromCartByProduct(cartId, productId);
	    ApiResponseMessage response=ApiResponseMessage.builder()
                                    .message("CartItem is Deleted !!")
                                    .success(true)
                                    .status(HttpStatus.OK)
                                    .build();
          return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
}
