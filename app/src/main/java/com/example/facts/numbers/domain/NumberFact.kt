package com.example.facts.numbers.domain

import com.example.facts.numbers.presentation.NumberUi

data class NumberFact(
    private val id: String,
    private val fact: String
) {

    interface Mapper<T> {
        fun map(id: String, fact: String): T
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, fact)
}

