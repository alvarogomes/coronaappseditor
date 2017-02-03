package br.com.virtualsolucoesti.componentes;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import br.com.virtualsolucoesti.resize.JResizer;
import br.com.virtualsolucoesti.util.AcaoPopupMenu;

public class XTextbox extends JResizer  implements IComponente{

	private String nome;
	private String valor;
	private JTextField j;
	
	
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
		j.setText(valor);
	}
	
	private JPopupMenu popup = new JPopupMenu();
	
	public XTextbox(String name) {
		super();
		j = new JTextField();
		j.setEditable(false);
		this.setScrollY("");
		this.nome = name.toLowerCase();
		
		// Verificando itens menus...
		popup.add(new AcaoPopupMenu("Remove Textbox") {
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

	    j.addMouseListener(clickListener);

	    j.setSize(200, 40);
		addComponent(j);
		
	}
}
