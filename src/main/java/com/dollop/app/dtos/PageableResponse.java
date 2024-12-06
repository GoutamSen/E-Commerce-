package com.dollop.app.dtos;
import java.util.List;

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
public class PageableResponse<T> {

	private List<T> content;
	@NotBlank(message="PageNumber is Required !!")
	private int pageNumber;
	@NotBlank(message="PageSize is Required !!")
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean lastPage;
}
