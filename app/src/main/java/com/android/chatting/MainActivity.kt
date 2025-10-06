package com.android.chatting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.composable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.android.chatting.screens.login.Login
import com.android.chatting.ui.theme.ChattingTheme
import com.android.chatting.ui.utils.LocalNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChattingTheme {
                val navController = rememberNavController()

                CompositionLocalProvider(
                    LocalNavController provides navController
                ) {
                    ChattyNavHost(navController)
                }
            }
        }
    }
}

@Composable
fun ChattyNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.LOGIN
    ) {
        composable(
            AppScreen.LOGIN
        ) {
            Login()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChattingTheme {
        Greeting("Android")
    }
}

object AppScreen {
    const val LOGIN = "login"
}