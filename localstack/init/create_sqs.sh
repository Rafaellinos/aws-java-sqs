#!/bin/bash
set -e

# Queue names
QUEUE_NAME="my-queue.fifo"
DLQ_NAME="my-queue-dlq.fifo"

# Create the DLQ and get its URL
DLQ_URL=$(awslocal sqs create-queue --queue-name "$DLQ_NAME" \
  --attributes '{"FifoQueue": "true", "ContentBasedDeduplication": "true"}' \
  --query "QueueUrl" --output text)

echo "✅ DLQ '$DLQ_NAME' created successfully! URL: $DLQ_URL"

# Get the ARN of the DLQ using AWS CLI built-in query
DLQ_ARN=$(awslocal sqs get-queue-attributes --queue-url "$DLQ_URL" \
  --attribute-names QueueArn --query "Attributes.QueueArn" --output text)

# Create the main FIFO queue with Redrive Policy
awslocal sqs create-queue --queue-name "$QUEUE_NAME" \
  --attributes "{
    \"FifoQueue\": \"true\",
    \"ContentBasedDeduplication\": \"true\",
    \"RedrivePolicy\": \"{\\\"deadLetterTargetArn\\\": \\\"$DLQ_ARN\\\", \\\"maxReceiveCount\\\": \\\"3\\\"}\"
  }"

echo "✅ SQS FIFO queue '$QUEUE_NAME' created successfully and linked to DLQ '$DLQ_NAME'!"
