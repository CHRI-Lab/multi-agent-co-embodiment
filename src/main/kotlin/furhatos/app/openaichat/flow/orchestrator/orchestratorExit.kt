package furhatos.app.openaichat.flow
import furhatos.flow.kotlin.*

import furhatos.app.openaichat.flow.main.Awake
import sayAndLog

val OrchestratorExit : State = state(Parent) {

    onEntry {
        delay(300)
        sayAndLog("assistant",
            "Great, I think you are ready to sell an item on the marketplace. Please click \"Submit\" on the iPad next to you.",
            "System")
        furhat.say("Great, I think you are ready to sell an item on the marketplace. Please click \"Submit\" on the iPad next to you.")

        println(Logs.saveLog())
        delay(300)
        goto(Idle)
    }

}




