./mvnw clean package
docker build -t s3-api:lastest .
docker compose up