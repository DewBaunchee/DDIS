package by.varyvoda.bankapp.domain.repository

import by.varyvoda.bankapp.domain.dependency.DependencyProvider
import by.varyvoda.bankapp.domain.repository.connection.ConnectionProvider
import by.varyvoda.bankapp.domain.repository.mapping.EntityMapping
import java.sql.JDBCType
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.time.LocalDate
import java.util.stream.Stream
import kotlin.streams.toList

abstract class AbstractRepository<Id, E>(protected val entityMapping: EntityMapping<E>) : Repository<Id, E> {

    private val connection = DependencyProvider.get().provide(ConnectionProvider::class.java).getConnection()

    private val nonGeneratedColumns =
        entityMapping
            .nonGeneratedFields
            .joinToString(",") { it.columnName }

    private val allColumns =
        entityMapping
            .fields
            .joinToString(",") { it.columnName }

    private val valuesString =
        entityMapping
            .nonGeneratedFields
            .joinToString(",", "(", ")") { "?" }

    private val whereId = "WHERE ${entityMapping.idField.columnName} = ?"

    private val tableName = entityMapping.tableName

    // Templates
    private val insertTemplate = "INSERT INTO $tableName ($nonGeneratedColumns) VALUES $valuesString"

    private val updateTemplate = "UPDATE $tableName SET ${
        entityMapping
            .fields
            .joinToString(", ") { "${it.columnName} = ?" }
    } $whereId"

    private val selectTemplate = "SELECT $allColumns FROM $tableName $whereId"

    private val selectAllTemplate = "SELECT $allColumns FROM $tableName"

    private val deleteTemplate = "DELETE FROM $tableName $whereId"

    override fun save(entity: E) {
        val query = connection.prepareStatement(insertTemplate)
        prepare(query, entity)
        query.execute()
    }

    private val accessors = MapAccessors<E>()
        .add(
            Float::class.java,
            { query, position, value -> query.setFloat(position, value) },
            { result, columnLabel -> result.getFloat(columnLabel) },
            JDBCType.FLOAT
        )
        .add(
            Double::class.java,
            { query, position, value -> query.setDouble(position, value) },
            { result, columnLabel -> result.getDouble(columnLabel) },
            JDBCType.DOUBLE
        )
        .add(
            Int::class.java,
            { query, position, value -> query.setInt(position, value) },
            { result, columnLabel -> result.getInt(columnLabel) },
            JDBCType.INTEGER
        )
        .add(
            String::class.java,
            { query, position, value -> query.setString(position, value) },
            { result, columnLabel -> result.getString(columnLabel) },
            JDBCType.VARCHAR
        )
        .add(
            Boolean::class.java,
            { query, position, value -> query.setBoolean(position, value) },
            { result, columnLabel -> result.getBoolean(columnLabel) },
            JDBCType.BOOLEAN
        )
        .add(
            LocalDate::class.java,
            { query, position, value -> query.setObject(position, value) },
            { result, columnLabel -> result.getObject(columnLabel, LocalDate::class.java) },
            JDBCType.DATE
        )

    private fun prepare(query: PreparedStatement, entity: E) {
        entityMapping.nonGeneratedFields.forEachIndexed { index, field ->
            set(query, entity, index + 1, field)
        }
    }

    private fun <T> setValue(
        query: PreparedStatement,
        value: T,
        position: Int,
        field: EntityMapping.Field<E, out Any>
    ) {
        if (value == null) {
            query.setNull(position, accessors.jdbcType(field).vendorTypeNumber)
            return
        }
        accessors.prepare(field as EntityMapping.Field<E, T>)(query, position, value)
    }

    private fun set(
        query: PreparedStatement,
        entity: E,
        position: Int,
        field: EntityMapping.Field<E, out Any>
    ) {
        setValue(query, field.getter(entity), position, field)
    }

    override fun update(entity: E) {
        val query = connection.prepareStatement(updateTemplate)
        prepare(query, entity)
        setValue(
            query,
            entityMapping.idField.getter(entity),
            entityMapping.fields.size + 1,
            entityMapping.idField
        )
        query.execute()
    }

    override fun get(id: Id): E? {
        return getOneBy(listOf(entityMapping.idField to id!!))
    }

    protected fun getOneBy(values: List<Pair<EntityMapping.Field<E, out Any>, Any>>): E? {
        return getListBy(values).getOrNull(0)
    }

    protected fun getListBy(values: List<Pair<EntityMapping.Field<E, out Any>, Any>>): List<E> {
        val query = connection.prepareStatement(
            "SELECT $allColumns FROM $tableName WHERE "
                + values.joinToString(" AND ") { "${it.first.columnName} = ?" }
        )
        values.forEachIndexed { index, value ->
            setValue(query, value.second, index + 1, value.first)
        }

        val result = query.executeQuery()
        return Stream.generate { }
            .takeWhile { result.next() }
            .map { parse(result) }
            .toList()
    }

    override fun getAll(): List<E> {
        val query = connection.prepareStatement(selectAllTemplate)
        val result = query.executeQuery()
        return Stream.generate { }
            .takeWhile { result.next() }
            .map { parse(result) }
            .toList()
    }

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
            accessors.getter(field)(resultSet, field.columnName)
        )
    }

    override fun delete(id: Id) {
        val query = connection.prepareStatement(deleteTemplate)
        setValue(query, id!!, 1, entityMapping.idField)
        query.execute()
    }

    private class MapAccessors<E> {

        private val map = mutableMapOf<Any, Accessors<out Any>>()

        fun <T : Any> add(
            aClass: Class<T>,
            preparer: (PreparedStatement, Int, T) -> Unit,
            getter: (ResultSet, String) -> T?,
            jdbcType: JDBCType
        ): MapAccessors<E> {
            map[aClass] = Accessors(preparer, getter, jdbcType)
            return this
        }

        fun <FT> prepare(field: EntityMapping.Field<E, FT>): (PreparedStatement, Int, FT?) -> Unit {
            return map[field.plainType]!!.preparer as (PreparedStatement, Int, FT?) -> Unit
        }

        fun <FT> getter(field: EntityMapping.Field<E, FT>): (ResultSet, String) -> FT? {
            return map[field.plainType]!!.getter as (ResultSet, String) -> FT?
        }

        fun <FT> jdbcType(field: EntityMapping.Field<E, FT>): JDBCType {
            return map[field.plainType]!!.jdbcType
        }

        class Accessors<T>(
            val preparer: (PreparedStatement, Int, T) -> Unit,
            val getter: (ResultSet, String) -> T?,
            val jdbcType: JDBCType
        )
    }
}

