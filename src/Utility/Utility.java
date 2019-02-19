package Utility;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JScrollPane;

import Date.AreaVo;
import draw.DrawPanel;

public class Utility {

	public static double[] getZoomPosition(JScrollPane mJScrollPane,DrawPanel mDrawPane,double x ,double y) {
		Point p = mJScrollPane.getViewport().getViewPosition();
		int drawX = (int)(mDrawPane.imageCropX * mDrawPane.zoom);
		int drawY = (int)(mDrawPane.imageCropY * mDrawPane.zoom);
		return new double[] {x-p.x+drawX,y-p.y+drawY};
	}	
	public static int[] getZoomPosition(JScrollPane mJScrollPane,DrawPanel mDrawPane,int x ,int y) {
		Point p = mJScrollPane.getViewport().getViewPosition();
		int drawX = (int)(mDrawPane.imageCropX * mDrawPane.zoom);
		int drawY = (int)(mDrawPane.imageCropY * mDrawPane.zoom);
		return new int[] {x-p.x+drawX,y-p.y+drawY};
	}
	public static double[] getZoomImagePosition(JScrollPane mJScrollPane,DrawPanel mDrawPane,double x ,double y) {
		Point p = mJScrollPane.getViewport().getViewPosition();
		
		return new double[] {x/mDrawPane.imageWidth * 100
				,y / mDrawPane.imageHeight * 100
				,(x-p.x)/mDrawPane.frameWidth * 100
				,(y-p.y)/mDrawPane.frameHeight * 100};
	}
	public static double[] getPixel(AreaVo vo,DrawPanel mDrawPane) {
		double PixelWidth = mDrawPane.frameWidth / ((int)mDrawPane.imageOrgWidth);
		double PixelHeight = mDrawPane.frameHeight / ((int)mDrawPane.imageOrgHeight);
			return new double[] {
					(int)(((int)vo.getX() - (int)mDrawPane.imageCropX) * PixelWidth),
					(int)(((int)vo.getY() - (int)mDrawPane.imageCropY) * PixelHeight),
					(int)(vo.getWidth() * PixelWidth),
					(int)(vo.getHeight() * PixelHeight)
			};
		
	}
}
