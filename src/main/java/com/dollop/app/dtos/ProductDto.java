package com.dollop.app.dtos;
import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
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
public class ProductDto {
	
	private String productId;
	@Size(min=3,max=30,message="Title List must be Five Character Long !!")
	@NotBlank(message="Title is Required !!")
	private String title;	
	@Size(min=5,max=30,message="Description List must be Five Character Long !!")
	@NotBlank(message="Description is Required !!")
	private String description;	
//	@NotBlank(message="Price is Required !!")
	private Double price;
//	@NotBlank(message="Discounted Price is Required !!")
	private Double discountedPrice;	
//	@NotBlank(message="Quantity is Required !!")
	private Integer quantity;	
	@CreationTimestamp
	private Timestamp addedDate;	
	private boolean live;	
	private boolean stock;
	private String productImageName;
	
}
