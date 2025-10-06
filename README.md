# Для разработки:

```bash
# Запустить только базы данных
docker-compose -f docker-compose-dev.yaml up -d

# Запустить приложение локально
./mvnw spring-boot:run
```

---

# Для продакшена:

```bash
# Собрать приложение
./mvnw clean package

# Запустить все сервисы
docker-compose up -d
```

---

# Для остановки:

```bash
docker-compose down
```

---

# Структура проекта:

```
src/main/java/com/hamming/bookhub/
├── domain/
│   ├── model/
│   │   ├── entity/
│   │   └── enums/
│   └── exception/              # Кастомные исключения доменного уровня
│       ├── BookNotFoundException.java
│       ├── UserNotFoundException.java
│       └── DomainException.java
├── application/
│   ├── service/
│   └── exception/              # Исключения уровня приложения
├── infrastructure/
│   ├── config/
│   ├── controller/
│   └── exception/              # Исключения инфраструктурного уровня
└── shared/
    ├── exception/              # Общие исключения
    │   ├── GlobalException.java
    │   ├── ValidationException.java
    │   └── ErrorCode.java
    └── handler/                # Глобальные обработчики
        ├── GlobalExceptionHandler.java
        ├── RestResponseEntityExceptionHandler.java
        └── ValidationExceptionHandler.java
```
