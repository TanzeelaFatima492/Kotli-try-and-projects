sealed class Result{

    //for hold data use data class

    data class Success(val data: String):Result(){}
    data class Error(val msg:String):Result(){}

    //create on run time
    //object keyword -> object

    object Loading:Result()

    fun processResult(result: Result){
        when(result){
            is Result.Error-> println("Error aya hai ${result.msg}")
            is Result.Success-> {
                println("Congratulations ${result.data}")
            }
            Result.Loading-> println("Dondh rhy hain")

        }

    }

}

fun main(){
    val success= Result.Success("congr")
    val error=Result.Error("Fail")
    var loading=Result.Loading

    success.processResult(success)
    error.processResult(error)
    loading.processResult(loading)

}

// restrict give excat option not too many