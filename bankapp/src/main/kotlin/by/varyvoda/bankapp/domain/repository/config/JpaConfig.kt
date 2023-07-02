import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
open class JpaConfig {
    @Bean
    open fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource()
        em.setPackagesToScan(*arrayOf("by.varyvoda.bankapp.domain.repository"))
        val vendorAdapter: JpaVendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter
        em.setJpaProperties(additionalProperties())
        return em
    }

    @Bean
    open fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.postgresql.Driver")
        dataSource.username = "postgres"
        dataSource.password = "1"
        dataSource.url = "jdbc:mysql://localhost:5433/bankapp?createDatabaseIfNotExist=true"
        return dataSource
    }

    @Bean
    open fun transactionManager(): PlatformTransactionManager? {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory().getObject()
        return transactionManager
    }

    @Bean
    open fun exceptionTranslation(): PersistenceExceptionTranslationPostProcessor? {
        return PersistenceExceptionTranslationPostProcessor()
    }

    open fun additionalProperties(): Properties {
        val properties = Properties()
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop")
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
        return properties
    }
}