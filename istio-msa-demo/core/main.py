from fastapi import FastAPI

from framework import get_logger
from api import core_api
import time

logger = get_logger(__name__)

app = FastAPI(
    title="istio-demo-core",
    docs_url="/docs",
    redoc_url="/redoc"
)


@app.get("/")
async def root():
    return {"message": "Hello World"}


@app.get("/health", tags=["health"])
async def health_check():
    return {"status": "ok"}

@app.get("/delay/health", tags=["health"])
async def delayed_health_check():
    time.sleep(3)
    return {"status": "ok"}

app.include_router(core_api, prefix="/sqs", tags=["SQS"])
