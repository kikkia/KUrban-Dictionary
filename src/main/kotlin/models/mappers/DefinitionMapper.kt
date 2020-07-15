package models.mappers

import models.Definition
import org.json.JSONObject
import java.time.Instant

class DefinitionMapper {
    companion object {
        private val linkRegex = Regex("([a-zA-Z ]+)/gm")
        @JvmStatic
        fun map(json: JSONObject) : Definition {
            val mentionedDefs = ArrayList<String>()
            for (match in linkRegex.findAll(json.getString("definition")))
                mentionedDefs.add(match.groupValues[2])
            for (match in linkRegex.findAll(json.getString("example")))
                mentionedDefs.add(match.groupValues[2])

            val created = Instant.parse(json.getString("written_on"))

            return Definition(json.getString("word"),
                    json.getString("definition"),
                    json.getString("example"),
                    json.getString("permalink"),
                    json.getInt("thumbs_up"),
                    json.getInt("thumbs_down"),
                    json.getString("author"),
                    json.getInt("defid"),
                    json.getString("current_vote"),
                    created,
                    mentionedDefs)
        }
    }
}