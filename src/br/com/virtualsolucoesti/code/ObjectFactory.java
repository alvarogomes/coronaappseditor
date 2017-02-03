package br.com.virtualsolucoesti.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Collection;

import br.com.virtualsolucoesti.componentes.XComponente;
import org.apache.commons.io.FileUtils;

public class ObjectFactory {
	private String caminho;
	
	public  ObjectFactory(String caminho) {
		this.caminho = caminho;
	}
	
	public void execute(XComponente componente, Collection<String> variaveis, Collection<String> metodos, Collection<String> objetos, Collection<String> remover, Collection<String> aparecer) throws Exception{
		String variavel ="";
		String metodo="";
		String objeto="";
		String removerObj = "";
		String aparecerObj = "";
		
		String view = "scrollView";
		
		String w = componente.getWidth();
		if (componente.isFix()) {
			 view = "viewGroup";
		}
		
		if (componente.isScreenWidth()) {
			w = "display.contentWidth";
		}
		String fixX = String.valueOf(Integer.valueOf(componente.getX()) + 90);
		String fixY = String.valueOf(Integer.valueOf(componente.getY()) + 30);
		variavel = "local "+componente.getNome() + ";";
		
		if (componente.getTipo().equals("XLabel")) {
			String irPara = "";
			if (componente.getGoTo() != null) {
				irPara = "storyboard.gotoScene(\"views."+componente.getGoTo()+"\" );";
			}
			objeto = componente.getNome() + " = uix.addLabel("+view+",\""+componente.getValor()+"\","+w+ ","+componente.getHeigth()+ ","+fixX+ ","+fixY+ ",function(event) "+irPara+" end);";
		}
		
		if (componente.getTipo().equals("XTextbox")) {
			//removerObj = "display.remove("+componente.getNome()+");";
			removerObj = componente.getNome()+".alpha = 0";
			aparecerObj= componente.getNome()+".alpha = 1";
			
			String scrollView = "true";
			if (componente.isFix()) {
				scrollView = "false";
			}
			
			String scrollY = componente.getScrollY();
			
			if (scrollY.trim().length() == 0 ) {
				scrollY = "nil";
			}
			
			objeto = componente.getNome() + " = uix.addTextbox("+view+",\""+componente.getValor()+"\","+w+ ","+componente.getHeigth()+ ","+fixX+ ","+fixY+ ","+scrollY+","+scrollView+");";
		}
		
		if (componente.getTipo().equals("XButton")) {
			String irPara = "";
			if (componente.getGoTo() != null) {
				irPara = "storyboard.gotoScene(\"views."+componente.getGoTo()+"\" );";
			}
			
			String w1 =  String.valueOf(Integer.valueOf(componente.getWidth()) + (Integer.valueOf(componente.getWidth()) *0.05));
			
			if (componente.isScreenWidth()) {
				w1 = w;
			}
			
			fixX = String.valueOf(Integer.valueOf(componente.getX())); //+ (Integer.valueOf(componente.getWidth()) *0.05));
			
			objeto = componente.getNome() + " = uix.addButton("+view+",\""+componente.getValor()+"\","+w1+ ","+componente.getHeigth()+ ","+fixX+ ","+fixY+ ",function(event) "+irPara+" end);";
		}
		
		if (componente.getTipo().equals("XImagem")) {
			//Copiar imagem para diretorio...
			File ff = new File(componente.getValor());
			String name = ff.getName();
			
			FileUtils.copyFile(ff, new File(caminho+"/images/"+name));
			/*InputStream is = new FileInputStream(ff);
			salvarArquivo(caminho+"/images/"+name, is);*/
			
			String irPara = "";
			if (componente.getGoTo() != null) {
				irPara = "storyboard.gotoScene(\"views."+componente.getGoTo()+"\" );";
			}
			
			//fixX = String.valueOf((Integer.valueOf(fixX) + (Integer.valueOf(fixX) *0.03)) - (Integer.valueOf(componente.getWidth())*0.3));
			//fixY = String.valueOf(Integer.valueOf(fixY) + (Integer.valueOf(fixY) *0.3) - (Integer.valueOf(componente.getHeigth())*0.3));
			fixX = String.valueOf((Integer.valueOf(componente.getX())+40));
			fixY = componente.getY();
			
			objeto = componente.getNome() + " = uix.addImage("+view+",\"images/"+name+"\","+w+ ","+componente.getHeigth()+ ","+fixX+ ","+fixY+ ",function(event) "+irPara+" end);";
		}
		
		if (componente.getTipo().equals("XAnimation")) {
			
			removerObj = componente.getNome()+".alpha = 0";
			aparecerObj= componente.getNome()+".alpha = 1";
			
			//Copiar imagem para diretorio...
			variavel = variavel + "\n local " + componente.getNome() + "Anim = {}";
			
			String[] arquivos = componente.getValor().split(";");
			int i = 1;
			for (String val: arquivos) {
				if (!val.equals("")) {
					File ff = new File(val);
					String name = ff.getName();
					
					FileUtils.copyFile(ff, new File(caminho+"/images/"+name));
					//InputStream is = new FileInputStream(ff);
					//salvarArquivo(, is);
					
					variavel = variavel + "\n" + componente.getNome() + "Anim["+i+"] = {};" + componente.getNome() + "Anim["+i+"] = \"images/"+name+ "\";";
					i++;
				}
			}
			
			String irPara = "";
			if (componente.getGoTo() != null) {
				irPara = "storyboard.gotoScene(\"views."+componente.getGoTo()+"\" );";
			}
			
			fixX = String.valueOf((Integer.valueOf(fixX) + (Integer.valueOf(fixX) *0.03)) - (Integer.valueOf(componente.getWidth())*0.3));
			fixY = String.valueOf(Integer.valueOf(fixY) + (Integer.valueOf(fixY) *0.3) - (Integer.valueOf(componente.getHeigth())*0.3));
			
			objeto =componente.getNome() +" = lib_movieclip.newAnimPerson("+componente.getNome() + "Anim,"+w+ ","+componente.getHeigth()+"); \n";
			objeto = objeto + "  "+componente.getNome()+".x="+fixX+";"+componente.getNome()+".y="+fixY+";\n" ;
			objeto = objeto + "  " +componente.getNome()+":setSpeed(0.4);\n";
			objeto = objeto + "  " +componente.getNome()+":play{ startFrame=1, endFrame="+i+", loop=0};\n";
			objeto = objeto + "  " +componente.getNome()+":addEventListener( \"tap\", function() "+irPara+ " end );\n";
			objeto = objeto + "  "+view+":insert("+componente.getNome()+");";
			
		}
		
		
		if (componente.getTipo().equals("XCombobox")) {
			variavel = variavel + "\nlocal tbl"+componente.getNome() + " = {};\n";
			variavel = variavel + "tbl"+componente.getNome() + "[1]={}; tbl"+componente.getNome() + "[1].codigo=1;tbl"+componente.getNome() + "[1].descricao=\"Item1\"; \n";

			fixX = String.valueOf(Integer.valueOf(componente.getX())+ ( Integer.valueOf(componente.getWidth()) *0.4 ) );
			//fixY = componente.getY();
			
			objeto = componente.getNome() + " = uix.addCombo("+view+","+w+ ","+componente.getHeigth()+ ","+fixX+ ","+fixY+ ",\"Select a option\",\"Confirm Selection\", tbl"+componente.getNome()+");";
		}
		
		if (componente.getTipo().equals("XScrollList")) {
			metodo = metodo + "\nlocal function "+componente.getNome() + "RowRender( event ) \n"+
			"  local row = event.row; \n local params = event.row.params;\n"+
			"  local rowHeight = row.contentHeight;\n  local rowWidth = row.contentWidth; "+
			"  uix.addLabelTable(row,params.item, 100,40, 60, 30); \n"+
			"end;\n";
			
			metodo = metodo + "\nlocal function "+componente.getNome() + "RowClick( event ) end;\n";
			
			fixX = String.valueOf(Integer.valueOf(fixX) + 25);
			fixY = String.valueOf(Integer.valueOf(fixY) + 95);
			
			objeto = componente.getNome() + " = uix.addTableList("+view+","+ w + ","+componente.getHeigth()+ ","+fixX+ ","+fixY+ ","+componente.getNome() + "RowRender, "+componente.getNome() + "RowClick);\n";
			objeto = objeto + "  " + componente.getNome() + ":insertRow({rowHeight = 60, params = { item=\"First Item\" } });";
		}
		
		if (componente.getTipo().equals("XMap")) {
			objeto = componente.getNome() + " = uix.addMap("+view+","+ w + ","+componente.getHeigth()+ ","+fixX+ ","+fixY+ ",37.331692, -122.030456);";
		}
		
		variaveis.add(variavel);
		metodos.add(metodo);
		objetos.add(objeto);
		remover.add(removerObj);
		aparecer.add(aparecerObj);
	}
	
	private void salvarArquivo(String caminho, InputStream initialStream ) throws Exception {
		byte[] buffer = new byte[initialStream.available()];
	    initialStream.read(buffer);
	 
	    File targetFile = new File(caminho);
	    OutputStream outStream = new FileOutputStream(targetFile);
	    outStream.write(buffer);
	}
	
}
