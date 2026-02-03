class Book{
    var title= "Android Development"
    var author="Sir tayyab"
    fun displayInfo(){
        println("This is $title written $author")
    }
}
fun main(){
    val book=Book()
    book.displayInfo()
}