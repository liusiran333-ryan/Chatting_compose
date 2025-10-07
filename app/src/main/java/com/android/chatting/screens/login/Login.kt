package com.android.chatting.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.chatting.AppScreen
import com.android.chatting.R
import com.android.chatting.ui.components.CenterRow
import com.android.chatting.ui.components.HeightSpacer
import com.android.chatting.ui.components.WidthSpacer
import com.android.chatting.ui.utils.LocalNavController

@Composable
fun Login() {
    val navController = LocalNavController.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var userName by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 48.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
        ) {
            Text(
                text = "Chatting",
                fontSize = 64.sp,
                color = Color(0xFF0E4A86),
                fontFamily = FontFamily.Cursive
            )
            HeightSpacer(20.dp)
            OutlinedTextField(
                value = userName,
                onValueChange = {
                    userName = it
                },
                colors = OutlinedTextFieldDefaults.colors(),
                label = {
                    Text("UserName")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            keyboardController?.hide()
                        }
                    ) {
                        Icon(painterResource(R.drawable.expand), null)
                    }
                },
                singleLine = true
            )
            HeightSpacer(10.dp)
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                colors = OutlinedTextFieldDefaults.colors(),
                label = {
                    Text("Password")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordHidden = !passwordHidden
                        }
                    ) {
                        Icon(painterResource(R.drawable.visibility), null)
                    }
                },
                singleLine = true
            )
            HeightSpacer(20.dp)
            Button(
                onClick = {
                    navController.navigate(AppScreen.MAIN) {
                        popUpTo(AppScreen.LOGIN) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(painterResource(R.drawable.login), null)
                WidthSpacer(5.dp)
                Text("Enter")
            }
            HeightSpacer(15.dp)
            CenterRow {
                Text(
                    text = "Forgot password?",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Sign up",
                    style = MaterialTheme.typography.labelLarge,
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.clickable(
                        onClick = {
                            navController.navigate(AppScreen.REGISTER)
                        },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
                )
            }
        }
    }
}