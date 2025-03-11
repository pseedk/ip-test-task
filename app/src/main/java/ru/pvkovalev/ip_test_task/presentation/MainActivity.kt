package ru.pvkovalev.ip_test_task.presentation

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowInsetsControllerCompat
import dagger.hilt.android.AndroidEntryPoint
import ru.pvkovalev.ip_test_task.presentation.screens.MainScreen
import ru.pvkovalev.ip_test_task.presentation.ui.theme.AppBarColor
import ru.pvkovalev.ip_test_task.presentation.ui.theme.IptesttaskTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val window: Window = this.window
            window.navigationBarColor = AppBarColor.toArgb()
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars =
                true
            IptesttaskTheme {
                MainScreen()
            }
        }
    }
}

