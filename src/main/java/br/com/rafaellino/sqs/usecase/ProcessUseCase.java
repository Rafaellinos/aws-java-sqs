package br.com.rafaellino.sqs.usecase;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProcessUseCase {

  @SneakyThrows
  public void processSomething(final String data) {
    // simulate something being process
    Thread.sleep(500);
    log.info("process success {}", data);
  }

}
