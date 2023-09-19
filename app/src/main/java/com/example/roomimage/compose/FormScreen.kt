package com.example.roomimage.compose

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.db.model.entity.Product
import com.example.roomimage.R
import com.example.roomimage.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(productViewModel: ProductViewModel, onClickNavigateTo: () -> Unit) {
    // Para subir la imagen ya sea de internet o de la galeria
    var imageUri by remember { mutableStateOf<Uri?>(null) } // URI de la img
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }
    var name by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }
    var price by remember { mutableStateOf(0F) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        // Para ingresar img
        ButtonSelectImage(imageUri = imageUri) { launcher.launch("image/*") }
        // Para ingresar nombre
        FormStringInputField(str = name, label = "Name") { name = it }
        // Para ingresar desc
        FormStringInputField(str = desc, label = "Description") { desc = it }
        // Para ingresar precio
        FormFloatInputField(num = priceText, label = "Price") {
            priceText = it
            price = it.toFloat()
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(50F)
        ) {
            // Persiste
            Button(onClick = { onSaveProduct(0, name, desc, imageUri.toString(), price, productViewModel) }) {
                Text(text = "Save")
            }
            // Lista los productos
            Button(onClick = { onClickNavigateTo() }) {
                Text(text = "List")
            }
        }
    }
}

@Composable
fun ButtonSelectImage(imageUri: Uri?, onClick: () -> Unit) {
    Button(
        shape = RoundedCornerShape(4.dp),
        onClick = onClick
    ) {
        AsyncImage(
            model = imageUri,
            contentDescription = "img",
            placeholder = painterResource(id = R.drawable.baseline_add_photo_alternate_24), // Este no lo muestra hay un error
            error = painterResource(id = R.drawable.baseline_add_photo_alternate_24), // Solo muestra el error
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .size(220.dp)
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FormStringInputField(str: String, label: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = str,
        onValueChange = onValueChange,
        label = { Text(text = label) }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FormFloatInputField(num: String, label: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = num,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
        label = { Text(text = label) }
    )
}

fun onSaveProduct(id: Int = 0, name: String, desc: String, imageUri: String, price: Float, viewModel: ProductViewModel) {
    // TODO: Validaciones
    // Persiste
    val product = Product(
        0,
        name,
        desc,
        imageUri,
        price
    )
    viewModel.insert(product)
}