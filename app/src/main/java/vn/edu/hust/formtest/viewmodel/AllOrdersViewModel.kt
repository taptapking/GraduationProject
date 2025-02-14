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
class AllOrdersViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
): ViewModel() {
    private val _allOrders = MutableStateFlow<Resource<List<Order>>>(Resource.Unspecified())
    val allOrders = _allOrders.asStateFlow()

    init{
        getAllOrders()
    }

    fun getAllOrders(){
        viewModelScope.launch{
            _allOrders.emit(Resource.Loading())
        }
//        firestore.collection("user").document(auth.uid!!).collection("orders").get()
            firestore.collection("order").whereEqualTo("userID",auth.uid!!).get()
            .addOnSuccessListener { querySnapshot ->
                val orders= querySnapshot.toObjects(Order::class.java)
                viewModelScope.launch{
                    _allOrders.emit(Resource.Success(orders))
                }
            }.addOnFailureListener {
                viewModelScope.launch{
                    _allOrders.emit(Resource.Error(it.message.toString()))
                }
            }
    }
}