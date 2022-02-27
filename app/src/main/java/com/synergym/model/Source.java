package com.synergym.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Source implements Serializable {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private Object id;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(Object id){
		this.id = id;
	}

	public Object getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"Source{" + 
			"name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}