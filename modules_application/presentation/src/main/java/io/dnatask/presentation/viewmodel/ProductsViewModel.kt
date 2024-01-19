package io.dnatask.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.dnatask.domain.ProductUseCases
import io.dnatask.domain.models.BuyProductResult
import io.dnatask.presentation.models.SelectableProductHolder
import io.dnatask.presentation.models.SelectableProductHolder.Companion.toSelectableProductHolderList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val productUseCases: ProductUseCases
) : ViewModel() {

    companion object {
        private const val TAG = "ProductsViewModel"
    }

    private val mutableProducts = MutableStateFlow<List<SelectableProductHolder>?>(null)
    val products: StateFlow<List<SelectableProductHolder>?> = mutableProducts.asStateFlow()

    private val mutablePurchaseResult = MutableSharedFlow<PurchaseResult>()
    val purchaseResult = mutablePurchaseResult.asSharedFlow()

    fun fetchProducts() {
        viewModelScope.launch {
            mutableProducts.value = productUseCases.getProducts().toSelectableProductHolderList()
        }
    }

    fun buySelectedProducts() {
        val selectedProducts = mutableProducts.value?.filter { it.isSelected }?.map { it.product }

        viewModelScope.launch(Dispatchers.IO) {
            if (selectedProducts.isNullOrEmpty()) {
                mutablePurchaseResult.emit(PurchaseResult.NoItemsSelected)
                return@launch
            }

            productUseCases.buy(selectedProducts.first().productID).let { result ->
                when (result) {
                    is BuyProductResult.Success -> {
                        mutablePurchaseResult.emit(PurchaseResult.Success)
                    }

                    is BuyProductResult.Failed -> {
                        Log.d(TAG, "buySelectedProducts Failed(errorMsg: ${result.errorMsg}")
                        mutablePurchaseResult.emit(PurchaseResult.Failed)
                    }
                }
            }
        }
    }

    sealed class PurchaseResult {
        object NoItemsSelected : PurchaseResult()
        object Success : PurchaseResult()
        object Failed : PurchaseResult()
    }
}