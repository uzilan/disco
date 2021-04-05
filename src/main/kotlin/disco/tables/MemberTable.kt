package disco.tables

import disco.model.Artist
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert

object MemberTable : Table(), WithInsertHelp<Artist> {
    val id = integer("id")
    val artistId = reference("artist_id", ArtistTable.id)
    val name = text("name")
    override val primaryKey = PrimaryKey(id)

    override fun insertHelp(batch: MutableList<Artist>) {
        val artistIdsAndMemebers = batch.flatMap { artist ->
            artist.members?.names?.map { member ->
                Pair(artist.id, member)
            }.orEmpty()
        }

        batchInsert(artistIdsAndMemebers, shouldReturnGeneratedValues = false, ignore = true) { pair ->
            this[artistId] = pair.first
            this[id] = pair.second.id
            this[name] = pair.second.name
        }
    }
}
