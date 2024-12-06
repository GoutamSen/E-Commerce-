package com.dollop.app.repositories;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.dollop.app.entities.Order;
import com.dollop.app.entities.User;

public interface UserRepository extends JpaRepository<User, String> ,PagingAndSortingRepository<User, String>{
 
	List<User> findByNameContaining(String keyword);
	User getUserByOrders(Order order);
	User getUserByEmail(String email);
	User getUserByEmailAndPassword(String email,String password);
	
}
