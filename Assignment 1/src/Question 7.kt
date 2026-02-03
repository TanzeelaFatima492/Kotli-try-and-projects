interface Playable {

    fun play()
}
class Guitar: Playable {
    override fun play() {
        println("Guitar:strum,thang,twang,zing");
        }
}
class Piano: Playable {
   override fun play() {
        println("Piano:tinkle,plink,clong,boom,crash");
        }
}
fun main() {
    var piano = Piano();
    var guitar = Guitar();
    guitar.play();
    piano.play();
}