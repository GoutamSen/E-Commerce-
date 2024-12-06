package com.dollop.app.services;
import com.dollop.app.dtos.OrderItemDto;
import com.dollop.app.dtos.PageableResponse;

public interface IOrderItemService {

	//1. Add OrderItem In Order
	OrderItemDto addOrderItemInOrder(OrderItemDto orderItemDto,String orderId,String productId);

	//2. Get OrderItem by OrderItemId
	OrderItemDto getOrderItemByOrderItemId(String orderItemId);
	
	//3. Get All OrderItem
	PageableResponse<OrderItemDto> getAllOrderItems(Integer  pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//4. Get All OrderItem in One Order
	PageableResponse<OrderItemDto> GetAllOrderItemInOrder(String orderId,Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	 //5. Get OrderItem From Order by Product
	 OrderItemDto getOrderItemFromOrderByProduct(String orderId,String productId);
	 
	 //6. Remove OrderItem From Order
	 void removeOrderItemFromOrder(String orderItemId);
	 
	 //7. Remove OrderItem From Order by Product
	 void removeOrderItemFromOrderByProduct(String orderId,String productId);
}
