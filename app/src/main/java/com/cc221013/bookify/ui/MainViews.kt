package com.cc221013.bookify.ui

import android.content.Intent
import android.graphics.Paint.Align
import android.net.Uri
import android.text.style.QuoteSpan
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
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.graphics.toColorInt
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.cc221013.bookify.R
import com.cc221013.bookify.ui.theme.Black
import com.cc221013.bookify.ui.theme.Calistoga
import com.cc221013.bookify.ui.theme.Blue
import com.cc221013.bookify.ui.theme.DarkBeige
import com.cc221013.bookify.ui.theme.DarkBlue
import com.cc221013.bookify.ui.theme.DarkRed
import com.cc221013.bookify.ui.theme.Grey
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




sealed class Screen(val route: String) {
    object Read : Screen("first")
    object TBR : Screen("second")
    object Wishlist : Screen("third")
    object AddBook : Screen("fourth")
    object BookDetails : Screen("fifth")
    object Stats : Screen("sixth")
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(mainViewModel: MainViewModel) {
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
            composable(Screen.BookDetails.route) {
                mainViewModel.selectScreen(Screen.BookDetails)
                BookDetails(mainViewModel, navController)
            }
            composable(Screen.Stats.route) {
                mainViewModel.selectScreen(Screen.Stats)
                StatsScreen(mainViewModel, navController)
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
fun SmallText(text: String?, color: Color) {
    Text(
        text = text ?: "",
        style = TextStyle(
            fontSize = 13.sp,
            color = color,
            fontFamily = Poppins,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    )
}

//Styled Text
@Composable
fun BigText(text: Int?, color: Color) {
    Text(
        text = text?.toString() ?: "",
        style = TextStyle(
            fontSize = 24.sp,
            color = color,
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    )
}

//Reading Statistics on Read Page
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

            Box(modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
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
                Spacer(modifier = Modifier.width(10.dp))
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
                Spacer(modifier = Modifier.width(10.dp))

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

//Vertical Scroll to filter Genres
@Composable
fun GenreScroll() {
    val genreColors = listOf(
        Violet,
        LightViolet,
        DarkBlue,
        LightRed,
        Pink,
        Grey,
        Black,
        DarkRed,
        Lime,
        Turquoise,
        Blue,
        DarkBeige,
        Mint
    )

    val genreNames = listOf(
        "all",
        "Fantasy", "Sci-Fi", "Romance", "New Adult", "Thriller", "Horror", "Erotica",
        "Manga", "Biography", "Novel", "History", "Non-Fiction"
    )

    LazyRow(
    ) {
        genreColors.zip(genreNames).forEach { (color, name) ->
            item {
                Column(
                    modifier = Modifier
                        .clickable {}
                        .padding(9.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.bookcover),
                        tint = color,
                        contentDescription = "book cover",
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = name,
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontSize = 12.sp,
                            color = Violet
                        ),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

        }
    }
}


@Composable
fun BookDetails(mainViewModel: MainViewModel, navController: NavHostController) {
    val book = mainViewModel.selectedBook.value

    if (book !== null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            //with back button, beigeswirl, rating, title/author and cover image
            item{
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.beigeswirl),
                        contentDescription = "Decorative Picture",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.goback),
                        contentDescription = "Back",
                        tint = Violet,
                        modifier = Modifier
                            .padding(20.dp)
                            .clickable { navController.navigate(Screen.Read.route) }
                            .size(40.dp)
                    )
                    //Book Information: Rating, Title, Genre
                    Column(
                        modifier = Modifier
                            .padding(top = 70.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.placeholderstars),
                                contentDescription = "Star Rating",
                                modifier = Modifier.width(180.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = book.title,
                                style = TextStyle(
                                    fontFamily = Calistoga,
                                    fontSize = 24.sp,
                                    color = Violet,
                                    textAlign = TextAlign.Center
                                )
                            )
                            Text(
                                text = book.author,
                                style = TextStyle(
                                    fontFamily = Poppins,
                                    fontSize = 18.sp,
                                    color = LightViolet,
                                    textAlign = TextAlign.Center
                                )
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            Box(
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(30.dp)
                                    .background(color = Violet, RoundedCornerShape(8.dp))
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxSize(),
                                    text = book.genre,
                                    style = TextStyle(
                                        fontFamily = Poppins,
                                        fontSize = 18.sp,
                                        color = NonWhite,
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }
                        }

                        //Book cover
                        Box(
                            modifier = Modifier
                                .padding(top = 20.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.bookcover),
                                contentDescription = "Book cover background",
                                tint = ColorUtils.getColorByName(book.color),
                                modifier = Modifier
                                    .size(275.dp)
                            )

                            Image(
                                painter = rememberImagePainter(book.cover),
                                contentDescription = "Entry Image",
                                modifier = Modifier
                                    .height(225.dp)
                                    .padding(70.dp, 5.dp, 0.dp, 0.dp)
                                    .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 0.dp))
                            )
                        }
                    }
                }
            }

            //book stats: pages, days, paperback
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(20.dp, 0.dp, 20.dp, 0.dp)
                        .background(color = Violet, RoundedCornerShape(10.dp)),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        BigText(text = book.pages, color = NonWhite)
                        SmallText(text = "pages", color = NonWhite)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        BigText(text = book.days, color = NonWhite)
                        SmallText(text = "days", color = NonWhite)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bookcover),
                            contentDescription = "paperback",
                            tint = NonWhite,
                            modifier = Modifier.size(38.dp)
                        )
                        SmallText(text = book.mediaType, color = NonWhite)
                    }
                }
            }

            //book review & quote
            item{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp, 0.dp, 0.dp, 0.dp)
                ) {
                    Spacer(modifier = Modifier.height(15.dp))

                    //AddBoook Details:
                    //Book Review with heading and review if there is a written review
                    if (book.review?.isNotEmpty() == true) {
                        Text(
                            text = "Review",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Violet,
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Bold,
                            )
                        )

                        Text(
                            text = book.review,
                            style = TextStyle(
                                fontSize = 15.sp,
                                color = Violet,
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Medium,
                            )
                        )
                    }
                    //Book Quote with heading and review if there is a written Quote
                    Spacer(modifier = Modifier.height(15.dp))
                    if (book.quote?.isNotEmpty() == true)  {
                        Text(
                            text = "Quote",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Violet,
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Bold,
                            )
                        )

                        //Quote
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(color = Yellow, RoundedCornerShape(2.dp))
                                    .width(5.dp)
                                    .height(40.dp)
                            )

                            Text(
                                text = book.quote,
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    color = Violet,
                                    fontFamily = Poppins,
                                    fontWeight = FontWeight.Medium,
                                ),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(start = 10.dp, end = 20.dp)
                            )
                        }


                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    //Buttons: Delete, Edit
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 20.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                    ){
                        Button(
                            onClick = { mainViewModel.clickDelete(book)
                                mainViewModel.setSelectedBook(book)
                                navController.navigate(Screen.Read.route)},
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(DarkBeige)
                                .width(160.dp),
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.Transparent),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.delte),
                                contentDescription = "upload image icon",
                                tint = DarkRed,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "delete",
                                style = TextStyle(fontSize = 15.sp, color = Violet, fontFamily = Poppins),
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .width(160.dp)
                                .background(DarkBeige),
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.Transparent),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit),
                                contentDescription = "upload image icon",
                                tint = Violet,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "edit",
                                style = TextStyle(fontSize = 15.sp, color = Violet, fontFamily = Poppins),
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
                    }

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
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .height(500.dp)
    ) {

        TopDecoration(navController, "Read Books", null)





            if (state.value.books.isEmpty()) { // Show a message if there are no books saved in this shelve
                // Show a message if there are no entries
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                     ){

                        Image(
                            painter = painterResource(id = R.drawable.emptystatepicture),
                            contentDescription = "Empty State Image",
                            modifier = Modifier
                                .height(250.dp)
                                .padding(top = 40.dp)
                        )

                        Text(
                            text = "It seems like you haven't read any books yet",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Violet,
                                fontFamily = Poppins,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 40.dp, end = 40.dp, top = 10.dp, bottom = 10.dp)
                        )
                        Button(onClick = { navController.navigate(Screen.AddBook.route) },
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Violet),
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.Transparent),)
                        {
                            Text(
                                text = "Add a new Book",
                                style = TextStyle(fontSize = 16.sp, color = NonWhite, fontFamily = Poppins, fontWeight = FontWeight.ExtraBold),

                            )
                        }



                }
                // If there are entries, show them
            } else {
                ReadStats()

                Spacer(modifier = Modifier.height(20.dp))

                GenreScroll()
                Spacer(modifier = Modifier.height(10.dp))
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2),
                ) {
                items(state.value.books.reversed()) { book -> // Reverse the list to show the newest entry on top

                    //One Book
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //One Book
                        Box(
                            modifier = Modifier
                                .clickable(onClick = {
                                    navController.navigate(Screen.BookDetails.route)
                                    mainViewModel.setSelectedBook(book)
                                })
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.bookcover),
                                contentDescription = "Book cover background",
                                modifier = Modifier.size(225.dp),
                                tint = ColorUtils.getColorByName(book.color)
                            )
                            // Top: Image
                            Image(
                                painter = rememberImagePainter(data = book.cover),
                                contentDescription = "Entry Image",
                                modifier = Modifier
                                    .height(175.dp)
                                    .padding(50.dp, 5.dp, 0.dp, 0.dp)
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
                                text = book.title,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Violet,
                                    fontFamily = Calistoga,
                                    textAlign = TextAlign.Center
                                ),
                            )
                            Text(
                                text = book.author,
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




            if (state.value.books.isEmpty()) { // Show a message if there are no books saved in this shelve
                // Show a message if there are no entries
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                ){

                    Image(
                        painter = painterResource(id = R.drawable.emptystatepicture),
                        contentDescription = "Empty State Image",
                        modifier = Modifier
                            .height(250.dp)
                            .padding(top = 40.dp)
                    )

                    Text(
                        text = "It seems like you haven't read any books yet",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Violet,
                            fontFamily = Poppins,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp, end = 40.dp, top = 10.dp, bottom = 10.dp)
                    )
                    Button(onClick = { navController.navigate(Screen.AddBook.route) },
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Violet),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.Transparent),)
                    {
                        Text(
                            text = "Add a new Book",
                            style = TextStyle(fontSize = 16.sp, color = NonWhite, fontFamily = Poppins, fontWeight = FontWeight.ExtraBold),

                            )
                    }



                }
            } else {
                GenreScroll()

                Spacer(modifier = Modifier.height(10.dp))
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(3),
                ) {
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
                                modifier = Modifier.size(150.dp),
                                tint = ColorUtils.getColorByName(book.color)
                            )
                            // Top: Image
                            Image(
                                painter = rememberImagePainter(data = book.cover),
                                contentDescription = "Entry Image",
                                modifier = Modifier
                                    .height(120.dp)
                                    .padding(32.dp, 2.5.dp, 0.dp, 0.dp)
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


            if (state.value.books.isEmpty()) {    // Show a message if there are no entries
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                ){

                    Image(
                        painter = painterResource(id = R.drawable.emptystatepicture),
                        contentDescription = "Empty State Image",
                        modifier = Modifier
                            .height(250.dp)
                            .padding(top = 40.dp)
                    )

                    Text(
                        text = "It seems like you haven't read any books yet",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Violet,
                            fontFamily = Poppins,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp, end = 40.dp, top = 10.dp, bottom = 10.dp)
                    )
                    Button(onClick = { navController.navigate(Screen.AddBook.route) },
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Violet),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.Transparent),)
                    {
                        Text(
                            text = "Add a new Book",
                            style = TextStyle(fontSize = 16.sp, color = NonWhite, fontFamily = Poppins, fontWeight = FontWeight.ExtraBold),

                            )
                    }

                }
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(3),
                ) {
                items(state.value.books.reversed()) { book -> // Reverse the list to show the newest entry on top
                if (book.shelf == "Wishlist") {


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
                                modifier = Modifier.size(150.dp),
                                tint = ColorUtils.getColorByName(book.color)
                            )
                            // Top: Image
                            Image(
                                painter = rememberImagePainter(data = book.cover),
                                contentDescription = "Entry Image",
                                modifier = Modifier
                                    .height(120.dp)
                                    .padding(32.dp, 5.dp, 0.dp, 0.dp)
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
}

@Composable
fun StatsScreen(mainViewModel: MainViewModel, navHostController: NavHostController){

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

    val genres = listOf(
        "Fantasy", "Sci-Fi", "Romance", "New Adult", "Thriller", "Horror", "Erotica",
        "Manga", "Biography", "Novel", "History", "Non-Fiction"
    )

    val languages = listOf(
        "English", "German", "French", "Spanish", "Italian", "Portuguese", "Russian", "Chinese", "Japanese", "Korean"
    )
     val mediaTypeList = listOf(
        "Paperback", "Ebook", "Audiobook"
     )
     val shelfList = listOf(
        "Read", "To be Read", "Wishlist"
     )
    val starRatings = listOf(
        5, 4, 3, 2, 1
    )
    var selectedGenre by remember { mutableStateOf(genres[0]) }
    var selectedLanguage by remember { mutableStateOf(languages[0]) }
    var selectedMediaType by remember { mutableStateOf(mediaTypeList[0]) }
    var selectedShelf by remember { mutableStateOf(shelfList[0]) }
    var selectedRating by remember { mutableStateOf(starRatings[0]) }
    var quotes by remember { mutableStateOf(listOf("")) }

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
                contentDescription = "Decorative Picture",
                modifier = Modifier.fillMaxWidth()
            )

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

        ColorList { selectedColor ->
            color = TextFieldValue(selectedColor.toString())
        }

 Column {
     StyledTextField(
         placeholder = "Book Title",
         value = title.text,
         onValueChange = { newTitle ->
             title = TextFieldValue(newTitle)
         }
     )
        StyledTextField(
            placeholder = "Author",
            value = author.text,
            onValueChange = { newAuthor ->
                author = TextFieldValue(newAuthor)
            }
        )


     StyledText(text = "Choose a Genre")
     StyledTextFieldWithDropdown(
         onValueChange = { newGenre -> selectedGenre = newGenre },
         items = genres,
         selectedValue = selectedGenre
     )

     StyledText(text = "Choose a Shelf")
        StyledTextFieldWithDropdown(
            onValueChange = { newShelf -> selectedShelf = newShelf },
            items = shelfList,
            selectedValue = selectedShelf
        )

         StyledText("Rate the book")
     StyledTextFieldWithDropdown(
         onValueChange = { newRating ->
             selectedRating = newRating.replace(" stars", "").toIntOrNull() ?: 0
         },
         items = starRatings.map { it.toString() },
         selectedValue = "$selectedRating stars"
     )

        StyledTextField(
            placeholder = "Review",
            value = review.text,
            onValueChange = { newReview ->
                review = TextFieldValue(newReview)
            }
        )

     QuoteSection(
         quotes = quotes,
         onQuoteAdded = { newQuote ->
             quotes = quotes.toMutableList().apply { add(newQuote) }
         },
         onQuoteRemoved = { index ->
             quotes = quotes.toMutableList().apply { removeAt(index) }
         }
     )



        StyledText(text = "Choose a Language")
        StyledTextFieldWithDropdown(
            onValueChange = { newLanguage -> selectedLanguage = newLanguage },
            items = languages,
            selectedValue = selectedLanguage
        )
     Row {
         ShortStyledTextField(
             placeholder = "Pages",
             value = pages.text,
             onValueChange = { newPages ->
                 pages = TextFieldValue(newPages)
             }
         )
         ShortStyledTextField(
             placeholder = "Days",
             value = days.text,
             onValueChange = { newDays ->
                 days = TextFieldValue(newDays)
             }
         )
     }
    StyledText(text = "Choose a Media Type")
        StyledTextFieldWithDropdown(
            onValueChange = { newMediaType -> selectedMediaType = newMediaType },
            items = mediaTypeList,
            selectedValue = selectedMediaType
        )

 }

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.violetswirlbottom),
                contentDescription = "Decorative Picture",
                modifier = Modifier.fillMaxWidth()
            )

            Icon (
                painter = painterResource(id = R.drawable.yellowtick),
                contentDescription = "Back",
                tint = Yellow,
                modifier = Modifier
                    .padding(end = 60.dp, bottom = 40.dp)
                    .align(Alignment.BottomEnd)
                    .size(50.dp)
                    .background(NonWhite, CircleShape)
                    .border(4.dp, Yellow, CircleShape)
                    .clickable {
                        //val currentDate = LocalDate.now() // Get the current date
                        //val formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) // Save the date in the wished format
                        val quotesText = quotes.joinToString(separator = ";")
                        mainViewModel.save(
                            Book(
                                title.text,
                                author.text,
                                selectedGenre,
                                color.text,
                                cover.text,
                                selectedShelf,
                                selectedRating,
                                review.text,
                                quotesText,
                                selectedLanguage,
                                pages.text.toIntOrNull(),
                                days.text.toIntOrNull(),
                                selectedMediaType
                            )
                        )
                        navController.navigate(Screen.Read.route)
                    }
                    .size(40.dp)


            )
        }
}

}
@Composable
fun QuoteSection(
    quotes: List<String>,
    onQuoteAdded: (String) -> Unit,
    onQuoteRemoved: (Int) -> Unit
) {
    // Use a list to store individual TextFieldValues for each quote
    val quoteValues = remember { quotes.map { TextFieldValue(it) }.toMutableList() }

    Column {
        // Existing quote input
        StyledTextField(
            placeholder = "Quote",
            value = quoteValues.getOrNull(0)?.text ?: "",
            onValueChange = { newQuote ->
                quoteValues[0] = TextFieldValue(newQuote)
                onQuoteAdded(quoteValues[0].text)
            }
        )

        // Display additional quote text fields
        for (i in 1 until quoteValues.size) {
            Row {
                StyledTextField(
                    placeholder = "Quote",
                    value = quoteValues.getOrNull(i)?.text ?: "",
                    onValueChange = { newQuote ->
                        quoteValues[i] = TextFieldValue(newQuote)
                    }
                )
            }
        }

        // Button to add new quote text field
        if (quoteValues.size < 10) {
            Button(
                onClick = {
                    // Add an empty quote and a new TextFieldValue
                    onQuoteAdded("")
                    quoteValues.add(TextFieldValue(""))
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .padding(start = 20.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add quote", tint = NonWhite)
                Text(
                    text = "add quote",
                    style = TextStyle(fontSize = 15.sp, color = NonWhite, fontFamily = Poppins),
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        } else {
            // Display a message or alternative UI when the limit is reached
            Text("Maximum quotes reached", color = Color.Red)
        }

        // Button to remove the last quote
        if (quoteValues.size > 1) {
            Button(
                onClick = {
                    // Remove the last quote and its corresponding TextFieldValue
                    onQuoteRemoved(quoteValues.size - 1)
                    quoteValues.removeLast()
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .padding(start = 20.dp)
            ) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "remove quote", tint = NonWhite)
                Text(
                    text = "remove quote",
                    style = TextStyle(fontSize = 15.sp, color = NonWhite, fontFamily = Poppins),
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
        val quotesText = quoteValues.joinToString(separator = ";") { it.text }
    }
}


@Composable
fun StyledTextFieldWithDropdown(
    onValueChange: (String) -> Unit,
    items: List<String>,
    selectedValue: String // New parameter to hold the selected value
) {
    var expanded by remember { mutableStateOf(false) }
    Column ( modifier = Modifier
        .padding(10.dp)){
    // DropdownMenu
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .width(300.dp)
            .background(Violet),
    ) {
        items.forEachIndexed { index, item ->
            DropdownMenuItem(
                onClick = {
                    onValueChange(item)
                    expanded = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = NonWhite,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }

    // DropdownToggle
    Box(
        modifier = Modifier
            .clickable { expanded = !expanded }
            .background(
                color = DarkBeige,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(15.dp),
    ) {
        Row(
            modifier = Modifier.width(250.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = selectedValue, // Display the selected value
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Violet,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = Violet
            )
        }
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

object ColorUtils {
    val colorMap = mapOf(
        "DarkRed" to DarkRed,
        "LightRed" to LightRed,
        "Pink" to Pink,
        "Orange" to Orange,
        "Lime" to Lime,
        "Mint" to Mint,
        "Turquoise" to Turquoise,
        "Blue" to Blue,
        "DarkBlue" to DarkBlue
    )
    fun getColorByName(name: String): Color {
        return colorMap[name] ?: Color.Black // Default to black if the color name is not found
    }
}
@Composable
fun ColorList(
    onColorSelected: (String) -> Unit
) {
    val colorList = listOf(
        "DarkRed", "LightRed", "Pink", "Orange", "Lime", "Mint", "Turquoise", "Blue", "DarkBlue"
    )

    var selectedColor by remember { mutableStateOf(colorList.first()) }
    Row {
        Spacer(modifier = Modifier.width(35.dp))
        LazyRow(content = {
            items(colorList) { colorName ->
                val color = ColorUtils.getColorByName(colorName)

                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(5.dp)
                        .background(color, CircleShape)
                        .clickable {
                            selectedColor = colorName
                            onColorSelected(colorName)
                        }
                        .border(
                            2.dp,
                            if (selectedColor == colorName) Violet else Color.Transparent,
                            CircleShape
                        )

                )
            }
        })
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShortStyledTextField(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit // This is a lambda that takes a String parameter
) {

    OutlinedTextField(
        modifier = Modifier
            .width(150.dp)
            .padding(10.dp),

        value = value,
        onValueChange = { newText ->
            // Instead of directly assigning to 'value', call the provided callback
            onValueChange(newText)
        },
        label = {
            Text(
                text = "$placeholder",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Violet,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold
                )
            )
        },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = DarkBeige,
            cursorColor = Violet,
            focusedIndicatorColor = Violet,
            focusedLabelColor = Violet,

            textColor = Violet,
            unfocusedIndicatorColor = DarkBeige,
        )
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StyledTextField(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit // This is a lambda that takes a String parameter
) {

    OutlinedTextField(
        modifier = Modifier
            .width(300.dp)
            .padding(10.dp),

        value = value,
        onValueChange = { newText ->
            // Instead of directly assigning to 'value', call the provided callback
            onValueChange(newText)
        },
        label = {
            Text(
                text = "$placeholder",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Violet,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold
                )
            )
        },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = DarkBeige,
            cursorColor = Violet,
            focusedIndicatorColor = Violet,
                focusedLabelColor = Violet,

                textColor = Violet,
                unfocusedIndicatorColor = DarkBeige,
        )
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


