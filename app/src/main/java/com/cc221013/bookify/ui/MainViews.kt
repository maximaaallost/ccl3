package com.cc221013.bookify.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cc221013.bookify.R

sealed class Screen(val route: String){
    object First: Screen("first")
    object Second: Screen("second")
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
            startDestination = Screen.First.route
        ){
            composable(Screen.First.route){
                mainViewModel.selectScreen(Screen.First)
                mainScreen(mainViewModel)
            }
            composable(Screen.Second.route){
                mainViewModel.selectScreen(Screen.Second)
                mainViewModel.getStudents()
                displayStudents(mainViewModel)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, selectedScreen: Screen){
    BottomNavigation (
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        NavigationBarItem(
            selected = (selectedScreen == Screen.First),
            onClick = { navController.navigate(Screen.First.route) },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "") })

        NavigationBarItem(
            selected = (selectedScreen == Screen.Second),
            onClick = { navController.navigate(Screen.Second.route) },
            icon = { Icon(imageVector = Icons.Default.AccountBox, contentDescription = "") })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainScreen(mainViewModel: MainViewModel){
    var name by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var uid by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.mainscreen_title),
            fontSize = 50.sp,
            style = TextStyle(fontFamily = FontFamily.Cursive)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.decorative_android_icon)
        )

        Spacer(
            modifier = Modifier.height(50.dp)
        )

        TextField(
            value = name,
            onValueChange = {
                    newText -> name = newText
            },
            label = { Text(text = stringResource(R.string.mainscreen_field_name) ) }
        )

        TextField(
            modifier = Modifier.padding(top = 20.dp),
            value = uid,
            onValueChange = {
                    newText -> uid = newText
            },
            label = {
                Text(text = stringResource(R.string.mainscreen_field_uid))
            }
        )

        Button(
            onClick = { mainViewModel.save(BccStudent(name.text,uid.text)) },
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(text = stringResource(R.string.mainscreen_button_save), fontSize = 20.sp)
        }
    }
}

@Composable
fun displayStudents(mainViewModel: MainViewModel){
    val state = mainViewModel.mainViewState.collectAsState()

    // https://developer.android.com/jetpack/compose/lists
    LazyColumn (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        item{
            Text(
                text = stringResource(R.string.displaystudents_title),
                fontWeight = FontWeight.Bold
            )
        }

        items(state.value.students){
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clickable { mainViewModel.editStudent(it) }
            ){
                Column (modifier = Modifier.weight(1f)) {
                    Text(text = "Name: ${it.name}")
                    Text(text = "UID: ${it.uid}")
                }
                IconButton(onClick = { mainViewModel.clickDelete(it)}) {
                    Icon(Icons.Default.Delete,"Delete")
                }
            }
        }
    }
    Column {
        editStudentModal(mainViewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editStudentModal(mainViewModel: MainViewModel){
    val state = mainViewModel.mainViewState.collectAsState()

    if(state.value.openDialog){
        var name by rememberSaveable { mutableStateOf(state.value.editStudent.name) }
        var uid by rememberSaveable { mutableStateOf(state.value.editStudent.uid) }

        // https://developer.android.com/jetpack/compose/components/dialog
        AlertDialog(
            onDismissRequest = {
                mainViewModel.dismissDialog()
            },
            text = {
                Column {
                    // https://www.jetpackcompose.net/textfield-in-jetpack-compose
                    TextField(
                        modifier = Modifier.padding(top = 20.dp),
                        value = name,
                        onValueChange = { newText -> name = newText },
                        label = { Text(text = stringResource(R.string.editmodal_field_name) ) }
                    )

                    TextField(
                        modifier = Modifier.padding(top = 20.dp),
                        value = uid,
                        onValueChange = { newText -> uid = newText },
                        label = { Text(text = stringResource(R.string.editmodal_field_uid)) }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        mainViewModel.saveStudent(
                            BccStudent(
                                name,
                                uid,
                                state.value.editStudent.id
                            )
                        )
                    }
                ) {
                    Text(stringResource(R.string.editmodal_button_save))
                }
            }
        )
    }
}
