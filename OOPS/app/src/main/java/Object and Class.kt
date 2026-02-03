class Student{
    var name:String = ""
    var rollnumber:Int = 0

    fun  DoMore(){
        println("Do Code Until Your computer shutdown")
    }

    fun Introduce(){
        println("My name is $name and roll number is $rollnumber")
    }
}

fun main(){

    var student1=Student()
    student1.DoMore()
    student1.name="Fatima"
    student1.rollnumber=12
    println("${student1.name}")
    println("${student1.rollnumber}")
    student1.Introduce()
    
    var student2=Student()
    student2.name="Tanzeela"
    student2.rollnumber=20
    println("${student2.name}")
    println("${student2.rollnumber}")
    student1.Introduce()


}