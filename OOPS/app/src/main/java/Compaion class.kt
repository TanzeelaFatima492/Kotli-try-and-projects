// Lets all instances of a class share a single instance of a set of
// variables or functions


class PhysicsSystem {
    companion object WorldConstants {
        val gravity = 9.8;
        val unit = "metric";
        fun computeForce(mass: Double, accel: Double): Double {
            return mass * accel;
        }
    }
}
fun main(){
    println(PhysicsSystem.WorldConstants.gravity);
    println(PhysicsSystem.WorldConstants.computeForce(10.0, 10.0));
}
