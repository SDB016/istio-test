from typing import Dict, Any

from settings import SETTINGS
from sqs.sqs_session import create_sqs_session
from framework.logger import get_logger

import re

logger = get_logger(__name__)

queue_url = SETTINGS.SQS_QUEUE_URL


async def send_message_to_queue(message: str) -> Dict[str, Any]:
    async with create_sqs_session(queue_url) as queue:
        try:
            deduplication_id = re.sub(r"[^a-zA-Z0-9!#$%&'()*+,-./:;<=>?@[\]^_`{|}~]", "", message)
            return await queue.send_message(
                MessageBody=message,
                MessageGroupId='group1',
                MessageDeduplicationId=deduplication_id[:30]
            )
        except Exception as e:
            logger.error(f"send_message_to_queue error: {e}")
