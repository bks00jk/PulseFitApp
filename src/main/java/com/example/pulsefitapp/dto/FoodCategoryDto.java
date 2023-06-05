package com.example.pulsefitapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    @NoArgsConstructor
    @Getter
    @Setter
    public class FoodCategoryDto {

        private String name;
        private String description;

        public FoodCategoryDto(String name, String description) {
            this.name = name;
            this.description = description;
        }

    }

