package ch.frankel.blog

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Refill
import java.time.Duration

class BucketFactory {

    fun create(size: Long, number: Long, duration: Duration): Bucket {
        val limit = Bandwidth.classic(size, Refill.intervally(number, duration))
        return Bucket.builder()
            .addLimit(limit)
            .build()
    }
}
