package com.cc221013.bookify.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.cc221013.bookify.R
import com.cc221013.bookify.ui.theme.Calistoga
import com.cc221013.bookify.ui.theme.Blue
import com.cc221013.bookify.ui.theme.DarkBeige
import com.cc221013.bookify.ui.theme.DarkBlue
import com.cc221013.bookify.ui.theme.DarkRed
import com.cc221013.bookify.ui.theme.LightBeige
import com.cc221013.bookify.ui.theme.LightRed
import com.cc221013.bookify.ui.theme.LightViolet
import com.cc221013.bookify.ui.theme.Lime
import com.cc221013.bookify.ui.theme.Mint
import com.cc221013.bookify.ui.theme.NonWhite
import com.cc221013.bookify.ui.theme.Orange
import com.cc221013.bookify.ui.theme.Pink
import com.cc221013.bookify.ui.theme.Poppins
import com.cc221013.bookify.ui.theme.Turquoise
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
        bottomBar = { BottomNavigationBar(navController, state.value.selectedScreen) }
    ) {
        NavHost(
            navController = navController,
            modifier = Modifier.padding(it),
            startDestination = Screen.Read.route
        ) {
            composable(Screen.Read.route) {
                mainViewModel.selectScreen(Screen.Read)
                mainViewModel.getBooks()
                ReadScreen(mainViewModel, navController)
            }
            composable(Screen.TBR.route) {
                mainViewModel.selectScreen(Screen.TBR)
                TBRScreen(mainViewModel, navController)
            }
            composable(Screen.Wishlist.route) {
                mainViewModel.selectScreen(Screen.Wishlist)
                WishlistScreen(mainViewModel, navController)
            }
            composable(Screen.AddBook.route) {
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
fun BottomNavigationBar(navController: NavHostController, selectedScreen: Screen) {
    BottomNavigation(
        backgroundColor = Violet,
        modifier = Modifier
            .height(70.dp)
            .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
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
fun TopDecoration(navController: NavHostController, titlePage: String, subHeading: String?) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.topswirl),
            contentDescription = "Beige Swirl in the Background of the Text"
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = titlePage,
                        style = TextStyle(
                            fontFamily = Calistoga,
                            fontSize = 36.sp,
                            color = Violet
                        )
                    )
                    if (subHeading != null) {
                        Text(
                            text = subHeading,
                            style = TextStyle(
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = Violet
                            )
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.trophy),
                        contentDescription = "Go to Reading Challenge",
                        tint = Violet,
                        modifier = Modifier
                            .size(54.dp)
                            .clickable { }
                    )
                    Spacer(modifier = Modifier.width(15.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = "Add a new Book",
                        tint = Violet,
                        modifier = Modifier
                            .size(54.dp)
                            .clickable { navController.navigate(Screen.AddBook.route) }
                    )

                }


            }
        }

    }
}


