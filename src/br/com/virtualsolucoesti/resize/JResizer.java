package br.com.virtualsolucoesti.resize;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import br.com.virtualsolucoesti.views.IPrincipalView;

public class JResizer extends JComponent implements Serializable{ 
    
	   private JPanel form;
	   private IPrincipalView principalView;
	   private boolean contemBorda;
	   private boolean fix;
	   private Object me;
	   private String goTo;
	   private Component myComp;
	   private boolean screenWidth;
	   private String scrollY;
	   
	   
	   
		public String getScrollY() {
			return scrollY;
		}
	
		public void setScrollY(String scrollY) {
			this.scrollY = scrollY;
		}

		public boolean isScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(boolean screenWidth) {
		this.screenWidth = screenWidth;
	}

		public Component getMyComp() {
		return myComp;
	}

		public String getGoTo() {
			return goTo;
		}

		public void setGoTo(String goTo) {
			this.goTo = goTo;
		}
	   
   public boolean isFix() {
		return fix;
	}

	public void setFix(boolean fix) {
		this.fix = fix;
	}

public Object getMe() {
		return me;
	}

	public void setMe(Object me) {
		this.me = me;
	}

	public JResizer(){ }
	   
	 public JPanel getForm() {
		 return this.form;
	 }
	public void setPrincipalView(IPrincipalView principalView) {
		   this.principalView = principalView;
	}

	public IPrincipalView getPrincipalView() {
		return principalView;
	}

	public void addComponent(Component comp){ 
		   setLayout(new BorderLayout());
		   myComp = comp;
           add(comp); 
           setBorder(new DefaultResizableBorder(6));
       }
	   
	   public JResizer(Component comp){ 
           this(comp, new DefaultResizableBorder(6));
           contemBorda = true;
       } 
    
       public JResizer(Component comp, ResizableBorder border){ 
           setLayout(new BorderLayout()); 
           add(comp); 
           setBorder(border); 
       } 
    
       public void setJPanel(JPanel form) {
    	   this.form = form;
       }
       
       public void removeBorder() {
    	   setBorder(null);
    	   addMouseListener(clickListener);
       }
       
       public  boolean isContemBorda() {
    	   return contemBorda;
       }
       
       public void colocarBorda() {
    	   setBorder(new DefaultResizableBorder(6));
       }
       public void setBorder(Border border){
    	   contemBorda = false;
    	   removeMouseListener(clickListener);
           removeMouseListener(resizeListener); 
           removeMouseMotionListener(resizeListener); 
           if(border instanceof ResizableBorder){ 
        	   if (principalView != null) principalView.desmarcarComponentes();
               addMouseListener(resizeListener); 
               addMouseMotionListener(resizeListener); 
               contemBorda = true;
           } 
           super.setBorder(border); 
           if (principalView != null) principalView.dadosComponenteSelecionado(this);
       } 
    
       public int getMyX() {
    	   didResized();
    	   return this.getX();
       }
       
       public int getMyY() {
    	   didResized();
    	   return this.getY();
       }
       public void didResized(){ 
           if(getParent()!=null){ 
               getParent().repaint(); 
               invalidate(); 
               ((JComponent)getParent()).revalidate(); 
               this.form.setSize(288, 96);
               if (principalView != null) principalView.dadosComponenteSelecionado(this);
           } 
       } 
       MouseInputListener clickListener = new MouseInputAdapter(){
    	   @Override
		   public void mouseClicked(MouseEvent e) {
    		   if (!isContemBorda()) {
    			   colocarBorda();
    		   }
    	   }
       };
       
       MouseInputListener resizeListener = new MouseInputAdapter(){ 
    	   
           public void mouseMoved(MouseEvent me){ 
               ResizableBorder border = (ResizableBorder)getBorder(); 
               setCursor(Cursor.getPredefinedCursor(border.getResizeCursor(me))); 
           } 
    
           public void mouseExited(MouseEvent mouseEvent){ 
               setCursor(Cursor.getDefaultCursor()); 
           } 
    
           private int cursor; 
           private Point startPos = null; 
    
           public void mousePressed(MouseEvent me){ 
               ResizableBorder border = (ResizableBorder)getBorder(); 
               cursor = border.getResizeCursor(me); 
               startPos = me.getPoint(); 
           } 
    
           public void mouseDragged(MouseEvent me){ 
               if(startPos!=null){ 
                   int dx = me.getX()-startPos.x; 
                   int dy = me.getY()-startPos.y; 
                   switch(cursor){ 
                       case Cursor.N_RESIZE_CURSOR: 
                           setBounds(getX(), getY()+dy, getWidth(), getHeight()-dy); 
                           didResized(); 
                           break; 
                       case Cursor.S_RESIZE_CURSOR: 
                           setBounds(getX(), getY(), getWidth(), getHeight()+dy); 
                           startPos = me.getPoint(); 
                           didResized(); 
                           break; 
                       case Cursor.W_RESIZE_CURSOR: 
                          setBounds(getX()+dx, getY(), getWidth()-dx, getHeight()); 
                          didResized(); 
                          break; 
                      case Cursor.E_RESIZE_CURSOR: 
                          setBounds(getX(), getY(), getWidth()+dx, getHeight()); 
                          startPos = me.getPoint(); 
                          didResized(); 
                          break; 
                      case Cursor.NW_RESIZE_CURSOR: 
                       setBounds(getX()+dx, getY()+dy, getWidth()-dx, getHeight()-dy); 
                          didResized(); 
                          break; 
                      case Cursor.NE_RESIZE_CURSOR: 
                          setBounds(getX(), getY()+dy, getWidth()+dx, getHeight()-dy); 
                          startPos = new Point(me.getX(), startPos.y); 
                          didResized(); 
                          break; 
                      case Cursor.SW_RESIZE_CURSOR: 
                          setBounds(getX()+dx, getY(), getWidth()-dx, getHeight()+dy); 
                          startPos = new Point(startPos.x, me.getY()); 
                          didResized(); 
                          break; 
                      case Cursor.SE_RESIZE_CURSOR: 
                          setBounds(getX(), getY(), getWidth()+dx, getHeight()+dy); 
                          startPos = me.getPoint(); 
                          didResized(); 
                          break; 
                      case Cursor.MOVE_CURSOR: 
                          Rectangle bounds = getBounds(); 
                          bounds.translate(dx, dy); 
                          setBounds(bounds); 
                          didResized(); 
                  } 
   
                  // cursor shouldn't change while dragging 
                  setCursor(Cursor.getPredefinedCursor(cursor)); 
              } 
          } 
   
          public void mouseReleased(MouseEvent mouseEvent){ 
              startPos = null; 
          } 
      }; 
  } 