package furhatos.app.openaichat

import furhatos.app.openaichat.flow.*
import furhatos.app.openaichat.flow.chatbot.serviceKey
import furhatos.skills.Skill
import furhatos.flow.kotlin.*
import furhatos.nlu.LogisticMultiIntentClassifier

class OpenaichatSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    if (serviceKey.isEmpty()) {
        println("Missing API key for OpenAI language model. ")
    } else{
        println(serviceKey)
    }

    LogisticMultiIntentClassifier.setAsDefault()
    Skill.main(args)
}
