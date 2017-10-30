package bearsCream;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class IceCream extends Thread{

	int i_random=(int)(Math.random()*25)+1; //아이스크림 이미지 랜덤
	
	Image icecreamimg=new ImageIcon(this.getClass().getResource("/icecream"+i_random+".png")).getImage();
	
	int x1,y1=100,x2,y2;
	int x_distance; //앞 아이스크림과 x거리
	int size;
	int speed;
	
	int b_x;//이거 전 층의 x좌표
	
	private boolean proceeded=true; //아이스크림의 진행여부
	private boolean i_catch=false; //받았는지 못받았는지
	
	public IceCream(int size, int speed){
		this.size=size;
		this.speed=speed;
		x1=(int)(Math.random()*880)+20;
		x2=x1+size;
		y1=0;
	}

	public boolean isProceeded(){
		return proceeded;
	}
	
	public boolean geti_catch(){
		return i_catch;
	}
	
	public boolean isCatch(int y1,int x1,int x2){
		int okay=this.size/2;
		if((this.y2<=y1&&this.y2>=(y1-10))){ // 확인 문장 쓰기
			if((this.x2-x1)>=okay&&(x2-this.x1)>=okay){ //아이스크림을 이전꺼보다 오른쪽으로 받았을때
				x_distance=this.x1-x1;
				i_catch=true;
			}
//			i_catch=true;
		}
		return i_catch;
	}
	
	public void close(){
		proceeded=false;
	}
	
	public void iceCreamDraw(Graphics g){
		g.drawImage(icecreamimg,x1,y1,size,(size/5)*4,null);
	}
	
	public void drop(){
		y1+=10;
		y2=y1+(size/5*4);
		if(i_catch){
			close();
		}
	}
	
	public void run(){
		try{
			while(true){
				drop();
				if(proceeded){
					Thread.sleep(speed);
				}else{
					//interrupt();
					break;
				}
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
}
