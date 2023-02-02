package ch.frankel.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.coRouter
import kotlin.time.Duration.Companion.seconds

@SpringBootApplication
class ResilientBootApplication

fun main(args: Array<String>) {

    val beans = beans {
        bean {
            BucketFactory().create(1, 1, 1.seconds)
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
