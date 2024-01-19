package io.dnatask.presentation.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.dnatask.common.Product

data class SelectableProductHolder(
    val product: Product
) {
    var isSelected by mutableStateOf(false)

    companion object {
        fun List<Product>.toSelectableProductHolderList(): List<SelectableProductHolder> {
            return this.map { it.toSelectableProductHolder() }
        }

        private fun Product.toSelectableProductHolder(): SelectableProductHolder {
            return SelectableProductHolder(this)
        }
    }
}