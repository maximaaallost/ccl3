package com.cc221013.bookify.ui

import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.cc221013.bookify.R
import com.cc221013.bookify.ui.theme.DarkBeige
import com.cc221013.bookify.ui.theme.LightBeige
import com.cc221013.bookify.ui.theme.LightViolet
import com.cc221013.bookify.ui.theme.NonWhite
import com.cc221013.bookify.ui.theme.Poppins
import com.cc221013.bookify.ui.theme.Violet
import com.cc221013.bookify.ui.theme.Yellow
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.ExecutorService

sealed class Screen(val route: String){
    object Read: Screen("first")
    object TBR: Screen("second")
    object Wishlist: Screen("third")
    object AddBook: Screen("fourth")
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(mainViewModel: MainViewModel){
    val state = mainViewModel.mainViewState.collectAsState()
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {BottomNavigationBar(navController, state.value.selectedScreen)}
    ) {
        NavHost(
            navController = navController,
            modifier = Modifier.padding(it),
            startDestination = Screen.Read.route
        ){
            composable(Screen.Read.route){
                mainViewModel.selectScreen(Screen.Read)
                mainViewModel.getBooks()
                ReadScreen(mainViewModel, navController)
            }
            composable(Screen.TBR.route){
                mainViewModel.selectScreen(Screen.TBR)
                TBRScreen(mainViewModel)
            }
            composable(Screen.Wishlist.route){
                mainViewModel.selectScreen(Screen.Wishlist)
                WishlistScreen(mainViewModel)
            }
            composable(Screen.AddBook.route){
                mainViewModel.selectScreen(Screen.AddBook)
                AddBookScreen(mainViewModel, navController)
            }
        }
    }
}


//NavigationBarItem -> adjust colors according to tab
@Composable
fun NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: Painter, // Change the type to Painter
    contentDescription: String
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(
                color = if (selected) Yellow else Violet,
                shape = RoundedCornerShape(50.dp)
            )
            .size(55.dp),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.material3.Icon(
            painter = icon, // Use the painter property
            contentDescription = contentDescription,
            tint = NonWhite,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, selectedScreen: Screen){
    BottomNavigation (
        backgroundColor = Violet,
        modifier = Modifier.height(70.dp) .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
    ) {
        Row(modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationBarItem(
                selected = (selectedScreen == Screen.Read),
                onClick = { navController.navigate(Screen.Read.route) },
                icon = painterResource(id = R.drawable.read),
                contentDescription = "Read Books section"
                )
            NavigationBarItem(
                selected = (selectedScreen == Screen.TBR),
                onClick = { navController.navigate(Screen.TBR.route) },
                icon = painterResource(id = R.drawable.tbr),
                contentDescription = "To be Read Section"
                )

            NavigationBarItem(
                selected = (selectedScreen == Screen.Wishlist),
                onClick = { navController.navigate(Screen.Wishlist.route) },
                icon = painterResource(id = R.drawable.wishlist),
                contentDescription = "Wishlist"
            )
        }
    }
}


