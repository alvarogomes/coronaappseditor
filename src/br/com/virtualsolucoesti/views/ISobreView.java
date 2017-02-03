package br.com.virtualsolucoesti.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;

import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ISobreView extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblNewLabel;
	private JLabel lblLink1;
	private JLabel lblLink2;

	/**
	 * Launch the application.
	 */
	
	private void openWebpage(String url) {
    	
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
            	URI uri = new URL(url).toURI();
            	desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	/**
	 * Create the dialog.
	 */
	public ISobreView() {
		setResizable(false);
		setBounds(100, 100, 450, 383);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(ISobreView.class.getResource("/br/com/virtualsolucoesti/images/logo-oficial.png")));
		}
		JLabel lblcMy = new JLabel("(C) 2016 All Rights Reserved");
		lblcMy.setHorizontalAlignment(SwingConstants.CENTER);
		lblLink1 = new JLabel("http://www.virtualsolucoesti.com/");
		lblLink1.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		lblLink1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				openWebpage(lblLink1.getText());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getDefaultCursor());
			}
		});
		lblLink1.setForeground(Color.BLUE);
		lblLink1.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lblCoronaAppsBusiness = new JLabel("Corona Apps Business Designer - v.1.0 - BETA");
		lblCoronaAppsBusiness.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblCoronaAppsBusiness.setHorizontalAlignment(SwingConstants.CENTER);
		lblLink2 = new JLabel("http://coronasdk.virtualsolucoesti.com/");
		lblLink2.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		lblLink2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openWebpage(lblLink2.getText());
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getDefaultCursor());
			}
		});
		lblLink2.setHorizontalAlignment(SwingConstants.CENTER);
		lblLink2.setForeground(Color.BLUE);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(138)
							.addComponent(lblNewLabel))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblcMy, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblLink1, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblCoronaAppsBusiness, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblLink2, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(18)
					.addComponent(lblcMy)
					.addGap(18)
					.addComponent(lblLink1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblCoronaAppsBusiness)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblLink2)
					.addContainerGap(32, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Close");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
