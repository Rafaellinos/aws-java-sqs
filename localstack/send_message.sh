aws --endpoint-url=http://localhost:4566 sqs send-message \
  --region sa-east-1 \
  --queue-url http://localhost:4566/000000000000/my-queue.fifo \
  --message-body "Hello FIFO!" \
  --message-group-id "group1"
