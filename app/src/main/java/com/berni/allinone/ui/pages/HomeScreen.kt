package com.berni.allinone.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.berni.allinone.ui.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController, userViewModel: UserViewModel) {
    val name by userViewModel.userName.collectAsState()
    val email by userViewModel.email.collectAsState()
    val photoUrl by userViewModel.photoUrl.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .clickable { expanded = !expanded }
            ) {
                if (!photoUrl.isNullOrBlank()) {
                    Image(
                        painter = rememberAsyncImagePainter(photoUrl),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(64.dp)
                            .padding(end = 8.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Default Icon",
                        modifier = Modifier
                            .size(64.dp)
                            .padding(end = 8.dp)
                    )
                }

                Text(
                    text = name.ifBlank { "User" },
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Profile") },
                        onClick = {
                            expanded = false
                            navController.navigate("profile")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Logout") },
                        onClick = {
                            expanded = false
                            coroutineScope.launch {
                                delay(150)
                                FirebaseAuth.getInstance().signOut()
                                userViewModel.clearUser()
                                navController.navigate("login") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Welcome, $name!", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text("You are logged in as:", style = MaterialTheme.typography.bodyLarge)
                Text(email, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
