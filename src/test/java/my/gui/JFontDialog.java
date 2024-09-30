/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.gui;

/**
 *
 * @author ty
 */


import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class JFontDialog extends JDialog {

    private JList<String> lstFontName, lstStyle, lstSize;
    private JLabel lbPreview;
    private JButton btOk, btCancel;
    
    private Font font;
    private final int[] arrStyle = {Font.PLAIN, Font.ITALIC, Font.BOLD, Font.ITALIC + Font.BOLD};    
    
    private final JNotepad parent;  // Lưu trữ tham chiếu tới cửa sổ JNotepad
    
    public JFontDialog(Frame owner, boolean modal) {
        super(owner, modal);
        parent = (JNotepad) owner;  // Lưu tham chiếu tới cửa sổ cha
        createGUI();
        setFontPreview();
        processEvent();
        setSize(600, 450);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(owner);  // Căn giữa hộp thoại với cửa sổ cha
    }


    private void createGUI() {
        JPanel p = new JPanel();
        p.setLayout(null);

        // Tạo danh sách các tên font từ hệ thống
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        lstFontName = new JList<>(fontNames);
        JScrollPane scrollFontName = new JScrollPane(lstFontName);
        lstFontName.setSelectedIndex(0);
        p.add(scrollFontName);
        scrollFontName.setBounds(5, 50, 200, 200);
        
        // Tạo danh sách style (kiểu chữ)
        String[] styles = {"Plain", "Italic", "Bold", "Italic Bold"};
        lstStyle = new JList<>(styles);
        JScrollPane scrollStyle = new JScrollPane(lstStyle);
        lstStyle.setSelectedIndex(1);
        p.add(scrollStyle);
        scrollStyle.setBounds(210, 50, 200, 200);
        
        // Tạo danh sách size (cỡ chữ)
        String[] sizes = {"8", "9", "10", "11", "12", "14", "16", "22", "24", "32", "48", "72"};
        lstSize = new JList<>(sizes);
        JScrollPane scrollSize = new JScrollPane(lstSize);
        lstSize.setSelectedIndex(9);
        p.add(scrollSize);
        scrollSize.setBounds(420, 50, 100, 200);
        
        // Tạo nhãn để hiển thị bản xem trước
        p.add(lbPreview = new JLabel("AaBbYyZz"));
        lbPreview.setBounds(200, 270, 300, 80);
        
        // Tạo các nút OK và Cancel
        p.add(btOk = new JButton("OK"));
        p.add(btCancel = new JButton("Cancel"));
        btOk.setBounds(250, 350, 100, 50);
        btCancel.setBounds(380, 350, 100, 50);
        
        // Đặt thùng chứa vào cửa sổ
        add(p);                 
    }
    

    private void setFontPreview() {
        String name = lstFontName.getSelectedValue();
        int style = arrStyle[lstStyle.getSelectedIndex()];
        int size = Integer.parseInt(lstSize.getSelectedValue());
        font = new Font(name, style, size);
        lbPreview.setFont(font);  // Cập nhật font xem trước
    }


    private void processEvent() {
        // Xử lý sự kiện thay đổi font name
        lstFontName.addListSelectionListener((ListSelectionEvent e) -> {
            setFontPreview();
        });
        
        // Xử lý sự kiện thay đổi style
        lstStyle.addListSelectionListener((ListSelectionEvent e) -> {
            setFontPreview();
        });
        
        // Xử lý sự kiện thay đổi size
        lstSize.addListSelectionListener((ListSelectionEvent e) -> {
            setFontPreview();
        });

        // Xử lý sự kiện nhấn nút OK
        btOk.addActionListener((ActionEvent e) -> {
            // Áp dụng font cho vùng soạn thảo của JNotepad
            parent.getEditorArea().setFont(font);
            // Đóng hộp thoại
            JFontDialog.this.dispose();
        });

        // Xử lý sự kiện nhấn nút Cancel
        btCancel.addActionListener((ActionEvent e) -> {
            JFontDialog.this.dispose();  // Đóng hộp thoại mà không áp dụng thay đổi
        });
    }
}

