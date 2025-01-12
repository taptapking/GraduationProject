package vn.edu.hust.formtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import vn.edu.hust.formtest.data.Product
import vn.edu.hust.formtest.util.Resource
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
): ViewModel() {
    private val _Products = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val Products: StateFlow<Resource<List<Product>>> = _Products

    private val pagingInfo = PagingInfo1()

    fun fetchProducts(query: String){
        if (query.isEmpty()) {
            viewModelScope.launch {
                _Products.emit(Resource.Error("Query string cannot be empty"))
            }
            return
        }
        if (!pagingInfo.isPagingEnd) {
            viewModelScope.launch {
                _Products.emit(Resource.Loading())
            }
            firestore.collection("Products").limit(pagingInfo.ProductsPage * 10)
                .orderBy("name") // The field to be searched
                .whereGreaterThanOrEqualTo("name", query) // Start matching from the query
                .whereLessThanOrEqualTo("name", query + "\uf8ff")
                .get()
                .addOnSuccessListener { result ->
                    val Products = result.toObjects(Product::class.java)
                    pagingInfo.isPagingEnd = Products == pagingInfo.oldProducts
                    pagingInfo.oldProducts = Products
                    viewModelScope.launch {
                        _Products.emit(Resource.Success(Products))
                    }
                    pagingInfo.ProductsPage++
                }
                .addOnFailureListener {
                    viewModelScope.launch {
                        _Products.emit(Resource.Error(it.message.toString()))
                    }
                }
        }
    }
}

internal data class PagingInfo1(
    var ProductsPage: Long = 1,
    var oldProducts: List<Product> = emptyList(),
    var isPagingEnd: Boolean = false
)