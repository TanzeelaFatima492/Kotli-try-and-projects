
open class Animal{
     open fun makesound(){  //open keyword for inheritance
        println("koe awaz nhi")
    }

}
class Dog:Animal(){

    override fun makesound(){
        println("Boww bowww")
    }

}
class Cat:Animal(){

    override fun makesound() {
        println("Meow Meow")
    }

}
//polymorphsim perform tb ho ga jb runtime py pta chly ga

fun main(){
    val animal1:Animal=Dog() // define a datatype of class to store data
    val animal2:Animal=Cat() // beacuse we use polymorphsim

    animal1.makesound()
    animal2.makesound()

}