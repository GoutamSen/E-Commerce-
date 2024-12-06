package com.dollop.app.dtos;
import java.util.Date;
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
public class CartDto {

	private String cartId;
	@NotBlank(message="Created Date is Required !!")
    private Date createdAt;
}
