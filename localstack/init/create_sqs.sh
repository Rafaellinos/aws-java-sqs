#!/bin/bash
set -e

# Queue names
QUEUE_NAME="my-queue.fifo"
DLQ_NAME="my-queue-dlq.fifo"

# Create the DLQ
DLQ_URL=$(awslocal sqs create-queue --queue-name "$DLQ_NAME" \
  --attributes '{
    "FifoQueue": "true",
    "ContentBasedDeduplication": "true"
  }' | jq -r '.QueueUrl')

echo "✅ DLQ '$DLQ_NAME' created successfully! URL: $DLQ_URL"

# Get the ARN of the DLQ
DLQ_ARN=$(awslocal sqs get-queue-attributes --queue-url "$DLQ_URL" \
  --attribute-names QueueArn | jq -r '.Attributes.QueueArn')

# Create the main FIFO queue with Redrive Policy
awslocal sqs create-queue --queue-name "$QUEUE_NAME" \
  --attributes "{
    \"FifoQueue\": \"true\",
    \"ContentBasedDeduplication\": \"true\",
    \"RedrivePolicy\": \"{\\\"deadLetterTargetArn\\\": \\\"$DLQ_ARN\\\", \\\"maxReceiveCount\\\": \\\"3\\\"}\"
  }"

echo "✅ SQS FIFO queue '$QUEUE_NAME' created successfully and linked to DLQ '$DLQ_NAME'!"
