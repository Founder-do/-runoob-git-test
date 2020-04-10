package cn.tedu.shoot;

import java.awt.image.BufferedImage;

public class Airplane extends FlyingThing implements Score{
	/*public Airplane() {
		//作用是可以new实例化时不给参数
	}*/
	//保存小敌机相关图片的数组
	private static BufferedImage[] images;
	static {
		images=new BufferedImage[6];
		images[0]=readImage("airplane0.png");
		//爆炸图
		for(int i=1;i<images.length;i++) {
			images[i]=readImage("bom"+i+".png");
		}
	}
	private int life;
	private int doubleFire;
	private int Step;
	public Airplane(){
		//this.width表示自己属性的宽度，本来没有赋值
		//多用在构造方法中
		super(48,50);
		this.life=2;
		this.Step=3;
		this.doubleFire=10;
	}
	public void show(){
		super.show();
		System.out.println("生命值"+life);
		System.out.println("火力值"+ doubleFire);
		System.out.println("速度"+Step);
	}
	public void step(){
		//小敌机向下移动
		y+=Step;
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
	public Bullet1[] shoot(){
		Bullet1 bs[]=null;
		int len=this.width/4;
		if(doubleFire>0) {
			bs=new Bullet1[4];
			bs[0]=new Bullet1(this.x,this.y+this.height+20);
			bs[1]=new Bullet1(this.x+len,this.y+this.height+20);
			bs[2]=new Bullet1(this.x+3*len,this.y+this.height+20);
			bs[3]=new Bullet1(this.x+4*len,this.y+this.height+20);
			doubleFire--;
		}else{
			bs=new Bullet1[1];
			bs[0]=new Bullet1(this.x+2*len,this.y+this.height+20);
		}
		return bs;
	}
	public void subLife() {
		this.life--;
	}
	public int getLife() {
		return this.life;
	}
	public void clearDoubleFire() {
		this.doubleFire=0;
	}
	public int getScore() {
		// 击中小敌机得一分
		return 1;
	}
	}