open class Gphouse{

    var room=1
    var material="soil"

    open fun decribe(){
        println("GP: $room & $material")
    }
}

open class Phouse:Gphouse() {

    init {
        room=5
        material="concrete"
    }

    override fun decribe() {
        println("P: $room & $material")
    }

}


class  Chouse(var type:String):Phouse(){

    var types="modern"

    init {
        room=7
    }

    override fun decribe() {
        super.decribe()  //call the parent class function

        println("C: $room & ${types}l")
    }
}

fun main(){

//    var Ghouse=Gphouse()
//    Ghouse.decribe()
//
//    var p_house=Phouse()
//    p_house.decribe()

    var child=Chouse("modern")
    child.decribe()
}