## Тестирование функциональности заказа карт. Формирование отчетов через Allure
___

**Руководство к использованию:**

Для реализации автотестов был создан утиличный класс-генератор данных, а также использовался Faker, Lombok.
* Запустить приложение командой

```
java -jar app-card-delivery.jar
```
* Запустить автотесты командой

```
./gradlew test -Dselenide.headless=true --info
```
* Запустить отчеты командой:

```
./gradlew allureReport (первоначальная команда)
```
```
./gradlew allureServe (запуск и открытие отчетов)
```

### Результаты тестирования
В ходе тестирования были выявлены ошибки приложения. По ошибкам заведены issue:

1.[Поле "Фамилия Имя" допускает ввод только имен без ограничений](https://github.com/Zumaletto/HW2-3-DeliveryNewDate/issues/1)

2.[Фамилия с "Ё" не проходит валидацию в поле "Фамилия Имя"](https://github.com/Zumaletto/HW2-3-DeliveryNewDate/issues/2)

3.[Поле "Мобильный телефон" не выдает сообщение об ошибке при вводе невалидного телефона](https://github.com/Zumaletto/HW2-3-DeliveryNewDate/issues/3)

Результат отчета [Allure](https://github.com/Zumaletto/HW4-1-Allure/tree/master/build/reports/allure-report)
