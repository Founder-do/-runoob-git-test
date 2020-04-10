package cn.tedu.shoot;

import java.awt.image.BufferedImage;

public class BigAirplane extends FlyingThing implements Score{
	private int xStep,yStep;
	private static BufferedImage[] images;
	static {
		images=new BufferedImage[6];
		images[0]=readImage("bigairplane0.png");
		//爆炸图
		for(int i=1;i<images.length;i++) {
			images[i]=readImage("bom"+i+".png");
		}
	}
public BigAirplane() {
	super(66,89);
	xStep=2;
	yStep=2;
}
public void show(){
	super.show();
	System.out.println("x速度"+xStep);
	System.out.println("y速度"+yStep);
}
public void step(){
	y+=yStep;
	x+=xStep;
	//如果奖励机碰撞了左右两侧的边界，修改移动方向
	if(x<=0||x>=400-this.width) {
		xStep*=-1;
	}
}
public void step1(){//备用
	y+=2*yStep;
	x-=xStep;
	if(x<=0||x>=400-this.width) {
		xStep*=-1;
	}
}

int index=1;
public BufferedImage getImage() {
	BufferedImage img=null;
	//如果活着返回第1张图
	if(isLife()) {img=images[0];}else if(isDead()) {
		//如果死了，获得爆炸图片
		img=images[index];
		index++;
		//如果爆炸完毕，将敌机设置为消失
		if(index==images.length){
			state=REMOVE;
		}
	}
	return img;
}
@Override
public int getScore() {
	// TODO Auto-generated method stub
	return 3;
}
}
