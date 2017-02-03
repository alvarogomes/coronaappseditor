package br.com.virtualsolucoesti.util;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public abstract class AcaoPopupMenu extends AbstractAction{

	public AcaoPopupMenu(String name){
        super(name);
    }
	public abstract void actionPerformed(ActionEvent e);
}
