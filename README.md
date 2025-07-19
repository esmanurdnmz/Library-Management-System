# 📚 Library Management System
A simple Java Swing + MySQL desktop application to manage books in a library.
Users can add books, borrow books, and return them — with real-time updates in the database.
✨ Features
-Add new books to the library

-Borrow books (mark as borrowed & save borrower info)

-Return books (mark as available)

-View current inventory with status

-Basic Swing GUI

🛠 Technologies
Java (JDK 21)

Swing for UI

MySQL for database

JDBC (MySQL Connector/J)

## 📦 Project Structure
```
src/
├── book.java                # Book entity class
├── dataAccessObject.java    # Database access logic (add, borrow, return, list)
├── BorrowBookUI.java        # Borrow book window
├── mainUI.java              # Main UI window showing inventory
├── MyJdbs.java              # JDBC connection utility
└── Main.java                # Entry point
```
## ⚙️ Installation & Run
1️⃣ Clone this repository:
```
git clone https://github.com/yourusername/library-system.git
cd library-system
```
2️⃣ Import as a Java project in IntelliJ IDEA or any IDE you like.

3️⃣ Make sure you have:

MySQL running locally (default port 3306)

A database named library
(You can create it manually or run this SQL):
```
CREATE DATABASE library;
```
4️⃣ Add MySQL Connector/J to your project:

If using IntelliJ:
File → Project Structure → Modules → Dependencies → + → JARs or directories
Select: mysql-connector-j-xxx.jar

Or add via Maven dependency.

5️⃣ Edit MyJdbs.java if your MySQL username/password is different:
```
DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");

```
6️⃣ Run Main.java.

## 🗃 Database tables
You’ll need two tables:
```
CREATE TABLE books (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255),
  author VARCHAR(255),
  is_borrowed BOOLEAN DEFAULT false
);

CREATE TABLE loans (
  id INT AUTO_INCREMENT PRIMARY KEY,
  book_id INT,
  borrower_name VARCHAR(255),
  borrow_date DATE,
  return_date DATE,
  FOREIGN KEY (book_id) REFERENCES books(id)
);

```
## ✅ What to commit to GitHub
-All your .java source files in src/

-Your .form UI files (if you created them via GUI designer)

-README.md

-Optional: .gitignore (to ignore /out, /target, .idea/, etc.)

❗ Don’t commit:

-/out or /target folders (compiled files)

-.idea/ folder (IDE configs)

-*.class files


