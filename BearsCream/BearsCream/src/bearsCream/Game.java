package bearsCream;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Game extends Thread{
	public Image judgment = null;
	ArrayList<IceCream> iList=new ArrayList<IceCream>(5);

	IceCream icc;
	Bear b=new Bear();
	
	public static Cornet c=new Cornet(430,660);
	private int size=100;
	private int speed=150;
	
	private int before_x1,before_x2,before_y1;
	
	private int i_cnt=0;
	
	private int score=0;
	
	public Game(){
	}
	
	public void screenDraw(Graphics g){
		b.BearDraw(g);
		g.drawImage(judgment, 400, 300, 200, 62,null);
		for(int i=0;i<iList.size();i++){
			IceCream ic=iList.get(i);
			if(i==0){
				before_y1=c.y;
				before_x1=c.x;
				before_x2=c.x+139;
			}else{
				before_y1=iList.get(i-1).y1;
				before_x1=iList.get(i-1).x1;
				before_x2=iList.get(i-1).x2;
			}
			if(ic.isCatch(before_y1,before_x1,before_x2)){
				if(i==0){
					ic.x1=c.x+ic.x_distance;
					ic.y1=660-(ic.size/5*4);
				}
				else{
					ic.y1=iList.get(i-1).y1-(ic.size/5*4);
					ic.x1=iList.get(i-1).x1+ic.x_distance;
				}
				ic.x2=ic.x1+ic.size;
				ic.iceCreamDraw(g);
				if(i_cnt==5){//5개면 삭제
					b.look++;
					judgment=new ImageIcon(this.getClass().getResource("/catch.png")).getImage();
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(int j=0;j<i_cnt;j++)
						iList.remove(0); //5개되면 삭제
					i_cnt=0;
					score++;
				}
				if(i>=i_cnt)
					i_cnt++;
			}else{
				ic.iceCreamDraw(g);
				if(ic.y2>before_y1&&iList.size()==5){
					b.look--;
					judgment=new ImageIcon(this.getClass().getResource("/miss.png")).getImage();
					for(int j=0;j<5;j++){
						iList.remove(0);
					}
				}
			}
		}
		g.drawImage(c.cornet,c.x,c.y,92,70,null);
		g.setColor(Color.white);
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.drawString(String.valueOf(score), 920, 45);
	}
	
	public void pressLeft(){
		c.Left();
	}
	
	public void pressRight(){
		c.Right();
	}
	public void run(){
		try {
			while(true){
				for(int i=0;i<5;i++){
					icc=new IceCream(size,speed);
					iList.add(icc);
					size-=10;
					icc.start();
					Thread.sleep(speed*20);//1개 만들고 쉬기
				}
				Thread.sleep(speed*25);//5개 만들고 쉬기
				judgment=null;
				size=100;
				speed-=5;
				if(b.look==-2){
					interrupt();
					break;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
