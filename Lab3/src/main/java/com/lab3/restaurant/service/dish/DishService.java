package com.lab3.restaurant.service.dish;

import com.lab3.restaurant.dto.dish.DishDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DishService {
    public Page<DishDto> findPaginated(Pageable pageable);
}
