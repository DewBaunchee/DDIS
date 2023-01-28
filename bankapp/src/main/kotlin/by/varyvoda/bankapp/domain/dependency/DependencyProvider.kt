package by.varyvoda.bankapp.domain.dependency;

class DependencyProvider private constructor() {

    private val map = HashMap<String, Any>()

    companion object {
        private val INSTANCE = DependencyProvider()

        fun get(): DependencyProvider {
            return INSTANCE
        }
    }

    private fun register(name: String, instance: Any) {
        map[name] = instance
    }

    fun <T>register(aClass: Class<T>, instance: Any) {
        register(classToName(aClass), instance)
    }

    fun provide(name: String): Any {
        return map[name] ?: throw NoSuchElementException("No dependency with name $name")
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> provide(aClass: Class<T>): T {
        val name = classToName(aClass)
        return map[name] as T ?: throw NoSuchElementException("No dependency with name $name")
    }

    private fun <T> classToName(aClass: Class<T>): String {
        return aClass.packageName
    }
}