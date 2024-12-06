package com.dollop.app.service.impl;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.dollop.app.dtos.OrderItemDto;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.entities.Order;
import com.dollop.app.entities.OrderItem;
import com.dollop.app.entities.Product;
import com.dollop.app.exceptions.ResourceNotFoundException;
import com.dollop.app.helper.Helper;
import com.dollop.app.repositories.OrderItemRepository;
import com.dollop.app.repositories.OrderRepository;
import com.dollop.app.repositories.ProductRepository;
import com.dollop.app.services.IOrderItemService;

@Service
public class OrderItemServiceImpl implements IOrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ModelMapper mapper;
	
	//1. Add OrderItem In Order
	@Override
	public OrderItemDto addOrderItemInOrder(OrderItemDto orderItemDto, String orderId, String productId) {
		Order order=orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order Does Not Exists !!"));
		Product product=productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Does Not Exists !!"));
		if(product.getLive()==false) {
			throw new ResourceNotFoundException("Product is Exists but It is Not Live !!");
		}
		OrderItem existingOrderItem=orderItemRepository.getOrderItemByOrderAndProduct(order, product);
        if(existingOrderItem!=null) {
        	throw new ResourceNotFoundException("Product All Ready Exists in this Order !!");
        }
        String orderItemId=UUID.randomUUID().toString();
        orderItemDto.setOrdetItemId(orderItemId);
        Double totalPrice=orderItemDto.getQuantity()*(product.getPrice()/product.getQuantity()-product.getDiscountedPrice()/product.getQuantity());     		
        orderItemDto.setTotalPrice(totalPrice);
        order.setOrderAmount(totalPrice+order.getOrderAmount());
        orderRepository.save(order);    
        
        OrderItem orderItem=dtoToEntity(orderItemDto);
        orderItem.setOrder(order);      
        orderItem.setProduct(product);
        OrderItem savedOrderItem=orderItemRepository.save(orderItem);
        return entityToDto(savedOrderItem);	
	}

	private OrderItemDto entityToDto(OrderItem orderItem) {
		return mapper.map(orderItem, OrderItemDto.class);
	}

	private OrderItem dtoToEntity(OrderItemDto orderItemDto) {
		return mapper.map(orderItemDto, OrderItem.class);
	}

	//2. Get OrderItem by OrderItemId
	@Override
	public OrderItemDto getOrderItemByOrderItemId(String orderItemId) {
		OrderItem orderItem=orderItemRepository.findById(orderItemId).orElseThrow(()-> new ResourceNotFoundException(" OrderItem Does Not Exists !!"));
		return entityToDto(orderItem);
	}
	
	//3. Get All OrderItem
	@Override
	public PageableResponse<OrderItemDto> getAllOrderItems(Integer pageNumber, Integer pageSize, String sortBy,
			String sortDir) {
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?
				  (Sort.by(sortBy).descending()):
				  (Sort.by(sortBy).ascending());
	    Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
	    Page<OrderItem> page=orderItemRepository.findAll(pageable);
	    PageableResponse<OrderItemDto> response=Helper.getPageableResponse(page, OrderItemDto.class);
		return response;
	}
	
	//4. Get All OrderItem in One Order
	@Override
	public PageableResponse<OrderItemDto> GetAllOrderItemInOrder(String orderId, Integer pageNumber, Integer pageSize,
			String sortBy, String sortDir) {
		Order order=orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException(" Order Does Not Exists !!"));
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?
				   (Sort.by(sortBy).ascending()):
				   (Sort.by(sortBy).descending());
        Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
        Page<OrderItem> page=orderItemRepository.getOrderItemByOrder(order, pageable);
        PageableResponse<OrderItemDto> response=Helper.getPageableResponse(page, OrderItemDto.class);
		return response;
	}

	//5. Get OrderItem From Order by Product
	@Override
	public OrderItemDto getOrderItemFromOrderByProduct(String orderId, String productId) {
		Product product=productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException(" Product Does Not Exists !!"));
        Order order=orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order"));
		OrderItem orderItem=orderItemRepository.getOrderItemByOrderAndProduct(order, product);
		if(orderItem==null) {
			throw new ResourceNotFoundException("Product Does Not Exists in this Order !!");
		}
        return entityToDto(orderItem);
	}

	 //6. Remove OrderItem From Order
	@Override
	public void removeOrderItemFromOrder(String orderItemId) {
		OrderItem orderItem=orderItemRepository.findById(orderItemId).orElseThrow(()-> new ResourceNotFoundException("OrderItem Does Not Exists !!"));
		orderItemRepository.delete(orderItem);
	}

	 //7. Remove OrderItem From Order by Product
	@Override
	public void removeOrderItemFromOrderByProduct(String orderId, String productId) {
		Order order=orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order Does Not Exists !!"));
        Product product=productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product Does Not Exists !!"));
        OrderItem orderItem=orderItemRepository.getOrderItemByOrderAndProduct(order, product);
	    if(orderItem==null) {
	    	throw new ResourceNotFoundException(" Product Does Not Exists for this Order !!");
	    }
	    orderItemRepository.delete(orderItem);
	}
	

}
