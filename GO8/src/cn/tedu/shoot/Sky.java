package cn.tedu.shoot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sky extends FlyingThing{
	private static BufferedImage image;
	static {
		image=readImage("background.png");
	}
	//第二张背景图的y轴
	private int y1;
	private int Step;
	public Sky() {
   super(World.WIDTH,World.HEIGHT,0,0);
		this.Step=1;
		this.y1=-World.HEIGHT;
	}
	public void show(){
		super.show();
		System.out.println("速度"+Step);
		System.out.println("图片位置"+y1);
	}
	public void step(){
		//两张图都移动
		y+=Step;
		y1+=Step;
		//当任何一张图片移动出窗体时都要重置到下方
		if(y>World.HEIGHT) {
			y=-World.HEIGHT;
		}
		if(y1>World.HEIGHT) {
			y1=-World.HEIGHT;
		}
	}
	public BufferedImage getImage() {
		return image;
	}
	public void paintThing(Graphics g) {
		g.drawImage(getImage(),x,y,null);
		g.drawImage(getImage(),x,y1,null);
	}
}
