package draw;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Utility.EventUtils;
import main.MainFrame;

public class ImagePanel extends JPanel{
	public JPanel scrollPanel;
	public DrawPanel mDrawPanel;
	public JScrollPane drawPanelScroll;
	public MainFrame mMainFrame;
	
	public final static int NON = -999;
	
	public ImagePanel(MainFrame mMainFrame) {
		this.mMainFrame = mMainFrame;
		mDrawPanel = new DrawPanel(this,mMainFrame);
		
		drawPanelScroll = new JScrollPane(mDrawPanel);
		drawPanelScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		drawPanelScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		drawPanelScroll.setBackground(new Color(255,255,255));
		scrollPanel = new JPanel();
		scrollPanel.setLayout(new GridBagLayout());
		scrollPanel.add(drawPanelScroll,EventUtils.layout(0,0,1,1,1,1,NON,NON,GridBagConstraints.BOTH,NON));
		
		drawPanelScroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				mDrawPanel.repaint();
			}
			
		});
		drawPanelScroll.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				mDrawPanel.repaint();
			}
			
		});
		drawPanelScroll.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				mDrawPanel.repaint();
			}
			
		});
	}
}
