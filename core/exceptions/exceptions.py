from fastapi import HTTPException
from starlette import status


class BaseHTTPException(HTTPException):
    status_code = status.HTTP_500_INTERNAL_SERVER_ERROR
    default_message = ""

    def __init__(self, detail: str = None) -> None:
        if detail is None:
            detail = self.default_message
        super().__init__(status_code=self.status_code, detail=detail)


class AWSNoCredentialsException(BaseHTTPException):
    default_message = "AWS Access_Key_ID or AWS Secret_Access_key is not available"
