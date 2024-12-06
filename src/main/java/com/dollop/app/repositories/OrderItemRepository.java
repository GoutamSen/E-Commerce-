package com.dollop.app.repositories;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dollop.app.entities.Order;
import com.dollop.app.entities.OrderItem;
import com.dollop.app.entities.Product;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

	Page<OrderItem> getOrderItemByOrder(Order order,Pageable pageable);
	List<OrderItem> getOrderItemsByOrder(Order order);
	OrderItem getOrderItemByOrderAndProduct(Order order,Product product);
}
