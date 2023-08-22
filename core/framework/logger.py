import logging
import sys

from settings import SETTINGS

LOG_LEVEL = SETTINGS.LOG_LEVEL
LOG_FORMAT = SETTINGS.LOG_FORMAT
logging_formatter = logging.Formatter(LOG_FORMAT)

logging_handler = logging.StreamHandler(sys.stdout)
logging_handler.setFormatter(logging_formatter)


def get_logger(name: str) -> logging.Logger:
    """Get a logger with a given name."""
    logger: logging.Logger = logging.getLogger(name)
    logger.addHandler(logging_handler)
    logger.setLevel(LOG_LEVEL.upper())

    return logger
