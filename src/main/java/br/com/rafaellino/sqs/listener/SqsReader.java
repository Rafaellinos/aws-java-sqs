package br.com.rafaellino.sqs.listener;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SqsReader {

  @SqsListener
  public void listener(@Payload List<Message<String>> messages, Acknowledgement acknowledgement) {
    for (Message<String> message : messages) {
      System.out.println(message.getPayload());
      System.out.println(message.getHeaders());
    }
    acknowledgement.acknowledge();
  }



}
