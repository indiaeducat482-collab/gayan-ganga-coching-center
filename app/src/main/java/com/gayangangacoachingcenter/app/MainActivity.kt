package com.gayangangacoachingcenter.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

private val Purple = Color(0xFF5B35D5)
private val PurpleDark = Color(0xFF4525A4)
private val Bg = Color(0xFFF8F7FC)

data class Feature(
    val title: String,
    val icon: ImageVector
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GayanGangaApp()
        }
    }
}

@Composable
fun GayanGangaApp() {

    var showAuth by remember {
        mutableStateOf(
            FirebaseAuth.getInstance().currentUser == null
        )
    }

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Purple,
            secondary = Color(0xFFF97316),
            background = Bg
        )
    ) {

        if (showAuth) {

            AuthScreen(
                onSuccess = {
                    showAuth = false
                }
            )

        } else {

            MainDashboard(
                onLogout = {
                    FirebaseAuth.getInstance().signOut()
                    showAuth = true
                }
            )
        }
    }
}

@Composable
fun AuthScreen(
    onSuccess: () -> Unit
) {

    var isRegister by remember {
        mutableStateOf(false)
    }

    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var error by remember {
        mutableStateOf("")
    }

    var loading by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Bg),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = Purple,
                    modifier = Modifier.size(58.dp)
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = "GAYAN GANGA",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    color = Purple
                )

                Text(
                    text = "COCHING CENTER",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                if (isRegister) {

                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        label = {
                            Text("Student Name")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = {
                        Text("Email")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = {
                        Text("Password")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                if (error.isNotBlank()) {

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                Button(
                    onClick = {

                        if (
                            email.isBlank() ||
                            password.length < 6
                        ) {

                            error =
                                "Valid email aur minimum 6 character password dijiye."

                            return@Button
                        }

                        loading = true

                        val task = if (isRegister) {

                            FirebaseAuth
                                .getInstance()
                                .createUserWithEmailAndPassword(
                                    email.trim(),
                                    password
                                )

                        } else {

                            FirebaseAuth
                                .getInstance()
                                .signInWithEmailAndPassword(
                                    email.trim(),
                                    password
                                )
                        }

                        task.addOnCompleteListener {

                            loading = false

                            if (it.isSuccessful) {

                                onSuccess()

                            } else {

                                error =
                                    it.exception?.localizedMessage
                                        ?: "Authentication failed"
                            }
                        }
                    },
                    enabled = !loading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {

                    Text(
                        text = when {

                            loading ->
                                "Please wait..."

                            isRegister ->
                                "CREATE ACCOUNT"

                            else ->
                                "LOGIN"
                        }
                    )
                }

                TextButton(
                    onClick = {

                        isRegister = !isRegister
                        error = ""
                    }
                ) {

                    Text(
                        text =
                            if (isRegister)
                                "Already have an account? Login"
                            else
                                "New student? Create account"
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDashboard(
    onLogout: () -> Unit
) {

    var selectedTab by remember {
        mutableIntStateOf(0)
    }

    val user =
        FirebaseAuth.getInstance().currentUser

    val displayName =
        user?.email
            ?.substringBefore("@")
            ?.replaceFirstChar {
                it.uppercase()
            }
            ?: "Student"

    val features = listOf(

        Feature(
            "Paid Classes",
            Icons.Default.PlayCircle
        ),

        Feature(
            "Free Courses",
            Icons.Default.School
        ),

        Feature(
            "Free Weekly Test",
            Icons.Default.AssignmentTurnedIn
        ),

        Feature(
            "Books",
            Icons.Default.MenuBook
        ),

        Feature(
            "PDF Class Notes",
            Icons.Default.PictureAsPdf
        ),

        Feature(
            "Paid Test Series",
            Icons.Default.Quiz
        ),

        Feature(
            "Syllabus / Previous Year",
            Icons.Default.LibraryBooks
        ),

        Feature(
            "Create Test",
            Icons.Default.EditNote
        ),

        Feature(
            "Daily Quiz",
            Icons.Default.CheckCircle
        )
    )

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "GAYAN GANGA",
                        fontWeight = FontWeight.Bold
                    )
                },

                navigationIcon = {

                    IconButton(
                        onClick = {}
                    ) {

                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },

                actions = {

                    IconButton(
                        onClick = {}
                    ) {

                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications"
                        )
                    }

                    IconButton(
                        onClick = onLogout
                    ) {

                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile"
                        )
                    }
                },

                colors =
                    TopAppBarDefaults.topAppBarColors(

                        containerColor = Purple,

                        titleContentColor = Color.White,

                        navigationIconContentColor =
                            Color.White,

                        actionIconContentColor =
                            Color.White
                    )
            )
        },

        bottomBar = {

            NavigationBar {

                NavigationBarItem(

                    selected = selectedTab == 0,

                    onClick = {
                        selectedTab = 0
                    },

                    icon = {
                        Icon(
                            Icons.Default.Home,
                            null
                        )
                    },

                    label = {
                        Text("Home")
                    }
                )

                NavigationBarItem(

                    selected = selectedTab == 1,

                    onClick = {
                        selectedTab = 1
                    },

                    icon = {
                        Icon(
                            Icons.Default.VideoLibrary,
                            null
                        )
                    },

                    label = {
                        Text("Courses")
                    }
                )

                NavigationBarItem(

                    selected = selectedTab == 2,

                    onClick = {
                        selectedTab = 2
                    },

                    icon = {
                        Icon(
                            Icons.Default.Quiz,
                            null
                        )
                    },

                    label = {
                        Text("Tests")
                    }
                )

                NavigationBarItem(

                    selected = selectedTab == 3,

                    onClick = {
                        selectedTab = 3
                    },

                    icon = {
                        Icon(
                            Icons.Default.Person,
                            null
                        )
                    },

                    label = {
                        Text("Profile")
                    }
                )
            }
        }

    ) { pad ->

        if (selectedTab == 0) {

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(pad)
                    .background(Bg)
            ) {

                Text(
                    text = "Hello, $displayName",

                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical = 16.dp
                    ),

                    fontSize = 22.sp,

                    fontWeight = FontWeight.Bold
                )

                Card(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),

                    shape = RoundedCornerShape(20.dp),

                    colors =
                        CardDefaults.cardColors(
                            containerColor = PurpleDark
                        )
                ) {

                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(
                                text = "GAYAN GANGA",

                                color = Color.White,

                                fontSize = 25.sp,

                                fontWeight =
                                    FontWeight.Bold
                            )

                            Text(
                                text =
                                    "COCHING CENTER",

                                color =
                                    Color(0xFFFFD66B),

                                fontWeight =
                                    FontWeight.Bold
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(6.dp)
                            )

                            Text(
                                text =
                                    "Learn • Practice • Achieve",

                                color =
                                    Color.White.copy(
                                        alpha = .9f
                                    )
                            )
                        }

                        Icon(
                            imageVector =
                                Icons.Default.School,

                            contentDescription = null,

                            tint = Color.White,

                            modifier =
                                Modifier.size(64.dp)
                        )
                    }
                }

                Text(
                    text = "Explore Learning",

                    modifier = Modifier.padding(
                        start = 20.dp,
                        top = 18.dp,
                        end = 20.dp,
                        bottom = 10.dp
                    ),

                    fontSize = 19.sp,

                    fontWeight =
                        FontWeight.Bold
                )

                LazyVerticalGrid(

                    columns =
                        GridCells.Fixed(3),

                    contentPadding =
                        PaddingValues(
                            horizontal = 12.dp,
                            vertical = 6.dp
                        ),

                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(10.dp)
                ) {

                    items(features) { feature ->

                        FeatureCard(feature)
                    }
                }
            }

        } else {

            CenterContent(

                when (selectedTab) {

                    1 ->
                        "Courses"

                    2 ->
                        "Tests & Quiz"

                    else ->
                        "Student Profile"
                }
            )
        }
    }
}

@Composable
fun FeatureCard(
    feature: Feature
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .height(132.dp)
            .clickable {},

        shape =
            RoundedCornerShape(16.dp),

        colors =
            CardDefaults.cardColors(
                containerColor = Color.White
            ),

        elevation =
            CardDefaults.cardElevation(3.dp)
    ) {

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Center
        ) {

            Icon(

                imageVector =
                    feature.icon,

                contentDescription = null,

                tint = Purple,

                modifier =
                    Modifier.size(43.dp)
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            Text(

                text =
                    feature.title,

                fontSize = 12.sp,

                fontWeight =
                    FontWeight.SemiBold,

                lineHeight =
                    15.sp
            )
        }
    }
}

@Composable
fun CenterContent(
    title: String
) {

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(Bg),

        contentAlignment =
            Alignment.Center
    ) {

        Column(
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Icon(

                imageVector =
                    Icons.Default.School,

                contentDescription = null,

                tint = Purple,

                modifier =
                    Modifier.size(72.dp)
            )

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Text(

                text = title,

                fontSize = 24.sp,

                fontWeight =
                    FontWeight.Bold
            )

            Text(
                text =
                    "Firebase data yahan load hoga.",

                color =
                    Color.Gray
            )
        }
    }
}
