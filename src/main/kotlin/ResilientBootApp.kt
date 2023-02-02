package ch.frankel.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.coRouter

@SpringBootApplication
@ConfigurationPropertiesScan
class ResilientBootApplication

fun main(args: Array<String>) {

    val beans = beans {
        bean {
            val props = ref<BucketProperties>()
            BucketFactory().create(
                props.size,
                props.refresh.tokens,
                props.refresh.duration
            )
        }
        bean {
            coRouter {
                val handler = HelloHandler(ref())
                GET("/hello") { handler.hello(it) }
                GET("/hello/{who}") { handler.helloWho(it) }
            }
        }
    }

    runApplication<ResilientBootApplication>(*args) {
        addInitializers(beans)
    }
}
