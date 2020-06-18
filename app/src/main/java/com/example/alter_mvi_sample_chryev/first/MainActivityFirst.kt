package com.example.alter_mvi_sample_chryev.first

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.alter_mvi_sample_chryev.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivityFirst : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainViewModelFirst: MainViewModelFirst by viewModels()

        mainViewModelFirst.state.observe(this, Observer { state ->
            render(state)
        })
        mainViewModelFirst.loadData()
    }

    private fun render(state: MainScreenStateFirst) {
        activity_title.text = state.title
        activity_subtitle.text = state.subtitle

        progress_bar.isVisible = state.isLoading
        activity_title.isVisible = !state.isLoading
        activity_subtitle.isVisible = !state.isLoading
    }
}
