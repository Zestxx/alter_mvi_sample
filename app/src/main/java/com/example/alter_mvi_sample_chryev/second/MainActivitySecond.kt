package com.example.alter_mvi_sample_chryev.second

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.alter_mvi_sample_chryev.R
import com.example.alter_mvi_sample_chryev.core.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivitySecond : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainViewModelSecond: MainViewModelSecond by viewModels()

        mainViewModelSecond.state.observe(this, Observer { state ->
            render(state)
        })
        mainViewModelSecond.loadData()
    }

    private fun render(state: LoadingState<MainScreenStateSecond>) {
        when (state) {
            is ContentData -> showContent(state.content)
            is Loading -> showLoading()
            is Stub -> showStub(state)
        }
    }

    private fun showStub(stub: Stub<*>) {
        //Отображаем заглушку
    }

    private fun showLoading() {
        progress_bar.isVisible = true
        activity_title.isVisible = false
        activity_subtitle.isVisible = false
    }

    private fun showContent(state: MainScreenStateSecond) {
        activity_title.text = state.title
        activity_subtitle.text = state.subtitle

        progress_bar.isVisible = false
        activity_title.isVisible = true
        activity_subtitle.isVisible = true
    }
}
