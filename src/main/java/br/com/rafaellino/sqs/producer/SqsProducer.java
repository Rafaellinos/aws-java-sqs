//package br.com.rafaellino.sqs.producer;
//
//import io.awspring.cloud.sqs.operations.SqsSendOptions;
//import io.awspring.cloud.sqs.operations.SqsTemplate;
//import io.awspring.cloud.sqs.operations.SqsTemplateBuilder;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@Component
//@RequiredArgsConstructor
//public class SqsProducer {
//
//  private final SqsTemplate sqsTemplate;
//  private static final int NUM_THREADS = 100;
//
//  @Value("${sqs.queue.name}")
//  private String queueName;
//
//
//  public void produceMany() {
//    ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
//
//    // Submit 100 tasks
//    for (int i = 1; i <= NUM_THREADS; i++) {
//
//      executor.submit(() -> {
//        while (true) {
//          List<Message<String>> messages = new ArrayList<>(10);
//          for (int m = 0; m < 10; m ++) {
//            messages.add(MessageBuilder.withPayload("hello from " + UUID.randomUUID()).build());
//          }
//          sqsTemplate.sendMany(queueName, messages); // send batch of 10
//        }
//      });
//
//    }
//
//    // Shutdown the executor (gracefully waits for tasks to finish)
//    executor.shutdown();
//  }
//}
