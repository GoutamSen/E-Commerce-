package com.dollop.app.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dollop.app.dtos.CartDto;
import com.dollop.app.services.ICartService;

@RestController
@RequestMapping("/carts")
public class CartController {

	@Autowired
	private ICartService cartService;
	
	//1. Create Cart with User
	@PostMapping("/create")
	public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto,@RequestParam String userId){
		return new ResponseEntity<CartDto>(cartService.createCart(cartDto, userId),HttpStatus.CREATED);
	}
	
	//2. Get Cart by CartId
	@GetMapping("/get/{cartId}")
	public ResponseEntity<CartDto> getCartByCartId(@PathVariable String cartId){
		return new ResponseEntity<CartDto>(cartService.getCartByCartId(cartId),HttpStatus.OK);
	}
	
	//3. Get Cart by UserId
	@GetMapping("/get/{userId}")
	public ResponseEntity<CartDto> getCartByUserId(@RequestParam String userId){
		return new ResponseEntity<CartDto>(cartService.getCartByUser(userId),HttpStatus.OK);
	}
	
	//4. Clear Cart by CartId
	@DeleteMapping("/delete/{cartId}")
	public ResponseEntity<String> removeCartByCartId(@RequestParam String cartId){
		cartService.clearCartByCartId(cartId);
		return new ResponseEntity<String>(" Cart Removed !!",HttpStatus.OK);
	}
	
	//5. Clear Cart by UserId
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> removeCartByUserId(@RequestParam String userId){
    	cartService.clearCartByUserId(userId);
    	return new ResponseEntity<String>(" Cart Removed !!",HttpStatus.OK);
    }
    
    //6. Get Cart by CartItem
    @GetMapping("/get/{cartItemId}")
    public ResponseEntity<CartDto> getCartByCartItem(@RequestParam String cartItemId){
    	return new ResponseEntity<CartDto>(cartService.getCartByCartItem(cartItemId),HttpStatus.OK);
    }
   
}
