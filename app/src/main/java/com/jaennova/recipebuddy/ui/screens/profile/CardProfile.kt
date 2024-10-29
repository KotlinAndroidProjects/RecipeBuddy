package com.jaennova.recipebuddy.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaennova.recipebuddy.R

@Composable
fun CardProfile(modifier: Modifier = Modifier) {
    OutlinedCard(
        modifier = modifier
            .padding(4.dp)
            .size(width = 450.dp, height = 260.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Profile Image"
            )
            Text(
                text = "Jaen Nova",
                style = TextStyle(),
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "I love to cook and eat delicious food",
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenContentPreview() {
    CardProfile()
}