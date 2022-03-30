# Recipe App Server

## Description
Сервер для мобильного приложения каталога рецептов, разработанный в рамках проекта по СИПИ.

## Usage
### Dependencies

- JDK 17
- Docker (не обязателен)

Java 17 не обязательно ставить в path.  
Выставить переменные среды RB_DB_HOST, RB_DB, RB_DB_USER, RB_DB_PASSWORD, RB_DEFAULT_PWD.  
Docker нужен только если хотите запустить как контейнер.

### Intellij IDEA
Если не ставили Java 17 в path то зайти в File -> Project Structure, добавить и выбрать JDK 17.
Запустить/собрать тыкая на соответствующие кнопки.
Сервер будет досутпен по http://localhost:8082/

### Docker
Сервер запущенный в докере потребляет значительно меньше ресурсов чем Intellij.  

Перейти в папку с проектом, собрать образ c тэгом по усмотрению.

```bash
docker build -t <tag name> .
```

Запустить контейнер, вместо порта 8082 можно выбрать любой открытый, например `-p 9092:8082`.  
Сервер будет досутпен по http://localhost:8082/ ( если не менять порт).
```bash
docker run -d --rm -p 8082:8082 -e RB_DB_HOST=$RB_DB_HOST -e RB_DB=$RB_DB -e RB_DB_USER=$RB_DB_USER -e RB_DB_PASSWORD=$RB_DB_PASSWORD -e RB_DEFAULT_PWD=$RB_DEFAULT_PWD --name <my name> <tag name>
```

Остановить контейнер.
```bash
docker stop <my name>
```