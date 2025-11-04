package com.android.chatting.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.android.chatting.AppScreen
import com.android.chatting.bean.UserProfileData
import com.android.chatting.ui.components.CenterRow
import com.android.chatting.ui.components.CircleShapeImage
import com.android.chatting.ui.components.NumberChips
import com.android.chatting.ui.components.WidthSpacer
import com.android.chatting.ui.theme.chattingColors
import com.android.chatting.ui.utils.LocalNavController
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun FriendMessageItem(
    userProfileData: UserProfileData,
    lastMsg: String,
    unreadCound: Int
) {
    val navController = LocalNavController.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("${AppScreen.CONVERSATION}/${userProfileData.uid}")
            }
    ) {
        CenterRow(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
        ) {
            CircleShapeImage(60.dp, painter = painterResource(id = userProfileData.avatarRes))
            Spacer(Modifier.padding(horizontal = 10.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = userProfileData.nickname,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.chattingColors.textColor
                )
                Spacer(Modifier.padding(vertical = 3.dp))
                Text(
                    text = lastMsg,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.chattingColors.textColor
                )
            }
            WidthSpacer(4.dp)
            val randomTime = remember {
                "${Random.nextInt(0, 24)}:${Random.nextInt(10, 60)}"
            }
            if (unreadCound > 0) {
                Box(
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(randomTime, color = MaterialTheme.chattingColors.textColor)
                        Spacer(Modifier.padding(vertical = 3.dp))
                        NumberChips(unreadCound)
                    }
                }
            }
        }
    }
}