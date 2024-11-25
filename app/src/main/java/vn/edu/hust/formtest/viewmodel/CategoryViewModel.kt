package vn.edu.hust.formtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.edu.hust.formtest.data.Category
import vn.edu.hust.formtest.data.Product
import vn.edu.hust.formtest.util.Resource

class CategoryViewModel constructor(
    private val firestore: FirebaseFirestore,
    private val category: Category
): ViewModel() {
    private val _offerProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val offerProducts = _offerProducts.asStateFlow()

    private val _bestProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestProducts = _bestProducts.asStateFlow()

    private val pagingInfo = PagingInfo()

    init {
        fetchOfferProducts()
        fetchBestProducts()
    }

    fun fetchOfferProducts(){
        viewModelScope.launch {
            _offerProducts.emit(Resource.Loading())
        }
        firestore.collection("Products").whereEqualTo("category",category.category)
            .whereNotEqualTo("offerPercentage",null)
            .get()
            .addOnSuccessListener {
                val products = it.toObjects(Product::class.java)
                viewModelScope.launch{
                    _offerProducts.emit(Resource.Success(products))
                }
            }.addOnFailureListener{
                viewModelScope.launch{
                    _offerProducts.emit(Resource.Error(it.message.toString()))
                }
            }

    }

    fun fetchBestProducts(){
        if (!pagingInfo.isPagingEnd){
            viewModelScope.launch {
                _bestProducts.emit(Resource.Loading())
            }
            firestore.collection("Products").whereEqualTo("category",category.category)
                .limit(pagingInfo.bestProductsPage * 10)
                .whereEqualTo("offerPercentage",null)
                .get()
                .addOnSuccessListener {
                    val products = it.toObjects(Product::class.java)
                    pagingInfo.isPagingEnd = products == pagingInfo.oldBestProducts
                    pagingInfo.oldBestProducts = products
                    viewModelScope.launch{
                        _bestProducts.emit(Resource.Success(products))
                    }
                    pagingInfo.bestProductsPage++
                }.addOnFailureListener{
                    viewModelScope.launch{
                        _bestProducts.emit(Resource.Error(it.message.toString()))
                    }
                }
        }

    }
}

internal data class PagingInfo2(
    var bestProductsPage: Long = 1,
    var oldBestProducts: List<Product> = emptyList(),
    var isPagingEnd: Boolean = false
)