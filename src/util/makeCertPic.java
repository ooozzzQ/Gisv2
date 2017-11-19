package util;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;

 
public class makeCertPic {
	private static  Random r=new Random();
 
	private char mapTable[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9' };

	 
	public String getCertPic(int width, int height, OutputStream os) {
		if (width <= 0)
			width =80;
		if (height <= 0)
			height = 30;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		 
		Graphics g = image.getGraphics();
		 
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		 
		g.setColor(Color.white);
		g.drawRect(0, 0, width - 1, height - 1);
		 
		String strEnsure = "";
		
		for (int i = 0; i < 4; ++i) {
			strEnsure += mapTable[(int) (mapTable.length * Math.random())];
		}
		 
		g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
		g.setFont(new Font("Atlantic Inline", Font.BOLD, 25));
		String str = strEnsure.substring(0, 1);
		g.drawString(str, 8, 20);
		str = strEnsure.substring(1, 2);
		g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
		g.drawString(str, 25, 18);
		str = strEnsure.substring(2, 3);
		g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
		g.drawString(str, 40, 22);
		str = strEnsure.substring(3, 4);
		g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
		g.drawString(str, 60, 25);
		 
	
		for (int i = 0; i < 10; i++) {
			int x = r.nextInt(width);
			int y = r.nextInt(height);
			g.drawOval(x, y, 1, 1);
			//g.drawLine(r.nextInt(10), r.nextInt(20),r.nextInt(40),r.nextInt(60));
		}
		
		 
		g.dispose();
		try {
			 
			ImageIO.write(image, "JPEG", os);
		} catch (IOException e) {
			return "";
		}
		return strEnsure;
	}
}
