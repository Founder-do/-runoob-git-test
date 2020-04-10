package cn.tedu.shoot;

import java.awt.image.BufferedImage;

public class Bullet extends FlyingThing{
	private static BufferedImage images;
	static {
		images=readImage("bullet.png");
	}
	private int Step;
	public Bullet(int x,int y){
		super(8,20,x,y);
		this.Step=20;
	}
	public void show(){
		super.show();
		System.out.println("速度"+Step);
	}
	public void step(){
		y-=Step;
	}
	public BufferedImage getImage() {
		BufferedImage img=null;
		//如果活着返回第1张图
		if(isLife()) {img=images;}else if(isDead()) {
			state=REMOVE;
		}
		return img;
	}
	//子弹向上出界的判断
	public boolean isOutOfBounds() {
		return y<-this.height;
	}
}
