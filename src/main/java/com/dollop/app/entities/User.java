package com.dollop.app.entities;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
@Table(name="userstable")
public class User {

    @Id	
    @Column(name="user_id")
	private String userId;
    @Column(name="user_name")
	private String name;
    @Column(name="user_email",unique=true)
    private String email;
    @Column(name="user_password",length=50)
	private String password;
    @Column(name="user_gender",length=6)
	private String gender;
    @Column(name="user_about",length=1000)
	private String about;
    
    @Column(name="user_image_name")
    @ElementCollection
    @CollectionTable(name="imageName",joinColumns=@JoinColumn(name="image_name_fk"))
    @JsonIgnore
	private List<String> imageName;
      
    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY,mappedBy="user")
    private List<Order> orders=new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Role> roles=new HashSet<>();
    
    @OneToOne(mappedBy="user",cascade = CascadeType.REMOVE)
    private Cart cart;
}
