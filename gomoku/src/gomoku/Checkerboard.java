package gomoku;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Checkerboard extends JPanel implements Config{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image = (Image) new ImageIcon("picture/timg.jpg").getImage(); 
	@Override
	public void paint(Graphics g) {
		//super.paint(g);
		//∆Â≈Ã
		g.drawImage(image,0, 0, x+size*(col-1)+20, 20+y+size*(row-1), this); 
		g.setColor(Color.black);
		for(int i=0;i<row;i++) {
			g.drawLine(x, y+size*i, x+size*(col-1), y+size*i);
		}
		for(int j=0;j<col;j++) {
			g.drawLine(x+size*j, y, x+size*j, y+size*(row-1));
		}
		
		//∆Â◊”
		for(int i=0;i<row;i++) {
			for(int j=0;j<col;j++) {
				if(Gframe.piece[i][j]==1) {
					int tx=size*j+20;
					int ty=size*i+20;
					g.setColor(Color.black);
					g.fillOval(tx-size/2, ty-size/2, size, size);
				}
				else if(Gframe.piece[i][j]==2) {
					int tx=size*j+20;
					int ty=size*i+20;
					g.setColor(Color.white);
					g.fillOval(tx-size/2, ty-size/2, size, size);
				}
			}
		}
	}

}
