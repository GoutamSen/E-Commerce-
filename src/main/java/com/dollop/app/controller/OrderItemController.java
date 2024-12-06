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
import com.dollop.app.dtos.OrderItemDto;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.services.IOrderItemService;

@RestController
@RequestMapping("/orderItems")
public class OrderItemController {

	@Autowired
	private IOrderItemService orderItemService;
	
	
	//1. Add OrderItem In Order
	@PostMapping("/add")
    public ResponseEntity<OrderItemDto> addOrderItem(@RequestBody OrderItemDto orderItemDto ,@RequestParam String orderId,@RequestParam String productId){
    	return new ResponseEntity<OrderItemDto>(orderItemService.addOrderItemInOrder(orderItemDto, orderId, productId),HttpStatus.CREATED);
    }
	
	//2. Get OrderItem by OrderItemId
	@GetMapping("/get/{orderItemId}")
    public ResponseEntity<OrderItemDto> getOrderItemByOrderItemId(@RequestParam String orderItemId){
    	return new ResponseEntity<OrderItemDto>(orderItemService.getOrderItemByOrderItemId(orderItemId),HttpStatus.OK);
    }
    
	//3. Get All OrderItems
	@GetMapping("/getAll")
	public ResponseEntity<PageableResponse<OrderItemDto>> getAllOrderItems(
			 @RequestParam(value="pageNumber",defaultValue="0",required=false)Integer pageNumber,
			 @RequestParam(value="pageSize",defaultValue="10",required=false)Integer pageSize,
			 @RequestParam(value="sortBy",defaultValue="name",required=false)String sortBy,
			 @RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir	){
		return new ResponseEntity<PageableResponse<OrderItemDto>>(orderItemService.getAllOrderItems(pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
	}
	
	//4. Get All OrderItem in One Order
	@GetMapping("/getAll/{orderId}")
     public ResponseEntity<PageableResponse<OrderItemDto>> GetAllOrderItemInOrder(
    		 @RequestParam String orderId,
			 @RequestParam(value="pageNumber",defaultValue="0",required=false)Integer pageNumber,
			 @RequestParam(value="pageSize",defaultValue="10",required=false)Integer pageSize,
			 @RequestParam(value="sortBy",defaultValue="name",required=false)String sortBy,
			 @RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir	
    		 ){
    	 return new ResponseEntity<PageableResponse<OrderItemDto>>(orderItemService.GetAllOrderItemInOrder(orderId, pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
     }
	
	//5. Get OrderItem From Order by Product
	@GetMapping("/get/{orderId}/{productId}")
	public ResponseEntity<OrderItemDto> getOrderItemFromOrderByProduct(@RequestParam String orderId,@RequestParam String productId){
		return new ResponseEntity<OrderItemDto>(orderItemService.getOrderItemFromOrderByProduct(orderId, productId),HttpStatus.OK);
	}
	
	 //6. Remove OrderItem From Order
	 @DeleteMapping("/removed/{orderItem}")
     public ResponseEntity<ApiResponseMessage> removeOrderItemFromOrder(@RequestParam String orderItemId){
    	 orderItemService.removeOrderItemFromOrder(orderItemId);
    	 ApiResponseMessage response=ApiResponseMessage.builder()
    			                     .message("OrderItem is Deleted !!")
    			                     .success(true)
    			                     .status(HttpStatus.OK)
    			                     .build();
    	 return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.OK);
     }
	
	 //7. Remove OrderItem From Order by Product
	 @DeleteMapping("/remove/{orderId}/{productId}")
      public ResponseEntity<ApiResponseMessage> removeOrderItemFromOrderByProduct(@RequestParam String orderId, @RequestParam String productId){
      orderItemService.removeOrderItemFromOrderByProduct(orderId, productId);
      ApiResponseMessage response=ApiResponseMessage.builder()
                                  .message("OrderItem is Deleted !!")
                                  .success(true)
                                  .status(HttpStatus.OK)
                                  .build();
      return new ResponseEntity<ApiResponseMessage>(response,HttpStatus.OK);  		  
      }
}
