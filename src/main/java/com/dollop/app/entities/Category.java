package com.dollop.app.entities;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="categories")
public class Category {
    
	@Id
	@Column(name="category_id")
	private String categoryId;
	@Column(name="category_title",length = 60,nullable = false)
	private String title;
	@Column(name="category_desc",length = 500)
	private String description;
	
	@Column(name="category_cover_image")
	@ElementCollection
	@CollectionTable(name="coverImage",joinColumns=@JoinColumn(name="cover_image_fk"))
	@JsonIgnore
	private List<String> coverImage;
		
	@OneToMany(mappedBy="category",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Product> products=new ArrayList<>();
}
