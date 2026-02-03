fun main(){

    println("\nContinue statement\n")

    for (i in 1..4) {
        println("i = $i")
        if(i==3)
            continue
        println("Skipping $i")
    }

    println("\nBreak statement\n")

    val names = listOf("Ali", "Sara", "Tanzeela", "Ahmed")
    for (name in names) {
        if (name == "Tanzeela") {
            println("Found it!")
            break
        }
        println("Checking $name")
    }
}
