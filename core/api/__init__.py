from fastapi import APIRouter

from api.core_api import core_api

sqs_router = APIRouter()
sqs_router.include_router(core_api)

__all__ = [
    "core_api"
]