//If you’re certain a variable won’t be null, use !! to force the variable
//into a non-null type. Then you can call methods/properties on it.

fun main(){
    var s=arrayOf("red","blue","green")
    val len = s!!.Length
}

