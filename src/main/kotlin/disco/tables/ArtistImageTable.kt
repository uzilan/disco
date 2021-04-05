package disco.tables

import disco.model.Artist
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert

object ArtistImageTable : Table(), WithInsertHelp<Artist> {
    val id = integer("id").autoIncrement()
    val artistId = reference("artist_id", ArtistTable.id)
    val height = integer("height")
    val width = integer("width")
    val type = text("type").nullable()
    val uri = text("uri").nullable()
    val uri150 = text("uri150").nullable()
    override val primaryKey = PrimaryKey(id)

    override fun insertHelp(batch: MutableList<Artist>) {
        val artistIdsAndImages = batch.flatMap { artist ->
            artist.images?.images?.map { image ->
                Pair(artist.id, image)
            }.orEmpty()
        }

        batchInsert(artistIdsAndImages, shouldReturnGeneratedValues = false) { pair ->
            this[artistId] = pair.first
            this[height] = pair.second.height
            this[width] = pair.second.width
            this[ArtistImageTable.type] = pair.second.type
            this[uri] = pair.second.uri
            this[uri150] = pair.second.uri150
        }
    }
}
