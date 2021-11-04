package com.student.Medium;

public class Subject
{
	private String subName,subTeacher,subUrl;
	private int imgIcon;
	
	public Subject(){}
	public Subject(int imgIcon,String subName,String subTeacher,String subUrl){
		this.imgIcon=imgIcon;
		this.subName=subName;
		this.subTeacher=subTeacher;
		this.subUrl=subUrl;
	}
	public void setImg(int imgIcon){
		this.imgIcon=imgIcon;
	}
	public int getImg(){
		return imgIcon;
	}
	public void setSubName(String subName){
		this.subName=subName;
	}
	public String getSubName(){
		return subName;
	}
	public void subTeacher(String subTeacher){
		this.subTeacher=subTeacher;
	}
	public String getSubTeacher(){
		return subTeacher;
	}
	public void setSubUrl(String subUrl){
		this.subUrl=subUrl;
	}
	public String getSubUrl(){
		return subUrl;
	}
}
