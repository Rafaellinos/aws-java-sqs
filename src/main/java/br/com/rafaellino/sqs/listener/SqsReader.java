package br.com.rafaellino.sqs.listener;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.BatchAcknowledgement;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
class SqsReader {

  @SqsListener(
          queueNames = "my-queue.fifo",
          factory = "defaultSqsContainerFactory"
  )
  public void listener(@Payload List<Message<String>> messages, BatchAcknowledgement<String> batchAcknowledgement) {
    for (Message<String> message : messages) {
      log.info("messages received with payload {} and headers {}", message.getPayload(), message.getHeaders());
    }
    batchAcknowledgement.acknowledge();
  }



}
