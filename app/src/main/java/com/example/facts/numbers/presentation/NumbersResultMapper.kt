package com.example.facts.numbers.presentation

import com.example.facts.numbers.domain.NumberFact
import com.example.facts.numbers.domain.NumbersResult

class NumbersResultMapper(
    private val communications: NumbersCommunications,
    private val mapper: NumberFact.Mapper<NumberUi>
) : NumbersResult.Mapper<Unit> {

    override fun map(list: List<NumberFact>, errorMessage: String) = communications.showState(
        if (errorMessage.isEmpty()) {
            val numberList = list.map { it.map(mapper) }
            if (numberList.isNotEmpty())
                communications.showList(numberList)
            UiState.Success()
        } else
            UiState.Error(errorMessage)
    )
}
