package com.example.alter_mvi_sample_chryev.first

import android.os.Handler
import com.example.alter_mvi_sample_chryev.core.SingleStateViewModel

class MainViewModelFirst : SingleStateViewModel<MainScreenStateFirst>() {

    override fun getInitialState(): MainScreenStateFirst {
        return MainScreenStateFirst(
            title = "",
            subtitle = "",
            isLoading = true
        )
    }

    fun loadData() {
        //Эмуляция загрузки данных
        Handler().postDelayed({
            val title = loadTitle()
            val subtitle = loadSubtitle()
            updateState {
                copy(
                    title = title,
                    isLoading = false
                )
            }

            updateState {
                copy(subtitle = subtitle)
            }
        }, 2000)
    }

    private fun loadTitle(): String {
        return "Test title"
    }

    private fun loadSubtitle(): String {
        return "Test subtitle"
    }
}
