package cn.tedu.shoot;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Bee extends FlyingThing implements Award{
	private static BufferedImage[] images;
	static {
		images=new BufferedImage[7];
		images[0]=readImage("bee3.png");
		images[1]=readImage("bee0.png");
		//爆炸图
		for(int i=2;i<images.length;i++) {
			images[i]=readImage("bom"+(i-1)+".png");//拼接：system输出的为字符串只有变量和基本类型可以不加
			//""引号，中英文符号必加，如果需要运算需加括号，+号左右为字符串例如：   （""+4+-2+""）的结果为字符串4-2
		}
	}
	//移动分方向
	private double xStep;
	private double yStep;
	//奖励类型
	int awardType;
	public Bee() {
		super(60,51);
		this.xStep=2.2;
		this.yStep=2.3;
		Random ran=new Random();
		// 随即生成数存入奖励类型，用于击中敌机时获得不同奖励
		awardType=ran.nextInt(2);
		this.awardType=awardType;
	}
	public void show(){
		super.show();
		System.out.println("x速度"+xStep);
		System.out.println("y速度"+yStep);
		System.out.println("奖励类型"+awardType);
	}
	public void step(){
		y+=yStep;
		x+=xStep;
		//如果奖励机碰撞了左右两侧的边界，修改移动方向
		if(x<=0||x>=400-this.width) {
			xStep*=-1;
		}
	}
	int index=2,num=0;
	public BufferedImage getImage() {
		BufferedImage img=null;
		//如果活着返回第1张图
		if(isLife()) {img=images[num%2];
		num++;
		}else if(isDead()) {
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
	public int awardType() {
		// TODO Auto-generated method stub
		return this.awardType;
	}
}
