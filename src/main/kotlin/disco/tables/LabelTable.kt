package disco.tables

import disco.model.Label
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert

object LabelTable : Table(), WithInsertHelp<Label> {
    val id = integer("id")
    val name = text("name")
    val contactinfo = text("contactinfo").nullable()
    val profile = text("profile").nullable()
    val parentLabel = text("parentLabel").nullable()
    val dataQuality = text("dataQuality")
    override val primaryKey = PrimaryKey(id)


    override fun insertHelp(batch: MutableList<Label>) {
        batchInsert(batch, shouldReturnGeneratedValues = false) { label ->
            this[id] = label.id
            this[name] = label.name
            this[contactinfo] = label.contactinfo
            this[profile] = label.profile
            this[parentLabel] = label.parentLabel
            this[dataQuality] = label.dataQuality
        }
    }
}
