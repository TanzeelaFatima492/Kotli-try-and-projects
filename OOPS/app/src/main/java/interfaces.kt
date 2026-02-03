interface Shape {
    fun computeArea() : Double
}
class Circle(val radius:Double) : Shape {
    override fun computeArea() = Math.PI * radius * radius
}

fun main(){
    var circle=Circle(5)
    println(circle.computeArea())
}


