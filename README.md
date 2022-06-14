## Запуск
### Запуск без получения образа из Docker hub
Для начала нужно забилдить проект, для этого нужно выполнить следующую строку в терминале из корневой папки проекта:
```
gradlew build
```
Далее необходимо создать образ Docker:
```
docker build -t delta_currency_checker .
```
Сам запуск:
```
docker run -dp 8080:8080 delta_currency_checker
```

### Запуск с получением образа из Docker hub
```
docker run -dp 8080:8080 smirnovss/delta_currency_checker
```
## Эндпоинты

| Эндпоинты  | Возможные значения  | Описание |
| :------------ |:---------------| -----|
| GET /api/checkDelta/{currencyCode}  | Код валюты, состоящий из трех латинских букв. Пример: RUB, EUR, JPY | Ответ в формате json  |
| GET /checkDelta/{currencyCode}      | Код валюты, состоящий из трех латинских букв. Пример: RUB, EUR, JPY |   Ответ в формате html-траницы |
