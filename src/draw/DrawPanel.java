package draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import Date.AreaVo;
import Date.ScrollDate;
import event.DrawTouchListener;
import main.MainFrame;

public class DrawPanel extends JPanel{
	public Image img;
	float width;
	float height;
	
	public ImagePanel mImagePanel;
	
	float scrollWidth = 1;
	float scrollHeight = 1;
	
	public DrawTouchListener mDrawTouchListener;
	
	public MainFrame mMainFrame;
	
	public float frameWidth;
	public float frameHeight;
	
	public float imageWidth;
	public float imageHeight;
	
	public int drawX;
	public int drawY;
	
	double imageOrgWidth;
	double imageOrgHeight;
	
	public float zoom = 1.0f;
	public float autoReSize = 15.0f;
	public double zoomPosition[];
	public ScrollDate zoomScrollDate;
	
	public ArrayList<AreaVo> areaList;
	public int selectArea = -999;
	public int imageCropX = 0;
	public int imageCropY = 0;
	
	public boolean isDraw = false;
	
	double PixelX[][];
	double PixelY[][];
	double PixelZoom = 0.0d;
	
	public void setImage(Image image) {
		this.img = image;
		imageWidth = img.getWidth(null);
		imageHeight = img.getHeight(null);	
	}
	
	public DrawPanel(ImagePanel mImagePanel,MainFrame mMainFrame) {
		this.mImagePanel = mImagePanel;
		this.mMainFrame = mMainFrame;
		mDrawTouchListener = new DrawTouchListener(this);
		areaList = new ArrayList<AreaVo>();
	
		
		setListener();
		invalidate();
		repaint();
		updateUI();

	}
	
	
	public void setListener() {
		addMouseListener(mDrawTouchListener.mDrawMouseListener);
		addMouseMotionListener(mDrawTouchListener.mDrawMouseMotionListener);
		addMouseWheelListener(mDrawTouchListener.mDrawMouseWheelListener);
	}
	
	Image img_buffer = null;
	Graphics buffer = null;
	
	public void paint(Graphics g) {
		
		if(img!=null) {
			isDraw = true;
			setViewSize();
			
			boolean tempIsDraw = false;
			
			if(zoomScrollDate==null) {
				tempIsDraw = true;
				createView();
				drawBackground();
				drawBackLine();
				drawAreaRect();
				drawDragRect();
			}
			scrollReSize();
			if(zoomScrollDate==null) {
				if(tempIsDraw) {
					Point p = mImagePanel.drawPanelScroll.getViewport().getViewPosition();
					g.drawImage(img_buffer, p.x, p.y,this);
				}
			}
			
			isDraw = false;
		}else {
			
			mImagePanel.mDrawPanel.frameHeight = mImagePanel.scrollPanel.getHeight();
			mImagePanel.mDrawPanel.frameWidth = mImagePanel.scrollPanel.getWidth();
			img_buffer = createImage((int)frameWidth,(int)frameHeight);
			buffer = img_buffer.getGraphics();

			buffer.setColor(new Color(50,50,50));
			buffer.fillRect(0, 0, getWidth(),getHeight());
			g.drawImage(img_buffer, 0 ,0,this);
			
		}
	}
	public void createView() {
		img_buffer = createImage((int)frameWidth,(int)frameHeight);
		buffer = img_buffer.getGraphics();
	}
	
