package furhatos.app.openaichat.flow

import furhatos.app.openaichat.flow.main.Awake
import furhatos.app.openaichat.utils.stopIdleTimer
import furhatos.app.openaichat.setting.activate
import furhatos.app.openaichat.setting.hostPersona
import furhatos.flow.kotlin.*

val Idle : State = state {
    onEntry {
        stopIdleTimer()
        activate(hostPersona)
        furhat.attendNobody()
        furhat.listen(timeout = 1800000)
    }


    onResponse("hey", "hey furhat", "start", "go") {

        goto(OrchestratorGreeting)

    }
    onResponse {
        reentry()
    }

    onNoResponse {
        reentry()
    }
}





