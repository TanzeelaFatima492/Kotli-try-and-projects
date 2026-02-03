fun main(){
    var circle=Circle();
    println(circle.area(6));
    println(circle.parameter(7));

//    var num=3;
//    println(num.Triple());

    println(3.Triple());
}
class Circle{
    fun area( var r:Int):Double{
        return 3.14*r*r;
    }
}
fun Circle.parameter(var r:Int):Double{
    return 2*r*3.14;
}

fun Int.Triple():Int{
    return this*this*this;
}
