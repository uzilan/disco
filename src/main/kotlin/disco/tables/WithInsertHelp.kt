package disco.tables

interface WithInsertHelp<T> {
    fun insertHelp(batch: MutableList<T>)
}
