package com.berni.allinone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.berni.allinone.ui.Navigation
import com.berni.allinone.ui.theme.AllInOneTheme
import com.berni.allinone.ui.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AllInOneTheme {
                val navController = rememberNavController()
                val userViewModel: UserViewModel = viewModel()
                Navigation(navController, userViewModel)
            }
        }
    }
}
