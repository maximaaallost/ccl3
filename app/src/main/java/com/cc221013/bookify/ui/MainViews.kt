package com.cc221013.bookify.ui

import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
import com.cc221013.bookify.ui.theme.Yellow300
import com.cc221013.bookify.ui.theme.Yellow400
import com.cc221013.bookify.ui.theme.Yellow500
import com.cc221013.bookify.ui.theme.Yellow600
import com.cc221013.bookify.ui.theme.Yellow700
import com.cc221013.bookify.ui.theme.Yellow900
import com.cc221013.bookify.ui.theme.Red300
import com.cc221013.bookify.ui.theme.Red400
import com.cc221013.bookify.ui.theme.Red500
import com.cc221013.bookify.ui.theme.Red600
import com.cc221013.bookify.ui.theme.Red700
import com.cc221013.bookify.ui.theme.Red900
import com.cc221013.bookify.ui.theme.Turquoise300
import com.cc221013.bookify.ui.theme.Turquoise400
import com.cc221013.bookify.ui.theme.Turquoise500
import com.cc221013.bookify.ui.theme.Turquoise600
import com.cc221013.bookify.ui.theme.Turquoise700
import com.cc221013.bookify.ui.theme.Turquoise900
import com.cc221013.bookify.ui.theme.Blue300
import com.cc221013.bookify.ui.theme.Blue400
import com.cc221013.bookify.ui.theme.Blue500
import com.cc221013.bookify.ui.theme.Blue600
import com.cc221013.bookify.ui.theme.Blue700
import com.cc221013.bookify.ui.theme.Blue900
import com.cc221013.bookify.ui.theme.Green300
import com.cc221013.bookify.ui.theme.Green400
import com.cc221013.bookify.ui.theme.Green500
import com.cc221013.bookify.ui.theme.Green600
import com.cc221013.bookify.ui.theme.Green700
import com.cc221013.bookify.ui.theme.Green900
import com.cc221013.bookify.ui.theme.Purple300
import com.cc221013.bookify.ui.theme.Purple400
import com.cc221013.bookify.ui.theme.Purple500
import com.cc221013.bookify.ui.theme.Purple600
import com.cc221013.bookify.ui.theme.Purple700
import com.cc221013.bookify.ui.theme.Purple900
import com.cc221013.bookify.ui.theme.Pink300
import com.cc221013.bookify.ui.theme.Pink400
import com.cc221013.bookify.ui.theme.Pink500
import com.cc221013.bookify.ui.theme.Pink600
import com.cc221013.bookify.ui.theme.Pink700
import com.cc221013.bookify.ui.theme.Pink900
import com.cc221013.bookify.ui.theme.Orange300
import com.cc221013.bookify.ui.theme.Orange400
import com.cc221013.bookify.ui.theme.Orange500
import com.cc221013.bookify.ui.theme.Orange600
import com.cc221013.bookify.ui.theme.Orange700
import com.cc221013.bookify.ui.theme.Orange900
import com.cc221013.bookify.ui.theme.Brown300
import com.cc221013.bookify.ui.theme.Brown400
import com.cc221013.bookify.ui.theme.Brown500
import com.cc221013.bookify.ui.theme.Brown600
import com.cc221013.bookify.ui.theme.Brown700
import com.cc221013.bookify.ui.theme.Brown900
import com.cc221013.bookify.ui.theme.Grey300
import com.cc221013.bookify.ui.theme.Grey400
import com.cc221013.bookify.ui.theme.Grey500
import com.cc221013.bookify.ui.theme.Grey600
import com.cc221013.bookify.ui.theme.Grey700
import com.cc221013.bookify.ui.theme.Grey900


import java.time.LocalDate
import java.time.format.DateTimeFormatter


