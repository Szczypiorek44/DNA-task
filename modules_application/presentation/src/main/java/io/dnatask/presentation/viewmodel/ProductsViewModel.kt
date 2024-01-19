package io.dnatask.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.dnatask.data.CardReaderService
import io.dnatask.data.PaymentApiClient
import io.dnatask.data.PurchaseApiClient
import io.dnatask.domain.ProductUseCases
import io.dnatask.domain.ProductUseCasesImpl
import io.dnatask.domain.models.BuyProductResult
import io.dnatask.presentation.models.SelectableProductHolder
import io.dnatask.presentation.models.SelectableProductHolder.Companion.toSelectableProductHolderList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsViewModel(
) : ViewModel() {

    companion object {
        private const val TAG = "ProductsViewModel"
    }

    private val productUseCases: ProductUseCases =
        ProductUseCasesImpl(PaymentApiClient(), PurchaseApiClient(), CardReaderService())

    private val mutableProducts = MutableStateFlow<List<SelectableProductHolder>?>(null)
    val products: StateFlow<List<SelectableProductHolder>?> = mutableProducts.asStateFlow()

    fun fetchProducts() {
        viewModelScope.launch {
            mutableProducts.value = productUseCases.getProducts().toSelectableProductHolderList()
        }
    }

    fun buySelectedProducts() {
        val selectedProducts = mutableProducts.value?.filter { it.isSelected }?.map { it.product }

        if (!selectedProducts.isNullOrEmpty()) {
            viewModelScope.launch(Dispatchers.IO) {
                productUseCases.buy(selectedProducts.first().productID).let { result ->
                    when (result) {
                        is BuyProductResult.Success -> {
                            Log.d(TAG, "buySelectedProducts Success")

                        }

                        is BuyProductResult.Failed -> {
                            Log.d(TAG, "buySelectedProducts Failed(errorMsg: ${result.errorMsg}")

                        }
                    }
                }
            }
        }
    }
}