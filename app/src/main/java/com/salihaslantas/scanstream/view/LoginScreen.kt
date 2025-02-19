package com.salihaslantas.scanstream.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.salihaslantas.scanstream.R
import com.salihaslantas.scanstream.viewmodel.AuthenticationViewModel
import com.salihaslantas.scanstream.viewmodel.SignInResult

@Composable
fun LoginScreen(
    navController: NavController,
    authenticationViewModel: AuthenticationViewModel = viewModel()
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val signInState by authenticationViewModel.signInState.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.box),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Fit
            )
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            authenticationViewModel.signIn(email, password) { role ->
                                if (signInState == SignInResult.Success) {
                                    when (role) {
                                        "manager" -> navController.navigate("manager_screen")
                                        "operator" -> navController.navigate("operator_screen")
                                        else -> Toast.makeText(
                                            context,
                                            "Unknown role",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Sign In")
                    }
                    // Show Loading or Error Messages
                    when (signInState) {
                        is SignInResult.Loading -> CircularProgressIndicator(
                            modifier = Modifier.align(
                                Alignment.CenterHorizontally
                            )
                        )

                        is SignInResult.Error -> Text(
                            text = (signInState as SignInResult.Error).message,
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        else -> {}
                    }

                    Text(
                        text = "If you have forgotten your password, please contact your manager.",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}