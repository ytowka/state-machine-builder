package ui.models

data class StateMachine(
    val states: List<State>,
    val alphabet: List<Symbol>,
    val transition: List<List<Int>>,
    val startState: State,
    val acceptStates: List<State>
){
    fun check(word: List<Symbol>): Boolean{
        var currState = startState
        word.forEach { symbol ->
            val newStateIndex = transition[currState.index][symbol.index]
            currState = states[newStateIndex]
        }
        return currState in acceptStates
    }
}

