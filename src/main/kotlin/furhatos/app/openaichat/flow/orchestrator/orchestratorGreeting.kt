package furhatos.app.openaichat.flow

import furhatos.app.openaichat.utils.IdleTimeout
import furhatos.app.openaichat.utils.resetIdleTimer
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.records.Location

val OrchestratorGreeting = state(Parent) {
    onEntry {
        resetIdleTimer()

        furhat.attend(users.userClosestToPosition(Location(0.0, 0.0, 0.5)))
        furhat.gesture(Gestures.Nod)
        if (furhat.askYN("Would you like me to share my internal thought process with you? ")) {
            furhat.say("sure! I'll demonstrate my though process with you")
            delay((500))
            furhat.say("How Can I help you today?")
            goto(OrchestratorDemonstration)

        } else {
            furhat.say("Cool! Lets get straight into the conversation")
            delay((500))
            furhat.say("How Can I help you today?")

            goto(Orchestrator)
        }

    }

    onEvent<IdleTimeout> {
        print("sleep\n")
        goto(Idle)
    }
}
