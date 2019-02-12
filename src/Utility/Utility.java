package Utility;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JScrollPane;

import draw.DrawPanel;

public class Utility {
	public static int[] getScreen() {
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		return new int[] {res.width,res.height};
	}
	public static GridBagConstraints layout(double weight_x,double weight_y,int fill) {
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = fill != -1 ? fill : c.fill;
		c.weightx = weight_x != -1 ? weight_x : c.weightx;
		c.weighty = weight_y != -1 ? weight_y : c.weighty;
		
		return c;
	}
	public static GridBagConstraints layout(int x,int y,int width,int height,double weight_x,double weight_y,int fill) {
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;

		c.fill = fill != -1 ? fill : c.fill;
		
		c.gridx = x != -1 ? x : c.gridx;
		c.gridy = y != -1 ? y : c.gridy;
		
		c.gridwidth = width != -1 ? width : c.gridwidth;
		c.gridheight = height != -1 ? height : c.gridheight;
		
		c.weightx = weight_x != -1 ? weight_x : c.weightx;
		c.weighty = weight_y != -1 ? weight_y : c.weighty;
		
		return c;
	}
	
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
}
