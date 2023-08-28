package com.istiotest.daemon.sqs

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory
import io.awspring.cloud.sqs.listener.ListenerMode
import io.awspring.cloud.sqs.listener.QueueNotFoundStrategy
import io.awspring.cloud.sqs.listener.SqsContainerOptionsBuilder
import io.awspring.cloud.sqs.listener.acknowledgement.AcknowledgementOrdering
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import software.amazon.awssdk.auth.credentials.AwsCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import java.net.URI
import java.time.Duration

@Configuration
class SqsConfig {

    @Value("\${cloud.aws.credentials.access-key}")
    lateinit var accessKey: String

    @Value("\${cloud.aws.credentials.secret-key}")
    lateinit var secretKey: String

    @Value("\${cloud.aws.region}")
    lateinit var region: String

    @Value("\${cloud.aws.endpoint}")
    lateinit var endpoint: String


    @Bean
    fun sqsAsyncClient(): SqsAsyncClient = SqsAsyncClient.builder()
        .credentialsProvider {
            object : AwsCredentials {
                override fun accessKeyId(): String {
                    return accessKey
                }

                override fun secretAccessKey(): String {
                    return secretKey
                }
            }
        }
        .region(Region.of(region))
        .endpointOverride(URI(endpoint))
        .build()

    @Bean
    fun defaultSqsListenerContainerFactory(): SqsMessageListenerContainerFactory<Any>? {
        return SqsMessageListenerContainerFactory
            .builder<Any>()
            .configure { options: SqsContainerOptionsBuilder ->
                options
                    .listenerMode(ListenerMode.SINGLE_MESSAGE)
                    .acknowledgementMode(AcknowledgementMode.MANUAL)
//                    .acknowledgementInterval(Duration.ZERO)
//                    .acknowledgementThreshold(0)
                    .acknowledgementOrdering(AcknowledgementOrdering.ORDERED)
                    .maxConcurrentMessages(1)
                    .maxMessagesPerPoll(1)
                    .queueNotFoundStrategy(QueueNotFoundStrategy.FAIL)
                    .messageVisibility(Duration.of(5, java.time.temporal.ChronoUnit.MINUTES))
            }
            .sqsAsyncClient(sqsAsyncClient())
            .build()
    }

    @Bean
    fun mappingJackson2MessageConverter(): MappingJackson2MessageConverter? {
        val jackson2MessageConverter = MappingJackson2MessageConverter()
        jackson2MessageConverter.isStrictContentTypeMatch = false
        return jackson2MessageConverter
    }
}