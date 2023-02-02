package ch.frankel.blog

import io.github.bucket4j.Bucket
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import java.util.*


class HelloHandler(private val bucket: Bucket) {

    private suspend fun rateLimit(req: ServerRequest, f: suspend (ServerRequest) -> ServerResponse) =
        if (bucket.tryConsume(1))
            f.invoke(req)
        else
            ServerResponse.status(429).buildAndAwait()

    suspend fun hello(req: ServerRequest) = rateLimit(req) {
        ServerResponse.ok().bodyValueAndAwait("Hello World!")
    }

    suspend fun helloWho(req: ServerRequest) = rateLimit(req) {
        val capitalized = req.pathVariable("who").replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault())
            else it.toString()
        }
        ServerResponse.ok().bodyValueAndAwait("Hello $capitalized")
    }
}
