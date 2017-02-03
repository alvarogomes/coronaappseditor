package br.com.virtualsolucoesti.util;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RenameScreen {

	public static String execute(String documento){  
		    
        JTextField texto = new JTextField(10);  
        texto.setText(documento);
        JLabel rotulo = new JLabel("Rename:");  
                  
        JPanel entUsuario = new JPanel();  
        entUsuario.add(rotulo);  
        entUsuario.add(texto);  
        
        JOptionPane.showMessageDialog(null, entUsuario, "Enter the new name:", JOptionPane.PLAIN_MESSAGE);  
        
        return texto.getText();  
    }  
}
