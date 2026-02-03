fun main(){
    greet()
    greet("Tanzeela")
    greet("Tanzeela","Fatima")
}

fun  greet(){
    println("Hello")
}

fun greet(name:String){
    println("Hello $name")
}

fun greet(fname:String,lname:String){
    println("Hello $fname $lname")
}