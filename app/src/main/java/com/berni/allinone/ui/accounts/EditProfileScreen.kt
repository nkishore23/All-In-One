package com.berni.allinone.ui.accounts

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.berni.allinone.ui.viewmodel.UserViewModel
import com.berni.allinone.Helper.saveImageToInternalStorage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController, userViewModel: UserViewModel) {
    val context = LocalContext.current

    val currentName by userViewModel.userName.collectAsState()
    val currentEmail by userViewModel.email.collectAsState()
    val currentPhone by userViewModel.phone.collectAsState()
    val currentPhotoPath by userViewModel.photoUrl.collectAsState()

    var name by remember { mutableStateOf(currentName) }
    var phone by remember { mutableStateOf(currentPhone) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var localPhotoPath by remember { mutableStateOf(currentPhotoPath) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            // Save image to internal storage and update local path
            val savedPath = saveImageToInternalStorage(context, uri, "profile_photo.jpg")
            if (savedPath != null) {
                localPhotoPath = savedPath
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.BottomEnd
            ) {
                when {
                    selectedImageUri != null -> {
                        Image(
                            painter = rememberAsyncImagePainter(selectedImageUri),
                            contentDescription = "Selected Profile Photo",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    !localPhotoPath.isNullOrBlank() -> {
                        Image(
                            painter = rememberAsyncImagePainter("file://$localPhotoPath"),
                            contentDescription = "Current Profile Photo",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    else -> {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Default Profile Icon",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Change Photo",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    userViewModel.setUser(
                        name = name,
                        email = currentEmail,
                        phone = phone,
                        photoUrl = localPhotoPath ?: ""
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }
        }
    }
}
