package com.comics.kujiraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.comics.kujiraapp.navigation.AuthNavigation
import com.comics.kujiraapp.ui.theme.KujiraAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KujiraAppTheme {
                AuthNavigation()
            }
        }
    }
}
