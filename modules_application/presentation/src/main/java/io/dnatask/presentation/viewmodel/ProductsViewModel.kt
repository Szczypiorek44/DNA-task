package io.dnatask.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.dnatask.domain.ProductUseCases
import io.dnatask.domain.models.BuyProductResult
import io.dnatask.presentation.models.SelectableProductHolder
import io.dnatask.presentation.models.SelectableProductHolder.Companion.deselectAll
import io.dnatask.presentation.models.SelectableProductHolder.Companion.toSelectableProductHolderList
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

    private val mutableIsPaymentInProgress = MutableStateFlow(false)
    val isPaymentInProgress = mutableIsPaymentInProgress.asStateFlow()

    init {
        viewModelScope.launch {
            mutableProducts.value = productUseCases.getProducts().toSelectableProductHolderList()
        }
    }

    fun onPayButtonClicked() {
        viewModelScope.launch {
            if (mutableIsPaymentInProgress.value) {
                return@launch
            }

            mutableIsPaymentInProgress.value = true
            buySelectedProducts()
            mutableIsPaymentInProgress.value = false
        }
    }

    private suspend fun buySelectedProducts() {
        val selectedProducts = mutableProducts.value?.filter { it.isSelected }?.map { it.product }

        if (selectedProducts.isNullOrEmpty()) {
            mutablePurchaseResult.emit(PurchaseResult.NoItemsSelected)
            return
        }

        productUseCases.buy(selectedProducts.first().productID).let { result ->
            when (result) {
                is BuyProductResult.Success -> {
                    mutablePurchaseResult.emit(PurchaseResult.Success)
                    mutableProducts.apply { value = value?.deselectAll() }
                }

                is BuyProductResult.Failed -> {
                    Log.d(TAG, "buySelectedProducts Failed(errorMsg: ${result.errorMsg}")
                    mutablePurchaseResult.emit(PurchaseResult.Failed)
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