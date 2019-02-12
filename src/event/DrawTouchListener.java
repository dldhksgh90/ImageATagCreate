package event;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JScrollPane;

import Utility.Utility;
import draw.DrawPanel;

public class DrawTouchListener {
	public DrawMouseMotionListener mDrawMouseMotionListener;
	public DrawMouseListener mDrawMouseListener;
	public DrawMouseWheelListener mDrawMouseWheelListener;
	
	public EventAreaCreate mAreaCreate;
	public EventAreaMove mEventAreaMove;
	public EventDisplayMove mEventDisplayMove;
	public EventAreaReSize mEventAreaReSize;
	public EventAreaDown mEventAreaDown;
	
	public DrawPanel mDrawPanel;
	public boolean isCreatePressed = false;
	boolean isAreaMove = false;
	boolean isAreaReSize = false;
	boolean isDisplayMove = false;
	
	public DrawTouchListener(DrawPanel mDrawPanel) {
		this.mDrawPanel = mDrawPanel;
		mDrawMouseMotionListener = new DrawMouseMotionListener();
		mDrawMouseListener = new DrawMouseListener();
		mDrawMouseWheelListener = new DrawMouseWheelListener();
		mAreaCreate = new EventAreaCreate(mDrawPanel,this);
		mEventAreaMove= new EventAreaMove(mDrawPanel,this);
		mEventDisplayMove = new EventDisplayMove(mDrawPanel,this);
		mEventAreaReSize = new EventAreaReSize(mDrawPanel,this);
		mEventAreaDown = new EventAreaDown(mDrawPanel);
	}
	class DrawMouseMotionListener implements MouseMotionListener{
		@Override
		public void mouseDragged(MouseEvent e) {
			if(mDrawPanel.img == null) {
				return;
			}
			if(isAreaMove) {
				mEventAreaMove.areaMove(e);
			}else if(isCreatePressed) {
				mAreaCreate.move(e);
			}else if(isDisplayMove) {
				mEventDisplayMove.displayMove(e);
			}else if(isAreaReSize) {
				mEventAreaReSize.areaReSize(e);
			}
			mDrawPanel.repaint();
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			if(mDrawPanel.img == null) {
				return;
			}
			mDrawPanel.repaint();
		}
		
	}
	
	class DrawMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent e) {
			if(mDrawPanel.img == null) {
				return;
			}
			if(e.getModifiers()==4) {
				mAreaCreate.createAreaDown(e);
			}else if(e.getModifiers()==16) {
				int saveArea = mDrawPanel.selectArea;
				int view[] = mEventAreaDown.downView(e.getX(),e.getY());
				mEventAreaMove.downAreaX = view[1];
				mEventAreaMove.downAreaY = view[2];
				if(saveArea==view[0] && saveArea >= 0 ) {
					if(view[3] > 80 || view[4] > 80) {
						isAreaReSize = true;
						mEventAreaReSize.areaDown(e,view);
					}else {
						isAreaMove = true;
					}
				}else if(view[0] < 0) {
					isDisplayMove = true; 
					mEventDisplayMove.displayDown(e);
				}
			}
			
			mDrawPanel.repaint();
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if(mDrawPanel.img==null) {
				return;
			}
			
			if(isCreatePressed) {
				mAreaCreate.createAreaUp(e);
			}
			isCreatePressed = false;
			isAreaMove = false;
			isDisplayMove = false; 
			mDrawPanel.repaint();
		}
		
	}
	Thread drawThread;
	class DrawMouseWheelListener implements MouseWheelListener{

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			if(mDrawPanel.img == null) {
				return;
			}
			isCreatePressed = false;
			if(mDrawPanel.zoomScrollDate==null && !mDrawPanel.isDraw) {
				if(e.getWheelRotation()==-1) {
					mDrawPanel.zoom += 0.1f;
				}else {
					mDrawPanel.zoom -= 0.1f;
				}
				
				if(mDrawPanel.zoom < 0.1f) {
					mDrawPanel.zoom = 0.1f;
				}
				
				if(mDrawPanel.drawY>0||mDrawPanel.drawX>0) {
					
				}else {
					Point p = mDrawPanel.mImagePanel.drawPanelScroll.getViewport().getViewPosition();
					JScrollPane drawPanelScroll = mDrawPanel.mImagePanel.drawPanelScroll;
					mDrawPanel.zoomPosition = Utility.getZoomImagePosition(drawPanelScroll,mDrawPanel,e.getX(),e.getY());
					mDrawPanel.zoomScrollDate = mEventDisplayMove.getPoint(drawPanelScroll);
				}
				if(drawThread!=null) {
					drawThread.stop();
				}
				drawThread = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							Thread.sleep(30);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mDrawPanel.repaint();
						drawThread = null;
					}
					
				});
				drawThread.start();
			}
		}
		
	}
	
}















