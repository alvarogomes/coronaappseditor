package br.com.virtualsolucoesti.componentes;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import br.com.virtualsolucoesti.resize.JResizer;
import br.com.virtualsolucoesti.util.AcaoPopupMenu;

public class XImagem extends JResizer  implements IComponente{
	
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
	public XImagem(String nome, String imagem) {
		super();
		
		JLabel j = new JLabel("");
		final ImageIcon image = new ImageIcon(imagem); 
		j.setIcon(image);
		
		
		
		j.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int w = getWidth();
				int h = getHeight();
				
				if (w > image.getIconWidth()) {
					w = image.getIconWidth();
				}
				if (h > image.getIconHeight()) {
					h = image.getIconHeight();
				}
				
				Image scaledImage =image.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT);
				ImageIcon icon=new ImageIcon(scaledImage);
				((JLabel)getMyComp()).setIcon(icon);
			}
		});
		this.valor = imagem;
		this.nome = nome.toLowerCase();
		
		// Verificando itens menus...
				popup.add(new AcaoPopupMenu("Remove Image") {
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
