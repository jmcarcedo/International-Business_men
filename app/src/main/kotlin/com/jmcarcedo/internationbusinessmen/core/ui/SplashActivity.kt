package com.jmcarcedo.internationbusinessmen.core.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jmcarcedo.internationbusinessmen.R
import com.jmcarcedo.internationbusinessmen.transactions.ui.TransactionsActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        goToTransactions()
    }

    private fun goToTransactions() {
        Intent(this, TransactionsActivity::class.java).apply {
            addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
        }.also(::startActivity)
    }
}
