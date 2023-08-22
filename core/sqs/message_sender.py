from typing import Dict, Any

from settings import SETTINGS
from sqs.sqs_session import create_sqs_session

queue_url = SETTINGS.SQS_QUEUE_URL


async def send_message_to_queue(message: str) -> Dict[str, Any]:
    async with create_sqs_session(queue_url) as queue:
        return await queue.send_message(
            MessageBody=message,
            MessageGroupId='group1',
            MessageDeduplicationId=message
        )
