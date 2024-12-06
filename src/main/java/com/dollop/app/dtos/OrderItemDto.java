package com.dollop.app.dtos;
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
public class OrderItemDto {

	private String ordetItemId;
	@Size(min=5,max=30,message="Description List must be Five Character Long  !!")
	@NotBlank(message="Description is Required !!")
	private Integer quantity;
	@NotBlank(message="Total Price is Required !!")
	private Double totalPrice;
}
