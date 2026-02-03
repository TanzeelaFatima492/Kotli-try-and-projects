abstract class Shape {
    abstract fun area()
    open fun printShapeName() {}
}
class Circle : Shape() {
    override fun printShapeName() {
        println("Circle");
        }
    override fun area() {
        print("Enter radius of circle:");
        val radius = readLine()!!.toInt();
        println("Area of Circle: ${3.14* radius * radius}");
        }
}
class Rectangle : Shape() {
    override fun printShapeName() {
    println("Rectangle");
   }
    override fun area() {
        print("Enter length of rectangle:");
        val length = readLine()!!.toInt();
        print("Enter width of rectangle:");
        val width = readLine()!!.toInt();
        println("Area of Rectangle: ${length * width}");
        }
}
fun main() {
    val circle = Circle();
    circle.printShapeName();
    circle.area();
     val rectangle = Rectangle();
    rectangle.printShapeName();
    rectangle.area();
}