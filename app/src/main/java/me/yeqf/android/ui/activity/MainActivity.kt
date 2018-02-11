package me.yeqf.android.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import me.yeqf.android.R
import me.yeqf.android.persistence.entity.User
import me.yeqf.android.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

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
        viewModel.getUser("1"){
            textView.text = it.name
        }
    }

    private val mWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val user = User("1", s.toString())
            viewModel.addUser(user) {}
            viewModel.getDaily(2018, 2, 8) {
                Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
