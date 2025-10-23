package furhatos.app.openaichat.utils

import furhatos.event.Event
import furhatos.flow.kotlin.*
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger

class IdleTimeout : Event()

private val idleScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
private val idleGen = AtomicInteger(0)

fun FlowControlRunner.resetIdleTimer(timeoutMs: Long = 10_000) {
    val myGen = idleGen.incrementAndGet()
    println("reset IdleTimer")

    idleScope.launch {
        kotlinx.coroutines.delay(timeoutMs)
        if (idleGen.get() == myGen) {
            println("go sleep")
            call { raise(IdleTimeout()) }
        }
    }
}

fun FlowControlRunner.stopIdleTimer() {
    idleGen.incrementAndGet()

}