@Composable
fun SmallText(text: String, color: Color) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 13.sp,
            color = color,
            fontFamily = Poppins,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
    ))
}
@Composable
fun ReadStats() {
    Card(
        modifier = Modifier
            .width(370.dp)
            .clip(RoundedCornerShape(10.dp))
            .verticalScroll(rememberScrollState()),
        colors = CardDefaults.cardColors(containerColor = Violet)
    ) {

        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {

            Box(modifier = Modifier.padding (10.dp, 0.dp, 0.dp, 0.dp)){
                SmallText("Your reading Statistics", NonWhite)
            }

            Row(
                modifier = Modifier.padding(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .width(90.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            BorderStroke(2.dp, SolidColor(NonWhite)),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "dd", color = NonWhite)
                    SmallText("books", NonWhite)
                }
                Spacer (modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier
                        .width(130.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            BorderStroke(2.dp, SolidColor(NonWhite)),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "105.588", color = NonWhite)
                    SmallText("pages", NonWhite)
                }
                Spacer (modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            BorderStroke(2.dp, SolidColor(Yellow)),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = "hey",
                        modifier = Modifier.size(20.dp),
                        tint = Yellow
                    )
                    SmallText(text = "see more", color = Yellow)
                }
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReadScreen(mainViewModel: MainViewModel, navController: NavHostController) {
    val state = mainViewModel.mainViewState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(NonWhite),
            horizontalAlignment = Alignment.CenterHorizontally,
//            .fillMaxSize()
//            .fillMaxWidth()
        ) {

            TopDecoration(navController, "Read Books", null)

            ReadStats()

            Spacer(modifier = Modifier.height(20.dp))

            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
            ) {
                if (state.value.books.isEmpty()) { // Show a message if there are no books saved in this shelve
                    // Show a message if there are no entries
                    item {
                        Text(
                            text = "No Books saved yet",
                            style = TextStyle(
                                fontSize = 15.sp,
                                color = Color.Gray,
                                fontFamily = Poppins,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 40.dp, end = 40.dp, top = 10.dp)
                        )
                    }
                    // If there are entries, show them
                } else {
                    items(state.value.books.reversed()) { book -> // Reverse the list to show the newest entry on top

                        //One Book
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //One Book
                            Box() {
                                Icon(
                                    painter = painterResource(id = R.drawable.bookcover),
                                    contentDescription = "Book cover background",
                                    modifier = Modifier.size(225.dp)
                                )
                                // Top: Image
                                Image(
                                    painter = rememberImagePainter(data = book.cover),
                                    contentDescription = "Entry Image",
                                    modifier = Modifier
                                        .height(175.dp)
                                        .padding(40.dp, 5.dp, 0.dp, 0.dp)
                                        .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 0.dp))
                                )
                            }
                            // Middle: Description and Date
                            Column(
                                modifier = Modifier
                                    .width(150.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "${book.title}",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        color = Violet,
                                        fontFamily = Calistoga,
                                        textAlign = TextAlign.Center
                                    ),
                                )
                                Text(
                                    text = "${book.author}",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = LightViolet,
                                        fontFamily = Poppins,
                                        textAlign = TextAlign.Center
                                    ),
                                )

                            }
                        }

                    }
                }
            }

        }
    }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TBRScreen(mainViewModel: MainViewModel, navController: NavHostController) {

    val state = mainViewModel.mainViewState.collectAsState()

    Column(
        modifier = Modifier
            .background(NonWhite)
            .fillMaxSize()
            .fillMaxWidth()
    ) {
        TopDecoration(navController, "TBR", "Books in your shelf")

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(3),
        ) {
            if (state.value.books.isEmpty()) { // Show a message if there are no books saved in this shelve
                // Show a message if there are no entries
                item {
                    Text(
                        text = "No Books saved yet",
                        style = TextStyle(
                            fontSize = 15.sp,
                            color = Color.Gray,
                            fontFamily = Poppins,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp, end = 40.dp, top = 10.dp)
                    )
                }
                // If there are entries, show them
            } else {
                items(state.value.books.reversed()) { book -> // Reverse the list to show the newest entry on top

                    //One Book
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //One Book
                        Box() {
                            Icon(
                                painter = painterResource(id = R.drawable.bookcover),
                                contentDescription = "Book cover background",
                                modifier = Modifier.size(150.dp)
                            )
                            // Top: Image
                            Image(
                                painter = rememberImagePainter(data = book.cover),
                                contentDescription = "Entry Image",
                                modifier = Modifier
                                    .height(120.dp)
                                    .padding(24.dp, 2.5.dp, 0.dp, 0.dp)
                                    .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 0.dp))
                            )
                        }
                        // Middle: Description and Date
                        Column(
                            modifier = Modifier
                                .width(100.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${book.title}",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Violet,
                                    fontFamily = Calistoga,
                                    textAlign = TextAlign.Center
                                ),
                            )
                            Text(
                                text = "${book.author}",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = LightViolet,
                                    fontFamily = Poppins,
                                    textAlign = TextAlign.Center
                                ),
                            )

                        }
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistScreen(mainViewModel: MainViewModel, navController: NavHostController) {

    val state = mainViewModel.mainViewState.collectAsState()

    Column(
        modifier = Modifier
            .background(NonWhite)
            .fillMaxSize()
            .fillMaxWidth()
    ) {
        TopDecoration(navController, "Wishlist", "Books you want to buy")

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(3),
        ) {
            if (state.value.books.isEmpty()) { // Show a message if there are no books saved in this shelve
                // Show a message if there are no entries
                item {
                    Text(
                        text = "No Books saved yet",
                        style = TextStyle(
                            fontSize = 15.sp,
                            color = Color.Gray,
                            fontFamily = Poppins,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp, end = 40.dp, top = 10.dp)
                    )
                }
                // If there are entries, show them
            } else {
                items(state.value.books.reversed()) { book -> // Reverse the list to show the newest entry on top

                    //One Book
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //One Book
                        Box() {
                            Icon(
                                painter = painterResource(id = R.drawable.bookcover),
                                contentDescription = "Book cover background",
                                modifier = Modifier.size(150.dp)
                            )
                            // Top: Image
                            Image(
                                painter = rememberImagePainter(data = book.cover),
                                contentDescription = "Entry Image",
                                modifier = Modifier
                                    .height(120.dp)
                                    .padding(25.dp, 5.dp, 0.dp, 0.dp)
                                    .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 0.dp))
                            )
                        }
                        // Middle: Description and Date
                        Column(
                            modifier = Modifier
                                .width(100.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${book.title}",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Violet,
                                    fontFamily = Calistoga,
                                    textAlign = TextAlign.Center
                                ),
                            )
                            Text(
                                text = "${book.author}",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = LightViolet,
                                    fontFamily = Poppins,
                                    textAlign = TextAlign.Center
                                ),
                            )

                        }
                    }

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(mainViewModel: MainViewModel, navController: NavHostController) {


    val camState = mainViewModel.cameraState.collectAsState()
    val photosList = camState.value.photosListState // Get the list of photos taken
    val lastItem = if (photosList.isNotEmpty()) {
        photosList.last()
    } else {
        Uri.parse("android.resource://com.cc221013.bookify/drawable/placeholdercover")
    }

    var cover by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var title by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var author by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var genre by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var color by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var shelf by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var rating by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var review by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var quote by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var language by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var pages by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var days by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var mediaType by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

    val photoPicker = setupPhotoPicker { uri ->
        cover = TextFieldValue(uri.toString())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        //with back button, violetswirl and cover image
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.violetswirl),
                contentDescription = "Decorative Picture" )

            Icon (
                painter = painterResource(id = R.drawable.goback),
                contentDescription = "Back",
                tint = NonWhite,
                modifier = Modifier
                    .padding(20.dp)
                    .clickable { navController.navigate(Screen.Read.route) }
                    .size(40.dp)


            )
            Box( modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 90.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)){
                Icon(
                    painter = painterResource(id = R.drawable.bookcover),
                    contentDescription = "Book cover background",
                    tint = DarkBeige,
                    modifier = Modifier
                        .size(275.dp)
                )

                Image(
                    painter = rememberImagePainter(lastItem),
                    contentDescription = "Entry Image",
                    modifier = Modifier
                        .height(225.dp)
                        .padding(65.dp, 5.dp, 0.dp, 0.dp)
                        .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 0.dp))
                )
            }

        }


        //Picture upload Button
        Button(onClick = { photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Yellow),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.Transparent),
    ) {
        Icon(painter = painterResource(id = R.drawable.upload), contentDescription = "upload image icon", tint = Violet)
        Text(text = "upload cover image", style = TextStyle(fontSize = 15.sp, color = Violet, fontFamily = Poppins), modifier = Modifier.padding(start = 10.dp))
    }


        Text(text = "Book Color",
            style = TextStyle(fontSize = 16.sp, color = Violet, fontFamily = Poppins, fontWeight = FontWeight.ExtraBold),
            modifier = Modifier
                .padding(top = 20.dp, start = 45.dp)
                .align(Alignment.Start),
        )

        ColorList()

        Button(onClick = { },
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.Start)
                .padding(start = 40.dp),
            //colors = ButtonDefaults.buttonColors(backgroundColor = Yellow),
        ) {
            Icon(painter = painterResource(id = R.drawable.paintbrush), contentDescription = "custom color icon", tint = NonWhite)
            Text(text = "custom color", style = TextStyle(fontSize = 15.sp, color = NonWhite, fontFamily = Poppins), modifier = Modifier.padding(start = 10.dp))
        }

 Column {
        StyledTextField(title,"Book Title")
        StyledTextField(author,"Author")
        StyledTextField(color, "Color")
        StyledTextField(genre,"Genre")
        StyledText("Shelf")
        StyledTextField(shelf,"Shelf")
        StyledText("Rate the book")
        StyledTextField(rating,"Rating")
        StyledText("Write a review")
        StyledTextField(review,"Review")
        StyledText("Quotes")
        StyledTextField(quote,"'I will not die today' - Harry Potter")
        Button(onClick = {},
         modifier = Modifier
             .clip(RoundedCornerShape(8.dp))
             .padding(start = 20.dp)
         //colors = ButtonDefaults.buttonColors(backgroundColor = Yellow),
        ) {
         Icon(imageVector = Icons.Default.Add, contentDescription = "add quote", tint = NonWhite)
         Text(text = "add quote", style = TextStyle(fontSize = 15.sp, color = NonWhite, fontFamily = Poppins), modifier = Modifier.padding(start = 5.dp))
        }
        StyledTextField(language,"Language")
        StyledText("Amount of Pages")
        StyledTextField(pages,"Pages")
        StyledTextField(days,"Days")
        StyledTextField(mediaType,"Media Type")
 }

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
                        cover.text.toString(),
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
private fun setupPhotoPicker(onImagePicked: (Uri) -> Unit): ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?> {
   val context = LocalContext.current
    return rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            onImagePicked(uri)
            val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
            context.contentResolver.takePersistableUriPermission(uri, flag)
        } else {
            Log.e("PhotoPicker", "No image was picked")
        }
    }
}

