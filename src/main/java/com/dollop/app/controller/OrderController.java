package com.dollop.app.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dollop.app.dtos.OrderDto;
import com.dollop.app.dtos.PageableResponse;
import com.dollop.app.services.IOrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private IOrderService orderService;
	
	//1. Create Order with User
	@PostMapping("/create/{userId}")
	public ResponseEntity<OrderDto> createOrder(@RequestParam String userId,@RequestBody OrderDto orderDto){
		return new ResponseEntity<OrderDto>(orderService.createOrder(userId, orderDto),HttpStatus.CREATED);
	}
	
	//2. Update Order with User
	@PutMapping("/update/{userId}")
	public ResponseEntity<OrderDto> updateOrder(@RequestParam String userId,@RequestBody OrderDto orderDto){
		return new ResponseEntity<OrderDto>(orderService.updateOrder(userId, orderDto),HttpStatus.OK);
	}
	
	//3. Get Order by OrderId
	@GetMapping("/gets/{orderId}")
	public ResponseEntity<OrderDto> getOrderByOrderId(@RequestParam String orderId){
		return new ResponseEntity<OrderDto>(orderService.getOneOrder(orderId),HttpStatus.OK);
	}
	
	//4. Get Order by UserId
	@GetMapping("/get/{userId}")
	public ResponseEntity<OrderDto> getOrderByUserId(@RequestParam String userId){
		return new ResponseEntity<OrderDto>(orderService.getOrderByUser(userId),HttpStatus.OK);
	}
	
	//5. Get All Order
	@GetMapping("/getAll")
    public ResponseEntity<PageableResponse<OrderDto>> getAllOrder(
    		 @RequestParam(value="pageNumber",defaultValue="0",required=false)Integer pageNumber,
    		 @RequestParam(value="pageSize",defaultValue="10",required=false)Integer pageSize,
    		 @RequestParam(value="sortBy",defaultValue="name",required=false)String sortBy,
    		 @RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir	
    		   ){
    return new ResponseEntity<PageableResponse<OrderDto>>(orderService.getAllOrder(pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);
    }
    
    //6. Remove Order by OrderId
	@DeleteMapping("/removed/{orderId}")
    public ResponseEntity<String> removeOrderByOrderId(@RequestParam String orderId){
    	orderService.removeOrderByOrderId(orderId);
    	return new ResponseEntity<String>("Order Cancled !!",HttpStatus.OK);
    }
    
	//7. Remove Order by UserId
	@DeleteMapping("/remove/{userId}")
	public ResponseEntity<String> removeOrdedByUserId(@RequestParam String userId){
		orderService.removeOrderByUser(userId);
		return new ResponseEntity<String>("Order Cancled by User !!",HttpStatus.OK);
	}

}
