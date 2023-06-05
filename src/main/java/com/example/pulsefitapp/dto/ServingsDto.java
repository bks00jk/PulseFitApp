package com.example.pulsefitapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ServingsDto {
    private Float number;
    private String food;

    public ServingsDto(Float number, String food){
        this.number = number;
        this.food = food;
    }
}
