# Sprint_7 

Учебный проект по автотестированию API для приложения по заказу самокатов Яндекс.Самокат.

## Описание

Версия Java 11.

Проект использует следующие библиотеки:
- JUnit 4
- RestAssured
- Allure

## Документация

[Ссылка](https://qa-scooter.praktikum-services.ru/docs/) на документацию учебного сервиса Яндекс.Самокат.
[Ссылка](http://qa-scooter.praktikum-services.ru) на учебное приложение Яндекс.Самокат.

### Запуск автотестов

Для запуска автотестов необходимо:

1. Скачать код

 ```sh
   git clone https://github.com/ManaskinaTatyanaSergeevna/Sprint_7.git
   ```
   
2. Запустить команду в проекте

```sh
mvn clean test
```

3. Для создания отчета в Allure ввести команду

```sh
mvn allure:report
```

### Структура проекта

```bash
.gitignore
pom.xml
README.md
src
|-- main
|   |-- java
|   |   |-- api
|   |   |   |-- client
|   |   |   |   |-- CourierClient.java
|   |   |   |-- model
|   |   |   |   |-- AvailableStation.java
|   |   |   |   |-- Courier.java
|   |   |   |   |-- ListOfOrders.java
|   |   |   |   |-- Order.java
|   |   |   |   |-- PageInfo.java
|   |   |   |-- util
|   |   |   |   |-- Colors.java
|-- test
|   |-- java
|   |   |-- tests
|   |   |   |-- courier
|   |   |   |   |-- CourierLoginTest.java
|   |   |   |   |-- CreateCourierTest.java
|   |   |   |-- order
|   |   |   |   |-- CreateOrderTest.java
|   |   |   |   |-- GetListOfOrdersTest.java
 ```
