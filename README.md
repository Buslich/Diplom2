# Путешествие дня - Марракэш

## Описание
"Путешествие дня - Марракэш" — это веб-приложение для покупки туров с возможностью оплаты по дебетовой карте или с использованием уникальной технологии кредитования по данным банковской карты.

## Требования
Прежде чем приступить к запуску проекта, убедитесь, что у вас установлены следующие компоненты:

- **Операционная система**: Windows 10 Pro 
- **Браузер**: Google Chrome версии 130.0.6723.117 (64-bit)
- **IDE**: IntelliJ IDEA 2024.1.2 (Community Edition)
- **Java**: OpenJDK 11
- **Docker**: Docker Desktop  4.35.1

## Шаги для запуска

### 1. Клонирование репозитория
Для начала необходимо клонировать проект на ваш локальный компьютер. Для этого откройте терминал и выполните следующую команду:

```bash
git clone https://github.com/Buslich/Diplom2
```
### 2. Запуск проекта
Запустите необходимые контейнеры командой:

```bash
docker compose up
```
Запустите Java-приложение в зависимости от используемой базы данных:

- **Для MySQL**:

```bash
  java -jar aqa-shop.jar
```
- **Для PostgreSQL**:

```bash
java -jar aqa-shop.jar -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app
```
### 3. Открытие проекта в браузере
Откройте браузер и перейдите по адресу http://localhost:8080 для доступа к приложению.

### 4. Запуск автотестов и генерация отчета Allure

- **Для MySQL**:

```bash
./gradlew clean test
```
- **Для PostgreSQL**:
```bash
./gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app
```
- **Для генерации отчета Allure и его автоматического открытия в браузере выполните**:

```bash
./gradlew allureServe
```