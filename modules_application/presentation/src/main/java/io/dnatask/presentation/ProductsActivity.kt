package io.dnatask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import io.dnatask.presentation.theme.DNATaskAndroidTheme
import io.dnatask.presentation.theme.MainBackground
import io.dnatask.presentation.ui.ProductsRoute

class ProductsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DNATaskAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MainBackground
                ) {
                    ProductsRoute()
                }
            }
        }
    }
}






