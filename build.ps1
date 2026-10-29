./mvnw clean package -DskipTests
cp target/s3_api_service-0.0.1-SNAPSHOT.jar Docker
#docker build -t s3-api:lastest -f Docker/Dockerfile.api .
docker compose -f Docker/compose.yaml  --env-file .env build --no-cache
docker compose -f Docker/compose.yaml  --env-file .env up --force-recreate