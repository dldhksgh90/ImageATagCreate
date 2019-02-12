package event;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;

import Date.AreaVo;
import Utility.Utility;
import draw.DrawPanel;

public class EventAreaCreate {
	public int AreaCreateDownX;
	public int AreaCreateDownY;
	public int moveX;
	public int moveY;
	
	DrawPanel mDrawPanel;
	DrawTouchListener mDrawTouchListener;
	
	public EventAreaCreate(DrawPanel mDrawPanel,DrawTouchListener mDrawTouchListener) {
		this.mDrawPanel = mDrawPanel;
		this.mDrawTouchListener = mDrawTouchListener;
	}
	
	public void createAreaDown(MouseEvent e) {
		int saveArea = mDrawPanel.selectArea;
		int view[] = mDrawTouchListener.mEventAreaDown.downView(e.getX(), e.getY());
		if(view[0] >= 0) {
			if(saveArea == view[0]) {
				mDrawPanel.areaList.remove(saveArea);
				mDrawPanel.selectArea = -1;
			}
		}else {
			mDrawTouchListener.isCreatePressed = true;
			AreaCreateDownX = e.getX();
			AreaCreateDownY = e.getY();
			moveX = e.getX();
			moveY = e.getY();
		}
	}
	public void createAreaUp(MouseEvent e) {
		JScrollPane drawPanelScroll = mDrawPanel.mImagePanel.drawPanelScroll;
		Point p = mDrawPanel.mImagePanel.drawPanelScroll.getViewport().getViewPosition();
		
		if(mDrawPanel.drawY > 0 || mDrawPanel.drawX > 0) {
			float imageWidth = mDrawPanel.imageWidth;
			float imageHeight = mDrawPanel.imageHeight;
			
			int width = Math.abs(AreaCreateDownX - moveX);
			int height = Math.abs(AreaCreateDownY - moveY);
			
			int startX = AreaCreateDownX < moveX ? AreaCreateDownX : moveX;
			int startY = AreaCreateDownY < moveY ? AreaCreateDownY : moveY;
			
			startX = mDrawPanel.drawX > 0 ? startX - mDrawPanel.drawX : startX;
			startY = mDrawPanel.drawY > 0 ? startY - mDrawPanel.drawY : startY;
			
			if(width/imageWidth*100f >= 1 && height/imageHeight * 100f >= 1) {
				AreaVo vo = new AreaVo();
				vo.setX(startX);
				vo.setY(startY);
				vo.setWidth(width);
				vo.setHeight(height);
				vo.setImageWidth((int)imageWidth);
				vo.setImageHeight((int)imageHeight);
				
				vo.setPersentX(startX/imageWidth*100f);
				vo.setPersentY(startY/imageHeight*100f);
				vo.setPersentWidth(width/imageWidth*100f);
				vo.setPersentHeight(height/imageHeight*100f);
				mDrawPanel.areaList.add(vo);
			}
		}else {
			int areaCreateDownPosition[] = Utility.getZoomPosition(drawPanelScroll,mDrawPanel,AreaCreateDownX,AreaCreateDownY);
			AreaCreateDownX = areaCreateDownPosition[0];
			AreaCreateDownY = areaCreateDownPosition[1];
			
			int movePosition[] = Utility.getZoomPosition(drawPanelScroll, mDrawPanel, moveX, moveY);
			moveX = movePosition[0];
			moveY = movePosition[1];
			
			double imageWidth = mDrawPanel.imageWidth;
			double imageHeight = mDrawPanel.imageHeight;
			
			double width = Math.abs(AreaCreateDownX - moveX);
			double height = Math.abs(AreaCreateDownY - moveY);
			
			double startX = AreaCreateDownX < moveX ? AreaCreateDownX : moveX;
			double startY = AreaCreateDownY < moveY ? AreaCreateDownY : moveY;
			
			startX = mDrawPanel.drawX > 0 ? startX - mDrawPanel.drawX : startX;
			startY = mDrawPanel.drawY > 0 ? startY - mDrawPanel.drawY : startY;
			
			if(width/imageWidth * 100d >= 1 && height/imageHeight*100d >= 1) {
				AreaVo vo = new AreaVo();
				vo.setX((int)startX);
				vo.setY((int)startY);
				vo.setWidth((int)width);
				vo.setHeight((int)height);
				vo.setImageWidth((int)imageWidth);
				vo.setImageHeight((int)imageHeight);
				
				vo.setPersentX(startX/imageWidth*100d);
				vo.setPersentY(startY/imageHeight*100d);
				vo.setPersentWidth(width/imageWidth*100d);
				vo.setPersentHeight(height/imageHeight*100d);
				mDrawPanel.areaList.add(vo);
			}
		}
	}
	
	
	
	
	public void move(MouseEvent e) {
		moveX = e.getX();
		moveY = e.getY();
	}
	
	
	
	
	
}