import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.cc221013.bookify.data.ReadingChallengeDatabaseHandler
import kotlinx.coroutines.flow.update
import kotlin.collections.isNotEmpty
import kotlin.math.min
import kotlin.random.Random


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

    val bottomBar: (@Composable () -> Unit) =
        if (state.value.selectedScreen != Screen.AddBook && state.value.selectedScreen != Screen.BookDetails) {
            { BottomNavigationBar(navController, state.value.selectedScreen) }
        } else {
            { }
        }

    Scaffold(
        bottomBar = bottomBar
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
                AddBookScreen(mainViewModel, navController, readingChallenges = state.value.challenges)
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

                if (titlePage !== "Stats") {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.trophy),
                            contentDescription = "Go to Reading Challenge",
                            tint = Violet,
                            modifier = Modifier
                                .size(54.dp)
                                .clickable { navController.navigate(Screen.Stats.route) }
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
fun ReadStats(mainViewModel: MainViewModel, navController: NavHostController) {
    val books = mainViewModel.mainViewState.collectAsState().value.books
    val state = mainViewModel.mainViewState.collectAsState()
    val booksRead = books.filter { it.shelf == "Read" }.size
    val pagesRead = books.filter { it.shelf == "Read" }.sumOf { it.pages?.toInt() ?: 0 }
    Card(
        modifier = Modifier
            .width(370.dp)
            .clip(RoundedCornerShape(10.dp))
            .padding(start = 10.dp, end = 10.dp),
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
                    Text(text = booksRead.toString(), color = NonWhite)
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
                    Text(text = pagesRead.toString(), color = NonWhite)
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
                        .padding(10.dp)
                        .clickable { navController.navigate(Screen.Stats.route) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add),
                        contentDescription = "Plus",
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
fun GenreScroll(onGenreSelected: (String) -> Unit) {
    val genreColors = listOf(
        Violet,
        LightViolet,
        LightBeige,
        DarkBeige,
        DarkRed,
        LightRed,
        DarkBlue,
        Blue,
        Turquoise,
        Mint,
        Green300,
        Orange,
        Yellow
    )

    val genreNames = listOf(
        "All",
        "Fantasy", "Sci-Fi", "Romance", "New Adult", "Thriller", "Horror", "Erotica",
        "Manga", "Biography", "Novel", "History", "Non-Fiction"
    )

    LazyRow(
        content = {
            genreColors.zip(genreNames).forEach { (color, name) ->
                item {
                    Column(
                        modifier = Modifier
                            .clickable {
                                onGenreSelected(name)
                            }
                            .padding(12.dp),
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bookcover),
                            contentDescription = "book cover",
                            modifier = Modifier.size(50.dp),
                            tint = color
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
    )
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
            item {
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
                                    .size(260.dp)
                            )

                            Image(
                                painter = rememberImagePainter(book.cover),
                                contentDescription = "Entry Image",
                                modifier = Modifier
                                    .height(210.dp)
                                    .padding(65.dp, 5.dp, 0.dp, 0.dp)
                                    .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 0.dp))
                            )
                        }
                    }
                }
            }

            //book stats: pages, days, paperback
            item {
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

            //book review & quote and buttons (delete/edit)
            item {
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
                    if (book.quote?.isNotEmpty() == true) {
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
                            .padding(end = 20.dp, bottom = 40.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Button(
                            onClick = {
                                mainViewModel.clickDelete(book)
                                mainViewModel.setSelectedBook(book)
                                navController.navigate(Screen.Read.route)
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(DarkBeige)
                                .width(150.dp),
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
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    color = Violet,
                                    fontFamily = Poppins
                                ),
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
                        Button(
                            onClick = {},
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .width(150.dp)
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
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    color = Violet,
                                    fontFamily = Poppins
                                ),
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
    var selectedGenre by remember { mutableStateOf("all") }

    if (state.value.books.isEmpty() || state.value.books.none { it.shelf == "Read" }) {
        Column(){
            TopDecoration(navController = navController, titlePage = "Read Books", subHeading = null)
            EmptyState(navController = navController)
        }
    } else {

        val filteredBooks = state.value.books.filter {
            it.shelf == "Read" && (selectedGenre == "all" || it.genre == selectedGenre)
        }.reversed()

        // Show a message if no books are found in the selected genre
        if (filteredBooks.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TopDecoration(navController, "Read Books", null)
                ReadStats(mainViewModel, navController)
                Spacer(modifier = Modifier.height(20.dp))
                GenreScroll(onGenreSelected = { genre ->
                    selectedGenre = genre
                })
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    "No books in this genre", modifier = Modifier
                        .padding(16.dp), color = LightViolet
                )
            }

        } else {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
            ) {

                //Top Decoration: Read Books
                item (
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ){TopDecoration(navController, "Read Books", null)}

                //Read Stats
                item(
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth(0.9f),
                        horizontalArrangement = Arrangement.Center
                    ){
                        ReadStats(mainViewModel, navController)
                    }
                }

                //Spacer
                item(
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ){
                    Spacer(modifier = Modifier.height(20.dp))
                }
                //Genre horizontal scroll
                item(
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ){
                    GenreScroll(onGenreSelected = { genre ->
                        selectedGenre = genre
                    })
                    Spacer(modifier = Modifier.height(10.dp))
                }
                items(filteredBooks) { book ->
                    // One Book
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // One Book
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
    var selectedGenre by remember { mutableStateOf("all") }


        if (state.value.books.isEmpty() || state.value.books.none { it.shelf == "To be Read" }) {
            // Show a message if there are no entries or no entries in the TBR shelf
            Column(){
                TopDecoration(navController = navController, titlePage = "TBR", subHeading = "Books to be read")
                EmptyState(navController = navController)
            }
        } else {
            val filteredBooks = state.value.books.filter {
                it.shelf == "To be Read" && (selectedGenre == "all" || it.genre == selectedGenre)
            }.reversed()

            if (filteredBooks.isEmpty()) {
                // Show a message if no books are found in the selected genre
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    TopDecoration(navController, "TBR", "Books to be read")
                    Spacer(modifier = Modifier.height(20.dp))
                    GenreScroll(onGenreSelected = { genre ->
                        selectedGenre = genre
                    })
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "No books in this genre", modifier = Modifier
                            .padding(16.dp), color = LightViolet
                    )
                }
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(3),
                ) {

                    //Top Decoration: Read Books
                    item (
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ){TopDecoration(navController, "TBR", "Books to be read")}

                    //Spacer
                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ){
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    //Genre horizontal scroll
                    item(
                        span = {
                            GridItemSpan(maxLineSpan)
                        }
                    ){
                        GenreScroll(onGenreSelected = { genre ->
                            selectedGenre = genre
                        })
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    items(filteredBooks) { book ->
                        // One Book
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // One Book
                            Box(
                                modifier = Modifier.clickable { mainViewModel.dialogEditBook(book) }
                            ) {
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
                                    text = book.title,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = Violet,
                                        fontFamily = Calistoga,
                                        textAlign = TextAlign.Center
                                    ),
                                )
                                Text(
                                    text = book.author,
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
    Column {
        EditBook(mainViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistScreen(mainViewModel: MainViewModel, navController: NavHostController) {
    val state = mainViewModel.mainViewState.collectAsState()
    val book = mainViewModel.selectedBook.value

    Column(
        modifier = Modifier
            .background(NonWhite)
            .fillMaxSize()
            .fillMaxWidth()
    ) {

        if (state.value.books.isEmpty() || state.value.books.none { it.shelf == "Wishlist" }) {
            // Show a message if there are no entries or no entries in the wishlist shelf
            Column(){
                TopDecoration(navController = navController, titlePage = "Wishlist", subHeading = "Books you want to buy")
                EmptyState(navController = navController)

            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(3),
            ) {

               item (
                   span = {
                       GridItemSpan(maxLineSpan)
                   }
               ){
                   TopDecoration(navController, "Wishlist", "Books you want to buy")
               }

                items(state.value.books.filter { it.shelf == "Wishlist" }.reversed()) { book ->
                    // One Book
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // One Book
                        Box(
                            modifier = Modifier.clickable { mainViewModel.dialogEditBook(book) }
                        ) {
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
            Column {
                EditBook(mainViewModel)
            }

        }
    }
}


var genreData: List<PieSlices> = emptyList()
@Composable
fun StatsScreen(mainViewModel: MainViewModel, navController: NavHostController) {
    val readingChallenges = mainViewModel.getChallenges()
    val books = mainViewModel.mainViewState.collectAsState().value.books
    val state = mainViewModel.mainViewState.collectAsState()

    val booksRead = books.filter { it.shelf == "Read" }.size
    val pagesRead = books.filter { it.shelf == "Read" }.sumOf { it.pages?.toInt() ?: 0 }

    val mediaTypes = listOf("Ebook", "Audiobook", "Paperback")

    val mediaTypePercentages = mediaTypes.map { mediaType ->
        val mediaTypeCount = books.filter { it.shelf == "Read" && it.mediaType == mediaType }.size
        val percentage = if (booksRead > 0) {
            (mediaTypeCount.toFloat() / booksRead.toFloat()) * 100
        } else {
            0f
        }
        val iconResourceId = when (mediaType) {
            "Ebook" -> R.drawable.ebook
            "Audiobook" -> R.drawable.audiobook
            "Paperback" -> R.drawable.paperback
            else -> 0
        }
        MediaTypeDistributionItem(
            iconResourceId = iconResourceId,
            contentDescription = mediaType,
            percentage = percentage.toInt()
        )
    }
    val genres = books.mapNotNull { it.genre }
    val genreDistribution = calculateGenreDistribution(genres)

    // Mapping each genre to its corresponding color
    val genreColors = mapOf(
        "all" to Violet,
        "Fantasy" to LightViolet,
        "Sci-Fi" to DarkBlue,
        "Romance" to LightRed,
        "New Adult" to Pink,
        "Thriller" to Grey,
        "Horror" to Black,
        "Erotica" to DarkRed,
        "Manga" to Lime,
        "Biography" to Turquoise,
        "Novel" to Blue,
        "History" to DarkBeige,
        "Non-Fiction" to Mint
    )

    val genreData = calculateGenreDistribution(books.filter { it.shelf == "Read" }.mapNotNull { it.genre }).map { (genre, count) ->
        val color = genreColors[genre] ?: getRandomColor()
        PieSlices(count.toFloat(), color)
    }.toList()

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        item {
            TopDecoration(navController, "Stats", "Your reading in numbers")
        }
        item {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                //amount of books and pages
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    //Amount of books
                    Box(
                        modifier = Modifier
                            .background(color = Violet, RoundedCornerShape(10.dp))
                            .width(150.dp)
                            .height(80.dp),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = CenterHorizontally
                        ) {
                            BigText(text = booksRead, color = NonWhite)
                            SmallText(text = "books", color = NonWhite)
                        }

                    }
                    Spacer(modifier = Modifier.width(20.dp))

                    //Amount of Pages
                    Box(
                        modifier = Modifier
                            .background(color = Violet, RoundedCornerShape(10.dp))
                            .height(80.dp)
                            .width(180.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BigText(text = pagesRead, color = NonWhite)
                            SmallText(text = "pages", color = NonWhite)
                        }

                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                //Media Type Distribution
                Box(
                    modifier = Modifier
                        .background(color = Violet, RoundedCornerShape(10.dp))
                        .height(80.dp)
                        .width(350.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        SmallText(text = "Media Type", color = NonWhite)
                        Spacer(modifier = Modifier.width(10.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            // Media Type Distribution
                            MediaTypeDistribution(mediaTypes = mediaTypePercentages)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                //Data Vis for Genre
                Box(
                    modifier = Modifier
                        .background(color = Violet, RoundedCornerShape(10.dp))
                        .width(350.dp),
                    contentAlignment = Center
                ) {
                    Column {


                        PieChart(Modifier.size(200.dp, 200.dp), genreData)
                        Text(
                            buildAnnotatedString {
                                genreData.forEachIndexed { index, slice ->
                                    val percentage =
                                        (slice.value / calculateTotalPercentage(genreData)) * 100
                                    withStyle(style = SpanStyle(color = slice.color)) {
                                        append("${slice.value.toInt()} books - ${percentage.toInt()}%")
                                    }
                                    if (index < genreData.size - 1) {
                                        append("   ")
                                    }
                                }
                            },
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }


                Spacer(modifier = Modifier.height(20.dp))
                // Check if there are reading challenges
                if (state.value.challenges.isNotEmpty()) {
                    // Display reading challenge entries
                    ReadingChallengeEntries(readingChallenges = state.value.challenges, mainViewModel = mainViewModel)
                } else {
                    // Display add reading challenge button
                    AddReadingChallengeButton(mainViewModel)
                }
                Spacer(modifier = Modifier.width(10.dp))
            }

            addReadingChallengeAlert(mainViewModel)

        }

    }
}


// Add the following function to calculate the total percentage for genres:
fun calculateTotalPercentage(slices: List<PieSlices>): Float {
    return slices.sumByDouble { it.value.toDouble() }.toFloat()
}

class PieSlices (val value: Float, val color : Color = getRandomColor())
@Composable
fun PieChart(modifier: Modifier, slices: List<PieSlices>) {
    val sum = slices.map { it.value }.sum()
    Canvas(modifier = modifier) {

        slices.forEachIndexed { index, slice ->

            val start = 360f * slices.take(index).map { it.value }.sum() / sum
            val sweep = 360f * slice.value / sum
            drawArc(
                color = slice.color,
                startAngle = start,
                sweepAngle = sweep,
                useCenter = true
            )
        }
    }

}



fun calculateGenreDistribution(genres: List<String>): Map<String, Int> {
    return genres.groupBy { it }
        .mapValues { entry -> entry.value.size }
}

fun getRandomColor(): Color {
    val random = Random.Default
    return Color(random.nextFloat(), random.nextFloat(), random.nextFloat())
}

@Composable
fun MediaTypeDistribution(mediaTypes: List<MediaTypeDistributionItem>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        mediaTypes.forEach { item ->
            MediaTypeItem(item)
        }
    }
}

@Composable
fun MediaTypeItem(item: MediaTypeDistributionItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = item.iconResourceId),
            contentDescription = item.contentDescription,
            tint = NonWhite,
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "${item.percentage}%",
            style = TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = NonWhite
            )
        )
    }
}
@Composable
fun AddReadingChallengeButton(mainViewModel: MainViewModel) {
    // Add Reading Challenge Button
    Button(
        onClick = { mainViewModel.showReadingChallengeDialog() },
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Yellow),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.Transparent),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.add),
            contentDescription = "add icon",
            tint = Violet,
            modifier = Modifier.size(25.dp)
        )
        Text(
            text = "add reading challenge",
            style = TextStyle(fontSize = 15.sp, color = Violet, fontFamily = Poppins),
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@Composable
fun ReadingChallengeEntries(readingChallenges: List<ReadingChallenge>, mainViewModel: MainViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()
    Column(
        modifier = Modifier
            .background(color = Violet, RoundedCornerShape(10.dp))
            .width(350.dp)
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
    ) {

        readingChallenges.forEach { challenge ->
        if (state.value.finishedChallenge) {
            Column {


            Row {
                Image(
                    painter = painterResource(id = R.drawable.congratulations),
                    contentDescription = "trophy icon",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = "Congratulations!",
                        style = TextStyle(
                            fontSize = 22.sp,
                            color = NonWhite,
                            fontFamily = Calistoga,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    Text(
                        text = "You finished your reading challenge!",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = NonWhite,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                        ),
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Button (
                        onClick = { mainViewModel.deleteChallenge(challenge) },
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Yellow),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.Transparent),
                    ) {
                        Text(
                            text = "Thank you",
                            style = TextStyle(fontSize = 15.sp, color = Violet, fontFamily = Poppins, fontWeight = FontWeight.SemiBold),
                        )
                    }

                }

            }

            }
        }
            else {
           //  Display reading challenge entries

                Text(
                    text = challenge.title,
                    color = NonWhite,
                    fontSize = 20.sp,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold
                )
                val startDate =
                    LocalDate.parse(challenge.startDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                val endDate = challenge.days?.let { startDate.plusDays(it.toLong()) }

                SmallText(text = "Timeframe", color = NonWhite)
                if (endDate != null) {
                    Text(
                        text = "${challenge.startDate} - ${
                            endDate.format(
                                DateTimeFormatter.ofPattern(
                                    "dd.MM.yyyy"
                                )
                            )
                        }", color = NonWhite, fontSize = 20.sp
                    )
                }


//                val progressPercentage = calculateProgress(challenge.userBookCount, challenge.goalBookCount)* 100
                val progressPercentage = calculateProgress(mainViewModel.internBookCount, challenge.goalBookCount)* 100

//                val updatedUserBookCount = challenge.userBookCount + mainViewModel.internBookCount
                val updatedUserBookCount = mainViewModel.internBookCount



                Log.i ("internUserBookCount", mainViewModel.internBookCount.toString())
                Log.i ("updatedUserBookCount", updatedUserBookCount.toString())
                Log.i ("progresspercentage", progressPercentage.toString())


            mainViewModel.updateChallenge(
                    challenge.copy(
                        progress = progressPercentage / 100f, userBookCount = updatedUserBookCount
                    )
                )


                SmallText(
                    text = "Progress - ${String.format("%.0f", progressPercentage)}%",
                    color = NonWhite
                )
                state.value.finishedChallenge = progressPercentage >= 100
                // Progress Bar
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 12.dp)
                        .height(20.dp)
                ) {
                    LinearProgressIndicator(
                        color = Yellow,
                        backgroundColor = NonWhite,
                        progress = progressPercentage / 100f,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Row {
                    Column {
                        SmallText(text = "Goal", color = NonWhite)
                        Text(
                            text = "${challenge.goalBookCount} Books",
                            color = NonWhite,
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    Column {
                        SmallText(text = "Time", color = NonWhite)
                        Text(text = "${challenge.days} Days", color = NonWhite, fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

// Function to calculate progress percentage
fun calculateProgress(bookCount: Int, goalBookCount: Int): Float {
    return if (goalBookCount > 0) {
        // Ensure progress doesn't exceed 100%
        min(1.0f, bookCount.toFloat() / goalBookCount.toFloat())
    } else {
        0.0f
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(mainViewModel: MainViewModel, navController: NavHostController, readingChallenges: List<ReadingChallenge>) {
    val camState = mainViewModel.cameraState.collectAsState()
    val photosList = camState.value.photosListState // Get the list of photos taken
    val state = mainViewModel.mainViewState.collectAsState()
    val lastItem = if (photosList.isNotEmpty()) {
        photosList.last()
    } else {
        Uri.parse("android.resource://com.cc221013.bookify/drawable/placeholdercover")
    }

    var cover by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue(
                ""
            )
        )
    }
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

    val photoPicker = setupPhotoPicker { uri ->
        cover = TextFieldValue(uri.toString())
    }

    val genres = listOf(
        "Fantasy", "Sci-Fi", "Romance", "New Adult", "Thriller", "Horror", "Erotica",
        "Manga", "Biography", "Novel", "History", "Non-Fiction"
    )

    val languages = listOf(
        "English",
        "German",
        "French",
        "Spanish",
        "Italian",
        "Portuguese",
        "Russian",
        "Chinese",
        "Japanese",
        "Korean"
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
    var quotes by remember { mutableStateOf(listOf<String>()) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //with back button, violetswirl and cover image
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.violetswirl),
                contentDescription = "Decorative Picture",
                modifier = Modifier.fillMaxWidth()
            )

            Icon(
                painter = painterResource(id = R.drawable.goback),
                contentDescription = "Back",
                tint = NonWhite,
                modifier = Modifier
                    .padding(20.dp)
                    .clickable { navController.navigate(Screen.Read.route) }
                    .size(40.dp)


            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 90.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bookcover),
                    contentDescription = "Book cover background",
                    tint = ColorUtils.getColorByName(color.text),
                    modifier = Modifier
                        .size(275.dp)
                )

                Image(
                    painter = cover.text.let {
                        if (it.isNotEmpty()) {
                            rememberImagePainter(data = it)
                        } else {
                            painterResource(id = R.drawable.placeholdercover)
                        }
                    },
                    contentDescription = "Entry Image",
                    modifier = Modifier
                        .height(225.dp)
                        .padding(65.dp, 5.dp, 0.dp, 0.dp)
                        .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 0.dp))
                )
            }

        }


        //Picture upload Button
        Button(
            onClick = { photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Yellow),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.Transparent),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.upload),
                contentDescription = "upload image icon",
                tint = Violet
            )
            Text(
                text = "upload cover image",
                style = TextStyle(fontSize = 15.sp, color = Violet, fontFamily = Poppins),
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Text(
            text = "Book Color",
            style = TextStyle(
                fontSize = 16.sp,
                color = Violet,
                fontFamily = Poppins,
                fontWeight = FontWeight.ExtraBold
            ),
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
            if (selectedShelf == "Read") {
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
            }







        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.violetswirlbottom),
                contentDescription = "Decorative Picture",
                modifier = Modifier.fillMaxWidth()
            )

            Icon(
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

//                        val readingChallengeWithId1 =
//                            mainViewModel.getChallengeByIdAndUpdateState(1)
//
//
//                        if (readingChallengeWithId1 != null) {
//
//
//                        val progressPercentage = readingChallengeWithId1?.let {
//                            mainViewModel.calculateProgressPercentage(
//                                it
//                            )
//                        }
//                        if (progressPercentage != null) {
//                            challengeProgress = progressPercentage
//                        }
//
//                        mainViewModel.updateChallenge(
//                            ReadingChallenge(
//                                challengeTitle,
//                                challengeBookCount,
//                                challengeDays,
//                                challengeProgress,
//                                challengeStartDate,
//                                state.value.editReadingChallenge.id
//                            )
//                        )
                        navController.navigate(Screen.Read.route)
//                    }
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
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add quote",
                    tint = NonWhite
                )
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
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "remove quote",
                    tint = NonWhite
                )
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
    Column(
        modifier = Modifier
            .padding(10.dp)
    ) {
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
fun EmptyState(navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
    ) {

        Image(
            painter = painterResource(id = R.drawable.emptystatepicture),
            contentDescription = "Empty State Image",
            modifier = Modifier
                .height(250.dp)
                .padding(top = 40.dp)
        )

        Text(
            text = "There are no books in this shelf yet",
            style = TextStyle(
                fontSize = 18.sp,
                color = Violet,
                fontFamily = Poppins,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, top = 10.dp, bottom = 10.dp)
        )
        Button(
            onClick = { navController.navigate(Screen.AddBook.route) },
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Violet),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.Transparent),
        )
        {
            Text(
                text = "Add a new Book",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = NonWhite,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.ExtraBold
                ),

                )
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
        "Red900" to Red900,
        "Red700" to Red700,
        "Red600" to Red600,
        "Red500" to Red500,
        "Red400" to Red400,
        "Red300" to Red300,
        "Orange900" to Orange900,
        "Orange700" to Orange700,
        "Orange600" to Orange600,
        "Orange500" to Orange500,
        "Orange400" to Orange400,
        "Orange300" to Orange300,
        "Yellow900" to Yellow900,
        "Yellow700" to Yellow700,
        "Yellow600" to Yellow600,
        "Yellow500" to Yellow500,
        "Yellow400" to Yellow400,
        "Yellow300" to Yellow300,
        "Green900" to Green900,
        "Green700" to Green700,
        "Green600" to Green600,
        "Green500" to Green500,
        "Green400" to Green400,
        "Green300" to Green300,
        "Turquoise900" to Turquoise900,
        "Turquoise700" to Turquoise700,
        "Turquoise600" to Turquoise600,
        "Turquoise500" to Turquoise500,
        "Turquoise400" to Turquoise400,
        "Turquoise300" to Turquoise300,
        "Blue900" to Blue900,
        "Blue700" to Blue700,
        "Blue600" to Blue600,
        "Blue500" to Blue500,
        "Blue400" to Blue400,
        "Blue300" to Blue300,
        "Purple900" to Purple900,
        "Purple700" to Purple700,
        "Purple600" to Purple600,
        "Purple500" to Purple500,
        "Purple400" to Purple400,
        "Purple300" to Purple300,
        "Pink900" to Pink900,
        "Pink700" to Pink700,
        "Pink600" to Pink600,
        "Pink500" to Pink500,
        "Pink400" to Pink400,
        "Pink300" to Pink300,
        "Brown900" to Brown900,
        "Brown700" to Brown700,
        "Brown600" to Brown600,
        "Brown500" to Brown500,
        "Brown400" to Brown400,
        "Brown300" to Brown300,
        "Grey900" to Grey900,
        "Grey700" to Grey700,
        "Grey600" to Grey600,
        "Grey500" to Grey500,
        "Grey400" to Grey400,
        "Grey300" to Grey300
    )

    fun getColorByName(name: String): Color {
        return colorMap[name] ?: DarkBeige // Default to black if the color name is not found
    }
}

@Composable
fun ColorList(
    onColorSelected: (String) -> Unit
) {
    val colorList = listOf(
        "Red900", "Red700", "Red600", "Red500", "Red400", "Red300",
        "Orange900", "Orange700", "Orange600", "Orange500", "Orange400", "Orange300",
        "Yellow900", "Yellow700", "Yellow600", "Yellow500", "Yellow400", "Yellow300",
        "Green900", "Green700", "Green600", "Green500", "Green400", "Green300",
        "Turquoise900", "Turquoise700", "Turquoise600", "Turquoise500", "Turquoise400", "Turquoise300",
        "Blue900", "Blue700", "Blue600", "Blue500", "Blue400", "Blue300",
        "Purple900", "Purple700", "Purple600", "Purple500", "Purple400", "Purple300",
        "Pink900", "Pink700", "Pink600", "Pink500", "Pink400", "Pink300",
        "Brown900", "Brown700", "Brown600", "Brown500", "Brown400", "Brown300",
        "Grey900", "Grey700", "Grey600", "Grey500", "Grey400", "Grey300"
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
    Text(
        text = "$text",
        style = TextStyle(
            fontSize = 16.sp,
            color = Violet,
            fontFamily = Poppins,
            fontWeight = FontWeight.ExtraBold
        ),
        modifier = Modifier
            .padding(start = 16.dp, top = 10.dp)
    )
}



// Modal to edit an entry
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addReadingChallengeAlert(mainViewModel: MainViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()

    if (state.value.openDialogEditReadingChallenge) {

        var title by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(
                TextFieldValue("")
            )
        }
        var days by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(
                TextFieldValue("")
            )
        }
        var bookCount by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(
                TextFieldValue("")
            )
        }

        Dialog(
            onDismissRequest = {
                mainViewModel.dismissReadingChallengeDialog()
            }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.95f),
                colors = CardDefaults.cardColors(containerColor = Violet),
                shape = RoundedCornerShape(10.dp),
            ) {
                Column {
                    Text(
                        text = "Set your reading challenge",
                        style = TextStyle(
                            fontFamily = Calistoga,
                            fontSize = 20.sp,
                            color = NonWhite
                        ),
                        modifier = Modifier
                            .padding(10.dp)
                            .align(CenterHorizontally)
                    )

                    // Input fields for title, days, and amount of books
                    TextField(
                        modifier = Modifier.padding(10.dp),
                        value = title,
                        onValueChange = { newText -> title = newText },
                        label = { Text(text = "Title") },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Violet,
                            backgroundColor = LightBeige,
                            disabledIndicatorColor = LightBeige,
                            focusedIndicatorColor = Violet,
                            errorIndicatorColor = DarkRed
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )


                    Row {
                        TextField(
                            modifier = Modifier
                                .padding(10.dp)
                                .width(130.dp),
                            value = bookCount,
                            onValueChange = { newText -> bookCount = newText },
                            label = { Text(text = "Books") },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Violet,
                                backgroundColor = LightBeige,
                                disabledIndicatorColor = LightBeige,
                                focusedIndicatorColor = Violet,
                                errorIndicatorColor = DarkRed
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )

                        TextField(
                            modifier = Modifier
                                .padding(10.dp)
                                .width(130.dp),
                            value = days,
                            onValueChange = { newText -> days = newText },
                            label = { Text(text = "Days") },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Violet,
                                backgroundColor = LightBeige,
                                disabledIndicatorColor = LightBeige,
                                focusedIndicatorColor = Violet,
                                errorIndicatorColor = DarkRed
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }

                    Icon(
                        painter = painterResource(id = R.drawable.yellowtick),
                        contentDescription = "Back",
                        tint = Yellow,
                        modifier = Modifier
                            .padding(bottom = 20.dp, top = 15.dp)
                            .size(50.dp)
                            .background(NonWhite, CircleShape)
                            .border(4.dp, Yellow, CircleShape)
                            .align(CenterHorizontally)
                            .clickable {
                                val currentDate = LocalDate.now() // Get the current date
                                val formattedDate =
                                    currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                                mainViewModel.saveReadingChallenge(
                                    ReadingChallenge(
                                        title.text,
                                        days.text.toIntOrNull() ?: 0,
                                        bookCount.text.toIntOrNull() ?: 0,
                                        0,
                                        0f,
                                        formattedDate
                                    )
                                )
                            }
                            .size(40.dp)


                    )
                }
            }
        }
    }
}

@Composable
fun EditBook(mainViewModel: MainViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()
    val genres = listOf(
        "Fantasy", "Sci-Fi", "Romance", "New Adult", "Thriller", "Horror", "Erotica",
        "Manga", "Biography", "Novel", "History", "Non-Fiction"
    )
    var selectedGenre by remember { mutableStateOf(genres[0]) }



    if (state.value.openDialogEditBook) {
        var title by rememberSaveable { mutableStateOf(state.value.editBook.title) }
        var author by rememberSaveable { mutableStateOf(state.value.editBook.author) }
        var genre by rememberSaveable { mutableStateOf(state.value.editBook.genre) }
        var color by rememberSaveable { mutableStateOf(state.value.editBook.color) }
        var cover by rememberSaveable { mutableStateOf(state.value.editBook.cover) }
//        var shelf by rememberSaveable { mutableStateOf(state.value.editBook.shelf) }
        var rating by rememberSaveable { mutableStateOf(state.value.editBook.rating) }
        var review by rememberSaveable { mutableStateOf(state.value.editBook.review) }
        var quote by rememberSaveable { mutableStateOf(state.value.editBook.quote) }
        var language by rememberSaveable { mutableStateOf(state.value.editBook.language) }
        var pages by rememberSaveable { mutableStateOf(state.value.editBook.pages) }
        var days by rememberSaveable { mutableStateOf(state.value.editBook.days) }
        var mediaType by rememberSaveable { mutableStateOf(state.value.editBook.mediaType) }


        val shelfList = listOf(
            "Read", "To be Read", "Wishlist"
        )
        var shelfChanged by remember { mutableStateOf(false) }
        var shelf by rememberSaveable { mutableStateOf(state.value.editBook.shelf) }
        var selectedShelf by remember { mutableStateOf(shelfList[0]) }


        // https://developer.android.com/jetpack/compose/components/dialog
        Dialog(
            onDismissRequest = { mainViewModel.dismissDialog() }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
                    .height(480.dp),
                colors = CardDefaults.cardColors(containerColor = Violet),
                shape = RoundedCornerShape(10.dp),
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Edit your Book",
                        style = TextStyle(
                            fontFamily = Calistoga,
                            fontSize = 24.sp,
                            color = NonWhite
                        ),
                        modifier = Modifier.padding(start = 20.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Column(
                    ) {
                        TextField(
                            modifier = Modifier
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                            shape = RoundedCornerShape(8.dp),
                            value = title,
                            onValueChange = { newText -> title = newText },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Violet,
                                backgroundColor = DarkBeige,
                                focusedIndicatorColor = Yellow,
                                unfocusedIndicatorColor = Violet,
                                disabledIndicatorColor = LightBeige,
                                errorIndicatorColor = DarkRed,
                            ),
                            textStyle = TextStyle(
                                fontFamily = Poppins,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Violet
                            )
                        )

                        TextField(
                            modifier = Modifier
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                            shape = RoundedCornerShape(8.dp),
                            value = author,
                            onValueChange = { newText -> author = newText },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Violet,
                                backgroundColor = DarkBeige,
                                focusedIndicatorColor = Yellow,
                                unfocusedIndicatorColor = Violet,
                                disabledIndicatorColor = LightBeige,
                                errorIndicatorColor = DarkRed
                            ),
                            textStyle = TextStyle(
                                fontFamily = Poppins,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Violet
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        StyledTextFieldWithDropdown(
                            items = genres,
                            selectedValue = genre,
                            onValueChange = { newGenre ->
                                // Hier wird das ausgewählte Regal aktualisiert
                                genre = newGenre
                                selectedGenre = newGenre
                            }
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Change the Shelfing", style = TextStyle(
                                fontFamily = Poppins,
                                fontSize = 16.sp,
                                color = NonWhite
                            ),
                            modifier = Modifier.padding(start = 15.dp)
                        )

                        StyledTextFieldWithDropdown(
                            items = shelfList,
                            selectedValue = if (shelfChanged) selectedShelf else shelf,
                            onValueChange = { newShelf ->
                                shelfChanged = true
                                selectedShelf = newShelf
                            }
                        )
                    }

                }

                //Delete and Confirm
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //Delete Book
                    Icon(
                        modifier = Modifier
                            .clickable { }
                            .fillMaxHeight(0.7f),
                        painter = painterResource(id = R.drawable.delte),
                        contentDescription = "Delete Icon",
                        tint = DarkRed
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    //Confirm Button
                    androidx.compose.material.Button(
                        onClick = {
                            mainViewModel.saveBook(
                                Book(
                                    title,
                                    author,
                                    selectedGenre,
                                    color,
                                    cover,
                                    if (shelfChanged) selectedShelf else shelf,
                                    rating,
                                    review,
                                    quote,
                                    language,
                                    pages,
                                    days,
                                    mediaType,
                                    state.value.editBook.id
                                )
                            )
                        }, modifier = Modifier
                            .padding(top = 10.dp)
                            .height(45.dp)
                            .border(2.dp, NonWhite, shape = RoundedCornerShape(20.dp)),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Violet
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.confirm),
                                tint = NonWhite,
                                contentDescription = "Confirm Icon",
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "confirm",
                                fontSize = 16.sp,
                                fontFamily = Poppins,
                                fontWeight = FontWeight.SemiBold,
                                color = NonWhite
                            )
                        }

                    }

                }


            }
        }
    }
}
