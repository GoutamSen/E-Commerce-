package com.dollop.app.service.impl;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dollop.app.dtos.CartDto;
import com.dollop.app.entities.Cart;
import com.dollop.app.entities.CartItem;
import com.dollop.app.entities.User;
import com.dollop.app.exceptions.ResourceNotFoundException;
import com.dollop.app.repositories.CartItemRepository;
import com.dollop.app.repositories.CartRepository;
import com.dollop.app.repositories.UserRepository;
import com.dollop.app.services.ICartService;

@Service
public class CartServiceImpl implements ICartService {

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private ModelMapper mapper;
	
	//1. Create Cart with User
	@Override
	public CartDto createCart(CartDto cartDto,String userId) {	
		User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Does Not Exists !!"));
		if(cartRepository.getCartByUser(user)!=null) {
			throw new ResourceNotFoundException("This User's already Cart Exists !!");
		}
		String cartId=UUID.randomUUID().toString();
		cartDto.setCartId(cartId);
		Cart newcart=dtoToEntity(cartDto);
		newcart.setUser(user);
		Cart savedCart=cartRepository.save(newcart);
		CartDto savedCartDto=entityToDto(savedCart);
	return savedCartDto;
	}

	//2. Get Cart by CartId
	@Override
	public CartDto getCartByCartId(String cartId) {
        Cart cart=cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart Does Not Exists !!"));
        CartDto cartDto=entityToDto(cart);
		return cartDto;
	}
	
    //3. Get Cart by UserId
	@Override
	public CartDto getCartByUser(String userId) {
		User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Does Not Exists !!"));
	    Cart findedCart=cartRepository.getCartByUser(user);
	    CartDto findedCartDto=entityToDto(findedCart);
	return findedCartDto;
		}


	//4. Clear Cart by CartId
	@Override
	public void clearCartByCartId(String cartId) {
		Cart cart=cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException(" Cart Does Not Exists !!"));
		cartRepository.delete(cart);
	}

	//5. Clear Cart by UserId
	@Override
	public void clearCartByUserId(String userId) {
		User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Does Not Exists !!"));
	    Cart findedCart=cartRepository.getCartByUser(user);
	    cartRepository.delete(findedCart);
	}
	
	 //6. Convert Dto to Entity
	 private Cart dtoToEntity(CartDto cartDto) {
			return mapper.map(cartDto, Cart.class);
		}
		
	 //7. Convert Entity to Dto 
	 private CartDto entityToDto(Cart cart) {
	        return mapper.map(cart, CartDto.class);
		}

	 //8. Get Cart by CartItem
	@Override
	public CartDto getCartByCartItem(String cartItemId) {
		CartItem cartItem=cartItemRepository.findById(cartItemId).orElseThrow(()-> new ResourceNotFoundException("CartItem Does Not Exists !!"));
		Cart cart=cartRepository.getCartByCartItems(cartItem);
		CartDto cartDto=entityToDto(cart);
		return cartDto;
	}
}
