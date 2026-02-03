class Cars{

    var model:String
    var color:String

    constructor(model:String)
    {
        this.model=model  //this represent global variable
        this.color="no color"
        print("Model wla constructor")
    }

    constructor(model: String,color: String)
    {
        this.model=model  //this represent global variable
        this.color= color
        print("Model  & color wla constructor")
    }


    fun hon(){
        println("$color , $model........")

    }
}

fun main(){
    val car1=Cars("Chal ba")
    car1.hon()
    val car2=Cars("bye"," blue")
    car2.hon()

}