package gui;
//importaciones necesarias para la interfaz gráfica
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import agents.BookSellerAgent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class BookSellerGui extends JFrame {
	private BookSellerAgent myAgent;
	
	private JTextField titleField, priceField;
        private JLabel buyer, book, prec;
	
	public BookSellerGui(BookSellerAgent a) {
		super(a.getLocalName());
		
		myAgent = a;		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(3, 2)); 
                book = new JLabel("Book title: "); 
                book.setFont(new Font("Serif", Font.PLAIN, 15));
                p.add(book);
                titleField = new JTextField(20);
		p.add(titleField);                
                prec = new JLabel("Price: "); 
                prec.setFont(new Font("Serif", Font.PLAIN, 15));
                p.add(prec);
                priceField = new JTextField(20);
                priceField.setSize(234, 34);
		p.add(priceField);
		getContentPane().add(p, BorderLayout.CENTER);
		
		JButton addButton = new JButton("Sell");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String title = titleField.getText().trim();
					String price = priceField.getText().trim();
					
					myAgent.updateCatalogue(title, Integer.parseInt(price));
					titleField.setText("");
					priceField.setText("");
                                        buyer.setText("");
				}catch(Exception e) {
					JOptionPane.showMessageDialog(BookSellerGui.this, "Invalid values","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		p = new JPanel();
                p.setLayout(new GridLayout(2, 1));//2 filas 1 columna
                buyer = new JLabel(""); 
                buyer.setFont(new Font("Serif", Font.PLAIN, 16));
                buyer.setHorizontalAlignment(SwingConstants.CENTER);
                p.add(buyer);
		p.add(addButton);
		getContentPane().add(p, BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
		  public void windowClosing(WindowEvent e) {
		    myAgent.doDelete();
		  }
		});
		
		setResizable(false);
	}
	
        //Para que me regrese en la ventana del vendedor qué comprador lo compró
        public void buyer(String agente)
        {
            buyer.setText(agente);
        }
        
	public void showGui() {
	  pack();
	  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  int centerX = (int)screenSize.getWidth() / 2;
	  int centerY = (int)screenSize.getHeight() / 2;
	  
          setSize(420,140);
	  setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
	  super.setVisible(true);
	}
}