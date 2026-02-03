open class Vehicle {
    var brand: String = "";
    var speed: Int = 0;
    fun displayVehicleInfo() {
    println("Brand: $brand, Speed: $speed km/h");
    }
};
class Car : Vehicle() {
    var type: String = "";
    fun displayInfo() {
        println("Brand: $brand, Speed: $speed km/h, Type: $type");
     }
};
fun main() {
   val car = Car();
    car.brand= "Toyota";
    car.speed=180;
    car.type= "Sedan";
    car.displayInfo();
}