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
		float widthPersent_1 = (mDrawPanel.orgImageWidth/100f);
		float heightPersent_1 = (mDrawPanel.orgImageHeight/100f);
		
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
				vo.setX((startX/imageWidth*100f) * widthPersent_1);
				vo.setY((startY/imageHeight*100f) * heightPersent_1);
				vo.setWidth((width/imageWidth*100f) * widthPersent_1);
				vo.setHeight((height/imageHeight*100f) * heightPersent_1);
				vo.setImageWidth(mDrawPanel.orgImageWidth);
				vo.setImageHeight(mDrawPanel.orgImageHeight);
				
				mDrawPanel.areaList.add(vo);
			}
		}else {

			double tempDownX = (AreaCreateDownX - p.x) / mDrawPanel.zoom;
			double tempDownY = (AreaCreateDownY - p.y) / mDrawPanel.zoom; 
			double tempMoveX = (moveX - p.x) / mDrawPanel.zoom;
			double tempMoveY = (moveY - p.y) / mDrawPanel.zoom;
			
			double width = Math.abs(tempDownX - tempMoveX);
			double height = Math.abs(tempDownY - tempMoveY);
			
			double startX = tempDownX < tempMoveX ? tempDownX : tempMoveX;
			double startY = tempDownY < tempMoveY ? tempDownY : tempMoveY;

			
			
				AreaVo vo = new AreaVo();
				vo.setX((int)(mDrawPanel.imageCropX + (startX)));
				vo.setY((int)(mDrawPanel.imageCropY + (startY)));
				vo.setWidth((int)width);
				vo.setHeight((int)height);
				vo.setImageWidth(mDrawPanel.orgImageWidth);
				vo.setImageHeight(mDrawPanel.orgImageHeight);
				
				mDrawPanel.areaList.add(vo);
		}
	}
	
	public void move(MouseEvent e) {
		moveX = e.getX();
		moveY = e.getY();
	}
	
}
