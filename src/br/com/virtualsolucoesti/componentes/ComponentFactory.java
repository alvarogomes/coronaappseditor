package br.com.virtualsolucoesti.componentes;

import java.awt.Component;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ComponentFactory {

	private int contador = 0;
	
	
	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}

	public Component create(String name) {
		contador++;
		if (name.equals("texto")) {
			XTextbox t = new XTextbox("Textbox"+contador);
			return t;
		}
		if (name.equals("label")) {
			return new XLabel("Label" + contador);
		}
		
		if (name.equals("botao")) {
			return new XButton("Button" + contador);
		}
		
		if (name.equals("combo")) {
			return new XCombobox("Combobox" + contador);
		}
		
		if (name.equals("imagem")) {
			JFileChooser abrir = new JFileChooser();
			abrir.setFileFilter(new javax.swing.filechooser.FileFilter(){
	             //Filtro, converte as letras em minúsculas antes de comparar
				@Override 
				public boolean accept(File f){
	                  return  f.isDirectory() || f.getName().toLowerCase().endsWith(".jpg") || f.getName().toLowerCase().endsWith(".png");
	             }
	             //Texto que será exibido para o usuário
	             public String getDescription() {
	                  return "Arquivos de imagem (*.jpg, *.png)";
	             }
	        });
			abrir.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int retorno = abrir.showOpenDialog(null);  
            if (retorno==JFileChooser.APPROVE_OPTION)   {
                  String caminho = abrir.getSelectedFile().getAbsolutePath();
                  return new XImagem("Imagem"+ contador, caminho);
                  
            }
			return null;
		}
		
		if (name.equals("scrolllist")) {
			return new XScrollList("ScrolList" + contador);
		}
		
		if (name.equals("map")) {
			return new XMap("Map" + contador);
		}
		
		if (name.equals("animation")) {
			JFileChooser abrir = new JFileChooser();
			abrir.setFileFilter(new javax.swing.filechooser.FileFilter(){
	             //Filtro, converte as letras em minúsculas antes de comparar
				@Override 
				public boolean accept(File f){
	                  return  f.isDirectory() || f.getName().toLowerCase().endsWith(".jpg") || f.getName().toLowerCase().endsWith(".png");
	             }
	             //Texto que será exibido para o usuário
	             public String getDescription() {
	                  return "Arquivos de imagem (*.jpg, *.png)";
	             }
	        });
			abrir.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			abrir.setMultiSelectionEnabled(true);
			
			int retorno = abrir.showOpenDialog(null);  
            if (retorno==JFileChooser.APPROVE_OPTION)   {
            	  String caminho = "";
            	  File[] files = abrir.getSelectedFiles();
            	  for (File f: files) {
            		  caminho = caminho + f.getAbsolutePath() + ";";  
            	  }
                  
                  return new XAnimation("Animation"+ contador, caminho);
                  
            }
			return null;
		}
		
		return null;
	}
	
	public Component create(XComponente c) {
		
		contador++;
		if (c.getTipo().equals("XTextbox")) {
			XTextbox t = new XTextbox("Textbox"+contador);
			t.setName(c.getNome());
			t.setValor(c.getValor());
			t.setFix(c.isFix());
			t.setScreenWidth(c.isScreenWidth());
			t.setScrollY(c.getScrollY());
			
			t.setBounds(Integer.valueOf( c.getX()), Integer.valueOf(c.getY()), Integer.valueOf(c.getWidth()), Integer.valueOf(c.getHeigth()));
			return t;
		}
		if (c.getTipo().equals("XLabel")) {
			XLabel t = new XLabel("Label" + contador);
			t.setName(c.getNome());
			t.setValor(c.getValor());
			t.setFix(c.isFix());
			t.setGoTo(c.getGoTo());
			t.setScreenWidth(c.isScreenWidth());
			t.setBounds(Integer.valueOf( c.getX()), Integer.valueOf(c.getY()), Integer.valueOf(c.getWidth()), Integer.valueOf(c.getHeigth()));
			return t; 
		}
		
		if (c.getTipo().equals("XButton")) {
			XButton t = new XButton("Botao" + contador);
			t.setName(c.getNome());
			t.setValor(c.getValor());
			t.setFix(c.isFix());
			t.setGoTo(c.getGoTo());
			
			t.setScreenWidth(c.isScreenWidth());
			t.setBounds(Integer.valueOf( c.getX()), Integer.valueOf(c.getY()), Integer.valueOf(c.getWidth()), Integer.valueOf(c.getHeigth()));
			return t;
		}
		
		if (c.getTipo().equals("XCombobox")) {
			XCombobox t = new XCombobox("Combobox" + contador);
			t.setName(c.getNome());
			t.setValor(c.getValor());
			t.setFix(c.isFix());
			
			t.setScreenWidth(c.isScreenWidth());
			
			t.setBounds(Integer.valueOf( c.getX()), Integer.valueOf(c.getY()), Integer.valueOf(c.getWidth()), Integer.valueOf(c.getHeigth()));
			return t; 
		}
		
		if (c.getTipo().equals("XImagem")) {
            //String caminho = abrir.getSelectedFile().getAbsolutePath();
            XImagem t = new XImagem("Imagem"+ contador, c.getValor());
			t.setName(c.getNome());
			t.setValor(c.getValor());
			t.setFix(c.isFix());
			t.setGoTo(c.getGoTo());
			t.setScreenWidth(c.isScreenWidth());
			t.setBounds(Integer.valueOf( c.getX()), Integer.valueOf(c.getY()), Integer.valueOf(c.getWidth()), Integer.valueOf(c.getHeigth()));
			return t;
		}
		
		if (c.getTipo().equals("XAnimation")) {
            //String caminho = abrir.getSelectedFile().getAbsolutePath();
			XAnimation t = new XAnimation("Animation"+ contador, c.getValor());
			t.setName(c.getNome());
			t.setValor(c.getValor());
			t.setFix(c.isFix());
			t.setGoTo(c.getGoTo());
			
			t.setScreenWidth(c.isScreenWidth());
			
			t.setBounds(Integer.valueOf( c.getX()), Integer.valueOf(c.getY()), Integer.valueOf(c.getWidth()), Integer.valueOf(c.getHeigth()));
			return t;
		}
		
		
		if (c.getTipo().equals("XScrollList")) {
			XScrollList t = new XScrollList("ScrolList" + contador);
			t.setName(c.getNome());
			t.setValor(c.getValor());
			t.setFix(c.isFix());
			
			t.setScreenWidth(c.isScreenWidth());
			
			t.setBounds(Integer.valueOf( c.getX()), Integer.valueOf(c.getY()), Integer.valueOf(c.getWidth()), Integer.valueOf(c.getHeigth()));
			return t;
		}
		
		if (c.getTipo().equals("XMap")) {
			XMap t = new XMap("Map" + contador);
			t.setName(c.getNome());
			t.setValor(c.getValor());
			t.setFix(c.isFix());
			
			t.setScreenWidth(c.isScreenWidth());
			
			t.setBounds(Integer.valueOf( c.getX()), Integer.valueOf(c.getY()), Integer.valueOf(c.getWidth()), Integer.valueOf(c.getHeigth()));
			return t;
		}
		
		return null;
	}
	
}
