# RabbitMQ Service Runbook

This runbook explains how to operate the RabbitMQ instance provided via `docker-compose.yml`.

## Prerequisites
- Docker Engine 20.10+ and Docker Compose v2 (bundled with Docker Desktop on Windows)
- Access to the project directory `rabbitmq-microservice`

## Configuration
- The default credentials are `guest` / `guest`.
- Override them by setting environment variables before starting the stack:
  - `RABBITMQ_DEFAULT_USER`
  - `RABBITMQ_DEFAULT_PASS`
- Data persists in the named volume `rabbitmq_data`.
- On Windows, Docker stores named volumes inside its Linux VM (Hyper-V or WSL2). The data persists normally, though it is not directly visible in the Windows filesystem.

## Start RabbitMQ
From the project root:

```
docker compose up -d
```

This launches the RabbitMQ broker and management console.

## Verify
- Broker: `amqp://localhost:5672`
- Management UI: <http://localhost:15672>

Log in with the configured credentials.

## Stop RabbitMQ

```
docker compose down
```

This stops containers but preserves data in the volume.

## Reset State (Optional)

```
docker compose down -v
```

This removes the containers **and** deletes the `rabbitmq_data` volume.

## Inspect Logs

```
docker compose logs -f rabbitmq
```

Press `Ctrl+C` to exit the log stream.

## Troubleshooting
- Ensure no other service is bound to ports `5672` or `15672`.
- Use `docker compose ps` to confirm the container status.
- Restart the container if needed:

```
docker compose restart rabbitmq
```

