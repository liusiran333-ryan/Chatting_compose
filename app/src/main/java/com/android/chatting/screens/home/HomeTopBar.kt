package com.android.chatting.screens.home

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.chatting.R
import com.android.chatting.ui.components.CircleShapeImage
import com.android.chatting.ui.theme.LocalChattingColors
import com.android.chatting.ui.theme.chattingColors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    val chattingColors = LocalChattingColors.current

    CenterAlignedTopAppBar(
        title = {
            Text("Chatting", color = MaterialTheme.chattingColors.textColor)
        },
        actions = {
            IconButton(
                onClick = { chattingColors.toggleTheme() }
            ) {
                Icon(
                    imageVector = if (chattingColors.isLight) Icons.Rounded.DarkMode else Icons.Rounded.LightMode,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            ) {
                CircleShapeImage(size = 40.dp, painter = painterResource(id = R.drawable.ava4))
            }
        },
        modifier = Modifier.statusBarsPadding()
    )
}