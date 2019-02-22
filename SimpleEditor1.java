import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import java.io.*;

import java.nio.charset.*;
import java.nio.file.*;

import java.util.function.*;

public class SimpleEditor1 extends JFrame {
    public static final boolean DEBUG = true;
    
    public static final int WIDTH = 800, HEIGHT = 600;

    public JFrame window = this;
    
    public static void main(String[] args) {
	EventQueue.invokeLater(() -> new SimpleEditor1().setVisible(true));
    }

    public SimpleEditor1() {
	super();
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(WIDTH, HEIGHT);
	
	setLayout(new BorderLayout());
	add("North", men);
	add("Center", ed);
    }

    public Editor getCurrentEditor() {
	return currentEditor;
    }

    Editor ed = new Editor();

    class File2Edit {
	public boolean open() {
	    JFileChooser fch = new JFileChooser();
	    if(fch.showOpenDialog(window) != JFileChooser.APPROVE_OPTION) {
		return false;
	    }
	    close();
	    path = fch.getSelectedFile().toPath();
	    return true;
	}

	public boolean isOpen() {
	    return path != null;
	}

	public void close() {
	    path = null;
	}

	public boolean save() {
	    if(!isOpen()) {
		JFileChooser fch = new JFileChooser();
		if(fch.showSaveDialog(window) != JFileChooser.APPROVE_OPTION) {
		    return false;
		}
		path = fch.getSelectedFile().toPath();
	    }

	    return true;
	}

	public void readInto(JTextComponent text) {
	    try {
		text.read(Files.newBufferedReader(path), null);
	    } catch(IOException e) {
		JOptionPane.showMessageDialog(window, e.getMessage());
	    }
	}

	public void writeOut(JTextComponent text) {
	    try {
		text.write(Files.newBufferedWriter(path));
	    } catch(IOException e) {
		JOptionPane.showMessageDialog(window, e.getMessage());
	    }
	}

	public String getName() {
	    return path.toString();
	}
	
	private Path path = null;
    }

    class Editor extends JScrollPane {
	public Editor() {
	    super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    setViewportView(tea);
	    
	    if(DEBUG){
		setBackground(Color.YELLOW);
		tea.setBackground(Color.CYAN);
	    }

	    tea.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void changedUpdate(DocumentEvent e) {
			isModified = true;
		    }

		    @Override
		    public void insertUpdate(DocumentEvent e) {
			isModified = true;
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
			isModified = true;
		    }
		});
	}

	public boolean newFile() {
	    if(!closeFile()) {
		return false;
	    }
	    clear();
	    isModified = false;
	    return true;
	}

	public boolean openFile() {
	    if(!closeFile() || !fed.open()) {
		return false;
	    }
	    clear();
	    fed.readInto(tea);
	    isModified = false;
	    return true;
	}

	public boolean saveFile() {
	    if(!fed.save()) {
		return false;
	    }
	    fed.writeOut(tea);
	    isModified = false;
	    return true;
	}

	public boolean closeFile() {
	    if(isModified) {
		switch(JOptionPane.showConfirmDialog(window, "Do you want to save?")) {
		case JOptionPane.YES_OPTION:
		    saveFile();
		    break;
		case JOptionPane.NO_OPTION:
		    isModified = false;
		    break;
		case JOptionPane.CANCEL_OPTION:
		    return false;
		}
	    }
	    clear();
	    return true;
	}

	public void clear() {
	    tea.setText("");
	}
	
	public void paste() {
	    tea.paste();
	}

	

	final JTextArea tea = new JTextArea();
	final File2Edit fed = new File2Edit();

	private boolean isModified = false;
    }
    
    JMenuBar men = new JMenuBar() {{
	add(new JMenu("File") {{
	    add(new JMenuItem("New") {{
		addActionListener(event -> getCurrentEditor().newFile());
	    }});

	    add(new JMenuItem("Open...") {{
		addActionListener(event -> getCurrentEditor().openFile());
	    }});

	    add(new JMenuItem("Save") {{
		addActionListener(event -> getCurrentEditor().saveFile());
	    }});

	    add(new JMenuItem("Close") {{
		addActionListener(event -> getCurrentEditor().closeFile());
	    }});
	}});

	add(new JMenu("Edit") {{
	    add(new JMenuItem("Paste") {{
		addActionListener(event -> ed.paste());
	    }});
	}});
    }};

    private Editor currentEditor = ed;
}
