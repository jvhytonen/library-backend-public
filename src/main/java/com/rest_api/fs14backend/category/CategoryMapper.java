package com.rest_api.fs14backend.category;

import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public static Category newCategory(CategoryDTO category){
        return new Category(
                category.getName());
    }
}
