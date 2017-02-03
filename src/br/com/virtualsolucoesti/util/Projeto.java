package br.com.virtualsolucoesti.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

public class Projeto implements Serializable {

	private String nomeProjeto;
	private ArrayList<Screen> listaTelas = new ArrayList<Screen>();
	private boolean salvo;
	
	
	public boolean isSalvo() {
		return salvo;
	}
	public void setSalvo(boolean salvo) {
		this.salvo = salvo;
	}
	public String getNomeProjeto() {
		return nomeProjeto;
	}
	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}
	public ArrayList<Screen> getListaTelas() {
		return listaTelas;
	}
	public void setListaTelas(ArrayList<Screen> listaTelas) {
		this.listaTelas = listaTelas;
	}
	
	
}
