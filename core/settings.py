from typing import Optional

from pydantic import BaseSettings


class _Settings(BaseSettings):
    LOG_LEVEL: Optional[str] = "INFO"
    LOG_FORMAT: Optional[str] = "[%(asctime)s] %(levelname)s: %(message)s"
    DEBUG: Optional[str]
    APP_HOST: Optional[str]
    APP_PORT: Optional[str]
    SQS_QUEUE_URL: Optional[str]
    AWS_ACCESS_KEY_ID: Optional[str]
    AWS_SECRET_ACCESS_KEY: Optional[str]
    SQS_MAX_NUMBER_OF_MESSAGES: Optional[int] = 10
    SQS_WAIT_TIME_SECONDS: Optional[int] = 20
    SQS_MAX_DELETE_ATTEMPTS: Optional[int] = 3


SETTINGS = _Settings()
