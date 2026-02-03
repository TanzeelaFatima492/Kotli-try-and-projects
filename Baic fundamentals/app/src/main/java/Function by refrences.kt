fun modifyByReference(numbers: IntArray) {
    numbers[0] = numbers[0] + 10
    println("Inside function: ${numbers[0]}")
}

fun main() {
    val arr = intArrayOf(5)
    println("Before function call: ${arr[0]}")
    modifyByReference(arr)
    println("After function call: ${arr[0]}")
}
