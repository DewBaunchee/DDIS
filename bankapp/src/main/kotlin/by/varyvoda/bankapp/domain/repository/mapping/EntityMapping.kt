package by.varyvoda.bankapp.domain.repository.mapping

class EntityMapping<E>(
    val tableName: String,
    val creator: () -> E,
    val fields: List<Field<E, out Any>>
) {

    val idField = fields.find { it.isId }!!

    val nonIdFields = fields.filter { !it.isId }

    class Field<E, FT>(
        val isId: Boolean,
        val columnName: String,
        val plainType: Class<FT>,
        val getter: E.() -> FT?,
        val setter: E.(FT?) -> Unit,
    )
}