package com.example.roomimage.compose

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.db.model.entity.Product
import com.example.roomimage.R
import com.example.roomimage.viewmodel.ProductViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import java.io.File

@Composable
fun ProductListScreen(productViewModel: ProductViewModel, onClickNavigateTo: () -> Unit) {
    // Lista todos los productos de la base
    val list by productViewModel.allProducts.collectAsState(initial = listOf<Product>())
    Column() {
        ProductCardList(list, productViewModel)
        // Filtra los productos por nombre o id
        Button(onClick = onClickNavigateTo) {
            Text(text = "Pa atras")
        }
    }
}

// TODO: Eliminar producto
fun onDeleteProduct(product: Product, productViewModel: ProductViewModel) {
    // Validaciones...
    productViewModel.delete(product)
}

@Composable
fun ProductCardList(products: List<Product>, productViewModel: ProductViewModel) {
    LazyColumn() {
        items(products) { p ->
            ProductCard(product = p, productViewModel)
        }
    }
}

// El producto debe ser otro composable
@Composable
fun ProductCard(product: Product, productViewModel: ProductViewModel) {
    var imgUri by remember { mutableStateOf<Uri?>(null) }
    val padding = 8.dp
    Card(
        modifier = Modifier
            .padding(padding)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            // Aqui va id
            Text(text = product.id.toString(),
                fontWeight = FontWeight.Thin,
                textAlign = TextAlign.Center
            )
            // Aqui va nombre del prod en mayus y negrita
            Text(text = product.name,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        // Aqui va img
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(model = product.urlImage,
                contentDescription = "la img",
                error = painterResource(id = R.drawable.baseline_add_photo_alternate_24),
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .padding(padding)
                    .size(220.dp)
            )
        }
        //Image(painter = rememberAsyncImagePainter(model = imgFile), contentDescription = "img")
        // Aqui va precio en mayus y negrita
        Text(text = "$${product.price}",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(padding)
        )
        // Aqui va desc
        Text(text = product.description,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(padding)
        )
        Row(horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth().padding(padding)
        ) {
            Button(onClick = { onDeleteProduct(product, productViewModel) }) {
                Text(text = "Delete")
            }
        }
    }
}