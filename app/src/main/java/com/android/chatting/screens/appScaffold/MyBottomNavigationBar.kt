package com.android.chatting.screens.appScaffold

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Contacts
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.chatting.R

@Composable
fun MyBottomNavigation(
    selectedScreen: Int,
    onClick: (targetIndex: Int) -> Unit
) {
    NavigationBar(
        modifier = Modifier.navigationBarsPadding()
    ) {
        BottomScreen.entries.forEachIndexed { index, screen ->
            NavigationBarItem(
                selected = selectedScreen == index,
                onClick = { onClick(index) },
                icon = {
                    Icon(
                        imageVector = if (selectedScreen == index) screen.selectedIcon else screen.unselectedIcon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(stringResource(id = screen.label)) }
            )
        }
    }
}

enum class BottomScreen(
    @StringRes val label: Int,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
) {
    Message(R.string.message, Icons.Outlined.ChatBubbleOutline, Icons.Filled.ChatBubble),
    Contact(R.string.contact, Icons.Outlined.Contacts, Icons.Filled.Contacts),
    Explore(R.string.explore, Icons.Outlined.Explore, Icons.Filled.Explore)
}