package ch.frankel.blog

import io.github.bucket4j.Bucket
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import java.util.*


class HelloHandler(private val bucket: Bucket) {

    suspend fun hello(req: ServerRequest) =
        if (bucket.tryConsume(1))
            ServerResponse.ok().bodyValueAndAwait("Hello World!")
        else
            ServerResponse.status(429).buildAndAwait()

    suspend fun helloWho(req: ServerRequest) =
        if (bucket.tryConsume(1)) {
            val capitalized = req.pathVariable("who").replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                else it.toString()
            }
            ServerResponse.ok().bodyValueAndAwait("Hello $capitalized")
        } else
            ServerResponse.status(429).buildAndAwait()

}
