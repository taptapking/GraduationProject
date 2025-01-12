package vn.edu.hust.formtest.data.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import vn.edu.hust.formtest.data.Address
import vn.edu.hust.formtest.data.CartProduct
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random.Default.nextLong

@Parcelize
data class Order(
    var userID: String,
    val orderStatus: String = "",
    val totalPrice: Float = 0f,
    val products: List<CartProduct> = emptyList(),
    val address: Address = Address(),
    val date: String = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(Date()),
    val orderId : Long = nextLong(0, 100_000_000_000) + totalPrice.toLong()
): Parcelable {
    constructor(): this("","",0f, emptyList(), Address(), "",0)
}