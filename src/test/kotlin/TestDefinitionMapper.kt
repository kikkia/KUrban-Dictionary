import models.mappers.DefinitionMapper
import org.json.JSONObject
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestDefinitionMapper {
    @Test fun testMapper() {
        val json = JSONObject()
        json.put("word", "test")
        json.put("definition", "This is a [definition] that is [not] real")
        json.put("example", "when you don't need to [write] tests but do [anyways]")
        json.put("thumbs_up", 123)
        json.put("thumbs_down", 456)
        json.put("permalink", "http://x.com")
        json.put("author", "kikkia")
        json.put("defid", 177013)
        json.put("current_vote", "")
        json.put("written_on", "2005-05-14T00:00:00.000Z")

        val def = DefinitionMapper.map(json)
        assertEquals("test", def.word)
        assertEquals("This is a [definition] that is [not] real", def.definition)
        assertEquals("when you don't need to [write] tests but do [anyways]", def.example)
        assertEquals(123, def.thumbsUp)
        assertEquals(456, def.thumbsDown)
        assertEquals("http://x.com", def.permalink)
        assertEquals("kikkia", def.author)
        assertEquals(177013, def.defId)
        assertEquals("", def.currentVote)
        assertEquals("2005-05-14T00:00:00Z", def.created.toString())
        assertEquals(listOf("definition", "not", "write", "anyways"), def.linkedDefinitions)
    }
}