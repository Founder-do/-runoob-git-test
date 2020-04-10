package cn.tedu.shoot;

import java.awt.image.BufferedImage;

public class Bullet1 extends FlyingThing{
	private static BufferedImage []images;
	static {
		images=new BufferedImage[5];
		images[0]=readImage("bullet1.png");
		//爆炸图
		for(int i=1;i<images.length;i++) {
			images[i]=readImage("bom"+i+".png");
		}
	}
	private int Step;
	public Bullet1(int x,int y){
		super(8,20,x,y);
		this.Step=4;
	}
	public void show(){
		super.show();
		System.out.println("速度"+Step);
	}
	public void step(){
		y+=Step;
	}
	int index=1;
	public BufferedImage getImage() {
		BufferedImage img=null;
		if(isLife()) {img=images[0];}else if(isDead()) {
			//如果死了，获得爆炸图片
			img=images[index];
			index++;
			//如果爆炸完毕，将子弹1设置为消失
			if(index==images.length){
				state=REMOVE;
			}
		}
		return img;
	}
	//子弹向下出界的判断
	public boolean isOutOfBounds() {
		return y>2*World.WIDTH;
	}
}
