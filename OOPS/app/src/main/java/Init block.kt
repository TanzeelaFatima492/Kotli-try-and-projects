class car(val model:String){


    var formattedodel=""  //Global variable

    //init block 
    init {

        formattedodel=model.uppercase()
        println("$formattedodel")
    }

}
fun main(){

    var Car1=car("yahoo")
}
