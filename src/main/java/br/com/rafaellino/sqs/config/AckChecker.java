package br.com.rafaellino.sqs.config;

import io.awspring.cloud.sqs.listener.acknowledgement.AcknowledgementResultCallback;
import org.springframework.messaging.Message;

import java.util.Collection;

public class AckChecker implements AcknowledgementResultCallback<Object> {

  @Override
  public void onSuccess(Collection<Message<Object>> messages) {
    AcknowledgementResultCallback.super.onSuccess(messages);
  }

  @Override
  public void onFailure(Collection<Message<Object>> messages, Throwable t) {
    AcknowledgementResultCallback.super.onFailure(messages, t);
  }
}
