package com.langitpayment.githubapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener { finish() }
    }
}