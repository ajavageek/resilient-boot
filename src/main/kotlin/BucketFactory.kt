package ch.frankel.blog

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Refill
import kotlin.time.Duration
import kotlin.time.toJavaDuration

class BucketFactory {

    fun create(size: Long, number: Long, duration: Duration): Bucket {
        val limit = Bandwidth.classic(size, Refill.intervally(number, duration.toJavaDuration()))
        return Bucket.builder()
            .addLimit(limit)
            .build()
    }
}
