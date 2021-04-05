package disco.tables

import disco.model.Artist
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert

object ArtistUrlTable : Table(), WithInsertHelp<Artist> {
    val id = integer("id").autoIncrement()
    val artistId = reference("artist_id", ArtistTable.id)
    val url = text("url")
    override val primaryKey = PrimaryKey(id)

    override fun insertHelp(batch: MutableList<Artist>) {
        val artistIdsAndUrls = batch.flatMap { artist ->
            artist.urls?.urls?.map { url ->
                Pair(artist.id, url)
            }.orEmpty()
        }

        batchInsert(artistIdsAndUrls, shouldReturnGeneratedValues = false) { pair ->
            this[artistId] = pair.first
            this[url] = pair.second
        }
    }
}
