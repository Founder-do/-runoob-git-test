package cn.tedu.shoot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;


//父类
public abstract class FlyingThing {
	//定义三种常量
	public static final int LIFE=0;//活的
	public static final int DEAD=1;//死的
	public static final int REMOVE=2;//消失
	//定义当前对象的状态属性
	protected int state=LIFE;//初始默认活
	//所有子类共有属性
	protected int width;
	protected int height;
	//位置信息
	protected int x;
	protected int y;
	//定义一个单位移动的方法
	public abstract void step();
	//判断状态的方法
	public boolean isLife() {
		return state==LIFE;
	}
	public boolean isDead() {
		return state==DEAD;
	}
	public boolean isRemove() {
		return state==REMOVE;
	}
	//获得图片的抽象方法
	public abstract BufferedImage  getImage();
	//绘制图片到窗体
	public void paintThing(Graphics g){
		g.drawImage(getImage(),x,y,null);
	}
	//判断出界的方法
	public boolean isOutOfBounds() {
		
		return y>World.HEIGHT;
	}
	//判断碰撞的方法  判断的是this（当前对象）是否和参数中的飞行物碰撞
	public boolean hit(FlyingThing f) {
		//this.想象为子弹，f想象为一架敌机
		int x1=f.x-this.width;//左侧点
		int x2=f.x+f.width;
		int y1=f.y-this.height;//上方点
		int y2=f.y+f.height;
		return this.x>x1&&this.x<x2&&this.y>y1&&this.y<y2;
	}
	
	public boolean hit2(Bullet1 b) {
		//this.想象为子弹，b想象为英雄机
		int x1=b.x-this.width;//左上角 
		int x2=b.x+b.width;
		int y1=b.y-this.height;//右下角
		int y2=b.y+b.height;
		return this.x>x1&&this.x<x2&&this.y>y1&&this.y<y2;
	}
	
	public boolean hit1(Bullet1 m) {
		//this.想象为子弹，m想象为子弹1
		int x1=m.x-this.width;//左侧点  //8-8
		int x2=m.x+m.width;//8+20
		int y1=m.y-this.height;//上方点
		int y2=m.y+m.height;
		return this.x>x1&&this.x<x2&&this.y>y1&&this.y<y2;
	}
	//改变飞行物状态为死的方法
	public void goDead() {
		state=DEAD;
	}
	//小敌机，大敌机，奖励机使用的构造
	public FlyingThing(int width,int height) {
		this.width=width;
		this.height=height;
		//所有敌机的y值都是负高度，因为出现在屏幕上方
		y=-height;
		Random ran=new Random();
		x=ran.nextInt(400-width);
	}
	//子弹，天空，英雄机构造
	public FlyingThing(int width,int height,int x,int y){
		this.width=width;
		this.height=height;
		this.x=x;
		this.y=y;
	}
	
	/*public FlyingThing() {
		//子类构造方法中不指定调用父类的哪个构造，那么默认调用父类的无参构造
	}
	public FlyingThing(int width,int height,int x,int y) {
		this.width=width;//this.width表示自己属性的宽度，本来没有赋值
		this.height=height;//多用在构造方法中
		this.x=x;
		this.y=y;
	}*/
	//编写方法输出小敌机信息
	public void show(){
		System.out.println("宽"+width+"高"+height);
		System.out.println("x坐标"+x+"y坐标"+y);
	}
	public static BufferedImage readImage(String fileName) {
		try {
			//根据文件名，将图片获取并赋值给img
			BufferedImage img=ImageIO.read(FlyingThing.class.getResource(fileName));
		return img;	
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
}

