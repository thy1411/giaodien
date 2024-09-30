/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my.gui;

/**
 *
 * @author ty
 */

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;


public class JNotepad extends JFrame {

    private JMenuBar menuBar;
    private JMenu mFile, mEdit, mFormat, mView, mHelp, mZoom; 
    private JMenuItem itemNew, itemOpen, itemSave, itemSaveAs, itemExit;
    private JMenuItem itemCopy, itemPaste;
    private JCheckBoxMenuItem itemWrap;
    private JTextArea txtEditor;
    private JToolBar toolBar;
    private JButton btNew, btOpen, btSave;
    private File currentFile;

    public JNotepad(String title) {
        super(title);
        createMenu();
        createGui();
        createToolBar();

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createMenu() {
    menuBar = new JMenuBar();

    // Tạo các menu
    mFile = new JMenu("File");
    mEdit = new JMenu("Edit");
    mFormat = new JMenu("Format");
    mView = new JMenu("View");
    mHelp = new JMenu("Help");
    mZoom = new JMenu("Zoom");

    // Thêm các mục menu vào menu
    mFile.add(itemNew = new JMenuItem("New"));
    mFile.add(itemOpen = new JMenuItem("Open..."));
    mFile.add(itemSave = new JMenuItem("Save"));
    mFile.add(itemSaveAs = new JMenuItem("Save As..."));
    mFile.addSeparator();
    mFile.add(itemExit = new JMenuItem("Exit"));

    mEdit.add(itemCopy = new JMenuItem("Copy"));
    mEdit.add(itemPaste = new JMenuItem("Paste"));

    mView.add(new JMenuItem(" Show Status Bar"));
    mView.add(new JMenuItem(" Show Toolbar"));
    mView.add(new JMenuItem(" Show Line Numbers"));
    mView.add(new JMenuItem(" Show Toolbar"));
    mView.add(new JMenuItem(" Show Inline Hints"));
    
    mHelp.add(new JMenuItem("Help Contents"));
    mHelp.add(new JMenuItem("About"));
    mHelp.add(new JMenuItem("Star Page"));
    
    mZoom.add(new JMenuItem("Zoom In"));
    mZoom.add(new JMenuItem("Zoom Out"));
    
    // Format menu
    itemWrap = new JCheckBoxMenuItem("Word Wrap", true);
    mFormat.add(itemWrap);

    // Thêm action listener cho itemWrap
    itemWrap.addActionListener(e -> toggleWordWrap());

    // Thêm các menu mới vào menuBar
    menuBar.add(mFile);
    menuBar.add(mEdit);
    menuBar.add(mFormat);
    menuBar.add(mView);
    menuBar.add(mHelp);
    menuBar.add(mZoom);

    // Thêm accelerators
    itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
    itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
    itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
    itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));

    // Thêm action listeners
    itemNew.addActionListener(e -> newFile());
    itemOpen.addActionListener(e -> openFile());
    itemSave.addActionListener(e -> saveFile());
    itemSaveAs.addActionListener(e -> saveFileAs());
    itemExit.addActionListener(e -> exitApplication());
    itemCopy.addActionListener(e -> copyText());
    itemPaste.addActionListener(e -> pasteText());

    setJMenuBar(menuBar);
}
    private void toggleWordWrap() {
    txtEditor.setLineWrap(itemWrap.isSelected());
    txtEditor.setWrapStyleWord(itemWrap.isSelected()); // Ngắt dòng theo từ
}

    private void createGui() {
        txtEditor = new JTextArea();
        txtEditor.setFont(new Font("Arial", Font.PLAIN, 16));
        txtEditor.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(txtEditor);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createToolBar() {
        toolBar = new JToolBar();
        toolBar.add(btNew = new JButton("New"));
        toolBar.add(btOpen = new JButton("Open"));
        toolBar.add(btSave = new JButton("Save"));

        btNew.setIcon(new ImageIcon(this.getClass().getResource("/img/new-document.png")));
        btOpen.setIcon(new ImageIcon(this.getClass().getResource("/img/open.png")));
        btSave.setIcon(new ImageIcon(this.getClass().getResource("/img/bookmark.png")));


        btNew.addActionListener(e -> newFile());
        btOpen.addActionListener(e -> openFile());
        btSave.addActionListener(e -> saveFile());

        add(toolBar, BorderLayout.NORTH);
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (FileInputStream fis = new FileInputStream(fileChooser.getSelectedFile())) {
                byte[] data = fis.readAllBytes();
                txtEditor.setText(new String(data));
                currentFile = fileChooser.getSelectedFile();
                setTitle(currentFile.getName() + " - JNotepad");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage());
            }
        }
    }

    private void saveFile() {
        if (currentFile != null) {
            try (FileOutputStream fos = new FileOutputStream(currentFile)) {
                fos.write(txtEditor.getText().getBytes());
                setTitle(currentFile.getName() + " - JNotepad");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
            }
        } else {
            saveFileAs();
        }
    }

    private void saveFileAs() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            saveFile();
        }
    }

    private void exitApplication() {
        System.exit(0);
    }

    private void newFile() {
        txtEditor.setText("");
        currentFile = null;
        setTitle("Untitled - JNotepad");
    }

    private void copyText() {
        StringSelection stringSelection = new StringSelection(txtEditor.getSelectedText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private void pasteText() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
            if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                String text = (String) clipboard.getData(DataFlavor.stringFlavor);
                txtEditor.replaceSelection(text);
            }
        } catch (UnsupportedFlavorException | IOException ex) {
            JOptionPane.showMessageDialog(this, "Error pasting text: " + ex.getMessage());
        }
    }

    public JTextArea getEditorArea() {
        return txtEditor;
    }

}

