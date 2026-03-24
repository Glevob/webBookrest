# webBookrest
REST‑сервис для управления книгами, авторами и пользователями библиотеки с разграничением прав доступа и унифицированной обработкой ошибок в JSON‑формате.


# Описание предметной области:
Система предназначена для учёта книг и авторов и управления доступом пользователей:
- Хранение книг с названием, датой публикации, ценой, рейтингом и описанием.
- Хранение авторов с ФИО и уникальным email.
- Работа с жанрами и связями «автор–книга».
- Регистрация пользователей, вход по логину (email) и паролю, назначение ролей USER, TECHNOLOGIST, FULL.
- Ограничение доступа к административным операциям (создание/изменение/удаление сущностей) по ролям.
- Все ошибки (валидация, права доступа, отсутствие сущностей, технические сбои) возвращаются в виде JSON‑ответов с кодами 400, 401, 403, 404, 422 и 500


# Стек технологий:
- Java 21
- Spring Boot 3 / Spring Web / Spring Data JPA / Spring Security 6
- Hibernate (JPA‑провайдер, Bean Validation)
- PostgreSQL
- Lombok
- Jakarta Validation (Bean Validation)
- Thymeleaf для web‑страниц админки
- Gradle для сборки


#Как запустить проект:
1. Настройка БД PostgreSQL.
2. Сбор и запуск приложения.
3. Запуск REST API - http://localhost:8080
4. При старте автоматически создается пользователь (с ролью FULL) - логин as@as.com, пароль asdasd


#Описание маршрутов API:
1. Аутентификация
- POST	/auth/login	- вход по логину (email) и паролю, создание сессии
- POST	/auth/logout - выход, завершение сессии
- POST	/api/registration	- регистрация нового пользователя
2. Книги
- GET	/authors_bookss	- получить список всех книг	(для авторизованных)
- GET	/api/books/{id}	- получить книгу по id	(для авторизованных)
- POST	/api/books	- создать книгу	(для TECHNOLOGIST и FULL)
- PUT/PATCH	/api/books/{id}	- обновить книгу	(для TECHNOLOGIST и FULL)
- DELETE	/api/books/{id}	- удалить книгу	(для TECHNOLOGIST и FULL)
3. Авторы
- GET	/authors	- список авторов	(для TECHNOLOGIST и FULL)
- GET	/authors/{id}	- автор по id	(для TECHNOLOGIST и FULL)
- POST	/authors	- создать автора	(для TECHNOLOGIST и FULL)
- PUT	/authors/{id}	- обновить автора	(для TECHNOLOGIST и FULL)
- DELETE	/api/authors/{id}	- удалить автора	(для TECHNOLOGIST и FULL)
4. Администрирование пользователей
- GET	/api/admin/users	- список пользователей	(для FULL)
- GET	/api/admin/users/{id}	- пользователь по id	(для FULL)
- PUT	/api/admin/users/{id}	- обновить логин/пароль/роль	(для FULL)
- DELETE	/api/admin/users/{id}	- удалить пользователя	(для FULL)


#Примеры запросов и ответов:
1. POST http://localhost:8080/api/books
Тело запроса:
{
  "bookTitle": "Новая книга",
  "datePublic": "2024-01-01",
  "price": 100,
  "star": 4,
  "descriptBook": "Интересная книга",
  "genreId": 1
}

