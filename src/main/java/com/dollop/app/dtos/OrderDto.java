package com.dollop.app.dtos;
import java.util.Date;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

	private String orderId;
	@Size(min=4,max=30,message="Order Status List must be 4 Character Long !!")
	@NotBlank(message="Order Status is Required !!")
	private String orderStatus;
	@Size(min=4,max=30,message="Payment Status List must be 4 Character Long !!")
	@NotBlank(message="Payment Status is Required !!")
	private String paymentStatus;
	@NotBlank(message="Order Amount is Required !!")
	private Double orderAmount;
	@Size(min=5,max=30,message="Billing Address List must be Five Character Long !!")
	@NotBlank(message="Billing Address  is Required !!")
	private String billingAddress;
	@Size(min=5,max=30,message="Billing Phone List must be Five Character Long !!")
	@NotBlank(message="Billing Phone is Required !!")
	private String billingphone;
	@Size(min=5,max=30,message="Billing Name List must be Five Character Long !!")
	@NotBlank(message="Billing Name is Required !!")
	private String billingname;
	@NotBlank(message="Order Date is Required !!")
	private Date orderDate;
	@NotBlank(message="Delivery Date is Required !!")
	private Date deliveredDate;	
	
}