	public void setViewSize() {
		
		mImagePanel.mDrawPanel.frameHeight = mImagePanel.scrollPanel.getHeight();
		mImagePanel.mDrawPanel.frameWidth = mImagePanel.scrollPanel.getWidth();
		
		imageWidth = img.getWidth(null) * zoom;
		imageHeight = img.getHeight(null) * zoom;
		
		if(imageWidth < frameWidth) {
			width = frameWidth;
			int temp = (int)Math.abs(width - imageWidth);
			drawX = temp/2;
		}else {
			drawX = 0;
			width = imageWidth;
		}
		
		if(imageHeight < frameHeight) {
			height = frameHeight;
			int temp = (int) Math.abs(height - imageHeight);
			drawY = temp/2;
		}else {
			drawY = 0;
			height = imageHeight;
		}
		
	}
	public boolean isPixel() {
		return ((imageOrgWidth/img.getWidth(null)*100)<autoReSize);
	}
	public void drawBackLine() {
		if(isPixel()) {
			
			ScrollDate date = mDrawTouchListener.mEventDisplayMove.getPoint(mImagePanel.drawPanelScroll);
			Point p = mImagePanel.drawPanelScroll.getViewport().getViewPosition();
			double PixelWidth = frameWidth / ((int)imageOrgWidth);
			double PixelHeight = frameHeight / ((int)imageOrgHeight);
			if(PixelZoom != zoom) {
				PixelX = new double[(int)imageOrgWidth][(int)imageOrgHeight];
				PixelY = new double[(int)imageOrgWidth][(int)imageOrgHeight];
				PixelZoom = zoom;
				for(int i=0;i<PixelX.length;i++) {
					for(int j=0;j<PixelX[i].length;j++) {
						PixelX[i][j] = (((PixelWidth* i)+(imageCropX * zoom)) / imageWidth) * 100;
						PixelY[i][j] = (((PixelHeight* i)+(imageCropY * zoom)) / imageHeight) * 100;
					}
				}
			}
			((Graphics2D)buffer).setStroke(new BasicStroke(1));
			buffer.setColor(new Color(130,130,130,200));
			
			for(double i = 0;i<=frameWidth;i += PixelWidth) {
				buffer.drawLine((int)i,(int)0,(int)i,(int)frameHeight);
			}
			for(double i = 0;i<=frameHeight;i += PixelHeight) {
				buffer.drawLine((int)0,(int)i,(int)frameWidth,(int)i);
			}
		}
	}
	public void drawBackground() {
		buffer.setColor(new Color(50,50,50));
		buffer.fillRect(0, 0, getWidth(),getHeight());
		buffer.setColor(new Color(120,120,120));
		buffer.fillRect(drawX, drawY, (int)imageWidth,(int)imageHeight);
		
		Point p = mImagePanel.drawPanelScroll.getViewport().getViewPosition();
		if(drawX > 0 || drawY > 0) {
			buffer.drawImage(img, drawX - p.x, drawY - p.y, (int)imageWidth,(int)imageHeight,this);
		}else {
			ScrollDate date = mDrawTouchListener.mEventDisplayMove.getPoint(mImagePanel.drawPanelScroll);
			double scrollPersentX = (date.x/date.orgWidth*100d);
			double scrollPersentY = (date.y / date.orgHeight*100d);
			
			imageOrgWidth = ((img.getWidth(null) / 100d) * (frameWidth/imageWidth * 100d));
			imageOrgHeight = ((img.getHeight(null) / 100d) * (frameHeight/imageHeight * 100d));
			
			double imgX_1 = (img.getWidth(null)-imageOrgWidth)/100d;
			double imgY_1 = (img.getHeight(null)-imageOrgHeight)/100d;
			imageCropX = (int)(imgX_1 * scrollPersentX);
			imageCropY = (int)(imgY_1 * scrollPersentY);
			BufferedImage crop = ((BufferedImage)img).getSubimage(imageCropX, imageCropY, (int)imageOrgWidth,(int)imageOrgHeight);
			buffer.drawImage(crop, 0, 0, (int)frameWidth,(int)frameHeight,null);
		}
	}
	public void scrollReSize() {
		if(scrollWidth != width || scrollHeight != height) {
			scrollHeight = height;
			scrollWidth = width;
			
			float width = drawX == 0 ? scrollWidth : 0;
			float height = drawY == 0 ? scrollHeight : 0;
			setPreferredSize(new Dimension((int)width,(int)height));
			updateUI();
		}
		if(drawY>0||drawX>0) {
			setPreferredSize(new Dimension(drawX > 0 ? 0 : (int)width,drawY > 0 ? 0 : (int)height));
		}
		ScrollDate date = mDrawTouchListener.mEventDisplayMove.getPoint(mImagePanel.drawPanelScroll);
		if(zoomScrollDate!=null && (zoomScrollDate.orgWidth != date.orgWidth || zoomScrollDate.orgHeight != date.orgHeight)) {
			double imageOrgWidth = ((img.getWidth(null) / 100d) * (frameWidth/imageWidth * 100d));
			double imageOrgHeight = ((img.getHeight(null) / 100d) * (frameHeight/imageHeight * 100d));
			
			double zoomImageMax_X = (img.getWidth(null) - imageOrgWidth);
			double zoomImageMax_Y = (img.getHeight(null) - imageOrgHeight);
			
			double tempMoveImageX = (zoomPosition[0]) * (img.getWidth(null) / 100d);
			double tempMoveImageY = (zoomPosition[1]) * (img.getHeight(null) / 100d);
			int tempMoveScrollX = (int)(((tempMoveImageX/zoomImageMax_X*100)*(date.orgWidth/100d)));
			int tempMoveScrollY = (int)(((tempMoveImageY/zoomImageMax_Y*100)*(date.orgHeight/100d)));

			double tempMoveMouseX = (zoomPosition[2]) * (imageOrgWidth / 100d);
			double tempMoveMouseY = (zoomPosition[3]) * (imageOrgHeight / 100d);
			int tempMoveScrollMouseX = (int)(((tempMoveMouseX/zoomImageMax_X*100d)*(date.orgWidth/100d)));
			int tempMoveScrollMouseY = (int)(((tempMoveMouseY/zoomImageMax_Y*100d)*(date.orgHeight/100d)));
			
			mImagePanel.drawPanelScroll.getHorizontalScrollBar().setValue(tempMoveScrollX - tempMoveScrollMouseX);
			mImagePanel.drawPanelScroll.getVerticalScrollBar().setValue(tempMoveScrollY - tempMoveScrollMouseY);
			zoomScrollDate = null;
		}else {
			if(zoomScrollDate!=null && !(zoomScrollDate.orgWidth != date.orgWidth)) {
				if(drawY>0 && drawX>0) {
					zoomScrollDate = null;
				}
			}
		}
	}
	public void drawDragRect() {
		if(mDrawTouchListener.isCreatePressed) {
			Point p = mImagePanel.drawPanelScroll.getViewport().getViewPosition();
			
			int width = Math.abs(mDrawTouchListener.mAreaCreate.AreaCreateDownX - mDrawTouchListener.mAreaCreate.moveX);
			int height = Math.abs(mDrawTouchListener.mAreaCreate.AreaCreateDownY - mDrawTouchListener.mAreaCreate.moveY);
			
			int startX = mDrawTouchListener.mAreaCreate.AreaCreateDownX < mDrawTouchListener.mAreaCreate.moveX ?
					mDrawTouchListener.mAreaCreate.AreaCreateDownX : mDrawTouchListener.mAreaCreate.moveX;
			int startY = mDrawTouchListener.mAreaCreate.AreaCreateDownY < mDrawTouchListener.mAreaCreate.moveY ?
					mDrawTouchListener.mAreaCreate.AreaCreateDownY : mDrawTouchListener.mAreaCreate.moveY;
			startX = startX - p.x;
			startY = startY - p.y;
			buffer.setColor(new Color(0,0,255,60));
			buffer.fillRect(startX, startY, width, height);
			buffer.setColor(new Color(0,0,255,150));
			buffer.drawRect(startX, startY, width, height);
		}
	}
	public Font getFont(String name,int style,int height) {
		int size = height;
		Boolean up = null;
		while(true) {
			Font font = new Font(name,style,size);
			int testHeight = getFontMetrics(font).getHeight();
			if(testHeight < height && up != Boolean.FALSE) {
				size++;
				up = Boolean.TRUE;
			}else if(testHeight > height && up != Boolean.TRUE) {
				size--;
				up = Boolean.FALSE;
			}else {
				return font;
			}
		}
	}
	public void drawAreaRect() {
		Point p = mImagePanel.drawPanelScroll.getViewport().getViewPosition();
		if(drawY > 0 || drawX > 0) {
			
			double imageWidthPersent_1 = imageWidth / 100d;
			double imageHeightPersent_1 = imageHeight / 100d;
			for(int i=0;i<areaList.size();i++) {
				AreaVo vo = areaList.get(i);
				if(selectArea==i) {
					buffer.setColor(new Color(255,0,0,100));
				}else {
					buffer.setColor(new Color(30,30,255,80));
				}
				
				int x = drawX + ((int)(imageWidthPersent_1 * vo.getPersentX()) - (p.x));
				int y = drawY + ((int)(imageHeightPersent_1 * vo.getPersentY()) - (p.y));
				int width = (int)(imageWidthPersent_1 * vo.getPersentWidth());
				int height = (int)(imageHeightPersent_1 * vo.getPersentHeight());
				buffer.fillRect(x, y, width, height);
				
				int fontHeight = height/2;
				buffer.setColor(new Color(255,255,255,255));
				buffer.setFont(getFont("Serif",Font.BOLD,fontHeight));
				buffer.drawString(""+i, x, y+(fontHeight/2));
				
				buffer.setColor(new Color(0,0,255,150));
			}
		}else {
			double imageWidth = img.getWidth(null);
			double imageHeight = img.getHeight(null);
			double imageWidth_P1 = imageWidth/100d;
			double imageHeight_P1 = imageHeight/100d;
			
			double imageWidthPersent_1 = this.imageWidth / 100d;
			double imageHeightPersent_1 = this.imageHeight / 100d;
			
			for(int i=0;i<areaList.size();i++) {
				AreaVo vo = areaList.get(i);
				if(selectArea==i) {
					buffer.setColor(new Color(255,0,0,100));
				}else {
					buffer.setColor(new Color(30,30,255,80));
				}
				int x = drawX + (int)(imageWidthPersent_1 * vo.getPersentX()) - (int)(imageCropX * zoom);
				int y = drawY + (int)(imageHeightPersent_1 * vo.getPersentY()) - (int)(imageCropY * zoom);
				
				int width = (int)(imageWidthPersent_1 * vo.getPersentWidth());
				int height = (int)(imageHeightPersent_1 * vo.getPersentHeight());
				buffer.fillRect(x, y, width, height);
				int fontHeight = height/2;
				buffer.setColor(new Color(255,255,255,255));
				buffer.setFont(getFont("Serif",Font.BOLD,fontHeight));
				buffer.drawString(""+i, x, y+(fontHeight/2));
				buffer.setColor(new Color(0,0,255,150));
			
			}
		}
	}
}




























