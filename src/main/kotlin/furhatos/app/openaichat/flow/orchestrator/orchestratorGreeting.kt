package furhatos.app.openaichat.flow

import clearChat
import clearChatAsync
import furhatos.app.openaichat.setting.activate
import furhatos.app.openaichat.setting.carePersona
import furhatos.app.openaichat.setting.creativePersona
import furhatos.app.openaichat.setting.emotionalPersona
import furhatos.app.openaichat.setting.ethicPersona
import furhatos.app.openaichat.setting.logicalPersona
import furhatos.app.openaichat.setting.orchestratorPersona
import furhatos.app.openaichat.setting.personas
import furhatos.app.openaichat.setting.profitPersona
import furhatos.app.openaichat.setting.speakerPersona
import furhatos.app.openaichat.utils.IdleTimeout
import furhatos.app.openaichat.utils.resetIdleTimer
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.records.Location
import postMessage
import sayAndLog

val OrchestratorGreeting = state(Parent) {
    onEntry {
        clearChatAsync()
        resetIdleTimer()
        furhat.dialogHistory.clear()
        for (persona in personas){
            persona.context.clear()
        }
        orchestratorPersona.context.clear()

        furhat.attend(users.userClosestToPosition(Location(0.0, 0.0, 0.5)))
        furhat.say("Hi there! I'm the Orchestrator Agent, " +
                "here to assist you figure in selling an item on a popular online marketplace ")
        sayAndLog("assistant",
            "Hi there! I'm the Orchestrator Agent, here to assist you figure in selling an item on a popular online marketplace ",
            "System")

        sayAndLog("assistant",
            "Your can exit our conversation at any time by saying exit.",
            "System")
        furhat.say("Your can exit our conversation at any time by saying exit.")

        sayAndLog("assistant",
            "Before we start, I'll give a introduction to my team of agents.",
            "System")
        furhat.say("Before we start, I'll give a introduction to my team of agents.")
        goto(OrchestratorDemonstration)

        delay((200))

        sayAndLog("assistant",
            "Here is Emma, our Ethics Agent that ensures our sale be honest, fairness, and transparent.",
            "System")
        furhat.say("Here is Emma, our Ethics Agent that ensures our sale be honest, fairness, and transparent.")

        delay((200))
        switchPersona()
        activate(ethicPersona)

        delay((200))
        sayAndLog("assistant",
            "Hi! I'm emma",
            ethicPersona.name)
        furhat.say("Hi! I'm emma")

        delay((400))
        switchPersona()
        activate(orchestratorPersona)


        sayAndLog("assistant",
            "Say Hello to Maurice, our profit agent. Here to suggest the most competitive pricing to maximise profit.",
            "System")
        furhat.say("Say Hello to Maurice, our profit agent. Here to suggest the most competitive pricing to maximise profit.")

        delay((200))
        switchPersona()
        activate(profitPersona)
        delay((200))

        sayAndLog("assistant",
            "Hi! I'm Maurice",
            profitPersona.name)
        furhat.say("Hi! I'm Maurice")

        delay((400))
        switchPersona()
        activate(orchestratorPersona)

        sayAndLog("assistant",
            "Meet Jane, our customer care agent. She’s here to make sure we have positive and friendly relationships with our potential buyers",
            "System")
        furhat.say("Meet Jane, our customer care agent. She’s here to make sure we have positive and friendly relationships with our potential buyers")

        delay((200))
        switchPersona()
        activate(carePersona)
        delay((200))
        sayAndLog("assistant",
            "Hi! I'm Jane",
            carePersona.name)
        furhat.say("Hi! I'm Jane ")

        delay((400))
        switchPersona()
        activate(orchestratorPersona)

        sayAndLog("assistant",
            "Whats the product you want to sale?",
            "System")
        furhat.say("Whats the product you want to sale?")

        goto(OrchestratorDemonstration)



    }

    onEvent<IdleTimeout> {
        print("sleep\n")
        goto(Idle)
    }
}


