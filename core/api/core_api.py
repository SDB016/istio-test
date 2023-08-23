from typing import Dict, Any

from fastapi import APIRouter
from pydantic import BaseModel

from sqs.message_sender import send_message_to_queue

core_api = APIRouter()

class Message(BaseModel):
    message: str

@core_api.post("/sendMessage", description="send message to queue")
async def send_message(message: Message) -> Dict[str, Any]:
    response = await send_message_to_queue(message.message)
    return {"response": response}