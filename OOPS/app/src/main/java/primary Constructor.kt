class Class(val name:String,var rollnumber:Int){

    fun introduce(){
        println("My name $name or roll number $rollnumber")
    }
}

fun main(){
    var student1= Class("Tanzeela",12)
    student1.introduce()

    var student2= Class("Fatima",2)
    student2.introduce()
}
