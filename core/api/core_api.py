from typing import Dict, Any

from fastapi import APIRouter

from sqs.message_sender import send_message_to_queue

core_api = APIRouter()


@core_api.post("/{message}", description="send message to queue")
async def send_message(message: str) -> Dict[str, Any]:
    response = send_message_to_queue(message)
    return {"response": response}