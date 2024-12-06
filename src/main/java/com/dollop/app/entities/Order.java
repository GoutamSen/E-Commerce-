package com.dollop.app.entities;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name="orders")
public class Order {
 
	@Id
	@Column(name="order_id")
	private String orderId;
	@Column(name="order_status")
	private String orderStatus;
	@Column(name="order_payment_status")
	private String paymentStatus;
	@Column(name="order_amount")
	private Double orderAmount;
	@Column(name="order_billing_address")
	private String billingAddress;
	@Column(name="order_billing_phone")
	private String billingphone;
	@Column(name="order_billing_name")
	private String billingname;
	@Column(name="order_date")
	private Date orderDate;
	@Column(name="order_delivered_date")
	private Date deliveredDate;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
    
	@OneToMany(mappedBy="order", fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private List<OrderItem> orderItem=new ArrayList<>();
	
}
