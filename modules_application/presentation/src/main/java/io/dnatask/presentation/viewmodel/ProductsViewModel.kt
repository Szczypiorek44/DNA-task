package io.dnatask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.dnatask.data.Product
import io.dnatask.data.PurchaseApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {

    val purchaseApiClient: PurchaseApiClient = PurchaseApiClient()

    private var mutableCart = MutableStateFlow<Map<String, Long>>(mapOf())
    var cart: StateFlow<Map<String, Long>> = mutableCart.asStateFlow()

    private var mutableProducts = MutableStateFlow<List<Product>?>(null)
    var products: StateFlow<List<Product>?> = mutableProducts.asStateFlow()

    fun fetchProducts() {
        viewModelScope.launch {
            mutableProducts.value = purchaseApiClient.getProducts()
        }
    }

    fun addToCart(productID: String) {

        val newMap = mutableCart.value.toMutableMap()
        newMap[productID] = (mutableCart.value[productID] ?: 0L) + 1L

        mutableCart.value = newMap.toMap()
    }
}