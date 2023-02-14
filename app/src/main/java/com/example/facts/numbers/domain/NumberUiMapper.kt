package com.example.facts.numbers.domain

import com.example.facts.numbers.presentation.NumberUi

class  NumberUiMapper : NumberFact.Mapper<NumberUi> {
    override fun map(id: String, fact: String): NumberUi = NumberUi(id, fact)
}