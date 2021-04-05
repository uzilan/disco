package disco.tables

import disco.model.Artist
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert

object NamevariationTable : Table(), WithInsertHelp<Artist> {
    val id = integer("id").autoIncrement()
    val artistId = reference("artist_id", ArtistTable.id)
    val name = text("name")
    override val primaryKey = PrimaryKey(SublabelTable.id)

    override fun insertHelp(batch: MutableList<Artist>) {
        val artistIdsAndNamevariations = batch.flatMap { artist ->
            artist.namevariations?.names?.map { namevariation ->
                Pair(artist.id, namevariation)
            }.orEmpty()
        }

        batchInsert(artistIdsAndNamevariations) { pair ->
            this[artistId] = pair.first
            this[name] = pair.second
        }
    }


}
