class Bank{
    private var BankAccount:Int=0
    private var AccountName:String="Tanzeela"
//    private var AccountName:String=""

    public fun getdata(){
        if(AccountName.isEmpty())
        {
            print("Enter your name:")
            var name= readLine()?:""
            AccountName=name
        }
        println("Your account name is $AccountName")
    }
}

fun main(){
    var Bank1=Bank()
    Bank1.getdata()

}