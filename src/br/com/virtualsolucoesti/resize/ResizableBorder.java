package br.com.virtualsolucoesti.resize;

import java.awt.event.MouseEvent;

import javax.swing.border.Border;

public interface ResizableBorder extends Border{
    public int getResizeCursor(MouseEvent me);
}