package com.example.a13797.gznews.method;

public class Forum {

    private int image;
    private String forum_name;
    private String forum_content;
    private int forum_image_content;
    private String forum_fenxiang;
    private String forum_pinglun;
    private String forum_zan;

    public Forum(int image, String forum_name, String forum_content,int forum_image_content, String forum_fenxiang, String forum_pinglun, String forum_zan) {
        this.image = image;
        this.forum_name = forum_name;
        this.forum_content = forum_content;
        this.forum_image_content = forum_image_content;
        this.forum_fenxiang = forum_fenxiang;
        this.forum_pinglun = forum_pinglun;
        this.forum_zan = forum_zan;

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getForum_name() {
        return forum_name;
    }

    public void setForum_name(String forum_name) {
        this.forum_name = forum_name;
    }

    public String getForum_content() {
        return forum_content;
    }

    public void setForum_content(String forum_content) {
        this.forum_content = forum_content;
    }

    public int getForum_image_content() {
        return forum_image_content;
    }

    public void setForum_image_content(int forum_image_content) {
        this.forum_image_content = forum_image_content;
    }

    public String getForum_fenxiang() {
        return forum_fenxiang;
    }

    public void setForum_fenxiang(String forum_fenxiang) {
        this.forum_fenxiang = forum_fenxiang;
    }

    public String getForum_pinglun() {
        return forum_pinglun;
    }

    public void setForum_pinglun(String forum_pinglun) {
        this.forum_pinglun = forum_pinglun;
    }

    public String getForum_zan() {
        return forum_zan;
    }

    public void setForum_zan(String forum_zan) {
        this.forum_zan = forum_zan;
    }
}
