package event;

import java.awt.Point;

import Date.AreaVo;
import Date.AreaVo.AreaPoint;
import draw.DrawPanel;

public class EventAreaDown {
	DrawPanel mDrawPanel;
	public EventAreaDown(DrawPanel mDrawPanel) {
		this.mDrawPanel = mDrawPanel;
	}
	public int[] downView(int x,int y) {
		float distanceCenterPx = 0;
		float distanceCenterPy = 0;
		int directionX = 0;
		int directionY = 0;
		
		mDrawPanel.selectArea = -1;
		int touchBoxX = 0;
		int touchBoxY = 0;
		
		double PixelWidth = mDrawPanel.frameWidth / ((int)mDrawPanel.imageOrgWidth);
		double PixelHeight = mDrawPanel.frameHeight / ((int)mDrawPanel.imageOrgHeight);
		
		Point p = mDrawPanel.mImagePanel.drawPanelScroll.getViewport().getViewPosition();
		if(mDrawPanel.drawX > 0 || mDrawPanel.drawY > 0 ) {
			
			double imageWidthPersent_1 = mDrawPanel.imageWidth / 100d;
			double imageHeightPersent_1 = mDrawPanel.imageHeight / 100d;
			for(int i=0;i<mDrawPanel.areaList.size();i++) {
				AreaVo vo = mDrawPanel.areaList.get(i);
				AreaPoint mAreaPoint = vo.getPoint();
				int startX = mDrawPanel.drawX + ((int)(imageWidthPersent_1 * mAreaPoint.persentX));
				int startY = mDrawPanel.drawY + ((int)(imageHeightPersent_1 * mAreaPoint.persentY));
				int width = (int)(imageWidthPersent_1 * mAreaPoint.persentWidth);
				int height = (int)(imageHeightPersent_1 * mAreaPoint.persentHeight);
				
				if(startX < x && startX + width> x) {
					if(startY < y && startY + height > y) {
						touchBoxX = x - startX;
						touchBoxY = y - startY;
						directionX = (startX + (width/2)) - x < 0 ? 0 : 1;
						directionY = (startY + (height/2)) - y < 0 ? 0 : 1;
						
						distanceCenterPx = (float)Math.abs((startX + (width/2)) - x ) / (width/2) * 100f;
						distanceCenterPy = (float)Math.abs((startY + (height/2)) - y ) / (height/2) * 100f;
						mDrawPanel.selectArea = i;
					}
					
				}
			}
		}else {
			double imageWidth = mDrawPanel.img.getWidth(null);
			double imageHeight = mDrawPanel.img.getHeight(null);
			double imageWidth_P1 = imageWidth/100d;
			double imageHeight_P1 = imageHeight/100d;
			
			double imageWidthPersent_1 = mDrawPanel.imageWidth / 100d;
			double imageHeightPersent_1 = mDrawPanel.imageHeight / 100d;
			
			x = x-p.x;
			y = y-p.y;
			for(int i=0;i<mDrawPanel.areaList.size();i++) {
				AreaVo vo = mDrawPanel.areaList.get(i);
				int startX = (int)(((int)vo.getX() - (int)mDrawPanel.imageCropX) * PixelWidth);
				int startY = (int)(((int)vo.getY() - (int)mDrawPanel.imageCropY) * PixelHeight);
				int width = (int)(vo.getWidth() * PixelWidth);
				int height = (int)(vo.getHeight() * PixelHeight);
				
				
				if(startX < x && startX + width > x) {
					if(startY < y && startY + height > y) {
						touchBoxX = x - startX;
						touchBoxY = y - startY;
						directionX = (startX + (width/2)) - x < 0 ? 0 : 1;
						directionY = (startY + (height/2)) - y < 0 ? 0 : 1;
						distanceCenterPx = (float)Math.abs((startX + (width/2)) - x) / (width/2) * 100f;
						distanceCenterPx = (float)Math.abs((startY + (height/2)) - y) / (height/2) * 100f;
						mDrawPanel.selectArea = i;
					}
				}
			}
		}
		return new int[] {mDrawPanel.selectArea,touchBoxX,touchBoxY,(int)distanceCenterPx,(int)distanceCenterPy,directionX,directionY};
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
