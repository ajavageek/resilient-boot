package ch.frankel.blog

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import java.util.*


class HelloHandler {
    suspend fun hello(req: ServerRequest) = ServerResponse.ok().bodyValueAndAwait("Hello World!")
    suspend fun helloWho(req: ServerRequest): ServerResponse {
        val capitalized = req.pathVariable("who").replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault())
            else it.toString()
        }
        return ServerResponse.ok().bodyValueAndAwait("Hello $capitalized")
    }
}
