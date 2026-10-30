./mvnw clean package -DskipTests
cp target/s3_api_service-0.0.1-SNAPSHOT.jar Docker
docker compose -f Docker/compose.yaml  --env-file .env rm -sf s3-api
docker compose -f Docker/compose.yaml  --env-file .env build --no-cache
docker compose -f Docker/compose.yaml  --env-file .env up --force-recreate