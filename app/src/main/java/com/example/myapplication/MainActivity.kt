package com.example.myapplication

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myapplication.ui.theme.MyApplicationTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MyApplicationTheme {
                MovieNavigation()
            }
        }
    }
}

// Preview remains the same
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        MovieNavigation()
    }
}

@Composable
fun MovieNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MovieScreens.HomeScreen.name) {
        composable(MovieScreens.HomeScreen.name) {
            HomeScreen(navController)
        }
        composable(
            route = MovieScreens.DetailsScreen.name + "/{movie}",
            arguments = listOf(navArgument("movie") { type = NavType.StringType })
        ) { backStackEntry ->
            DetailsScreen(navController = navController, movieId = backStackEntry.arguments?.getString("movie"))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Yellow),
                title = { Text(text = "Movies") }
            )
        }
    ) { paddingValues ->
        MainContent(navController = navController, modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun MainContent(navController: NavController, movieList: List<Movie> = getMovies(), modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(12.dp).background(color = Color.Yellow)) {
        LazyColumn {
            items(items = movieList) { movie ->
                MovieRow(
                    movie = movie,
                    onItemClick = { selectedMovie ->
                        navController.navigate(route = MovieScreens.DetailsScreen.name + "/$selectedMovie")
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavController, movieId: String?) {
    val movie = getMovies().firstOrNull { it.id == movieId } ?: return
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Yellow),
                title = {
                    Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.background(color = Color.Yellow)) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Arrow",
                            modifier = Modifier.clickable { navController.popBackStack() }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "Movies", style = TextStyle(color = Color.Black))
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier
            .padding(paddingValues)
            .background(color = Color.Yellow)
            .fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top

            ) {
                MovieRow(movie = movie)
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                Text(text = "Movie Images")
                HorizontalScrollableImageView(images = movie.images)
            }
        }
    }
}

@Composable
fun HorizontalScrollableImageView(images: List<String>) {
    LazyRow {
        items(images) { image ->
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .background(color = Color.Yellow)
                    .size(240.dp)
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = "Movie Image",

                    modifier = Modifier.fillMaxHeight()
                        .background(color = Color.Yellow)
                )
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie, onItemClick: (String) -> Unit = {}) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(color= Color.Yellow)
            .clickable { onItemClick(movie.id) }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = movie.poster,
                contentDescription = "Movie Poster",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = movie.title, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Director: ${movie.director}", style = TextStyle(fontSize = 16.sp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Year: ${movie.year}", style = TextStyle(fontSize = 16.sp))
                Text(text = "Actors: ${movie.actors}", style = TextStyle(fontSize = 16.sp))
                Text(text = "Plot: ${movie.plot}", style = TextStyle(fontSize = 16.sp))
                Text(text = "Rating: ${movie.rating}", style = TextStyle(fontSize = 16.sp))
            }
        }
    }
}















