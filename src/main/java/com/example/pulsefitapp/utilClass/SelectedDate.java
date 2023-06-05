package com.example.pulsefitapp.utilClass;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Used in Profile page when user selects caledar date and Meals/Workouts for that date are retrieved/displayed
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SelectedDate {

    private String date;

    public SelectedDate(String date) {
        this.date = date;
    }

}