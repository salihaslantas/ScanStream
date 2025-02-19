package com.salihaslantas.scanstream

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.salihaslantas.scanstream.ui.theme.ScanStreamTheme
import com.salihaslantas.scanstream.view.LoginScreen
import com.salihaslantas.scanstream.viewmodel.AuthenticationViewModel

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        auth = Firebase.auth
        db = Firebase.firestore

        setContent {
            ScanStreamTheme {
                Scaffold { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        val navController = rememberNavController()
                        val currentUser = auth.currentUser
                        NavHost(
                            navController = navController,
                            startDestination = "start_destination"
                        ) {
                            composable("start_destination") {
                                if (currentUser != null) {

                                } else {
                                    LoginScreen(
                                        authenticationViewModel = AuthenticationViewModel(
                                            Application()
                                        ), navController = navController
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}


