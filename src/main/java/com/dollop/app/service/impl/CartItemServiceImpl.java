package com.dollop.app.service.impl;
import java.util.List;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.dollop.app.dtos.CartItemDto;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.entities.Cart;
import com.dollop.app.entities.CartItem;
import com.dollop.app.entities.Product;
import com.dollop.app.exceptions.ResourceNotFoundException;
import com.dollop.app.helper.Helper;
import com.dollop.app.repositories.CartItemRepository;
import com.dollop.app.repositories.CartRepository;
import com.dollop.app.repositories.ProductRepository;
import com.dollop.app.services.ICartItemService;

@Service
public class CartItemServiceImpl implements ICartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	//1. Add CartItem in Cart
	@Override
	public CartItemDto addCartItemInCart(CartItemDto cartItemDto,String cartId,String productId) {	
	Cart cart=cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart Does Not Exists !!"));
	Product product=productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Does Not Exists !!"));
	if(product.getLive()==false) {
		throw new ResourceNotFoundException("Product is Exists but It is Not Live !!");
	}
	List<CartItem> cartItems=cartItemRepository.getCartItemsByCart(cart);
    for(CartItem cartItem: cartItems) {
    	if(cartItem.getProduct().equals(product)) {
    		throw new ResourceNotFoundException("Product All Ready Exists in this Cart !!");
    	}
    }
   	String cartItemId=UUID.randomUUID().toString();
    cartItemDto.setCartItemId(cartItemId);
    CartItem cartItem=dtoToEntity(cartItemDto); 
    Double totalPrize=cartItemDto.getQuantity()*(product.getPrice()/product.getQuantity()-product.getDiscountedPrice()/product.getQuantity());
    cartItem.setTotalPrize(totalPrize);  
    cartItem.setCart(cart);
    cartItem.setProduct(product);
    CartItem savedCartItem=cartItemRepository.save(cartItem);
	CartItemDto newCartItemDto=entityToDto(savedCartItem);
    return newCartItemDto;
	}

	//2. RemoveCartItem from Cart by CartItemId
	@Override
	public void removeCartItemFromCart(String cartItemId) {
		CartItem cartItem=cartItemRepository.findById(cartItemId).orElseThrow(()-> new ResourceNotFoundException(" CartItem Does Not Exist !!"));
		cartItemRepository.delete(cartItem);
	}
	
	//3. Get All CartItem by  One Cart
	@Override
	public PageableResponse<CartItemDto> getCartItemByCart(String cartId, Integer pageNumber, Integer pageSize,
			String sortBy, String sortDir) {
		Cart cart=cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart Does Not Exists !!"));
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?
				  (Sort.by(sortBy).descending()):
				  (Sort.by(sortBy).ascending());
	    Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
		Page<CartItem> page=cartItemRepository.getCartItemByCart(cart, pageable);
	    PageableResponse<CartItemDto> response=Helper.getPageableResponse(page, CartItemDto.class);
		return response;
	}
	
	//4. Get CartItem by CartItemId
	@Override
	public CartItemDto getCartItemByCartItemId(String cartItemId) {
		CartItem cartItem=cartItemRepository.findById(cartItemId).orElseThrow(()-> new ResourceNotFoundException("CartItem Does Not Exists !!"));
		CartItemDto cartItemDto=entityToDto(cartItem);
		return cartItemDto;
	}
	
	//5. Get All CartItem 
	@Override
	public PageableResponse<CartItemDto> getAllCartItem(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?
				  (Sort.by(sortBy).descending()):
				  (Sort.by(sortBy).ascending());
	    Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
	    Page<CartItem> page=cartItemRepository.findAll(pageable);
	    PageableResponse<CartItemDto> response=Helper.getPageableResponse(page, CartItemDto.class);
		return response;
	}
	
	//6. Convert Entity to Dto
	private CartItemDto entityToDto(CartItem cartItem) {
		return mapper.map(cartItem, CartItemDto.class);
	}

	//7. Convert Dto to Entity
	private CartItem dtoToEntity(CartItemDto cartItemDto) {	
		return mapper.map(cartItemDto,CartItem.class);
	}

	//9. Remove CartItem from Cart by Product
	@Override
	public void removeCartItemFromCartByProduct(String cartId,String productId) {
		Product product=productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException(" Product Does Not Exists !!"));
		Cart cart=cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException(" Cart Does Not Exists "));
		CartItem cartItem=cartItemRepository.getCartItemByCartAndProduct(cart, product);
		if(cartItem==null) {
			throw new ResourceNotFoundException(" CartItem Does Not Exists !!");
		}
		cartItemRepository.delete(cartItem);
	}

	//10. Get CartItem from Cart by product
	@Override
	public CartItemDto getCartItemFromCartByProduct(String cartId, String productId) {
		Product product=productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException(" Product Does Not Exists !!"));
		Cart cart=cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException(" Cart Does Not Exists "));
		CartItem cartItem=cartItemRepository.getCartItemByCartAndProduct(cart, product);
		if(cartItem==null) {
			throw new ResourceNotFoundException(" CartItem Does Not Exists !!");
		}
		return entityToDto(cartItem);
	}
}
