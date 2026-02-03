class Car{
    var model=""
    var name=""
    var colour=""
    fun horn()
    {
        println("$model bol rha hai .Beep  Beep!")
    }
    fun carcolour(color:String){
        colour=color
        println("$colour bhot acha hai")
    }
}

fun main(){
    var car1=Car()
    car1.model="xyz"
    car1.horn()
    car1.carcolour("red")

    var car2=Car()
    car2.model="abc"
    car2.horn()
    car2.carcolour("blue")
}