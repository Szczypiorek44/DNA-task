package io.dnatask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.dnatask.data.PurchaseApiClient
import io.dnatask.presentation.models.SelectableProductHolder
import io.dnatask.presentation.models.SelectableProductHolder.Companion.toSelectableProductHolderList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {

    val purchaseApiClient: PurchaseApiClient = PurchaseApiClient()

    private var mutableProducts = MutableStateFlow<List<SelectableProductHolder>?>(null)
    var products: StateFlow<List<SelectableProductHolder>?> = mutableProducts.asStateFlow()

    fun fetchProducts() {
        viewModelScope.launch {
            mutableProducts.value = purchaseApiClient.getProducts().toSelectableProductHolderList()
        }
    }

    fun addToCart(productID: String) {

//        val newMap = mutableCart.value.toMutableMap()
//        newMap[productID] = (mutableCart.value[productID] ?: 0L) + 1L
//
//        mutableCart.value = newMap.toMap()
    }
}