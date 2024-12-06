package com.dollop.app.entities;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="carts")
public class Cart {

	@Id
	private String cartId;
    private Date createdAt;
	
    @OneToOne
	private User user;
    
	@OneToMany(cascade = CascadeType.ALL,mappedBy="cart")
	private List<CartItem> cartItems=new ArrayList<>();
}
