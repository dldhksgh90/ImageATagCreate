package main;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Date.AreaVo;
import Date.AreaVo.AreaPoint;
import draw.DrawPanel;

public class AreaPrint {
	public JScrollPane scroll;
	JTextArea mJTextArea;
	public AreaPrint() {
		mJTextArea = new JTextArea();
		scroll = new JScrollPane(mJTextArea);
	}
	public void printStyle(DrawPanel mDrawPanel) {
		
		ArrayList<AreaVo> areaList = mDrawPanel.areaList;
		String style = "";
		for(int i=0;i< areaList.size();i++) {
			int imageWidth = mDrawPanel.img.getWidth(null);
			int imageHeight = mDrawPanel.img.getHeight(null);
			AreaVo vo = areaList.get(i);
			AreaPoint mAreaPoint = vo.getPoint();
			
			boolean isLeft = Math.abs(mAreaPoint.persentX + mAreaPoint.persentWidth - 100) < Math.abs(vo.getX());
			boolean isTop = Math.abs(mAreaPoint.persentY + mAreaPoint.persentHeight - 100) < Math.abs(vo.getY());
			
			double paddingHeight = rounds((((mAreaPoint.persentHeight * (imageHeight/100d))/imageWidth)*100d),1000);
			
			style += i + " : ";
			style += "width:"+rounds(mAreaPoint.persentWidth,1000)+"%;";
			style += isTop ? "padding-top:"+paddingHeight+"%;":"padding-bottom:"+paddingHeight+"%;";
			
			style += isLeft ? ("right:"+rounds((100f - (mAreaPoint.persentX+mAreaPoint.persentWidth)),1000)+"%;") : ("left:"+rounds(((mAreaPoint.persentX)),1000)+"%;");
			style += isTop ? ("bottom:"+rounds((100f - (mAreaPoint.persentY+mAreaPoint.persentHeight)),1000)+"%;") : ("top:"+rounds(((mAreaPoint.persentY)),1000)+"%;");
			style += "\n";
			
		}
		style = style.replaceAll(";", "; ");
		mJTextArea.setText(style);
	}
	public void printMap(DrawPanel mDrawPanel) {
		ArrayList<AreaVo> areaList = mDrawPanel.areaList;
		String style = "";
		
		int imageWidth = mDrawPanel.img.getWidth(null);
		int imageHeight = mDrawPanel.img.getHeight(null);
		float imageWidth_1 = (imageWidth/100f);
		float imageHeight_1 = (imageHeight/100f);
		for(int i=0;i< areaList.size();i++) {
			
			AreaVo vo = areaList.get(i);
			AreaPoint mAreaPoint = vo.getPoint();
			int startX = (int)(imageWidth_1 * mAreaPoint.persentX);
			int endX = (int)(imageWidth_1 * (mAreaPoint.persentX+mAreaPoint.persentWidth));
			int startY = (int)(imageWidth_1 * mAreaPoint.persentY);
			int endY = (int)(imageWidth_1 * (mAreaPoint.persentY+mAreaPoint.persentHeight));
			
			style += i + " : ";
			style += "<area shape=\"rect\" coords=\""+startX+","+startY+","+endX+","+endY+"\" href=\"\" />";
			style += "\n";
		}
		mJTextArea.setText(style);
	}
	public double rounds(double value,double size) {
		return ((double)((int)(value*size)))/size;
	}
}
