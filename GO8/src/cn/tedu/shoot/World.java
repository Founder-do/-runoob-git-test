package cn.tedu.shoot;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


import javax.swing.JFrame;
import javax.swing.JPanel;
//继承JPanel表示world类是一个窗口
public class World extends JPanel{
	public static final int WIDTH=400;
	public static final int HEIGHT=700;
	private int score;//分数
	//定义游戏状态常量
	public static final int START=0;
	public static final int RUNNING=1;
	public static final int PAUSE=2;
	public static final int GAME_OVER=3;
	//设置当前的游戏状态
	private int state=START;
	//声明三个状态对应的三个图片
	private static BufferedImage startImg;
	private static BufferedImage pauseImg;
	private static BufferedImage gameOverImg;
	static {
		startImg=FlyingThing.readImage("start.png");
		pauseImg=FlyingThing.readImage("pause.png");
		gameOverImg=FlyingThing.readImage("gameover.png");
	}
	
	 /*FlyingThing enemy[][]= {{new Airplane(),new Airplane(),new Airplane()},
			{new BigAirplane(),new BigAirplane(),new BigAirplane()},
			{new Bee(),new Bee(),new Bee()}};*/
	FlyingThing[]enemy= {
		
	};
	Hero hero=new Hero();
	Sky sky=new Sky();
	Bullet[]bullets={};
	Bullet1[]bulletss={};
	public void start() {
		//编写鼠标的监听器
		MouseAdapter l=new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				if(state==RUNNING) 
				hero.moveTo(e.getX(), e.getY());
			}
			//鼠标点击时的状态切换
			public void mouseClicked(MouseEvent e) {
				switch(state) {   
				case START:state=RUNNING;//如果开始状态   进入运行
				break;
				case GAME_OVER:state=START;
				//重置游戏
				score=0;
				hero=new Hero();
				sky=new Sky();
				bullets=new Bullet[0];
				bulletss=new Bullet1[0];
				enemy=new FlyingThing[0];
				
				
				
				break;
				}
			}
			//鼠标移入状态时切换
			public void mouseEntered(MouseEvent e) {
				if(state==PAUSE)
					state=RUNNING;
			}
			//鼠标移出状态时切换
			public void mouseExited(MouseEvent e) {
				if(state==RUNNING)
					state=PAUSE;
			}
		};
		//绑定鼠标的移动事件
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		int interval=25;//鼠标移动
		Timer timer=new Timer();
		TimerTask task=new TimerTask() {
			public void run() {
				if(state==RUNNING) {
				enterAction();
				moveAction();
				shootAction();
				shootAction1();
				outOfboundsAction();
				bulletHitAction();
				bulletHitAction1();
				heroHitAction();
				heroHitAction1();
				gameOverAction();
				gameOverAction1();
				}
				repaint();
				System.out.println(enemy.length);
			}
		};//启动记时器
		timer.schedule(task,100,50);
	}
	//生命值用完结束游戏
	public void gameOverAction() {
		if(hero.getLife()<=0) {
			state=GAME_OVER;
		}
	}
	public void gameOverAction1() {
		//Airplane d=(Airplane)enemy[i];
		for(int i=0;i<enemy.length;i++) {
			if(enemy[i]instanceof Airplane) {
		if(((Airplane)enemy[i]).getLife()<=0) {
			//enemy[i].goDead();
			((Airplane)enemy[i]).state=((Airplane)enemy[i]).DEAD;
		}}}
	}
	
	//判断英雄机和敌机碰撞方法
	public void heroHitAction() {
		for(int i=0;i<enemy.length;i++) {
			FlyingThing f=enemy[i];
			if(f.isLife()&&hero.hit(f)) {
				//撞死敌机
				if(enemy[i]instanceof Airplane) {
					((Airplane)enemy[i]).clearDoubleFire();
					((Airplane)enemy[i]).subLife();
				}else {
				f.goDead();
				}//英雄减命，火力
				hero.subLife();
				hero.clearDoubleFire();
				}
		}
	}
	//子弹，敌机碰撞方法
	public void bulletHitAction() {
		for(int i=0;i<bullets.length;i++) {
			Bullet b=bullets[i];//提取子弹
			//遍历所有敌机
			for(int j=0;j<enemy.length;j++) {
				FlyingThing f=enemy[j];
				//判断子弹是否击中敌机
				if(b.isLife()&&f.isLife()&&b.hit(f)) {
					b.goDead();
					if(enemy[j]instanceof Airplane) {
						//((Airplane)enemy[j]).clearDoubleFire();
						((Airplane)enemy[j]).subLife();
					}else {
					f.goDead();}
					//判断敌机是不是得分
					if(f instanceof Score) {
						Score s=(Score)f;
						score+=s.getScore();
					}
					if(f instanceof Award) {
						Award a=(Award)f;
						//获得这架奖励机的奖励值
						int num=a.awardType();
						switch(num) {
						case Award.LIFE:hero.addLife(); break;
						case Award.DOUBLE_FIRE: hero.addDoubleFire();break;
						}
					}
				}
			}
		}
	}
	//子弹与子弹1碰撞方法
		public void bulletHitAction1() {
			for(int i=0;i<bullets.length;i++) {
				Bullet a=bullets[i];
				//遍历所有子弹1
				for(int j=0;j<bulletss.length;j++) {
					Bullet1 b=bulletss[j];//提取子弹
					//判断子弹1是否击中子弹
					if(a.isLife()&&b.isLife()&&a.hit1(b)) {
						a.goDead();
						b.goDead();
					}
				}
			}
		}
		//子弹与英雄机碰撞方法
				public void heroHitAction1() {
						for(int j=0;j<bulletss.length;j++) {
							Bullet1 b=bulletss[j];//提取子弹
							//判断子弹1是否击中英雄机
							if(b.isLife()&&hero.hit2(b)){
								hero.subLife();
								hero.clearDoubleFire();
								b.goDead();
							}
						}
					}
	//检测出界的方法
	public void outOfboundsAction() {
	//没有出界的元素下标，同时保存没出界元素的数量
		/*FlyingThing[][] fs=new FlyingThing[enemy.length][];
		//定义保存为出界元素的数组
		//遍历所有敌机，检测是否出界
		for(int i=0;i<enemy.length;i++){
			int m=enemy[i].length;
			enemy[i]=new FlyingThing[m];
			for(int j=0;j<enemy[i].length;j++) {
			FlyingThing f=new Airplane();
			if(f.isOutOfBounds()&&f.isRemove()){
			m--;
			}
		}
		}*/
		int index=0;
        FlyingThing[]fs =new FlyingThing[enemy.length];
for(int i=0;i<enemy.length;i++) {
FlyingThing f=enemy[i];
if(!f.isOutOfBounds()&&!f.isRemove()) {
   fs[index]=f;
   index++;
}
}enemy=Arrays.copyOf(fs, index);
		 index=0;
		Bullet[]bs=new Bullet[bullets.length];//数组
		for(int i=0;i<bullets.length;i++) {
			Bullet b=bullets[i];//调用
			if(!b.isOutOfBounds()&&!b.isRemove()) {
				bs[index]=b;//放到新数组
				index++;//下标加1
			}
}bullets=Arrays.copyOf(bs, index);
	
	index=0;
	Bullet1[]bss=new Bullet1[bulletss.length];//数组
	for(int i=0;i<bulletss.length;i++) {
		Bullet1 b=bulletss[i];//调用
		if(!b.isOutOfBounds()&&!b.isRemove()) {
			bss[index]=b;//放到新数组
			index++;//下标加1
		}
}bulletss=Arrays.copyOf(bss, index);//缩容完毕
}
	
	//子弹进场
	int shootIndex=1;
	public void shootAction(){
		if(shootIndex%20==0) {
		//调用英雄机得开炮方法
		//Bullet[][] bs=hero.shoot();
		Bullet[]bs=hero.shoot();
		//对bullets数组进行扩容
		bullets=Arrays.copyOf(bullets, bullets.length+bs.length);
		//将英雄机发射得新炮弹
		//放到扩容后数组得位置
		System.arraycopy(bs, 0, bullets, bullets.length-bs.length,bs.length);
	}shootIndex++;}
	
	//子弹1进场
	int shootIndex1=1;
	public void shootAction1(){
		if(shootIndex1%20==0) {
			for(int i=0;i<enemy.length;i++) {
				if(enemy[i]instanceof Airplane) {
		Bullet1[]bs=((Airplane)enemy[i]).shoot();
		//对bulletss数组进行扩容
		bulletss=Arrays.copyOf(bulletss, bulletss.length+bs.length);
		//将英雄机发射得新炮弹
		//放到扩容后数组得位置
		System.arraycopy(bs, 0, bulletss, bulletss.length-bs.length,bs.length);
	}}}shootIndex1++;}
	
	
	//敌机进场
	int enterIndex=1;
	public void enterAction() {
		if(enterIndex%20==0) {
		//生成一架敌机
		FlyingThing fo=nextEnemy();
		enemy=Arrays.copyOf(enemy, enemy.length+1);
		//enemy[enemy.length-1]=new FlyingThing[]{fo,fo,fo};
		enemy[enemy.length-1]=fo;
	}enterIndex++;}
	//飞行物移动的方法
	public void moveAction() {
		sky.step();//调用抽象方法  
		for(int i=0;i<enemy.length;i++) {
			if(i%2==0) {
			enemy[i].step();}else {
			if(enemy[i]instanceof BigAirplane) { 
				((BigAirplane)enemy[i]).step1();}
			else {enemy[i].step();}
		} }
		for(int i=0;i<bullets.length;i++) {
			bullets[i].step();
	}for(int i=0;i<bulletss.length;i++) {
		bulletss[i].step();
	}
		} 
	//随机产生敌机
	public FlyingThing nextEnemy() {
		Random ran=new Random();
		FlyingThing fo=null;
		int num=ran.nextInt(100);
		if(num<40) {
			fo=new Airplane();
		}else if(num<90) {
			fo=new BigAirplane();
		}else {fo=new Bee();}
		return fo;
	}
	//重写了JPanel中的方法
	//方法名必须叫paint
	public void paint(Graphics g) {
		//先画背景，在画其他
		sky.paintThing(g);
		hero.paintThing(g);
		for(int i=0;i<bullets.length;i++) {
			bullets[i].paintThing(g);
		}
		for(int i=0;i<bulletss.length;i++) {
			bulletss[i].paintThing(g);
		}
		for(int i=0;i<enemy.length;i++) {
			enemy[i].paintThing(g);
			}
		//显示分数和生命值
		g.drawString("SCORE:"+score,20, 20);
		g.drawString("LIFE:"+hero.getLife(),20, 40);
		//根据游戏状态绘制状态图片
		switch(state) {
		case START:g.drawImage(startImg,0,0,null);
		break;
		case PAUSE:g.drawImage(pauseImg,0,0,null);
		break;
		case GAME_OVER:g.drawImage(gameOverImg,0,0,null);
		break;
		}
		}
	
	
	
	public static void main(String[] args) {
		World w=new World();
		//实例化一个java窗口
		JFrame f=new JFrame("飞机大战");
		//将w设置到窗口中
		f.add(w);
		//设置窗口的宽和高
		f.setSize(WIDTH,HEIGHT);
		//设置窗口关闭时程序结束
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置窗口居中
		f.setLocationRelativeTo(null);
		//显示窗口.自动调用paint
		f.setVisible(true);
		
		w.start();
		}
	}

