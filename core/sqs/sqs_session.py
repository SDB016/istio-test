from contextlib import asynccontextmanager
from typing import AsyncGenerator

import aioboto3
import botocore.exceptions

from exceptions.exceptions import AWSNoCredentialsException
from settings import SETTINGS

AWS_ACCESS_KEY_ID = SETTINGS.AWS_ACCESS_KEY_ID
AWS_SECRET_ACCESS_KEY = SETTINGS.AWS_SECRET_ACCESS_KEY
AWS_ENDPOINT_URL = SETTINGS.AWS_ENDPOINT_URL

@asynccontextmanager
async def create_sqs_session(queue_url: str) -> AsyncGenerator:
    try:
        async with aioboto3.Session(
                aws_access_key_id=AWS_ACCESS_KEY_ID,
                aws_secret_access_key=AWS_SECRET_ACCESS_KEY,
                region_name='ap-northeast-2',
                # aws_session_token=None
        ).resource('sqs', endpoint_url=AWS_ENDPOINT_URL) as sqs:
            queue = await sqs.Queue(queue_url)
            yield queue
    except botocore.exceptions.NoCredentialsError as e:
        raise AWSNoCredentialsException()