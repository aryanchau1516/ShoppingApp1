data class UserModel(
    val  name: String="",
    val email: String="",
    val uid : String="",
    val address: String="",
    val cartItems : Map<String, Long> = mapOf()
)
