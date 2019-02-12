package event;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;

import Date.AreaVo;
import Utility.Utility;
import draw.DrawPanel;

public class EventAreaMove {
	int downAreaX;
	int downAreaY;
	
	DrawPanel mDrawPanel;
	DrawTouchListener mDrawTouchListener;
	public EventAreaMove(DrawPanel mDrawPanel,DrawTouchListener mDrawTouchListener) {
		this.mDrawPanel = mDrawPanel;
		this.mDrawTouchListener = mDrawTouchListener;
	}
	public void areaMove(MouseEvent e) {
		int saveArea = mDrawPanel.selectArea;
		if(saveArea >= 0) {
			JScrollPane drawPanelScroll = mDrawPanel.mImagePanel.drawPanelScroll;
			Point p = mDrawPanel.mImagePanel.drawPanelScroll.getViewport().getViewPosition();
			if(mDrawPanel.drawY>0 || mDrawPanel.drawX > 0) {
				AreaVo vo = mDrawPanel.areaList.get(saveArea);
				float imageWidthPersent_1 = mDrawPanel.imageWidth / 100f;
				float imageHeightPersent_1 = mDrawPanel.imageHeight / 100f;
				float imageWidth = mDrawPanel.imageWidth;
				float imageHeight = mDrawPanel.imageHeight;
				int startX = e.getX() - downAreaX;
				int startY = e.getY() - downAreaY;
				startX = mDrawPanel.drawX > 0 ? startX - mDrawPanel.drawX : startX;
				startY = mDrawPanel.drawY > 0 ? startY - mDrawPanel.drawY : startY;
				
				vo.setPersentX(startX/imageWidth*100f);
				vo.setPersentY(startY/imageHeight*100f);
			}else {
				AreaVo vo = mDrawPanel.areaList.get(saveArea);
				float imageWidthPersent_1 = mDrawPanel.imageWidth / 100f;
				float imageHeightPersent_1 = mDrawPanel.imageHeight / 100f;
				float imageWidth = mDrawPanel.imageWidth;
				float imageHeight = mDrawPanel.imageHeight;
				int movePosition[] = Utility.getZoomPosition(drawPanelScroll,mDrawPanel,e.getX(),e.getY());
				int startX = movePosition[0] - downAreaX;
				int startY = movePosition[1] - downAreaY;
				
				startX = mDrawPanel.drawX > 0 ? startX - mDrawPanel.drawX : startX;
				startY = mDrawPanel.drawY > 0 ? startY - mDrawPanel.drawY : startY;
				vo.setPersentX(startX/imageWidth*100f);
				vo.setPersentY(startY/imageHeight*100f);
				
				
			}
		}
	}
}
