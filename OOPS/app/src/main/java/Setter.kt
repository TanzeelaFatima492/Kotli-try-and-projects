class Person(val firstName: String, val lastName:String) {
    var fullName:String = ""
        get() = "$firstName $lastName"
        set(value) {
            val components = value.split(" ")
            firstName = components[0]
            lastName = components[1]
            field = value
        }

}

fun main(){
    var person=Person()
    person.fullName = "Jane Smith"
}