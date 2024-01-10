FROM python:3.10.12-slim-buster AS base
WORKDIR /app
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

FROM base AS final
COPY . .
CMD ["python","-u", "runserver.py"]