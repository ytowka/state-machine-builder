package arch

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class ViewModel<State, Event, UserEvent: Event, SingleEvent>(val coroutineScope: CoroutineScope) {

    private val events: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _state: MutableStateFlow<State> by lazy { MutableStateFlow(initalState) }
    val state: StateFlow<State> get() = _state.asStateFlow()

    private val _singleEvents: MutableSharedFlow<SingleEvent> = MutableSharedFlow()
    val singleEvents get() =  _singleEvents.asSharedFlow()

    abstract val initalState: State
    protected fun processEvent(event: Event){
        coroutineScope.launch {
            events.emit(event)
        }
    }

    fun processUserEvent(event: UserEvent){
        coroutineScope.launch {
            events.emit(event)
        }
    }

    abstract fun reduce(state: State, event: Event): State

    init {
        coroutineScope.launch {
            events.collect(::reduceInternal)
        }
    }

    private fun reduceInternal(event: Event){
        val oldState = state.value
        val newState = reduce(oldState, event)
        _state.value = newState
        onSideEffect(oldState, newState, event)
    }

    fun showSingleEvent(singleEvent: SingleEvent){
        _singleEvents.tryEmit(singleEvent)
    }

    fun onSideEffect(oldState: State, newState: State, event: Event){}
}