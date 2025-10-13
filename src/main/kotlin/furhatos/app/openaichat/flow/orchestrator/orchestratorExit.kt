package furhatos.app.openaichat.flow
import furhatos.flow.kotlin.*

import furhatos.app.openaichat.flow.main.Awake

val OrchestratorExit : State = state(Parent) {

    onEntry {
        delay(300)
        furhat.say("Great, I think you are ready to sell an item on the marketplace. Please click \"Submit\" on the iPad next to you.")
        delay(300)
        goto(Awake)
    }

}




