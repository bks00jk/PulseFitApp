//GetMapping to "/" checks if User already created a Health Profile for today.
// "dailyHp" attribute "Click Below to Create a Meal or Workout" is added to model HP for today is found.
// Initially hidden "Create a Meal" and "Create a Workout" buttons are then displayed.
if(window.location.pathname === "/") {
    const createMeal = document.getElementById("createMeal")
    const createWorkout = document.getElementById("createWorkout")
    const dailyHp = document.getElementById("dailyHp")
  
    if(dailyHp.innerText == "Click Below to Create a Meal or Workout"){
        createMeal.classList.remove("hidden")
        createWorkout.classList.remove("hidden")
    }

}


// only want "load" event listener to be added and call addServingsCalories if on /servings page
if(window.location.pathname === "/servings") {
window.addEventListener("load", () => {
    addServingsCaloriesMacros();
})
}

// After each Serving created, page refreshes and this
// automatically takes Calories, Protein, Fat, Carbs from each Serving and calculates
// Total calories, protein, fat, carbs for current Meal.
const addServingsCaloriesMacros = () => {
    const servingsCals = document.querySelectorAll('#totalCals')
    const servingsProtein = document.querySelectorAll('#totalProtein')
    const servingsFat = document.querySelectorAll('#totalFat')
    const servingsCarbs = document.querySelectorAll('#totalCarbs')

    const mealCals = document.getElementById('mealCals')
    const mealProtein = document.getElementById('mealProtein')
    const mealFat = document.getElementById('mealFat')
    const mealCarbs = document.getElementById('mealCarbs')

    let totalMealCals = 0
    let totalMealProtein = 0
    let totalMealFat = 0
    let totalMealCarbs = 0

    servingsCals.forEach(s => totalMealCals += Number(s.innerText));
    servingsProtein.forEach(s => totalMealProtein += Number(s.innerText));
    servingsFat.forEach(s => totalMealFat += Number(s.innerText));
    servingsCarbs.forEach(s => totalMealCarbs += Number(s.innerText));

    mealCals.value = totalMealCals
    mealProtein.value = totalMealProtein
    mealFat.value = totalMealFat
    mealCarbs.value = totalMealCarbs

}

// Gives user option to click button and auto input current date/time for Meal
const currentDateTime = () => {
  const month = document.getElementById('month')
  const day = document.getElementById('day')
  const year = document.getElementById('year')
  const hour = document.getElementById('hour')
  const minute = document.getElementById('minute')
  const dayNight = document.getElementById('dayNight')

  const currentDate = new Date();
  const cDay = currentDate.getDate()
  const cMonth = currentDate.getMonth() + 1
  const cYear = currentDate.getFullYear()
  let cHours = currentDate.getHours()
  let cMinutes = currentDate.getMinutes()
  let cDayNight;

  cHours >= 12 ? cDayNight = "PM" : cDayNight = "AM"
  cHours > 12 ? cHours -= 12 : cHours = cHours
  cMinutes <= 9 ? cMinutes = "0" + cMinutes : cMinutes = cMinutes

  month.value = cMonth;
  day.value = cDay;
  year.value = cYear;
  hour.value = cHours;
  minute.value = cMinutes;
  dayNight.value = cDayNight;

}

// gives User option to click a button and auto input current date for the Workout
const currentDate = () => {
  const month = document.getElementById('month')
  const day = document.getElementById('day')
  const year = document.getElementById('year')

  const currentDate = new Date();
  const cDay = currentDate.getDate()
  const cMonth = currentDate.getMonth() + 1
  const cYear = currentDate.getFullYear()

  month.value = cMonth;
  day.value = cDay;
  year.value = cYear;

}

// Calculates Target Calories amount in Daily Health Profile Form
const calorieCalculator = () => {
  const weight = document.getElementById('calcWeight').value
  const height = document.getElementById('calcHeight').value
  const age = document.getElementById('calcAge').value

  const exerciseLevel = document.getElementById('exerciseLevel').value

  if(exerciseLevel == "null"){
    alert("Please choose Exercise Level for Calorie Calculator")
  }
  if(weight == ""){
      alert("Please enter Weight for Calorie Calculator")
    }
  if(height == null){
      alert("Please enter Height for Calorie Calculator")
    }
    if(age == null){
        alert("Please choose Age for Calorie Calculator")
      }

  const male= document.getElementById('male').checked
  const female= document.getElementById('female').checked

  const lose= document.getElementById('Lose').checked
  const maintain = document.getElementById('Maintain').checked
  const gain = document.getElementById('Gain').checked

  const targetCalories = document.getElementById('targetCalories')
  let calories;

  if(male){
    calories = exerciseLevel * (66 + (13.75 * weight) + (5 * height) - (6.75 * age))
    console.log("male")
  } else if(female){
    calories = exerciseLevel * (655 + (9.56 * weight) + (1.85 * height) - (4.68 * age))
      console.log("female")
    } else {
     alert("Please choose Gender for Calorie Calculator")
     calories = 0
    }

  if(lose){
    calories -= 500
    console.log("lose")
  } else if(gain){
    calories += 500
    console.log("gain")
  } else if(maintain) {
    calories = calories
    console.log("maintain")
  } else {
     alert("Please choose a Weight Goal for Calorie Calculator")
  }


  targetCalories.value = Math.round(calories)

}


// Search/filter food items in Add Serving Form
if(window.location.pathname === "/servings"){
let search = document.querySelector("#food-search");
  search.addEventListener("click", () => {

    let li = Array.from(document.querySelectorAll(".food"));

    search.addEventListener("keyup", () => {
      value = search.value.toLowerCase();
      for (i = 0; i < li.length; i++) {
        txtAttrib = li[i].value;
        if (txtAttrib.toLowerCase().indexOf(value) > -1) {
          li[i].style.display = "";
        } else {
          li[i].style.display = "none";
        }
      }
    });
  });
}


// Search/filter exercise names is Add Sets Form
if(window.location.pathname === "/sets"){
let search1 = document.querySelector("#exercise-search");
  search1.addEventListener("click", () => {

    let li1 = Array.from(document.querySelectorAll(".exercise"));

    search1.addEventListener("keyup", () => {
      value = search1.value.toLowerCase();
      for (i = 0; i < li1.length; i++) {
        txtAttrib = li1[i].value;
        if (txtAttrib.toLowerCase().indexOf(value) > -1) {
          li1[i].style.display = "";
        } else {
          li1[i].style.display = "none";
        }
      }
    });
  });
}

