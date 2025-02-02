## 📦 Features

1. **User Registration & Login**  
   - Secure JWT token generation upon successful login.
   - Password validation and error handling.

2. **BlogPost CRUD Operations**  
   - **Create Post:** Authenticated users can create blog posts.
   - **Read Post:** Retrieve all blog posts or a specific post by ID.
   - **Update Post:** Only the author can update their posts.
   - **Delete Post:** Only the author can delete their posts.

3. **Search Functionality**  
   - Search for posts based on keywords in the title.

4. **Authorization & Authentication**  
   - JWT-secured endpoints.
   - Role-based access control for post modification.

---

## 🔧 Tech Stack

- **Backend:** Java, Spring Boot, Spring Security, JPA
- **Database:** SQLite
- **Authentication:** JWT
- **Testing:** Postman

---

## ⚙️ API Endpoints

### **User Endpoints**

- **Register:** `POST /api/users/register`
- **Login:** `POST /api/users/login`
  - Returns a JWT token for authenticated requests.

### **BlogPost Endpoints** (Protected by JWT)

1. **Create a Blog Post**
   - `POST /api/posts/create?userId={id}`
   - **Body:**
     ```json
     {
       "title": "My First Blog Post",
       "content": "This is the content of my first blog post."
     }
     ```

2. **Retrieve All Posts**
   - `GET /api/posts`

3. **Retrieve a Single Post**
   - `GET /api/posts/{id}`

4. **Update a Post**
   - `PUT /api/posts/{id}`
   - **Body:**
     ```json
     {
       "title": "Updated Blog Post Title",
       "content": "This is the updated content."
     }
     ```

5. **Delete a Post**
   - `DELETE /api/posts/{id}`

6. **Search for Posts**
   - `GET /api/posts/search?keyword={keyword}`

---

## 🔒 Authentication

- Include the JWT token in the `Authorization` header for protected routes:

  ```bash
  Authorization: Bearer <JWT_TOKEN>
  ```

---

## 🗂️ Project Setup

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/LazyCoder02/Lab14-SpringBoot.git
   cd Lab14-SpringBoot
   ```

2. **Build the Project:**
   ```bash
   ./gradlew clean build
   ```

3. **Run the Application:**
   ```bash
   ./gradlew bootRun
   ```

4. **Access the API:**
   ```bash
   http://localhost:8080/api
   ```

5. **SQLite Database:**
   - The database file (`database.db` or similar) will be created automatically in the project directory.
   - To inspect the database, you can use tools like [DB Browser for SQLite](https://sqlitebrowser.org/).
