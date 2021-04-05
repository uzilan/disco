package disco.tables

import disco.model.Label
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert

object SublabelTable : Table(), WithInsertHelp<Label> {
    val id = integer("id")
    val labelId = reference("label_id", LabelTable.id)
    val label = text("label")
    override val primaryKey = PrimaryKey(id)

    override fun insertHelp(batch: MutableList<Label>) {
        val labelIdsAndSublabels = batch.flatMap { lbl ->
            lbl.sublabels?.labels?.map { sublabel ->
                Pair(lbl.id, sublabel)
            }.orEmpty()
        }

        batchInsert(labelIdsAndSublabels) { pair ->
            this[labelId] = pair.first
            this[id] = pair.second.id
            this[label] = pair.second.label
        }
    }
}
