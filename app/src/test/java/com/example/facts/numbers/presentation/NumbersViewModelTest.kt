package com.example.facts.numbers.presentation


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.facts.numbers.domain.NumberFact
import com.example.facts.numbers.domain.NumberUiMapper
import com.example.facts.numbers.domain.NumbersInteractor
import com.example.facts.numbers.domain.NumbersResult
import junit.framework.TestCase.assertEquals
import org.junit.Test

class NumbersViewModelTest : BaseTest() {

    /**
     * Initial test
     * At start fetch data and show it
     * then try to get some data successfully
     * then re-init and check the result
     */
    @Test
    fun `test init and re-init`() {
        val communications = TestNumberCommunications()
        val interactor = TestNumbersInteractor()
        //1) initialize
        val viewModel =
            NumbersViewModel(
                communications,
                interactor,
                NumbersResultMapper(communications, NumberUiMapper())
            )
        interactor.changeExpectedResult(NumbersResult.Success())
        //2) action
        viewModel.init(isFirstRun = true)
        //3) check
        assertEquals(1, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[0])

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success(), communications.stateCalledList[0])

        assertEquals(0, communications.numbersList.size)
        assertEquals(1, communications.timesShowList)

        //get data
        interactor.changeExpectedResult(NumbersResult.Failure("no internet connection"))
        viewModel.fetchRandomNumberFact()
        assertEquals(3, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[2])

        assertEquals(1, interactor.fetchAboutRandomNumberCalledList.size)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[3])

        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.Error("entered number is empty"), communications.stateCalledList[1])
        assertEquals(1, communications.timesShowList)

        viewModel.init(isFirstRun = false)
        assertEquals(4, communications.progressCalledList.size)
        assertEquals(2, communications.stateCalledList.size)
        assertEquals(1, communications.timesShowList)
    }

    /**
     * Try to get information about empty number
     */
    @Test
    fun `fact about empty number`() {
        val communications = TestNumberCommunications()
        val interactor = TestNumbersInteractor()
        //1) initialize
        val viewModel =
            NumbersViewModel(
                communications,
                interactor,
                NumbersResultMapper(communications, NumberUiMapper())
            )

        viewModel.fetchNumberFact("")

        assertEquals(0, interactor.fetchAboutRandomNumberCalledList.size)

        assertEquals(0, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Error("entered  number is empty"), communications.stateCalledList[0])
        assertEquals(0, communications.timesShowList)
    }

    /**
     * Try to get information about some number
     */
    @Test
    fun `fact about some number`() {
        val communications = TestNumberCommunications()
        val interactor = TestNumbersInteractor()
        //1) initialize
        val viewModel =
            NumbersViewModel(
                communications,
                interactor,
                NumbersResultMapper(communications, NumberUiMapper())
            )

        interactor.changeExpectedResult(
            NumbersResult.Success(
                listOf(NumberFact("45", "fact about 45"))
            )
        )
        viewModel.fetchNumberFact("45")

        assertEquals(1, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[0])

        assertEquals(1, interactor.fetchAboutRandomNumberCalledList.size)
        assertEquals(NumberFact("45", "fact about 45"), interactor.fetchAboutNumberCalledList[0])

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success(), communications.stateCalledList[0])
        assertEquals(1, communications.timesShowList)
        assertEquals(NumberUi("45", "fact about 45"), communications.numbersList[0])
    }


    private class TestNumbersInteractor : NumbersInteractor {

        private var result: NumbersResult = NumbersResult.Success()

        val initCalledList = mutableListOf<NumbersResult>()
        val fetchAboutNumberCalledList = mutableListOf<NumbersResult>()
        val fetchAboutRandomNumberCalledList = mutableListOf<NumbersResult>()

        fun changeExpectedResult(newResult: NumbersResult) {
            result = newResult
        }

        override suspend fun init(): NumbersResult {
            initCalledList.add(result)
            return result
        }

        override suspend fun factAboutNumber(number: String): NumbersResult {
            fetchAboutNumberCalledList.add(result)
            return result
        }

        override suspend fun factAboutRandomNumber(): NumbersResult {
            fetchAboutRandomNumberCalledList.add(result)
            return result
        }
    }
}