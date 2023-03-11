package com.udacity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        val fileTitle: String? = intent.getStringExtra(FILENAME)
        val statusTitle: String? = intent.getStringExtra(STATUS)
        fileName.text = fileTitle
        status.text = statusTitle
    }

    companion object {
        const val FILENAME = "fileName"
        const val STATUS = "status"
    }
}