@Composable
fun ReadScreen(mainViewModel: MainViewModel, navController: NavHostController){
    val state = mainViewModel.mainViewState.collectAsState()

  Text(text ="Read Screen")
    Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxSize()){
        Button(
            onClick = {navController.navigate(Screen.AddBook.route) },
            modifier = Modifier
                .padding(20.dp)
        ) {
            androidx.compose.material3.Icon(
                Icons.Default.Add,
                "Open Add Books",
                tint = Color.White
            )
        }
    }
    LazyColumn (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp)

    ) {
    if (state.value.books.isEmpty()) { // Show a message if there are no books saved in this shelve
        // Show a message if there are no entries
        item {
//            Image(
//                painter = painterResource(id = R.drawable.emptystateimage),
//                contentDescription = "Entry Image",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(300.dp)
//
//            )

            Text(
                text = "No Books saved yet",
                style = TextStyle(fontSize = 15.sp, color = Color.Gray, fontFamily = Poppins, textAlign = androidx.compose.ui.text.style.TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp, top = 10.dp)
            )
        }
    } else { // If there are entries, show them

        items(state.value.books.reversed()) { book -> // Reverse the list to show the newest entry on top
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { mainViewModel.editBook(book) }

            ) {

                // Top: Image
                Image(
                    painter = rememberImagePainter(data = book.cover),
                    contentDescription = "Entry Image",
                    modifier = Modifier

                        .height(200.dp)


                )

                // Middle: Description and Date
                Column(
                    modifier = Modifier
                        .padding(top = 210.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "${book.title}",
                        style = TextStyle(fontSize = 15.sp, color = Violet, fontFamily = Poppins),
                    )
                    Text(
                        text = "${book.author}",
                        style = TextStyle(fontSize = 15.sp, color = LightViolet, fontFamily = Poppins),
                    )

                }
            }
        }
    }
}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TBRScreen(mainViewModel: MainViewModel){
    Text(text ="TBR Screen")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistScreen(mainViewModel: MainViewModel){
    Text(text ="Wishlist Screen")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(mainViewModel: MainViewModel, navController: NavHostController) {
    val camState = mainViewModel.cameraState.collectAsState()
    val photosList = camState.value.photosListState // Get the list of photos taken

    var title by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                ""
            )
        )
    }
    var author by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        )
    }
    var genre by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                ""
            )
        )
    }
    var color by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                ""
            )
        )
    }
    var cover by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                ""
            )
        )
    }
    var shelf by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                ""
            )
        )
    }
    var rating by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        )
    }
    var review by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        )
    }
    var quote by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                ""
            )
        )
    }
    var language by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        )
    }
    var pages by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                ""
            )
        )
    }
    var days by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                ""
            )
        )
    }
    var mediaType by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        )
    }


    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Button(onClick = { navController.navigate(Screen.Read.route) }) {
            Text(text = "Back", style = TextStyle(fontSize = 20.sp, color = NonWhite))
        }
        Button(
            onClick = { mainViewModel.enableCameraPreview(true)},
            modifier = Modifier
                .padding(20.dp)
        ) { Text(text = "Upload Picture", style = TextStyle(fontSize = 20.sp, color = NonWhite)) }

    TextField(
        modifier = Modifier.padding(top = 10.dp),
        value = color,
        onValueChange = { newText -> color = newText },
        label = { Text(text = "Color") },
    )

    TextField(
        modifier = Modifier.padding(top = 10.dp),
        value = title,
        onValueChange = { newText -> title = newText },
        label = { Text(text = "Title") },
    )

    TextField(
        modifier = Modifier.padding(top = 10.dp),
        value = author,
        onValueChange = { newText -> author = newText },
        label = { Text(text = "Author") },
    )

    TextField(
        modifier = Modifier.padding(top = 10.dp),
        value = genre,
        onValueChange = { newText -> genre = newText },
        label = { Text(text = "Genre") },
    )

    TextField(
        modifier = Modifier.padding(top = 10.dp),
        value = shelf,
        onValueChange = { newText -> shelf = newText },
        label = { Text(text = "Shelf") },
    )

    TextField(
        modifier = Modifier.padding(top = 10.dp),
        value = rating,
        onValueChange = { newText -> rating = newText },
        label = { Text(text = "Rating") },
    )

    TextField(
        modifier = Modifier.padding(top = 10.dp),
        value = review,
        onValueChange = { newText -> review = newText },
        label = { Text(text = "Review") },
    )

    TextField(
        modifier = Modifier.padding(top = 10.dp),
        value = quote,
        onValueChange = { newText -> quote = newText },
        label = { Text(text = "Quote") },
    )

    TextField(
        modifier = Modifier.padding(top = 10.dp),
        value = language,
        onValueChange = { newText -> language = newText },
        label = { Text(text = "Language") },
    )

    TextField(
        modifier = Modifier.padding(top = 10.dp),
        value = pages,
        onValueChange = { newText -> pages = newText },
        label = { Text(text = "Pages") },
    )

    TextField(
        modifier = Modifier.padding(top = 10.dp),
        value = days,
        onValueChange = { newText -> days = newText },
        label = { Text(text = "Days") },
    )

    TextField(
        modifier = Modifier.padding(top = 10.dp),
        value = mediaType,
        onValueChange = { newText -> mediaType = newText },
        label = { Text(text = "Media Type") },
    )

        Button(
            onClick = {
//                val currentDate = LocalDate.now() // Get the current date
//                val formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) // Save the date in the wished format

                mainViewModel.save(
                    Book(
                        title.text,
                        author.text,
                        genre.text,
                        color.text,
                        photosList.last().toString(),
                        shelf.text,
                        rating.text.toIntOrNull(),
                        review.text,
                        quote.text,
                        language.text,
                        pages.text.toIntOrNull(),
                        days.text.toIntOrNull(),
                        mediaType.text
                    )
                )
                navController.navigate(Screen.Read.route)
            },
            modifier = Modifier.padding(20.dp),
        ) {
            Text(text = stringResource(R.string.addscreen_button_save), fontSize = 20.sp, color = Color.White, fontFamily = Poppins)
        }
}

}

@Composable
fun CameraView(mainViewModel: MainViewModel, previewView: PreviewView, imageCapture: ImageCapture, cameraExecutor: ExecutorService, directory: File){
    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()){
        AndroidView({previewView}, modifier = Modifier.fillMaxSize())

        Button(
            modifier = Modifier.padding(25.dp),
            onClick = {
                val photoFile = File(
                    directory,
                    SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.US).format(System.currentTimeMillis()) + ".jpg" // Save the photo with the current date
                )

                imageCapture.takePicture(
                    ImageCapture.OutputFileOptions.Builder(photoFile).build(),
                    cameraExecutor,
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onError(exception: ImageCaptureException) {
                            Log.e("camApp", "Error when capturing image")
                        }

                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            mainViewModel.setNewUri(Uri.fromFile(photoFile))
                        }
                    }
                )
            }
        )
        {
            androidx.compose.material3.Icon(
                Icons.Default.AddCircle,
                "Take Photo",
                tint = Color.White
            )
        }
    }
}
