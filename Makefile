run:
	./mvnw spring-boot:run

rebuild:
	./mvnw clean spring-boot:run

build:
	./mvnw clean package -DskipTests

stop-brew-pg:
	brew services stop postgresql@14

.PHONY: run rebuild build stop-brew-pg dev
dev:
	./dev.sh

