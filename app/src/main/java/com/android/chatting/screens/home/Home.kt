package com.android.chatting.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.android.chatting.mock.displayMessages
import com.android.chatting.ui.theme.chattingColors

@Composable
fun Home(drawerState: DrawerState) {
    Scaffold(
        topBar = {
            HomeTopBar(drawerState)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.chattingColors.backgroundColor)
        ) {
            itemsIndexed(
                displayMessages, key = { _, item ->
                    item.mid
                }
            ) { _, item ->
                FriendMessageItem(item.userProfile, item.lastMsg, item.unreadCount)
            }
        }
    }
}