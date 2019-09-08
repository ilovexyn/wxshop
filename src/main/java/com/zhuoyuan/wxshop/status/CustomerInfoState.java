package com.zhuoyuan.wxshop.status;

public class CustomerInfoState {

    public static final Integer fristGrade = 1;//散客（二维码进入） 1的上级默认为管理员
    public static final Integer secondGrade = 2;//通过分享产品进来 上级为 分享者
    public static final Integer thirdGrade = 3;//线下介绍 手动关联
    public static final Integer fouthGrade = 4;//会员（申请）4的上级默认为管理员
    public static final Integer fifthGrade = 5;//区级
    public static final Integer sixthGrade = 6;//市
    public static final Integer seventhGrade = 7;//管理员
}
