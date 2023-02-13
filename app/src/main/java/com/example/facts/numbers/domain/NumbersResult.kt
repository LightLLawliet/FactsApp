package com.example.facts.numbers.domain

sealed class NumbersResult {



    abstract fun <T> map(mapper: Mapper<T>): T

    class Success(private val list: List<NumberFact> = emptyList()) : NumbersResult() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(list, "")
    }

    class Failure(private val message: String) : NumbersResult() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(emptyList(), message)
    }
}