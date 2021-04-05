package disco

import com.thoughtworks.xstream.XStream
import disco.model.Artist
import disco.model.Label
import disco.tables.AliasTable
import disco.tables.ArtistImageTable
import disco.tables.ArtistTable
import disco.tables.ArtistUrlTable
import disco.tables.GroupTable
import disco.tables.LabelImageTable
import disco.tables.LabelTable
import disco.tables.LabelUrlTable
import disco.tables.MemberTable
import disco.tables.NamevariationTable
import disco.tables.SublabelTable
import mu.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.net.URL
import java.time.LocalDate
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.zip.GZIPInputStream

object App {
    private const val batchSize = 10000
    private val logger = KotlinLogging.logger {}

    enum class DownloadType { labels, artists, masters, releases }

    private fun gzipInputStream(downloadType: DownloadType): GZIPInputStream {
        val now = LocalDate.now()
        val url =
            "http://discogs-data.s3-us-west-2.amazonaws.com/data/${now.year}/discogs_20210301_${downloadType}.xml.gz"
        val openConnection = URL(url).openConnection()
        val inputStream = openConnection.getInputStream()
        return GZIPInputStream(inputStream)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val (username, password) = readProperties()
        connectDatabase(username, password)
        createTables()
//        processLabels()
        processArtists()
    }

    private fun readProperties(): Pair<String, String> {
        val stream = object {}.javaClass.getResourceAsStream("/config.properties")
        val properties = Properties()
        properties.load(stream)
        return Pair(properties.getProperty("username"), properties.getProperty("password"))
    }

    private fun connectDatabase(username: String, password: String) {
        Database.connect(
            "jdbc:postgresql://localhost/disco?user=$username&password=$password&rewriteBatchedInserts=true",
            driver = "org.postgresql.Driver"
        )
    }

    private fun createTables() {
        transaction {
            SchemaUtils.create(
                LabelTable, LabelImageTable, LabelUrlTable, SublabelTable,
                ArtistTable, ArtistImageTable, ArtistUrlTable, AliasTable, NamevariationTable, MemberTable, GroupTable
            )
        }
    }

    private fun processLabels() {
        val xStream = XStream()
        xStream.processAnnotations(arrayOf(Label::class.java))
        val xml = gzipInputStream(DownloadType.labels)
        val count = AtomicInteger(1)
        val batch = mutableListOf<Label>()

        xStream.createObjectInputStream(xml).use { stream ->
            try {
                while (true) {
                    batch.add(stream.readObject() as Label)

                    if (count.get() % batchSize == 0) {
                        transaction {
                            listOf(
                                LabelTable,
                                LabelImageTable,
                                LabelUrlTable,
                                SublabelTable,
                            ).forEach { it.insertHelp(batch) }

                            logger.info { "saved ${count.get()} labels" }
                        }
                        batch.clear()
                    }
                    count.incrementAndGet()
                }
            } catch (e: Exception) {
                println(e)
            } finally {
                println("done")
            }
        }
    }

    private fun processArtists() {
        val xStream = XStream()
        xStream.processAnnotations(arrayOf(Artist::class.java))
        val xml = gzipInputStream(DownloadType.artists)
        val count = AtomicInteger(1)
        val batch = mutableListOf<Artist>()

        xStream.createObjectInputStream(xml).use { stream ->
            try {
                while (true) {
                    batch.add(stream.readObject() as Artist)

                    if (count.get() % batchSize == 0) {
                        transaction {
                            listOf(
                                ArtistTable,
                                ArtistImageTable,
                                ArtistUrlTable,
                                AliasTable,
                                NamevariationTable,
                                MemberTable,
                                GroupTable,
                            ).forEach {
                                it.insertHelp(batch)
                            }

                            logger.info { "saved ${count.get()} artists" }
                        }
                        batch.clear()
                    }
                    count.incrementAndGet()
                }
            } catch (e: Exception) {
                println(e)
            } finally {
                println("done")
            }
        }
    }
}
