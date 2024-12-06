package com.dollop.app.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dollop.app.entities.Order;
import com.dollop.app.entities.User;

public interface OrderRepository extends JpaRepository<Order, String> {

	Order getOrderByUser(User user);
}
