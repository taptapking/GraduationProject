package vn.edu.hust.formtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.edu.hust.formtest.data.order.Order
import vn.edu.hust.formtest.util.Resource
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
): ViewModel() {
    private val _order = MutableStateFlow<Resource<Order>>(Resource.Unspecified())
    val order = _order.asStateFlow()

    fun placeOrder(order: Order){
        viewModelScope.launch {
            _order.emit(Resource.Loading())
        }
        firestore.runBatch { batch ->
            //add orders into user-order collection
            //add orders into orders collection
            //delete products from user-cart collection

            firestore.collection("user")
                .document(auth.uid!!)
                .collection("orders")
                .document()
                .set(order)

            firestore.collection("user").document().set(order)

            firestore.collection("user").document(auth.uid!!).collection("cart").get()
                .addOnSuccessListener {
                    it.documents.forEach {
                        it.reference.delete()
                    }
                }
        }.addOnSuccessListener {
            viewModelScope.launch{
                _order.emit(Resource.Success(order))
            }
        }.addOnFailureListener {
            viewModelScope.launch{
                _order.emit(Resource.Error(it.message.toString()))
            }
        }
    }
}