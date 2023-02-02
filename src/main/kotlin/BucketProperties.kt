package ch.frankel.blog

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "bucket4j.bucket")
data class BucketProperties(
    val size: Long,
    val refresh: BucketRefresh
)

data class BucketRefresh(
    val tokens: Long,
    val duration: Duration
)
