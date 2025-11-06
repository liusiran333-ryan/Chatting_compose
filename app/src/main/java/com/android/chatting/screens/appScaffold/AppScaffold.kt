package com.android.chatting.screens.appScaffold

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.chatting.screens.customized.LineChartDemo
import com.android.chatting.screens.drawer.PersonalProfile
import com.android.chatting.screens.home.Home
import com.android.chatting.screens.login.Login
import com.android.chatting.screens.register.Register
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AppScaffold() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    var selectedScreen by remember { mutableIntStateOf(0) }

    ModalNavigationDrawer(
        drawerContent = {
            PersonalProfile()
        },
        drawerState = drawerState,
        modifier = Modifier.navigationBarsPadding()
    ) {
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
                contentPadding = PaddingValues(
                    top = 0.dp,
                    bottom = it.calculateBottomPadding()
                )
            ) { page ->
                when(BottomScreen.entries[page]) {
                    BottomScreen.Message -> Home(drawerState)
                    BottomScreen.Contact -> LineChartDemo()
                    BottomScreen.Explore -> Login()
                }
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedScreen = page
        }
    }

    BackHandler(drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }
}