# Url shortener

Сокращатель ссылок.

Демо: [url.vlados.me](http://url.vlados.me)

Swagger: [url.vlados.me/swagger-ui/index.html](http://url.vlados.me/swagger-ui/index.html#/)

Приложение создано с использованием:

* Spring Boot
* OpenAPI
* PostgreSQL
* Docker

## API

| Метод      | Путь                       | Описание                                        | Пользователь должен быть аутентифицирован     |
|---------|-------------------------|----------------------------------------------|:------------------------------------------:|
| GET        | /api/v1/url/{shortUrl}     | Перейти на оригинальный сайт                    |                                            |
| POST       | /api/v1/url                | Создать новую коротную ссылку                   |                     x                      |
| DELETE     | /api/v1/url                | Удалить ссылку по id                            |                     x                      |
| DELETE     | /api/v1/url/expired        | Удалить все ссылки, чей срок годности истёк     |                     x                      |
| GET        | /api/v1/statistics         | Получить статистику о всех ссылках              |                     x                      |
| POST       | /api/v1/auth/login         | Вход в аккаунт                                  |                                            |
| POST       | /api/v1/register           | Создание нового аккаунта                        |                                            |

## Сборка и запуск

Сборка и запуск приложения:

```
./gradlew bootRun
```

## Сбора и запуск с использованием Docker

```
./gradlew bootJar
docker build -t url_shortener:latest .
docker-compose up -d
```