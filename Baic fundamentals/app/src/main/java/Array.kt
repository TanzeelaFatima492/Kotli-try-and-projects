fun main(){
    var arr= arrayOf("Monday","Tuesday","Wednesday","thursday","Friday","Saturday","Sunday")

    println("${arr[0]}")
    println("${arr[1]}")
    println("${arr[2]}")
    println("${arr[3]}")
    println("${arr[4]}")
    println("${arr[5]}")
    println("${arr[6]}")

    println("")
    for (days in arr)
        println(days)
}