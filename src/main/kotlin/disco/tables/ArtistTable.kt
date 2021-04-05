package disco.tables

import disco.model.Artist
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert

object ArtistTable : Table(), WithInsertHelp<Artist> {
    val id = integer("id")
    val name = text("name")
    val realname = text("realname").nullable()
    val profile = text("profile")
    val dataQuality = text("dataQuality")
    override val primaryKey = PrimaryKey(id)


    override fun insertHelp(batch: MutableList<Artist>) {
        batchInsert(batch, shouldReturnGeneratedValues = false) { artist ->
            this[id] = artist.id
            this[name] = artist.name
            this[realname] = artist.realname
            this[profile] = artist.profile
            this[dataQuality] = artist.dataQuality
        }
    }
}
