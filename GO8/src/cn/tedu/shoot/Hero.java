package cn.tedu.shoot;

import java.awt.image.BufferedImage;

public class Hero extends FlyingThing{
	private static BufferedImage[] images;
	static {images=new BufferedImage[2];
	images[0]=readImage("hero0.png");
	images[1]=readImage("hero1.png");}
	//英雄机的生命值
	private int life;
	//英雄机的活力值
	private int doubleFire;
	public Hero() {
		
		super(97,139,152,400);
		this.life=30;
		this.doubleFire=100;
	}
	public void show(){
		super.show();
		System.out.println("生命值"+life);
		System.out.println("火力值"+ doubleFire);
	}
	//空实现
	public void step(){
	}
	int index=0;
		public BufferedImage getImage() {
			BufferedImage img=null;
			img=images[index%2];//只能0或1
			index++;//下一次获得另一张图
			return img;
		}
		//英雄机开炮的方法
		public Bullet[] shoot() {
			Bullet bs[]=null;
			//为了方便使用英雄机宽度四分之一
			//定义宽度
			int len=this.width/4;
			//英雄机开炮分单排和双排
			/*if(doubleFire>20) {
				//四
				bs=new Bullet[2][];
				bs[0]=new Bullet []{new Bullet(this.x,this.y-20),new Bullet(this.x+len,this.y-20),
						new Bullet(this.x+2*len,this.y-20),new Bullet(this.x+3*len,this.y-20),new Bullet(this.x+4*len,this.y-20)};
				bs[1]=new Bullet []{new Bullet(this.x,this.y-40),new Bullet(this.x+len,this.y-40),
						new Bullet(this.x+2*len,this.y-40),new Bullet(this.x+3*len,this.y-40),new Bullet(this.x+4*len,this.y-40)};
				doubleFire--;//减火力
			}else if(doubleFire>0){
				//双
				bs=new Bullet[2][];
				bs[0]=new Bullet[] {new Bullet(this.x+len,this.y-20),new Bullet(this.x+3*len,this.y-20)};
				bs[1]=new Bullet[]{new Bullet(this.x+len,this.y-40),new Bullet(this.x+3*len,this.y-40)};
				doubleFire--;//减火力
			}else {
				//单
				bs=new Bullet[2][];
				bs[0]=new Bullet[] {new Bullet(this.x+2*len,this.y-20)};
				bs[1]=new Bullet[] {new Bullet(this.x+2*len,this.y-40)};
			}*/
			if(doubleFire>10) {
				bs=new Bullet[5];
				bs[0]=new Bullet(this.x,this.y-20);//子弹的坐标
				bs[1]=new Bullet(this.x+len,this.y-20);
				bs[2]=new Bullet(this.x+2*len,this.y-20);
				bs[3]=new Bullet(this.x+3*len,this.y-20);
				bs[4]=new Bullet(this.x+4*len,this.y-20);
				doubleFire--;//减火力值 
			}else if(doubleFire>0) {
				bs=new Bullet[2];
				bs[0]=new Bullet(this.x+len,this.y-20);
				bs[1]=new Bullet(this.x+3*len,this.y-20);
				doubleFire--;
			}else{
				//单
				bs=new Bullet[1];
				bs[0]=new Bullet(this.x+2*len,this.y-20);
			}
			return bs;
		}
		//英雄机移动方法
		public void moveTo(int x,int y) {
			//英雄机中心x轴到鼠标x轴
			this.x=x-this.width/2;
			this.y=y-this.height/2;
		}
		//英雄机的生命值，增加英雄机的生命值和活力值
		public int getLife() {
			return this.life;
		}
		public void addLife() {
			this.life++;
		}
		public void addDoubleFire() {
			this.doubleFire+=10;
		}
		//减命
		public void subLife() {
			this.life--;
		}//清空火力值
		public void clearDoubleFire() {
			this.doubleFire=0;
		}
	}
