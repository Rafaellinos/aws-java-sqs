package br.com.rafaellino.sqs.config;

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.FifoBatchGroupingStrategy;
import io.awspring.cloud.sqs.listener.ListenerMode;
import io.awspring.cloud.sqs.listener.acknowledgement.AcknowledgementOrdering;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;
import java.time.Duration;

@Configuration
public class SqsConfiguration {

  @Bean
  SqsAsyncClient sqsAsyncClient() {
    return SqsAsyncClient.builder()
            .endpointOverride(URI.create("http://localhost:4566"))
            .region(Region.SA_EAST_1)
            .credentialsProvider(StaticCredentialsProvider.create(
                    AwsBasicCredentials.builder()
                            .accessKeyId("fakeAccessKeyId")
                            .secretAccessKey("fakeSecretAccessKey")
                            .accountId("000000000000")
                            .build())
            )
            .build();
  }

  @Bean
  @Primary
  SqsMessageListenerContainerFactory<Object> defaultSqsContainerFactory() {
    return SqsMessageListenerContainerFactory.builder()
            .configure(options -> {
              options.fifoBatchGroupingStrategy(FifoBatchGroupingStrategy.PROCESS_MULTIPLE_GROUPS_IN_SAME_BATCH);
              options.acknowledgementMode(AcknowledgementMode.MANUAL);
              options.messageVisibility(Duration.ofSeconds(30));
              options.acknowledgementInterval(Duration.ZERO);
              options.acknowledgementOrdering(AcknowledgementOrdering.ORDERED);
              options.acknowledgementThreshold(0);
              options.listenerMode(ListenerMode.BATCH);
              options.maxConcurrentMessages(10);
              options.maxMessagesPerPoll(10);
            })
            .sqsAsyncClient(sqsAsyncClient())
            .acknowledgementResultCallback(new AckChecker())
            .build();
  }
}
