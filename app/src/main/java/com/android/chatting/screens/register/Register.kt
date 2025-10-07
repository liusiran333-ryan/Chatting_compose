package com.android.chatting.screens.register

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.chatting.R
import coil.compose.rememberAsyncImagePainter
import com.android.chatting.AppScreen
import com.android.chatting.ui.components.HeightSpacer
import com.android.chatting.ui.utils.LocalNavController
import com.android.chatting.ui.utils.popUpAllBackStackEntry

enum class FocusField {
    USERNAME,
    PASSWORD,
    REPEAT_PASSWORD,
    DEFAULT
}

@Composable
fun Register() {
    val navController = LocalNavController.current

    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var userName by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var repeatPassword by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var focusedTextField by rememberSaveable { mutableStateOf(FocusField.DEFAULT) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageUri = it
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 35.dp, vertical = 48.dp)
        ) {
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            HeightSpacer(15.dp)
            Box {
                Box(
                    modifier = Modifier
                        .size(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(2.dp, Color(0xFF0079D3), CircleShape)
                            .background(color = Color.Gray, shape = CircleShape),
                        shape = CircleShape,
                    ) {
                        Image(
                            painter = if (imageUri != null) {
                                rememberAsyncImagePainter(imageUri)
                            } else {
                                painterResource(id = R.drawable.ava1)
                            },
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable{
                                launcher.launch("image/*")
                            }

                        )
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.camera),
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.BottomEnd),
                    contentDescription = null
                )
            }
            HeightSpacer(12.dp)
            OutlinedTextField(
                value = userName,
                onValueChange = {
                    userName = it
                },
                colors = OutlinedTextFieldDefaults.colors(),
                label = { Text("UserName") },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (it.isFocused) {
                            focusedTextField = FocusField.USERNAME
                        }
                    },
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                isError = (userName.isEmpty() && focusedTextField == FocusField.USERNAME),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Ascii
                )
            )
            Box(
                modifier = Modifier
                    .height(20.dp)
            ) {
                if (focusedTextField == FocusField.USERNAME) {
                    Text(
                        text = if (userName.isEmpty()) "UserName can't be blank" else "You can use this UserName!",
                        color = if (userName.isNotEmpty()) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.error,
                        fontStyle = FontStyle.Italic,
                        fontSize = 10.sp
                    )
                }
            }
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                colors = OutlinedTextFieldDefaults.colors(),
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (it.isFocused) {
                            focusedTextField = FocusField.PASSWORD
                        }
                    },
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                isError = (password.isEmpty() && focusedTextField == FocusField.PASSWORD),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Ascii
                ),
                visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordHidden = !passwordHidden
                        }
                    ) {
                        Icon(painterResource(id = R.drawable.visibility), null)
                    }
                }
            )
            Box(
                modifier = Modifier
                    .height(20.dp)
            ) {
                if (focusedTextField == FocusField.PASSWORD) {
                    Text(
                        text = if (password.isEmpty()) "Password can't be blank" else "You can use this Password!",
                        color = if (password.isNotEmpty()) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.error,
                        fontStyle = FontStyle.Italic,
                        fontSize = 10.sp
                    )
                }
            }
            OutlinedTextField(
                value = repeatPassword,
                onValueChange = {
                    repeatPassword = it
                },
                colors = OutlinedTextFieldDefaults.colors(),
                label = { Text("Repeat Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (it.isFocused) {
                            focusedTextField = FocusField.REPEAT_PASSWORD
                        }
                    },
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                isError = ((repeatPassword.isEmpty() || repeatPassword != password) && focusedTextField == FocusField.REPEAT_PASSWORD),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Ascii
                ),
                visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordHidden = !passwordHidden
                        }
                    ) {
                        Icon(painterResource(id = R.drawable.visibility), null)
                    }
                }
            )
            Box(
                modifier = Modifier
                    .height(20.dp)
            ) {
                if (focusedTextField == FocusField.REPEAT_PASSWORD) {
                    Text(
                        text =
                            if (repeatPassword.isEmpty())
                                "Password can't be blank"
                            else if (repeatPassword != password)
                                "Passwords don't equal"
                            else
                                "You can use this Password!",
                        color = if (repeatPassword.isNotEmpty() && repeatPassword == password) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.error,
                        fontStyle = FontStyle.Italic,
                        fontSize = 10.sp
                    )
                }
            }
            HeightSpacer(18.dp)
            Button(
                onClick = {
                    navController.navigate(AppScreen.MAIN) {
                        popUpAllBackStackEntry(navController)
                    }
                },
                enabled = (password == repeatPassword && password.isNotEmpty() && userName.isNotEmpty()),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0079D3)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                elevation = ButtonDefaults.buttonElevation(10.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Finish register", color = Color.White)
            }
        }
    }
}