# Spring Boot Blog Application

This is a blog application built with **Spring Boot** that supports basic CRUD operations for **Posts**, **Categories**, and **Comments**. The application uses **JPA** for persistence and **ModelMapper** for mapping between entity and DTO objects.

## Features

- **Post Management**: Create, retrieve, update, and delete blog posts.
- **Category Management**: Manage categories associated with posts.
- **Comment Management**: Add, update, delete, and view comments on blog posts.
- **Pagination and Sorting**: The application supports pagination and sorting of posts.

## Technologies Used

- **Spring Boot**: Main framework for the application.
- **Spring Data JPA**: For database interaction.
- **ModelMapper**: For mapping between entity and DTO objects.
- **MySQL**: Database to store posts, categories, and comments.
- **Swagger**: For API documentation (optional if integrated).

## Getting Started

Follow the steps below to get the application up and running on your local machine.

### Prerequisites

- Java 17 or higher
- Maven
- MySQL or compatible database
- Postman or any other API testing tool (optional)

### Setup

1. **Clone the Repository**:

    ```bash
    git clone https://github.com/Zakihaidari/springboot-blog-app.git
    cd springboot-blog-app
    ```

2. **Setup MySQL Database**:

    - Create a new database in MySQL called `blog_db`.
    - Update the `application.properties` (or `application.yml`) in the `src/main/resources` folder with your database credentials:

      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/blog_db
      spring.datasource.username=root
      spring.datasource.password=root
      spring.jpa.hibernate.ddl-auto=update
      spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
      ```

3. **Build the Project**:

   Run the following command to clean and build the project using Maven:

    ```bash
    mvn clean install
    ```

4. **Run the Application**:

   Run the Spring Boot application using Maven:

    ```bash
    mvn spring-boot:run
    ```

   This will start the Spring Boot application on `http://localhost:8080`.

5. **Access the API**:

   The API supports various endpoints, such as:

    - `GET /api/posts`: Get all posts with pagination.
    - `POST /api/posts`: Create a new post.
    - `GET /api/posts/{id}`: Get a post by its ID.
    - `PUT /api/posts/{id}`: Update a post by its ID.
    - `DELETE /api/posts/{id}`: Delete a post by its ID.
    - `GET /api/categories`: Get all categories.
    - `POST /api/categories`: Create a new category.
    - `GET /api/categories/{id}`: Get a category by ID.
    - `PUT /api/categories/{id}`: Update a category by ID.
    - `DELETE /api/categories/{id}`: Delete a category by ID.
    - `GET /api/comments`: Get comments for a specific post.
    - `POST /api/comments`: Create a comment on a post.
    - `PUT /api/comments/{id}`: Update a comment by ID.
    - `DELETE /api/comments/{id}`: Delete a comment by ID.

6. **Test API Endpoints**: You can test the API endpoints using **Postman** or any other API testing tool.

### Example Requests

**Create a Post**:

```json
POST /api/posts

{
  "title": "My First Post",
  "description": "This is a post description.",
  "content": "This is the content of the post.",
  "categoryID": 1
}
```

**Get all Posts**:

```json
GET /api/posts?pageNo=0&pageSize=10&sortBy=title&sortDir=asc
```

**Create a Comment**:

```json
POST /api/posts/{postId}/comments

{
  "name": "John Doe",
  "email": "john@example.com",
  "body": "This is a comment."
}
```

## JWT Authentication

This application uses **JWT (JSON Web Token)** for authentication and authorization. The token is generated during the login process and should be included in the **Authorization** header for all subsequent requests.

### JWT Token Creation

To authenticate and obtain a JWT token, use the following **Login** endpoint.

**Login Endpoint**:

- **URL**: `http://localhost:8080/api/auth/login`
- **Method**: `POST`
- **Body**:

  ```json
  {
    "usernameOrEmail": "admin",
    "password": "admin"
  }
  ```

- **Response**:

  ```json
  {
    "accessToken": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbkBnsImV4cCI6MTc0MjY5NDkyOX0.9NMGKv_JBSr8eKb-LZl732nJLSAfyr8uKFA4W0E2nRSjQeaQmfoYzeyVcJl6fuu0",
    "tokenType": "Bearer"
  }
  ```

### Using JWT Token for API Requests

Once you have the JWT token from the login request, include it in the **Authorization** header for all subsequent API requests that require authentication.

**Example: Creating a Post**:

- **URL**: `http://localhost:8080/api/posts`
- **Method**: `POST`
- **Headers**:

  ```http
  Authorization: Bearer your_jwt_token_here
  ```

- **Body**:

  ```json
  {
    "title": "New Post Title",
    "description": "Description of the new post",
    "content": "Content of the new post",
    "categoryID": 1
  }
  ```

- **Response**:

  ```json
  {
    "id": 3,
    "title": "New Post Title",
    "description": "Description of the new post",
    "content": "Content of the new post",
    "category": {
      "id": 1,
      "name": "Technology"
    }
  }
  ```

#### Important:

- In **Postman** or any API client, **select "Bearer Token"** in the **Authorization** tab and paste your JWT token in the field provided.
- The token is included automatically in the **Authorization** header when making the API request.

## Database Schema

The following tables are used in the application:

- **posts**: Stores blog posts.
- **categories**: Stores categories for the posts.
- **comments**: Stores comments for the posts.

### Example Database Structure

```sql
CREATE TABLE categories (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description TEXT
);

CREATE TABLE posts (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  content TEXT,
  category_id BIGINT,
  FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE comments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100),
  body TEXT,
  post_id BIGINT,
  FOREIGN KEY (post_id) REFERENCES posts(id)
);
```

## Testing

- Unit tests are provided for services using **JUnit** and **Mockito**.
- Integration tests can be run using the **Spring Boot Test** feature.

To run tests, use the following command:

```bash
mvn test
```

## Contributing

1. Fork the repository.
2. Create your branch (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m 'Add new feature'`).
4. Push to your branch (`git push origin feature/your-feature`).
5. Create a pull request.