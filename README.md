
````markdown
# 🗨️ ChatRoom - Spring Boot Backend

A secure and role-based backend chatroom application built using **Spring Boot**. This system ensures that only registered users can chat, manage users, and access messages based on their roles.

---

## 📌 Features

- ✅ **User Authentication & Authorization**
  - Only users stored in the database can access chat functionalities.
- 🧑‍💼 **Admin Role**
  - Can view all chat messages.
  - Has access to change roles of other users.
- 👥 **Member Management**
  - Users can add new members (if they have permission).
- 🔐 **Role-based Access Control**
  - Fine-grained access to resources based on user roles.
- 📡 **RESTful API Endpoints**
  - Clean, structured endpoints for easy integration with any frontend or client.

---

## 🔗 Repository

GitHub: [https://github.com/MilindSaini/ChatRoom](https://github.com/MilindSaini/ChatRoom)

---

## ⚙️ Technologies Used

- Java 17+
- Spring Boot
- Spring Security
- MongoDB

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven
- MongoDB

### Clone the Repository

```bash
git clone https://github.com/MilindSaini/ChatRoom.git
cd ChatRoom
````

### Setup the Database

Configure your database credentials in `application.properties`:

```properties
server.port=8080
# server.servlet.context-path=/api
spring.data.mongodb.uri=<your-uri>
spring.data.mongodb.database=chatroom
spring.data.mongodb.auto-index-creation=true
spring.main.allow-circular-references=true
spring.data.mongodb.ssl.enabled=true
spring.data.mongodb.ssl.invalid-hostname-allowed=true
logging.level.org.springframework.security=DEBUG
```
### Run the Application

```bash
mvn spring-boot:run
```

---

## 📬 API Endpoints

Here all endpoints include:

## 📬 API Endpoints

| #  | Method | Endpoint                                | Description                       | Body (if applicable)                        |
|----|--------|------------------------------------------|-----------------------------------|---------------------------------------------|
| **AuthController** |
| 1  | POST   | `/api/auth/register`                     | Register a new user               | `{ "name": "John Doe", "email": "...", "password": "..." }` |
| 2  | POST   | `/api/auth/mainAdmin`                    | Register the main admin           | `{ "name": "Admin User", "email": "...", "password": "..." }` |
| 3  | GET    | `/api/auth/current`                      | Get the currently logged-in user  | -                                           |
| 4  | GET    | `/api/auth/users`                        | Get all registered users          | -                                           |
| 5  | GET    | `/api/auth/users/{id}`                   | Get user by ID                    | -                                           |
| **RoomController** |
| 6  | POST   | `/api/rooms`                             | Create a new room                 | `{ "name": "General", "description": "..." }` |
| 7  | GET    | `/api/rooms`                             | Get rooms for the current user    | -                                           |
| 8  | GET    | `/api/rooms/{id}`                        | Get room by ID                    | -                                           |
| 9  | POST   | `/api/rooms/{roomId}/members`            | Add a member to a room            | `{ "userId": "user123" }`                   |
| 10 | PUT    | `/api/rooms/{roomId}/admins`             | Promote a member to room admin    | `{ "userId": "user123" }`                   |
| **MessageController** |
| 11 | POST   | `/api/messages`                          | Send a message in a room          | `{ "roomId": "room123", "content": "..." }` |
| 12 | GET    | `/api/messages/{roomId}`                 | Get all messages for a room       | -                                           |


---

## 🛡️ Security

* Role-based access (USER, ADMIN)
* Secure endpoints with Spring Security

---

## 👨‍💻 Author

**Milind Saini**
GitHub: [@MilindSaini](https://github.com/MilindSaini)

---

## 📃 License

This project is open-source and available under the [MIT License](LICENSE).


