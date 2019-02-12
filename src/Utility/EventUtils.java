package Utility;

import java.awt.GridBagConstraints;

public class EventUtils {
	public static GridBagConstraints layout(int positionX,int positionY,int gridWidth,int gridHeight,
			double weightX,double weightY,int ipadx,int ipady,int fill,int anchor) {
		GridBagConstraints  c = new GridBagConstraints();

		c.fill = fill != -999 ? fill : c.fill;
		c.anchor = anchor != -999 ? anchor : c.anchor;
		
		c.gridx = positionX != -999 ? positionX : c.gridx;
		c.gridy = positionY != -999 ? positionY : c.gridy;
		
		c.gridwidth = gridWidth != -999 ? gridWidth : c.gridwidth;
		c.gridheight = gridHeight != -999 ? gridHeight : c.gridheight;
		
		c.weightx = weightX != -999 ? weightX : c.weightx;
		c.weighty = weightY != -999 ? weightY : c.weighty;
		
		c.ipadx = ipadx != -999 ? ipadx : c.ipadx;
		c.ipady = ipady != -999 ? ipady : c.ipady;
		
		
		return c;
	}
}
