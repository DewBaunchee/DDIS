package by.varyvoda.bankapp.domain.repository;

interface Repository<Id, E> {

    fun save(entity: E)

    fun update(entity: E)

    fun get(id: Id): E?

    fun getAll(): List<E>

    fun delete(id: Id)
}
