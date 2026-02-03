fun main(){
    val marks=calculateMarks(20,30,56)
    println("Your marks is $marks")
    orderfood("Samosa",2)
}

fun calculateMarks(a:Int, b:Int, c:Int): Int {
    val total=a+b+c
    return total
}

fun orderfood(item:String,quatity:Int){
    println("Your order is : $item  $quatity")
}
