package br.com.virtualsolucoesti.componentes;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import br.com.virtualsolucoesti.resize.JResizer;
import br.com.virtualsolucoesti.util.AcaoPopupMenu;
import br.com.virtualsolucoesti.util.ChooseScreen;

public class XLabel extends JResizer  implements IComponente{

	private String nome;
	private String valor;
	private JLabel j;
	private Color cor;
	
	
	public Color getCor() {
		return cor;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}

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
		didResized();
	}
	
	private JPopupMenu popup = new JPopupMenu();
	
	public XLabel(String nome) {
		super();
		j = new JLabel(nome);
		this.valor = nome;
		this.nome = nome.toLowerCase();
		
		// Verificando itens menus...
				popup.add(new AcaoPopupMenu("Remove Label") {
					@Override
					public void actionPerformed(ActionEvent e) {

						getPrincipalView().removerComponenteSelecionado();
					}
				});
				
				popup.add(new AcaoPopupMenu("Go To") {
					@Override
					public void actionPerformed(ActionEvent e) {
						getPrincipalView().goTo();
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
	       addComponent(j);
	}
}
