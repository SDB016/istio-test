from typing import Dict, Any

from fastapi import APIRouter, Request
from pydantic import BaseModel

from sqs.message_sender import send_message_to_queue
import json

core_api = APIRouter()

class Message(BaseModel):
    message: str

@core_api.post("/sendMessage", description="send message to queue")
async def send_message(request: Request, message: Message) -> Dict[str, Any]:
    request_body_str = (await request.body()).decode("utf-8")
    request_body_str = json.dumps(json.loads(request_body_str), separators=(',', ':'))
    response = await send_message_to_queue(request_body_str)
    return {"response": response}