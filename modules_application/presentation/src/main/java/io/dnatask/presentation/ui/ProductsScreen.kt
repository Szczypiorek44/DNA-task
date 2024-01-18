package io.dnatask.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.dnatask.data.Product
import io.dnatask.presentation.ProductsActivity
import io.dnatask.presentation.theme.Black
import io.dnatask.presentation.theme.DNATaskAndroidTheme
import io.dnatask.presentation.theme.White
import io.dnatask.presentation.viewmodel.ProductsViewModel
import io.dnatechnology.dnataskandroid.R

@Composable
fun ProductsRoute(productsViewModel: ProductsViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        productsViewModel.fetchProducts()
    }

    val products by productsViewModel.products.collectAsStateWithLifecycle()

    ProductsScreen(
        products,
        onProductClicked = { productsViewModel.addToCart(it.productID) })
}

@Composable
fun ProductsScreen(products: List<Product>?, onProductClicked: (Product) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!products.isNullOrEmpty()) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(products) {
                    ProductRow(
                        product = it,
                        onClicked = onProductClicked
                    )
                }
            }
        } else {
            Text(text = stringResource(R.string.loading))
        }

        PayButton(onPayButtonClicked())
    }
}

@Composable
private fun ProductRow(product: Product, onClicked: (Product) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp)
            .background(White)
            .border(1.dp, Black)
            .clickable(onClick = { onClicked.invoke(product) })
    ) {
        Text(
            text = product.toString(),
            color = Black,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(vertical = 8.dp)
        )
    }
}

@Composable
private fun PayButton(onClicked: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = ButtonDefaults.buttonColors(White),
        onClick = onClicked,
    ) {
        Text(
            text = stringResource(R.string.pay),
            color = Black,
            modifier = Modifier.padding(5.dp)
        )
    }
}

private fun onPayButtonClicked(): () -> Unit = {
    Log.d(ProductsActivity.TAG, "onPayButtonClicked()")
}

@Preview(showBackground = true)
@Composable
fun ProductsScreenPreview() {
    DNATaskAndroidTheme {
        ProductsRoute()
    }
}
