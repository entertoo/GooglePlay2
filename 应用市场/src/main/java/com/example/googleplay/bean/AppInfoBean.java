package com.example.googleplay.bean;

import java.util.List;

public class AppInfoBean {

    /*
		des	产品介绍：google市场app测试。
		downloadUrl	app/com.itheima.www/com.itheima.www
		iconUrl	app/com.itheima.www/icon.jpg
		id	1525489
		name	黑马程序员
		packageName	com.itheima.www
		size	91767
		stars	5
	 */
    public String des;
    public String downloadUrl;
    public String iconUrl;
    public long id;
    public String name;
    public String packageName;
    public long size;
    public float stars;

    //详细页面添加的属性
    public String author;    //应用作者
    public String date;    //应用发布日期
    public String downloadNum;    //应用下载量
    public String version;    //应用版本

    public List<SafeInfoBean> safe;    //安全信息
    public List<String> screen;    //Array

    public class SafeInfoBean {
        public String safeDes;    //已通过安智市场安全检测，请放心使用
        public int safeDesColor;    //0
        public String safeDesUrl;    //app/com.itheima.www/safeDesUrl0.jpg
        public String safeUrl;    //app/com.itheima.www/safeIcon0.jpg

        @Override
        public String toString() {
            return "SafeInfoBean{" +
                    "safeDes='" + safeDes + '\'' +
                    ", safeDesColor=" + safeDesColor +
                    ", safeDesUrl='" + safeDesUrl + '\'' +
                    ", safeUrl='" + safeUrl + '\'' +
                    '}';
        }
    }
}
