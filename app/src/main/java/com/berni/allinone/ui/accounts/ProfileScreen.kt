package com.berni.allinone.ui.accounts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
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

@Composable
fun ProfileScreen(navController: NavController, userViewModel: UserViewModel) {
    val name by userViewModel.userName.collectAsState()
    val email by userViewModel.email.collectAsState()
    val phone by userViewModel.phone.collectAsState()
    val photoPath by userViewModel.photoUrl.collectAsState()

    var expanded by remember { mutableStateOf(false) }

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
                if (!photoPath.isNullOrBlank()) {
                    Image(
                        painter = rememberAsyncImagePainter("file://$photoPath"),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(64.dp)
                            .padding(end = 8.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Icon",
                        modifier = Modifier.size(64.dp)
                    )
                }

                Text(text = name.ifBlank { "Unknown User" }, style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Profile") },
                        onClick = { expanded = false }
                    )
                    DropdownMenuItem(
                        text = { Text("Logout") },
                        onClick = {
                            FirebaseAuth.getInstance().signOut()
                            userViewModel.clearUser()
                            navController.navigate("login") {
                                popUpTo("profile") { inclusive = true }
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(horizontalAlignment = Alignment.Start) {
                Text("Full Name:", style = MaterialTheme.typography.bodyLarge)
                Text(name.ifBlank { "N/A" }, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(16.dp))

                Text("Email Address:", style = MaterialTheme.typography.bodyLarge)
                Text(email.ifBlank { "N/A" }, style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(16.dp))

                Text("Phone Number:", style = MaterialTheme.typography.bodyLarge)
                Text(phone.ifBlank { "N/A" }, style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { navController.navigate("editProfile") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Edit Profile")
            }
        }
    }
}
