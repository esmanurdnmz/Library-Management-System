// mainUI.java
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;

public class mainUI extends JFrame {
    private JPanel panel1;
    private JTable book_inventory;
    private JButton borrowBookButton;
    private JButton returnBookButton;
    private dataAccessObject dao = new dataAccessObject();

    public mainUI() {
        setTitle("Kütüphane Yönetimi");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel1 = new JPanel(new BorderLayout());
        setContentPane(panel1);

        book_inventory = new JTable();
        JScrollPane scrollPane = new JScrollPane(book_inventory);
        panel1.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        returnBookButton = new JButton("İade Et");
        borrowBookButton = new JButton("Ödünç Al");
        buttonPanel.add(returnBookButton);
        buttonPanel.add(borrowBookButton);
        panel1.add(buttonPanel, BorderLayout.SOUTH);

        returnBookButton.addActionListener(e -> {
            int selectedRow = book_inventory.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Lütfen iade etmek istediğiniz kitabı seçin!");
                return;
            }

            int bookId = (int) book_inventory.getValueAt(selectedRow, 0);
            boolean isBorrowed = book_inventory.getValueAt(selectedRow, 3).toString().equals("Ödünç Alındı");

            if (!isBorrowed) {
                JOptionPane.showMessageDialog(this, "Seçilen kitap zaten iade edilmiş.");
                return;
            }

            boolean success = dao.returnBook(bookId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Kitap iade edildi.");
                kitaplariListele();
            } else {
                JOptionPane.showMessageDialog(this, "İade işlemi başarısız.");
            }
        });

        borrowBookButton.addActionListener(e -> {
            int selectedRow = book_inventory.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Lütfen ödünç almak istediğiniz kitabı seçin!");
                return;
            }
            int bookId = (int) book_inventory.getValueAt(selectedRow, 0);
            BorrowBookUI borrowUI = new BorrowBookUI(this,bookId);
            borrowUI.setVisible(true);
        });

        kitaplariListele();  // İlk açılışta kitapları listele
    }

    public void kitaplariListele() {
        var kitaplar = dao.getAllBooks();
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Başlık", "Yazar", "Ödünç Durumu"}, 0);

        for (book b : kitaplar) {
            model.addRow(new Object[]{
                    b.getId(),
                    b.getTitle(),
                    b.getAuthor(),
                    b.isBorrowed() ? "Ödünç Alındı" : "Mevcut"
            });
        }
        book_inventory.setModel(model);
    }
}
