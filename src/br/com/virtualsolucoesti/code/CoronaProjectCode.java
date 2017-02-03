package br.com.virtualsolucoesti.code;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import br.com.virtualsolucoesti.componentes.XComponente;
import br.com.virtualsolucoesti.util.Projeto;
import br.com.virtualsolucoesti.util.Screen;

public class CoronaProjectCode {

	private Collection<String> variaveis = new ArrayList<String>();
	private Collection<String> metodos = new ArrayList<String>();
	private Collection<String> objetos = new ArrayList<String>();
	private Collection<String> remover = new ArrayList<String>();
	private Collection<String> aparecer = new ArrayList<String>();
	
	public void execute(Projeto p, String diretorio) throws Exception {
		 execute(p,diretorio, true);
	}
	public void execute(Projeto p, String diretorio, boolean mostrar) throws Exception {
		
		//Criando estrutura do projeto...
		
		diretorio = diretorio.replace("\\", "/");
		String diretorioProjeto = diretorio + "/"+p.getNomeProjeto()+"/";
		File f = new File(diretorioProjeto);
		f.mkdir();
		
		f = new File(diretorioProjeto + "/images/");
		f.mkdir();
		
		f = new File(diretorioProjeto + "/scripts/");
		f.mkdir();
		
		f = new File(diretorioProjeto + "/fonts/");
		f.mkdir();
		
		f = new File(diretorioProjeto + "/views/");
		f.mkdir();
		
		f = new File(diretorioProjeto + "/util/");
		f.mkdir();
		
		//Copiando Imagens..
		InputStream in = getInputStream("/br/com/virtualsolucoesti/code/files/Helvetica.ttf");
		salvarArquivo(diretorioProjeto + "/fonts/Helvetica.ttf", in);
		
		//Fonte
		in = getInputStream("/br/com/virtualsolucoesti/code/files/combo.png");
		salvarArquivo(diretorioProjeto + "/images/combo.png", in);
		
		//Copiando scripts
		//config.lua
		in = getClass().getResourceAsStream("/br/com/virtualsolucoesti/code/files/uix.lua");
		String conteudo = getStringFromInputStream(in);
		salvarArquivo(diretorioProjeto + "/util/uix.lua", conteudo);
		
		in = getClass().getResourceAsStream("/br/com/virtualsolucoesti/code/files/movieclip_201.lua");
		conteudo = getStringFromInputStream(in);
		salvarArquivo(diretorioProjeto + "/util/movieclip_201.lua", conteudo);
		
		//config.lua
		in = getClass().getResourceAsStream("/br/com/virtualsolucoesti/code/files/config.lua");
		conteudo = getStringFromInputStream(in);
		salvarArquivo(diretorioProjeto + "/config.lua", conteudo);
		
		//build.settings
		in = getClass().getResourceAsStream("/br/com/virtualsolucoesti/code/files/build.settings");
		conteudo = getStringFromInputStream(in);
		salvarArquivo(diretorioProjeto + "/build.settings", conteudo);
		
		//build.settings
		in = getClass().getResourceAsStream("/br/com/virtualsolucoesti/code/files/main.lua");
		conteudo = getStringFromInputStream(in);
		for (Screen s: p.getListaTelas())  {
			if (s.isMain()) {
				conteudo = conteudo.replace("#viewmain", "views."+ s.getDocument());
			}
		}
		salvarArquivo(diretorioProjeto + "/main.lua", conteudo);
		
		//Criando telas...
		for (Screen s: p.getListaTelas())  {
			variaveis = new ArrayList<String>();
			metodos = new ArrayList<String>();
			objetos = new ArrayList<String>();
			remover = new ArrayList<String>();
			aparecer = new ArrayList<String>();
			
			in = getClass().getResourceAsStream("/br/com/virtualsolucoesti/code/files/model.lua");
			conteudo = getStringFromInputStream(in);
			
			if (s.getPainel() != null) {
				//for (XComponente c: s.getPainel()) {
				for (int contador = s.getPainel().length; contador>=1;contador--) {
					XComponente c = s.getPainel()[contador-1];
					new ObjectFactory(diretorioProjeto).execute(c, variaveis, metodos, objetos, remover,aparecer);
				}
			}
			
			conteudo = conteudo.replace("#variaveis", collectionToString(variaveis));
			conteudo = conteudo.replace("#metodos", collectionToString(metodos));
			conteudo = conteudo.replace("#objetos", collectionToString(objetos));
			conteudo = conteudo.replace("#remover", collectionToString(remover));
			conteudo = conteudo.replace("#aparecer", collectionToString(aparecer));
			
			salvarArquivo(diretorioProjeto + "/views/"+s.getDocument()+".lua", conteudo);
		}
		
		if (mostrar) {
			JOptionPane.showMessageDialog(null,"Project generated with success!");
			
			if (Desktop.isDesktopSupported()) {
			    Desktop.getDesktop().open(new File(diretorioProjeto));
			}
		}
	}
	
	
	private String collectionToString(Collection<String> lista) {
		String retorno = "";
		for (String s: lista) {
			retorno = retorno + s + "\n";
		}
		return retorno;
	}
	private void salvarArquivo(String caminho, InputStream initialStream ) throws Exception {
		FileUtils.copyInputStreamToFile(initialStream, new File(caminho));
		/*
		byte[] buffer = new byte[initialStream.available()];
	    initialStream.read(buffer);
	 
	    File targetFile = new File(caminho);
	    OutputStream outStream = new FileOutputStream(targetFile);
	    outStream.write(buffer);
	    outStream.flush();
	    outStream.close();*/
	}
	private void salvarArquivo(String caminho, String conteudo ) throws Exception {
		
		File file = new File(caminho);
		if (file.exists()) {
			file.delete();
			file.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(conteudo);
		fileWriter.flush();
		fileWriter.close();
	}
	
	private InputStream getInputStream(String caminho) {
		InputStream stream;

		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		stream = classLoader.getResourceAsStream(caminho);

		if (stream == null) {
			stream = br.com.virtualsolucoesti.code.CoronaProjectCode.class.getClassLoader()
					.getResourceAsStream(caminho);
		}

		if (stream == null) {
			stream = br.com.virtualsolucoesti.code.CoronaProjectCode.class
					.getResourceAsStream(caminho);
		}

		
		return stream;
	}
	private String getStringFromInputStream(InputStream is) {

		String inputStreamString = new Scanner(is,"ISO-8859-1").useDelimiter("\\A").next();
		
		return inputStreamString;

	}
}
