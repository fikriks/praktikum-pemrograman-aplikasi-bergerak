package com.example.absen.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.example.absen.R
import com.example.absen.ViewModel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    val user = authViewModel.auth.currentUser
    var userInfo by remember { mutableStateOf<Map<String, Any>?>(null) }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(user) {
        user?.let {
            authViewModel.fetchUserInfo(
                onSuccess = { data ->
                    userInfo = data
                    loading = false
                },
                onFailure = { exception ->
                    errorMessage = exception.message
                    loading = false
                }
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profil") },
                actions = {
                    Button(
                        onClick = {
                            authViewModel.signOut()
                            navController.navigate("login") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.logout),
                            contentDescription = "Logout",
                            Modifier.size(24.dp)
                        )
                    }
                }
            )
        },
        content = { contentPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    if (userInfo != null) {
                        ProfileImage(name = userInfo?.get("nama") as String)
                    } else {
                        ProfileImage(name = "John Doe")
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    // Informasi Pengguna
                    if (loading) {
                        CircularProgressIndicator()
                    } else if (errorMessage != null) {
                        Text(text = "Error: $errorMessage")
                    } else if (userInfo != null) {
                        UserInfo(
                            name = userInfo?.get("nama") as String,
                            email = user?.email ?: "",
                            kelas = userInfo?.get("kelas") as String
                        )
                    } else {
                        Text(text = "User data not found")
                    }
                }
            }
        }
    )
}

@Composable
fun ProfileImage(name: String) {
    val modifiedName = name.replace(" ", "%")
    val url = "https://ui-avatars.com/api/?name=${modifiedName}"

    SubcomposeAsyncImage(
        model = url,
        contentDescription = "Profile Picture",
        modifier = Modifier
            .size(120.dp)
            .padding(8.dp).clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun UserInfo(name: String, email: String, kelas: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = email,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = kelas,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreen(navController = rememberNavController(), authViewModel = AuthViewModel())
}
