./mvnw clean package
docker build -t s3-api:lastest -f Docker/Dockerfile.api .
docker compose -f Docker/compose.yaml --env-file .env up