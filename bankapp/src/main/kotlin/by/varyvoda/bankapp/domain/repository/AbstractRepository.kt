package by.varyvoda.bankapp.domain.repository

import by.varyvoda.bankapp.domain.dependency.DependencyProvider
import by.varyvoda.bankapp.domain.repository.connection.ConnectionProvider
import by.varyvoda.bankapp.domain.repository.mapping.EntityMapping
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.stream.Stream
import kotlin.streams.toList

abstract class AbstractRepository<Id, E>(private val entityMapping: EntityMapping<E>) : Repository<Id, E> {

    private val connection = DependencyProvider.get().provide(ConnectionProvider::class.java).getConnection()

    private val columnsString =
        entityMapping
            .nonIdFields
            .joinToString(",") { it.columnName }

    private val columnsStringWithId =
        entityMapping
            .fields
            .joinToString(",") { it.columnName }

    private val valuesString =
        entityMapping
            .fields
            .joinToString(",", "(", ")") { "?" }

    private val whereId = "WHERE ${entityMapping.idField.columnName} = ?"

    private val tableName = entityMapping.tableName

    // Templates
    private val insertTemplate = "INSERT INTO $tableName ($columnsString) VALUES $valuesString"

    private val updateTemplate = "UPDATE $tableName SET ${
        entityMapping
            .fields
            .joinToString(", ") { "${it.columnName} = ?" }
    } $whereId"

    private val selectTemplate = "SELECT $columnsStringWithId FROM $tableName $whereId"

    private val selectAllTemplate = "SELECT $columnsStringWithId FROM $tableName"

    private val deleteTemplate = "DELETE FROM $tableName $whereId"

    override fun save(entity: E) {
        val query = connection.prepareStatement(insertTemplate)
        prepare(query, entity)
        query.execute()
    }

    private val plainSetters = mapOf<Class<out Any>, (PreparedStatement, Int, Any?) -> Unit>(
        Float::class.java to { query, position, value -> query.setFloat(position, value as Float) },
        Double::class.java to { query, position, value -> query.setDouble(position, value as Double) },
        Int::class.java to { query, position, value -> query.setInt(position, value as Int) },
        Long::class.java to { query, position, value -> query.setLong(position, value as Long) },
        String::class.java to { query, position, value -> query.setString(position, value as String) },
    )

    private fun prepare(query: PreparedStatement, entity: E) {
        entityMapping.nonIdFields.forEachIndexed { index, fieldDescription ->
            set(query, entity, index + 1, fieldDescription)
        }
    }

    private fun set(
        query: PreparedStatement,
        entity: E,
        position: Int,
        field: EntityMapping.Field<E, out Any>
    ) {
        plainSetters[field.plainType]!!(query, position, field.getter(entity))
    }

    override fun update(entity: E) {
        val query = connection.prepareStatement(updateTemplate)
        prepare(query, entity)
        plainSetters[entityMapping.idField.plainType]!!(
            query,
            entityMapping.fields.size + 1,
            entityMapping.idField.getter(entity)
        )
        query.execute()
    }

    override fun get(id: Id): E? {
        val query = connection.prepareStatement(selectTemplate)

        val result = query.executeQuery()
        if (!result.next()) return null

        return parse(result)
    }

    override fun getAll(id: Id): List<E> {
        val query = connection.prepareStatement(selectAllTemplate)
        val result = query.executeQuery()
        return Stream.generate { }
            .takeWhile { result.next() }
            .map { parse(result) }
            .toList()
    }

    private val plainGetters = mapOf<Class<out Any>, (ResultSet, String) -> Any?>(
        Float::class.java to { result, columnLabel -> result.getFloat(columnLabel) },
        Double::class.java to { result, columnLabel -> result.getFloat(columnLabel) },
        Int::class.java to { result, columnLabel -> result.getFloat(columnLabel) },
        Long::class.java to { result, columnLabel -> result.getFloat(columnLabel) },
        String::class.java to { result, columnLabel -> result.getFloat(columnLabel) },
    )

    private fun parse(resultSet: ResultSet): E {
        val entity = entityMapping.creator()
        entityMapping.fields.forEach { fieldDescription ->
            set(
                fieldDescription,
                entity,
                resultSet,
            )
        }
        return entity
    }

    private fun <FT> set(field: EntityMapping.Field<E, FT>, entity: E, resultSet: ResultSet) {
        field.setter(
            entity,
            plainGetters[field.plainType]!!(resultSet, field.columnName) as FT
        )
    }

    override fun delete(id: Id) {
        val query = connection.prepareStatement(deleteTemplate)
        plainSetters[entityMapping.idField.plainType]!!(query, 1, id!!)
        query.execute()
    }
}