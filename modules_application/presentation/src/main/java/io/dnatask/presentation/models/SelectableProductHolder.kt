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

        fun List<SelectableProductHolder>.deselectAll(): List<SelectableProductHolder> {
            return this.onEach { it.isSelected = false }
        }


        private fun Product.toSelectableProductHolder(): SelectableProductHolder {
            return SelectableProductHolder(this)
        }
    }
}