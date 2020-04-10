package cn.tedu.shoot;
//击中时获得奖励接口
public interface Award {
//奖励常量
	int LIFE=0;
	int DOUBLE_FIRE=1;
	//获得的奖励
	public int awardType();
}
