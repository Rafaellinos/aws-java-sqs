package br.com.rafaellino.sqs.config;

import io.awspring.cloud.sqs.listener.acknowledgement.AcknowledgementResultCallback;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.Message;

import java.util.Collection;

@Log4j2
public class AckChecker implements AcknowledgementResultCallback<Object> {

  @Override
  public void onSuccess(Collection<Message<Object>> messages) {
    log.info("success on messages {}", messages);
    AcknowledgementResultCallback.super.onSuccess(messages);
  }

  @Override
  public void onFailure(Collection<Message<Object>> messages, Throwable throwable) {
    log.error("failure on ack on messages {}", messages, throwable);
    AcknowledgementResultCallback.super.onFailure(messages, throwable);
  }
}
