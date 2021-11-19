package com.liyan.cocos_standard.bean;

import androidx.annotation.Keep;

import java.io.Serializable;

/*
 * @date: 2020/12/31 10:18
 * @author: 庄宏岩
 * @description: 用户实体类
 */
@Keep
public final class LYUserInfo implements Serializable{
    public static int user_status_normal = 1;//正常
    public static int user_status_freeze = 2;//冻结
    public String token = "";//用户标识
    public String user_code = "";//邀请码
    public String user_id = ""; //用户id
    public String user_name = "";//用户昵称
    public String avatar = ""; //头像地址
    public String openid;//是否绑定微信
    public String unionid; //是否绑定微信
    public int signDay = 0; //连续签到的天数
    public int signStatus = 0; //是否签到
    public int coin = 0; //当前金币数
    public String totalCashed;//累计提现
    public int todayCoin = 0; //当天金币数
    public boolean isLogin; //是否已登录
    public boolean isLogout; //是否退出登录
    public int coinRate = 10000; //汇率
    public int status = 1; //用户的状态 1=正常 2=封禁
    public boolean isWithdrawals;//是否已提现过
    public int force_active; //TODO 等于1代表提现做激活任务，激活任务分两种，判断user_location_allowed是否等于true，如果等于true，就用激励视频作为下载任务，如果是false，就用珊瑚广告作为下载任务
    public int extra_withdrawals;//TODO 大于0代表已经做过提现激活任务
    public boolean user_location_allowed; //TODO false: 代表为北上广深的用户，需要谨慎对待，true: 代表其他地区用户，可以适当做一些引导
    public boolean extra_withdrawals_used;//TODO true代表提现解锁已使用，如果已使用，需要再次做解锁激活任务，false代表未使用，可直接提现
    public String user_medal; //用户质量分等级
    public int withdrawals_count;//当天提现次数
    public int user_money_count;//当天现金提现次数
    public int withdrawals_limit;//当天剩余提现次数
    public int video_coin;//视频币数量
    public String game_info; //游戏数据
    public int user_invite_total;//今日邀请
    public int user_invite_hold;//等待邀请
    public int user_invite_success;//邀请成功
    public int user_invite_reward;//邀请奖励
    public int user_level; //用户等级，用户每升一级增加1%的金币加成
    public int user_level_score; //用户当前等级的进度
    public int user_next_level_score; //用户下一等级的进度
    public String create_time; //用户注册时间
    public String money; //奖励金
    public boolean user_invited;//是否已填写邀请码
    public String shareUrl = "https://liyan-mobi-video.oss-cn-beijing.aliyuncs.com/follow-video/app/jbxxl.apk";
    public boolean hide_main_view = false; //不主动弹出主线
    public boolean user_cpl_status; //是否显示cpl游戏
    public int real_name_authorize_status; // 0:未实名，1：已实名，2：强制实名
    public int no_adult; // 0:未成年，1：已成年
}
