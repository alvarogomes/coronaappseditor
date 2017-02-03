package br.com.virtualsolucoesti.views;

import javax.swing.JFrame;

import java.awt.Graphics;
import java.awt.Window.Type;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class ISplashScreen extends JWindow {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon imgicon=new ImageIcon(ISplashScreen.class.getResource("/br/com/virtualsolucoesti/images/splash.jpg"));
	
	public ISplashScreen() {
		setSize(500,500);
	}

	public void paint(Graphics g)
	{
		g.drawImage(imgicon.getImage(),0,0,this);
	}
}
