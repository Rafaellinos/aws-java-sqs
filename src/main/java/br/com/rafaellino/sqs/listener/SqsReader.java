package br.com.rafaellino.sqs.listener;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class SqsReader {

  @SqsListener
  public void listener(@Payload List<Message<String>> messages, Acknowledgement acknowledgement) {
    for (Message<String> message : messages) {
      log.info("messages received with payload {} and headers {}", message.getPayload(), message.getHeaders());
    }
    acknowledgement.acknowledge();
  }



}
