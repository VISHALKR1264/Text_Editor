import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Desktop;
import javax.swing.JFileChooser;
import java.awt.Toolkit;
import java.awt.Image;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;

 class TextEdit implements ActionListener {
    JFrame f;
    JMenuBar menuBar;
    JMenu File, Edit, Themes, Help;
    JTextArea textarea;
    JScrollPane Scroll;
    JMenuItem DarkTheme, Save, Open, New, Close, Cut, Copy, Paste, SelectAll, FontSize, documentHelpItem;
    JPanel saveFileOptionWindow;
    JLabel fileLabel, dirLabel;
    JTextField filename, dirname;

    TextEdit() {
        f = new JFrame("Vishal Editor");
        Image img = Toolkit.getDefaultToolkit().getImage("C:\\Users\\KIIT\\Desktop\\project\\TextEditor\\Main Logo.png"); // adding image
        f.setIconImage(img);
        menuBar = new JMenuBar();

        // menues
        File = new JMenu("File");
        Edit = new JMenu("Edit");
        Themes = new JMenu("Themes");
        Help = new JMenu("Help");

        // Add menues to menubar
        menuBar.add(File);
        menuBar.add(Edit);
        menuBar.add(Themes);
        menuBar.add(Help);
        f.setJMenuBar(menuBar);

        // Adding subbmenu to Files
        Save = new JMenuItem("Save");
        Open = new JMenuItem("Open");
        New = new JMenuItem("New");
        Close = new JMenuItem("Close");
        File.add(Save);
        File.add(New);
        File.add(Open);
        File.add(Close);

        // Adding submenues to edit

        Cut = new JMenuItem("Cut");
        Copy = new JMenuItem("Copy");
        Paste = new JMenuItem("Paste");
        SelectAll = new JMenuItem("SelectAll");
        FontSize = new JMenuItem("FontSize");
        Edit.add(Cut);
        Edit.add(Copy);
        Edit.add(Paste);
        Edit.add(SelectAll);
        Edit.add(FontSize);

        // Adding Submenues to Themes

        DarkTheme = new JMenuItem("DarkTheme");
        Themes.add(DarkTheme);
        // Help menu
        documentHelpItem = new JMenuItem("documentHelpItem");
        Help.add(documentHelpItem);

        // TextArea
        textarea = new JTextArea(32, 88);
        f.add(textarea);

        // ScrollPanel
        Scroll = new JScrollPane(textarea);
        Scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        Scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        f.add(Scroll);

        // --------------------------------->
        // now actionlistener is added due to which whenever a click on any option some
        // action will be done
        // And giving the reference of current object
        Save.addActionListener(this);
        Open.addActionListener(this);
        New.addActionListener(this);
        Close.addActionListener(this);
        Cut.addActionListener(this);
        Copy.addActionListener(this);
        Paste.addActionListener(this);
        SelectAll.addActionListener(this);
        FontSize.addActionListener(this);
        DarkTheme.addActionListener(this);
        documentHelpItem.addActionListener(this);
        f.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                int confirmExit = JOptionPane.showConfirmDialog(f, "Do you want to exit?", "Confirm Before Saving...",
                        JOptionPane.YES_NO_OPTION);

                if (confirmExit == JOptionPane.YES_OPTION)
                    f.dispose();
                else if (confirmExit == JOptionPane.NO_OPTION)
                    f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {
            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {
            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {
            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {
            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {
            }
        });

        // Keyboard listener

        KeyListener k = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_S && e.isControlDown())
                    saveTheFile(); // Saving the file
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        textarea.addKeyListener(k);
        // Default layout for frame
        f.setSize(1000, 596);
        f.setResizable(false);
        f.setLocation(250, 100);

        f.setLayout(new FlowLayout());
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Copy paste operation
        if (e.getSource() == Cut)
            textarea.cut();
        if (e.getSource() == Copy)
            textarea.copy();
        if (e.getSource() == Paste)
            textarea.paste();
        if (e.getSource() == SelectAll)
            textarea.selectAll();

        // Change the fontSize by value
        if (e.getSource() == FontSize) {
            String sizeOfFont = JOptionPane.showInputDialog("ENTER FONT SIZE", JOptionPane.OK_CANCEL_OPTION);
            if (sizeOfFont != null) {
                int convertSizeOfFont = Integer.parseInt(sizeOfFont);
                Font font = new Font(Font.SANS_SERIF, Font.PLAIN, convertSizeOfFont);
                textarea.setFont(font);
            }
        }

        // Open the file
        if (e.getSource() == Open) {
            JFileChooser choosefile = new JFileChooser();
            int i = choosefile.showOpenDialog(f);
            if (i == JFileChooser.APPROVE_OPTION) {
                File file = choosefile.getSelectedFile(); // select the file
                String filePath = file.getPath(); // get the file path
                String fileNameToShow = file.getName(); // get the file name
                f.setTitle(fileNameToShow);

                try {
                    BufferedReader readFile = new BufferedReader(new FileReader(filePath));
                    String tempString1 = "";
                    String tempString2 = "";

                    while ((tempString1 = readFile.readLine()) != null)
                        tempString2 += tempString1 + "\n";

                    textarea.setText(tempString2);
                    readFile.close();
                } catch (Exception ae) {
                    ae.printStackTrace();
                }
            }
        }

        // Save the file
        if (e.getSource() == Save)
            saveTheFile();

        // New menu operations
        if (e.getSource() == New)
            textarea.setText("");

        // Exit from the window
        if (e.getSource() == Close)
            System.exit(1);

        // themes area
        if (e.getSource() == DarkTheme) {
            textarea.setBackground(Color.DARK_GRAY); // dark Theme
            textarea.setForeground(Color.WHITE);
        }
        // help section

        if (e.getSource() == documentHelpItem) {
            try {
                String url = "http://www.google.com";
                Desktop.getDesktop().browse(URI.create(url));
            } catch (Exception a) {
                a.printStackTrace();
            }
        }
    }

    // Save the file
    public void saveTheFile() {
        saveFileOptionWindow = new JPanel(new GridLayout(2, 1));
        fileLabel = new JLabel("Filename      :- ");
        dirLabel = new JLabel("Save File To :- ");
        filename = new JTextField();
        dirname = new JTextField();

        saveFileOptionWindow.add(fileLabel);
        saveFileOptionWindow.add(filename);
        saveFileOptionWindow.add(dirLabel);
        saveFileOptionWindow.add(dirname);

        JOptionPane.showMessageDialog(f, saveFileOptionWindow); // show the saving dialogue box
        String fileContent = textarea.getText();
        String filePath = dirname.getText();

        try {
            BufferedWriter writeContent = new BufferedWriter(new FileWriter(filePath));
            writeContent.write(fileContent);
            writeContent.close();
            JOptionPane.showMessageDialog(f, "File Successfully saved!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TextEdit();
    }
}
