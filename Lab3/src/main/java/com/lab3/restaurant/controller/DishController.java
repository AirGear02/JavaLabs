package com.lab3.restaurant.controller;

import com.lab3.restaurant.dto.dish.DishDto;
import com.lab3.restaurant.service.dish.DishService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @RequestMapping("/")
    public String getAllUsers(Model model,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);

        Page<DishDto> dishPage = dishService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("dishPage", dishPage);
        int totalPages = dishPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = new ArrayList<>();
            if (currentPage > 1) {
                pageNumbers.add(currentPage - 1);
            }
            pageNumbers.add(currentPage);
            if (currentPage < totalPages) {
                pageNumbers.add(currentPage + 1);
            }
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "index";
    }
}
