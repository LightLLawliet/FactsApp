package com.example.facts.numbers.presentation

interface NumbersCommunications {

    fun showProgress(show: Boolean)

    fun showState(state: UiState)

    fun showList(list: List<NumberUi>)

    class Base(

    ){

    }
}