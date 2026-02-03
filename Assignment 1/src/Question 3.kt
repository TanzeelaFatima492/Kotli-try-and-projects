class Laptop(val brand:String, val price:Int, val processor:String){
    constructor():this("Dell",75000,"Intel i5")
    constructor(brand:String, price:Int):this(brand, price,"Intel i3")
    fun showSpecs() {
        println("Brand: $brand, Price: $price, Processor: $processor");
    }
};
fun main() {
    val laptop1 = Laptop();
    val laptop2 = Laptop("HP", 95000, "Ryzen 7");

    val laptop3 = Laptop("Lenovo", 68000);
    laptop1.showSpecs();
    laptop2.showSpecs();
    laptop3.showSpecs();
}