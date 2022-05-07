package com.lab3.restaurant.service.dish;

import com.lab3.restaurant.dao.DishRepository;
import com.lab3.restaurant.dto.dish.DishDto;
import com.lab3.restaurant.model.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;

    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public Page<DishDto> findPaginated(Pageable pageable) {
        List<DishEntity> dishes = new ArrayList<>(dishRepository.findAll());
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<DishDto> list;
        if (dishes.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, dishes.size());
            list = dishes.subList(startItem, toIndex).stream().map(dish -> new DishDto(dish.getId(),
                    dish.getTitle(), dish.getDescription(), dish.getPhotoPath())).toList();
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), dishes.size());

    }
}
