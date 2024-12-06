package com.dollop.app.entities;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="products")
public class Product {

	@Id
	@Column(name="product_id")
	private String productId;
	@Column(name="product_title")
	private String title;
	@Column(name="product_desc",length=1000)
	private String description;
	@Column(name="product_price")
	private Double price;
	@Column(name="product_discountedPrice")
	private Double discountedPrice;
	@Column(name="product_countity")
	private Integer quantity;
	@CreationTimestamp
	private Date addedDate;
	@Column(name="product_live")
	private Boolean live;
	@Column(name="product_stock")
	private Boolean stock;
	@Column(name="product_image_name")
	private String productImageName;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="category_Id")
	private Category category;
	
}
