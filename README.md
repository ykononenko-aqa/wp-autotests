# wp-autotests

Учебный проект для освоения **Selenide** и практики UI-автоматизации на Java.
## Стек

- **Java 17**, **Gradle 8.5**
- **Selenide** — UI-тесты
- **REST Assured** — API-тесты и подготовка данных
- **JUnit 5** — тестовый фреймворк
- **AssertJ** — ассерты (включая SoftAssertions)
- **Allure** — отчёты
- **Lombok**, **DataFaker**
- ## Инфраструктура

- WordPress + MySQL + Adminer в Docker Compose
- WordPress: `http://localhost:8080`
- Adminer: `http://localhost:8081` (сервер `db`, пользователь `wordpress`, пароль `wordpress`)

## WordPress REST API
WordPress не поддерживает Basic Auth из коробки. Нужно:
Скачать ZIP-архив: WP-API/Basic-Auth
В админке WordPress: Плагины → Добавить новый → Загрузить плагин
Выбрать скачанный архив, нажать Установить, затем Активировать

