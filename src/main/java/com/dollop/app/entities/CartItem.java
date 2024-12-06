package com.dollop.app.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="cartitems")
public class CartItem {
 
	@Id
	@Column(name="cartitem_id")
	private String cartItemId;
	@Column(name="cartitem_quantity")
	private Double quantity;
	@Column(name="cartitem_total_prize")
	private Double totalPrize;
	
	@OneToOne
	@JoinColumn(name="product_Id")
	private Product product;
	
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="cart_Id")
	private Cart cart;
	
}
