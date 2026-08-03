package com.zumo.app.ui.auth

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zumo.app.ui.components.ZumoTextField
import com.zumo.app.ui.theme.*

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var isLogin by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val accent = LocalZumoAccent.current

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) onLoginSuccess()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedContent(
                targetState = isLogin,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(400)) + slideInVertically { it / 2 }) togetherWith
                    (fadeOut(animationSpec = tween(300)) + slideOutVertically { -it / 2 })
                },
                label = "title"
            ) { login ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "ZUMO",
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Black,
                        color = accent.primary,
                        letterSpacing = 8.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (login) "Welcome back" else "Create account",
                        color = ZumoTextSecondary,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            GlassSurface(
                modifier = Modifier.fillMaxWidth(),
                glowColor = accent.glow,
                cornerRadius = 20.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!isLogin) {
                        ZumoTextField(
                            value = username,
                            onValueChange = { username = it; viewModel.clearError() },
                            placeholder = "Username",
                            accent = accent,
                            imeAction = ImeAction.Next
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                    }

                    ZumoTextField(
                        value = email,
                        onValueChange = { email = it; viewModel.clearError() },
                        placeholder = "Email",
                        accent = accent,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    ZumoTextField(
                        value = password,
                        onValueChange = { password = it; viewModel.clearError() },
                        placeholder = "Password",
                        accent = accent,
                        keyboardType = KeyboardType.Password,
                        imeAction = if (isLogin) ImeAction.Done else ImeAction.Next,
                        isPassword = !showPassword,
                        trailingIcon = {
                            TextButton(onClick = { showPassword = !showPassword }) {
                                Text(
                                    text = if (showPassword) "Hide" else "Show",
                                    color = accent.primary.copy(alpha = 0.7f),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    )

                    AnimatedVisibility(
                        visible = uiState.error != null,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        Text(
                            text = uiState.error ?: "",
                            color = ZumoAccents.RoseEmber.primary,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (email.isBlank() || password.isBlank()) return@Button
                            if (!isLogin && username.isBlank()) return@Button
                            if (isLogin) viewModel.login(email.trim(), password)
                            else viewModel.signup(email.trim(), username.trim(), password)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        enabled = !uiState.isLoading,
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = accent.primary,
                            contentColor = ZumoTextPrimary,
                            disabledContainerColor = accent.primary.copy(alpha = 0.4f)
                        )
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                color = ZumoTextPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = if (isLogin) "Sign In" else "Create Account",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isLogin) "No account? " else "Have an account? ",
                            color = ZumoTextSecondary,
                            fontSize = 14.sp
                        )
                        TextButton(onClick = {
                            isLogin = !isLogin
                            viewModel.clearError()
                        }) {
                            Text(
                                text = if (isLogin) "Sign up" else "Sign in",
                                color = accent.primary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(visible = isLogin) {
                TextButton(onClick = {}) {
                    Text(
                        text = "Forgot password?",
                        color = accent.primary.copy(alpha = 0.6f),
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
