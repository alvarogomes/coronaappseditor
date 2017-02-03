package br.com.virtualsolucoesti.main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.UIManager;

import br.com.virtualsolucoesti.views.IPrincipalView;
import br.com.virtualsolucoesti.views.ISplashScreen;
import de.javasoft.plaf.synthetica.SyntheticaSilverMoonLookAndFeel;

public class Main {

	public static void main(String[] args) {
		
		try {
			
			UIManager.setLookAndFeel(new SyntheticaSilverMoonLookAndFeel());
			
			ISplashScreen splash = new ISplashScreen();
			splash.setLocationRelativeTo(null);
			splash.setVisible(true);
			
			Thread.sleep(5000);
			
			splash.setVisible(false);
			splash = null;
			
			IPrincipalView frame = new IPrincipalView();
			/*Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setBounds(0, 20, screenSize.width, screenSize.height-20);
			frame.getButton_6().setLocation(screenSize.width-80 , 6);
			frame.getButton_3().setLocation(screenSize.width-150 , 6);
			*/
			frame.getBtnStop().setVisible(false);
			frame.novoProjeto();
			frame.setVisible(true);
			frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
