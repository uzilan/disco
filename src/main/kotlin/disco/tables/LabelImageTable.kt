package disco.tables

import disco.model.Label
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert

object LabelImageTable : Table(), WithInsertHelp<Label> {
    val id = integer("id").autoIncrement()
    val labelId = reference("label_id", LabelTable.id)
    val height = integer("height")
    val width = integer("width")
    val type = text("type").nullable()
    val uri = text("uri").nullable()
    val uri150 = text("uri150").nullable()
    override val primaryKey = PrimaryKey(id)


    override fun insertHelp(batch: MutableList<Label>) {
        val labelIdsAndImages = batch.flatMap { label ->
            label.images?.images?.map { image ->
                Pair(label.id, image)
            }.orEmpty()
        }

        batchInsert(labelIdsAndImages, shouldReturnGeneratedValues = false) { pair ->
            this[labelId] = pair.first
            this[height] = pair.second.height
            this[width] = pair.second.width
            this[LabelImageTable.type] = pair.second.type
            this[uri] = pair.second.uri
            this[uri150] = pair.second.uri150
        }
    }
}
