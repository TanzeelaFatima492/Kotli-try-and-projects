class Person(val firstName: String, val lastName:String) {
    val fullName:String
        get() {
            return "$firstName $lastName"
        }
}

fun main() {
    println("Using Getter");
    val person = Person("John", "Doe")
    println(person.fullName)
//    => John Doe

}

