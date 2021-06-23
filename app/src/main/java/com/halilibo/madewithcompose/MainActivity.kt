package com.halilibo.madewithcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.halilibo.madewithcompose.calendar.CalendarDemo
import com.halilibo.madewithcompose.circlesonlines.CirclesOnLinesDemo
import com.halilibo.madewithcompose.dotsandlines.DotsAndLinesDemo
import com.halilibo.madewithcompose.schedulecalendar.ScheduleCalendarDemo
import com.halilibo.madewithcompose.ui.theme.LocalNightMode
import com.halilibo.madewithcompose.ui.theme.MadeWithComposeTheme
import com.halilibo.madewithcompose.videoplayer.VideoPlayerDemo
import com.halilibo.madewithcompose.weightentry.WeightEntryDemo

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MadeWithComposeTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        val currentBackStackEntry = navController.currentBackStackEntryAsState().value
                        val currentRouteName = currentBackStackEntry?.destination?.route
                        val currentRouteTitle = Demo.values().firstOrNull { it.destination == currentRouteName }?.title
                        TopAppBar(
                            title = {
                                Text(text = currentRouteTitle ?: "Home")
                            },
                            actions = {
                                val nightMode = LocalNightMode.current
                                Icon(Icons.Default.LightMode, contentDescription = "")
                                Spacer(modifier = Modifier.width(8.dp))
                                Switch(
                                    checked = nightMode.isNight,
                                    onCheckedChange = { isNight ->
                                        if (isNight) nightMode.setNight() else nightMode.setDay()
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(Icons.Default.DarkMode, contentDescription = "")
                                Spacer(modifier = Modifier.width(16.dp))
                            }
                        )
                    }
                ) {
                    Surface {
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") {
                                HomePage(navController)
                            }
                            Demo.values().forEach { demo ->
                                composable(demo.destination) {
                                    demo.composable()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class Demo(
    val title: String,
    val destination: String,
    val composable: @Composable () -> Unit
) {
    ScheduleCalendar(
        "Schedule Calendar",
        "schedulecalendar",
        @Composable {
            ScheduleCalendarDemo()
        }
    ),
    VideoPlayer(
        "Video Player",
        "videoplayer",
        @Composable {
            VideoPlayerDemo()
        }
    ),
    DotsAndLines(
        "Dots and Lines",
        "dotsandlines",
        @Composable {
            DotsAndLinesDemo()
        }
    ),
    Calendar(
        "Calendar",
        "calendar",
        @Composable {
            CalendarDemo()
        }
    ),
    CirclesOnLines(
        "Circles on Lines",
        "circlesonlines",
        @Composable {
            CirclesOnLinesDemo()
        }
    ),
    WeightEntry(
        "Weight Entry",
        "weightentry",
        @Composable {
            WeightEntryDemo()
        }
    )
}

@Composable
fun HomePage(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(Demo.values()) { demo ->
            Card(
                Modifier
                    .padding(8.dp)
                    .clickable {
                        navController.navigate(demo.destination)
                    }) {
                Text(
                    text = demo.title,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    }
}
