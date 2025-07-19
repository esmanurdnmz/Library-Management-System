// dataAccessObject.java
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dataAccessObject {

    public Connection connect() {
        return MyJdbs.veritabanıBaglan();
    }

    public void addBook(book book) {
        String sql = "INSERT INTO books (title, author, is_borrowed) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setBoolean(3, book.isBorrowed());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    book.setId(generatedKeys.getInt(1));
                }
            }
            System.out.println("Kitap eklendi.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean borrowBook(int bookId, String borrowerName) {
        String updateBook = "UPDATE books SET is_borrowed = true WHERE id = ? AND is_borrowed = false";
        String insertLoan = "INSERT INTO loans (book_id, borrower_name, borrow_date) VALUES (?, ?, ?)";

        try (Connection conn = connect()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtUpdateBook = conn.prepareStatement(updateBook)) {
                stmtUpdateBook.setInt(1, bookId);
                int rows = stmtUpdateBook.executeUpdate();

                if (rows == 0) {
                    conn.rollback();
                    System.out.println("Kitap bulunamadı ya da zaten ödünç alınmış.");
                    return false;
                }
            }

            try (PreparedStatement stmtInsertLoan = conn.prepareStatement(insertLoan)) {
                stmtInsertLoan.setInt(1, bookId);
                stmtInsertLoan.setString(2, borrowerName);
                stmtInsertLoan.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));
                stmtInsertLoan.executeUpdate();
            }

            conn.commit();
            System.out.println("Kitap ödünç verildi.");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<book> getAllBooks() {
        List<book> books = new ArrayList<>();
        String sql = "SELECT id, title, author, is_borrowed FROM books";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                book b = new book(rs.getString("title"), rs.getString("author"));
                b.setId(rs.getInt("id"));
                b.setBorrowed(rs.getBoolean("is_borrowed"));
                books.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public boolean returnBook(int bookId) {
        String updateBook = "UPDATE books SET is_borrowed = false WHERE id = ? AND is_borrowed = true";
        String updateLoan = "UPDATE loans SET return_date = ? WHERE book_id = ? AND return_date IS NULL";

        try (Connection conn = connect()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtUpdateBook = conn.prepareStatement(updateBook)) {
                stmtUpdateBook.setInt(1, bookId);
                int rows = stmtUpdateBook.executeUpdate();

                if (rows == 0) {
                    conn.rollback();
                    System.out.println("Kitap bulunamadı ya da zaten iade edilmiş.");
                    return false;
                }
            }

            try (PreparedStatement stmtUpdateLoan = conn.prepareStatement(updateLoan)) {
                stmtUpdateLoan.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
                stmtUpdateLoan.setInt(2, bookId);
                stmtUpdateLoan.executeUpdate();
            }

            conn.commit();
            System.out.println("Kitap geri verildi.");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
