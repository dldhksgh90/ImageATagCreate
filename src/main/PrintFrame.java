package main;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Date.AreaVo;
import draw.DrawPanel;

public class PrintFrame extends JFrame{
	
	public JScrollPane scroll;
	JTextArea mJTextArea;
	
	public PrintFrame() {
		setBounds(100,200,40,20);
		setUndecorated(true);
		setVisible(true);
		mJTextArea = new JTextArea();
		scroll = new JScrollPane(mJTextArea);
		add(scroll);
	}
	
	public void printStyle(DrawPanel mDrawPanel) {
		ArrayList<AreaVo> areaList = mDrawPanel.areaList;
		String style = "";
		for(int i=0;i< areaList.size();i++) {
			int imageWidth = mDrawPanel.img.getWidth(null);
			int imageHeight = mDrawPanel.img.getHeight(null);
			AreaVo vo = areaList.get(i);
			boolean isLeft = Math.abs(vo.getPersentX() + vo.getPersentWidth() - 100) < Math.abs(vo.getX());
			boolean isTop = Math.abs(vo.getPersentY() + vo.getPersentHeight() - 100) < Math.abs(vo.getY());
			
			double paddingHeight = rounds((((vo.getPersentHeight() * (imageHeight/100d))/imageWidth)*100d),1000);
			
			style += i + " : ";
			style += "width:"+rounds(vo.getPersentWidth(),1000)+"%;";
			style += isTop ? "padding-top:"+paddingHeight+"%;":"padding-bottom:"+paddingHeight+"%;";
			
			style += isLeft ? ("right:"+rounds((100f - (vo.getPersentX()+vo.getPersentWidth())),1000)+"%;") : ("left:"+rounds(((vo.getPersentX())),1000)+"%;");
			style += isTop ? ("bottom:"+rounds((100f - (vo.getPersentY()+vo.getPersentHeight())),1000)+"%;") : ("top:"+rounds(((vo.getPersentY())),1000)+"%;");
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
			int startX = (int)(imageWidth_1 * vo.getPersentX());
			int endX = (int)(imageWidth_1 * (vo.getPersentX()+vo.getPersentWidth()));
			int startY = (int)(imageWidth_1 * vo.getPersentY());
			int endY = (int)(imageWidth_1 * (vo.getPersentY()+vo.getPersentHeight()));
			
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
