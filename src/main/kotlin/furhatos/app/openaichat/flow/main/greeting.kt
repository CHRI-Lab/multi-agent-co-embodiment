package furhatos.app.openaichat.flow

import furhatos.app.openaichat.utils.IdleTimeout
import furhatos.app.openaichat.utils.resetIdleTimer
import furhatos.app.openaichat.setting.orchestratorPersona
import furhatos.app.openaichat.setting.personas
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.furhat

val Greeting = state(Parent) {
    onEntry {
        resetIdleTimer()
        furhat.dialogHistory.clear()
        for (persona in personas){
            persona.context.clear()
        }
        orchestratorPersona.context.clear()

        furhat.say(
            "Hello, I’m your social robot for today’s session." +
                    " Before we begin, you can decide how I should work."
        )
        furhat.ask("Do you want me to explain how does Speaker and Orchestrator works?", endSil = 2000)
    }

    onReentry {

        furhat.ask("Would you like to talk to Speaker or Orchestrator?", endSil = 2000)
    }

    onResponse("speaker") {
        resetIdleTimer()
        goto(SpeakerGreeting)

    }
    onResponse("orchestrator") {
        resetIdleTimer()
        goto(OrchestratorGreeting)

    }

    onResponse("Explain", "Yes", "explain to me") {
        resetIdleTimer()
        furhat.say("With the Orchestrator model, I act like a moderator and summarise their response to you")
        furhat.say("With the Speaker model, the personalities talk to you more directly.")
        reentry()
    }

    onResponse( "No", "Thanks") {
        resetIdleTimer()
        furhat.say("Cool! let's skip right ahead.")
        reentry()
    }


    onResponse("goodbye", "see you later", "exit"){
        furhat.say("It's nice chatting with you, see you later.")
        goto(Idle)
    }

    onEvent<IdleTimeout> {
        print("sleep\n")
        goto(Idle)
    }
}