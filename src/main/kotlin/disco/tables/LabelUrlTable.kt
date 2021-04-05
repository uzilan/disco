package disco.tables

import disco.model.Label
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert

object LabelUrlTable : Table(), WithInsertHelp<Label> {
    val id = integer("id").autoIncrement()
    val labelId = reference("label_id", LabelTable.id)
    val url = text("url")
    override val primaryKey = PrimaryKey(id)

    override fun insertHelp(batch: MutableList<Label>) {
        val labelIdsAndUrls = batch.flatMap { label ->
            label.urls?.urls?.map { url ->
                Pair(label.id, url)
            }.orEmpty()
        }

        batchInsert(labelIdsAndUrls, shouldReturnGeneratedValues = false) { pair ->
            this[labelId] = pair.first
            this[url] = pair.second
        }
    }
}
