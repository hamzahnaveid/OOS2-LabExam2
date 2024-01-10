import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/*
 * Author: Hamzah Naveid (C22428264)
 */

public class ProductManagement extends JFrame implements ActionListener {
	
	ArrayList<Product> productList = new ArrayList<>();
	JComboBox<String> categoryBox;
	String[] categoryList = {"Electronic", "Gardening Deco", "Hardware Access", "Home Deco", "Plumbing"};
	JTextField codeTxt;
	JTextField nameTxt;
	JTextField priceTxt;
	DefaultTableModel model;
	JTable table;
	JTextArea displayArea;
	JLabel avgLbl;
	JTextField searchTxt;
	
	public ProductManagement() {
		super("Catalogue Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create JPanels for JTabbedPane
		JPanel productPane = new JPanel();
		productPane.setLayout(new BorderLayout());
		JPanel searchPane = new JPanel();
		searchPane.setLayout(new BorderLayout());
		
		//Create JTabbedPane
		JTabbedPane tab = new JTabbedPane();
		
		//Create JPanel for one part of productPane panel
		JPanel productDetails = new JPanel();
		
		//Create components
		JLabel code = new JLabel("Product Code:");
		codeTxt = new JTextField(20);
		JLabel name = new JLabel("Name:");
		nameTxt = new JTextField(20);
		JLabel category = new JLabel("Category");
		categoryBox = new JComboBox(categoryList);
		JLabel price = new JLabel("Price:");
		priceTxt = new JTextField(20);
		JButton addProduct = new JButton("Add Product");
		addProduct.addActionListener(this);
		
		//Add components
		productDetails.add(code);
		productDetails.add(codeTxt);
		productDetails.add(name);
		productDetails.add(nameTxt);
		productDetails.add(category);
		productDetails.add(categoryBox);
		productDetails.add(price);
		productDetails.add(priceTxt);
		productDetails.add(addProduct);
		
		productPane.add(productDetails, BorderLayout.NORTH);
		
		//Create JPanel for the other part of productPane panel
		JPanel tableView = new JPanel();
		
		//Create JTable
		String[] columnsname = {"Code", "Name", "Category", "Price"};
		model = new DefaultTableModel(columnsname, 0);
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrl = new JScrollPane(table);
		
		productPane.add(scrl, BorderLayout.CENTER);
		
		//Add tab to the JTabbedPane
		tab.add(productPane, "Product Details");
		
		//Create JPanel for one part of searchPane panel
		JPanel searchDetails = new JPanel();
		
		//Create components
		JLabel searchLbl = new JLabel("Search Text:");
		searchTxt = new JTextField(20);
		JButton searchBtn = new JButton("Search");
		searchBtn.addActionListener(this);
		JButton clearBtn = new JButton("Clear Search");
		clearBtn.addActionListener(this);
		
		searchDetails.add(searchLbl);
		searchDetails.add(searchTxt);
		searchDetails.add(searchBtn);
		searchDetails.add(clearBtn);
		
		searchPane.add(searchDetails, BorderLayout.NORTH);
		
		//Create JPanel for one part of searchPane panel
		JPanel displaySearch = new JPanel();
		
		//Create components
		displayArea = new JTextArea();
		displayArea.setPreferredSize(new Dimension(300, 100));
		displayArea.setEditable(false);
		
		displaySearch.add(displayArea);
		
		searchPane.add(displayArea, BorderLayout.CENTER);
		
		//Create JPanel for one part of searchPane panel
		JPanel avg = new JPanel();
		
		avgLbl = new JLabel("");
		avg.add(avgLbl);
		
		searchPane.add(avg, BorderLayout.SOUTH);
		
		//Adding tab to the JTabbedPane
		tab.add(searchPane,"Search by Category");
		
		add(tab);	
	}

	public static void main(String[] args) {
		ProductManagement win = new ProductManagement();
		win.pack();
		win.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Add Product")) {
			if ((!codeTxt.getText().isEmpty()) && (!nameTxt.getText().isEmpty()) && (!priceTxt.getText().isEmpty())) {
				
				if (!isUniqueCode(codeTxt.getText())) {
					JOptionPane.showMessageDialog(null, "Product code must be unique", "Invalid input", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if (!isNumber(priceTxt.getText())) {
					JOptionPane.showMessageDialog(null, "Price must be a number", "Invalid input", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				Object[] row = {codeTxt.getText(), nameTxt.getText(), categoryBox.getSelectedItem(), priceTxt.getText()};
				model.addRow(row);
				Product product = new Product(codeTxt.getText(), nameTxt.getText(), (String)categoryBox.getSelectedItem(), Double.parseDouble(priceTxt.getText()));
				productList.add(product);
				codeTxt.setText("");
				nameTxt.setText("");
				priceTxt.setText("");
				table.clearSelection();
			}
			
			else {
				JOptionPane.showMessageDialog(null, "Fields must not be empty", "Invalid input", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		else if(e.getActionCommand().equals("Search")) {
			String details = "";
			double sum = 0.0, avg = 0.0;
			int count = 0;
			for(Product p : productList) {
				if(searchTxt.getText().equalsIgnoreCase(p.getCategory())) {
					details += p.toString() + "\n";
					displayArea.setText(details);
					
					count++;
					sum += p.getPrice();	
				}
			}
			avg = sum/count;
			
			avgLbl.setText("Average cost of the products found: €" + avg);
		}
		
		else if(e.getActionCommand().equals("Clear Search")) {
			displayArea.setText("");
			avgLbl.setText("");
		}
	}
	
	public boolean isUniqueCode(String code) {
		boolean unique = true;
		
		for(Product p : productList) {
			if(p.getCode().equalsIgnoreCase(code)) {
				unique = false;
			}
		}
		
		return unique;
	}
	
	public boolean isNumber(String price) {
		boolean number = true;
		
		for(int i = 0; i < price.length(); i++) {
			if((price.charAt(i) < '0' || price.charAt(i) > '9') && price.charAt(i) != '.') {
				number = false;
			}
		}
		
		return number;
	}
}
