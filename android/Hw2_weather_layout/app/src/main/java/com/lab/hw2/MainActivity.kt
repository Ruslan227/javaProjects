package com.lab.hw2

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


var themeMode: Int = 0
const val MAX_MODE_VAL: Int = 1
const val THEME_MOD = "themeMode"

class MainActivity : AppCompatActivity() {

    private fun nextThemeMode(): Int {
        if (themeMode == MAX_MODE_VAL)
            return 0
        return themeMode + 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        themeMode =
            if (AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES) {
                setTheme(R.style.light_theme)
                0
            } else {
                setTheme(R.style.dark_theme)
                1
            }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            if (themeMode == 0) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                setTheme(R.style.light_theme)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                setTheme(R.style.dark_theme)
            }
            themeMode = nextThemeMode()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(THEME_MOD, themeMode)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        themeMode = savedInstanceState.getInt(THEME_MOD)
    }

}