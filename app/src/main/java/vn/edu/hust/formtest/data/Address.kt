package vn.edu.hust.formtest.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val addressTitle: String,
    val fullName: String,
    val street: String,
    val phone: String,
    val city: String,
    val district: String
): Parcelable {
    constructor(): this("","","","","","")
}
