package me.yeqf.android.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import me.yeqf.android.R
import me.yeqf.android.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import me.yeqf.android.R.id.textView
import me.yeqf.common.utils.TimeUtils

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        lifecycle.addObserver(viewModel)
    }

    override fun onStart() {
        super.onStart()
        edit.addTextChangedListener(mWatcher)
    }

    private val mWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val timeStr = s.toString()
            val date = TimeUtils.getDate(timeStr, TimeUtils.FORMAT_YYYYMMDD)
            viewModel.getDaily(date[0], date[1], date[2]) {
                textView.text = (System.currentTimeMillis().toString() + "\n" + it.toString())
            }
//            viewModel.getCategory(s.toString(), 10, 1) {
//                textView.text = (System.currentTimeMillis().toString() + "\n" + it.toString())
//            }
        }
    }
}
