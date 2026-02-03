//not tore any data
interface Shape {
    fun computeArea() : Double  // no body
}
class Circle(val radius:Double) : Shape {
    override fun computeArea() = Math.PI * radius * radius
//overide in class must implement all functonality
}
fun main(){
    val c = Circle(3.0)
    println(c.computeArea());
}
