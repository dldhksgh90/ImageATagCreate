package event;

import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;

import Date.AreaVo;
import Utility.Utility;
import draw.DrawPanel;

public class EventAreaReSize {
	
	int downAreaX;
	int downAreaY;
	double downPersentX;
	double downPersentY;
	double downPersentWidth;
	double downPersentHeight;
	double downFullX;
	double downFullY;
	
	int downView[];
	
	DrawPanel mDrawPanel;
	DrawTouchListener mDrawTouchListener;
	
	double directionX = 0;
	double directionY = 0;
	
	public EventAreaReSize(DrawPanel mDrawPanel,DrawTouchListener mDrawTouchListener) {
		this.mDrawPanel = mDrawPanel;
		this.mDrawTouchListener = mDrawTouchListener;
	}
	public void areaDown(MouseEvent e,int[] view) {
		JScrollPane drawPanelScroll = mDrawPanel.mImagePanel.drawPanelScroll;
		
		float imageHeight = mDrawPanel.imageHeight;
		float imageWidth = mDrawPanel.imageWidth;
		
		int downPosition[] = Utility.getZoomPosition(drawPanelScroll,mDrawPanel,e.getX(),e.getY());
		downPersentX = (downPosition[0] - mDrawPanel.drawX) / imageWidth * 100;
		downPersentY = (downPosition[1] - mDrawPanel.drawY) / imageHeight * 100;
		
		int saveArea = mDrawPanel.selectArea;
		if (saveArea >= 0) {
			AreaVo vo = mDrawPanel.areaList.get(saveArea);
			downPersentWidth = vo.getPersentWidth();
			downPersentHeight = vo.getPersentHeight();
			downFullX = vo.getPersentWidth() + vo.getPersentX();
			downFullY = vo.getPersentHeight() + vo.getPersentY();
		}
		
		directionX = view[5];
		directionY = view[6];
		downView = view;
		System.out.println("=============================");
		System.out.println(downPersentWidth+" : " + downPersentHeight);
		System.out.println(downPersentWidth+" : " + downPersentHeight);
		System.out.println(downPersentWidth+" : " + downPersentHeight);
		
	}
	public void areaReSize(MouseEvent e) {
		JScrollPane drawPanelScroll = mDrawPanel.mImagePanel.drawPanelScroll;
		
		int saveArea = mDrawPanel.selectArea;
		
		if(saveArea >= 0) {
			AreaVo vo = mDrawPanel.areaList.get(saveArea);
			float imageWidthPersent_1 = mDrawPanel.imageWidth / 100f;
			float imageHeightPersent_1 = mDrawPanel.imageHeight / 100f;
			float imageWidth = mDrawPanel.imageWidth;
			float imageHeight = mDrawPanel.imageHeight;
			int startX = e.getX() - downAreaX;
			int startY = e.getY() - downAreaY;
			startX = mDrawPanel.drawX > 0 ? startX - mDrawPanel.drawX : startX;
			startY = mDrawPanel.drawY > 0 ? startY - mDrawPanel.drawY : startY;
			int movePosition[] = Utility.getZoomPosition(drawPanelScroll, mDrawPanel, e.getX(), e.getY());
			float areaMoveWidth = (movePosition[0] - mDrawPanel.drawX) / imageWidth * 100f;
			float areaMoveHeight = (movePosition[1] - mDrawPanel.drawY) / imageHeight * 100;
			double reWidth = 0;
			double reHeight = 0;
			if(directionX == 0) {
				reWidth = downPersentWidth + (areaMoveWidth - downPersentX);
			}else {
				reWidth = downPersentWidth + (downPersentX - areaMoveWidth);
			}
			
			if(directionY == 0) {
				reHeight = downPersentHeight + (areaMoveHeight - downPersentY);
			}else {
				reHeight = downPersentHeight + (downPersentY - areaMoveHeight);
			}
			reWidth = reWidth < 1 ? 1 : reWidth;
			reHeight = reHeight < 1 ? 1 : reHeight;
			
			vo.setPersentWidth(reWidth);
			vo.setPersentHeight(reHeight);
			
			if(directionX != 0 ) {
				vo.setPersentX(downFullX - vo.getPersentWidth());
			}
			if(directionY != 0) {
				vo.setPersentY(downFullY - vo.getPersentHeight());
			}
		}
	}
}
















