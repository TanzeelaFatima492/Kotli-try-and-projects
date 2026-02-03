//enum class ko hold nhi kr skty object mn

enum class Traffic(var action:String) {
    RED("Ruk jao"),
    YELLOW("Ready"),
    GREEN("Jao");

    fun getinstruction(): String {
        return "Traffic light hai $action"
    }

}

    fun processLight(light: Traffic) {
        when (light) {
            Traffic.RED -> println(light.getinstruction())
            Traffic.YELLOW -> println(light.getinstruction())
            Traffic.GREEN -> println(light.getinstruction())
        }
    }

    fun main(){
        val red=Traffic.RED
        val yellow=Traffic.YELLOW
        val green=Traffic.GREEN

        processLight(red)
        processLight(yellow)
        processLight(green)
    }

