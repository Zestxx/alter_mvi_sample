package com.example.alter_mvi_sample_chryev.second

import android.os.Handler
import com.example.alter_mvi_sample_chryev.core.MultipleStateViewModel

class MainViewModelSecond : MultipleStateViewModel<MainScreenStateSecond>() {

    fun loadData() {
        showLoading()
        //Эмуляция загрузки данных
        Handler().postDelayed({
            val title = loadTitle()
            val subtitle = loadSubtitle()
            showContent(
                MainScreenStateSecond(
                    title = title,
                    subtitle = subtitle
                )
            )
        }, 2000)
    }

    private fun loadTitle(): String {
        return "Test title"
    }

    private fun loadSubtitle(): String {
        return "Test subtitle"
    }
}
