package br.com.virtualsolucoesti.util;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ChooseScreen {

	public static String execute(Projeto p){  
		JComboBox combobox = new JComboBox();
		
		for (Screen s: p.getListaTelas()) {
			combobox.addItem(s.getDocument());
		}
        
        JLabel rotulo = new JLabel("Screen:");  
                  
        JPanel entUsuario = new JPanel();  
        entUsuario.add(rotulo);  
        entUsuario.add(combobox);  
        
        JOptionPane.showMessageDialog(null, entUsuario, "Go to:", JOptionPane.PLAIN_MESSAGE);  
        
        return (String) combobox.getSelectedItem();  
    }  
}
