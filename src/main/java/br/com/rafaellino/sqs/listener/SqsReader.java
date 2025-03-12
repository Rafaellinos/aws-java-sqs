package br.com.rafaellino.sqs.listener;

import br.com.rafaellino.sqs.usecase.ProcessUseCase;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.BatchAcknowledgement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
class SqsReader {
  private final ProcessUseCase processUseCase;

  @SqsListener(
          queueNames = "${sqs.queue.name}",
          factory = "defaultSqsContainerFactory"
  )
  public void listener(@Payload List<Message<String>> messages, BatchAcknowledgement<String> batchAcknowledgement) {
    for (Message<String> message : messages) {
      log.info("messages received with payload {} and headers {}", message.getPayload(), message.getHeaders());
      processUseCase.processSomething(message.getPayload());
    }
    batchAcknowledgement.acknowledge();
  }



}
