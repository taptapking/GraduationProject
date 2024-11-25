package vn.edu.hust.formtest.data

sealed class Category (val category: String) {

    object Camcorder: Category("Camcorder")
    object Compact: Category("Compact")
    object DSLR: Category("DSLR")
    object Mirrorless: Category("Mirrorless")
    object Accessory: Category("Accessory")
}