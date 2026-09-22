run:
	./mvnw spring-boot:run

rebuild:
	./mvnw clean spring-boot:run

build:
	./mvnw clean package -DskipTests

.PHONY: run rebuild build dev
dev:
	./dev.sh

