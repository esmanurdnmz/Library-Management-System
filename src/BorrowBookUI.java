// BorrowBookUI.java
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BorrowBookUI extends JFrame {
    private JTextField bookIDField;
    private JTextField Borrower_Name;
    private JButton confirmButton;
    private JPanel jpanel;

    private dataAccessObject dao = new dataAccessObject();
    private mainUI parentUI;

    public BorrowBookUI(mainUI parent, int bookId) {
        this.parentUI = parent;
        setContentPane(jpanel);
        setTitle("Kitap Ödünç Alma");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        bookIDField.setText(String.valueOf(bookId));
        bookIDField.setEditable(false);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String borrowerName = Borrower_Name.getText().trim();

                if (borrowerName.isEmpty()) {
                    JOptionPane.showMessageDialog(BorrowBookUI.this, "Lütfen ödünç alan kişinin adını giriniz!");
                    return;
                }

                boolean success = dao.borrowBook(bookId, borrowerName);

                if (success) {
                    JOptionPane.showMessageDialog(BorrowBookUI.this, "Kitap ödünç verildi!");
                    parentUI.kitaplariListele();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(BorrowBookUI.this, "Kitap bulunamadı veya zaten ödünç alınmış.");
                }
            }
        });
    }
}
