//only use to store data
//Data classes make your code much more concise!


data class Player(val name: String, val score: Int)

fun main(){
    val firstPlayer = Player("Lauren", 10)
    println(firstPlayer);
}