Ответ 201:
{
    "idBook": 6,
    "bookTitle": "Новая книга",
    "datePublic": "2024-01-01",
    "price": 100,
    "star": 4,
    "descriptBook": "Интересная книга",
    "cover": null,
    "genre": {
        "idGenre": 1,
        "genreName": "Сказка"
    }
}
2. GET http://localhost:8080/api/books
Ответ 200:
[
    {
        "idBook": 3,
        "bookTitle": "Гарри Поттер и Философский Камень",
        "datePublic": "1997-06-26",
        "price": 1300,
        "star": 5,
        "descriptBook": "Перечитал, так как подарили на День Рождения полную коллекцию в правильном переводе. Первый раз читал ещё в школе",
        "cover": null,
        "genre": {
            "idGenre": 6,
            "genreName": "Художественная литература"
        }
    },
    {
        "idBook": 5,
        "bookTitle": "Название",
        "datePublic": "2024-01-01",
        "price": 100,
        "star": 3,
        "descriptBook": "Описание книги",
        "cover": null,
        "genre": {
            "idGenre": 1,
            "genreName": "Сказка"
        }
    },
    {
        "idBook": 6,
        "bookTitle": "Новая книга",
        "datePublic": "2024-01-01",
        "price": 100,
        "star": 4,
        "descriptBook": "Интересная книга",
        "cover": null,
        "genre": {
            "idGenre": 1,
            "genreName": "Сказка"
        }
    }
]

3. GET http://localhost:8080/api/books/3
Ответ 200:
{
    "idBook": 3,
    "bookTitle": "Гарри Поттер и Философский Камень",
    "datePublic": "1997-06-26",
    "price": 1300,
    "star": 5,
    "descriptBook": "Перечитал, так как подарили на День Рождения полную коллекцию в правильном переводе. Первый раз читал ещё в школе",
    "cover": null,
    "genre": {
        "idGenre": 6,
        "genreName": "Художественная литература"
    }
}
4. http://localhost:8080/api/books/3
Тело запроса:
{
  "bookTitle": "ITwd",
  "datePublic": "1986-09-15",
  "price": 1000,
  "star": 5,
  "descriptBook": "Ужастик, про детей в конце 50-ых и выросших в середине 80-ых. Смотрел только новые экранизации",
  "genreId": 4
}
Ответ 200:
{
    "idBook": 3,
    "bookTitle": "ITwd",
    "datePublic": "1986-09-15",
    "price": 1000,
    "star": 5,
    "descriptBook": "Ужастик, про детей в конце 50-ых и выросших в середине 80-ых. Смотрел только новые экранизации",
    "cover": null,
    "genre": {
        "idGenre": 4,
        "genreName": "Ужас"
    }
}
5. PATCH http://localhost:8080/api/books/3
Тело запроса:
{
  "price": 1300,
  "descriptBook": "Перечитал, так как подарили на День Рождения полную коллекцию в правильном переводе. Первый раз читал ещё в школе"
}
Ответ 200:
{
    "idBook": 3,
    "bookTitle": "ITwd",
    "datePublic": "1986-09-15",
    "price": 1300,
    "star": 5,
    "descriptBook": "Перечитал, так как подарили на День Рождения полную коллекцию в правильном переводе. Первый раз читал ещё в школе",
    "cover": null,
    "genre": {
        "idGenre": 4,
        "genreName": "Ужас"
    }
}
6. DELETE http://localhost:8080/api/books/3
Ответ 204 No Content
7. Получение пользователя (админ)
GET http://localhost:8080/api/admin/users/1
Ответ 200:
{
    "idUser": 12,
    "login": "ad@ad.com",
    "userRoles": [
        {
            "idUserRole": 8,
            "userAuthority": "FULL"
        }
    ]
}


# Примеры ошибок:
Все ошибки возвращаются в JSON‑формате вида:
{
  "codeId": -6472092349490575199,
  "message": "...",
  "error": "Unauthorized",
  "status": 401,
  "details": "..."
}

