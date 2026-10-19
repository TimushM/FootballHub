# Football Fan App

Приложение для футбольных болельщиков: клубы, сборные, матчи, профили игроков, трансферы.

## Запуск (Linux, OpenJDK 21, Maven, Docker)

```bash
docker compose up -d
mvn clean javafx:run
```

Параметры БД можно переопределить переменными окружения: `DB_URL`, `DB_USER`, `DB_PASSWORD`.

## Тесты

```bash
mvn test
```
(нужен запущенный Docker для TestContainers)
