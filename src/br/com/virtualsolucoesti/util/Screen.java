package br.com.virtualsolucoesti.util;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.JPanel;

import br.com.virtualsolucoesti.componentes.XComponente;

public class Screen implements Serializable {

	private String document;
	private boolean main;
	private XComponente[] painel;
	
	public Screen() {
		
	}
	public Screen(String document, boolean main, XComponente[] painel) {
		super();
		this.document = document;
		this.main = main;
		this.painel = painel;
	}
	
	
	
	public String getDocument() {
		return document;
	}



	public void setDocument(String document) {
		this.document = document;
	}



	public boolean isMain() {
		return main;
	}


	public void setMain(boolean main) {
		this.main = main;
	}


	public XComponente[] getPainel() {
		return painel;
	}
	public void setPainel(XComponente[] painel) {
		this.painel = painel;
	}
	
}
