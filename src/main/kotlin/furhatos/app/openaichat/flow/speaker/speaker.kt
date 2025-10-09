package furhatos.app.openaichat.flow

import furhatos.app.openaichat.setting.activate
import furhatos.app.openaichat.setting.speakerPersona
import furhatos.flow.kotlin.*
import com.google.gson.Gson
import furhatos.app.openaichat.flow.speaker.CreativeAgent
import furhatos.app.openaichat.flow.speaker.EmotionalAgent
import furhatos.app.openaichat.flow.speaker.LogicalAgent
import furhatos.app.openaichat.flow.chatbot.getOpenAiResponse
import furhatos.app.openaichat.utils.IdleTimeout
import furhatos.app.openaichat.utils.resetIdleTimer
import furhatos.app.openaichat.setting.SPEAKER_PROMPT
import furhatos.app.openaichat.setting.creativePersona
import furhatos.app.openaichat.setting.emotionalPersona
import furhatos.app.openaichat.setting.logicalPersona
import furhatos.gestures.Gestures


val Speaker : State = state(Parent) {
    onEntry {
        resetIdleTimer()
        activate(speakerPersona)
        switchPersona()
        reentry()
    }

    onReentry {
        furhat.listen(endSil = 2000)
    }

    onResponse(creativePersona.name) {
        resetIdleTimer()
        goto(CreativeAgent)
    }

    onResponse(emotionalPersona.name) {
        resetIdleTimer()
        goto(EmotionalAgent)
    }
    onResponse(logicalPersona.name) {
        resetIdleTimer()
        goto(LogicalAgent)
    }

    onResponse("exit", "quit") {
        resetIdleTimer()
        goto(Greeting)
    }

    onResponse {
        resetIdleTimer()
        val history = getFurhatMessage()
        val response = call {
            getOpenAiResponse(SPEAKER_PROMPT, history, 0.0, 50)
        } as String
        println(response)
        try {
            val gson = Gson()
            val decision: SwitchDecision = gson.fromJson(response, SwitchDecision::class.java)
            println("Action = ${decision.action}, Agent = ${decision.agent}")
            when (decision.agent) {
                "logical_agent"  -> goto(LogicalAgent)
                "creative_agent" -> goto(CreativeAgent)
                "emotional_agent"-> goto(EmotionalAgent)
            }

        } catch (e: Exception) {
            println("Failed to parse JSON: $response")
            e.printStackTrace()
            goto(EmotionalAgent)
        }

    }




    onNoResponse {
        reentry()
    }

    onEvent<IdleTimeout> {
        print("sleep\n")
        goto(Idle)
    }
}

fun FlowControlRunner.switchPersona() {
    furhat.gesture(Gestures.Nod)


}


data class SwitchDecision(
    val action: String,
    val agent: String
)