1. 400 Bad Request
некорректный JSON, неверный тип параметра, сломанное тело запроса.
POST http://localhost:8080/api/books
Тело зароса:
{
  "bookTitle": "Новая книга",
  "datePublic": "2024-01-01",
  "price": 100,
  "star": 4,
  "descriptBook": "Интересная книга",
  "genreId": 1

Ответ: 
{
    "error": "Bad Request",
    "httpCode": 400,
    "details": "Повреждённый JSON в теле запроса",
    "message": "Некорректный формат JSON",
    "codeID": -3762406160841881419
}

2. 401 Unauthorized
пользователь не авторизован, нет/неверный JSESSIONID, сессия истекла.
Ответ 401 Unauthorized:
{
    "codeId": -5441064696854133459,
    "message": "Пользователь не авторизован",
    "error": "Unauthorized",
    "status": 401,
    "details": "Сессия отсутствует или истекла"
}

3. 403 Forbidden
пользователь авторизован, но роль недостаточна.
Ответ запроса:
{
    "codeId": 6587759651945923004,
    "message": "Недостаточно прав доступа",
    "error": "Forbidden",
    "status": 403,
    "details": "У пользователя нет необходимых прав для выполнения этого действия"
}

4. 404 Not Found
сущность по id не найдена или обращение к несуществующему ресурсу.
Запрос: PUT http://localhost:8080/api/books/399
Ответ:
{
    "error": "Not Found",
    "message": "Книга с id 399 не найдена",
    "status": 404,
    "codeId": -6456922333595744756,
    "details": "Сущность с указанным id не найдена"
}

5. 405 Method Not Allowed
вызывается неподдерживаемый HTTP‑метод.
Запрос GET http://localhost:8080/auth/login
Ответ:
{
    "error": "Method Not Allowed",
    "httpCode": 405,
    "details": "Для ресурса вызван неподдерживаемый HTTP-метод: GET",
    "message": "Метод не поддерживается",
    "codeID": 3428907244799675056
}

6. 422 Unprocessable Entity
 JSON корректный, но данные не проходят валидацию.
POST http://localhost:8080/api/books
Тело запроса:
{
  "bookTitle": "",
  "datePublic": "2024-01-01",
  "price": 100,
  "star": 4,
  "descriptBook": "Интересная книга",
  "genreId": 1
}
Ответ:
{
    "error": "Unprocessable Entity",
    "httpCode": 422,
    "details": "{bookTitle=Название не может быть пустым}",
    "message": "Данные не прошли валидацию",
    "codeID": 8386709318936252307
}

7. 500 Internal Server Error
непредвиденная ошибка на сервере, проблемы с БД, необработанные исключения.
пример запроса: DELETE http://localhost:8080/api/admin/users/19
Ответ:
{
    "error": "Internal Server Error",
    "httpCode": 500,
    "details": "Непредвиденная ошибка сервера",
    "message": "Что-то пошло не так, уже исправляем. Обратитесь с номером ошибки: e6b13500-325c-45c1-b9f5-5bc95562e161",
    "codeID": -7820870446222585940
}


# Структура проекта:
main.java.com.example.demo:
  config:
    DataInitializer
    GenreDataLoader
    SpringSecurityConfiguration
  controller:
    AuthorController
    AuthorsBooksController
    DebugController
    GenreController
    RegistrationController
    api:
      AdminUserRestController
      AuthRestController
      BookRestController
      RegistrationRestController
    webs:
      AdminUserController
      AnonController
      AuthController
      AuthorsBooksWebController
      AuthorWebController
      BooksMainController
      MainController
  dto:
    AdminUserUpdateRequest
    BookPatchRequest
    BookRequest
    BookWithAuthorsDto
    LoginRequest
    LoginResponse
    RegistrationRequest
  exceptions:
    BookNotFoundException
    BusinessValidationException
    ErrorResponse
    GlobalExceptionHandler
    LoginAlreadyExistsException
    UsernameAlreadyExistsException
  model:
    Author
    AuthorsBooks
    Book
    Genre
    User
    UserAuthority
    UserRole
  repository:
    AuthorRepository
    AuthorsBooksRepository
    BookRepository
    GenreRepository
    UserRepository
    UserRoleRepository
  service:
    AdminUserService
    AuthorsBooksService
    AuthorsBooksServiceImpl
    AuthorService
    AuthorServiceImpl
    BookService
    BookServiceImpl
    GenreService
    GenreServiceImpl
    UserService
    UserServiceImpl
  DemoApplication
