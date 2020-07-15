import models.Definition
import models.mappers.DefinitionMapper
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.json.JSONObject
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class KurbanClient(poolSize: Int) {
    private val DEFINE_URL = "http://api.urbandictionary.com/v0/define?term="
    private val AUTOCOMPLETE_URL = "http://api.urbandictionary.com/v0/autocomplete?term="

    private val threadPool: ExecutorService = Executors.newFixedThreadPool(poolSize)

    fun getResultsAsync(term: String) : Future<List<Definition>> {
        return threadPool.submit {getResults(term)} as Future<List<Definition>>
    }

    fun getResults(term: String) : List<Definition> {
        val defs = ArrayList<Definition>()
        for (item in getJson(DEFINE_URL + term).getJSONArray("list")) {
           defs.add(DefinitionMapper.map(item as JSONObject))
        }
        return defs
    }

    fun getAutoCompleteAsync(term: String) : Future<List<String>> {
        return threadPool.submit {getAutoComplete(term)} as Future<List<String>>
    }

    fun getAutoComplete(term: String) : List<String> {
        val results = ArrayList<String>()
        for (item in getJson(AUTOCOMPLETE_URL + term).getJSONArray("list")) {
            results.add(item as String)
        }
        return results
    }

    fun getJson(url: String) : JSONObject {
        HttpClients.createDefault().use { client ->
            val get = HttpGet(url)
            return JSONObject(EntityUtils.toString(client.execute(get).entity))
        }
    }

    class Builder {
        private var poolSize: Int = 1 // DEFAULT

        fun setPoolSize(size: Int) : Builder {
            this.poolSize = size
            return this
        }

        fun getPoolSize(): Int {
            return poolSize
        }

        fun build() : KurbanClient {
            return KurbanClient(this.poolSize)
        }
    }
}