@Composable
fun ColorList() {
    val colorList = listOf(
        DarkRed, LightRed, Pink, Orange, Lime, Mint, Turquoise, Blue, DarkBlue
    )
   Row {
        Spacer(modifier = Modifier.padding(20.dp))
        LazyRow {
            items(colorList) { color ->
                Icon(
                    painter = painterResource(id = R.drawable.circle),
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(5.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledTextField( value: TextFieldValue, label: String) {
    var inputValue = value;
    TextField(
        value = inputValue,
//        colors = TextFieldDefaults.textFieldColors(
//            backgroundColor = DarkBeige,
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//            disabledIndicatorColor = Color.Transparent
//        ),
        modifier = Modifier
            .padding(top = 10.dp)
            .padding(horizontal = 16.dp)
            .background(DarkBeige, RoundedCornerShape(8.dp)),
        onValueChange = { newText -> inputValue = newText },
        label = { Text(text = "$label", color = Violet, fontSize = 13.sp) }
    )
}

@Composable
fun StyledText(text: String) {
    Text(text = "$text",
        style = TextStyle(fontSize = 16.sp, color = Violet, fontFamily = Poppins, fontWeight = FontWeight.ExtraBold),
        modifier = Modifier
            .padding(start = 16.dp, top = 10.dp )
    )
}


