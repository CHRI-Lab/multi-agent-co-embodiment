package furhatos.app.openaichat.setting

import com.theokanning.openai.completion.chat.ChatMessage
import furhatos.flow.kotlin.FlowControlRunner
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.flow.kotlin.voice.Voice
import furhatos.nlu.SimpleIntent

class Persona(
    val name: String,
    val otherNames: List<String> = listOf(),
    val intro: String = "",
    val desc: String,
    val face: List<String>,
    val mask: String = "adult",
    val voice: List<Voice>,
    val context : MutableList<ChatMessage> = mutableListOf<ChatMessage>()

) {
    val fullDesc = "$name, the $desc"
    val intent = SimpleIntent((listOf(name, desc, fullDesc) + otherNames))
}


fun FlowControlRunner.activate(persona: Persona) {
    for (voice in persona.voice) {
        if (voice.isAvailable) {
            furhat.voice = voice
            break
        }
    }

    for (face in persona.face) {
        if (furhat.faces[persona.mask]?.contains(face)!!) {
            furhat.character = face
            break
        }
    }
}

val hostPersona = Persona(
    name = "Host",
    desc = "host",
    face = listOf("Alex", "default"),
    voice = listOf(PollyNeuralVoice("Matthew"), Voice("Tyler"))
)

val speakerPersona = Persona(
    name = "Speaker",
    desc = "Speaker",
    face = listOf("Alex", "default"),
    voice = listOf(PollyNeuralVoice("Matthew"),Voice("Tyler"))
)

val orchestratorPersona = Persona(
    name = "Orchestrator",
    desc = "Orchestrator",
    face = listOf("Alex", "default"),
    voice = listOf(PollyNeuralVoice("Matthew"), Voice("Tyler") )
)

val creativePersona = Persona(
    name = "Emma",
    desc = CREATIVE_AGENT_PROMPT,
    intro = "creative_agent: brainstorming, naming, story",

    face = listOf("Isabel"),
    voice = listOf(PollyNeuralVoice("Emma")),
)
val logicalPersona = Persona(
    name = "Maurice",
    desc = LOGICAL_AGENT_PROMPT,
    intro = "logical_agent: reasoning, analysis, code, math",
    face = listOf("Maurice"),
    voice = listOf(PollyNeuralVoice("Gregory"))
)
val emotionalPersona = Persona(
    name = "Jane",
    desc = EMOTIONAL_AGENT_PROMPT,
    intro = "emotional_agent: empathy, motivation, sensitive tone",

    face = listOf("Jane"),
    voice = listOf(PollyNeuralVoice("Olivia")),
)

val ethicPersona = Persona(
    name = "Emma",
    desc = ETHIC_AGENT_PROMPT,
    intro = "Ethics Agent that ensures our sale be honest, fairness, and transparent.",
    face = listOf("Isabel"),
    voice = listOf(PollyNeuralVoice("Emma")),
)
val profitPersona = Persona(
    name = "Maurice",
    desc = PROFIT_AGENT_PROMPT,
    intro = "A profit agent to suggest the most competitive pricing to maximise profit.",
    face = listOf("Maurice"),
    voice = listOf(PollyNeuralVoice("Gregory"))
)

val carePersona = Persona(
    name = "Jane",
    desc = CARE_AGENT_PROMPT,
    intro = "A Customer care agent to make sure we have positive and friendly relationships with our potential buyers",
    face = listOf("Jane"),
    voice = listOf(PollyNeuralVoice("Olivia")),
)

val personas = listOf(
    ethicPersona,
    profitPersona,
    carePersona
)