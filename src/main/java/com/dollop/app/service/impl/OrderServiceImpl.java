package com.dollop.app.service.impl;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.dollop.app.dtos.OrderDto;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.entities.Order;
import com.dollop.app.entities.User;
import com.dollop.app.exceptions.ResourceNotFoundException;
import com.dollop.app.helper.Helper;
import com.dollop.app.repositories.OrderRepository;
import com.dollop.app.repositories.UserRepository;
import com.dollop.app.services.IOrderService;
import org.springframework.data.domain.Pageable;


@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper mapper;
	
	//1. Create Order with User
	@Override
	public OrderDto createOrder(String userId, OrderDto orderDto) {
		User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Does  Not Exists !!"));
		if(orderRepository.getOrderByUser(user)!=null) {
			throw new ResourceNotFoundException("This User's Order Already Exists !!");
		}
		String orderId=UUID.randomUUID().toString();
		orderDto.setOrderId(orderId);
		Order order=dtoToEntity(orderDto);
		order.setUser(user);
		Order savedOrder=orderRepository.save(order);
		OrderDto savedOrderDto=entityToDto(savedOrder);
		return savedOrderDto;
	}

	//2. Update Order with User
	@Override
	public OrderDto updateOrder(String userId, OrderDto orderDto) {
		User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Does Not Exists !!"));
//		Order existingOrder=orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException(" Order Does Not Exists !!"));
        Order existingOrder=orderRepository.getOrderByUser(user);
        existingOrder.setOrderId(existingOrder.getOrderId());
		existingOrder.setBillingAddress(orderDto.getBillingAddress());
		existingOrder.setBillingname(orderDto.getBillingname());
		existingOrder.setBillingphone(orderDto.getBillingphone());
		existingOrder.setDeliveredDate(orderDto.getDeliveredDate());
		existingOrder.setOrderAmount(orderDto.getOrderAmount());
		existingOrder.setOrderStatus(orderDto.getOrderStatus());
		existingOrder.setPaymentStatus(orderDto.getPaymentStatus());
		existingOrder.setUser(user);
	    Order updatedOrder=orderRepository.save(existingOrder);
	    OrderDto updatedOrderDto=entityToDto(updatedOrder);
		return updatedOrderDto;
	}

	//3. Get Order by OrderId
	@Override
	public OrderDto getOneOrder(String orderId) {
		Order order=orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order Does Not Exists !!"));
		OrderDto orderDto=entityToDto(order);
		return orderDto;
	}

	//4. Get Order by UserId
	@Override
	public OrderDto getOrderByUser(String userId) {
	  User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Does Not Exists !!"));
	  Order order=orderRepository.getOrderByUser(user);
      if(order==null) {
    	  throw new ResourceNotFoundException("Order Does Not Exist for this User");
      }
	  return entityToDto(order);
	   
	}

	//5. Get All Order
	@Override
	public PageableResponse<OrderDto> getAllOrder(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
	@SuppressWarnings("unused")
	Sort sort=(sortDir.equalsIgnoreCase("desc"))?
			  (Sort.by(sortBy).descending()):
			  (Sort.by(sortBy).ascending());
	Pageable pageable=PageRequest.of(pageNumber, pageSize);
	Page<Order> page=orderRepository.findAll(pageable);
	PageableResponse<OrderDto> response=Helper.getPageableResponse(page, OrderDto.class);
    return response;
	}

	//6. Remove Order by OrderId
	@Override
	public void removeOrderByOrderId(String orderId) {
      Order order=orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order Does Not Exists !!"));
	  orderRepository.delete(order);
	}

	//7. Remove Order by UserId
	@Override
	public void removeOrderByUser(String userId) {
	 User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Does Not Exists !!"));
     Order order=orderRepository.getOrderByUser(user);
     orderRepository.delete(order);
	}
	
	//8. Convert Dto to Entity
	private Order dtoToEntity(OrderDto orderDto) {
		return mapper.map(orderDto, Order.class);
	}
	
	
	//9. Convert Entity to Dto
	private OrderDto entityToDto(Order order) {
		return mapper.map(order, OrderDto.class);
	}
}
