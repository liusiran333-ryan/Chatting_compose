package com.android.chatting.screens.appScaffold

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.android.chatting.screens.login.Login
import com.android.chatting.screens.register.Register
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AppScaffold() {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    var selectedScreen by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            MyBottomNavigation(
                selectedScreen = selectedScreen,
                onClick = {
                    scope.launch {
                        pagerState.scrollToPage(it)
                    }
                }
            )
        }
    ) {
        HorizontalPager(
            count = BottomScreen.entries.size,
            state = pagerState,
            userScrollEnabled = false,
            contentPadding = it
        ) { page ->
            when(BottomScreen.entries[page]) {
                BottomScreen.Message -> Register()
                BottomScreen.Contact -> Register()
                BottomScreen.Explore -> Login()
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedScreen = page
        }
    }
}