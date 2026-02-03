fun calculateBill(itemName:String,price:Double,quantity:Int=1,discount: Double = 0.0, onBillGenerated: (Double) -> Unit) {
    val total = (price * quantity) - discount;
    onBillGenerated(total);
}

fun main() {
    calculateBill(itemName = "Burger",price = 500.0,quantity = 2,discount = 50.0) {
            total -> println("The total bill for Burger is Rs. $total");
    }
    calculateBill("Fries", 250.0) {
            total -> println("The total bill for Fries is Rs. $total");
    }
}