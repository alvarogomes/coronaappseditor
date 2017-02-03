package br.com.virtualsolucoesti.componentes;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import br.com.virtualsolucoesti.resize.JResizer;
import br.com.virtualsolucoesti.util.AcaoPopupMenu;
import br.com.virtualsolucoesti.views.IPrincipalView;

public class XMap extends JResizer  implements IComponente{
	
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
	public XMap(String nome) {
		super();
		
		JLabel j = new JLabel("");
		ImageIcon image = new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/maps-tela.png")); 
		j.setIcon(image);
		
		this.nome = nome.toLowerCase();
		
		// Verificando itens menus...
				popup.add(new AcaoPopupMenu("Remove Map") {
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
	    
		addComponent(j);
		
	}
	
}
