package com.dollop.app.services;
import com.dollop.app.dtos.OrderDto;
import com.dollop.app.dtos.PageableResponse;

public interface IOrderService {

	//1. Create Order with User
	OrderDto createOrder(String userId,OrderDto orderDto);
	
	//2. Update Order with User
	OrderDto updateOrder(String userId,OrderDto orderDto);
	
	//3. Get Order by OrderId
	OrderDto getOneOrder(String orderId);
	
	//4. Get Order by UserId
	OrderDto getOrderByUser(String userId);
	
	//5. Get All Order
	PageableResponse<OrderDto> getAllOrder(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//6. Remove Order by OrderId
	void removeOrderByOrderId(String orderId);
	
	//7. Remove Order by UserId
	void removeOrderByUser(String userId);
	
}
