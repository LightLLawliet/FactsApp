package com.example.facts.numbers.presentation

import android.widget.TextView
import org.w3c.dom.Text

data class NumberUi(private val id: String, private val fact: String) {

    fun map(head: TextView, subTitle: TextView) {
        head.text = id
        subTitle.text = fact
    }
}