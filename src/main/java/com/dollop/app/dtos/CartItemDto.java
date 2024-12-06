package com.dollop.app.dtos;
import jakarta.validation.constraints.NotBlank;
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
public class CartItemDto {

	private String cartItemId;
	@NotBlank(message="Quantity is Required !!")
	private Double quantity;
	@NotBlank(message="Total Price is Required !!")
	private Double totalPrice;	

}
