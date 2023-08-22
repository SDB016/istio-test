from contextlib import asynccontextmanager
from typing import AsyncGenerator

import aioboto3
import botocore.exceptions

from exceptions.exceptions import AWSNoCredentialsException
from settings import SETTINGS

AWS_ACCESS_KEY_ID = SETTINGS.AWS_ACCESS_KEY_ID
AWS_SECRET_ACCESS_KEY = SETTINGS.AWS_SECRET_ACCESS_KEY


@asynccontextmanager
async def create_sqs_session(queue_url: str) -> AsyncGenerator:
    try:
        async with aioboto3.Session(
                aws_access_key_id=AWS_ACCESS_KEY_ID,
                aws_secret_access_key=AWS_SECRET_ACCESS_KEY
        ).resource('sqs') as sqs:
            queue = await sqs.Queue(queue_url)
            yield queue
    except botocore.exceptions.NoCredentialsError as e:
        raise AWSNoCredentialsException()