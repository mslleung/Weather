package com.stockviva.weather.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // always draw fullscreen, allocate spaces for system bars manually
        // this allows better control over the system insets
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            App()
        }
    }

}
