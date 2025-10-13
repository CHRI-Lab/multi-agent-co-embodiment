package furhatos.app.openaichat.flow.main

import furhatos.app.openaichat.flow.Greeting
import furhatos.app.openaichat.flow.Idle
import furhatos.app.openaichat.flow.OrchestratorGreeting
import furhatos.app.openaichat.flow.Parent
import furhatos.app.openaichat.utils.IdleTimeout
import furhatos.app.openaichat.utils.resetIdleTimer
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state

val Awake = state(Parent) {

    onEntry {
        furhat.say(
            "Welcome! Say \"Hey Furhat\" if you want to start the conversation."
        )
        resetIdleTimer()

        furhat.listen(endSil = 2000)

    }


    onResponse("hey", "hey furhat", "start", "go") {

        goto(OrchestratorGreeting)

    }

    onEvent<IdleTimeout> {
        print("sleep\n")
        goto(Idle)
    }
}