package com.zumo.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zumo.app.ui.auth.AuthScreen
import com.zumo.app.ui.conversations.ConversationsScreen
import com.zumo.app.ui.chat.ChatScreen
import com.zumo.app.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ZUMO", "MainActivity onCreate")
        
        try {
            enableEdgeToEdge()
            setContent {
                ZumoTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        val navController = rememberNavController()
                        CubesBackground {
                            NavHost(
                                navController = navController,
                                startDestination = "auth"
                            ) {
                                composable("auth") {
                                    AuthScreen(
                                        onLoginSuccess = {
                                            Log.d("ZUMO", "Login success, navigating to conversations")
                                            try {
                                                navController.navigate("conversations") {
                                                    popUpTo("auth") { inclusive = true }
                                                }
                                            } catch (e: Exception) {
                                                Log.e("ZUMO", "Navigation failed", e)
                                            }
                                        }
                                    )
                                }
                                composable("conversations") {
                                    ConversationsScreen(
                                        onConversationClick = { conversationId ->
                                            navController.navigate("chat/$conversationId")
                                        }
                                    )
                                }
                                composable("chat/{conversationId}") { backStackEntry ->
                                    val conversationId = backStackEntry.arguments?.getString("conversationId") ?: ""
                                    ChatScreen(
                                        conversationId = conversationId,
                                        onBack = { navController.popBackStack() }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("ZUMO", "FATAL in onCreate", e)
            throw e
        }
    }
}
