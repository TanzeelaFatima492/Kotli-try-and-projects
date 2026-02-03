import android.speech.tts.TextToSpeech.Engine

abstract class vehicle{
     abstract  fun engine()  //without body function

     fun horn(){ //with body function
         println("Biiiii .....")
     }

 }

 class cars:vehicle(){ // extract the class

     override fun engine() {//because of fuction of parent is used
         println("Car Engine 100")
     }

 }

class bike:vehicle(){
    override fun engine() {
        println("Car Engine....")
    }
}

fun main(){
    val car=cars()
     // car.engine() //give error  Unresolved reference: engine
    car.horn()
}
