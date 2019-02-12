package event;

import java.awt.event.MouseEvent;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import Date.ScrollDate;
import draw.DrawPanel;

public class EventDisplayMove {
	DrawPanel mDrawPanel;
	DrawTouchListener mDrawTouchListener;
	
	private double downPersentX;
	private double downPersentY;
	private double downScrollPersentX;
	private double downScrollPersentY;
	
	public EventDisplayMove(DrawPanel mDrawPanel,DrawTouchListener mDrawTouchListener) {
		this.mDrawPanel = mDrawPanel;
		this.mDrawTouchListener = mDrawTouchListener;
	}
	public ScrollDate getPoint(JScrollPane scroll) {
		JScrollBar vertical = scroll.getVerticalScrollBar();
		JScrollBar horizontal = scroll.getHorizontalScrollBar();
		
		double height = vertical.getMaximum();
		double orgHeight = vertical.getMaximum() - vertical.getVisibleAmount();
		double positionY = vertical.getValue();
		
		double width = horizontal.getMaximum();
		double orgWidth = horizontal.getMaximum() - horizontal.getVisibleAmount();
		double positionX  = horizontal.getValue();
	
		return new ScrollDate(positionX,positionY,width,height,orgWidth,orgHeight,horizontal.getVisibleAmount(),vertical.getVisibleAmount());
	}
	
	public void displayDown(MouseEvent e) {
		float imageWidth = mDrawPanel.imageWidth;
		float imageHeight = mDrawPanel.imageHeight;
		
		downPersentX = (e.getX() - mDrawPanel.drawX) / imageWidth * 100;
		downPersentY = (e.getY() - mDrawPanel.drawY) / imageHeight * 100;
		

		JScrollBar vertical = mDrawPanel.mImagePanel.drawPanelScroll.getVerticalScrollBar();
		JScrollBar horizontal =  mDrawPanel.mImagePanel.drawPanelScroll.getHorizontalScrollBar();
		ScrollDate date = getPoint(mDrawPanel.mImagePanel.drawPanelScroll);
		
		downScrollPersentX = date.x / date.orgWidth * 100;
		downScrollPersentY = date.y / date.orgHeight * 100;
	}
	
	public void displayMove(MouseEvent e) {
		double imageWidth = mDrawPanel.imageWidth;
		double imageHeight = mDrawPanel.imageHeight;
		double persentX = (e.getX() - mDrawPanel.drawX) / imageWidth * 100;
		double persentY = (e.getY() - mDrawPanel.drawY) / imageHeight * 100;
		
		double movePersentX = downPersentX - persentX;
		double movePersentY = downPersentY - persentY;
		
		ScrollDate date = getPoint(mDrawPanel.mImagePanel.drawPanelScroll);
		double scrollMoveX = (downScrollPersentX + movePersentX) * (date.orgWidth / 100d);
		double scrollMoveY = (downScrollPersentY + movePersentY) * (date.orgHeight / 100d);
		mDrawPanel.mImagePanel.drawPanelScroll.getHorizontalScrollBar().setValue((int)scrollMoveX);
		mDrawPanel.mImagePanel.drawPanelScroll.getVerticalScrollBar().setValue((int)scrollMoveY);
		
	}
	
	
}
