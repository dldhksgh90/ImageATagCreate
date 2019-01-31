package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Utility.EventUtils;
import draw.ImagePanel;

public class MainFrame extends JFrame{
	public final static int NON = -999;
	public final static int width = 500;
	public final static int height = 300;
	
	PrintFrame mPrintFrame;
	ImagePanel mImagePanel;
	JPanel imagePanel;
	JPanel toolPanel;
	JFileChooser open = new JFileChooser();
	JMenu file;
	JMenuItem openFile;
	JMenuItem openURL;
	JMenuItem printArea;
	
	public MainFrame() {
		mImagePanel = new ImagePanel(this);
		mPrintFrame = new PrintFrame();
		setMenu();
		setFrame();
		setPrintFrame();
		
		setSize(width,height);
	}
	
	public void setMenu() {
		file = new JMenu("파일(F)");
		file.setMnemonic('F');
		openFile = new JMenuItem("FILE열기");
		openURL = new JMenuItem("URL열기");
		printArea = new JMenuItem("출력");
		
		file.add(openFile);
		file.add(openURL);
		file.add(printArea);
		JMenuBar nb = new JMenuBar();
		nb.add(file);
		setJMenuBar(nb);

		openFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int re = open.showOpenDialog(MainFrame.this);
				if(re == JFileChooser.APPROVE_OPTION) {
					String fN;
					String dir;
					String str;
					File file_open = open.getSelectedFile();
					try {
						Image image = ImageIO.read(file_open);
						mImagePanel.mDrawPanel.repaint();
						mImagePanel.mDrawPanel.updateUI();
						mImagePanel.mDrawPanel.invalidate();
						
					}catch(Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		});
		
		openURL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					try {
						String input = JOptionPane.showInputDialog("image URL");
						if(input.indexOf("http")==-1) {
							while(input.charAt(0)=='/') {
								input = input.substring(1,input.length());
							}
							input = "http://" + input;
						}
						
						URL url;
						url = new URL(input);
						System.out.println(input);
						mImagePanel.mDrawPanel.setImage(ImageIO.read(url));
						mImagePanel.mDrawPanel.areaList.removeAll(mImagePanel.mDrawPanel.areaList);
						
						mImagePanel.mDrawPanel.repaint();
						mImagePanel.mDrawPanel.updateUI();
						mImagePanel.mDrawPanel.invalidate();
						
					}catch(Exception e) {
						e.printStackTrace();
					}
					
				
			}
		});
		printArea.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					try {
						mPrintFrame.printStyle(mImagePanel.mDrawPanel);
					}catch(Exception e) {
						e.printStackTrace();
					}
					
				
			}
		});
	}
	public int[] getScreen() {
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		return new int[] {res.width,res.height};
	}
	
	public void setFrame() {
		int screen[] = getScreen();
		
		setBounds(500,600,width,height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		setComponent();
		setVisible(true);
		
		addComponentListener(new ComponentListener() {

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				setPrintFrame();
				mImagePanel.mDrawPanel.repaint();
				mImagePanel.mDrawPanel.updateUI();
				mImagePanel.mDrawPanel.invalidate();
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				setPrintFrame();
				mImagePanel.mDrawPanel.repaint();
				mImagePanel.mDrawPanel.updateUI();
				mImagePanel.mDrawPanel.invalidate();
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	public void setPrintFrame() {
		mPrintFrame.setBounds(getLocation().x,getLocation().y+getHeight(),getWidth(),60);
	}
	public void setComponent() {
		imagePanel = imageLayout();
		add(imagePanel,EventUtils.layout(0, 1, 1, 1, 1, 3, NON, NON, GridBagConstraints.BOTH, NON));
	}
	
	public JPanel imageLayout() {
		JPanel jpanel = new JPanel();
		jpanel.setBackground(new Color(255,255,255));
		jpanel.setLayout(new GridBagLayout());
		jpanel.add(mImagePanel.scrollPanel,EventUtils.layout(0, 1, 4, NON, 1, 10, NON, NON, GridBagConstraints.BOTH, NON));
		return jpanel;
	}
	
	
	
	
	
	
	
	
	
}
