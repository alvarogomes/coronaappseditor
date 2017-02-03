package br.com.virtualsolucoesti.componentes;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import br.com.virtualsolucoesti.resize.JResizer;
import br.com.virtualsolucoesti.util.AcaoPopupMenu;

public class XCombobox extends JResizer implements IComponente{
	
	private String nome;
	private String valor;
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	private JPopupMenu popup = new JPopupMenu();
	public XCombobox(String nome) {
		super();
		JComboBox j = new JComboBox();
		
		this.valor = nome;
		this.nome = nome.toLowerCase();
		
		j.setEditable(false);
		j.addItem("");
		
		popup.add(new AcaoPopupMenu("Remove Combobox") {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				getPrincipalView().removerComponenteSelecionado();
			}
		});
		MouseInputListener clickListener = new MouseInputAdapter(){
			   int myX;
			   int myY;
	    	   @Override
			   public void mouseClicked(MouseEvent e) {
	    		   myX = getMyX();
	    		   myY = getMyY();
	    		   if (!isContemBorda()) {
	    			   colocarBorda();
	    		   }
	    	   }
	    	   
	    	   public void mousePressed(MouseEvent me){
	                showPopup(me);
	            }

	            public void mouseReleased(MouseEvent me){
	                showPopup(me);
	            }

	            private void showPopup(MouseEvent me){
	                if(me.isPopupTrigger()){
	                    popup.show(getForm(), myX, myY);
	                }
	            }
	       };
	       
	       Component c[] = j.getComponents();
	       for (int i =0; i < c.length; i++) {
	            // add event listener to all of the child components
	            c[i].addMouseListener(clickListener);
	       }
	    j.addMouseListener(clickListener);
	    j.setSize(200, 40);
		addComponent(j);
	}
}
