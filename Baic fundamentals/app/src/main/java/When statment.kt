fun main(){
    var timeOfDay="Morning"
    
    when(timeOfDay) {
        "Morning" -> println("Eat breakfast")
        "Evening" -> println("Eat Lunch")
        "Night" -> println("Eat dinner")

        else -> println("You are on Fast")
    }
}