package by.varyvoda.bankapp

import by.varyvoda.bankapp.domain.dependency.registerDependencies
import by.varyvoda.bankapp.domain.repository.init.initializeRepositories
import by.varyvoda.bankapp.ui.view.MainView
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import tornadofx.*
import kotlin.reflect.KClass

@SpringBootApplication
open class BankApp : App(MainView::class) {

    private lateinit var context: ConfigurableApplicationContext

    override fun init() {
        this.context = SpringApplication.run(this.javaClass)
        context.autowireCapableBeanFactory.autowireBean(this)

        FX.dicontainer = object : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>): T = context.getBean(type.java)
            override fun <T : Any> getInstance(type: KClass<T>, name: String): T = context.getBean(name, type.java)
        }
    }

    override fun stop() {
        super.stop()
        context.close()
    }

}

fun main() {
    registerDependencies()
    initializeRepositories()
    launch<BankApp>()
}