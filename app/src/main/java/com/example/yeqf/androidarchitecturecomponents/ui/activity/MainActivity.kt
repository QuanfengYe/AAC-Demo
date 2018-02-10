package com.example.yeqf.androidarchitecturecomponents.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.yeqf.androidarchitecturecomponents.R
import com.example.yeqf.androidarchitecturecomponents.persistence.entity.User
import com.example.yeqf.androidarchitecturecomponents.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var viewModel: MainActivityViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        initView()
    }

    private fun initView() {
        val data = viewModel?.getUser("1")
        data?.observe(this, Observer<User> { user ->
            textView.text = user?.name
        })
        button.setOnClickListener {
            val user = User("1", edit.text.toString())
            viewModel?.addUser(user)
        }
    }
}
