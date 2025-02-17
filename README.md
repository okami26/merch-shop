# Merch Shop
# Тестовое задание Avito стажировка
## Описание

Этот проект представляет собой сервис магазина мерча, где сотрудники могут обмениваться монетками и приобретать товары. Приложение построено с использованием Spring Boot, Java и PostgreSQL.

## Запуск проекта

1. Убедитесь, что у вас установлен [Docker](https://www.docker.com/get-started) и [Docker Compose](https://docs.docker.com/compose/install/).
2. Клонируйте репозиторий:

   ```bash
   git clone https://github.com/okami26/merch-shop.git
   cd merch-shop

3. Постройте проект с помощью Maven:
   ```bash
     mvn clean install
4. Запустите приложение с использованием Docker Compose:
   ```bash
     docker-compose up --build
