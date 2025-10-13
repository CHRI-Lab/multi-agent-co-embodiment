package furhatos.app.openaichat.flow

import furhatos.app.openaichat.setting.activate
import furhatos.app.openaichat.setting.creativePersona
import furhatos.app.openaichat.setting.emotionalPersona
import furhatos.app.openaichat.setting.logicalPersona
import furhatos.app.openaichat.setting.orchestratorPersona
import furhatos.app.openaichat.setting.speakerPersona
import furhatos.app.openaichat.utils.IdleTimeout
import furhatos.app.openaichat.utils.resetIdleTimer
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.records.Location

val OrchestratorGreeting = state(Parent) {
    onEntry {
        resetIdleTimer()
        furhat.attend(users.userClosestToPosition(Location(0.0, 0.0, 0.5)))
        furhat.say("“Hi there! I'm here to assist you figure in selling an item on a popular online marketplace " +
                "Before we start, I'll give a introduction to each of the agent in me and what they do\"")

        delay((200))

        furhat.say("Here is Emma, our Ethics Agent that ensures our sale be honest, fairness, and transparent.")
        delay((200))
        switchPersona()
        activate(creativePersona)

        delay((200))

        furhat.say("Hi! I'm emma ")
        delay((400))
        switchPersona()
        activate(orchestratorPersona)



        furhat.say("Say Hello to Maurice, our profit expert. Here to suggest the most competitive pricing to maximise profit.")
        delay((200))
        switchPersona()
        activate(logicalPersona)
        delay((200))
        furhat.say("Hi! I'm Maurice ")

        delay((400))
        switchPersona()
        activate(orchestratorPersona)

        furhat.say("Meet Jane, she’s here to make sure we have positive and friendly relationships with our potential buyers")
        delay((200))
        switchPersona()
        activate(emotionalPersona)
        delay((200))
        furhat.say("Hi! I'm Jane ")
        delay((400))
        switchPersona()
        activate(orchestratorPersona)

        furhat.say("Whats the product you want to sale?")

        goto(OrchestratorDemonstration)



    }

    onEvent<IdleTimeout> {
        print("sleep\n")
        goto(Idle)
    }
}
