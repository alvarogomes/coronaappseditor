package br.com.virtualsolucoesti.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.virtualsolucoesti.code.CoronaProjectCode;
import br.com.virtualsolucoesti.componentes.ComponentFactory;
import br.com.virtualsolucoesti.componentes.IComponente;
import br.com.virtualsolucoesti.componentes.XAnimation;
import br.com.virtualsolucoesti.componentes.XComponente;
import br.com.virtualsolucoesti.componentes.XImagem;
import br.com.virtualsolucoesti.componentes.XTextbox;
import br.com.virtualsolucoesti.resize.JResizer;
import br.com.virtualsolucoesti.util.AcaoPopupMenu;
import br.com.virtualsolucoesti.util.ChooseScreen;
import br.com.virtualsolucoesti.util.Projeto;
import br.com.virtualsolucoesti.util.RenameScreen;
import br.com.virtualsolucoesti.util.Screen;
import de.javasoft.plaf.synthetica.SyntheticaSilverMoonLookAndFeel;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JSeparator;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.Font;
import javax.swing.JTree;
import javax.swing.JWindow;
import javax.swing.JTextField;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class IPrincipalView extends JFrame {
	private TreePath selPath;
	private ComponentFactory componentFactory = new ComponentFactory();
	private Projeto projeto = new Projeto();
	private DefaultTreeModel model;
	private DefaultMutableTreeNode top;
	private IPrincipalView mySelf;
	private Object componenteSelecionado;
	private JPanel contentPane;
	private JButton button_6;
	private JPanel panel_3;
	private JTextField txtNome;
	private JTextField txtValor;
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtWidth;
	private JTextField txtHeigth;
	private JToggleButton tglbtnNewToggleButton;
	private JToggleButton tglbtnLabel;
	private JToggleButton tglbtnImagem;
	private JToggleButton tglbtnCaixaDeTexto;
	private JToggleButton tglbtnImage;
	private JToggleButton tglbtnCombobox;
	private JToggleButton tglbtnScrollList;
	private String componenteAdicionar;
	private JLabel label;
	private JPanel panel_4;
	private JTree tree;
	
	private String arquivoAtivo;
	private JCheckBox checkBox;
	private JButton button_3;
	private JButton btnSimulador;
	private JButton btnStop;
	private Process proc = null;
	private String caminhoProjetoSimulador;
	private JLabel lblTelaAtiva;
	private JCheckBox checkBox_1;
	private JTextField txtScrollY;
	
	public JButton getBtnStop() {
		return btnStop;
	}
	public JButton getButton_6() {
		return button_6;
	}
	public JButton getButton_3() {
		return button_3;
	}
	private boolean existeMainScreen() {
		boolean retorno = false;
		for(Screen s: projeto.getListaTelas()) {
			if (s.isMain()) {
				retorno = true;
				break;
			}
		}
		
		return retorno;
	}
	private void carregarTela(String tela) {
		  gravarAlteracoesLocais();
		  
          for(Screen s: projeto.getListaTelas()) {
  			Screen x = s;
  			if (x.getDocument().equals(tela)) {
  				this.arquivoAtivo = tela;
  				lblTelaAtiva.setText(arquivoAtivo);
  				panel_3.removeAll();
  				//TODO - FIX 3  - OK
  				ComponentFactory cf = new ComponentFactory();
  				if (x.getPainel() == null ) {
  					return;
  				}
  				for(XComponente c: x.getPainel()) {
  					//panel_3.add(c);
  					Component cc = cf.create(c);
  					
  					((JResizer)cc).setJPanel(panel_3);
		            ((JResizer)cc).setPrincipalView(mySelf);
		            panel_3.setSize(288, 96);
		            
		            ((JResizer)cc).didResized();
		            
		            componenteAdicionar = "";
		            desmarcarBotoes(tglbtnNewToggleButton);
		            tglbtnNewToggleButton.setSelected(true);
		            
  					panel_3.add(cc);
  				}
  				cf = null;
  				atualizarPainel();
  				
  				desmarcarComponentes();
  				limparComponenteSelecionado();
  			}
  		}
	}
	private void gravarAlteracoesLocais() {
		ArrayList<Screen> n = new ArrayList<Screen>();
		
		for(Screen s: projeto.getListaTelas()) {
			boolean main = s.isMain();
			
			if (s.getDocument().equals(this.arquivoAtivo)) {
				//TODO - FIX 2  - OK
				int total = this.panel_3.getComponents().length;
				XComponente[] lista = new XComponente[total];
				int cont = 0;
				
				for (Component c: this.panel_3.getComponents()) {
					if (c instanceof JResizer) {
						
						XComponente x = new XComponente();
						x.setTipo(c.getClass().getSimpleName());
						x.setNome(((IComponente)c).getNome());
						x.setValor(((IComponente)c).getValor());
						
						x.setX(String.valueOf(((JResizer)c).getX()));
						x.setY(String.valueOf(((JResizer)c).getY()));
						x.setWidth(String.valueOf(((JResizer)c).getWidth()));
						x.setHeigth(String.valueOf(((JResizer)c).getHeight()));
						x.setFix(((JResizer)c).isFix());
						x.setGoTo(((JResizer)c).getGoTo());
						x.setScreenWidth(((JResizer)c).isScreenWidth());
						x.setScrollY(((JResizer)c).getScrollY());
						
						lista[cont] = x;
						cont++;
					}
				}
				s.setPainel(lista);
			} 
			
			n.add(s);
		}
		projeto.getListaTelas().clear();
		projeto.getListaTelas().addAll(n);
		
	}
	private void carregarTreeView() {
		
		top= new DefaultMutableTreeNode(projeto.getNomeProjeto());
		
		String temp ="";
		for(Screen s: projeto.getListaTelas()) {
			DefaultMutableTreeNode d = new DefaultMutableTreeNode(s.getDocument());
			if (s.isMain())
				temp = s.getDocument();
			top.add(d);
		}
		final String main = temp;
		
		tree.setCellRenderer(new DefaultTreeCellRenderer()
        {
	             public Component getTreeCellRendererComponent(JTree pTree,
	                 Object pValue, boolean pIsSelected, boolean pIsExpanded,
	                 boolean pIsLeaf, int pRow, boolean pHasFocus) {
					    DefaultMutableTreeNode node = (DefaultMutableTreeNode)pValue;
					    super.getTreeCellRendererComponent(pTree, pValue, pIsSelected,
				                     pIsExpanded, pIsLeaf, pRow, pHasFocus);
				                 
					    if (!node.isRoot()) {
					    	if (node.toString().equals(main))
					    		setForeground(Color.blue);
					    }
					    return (this);
					}
	    });
		model = new DefaultTreeModel(top);
        tree.setModel(model);
		tree.expandPath(new TreePath(top));
		
		
		MouseListener ml = new MouseAdapter() {
			private void executar(MouseEvent e) {
				int selRow = tree.getRowForLocation(e.getX(), e.getY());
		        
		        selPath = tree.getPathForLocation(e.getX(), e.getY());
		        if(selRow != -1) {
		        	
		        	//Criando menus...
		        	JPopupMenu popup = new JPopupMenu();
		        	String nomeMenu = "Rename Screen";
		        	if (selPath.getPathCount() == 1) {
		        		nomeMenu = "Rename Project";
		        	}
		        	if (selPath.getPathCount() == 1) {
			        	popup.add(new AcaoPopupMenu("New Screen") {
			    			@Override
			    			public void actionPerformed(ActionEvent e) {
			    				projeto.getListaTelas().add(new Screen("New Document"+(selPath.getPathCount()+1), false, null));
			    				carregarTreeView();
			    			}
			    		});
		        	}
		        	
		        	popup.add(new AcaoPopupMenu(nomeMenu) {
		    			@Override
		    			public void actionPerformed(ActionEvent e) {
		    				gravarAlteracoesLocais();
		    				String valor = RenameScreen.execute(selPath.getLastPathComponent().toString());
		    				
		    				if (valor.equals("")) {
		    					return;
		    				}
		    				
		    				if (selPath.getLastPathComponent().toString().equals(arquivoAtivo)) {
		    					arquivoAtivo = valor;
		    					lblTelaAtiva.setText(arquivoAtivo);
		        			}
		    				
		    				if (selPath.getPathCount() == 1) {
		    					projeto.setNomeProjeto(valor);
		    				} else {
			    				boolean confirma = false;
			    				
			    				for (Screen s: projeto.getListaTelas()) {
			    					if (valor.equals(s.getDocument())) {
			    						confirma = true;
			    					}
			    				}
			    				if (!confirma) {
			    					//Renomeando...
			    					Collection<Screen> n = new ArrayList<Screen>();
			    					for (Screen s: projeto.getListaTelas()) {
				    					if (selPath.getLastPathComponent().toString().equals(s.getDocument())) {
				    						s.setDocument(valor);
				    					}
				    					n.add(s);
				    				}
			    				} else {
			    					JOptionPane.showMessageDialog(null,"Exists Screen with this name!");
			    				}
		    				}
		    				carregarTreeView();
		    			}
		    		});
		        	if (selPath.getPathCount() == 2) {
		        		popup.add(new AcaoPopupMenu("Set Main Screen") {
			    			@Override
			    			public void actionPerformed(ActionEvent e) {
			    				
			    				gravarAlteracoesLocais();
			    				Collection<Screen> n = new ArrayList<Screen>();
			    				for (Screen s: projeto.getListaTelas()) {
			    					s.setMain(false);
			    					if (selPath.getLastPathComponent().toString().equals(s.getDocument())) {
			    						s.setMain(true);
			    					}
			    					n.add(s);
			    				}
			    				projeto.getListaTelas().clear();
			    				projeto.getListaTelas().addAll(n);
			    				carregarTreeView();
			    				
			    			}
			    		});
		        		
		        		popup.add(new AcaoPopupMenu("Delete Screen") {
			    			@Override
			    			public void actionPerformed(ActionEvent e) {
			    				
			    				if (projeto.getListaTelas().size() == 1) {
			    					JOptionPane.showMessageDialog(null,"Have to be one Screen in the project!");
			    					return;
			    				}
			    				for (Screen s: projeto.getListaTelas()) {
			    					if (selPath.getLastPathComponent().toString().equals(s.getDocument())) {
			    						if (arquivoAtivo.equals(s.getDocument())) {
			    							panel_3.removeAll();
			    							desmarcarComponentes();
			    							limparComponenteSelecionado();
			    							atualizarPainel();
			    						}
			    						projeto.getListaTelas().remove(s);
			    						carregarTreeView();
			    						break;
			    					}
			    				}
			    				//Carregar primeiro item...
			    				for (Screen s: projeto.getListaTelas()) {
			    					carregarTela(s.getDocument());
			    					break;
			    				}
			    				
			    			}
			    		});
		        		
		        		popup.add(new AcaoPopupMenu("Clone this Screen") {
			    			@Override
			    			public void actionPerformed(ActionEvent e) {
			    				
			    				gravarAlteracoesLocais();
			    				
			    				Screen c = null;
			    				int contador = 1;
			    				for (Screen s: projeto.getListaTelas()) {
			    					if (selPath.getLastPathComponent().toString().equals(s.getDocument())) {
			    						//TODO - CLONAR TELA
			    						c = s;
			    					}
			    					contador++;
			    				}
			    				
			    				Screen x1 =  new Screen("New Document"+(contador), false, null);
			    				x1.setPainel(c.getPainel());
			    				x1.setMain(false);
	    						projeto.getListaTelas().add(x1);
	    						
	    						carregarTreeView();
			    				
			    			}
			    		});
		        		
		        	}
		        	
		        	if(e.isPopupTrigger()){
	                    popup.show(tree, e.getX(), e.getY());
	                }
		        	
		        	if(!e.isPopupTrigger()){
		        	if(e.getClickCount() == 2) {
		        		if (selPath.getPathCount() == 2 ) {
		        			//Arbri Tela...
		        			System.out.println(selPath.getLastPathComponent().toString() + " - " + selPath.getPathCount());
		        			
		        			carregarTela(selPath.getLastPathComponent().toString());
		        		}
		            }
		        	}
		        }
			}
		    public void mousePressed(MouseEvent e) {
		        this.executar(e);
		    }
		    
		    public void mouseReleased(MouseEvent e) {
		        this.executar(e);
		    }
		};
		
		tree.addMouseListener(ml);
	}
	
	public void novoProjeto() {
		projeto = new Projeto();
		projeto.setNomeProjeto("New Project");
		//TODO - FIX 1 - OK
		//projeto.getListaTelas().add(new Screen("New Document",true, this.panel_3.getComponents()));
		projeto.getListaTelas().add(new Screen("New Document",true, null));
		arquivoAtivo = "New Document";
		lblTelaAtiva.setText(arquivoAtivo);
		
		panel_3.removeAll();
		carregarTreeView();
		atualizarPainel();
	}
	
	public void limparComponenteSelecionado() {
		this.componenteSelecionado = null;
		txtNome.setText("");
		
		txtX.setText("");
		txtY.setText("");
		txtWidth.setText("");
		txtHeigth.setText("");
		
		txtNome.setText("");
		txtValor.setText("");
		
		checkBox.setSelected(false);
		checkBox_1.setSelected(false);
		
		txtScrollY.setText("");
		txtScrollY.setEnabled(false);
		
		panel_4.repaint();
		
	}
	
	private IComponente getObject() {
		return (IComponente) this.componenteSelecionado;
	}
	public void dadosComponenteSelecionado(Object j) {
		this.componenteSelecionado = j;
		
		txtNome.setText(getObject().getNome());
		txtValor.setText(getObject().getValor());
		
		txtX.setText(String.valueOf(((JResizer)j).getX()));
		txtY.setText(String.valueOf(((JResizer)j).getY()));
		txtWidth.setText(String.valueOf(((JResizer)j).getWidth()));
		txtHeigth.setText(String.valueOf(((JResizer)j).getHeight()));
		checkBox.setSelected(((JResizer)j).isFix());
		checkBox_1.setSelected(((JResizer)j).isScreenWidth());
		
		txtScrollY.setText(((JResizer)j).getScrollY());
		txtWidth.setEnabled(!checkBox_1.isSelected());
		
		txtScrollY.setEnabled((j instanceof XTextbox));
		txtValor.setEnabled(!(j instanceof XImagem) & !(j instanceof XAnimation));
		panel_4.repaint();
	}
	
	public void atualizarPainel() {
		panel_3.revalidate();
        label.revalidate();
        panel_3.setSize(288, 96);
	}
	
	public void goTo() {
		String caminho = ChooseScreen.execute(this.projeto);
		if (this.componenteSelecionado != null) {
			if (caminho != null) {
				((JResizer)this.componenteSelecionado).setGoTo(caminho);
				atualizarPainel();
			}
		}
	}
	public void removerComponenteSelecionado() {
		this.panel_3.remove(((Component)this.componenteSelecionado));
		this.panel_3.repaint();
		label.repaint();
		
		panel_3.revalidate();
        label.revalidate();
        panel_3.setSize(288, 96);
        
        limparComponenteSelecionado();
		
	}
	
	public void desmarcarComponentes() {
		for (Component c: panel_3.getComponents()) {
    		if (c instanceof JResizer) {
    			((JResizer)c).removeBorder();
    		}
    	}
	}
	private void desmarcarBotoes(JToggleButton jb) {
		if (!jb.equals(tglbtnNewToggleButton))  this.tglbtnNewToggleButton.setSelected(false);
		if (!jb.equals(tglbtnLabel))  this.tglbtnLabel.setSelected(false);
		if (!jb.equals(tglbtnImagem))  this.tglbtnImagem.setSelected(false);
		if (!jb.equals(tglbtnCaixaDeTexto))  this.tglbtnCaixaDeTexto.setSelected(false);
		if (!jb.equals(tglbtnImage))  this.tglbtnImage.setSelected(false);
		if (!jb.equals(tglbtnCombobox))  this.tglbtnCombobox.setSelected(false);
		if (!jb.equals(tglbtnScrollList))  this.tglbtnScrollList.setSelected(false);
	}
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public IPrincipalView() {
		setTitle("Corona - Business Apps Designer - BETA");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1052, 781);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.textHighlight);
		
		panel_4 = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 334, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 449, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(2)
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
					.addGap(9)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE))
					.addGap(25))
		);
		
		JLabel lblPropriedades = new JLabel("Properties");
		lblPropriedades.setBounds(15, 91, 94, 17);
		lblPropriedades.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(15, 150, 40, 16);
		
		txtNome = new JTextField();
		txtNome.setBounds(94, 144, 134, 28);
		txtNome.setColumns(10);
		
		JLabel lblValue = new JLabel("Value:");
		lblValue.setBounds(15, 184, 40, 16);
		
		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					if (getObject() != null) {
						if (txtValor.getText().trim() != "") {
							getObject().setValor(txtValor.getText());
							atualizarPainel();
						}
					}
				}
			}
		});
		txtValor.setBounds(94, 178, 134, 28);
		txtValor.setColumns(10);
		
		JLabel lblLeft = new JLabel("X:");
		lblLeft.setBounds(15, 218, 40, 16);
		
		txtX = new JTextField();
		txtX.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {  
				String caracteres="0987654321";
				if(!caracteres.contains(evt.getKeyChar()+"")){
					evt.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					if (getObject() != null) {
						if (txtX.getText().trim() != "") {
							((JResizer)componenteSelecionado).setLocation(Integer.valueOf(txtX.getText()),
									Integer.valueOf(txtY.getText()));
							atualizarPainel();
						}
					}
				}
			}
		});
		txtX.setBounds(94, 212, 134, 28);
		txtX.setColumns(10);
		
		JLabel lblY = new JLabel("Y:");
		lblY.setBounds(15, 252, 40, 16);
		
		txtY = new JTextField();
		txtY.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres="0987654321";
				if(!caracteres.contains(e.getKeyChar()+"")){
					e.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					if (getObject() != null) {
						if (txtY.getText().trim() != "") {
							((JResizer)componenteSelecionado).setLocation(Integer.valueOf(txtX.getText()),
									Integer.valueOf(txtY.getText()));
							atualizarPainel();
						}
					}
				}
			}
		});
		txtY.setBounds(94, 246, 134, 28);
		txtY.setColumns(10);
		
		JLabel lblWidth = new JLabel("Width:");
		lblWidth.setBounds(15, 286, 40, 16);
		
		txtWidth = new JTextField();
		txtWidth.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres="0987654321";
				if(!caracteres.contains(e.getKeyChar()+"")){
					e.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					if (getObject() != null) {
						if (txtWidth.getText().trim() != "") {
							((JResizer)componenteSelecionado).setSize(Integer.valueOf(txtWidth.getText()), Integer.valueOf(txtHeigth.getText()));
							atualizarPainel();
						}
					}
				}
			}
		});
		txtWidth.setBounds(94, 280, 134, 28);
		txtWidth.setColumns(10);
		
		txtHeigth = new JTextField();
		txtHeigth.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres="0987654321";
				if(!caracteres.contains(e.getKeyChar()+"")){
					e.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					if (getObject() != null) {
						if (txtHeigth.getText().trim() != "") {
							((JResizer)componenteSelecionado).setSize(Integer.valueOf(txtWidth.getText()), Integer.valueOf(txtHeigth.getText()));
							atualizarPainel();
						}
					}
				}
			}
		});
		txtHeigth.setBounds(94, 314, 134, 28);
		txtHeigth.setColumns(10);
		
		JLabel lblHeigth = new JLabel("Heigth:");
		lblHeigth.setBounds(15, 320, 67, 16);
		panel_4.setLayout(null);
		panel_4.add(lblPropriedades);
		panel_4.add(lblName);
		panel_4.add(txtNome);
		panel_4.add(lblValue);
		panel_4.add(txtValor);
		panel_4.add(lblLeft);
		panel_4.add(txtX);
		panel_4.add(lblY);
		panel_4.add(txtY);
		panel_4.add(lblWidth);
		panel_4.add(txtWidth);
		panel_4.add(txtHeigth);
		panel_4.add(lblHeigth);
		
		JLabel lblImmobile = new JLabel("Fixed:");
		lblImmobile.setBounds(15, 357, 67, 16);
		panel_4.add(lblImmobile);
		
		checkBox = new JCheckBox("");
		checkBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (getObject() != null) {
					((JResizer)componenteSelecionado).setFix(checkBox.isSelected());
					atualizarPainel();
				}
			}
		});
		
		checkBox.setBounds(124, 354, 94, 23);
		panel_4.add(checkBox);
		
		JLabel lblActiveScreen = new JLabel("Active Screen");
		lblActiveScreen.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		lblActiveScreen.setBounds(15, 19, 142, 17);
		panel_4.add(lblActiveScreen);
		
		lblTelaAtiva = new JLabel("");
		lblTelaAtiva.setForeground(Color.BLUE);
		lblTelaAtiva.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblTelaAtiva.setBounds(15, 48, 213, 17);
		panel_4.add(lblTelaAtiva);
		
		JLabel lblAllWidth = new JLabel("Screen Width:");
		lblAllWidth.setBounds(15, 387, 94, 16);
		panel_4.add(lblAllWidth);
		
		checkBox_1 = new JCheckBox("");
		checkBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (getObject() != null) {
					((JResizer)componenteSelecionado).setScreenWidth(checkBox_1.isSelected());
					
					txtWidth.setEnabled( !checkBox_1.isSelected() );
					
					atualizarPainel();
				}
			}
		});
		checkBox_1.setBounds(124, 380, 47, 23);
		panel_4.add(checkBox_1);
		
		JLabel lblScrollY = new JLabel("Scroll Y:");
		lblScrollY.setBounds(15, 421, 67, 16);
		panel_4.add(lblScrollY);
		
		txtScrollY = new JTextField();
		txtScrollY.setEnabled(false);
		txtScrollY.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres="-0987654321";
				if(!caracteres.contains(e.getKeyChar()+"")){
					e.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					if (getObject() != null) {
						if (txtScrollY.getText().trim() != "") {
							((JResizer)componenteSelecionado).setScrollY(txtScrollY.getText());
							atualizarPainel();
						}
					}
				}
			}
		});
		txtScrollY.setColumns(10);
		txtScrollY.setBounds(94, 415, 134, 28);
		panel_4.add(txtScrollY);
		
		JLabel lblNewLabel = new JLabel("Components");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		
		tglbtnNewToggleButton = new JToggleButton("Mouse");
		tglbtnNewToggleButton.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				desmarcarBotoes(tglbtnNewToggleButton);
				componenteAdicionar = "";
			}
		});
		tglbtnNewToggleButton.setHorizontalAlignment(SwingConstants.LEFT);
		tglbtnNewToggleButton.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/mouse.png")));
		
		tglbtnLabel = new JToggleButton("Label");
		tglbtnLabel.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				desmarcarBotoes(tglbtnLabel);
				componenteAdicionar = "label";
			}
		});
		tglbtnLabel.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/text.png")));
		tglbtnLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		tglbtnCaixaDeTexto = new JToggleButton("TextBox");
		tglbtnCaixaDeTexto.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				desmarcarBotoes(tglbtnCaixaDeTexto);
				componenteAdicionar = "texto";
			}
		});
		tglbtnCaixaDeTexto.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/caixa.png")));
		tglbtnCaixaDeTexto.setHorizontalAlignment(SwingConstants.LEFT);
		
		tglbtnImagem = new JToggleButton("Button");
		tglbtnImagem.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				desmarcarBotoes(tglbtnImagem);
				componenteAdicionar = "botao";
			}
		});
		tglbtnImagem.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/botao.png")));
		tglbtnImagem.setHorizontalAlignment(SwingConstants.LEFT);
		
		tglbtnImage = new JToggleButton("Image");
		tglbtnImage.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				desmarcarBotoes(tglbtnImage);
				componenteAdicionar = "imagem";
			}
		});
		tglbtnImage.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/imagem.png")));
		tglbtnImage.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblProjeto = new JLabel("Project");
		lblProjeto.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		
		tree = new JTree();
		
		
		tglbtnCombobox = new JToggleButton("Combobox");
		tglbtnCombobox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				desmarcarBotoes(tglbtnCombobox);
				componenteAdicionar = "combo";
			}
		});
		tglbtnCombobox.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/combo.png")));
		tglbtnCombobox.setHorizontalAlignment(SwingConstants.LEFT);
		
		tglbtnScrollList = new JToggleButton("Table View");
		tglbtnScrollList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				desmarcarBotoes(tglbtnScrollList);
				componenteAdicionar = "scrolllist";
			}
		});
		tglbtnScrollList.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/list.png")));
		tglbtnScrollList.setHorizontalAlignment(SwingConstants.LEFT);
		
		JToggleButton tglbtnMap = new JToggleButton("Map");
		tglbtnMap.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				desmarcarBotoes(tglbtnScrollList);
				componenteAdicionar = "map";
			}
		});
		tglbtnMap.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/map.png")));
		tglbtnMap.setHorizontalAlignment(SwingConstants.LEFT);
		
		JToggleButton tglbtnAnimation = new JToggleButton("Animation");
		tglbtnAnimation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desmarcarBotoes(tglbtnScrollList);
				componenteAdicionar = "animation";
			}
		});
		tglbtnAnimation.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/animation.png")));
		tglbtnAnimation.setHorizontalAlignment(SwingConstants.LEFT);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(tglbtnAnimation, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(tglbtnScrollList, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tglbtnMap, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
						.addComponent(tree, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(tglbtnImage, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tglbtnCombobox, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(tglbtnCaixaDeTexto, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(tglbtnImagem, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(tglbtnNewToggleButton, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tglbtnLabel, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel)
						.addComponent(lblProjeto, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(17)
					.addComponent(lblProjeto, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tree, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblNewLabel)
					.addGap(18)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(tglbtnLabel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addComponent(tglbtnNewToggleButton))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(tglbtnCaixaDeTexto, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addComponent(tglbtnImagem, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(tglbtnImage, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addComponent(tglbtnCombobox, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(tglbtnScrollList, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
						.addComponent(tglbtnMap, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tglbtnAnimation, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addGap(105))
		);
		panel_1.setLayout(gl_panel_1);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/phone.png")));
		
		panel_3 = new JPanel();
		panel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x=e.getX();
			    int y=e.getY();
			    
			    if (componenteAdicionar == null) {
			    	return;
			    }
			    if (componenteAdicionar.trim() != "") {
			    	Component comp = componentFactory.create(componenteAdicionar);
			    	
			    	
			    	if (comp != null) {
			    		desmarcarComponentes();
			    		limparComponenteSelecionado();
			    		
			    		Dimension bounds = comp.getPreferredSize();
			            //JResizer resizer = new JResizer(comp);
			            ((JResizer)comp).setBounds(x, y, bounds.width, bounds.height);
			            
			            panel_3.add(comp);
			            panel_3.setComponentZOrder(comp, 0);
			            panel_3.repaint();
			            label.repaint();
			            comp.invalidate();
			            
			            panel_3.revalidate();
			            label.revalidate();
			            
			            ((JResizer)comp).setJPanel(panel_3);
			            ((JResizer)comp).setPrincipalView(mySelf);
			            panel_3.setSize(288, 96);
			            
			            ((JResizer)comp).didResized();
			            
			            componenteAdicionar = "";
			            desmarcarBotoes(tglbtnNewToggleButton);
			            tglbtnNewToggleButton.setSelected(true);
			    	}
			    	
			    } else {
			    	desmarcarComponentes();
			    	limparComponenteSelecionado();
			    }
			}
			
		});
		panel_3.setBackground(Color.WHITE);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(64)
					.addComponent(label)
					.addContainerGap(68, Short.MAX_VALUE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(73)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(96, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(14)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 636, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(92)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 484, GroupLayout.PREFERRED_SIZE))
		);
		panel_3.setLayout(null);
		panel_2.setLayout(gl_panel_2);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				novoProjeto();
			}
		});
		button.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/novo.png")));
		button.setToolTipText("New Project");
		
		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser abrir = new JFileChooser();
				abrir.setFileFilter(new javax.swing.filechooser.FileFilter(){
		             //Filtro, converte as letras em minúsculas antes de comparar
					@Override 
					public boolean accept(File f){
		                  return f.isDirectory() || f.getName().toLowerCase().endsWith(".cbp") ;
		             }
		             //Texto que será exibido para o usuário
		             public String getDescription() {
		                  return "Corona Business App Project (*.cbp)";
		             }
		        });
				abrir.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				
				int retorno = abrir.showOpenDialog(null); 
	            if (retorno==JFileChooser.APPROVE_OPTION)   {
	            	try  {
	                  String caminho = abrir.getSelectedFile().getAbsolutePath();
	                  
	                  byte[] encoded = Files.readAllBytes(Paths.get(caminho));
	                  
	                  ObjectMapper mapper = new ObjectMapper();
	                  projeto = (Projeto) mapper.readValue(encoded, Projeto.class);
	                  //projeto = (Projeto) objectinputstream.readObject();
	                  
	                  int contador = 0;
	                  for(Screen s: projeto.getListaTelas()) {
	          			Screen x = s;
	          			boolean main = x.isMain();
	          			if (main) {
	          				arquivoAtivo = x.getDocument();
	          				lblTelaAtiva.setText(arquivoAtivo);
	          				panel_3.removeAll();
	          				//TODO - FIX 4 - OK
	          				ComponentFactory cf = new ComponentFactory();
	          				for(XComponente c: x.getPainel()) {
	          					//panel_3.add(c);
	          					Component cc = cf.create(c);
	          					
	          					((JResizer)cc).setJPanel(panel_3);
	    			            ((JResizer)cc).setPrincipalView(mySelf);
	    			            panel_3.setSize(288, 96);
	    			            
	    			            ((JResizer)cc).didResized();
	    			            
	    			            componenteAdicionar = "";
	    			            desmarcarBotoes(tglbtnNewToggleButton);
	    			            tglbtnNewToggleButton.setSelected(true);
	    			            
	          					panel_3.add(cc);
	          				}
	          				contador = contador + x.getPainel().length;
	          				cf = null;
	          				atualizarPainel();
	          				
	          				
	          				carregarTreeView();
	          				desmarcarComponentes();
	          				limparComponenteSelecionado();
	          				
	          			} else {
	          				contador = contador + x.getPainel().length;
	          			}
	          		}
	                  
	                  componentFactory.setContador(contador);
	                  
	            	} catch (Exception ex) {
	            		ex.printStackTrace();
	            	}
	            }
			}
		});
		button_1.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/abrir.png")));
		button_1.setToolTipText("Open Project");
		
		JButton button_2 = new JButton("");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser abrir = new JFileChooser();
				abrir.setFileFilter(new javax.swing.filechooser.FileFilter(){
		             //Filtro, converte as letras em minúsculas antes de comparar
					@Override 
					public boolean accept(File f){
		                  return f.isDirectory() || f.getName().toLowerCase().endsWith(".cbp") ;
		             }
		             //Texto que será exibido para o usuário
		             public String getDescription() {
		                  return "Corona Business App Project (*.cbp)";
		             }
		        });
				abrir.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				
				int retorno = abrir.showSaveDialog(null); 
	            if (retorno==JFileChooser.APPROVE_OPTION)   {
	            	try  {
	                  String caminho = abrir.getSelectedFile().getAbsolutePath();
	                  int a = caminho.lastIndexOf(".");
	                  if (a == -1)
	                	  caminho = caminho + ".cbp";
	                  
	                  gravarAlteracoesLocais();
	                  ObjectMapper mapper = new ObjectMapper();
	                  String jsonInString = mapper.writeValueAsString(projeto);
	                  
	                  File myFoo = new File(caminho);
	                  FileOutputStream fooStream = new FileOutputStream(myFoo, false);
	                  fooStream.write(jsonInString.getBytes());
	                  fooStream.close();
	                  
	            	} catch (Exception ex) {
	            		ex.printStackTrace();
	            	}
	            }
				
			}
		});
		button_2.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/salvar.png")));
		button_2.setToolTipText("Save Project");
		
		JButton button_4 = new JButton("");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					if (!existeMainScreen()) {
						JOptionPane.showMessageDialog(null, "Set a main Screen first!");
						return;
					}
					gravarAlteracoesLocais();
					JFileChooser f = new JFileChooser();
			        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
			        
			        int retorno = f.showSaveDialog(null); 
		            if (retorno==JFileChooser.APPROVE_OPTION)   {
		            	String diretorio = f.getSelectedFile().getAbsolutePath();
		            	
		            	new CoronaProjectCode().execute(projeto, diretorio);
		            }
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		button_4.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/gerarcodigo.png")));
		button_4.setToolTipText("Generate Corona Project");
		
		button_6 = new JButton("");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		button_6.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/logout.png")));
		button_6.setToolTipText("Exit Corona Business App Designer");
		
		button_3 = new JButton("");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ISobreView sobre = new ISobreView();
				sobre.setLocationRelativeTo(null);  
				sobre.setModal(true);
				sobre.setVisible(true);
			}
		});
		button_3.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/help.png")));
		button_3.setToolTipText("About");
		
		btnSimulador = new JButton("");
		btnSimulador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (!existeMainScreen()) {
						JOptionPane.showMessageDialog(null, "Set a main Screen first!");
						return;
					}
					//btnSimulador.setVisible(false);
					SimpleDateFormat sf = new SimpleDateFormat("ddMMyyyyhhmmss");
					String pasta = sf.format(new Date());
					
					String caminho = new File("").getAbsolutePath() + "/test/"+ pasta+"/";
					caminho = caminho.replace("\\", "/");
					
					File dir = new File(caminho);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					caminhoProjetoSimulador = caminho;
					gravarAlteracoesLocais();
					new CoronaProjectCode().execute(projeto, caminho,false);
					
					String[] simulator = {"/Applications/CoronaSDK/Corona Terminal",caminho +"/"+projeto.getNomeProjeto()+"/main.lua"};
					
					String nomeSO = System.getProperty("os.name");
					proc = null;
					String caminhoArquivoBat = "";
					if(nomeSO.contains(new StringBuffer("Windows"))) {
						String prj = caminho +"/"+projeto.getNomeProjeto()+"/main.lua";
						prj = prj.replace("/", "\\");
						
						String arquivoBat = "\"C:\\Program Files (x86)\\Corona Labs\\Corona SDK\\Corona Simulator.exe\" -no-console \"" + prj + "\"";
						
						caminhoArquivoBat = new File("").getAbsolutePath() + "/test/test.bat";
						caminhoArquivoBat = caminhoArquivoBat.replace("\\", "/");
						
						//Savando arquivo bat...
						File file = new File(caminhoArquivoBat);
						if (file.exists()) {
							file.delete();
							file.createNewFile();
						}
						FileWriter fileWriter = new FileWriter(file);
						fileWriter.write(arquivoBat);
						fileWriter.flush();
						fileWriter.close();
						
						String[] simulatorWindows = {"cmd","/c",caminhoArquivoBat};
						
			    		proc = Runtime.getRuntime().exec(simulatorWindows);
			    		
			    	} else {
			    		proc = Runtime.getRuntime().exec(simulator);
			    	}
					//btnStop.setVisible(true);
			    	proc.waitFor();
			    	
			    	dir = new File(caminhoProjetoSimulador);
					if (dir.exists()) {
						FileUtils.forceDelete(dir);
					}
			    	
					dir = new File(caminhoArquivoBat);
					if (dir.exists()) {
						FileUtils.forceDelete(dir);
					}
					
			    	
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getClass().getName()+" : "+ex.getMessage()+
		    				"\n pilha "+ex.getStackTrace()[0].getClassName()+" method "+ex.getStackTrace()[0].getMethodName()+
							" : linha "+ex.getStackTrace()[0].getLineNumber());
				}
				
			}
		});
		btnSimulador.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/simulator.png")));
		btnSimulador.setToolTipText("Test in Corona Simulator");
		
		btnStop = new JButton("");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				//proc.destroy();
				//btnStop.setVisible(false);
				
				//Apaga diretorio criado....
				//File dir = new File(caminhoProjetoSimulador);
				//if (dir.exists()) {
				//	dir.delete();
				//}
				
				//btnSimulador.setVisible(true);
				
				
			}
		});
		btnStop.setIcon(new ImageIcon(IPrincipalView.class.getResource("/br/com/virtualsolucoesti/images/stop.png")));
		btnStop.setToolTipText("Test in Corona Simulator");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(8)
					.addComponent(button)
					.addGap(2)
					.addComponent(button_1)
					.addGap(1)
					.addComponent(button_2)
					.addGap(12)
					.addComponent(button_4)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSimulador, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addGap(592)
					.addComponent(button_3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(button_6)
					.addGap(16))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSimulador, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_6, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_4, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		
		this.mySelf = this;
	}
}
