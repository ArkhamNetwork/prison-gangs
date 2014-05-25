package com.mydeblob;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Gang {
	private FileManager f = FileManager.getFileManager();

	  private ArrayList<String> members = new ArrayList<String>(); 
	  private ArrayList<String> trusted = new ArrayList<String>(); 
	  private ArrayList<String> officers = new ArrayList<String>(); 
	  private ArrayList<String> leaders = new ArrayList<String>();
	  private String owner = null;
	  private String name;

	public Gang(String name){
	    this.name = name;
	    for(String s:f.getGangConfig().getStringList("gangs." + name + ".members")){
	    	members.add(s);
	    }
	    for(String s:f.getGangConfig().getStringList("gangs." + name + ".trusted")){
	    	trusted.add(s);
	    }
	    for(String s:f.getGangConfig().getStringList("gangs." + name + ".officers")){
	    	officers.add(s);
	    }
	    for(String s:f.getGangConfig().getStringList("gangs." + name + ".leaders")){
	    	leaders.add(s);
	    }
	    owner = f.getGangConfig().getString("gangs." + name + ".owner");
	  }
	  public String getName(){
	    return this.name;
	  }
	  public ArrayList<String> getMembers(){
	    return this.members;
	  }
	  public void addMember(Player p){
	    this.members.add(p.getName());
	    f.getGangConfig().set("gangs." + this.name + ".members", members);
	    f.saveGangConfig();
	  }
	  public void removeMember(Player p){
	    this.members.remove(p.getName());
	    f.getGangConfig().set("gangs." + this.name + ".members", members);
	    f.saveGangConfig();
	  }
	  public ArrayList<String> getTrusted(){
	    return this.trusted;
	  }
	  public void addTrusted(Player p) {
	    this.trusted.add(p.getName());
	    f.getGangConfig().set("gangs." + this.name + ".trusted", trusted);
	    f.saveGangConfig();
	  }
	  public void removeTrusted(Player p) {
	    this.trusted.remove(p.getName());
	    f.getGangConfig().set("gangs." + this.name + ".trusted", trusted);
	    f.saveGangConfig();
	  }
	  public ArrayList<String> getOfficers(){
		  return this.officers;
	  }
	  public void addOfficer(Player p){
		  this.officers.add(p.getName());
		  f.getGangConfig().set("gangs." + this.name + ".officers", officers);
		  f.saveGangConfig();
	  }
	  public void removeOfficer(Player p){
		  this.officers.remove(p.getName());
		  f.getGangConfig().set("gangs." + this.name + ".officers", officers);
		  f.saveGangConfig();
	  }
	  public ArrayList<String> getLeaders(){
		  return this.leaders;
	  }
	  public void addLeader(Player p){
		  this.leaders.add(p.getName());
		  f.getGangConfig().set("gangs." + this.name + ".leaders", leaders);
		  f.saveGangConfig();
	  }
	  public void removeLeader(Player p){
		  this.leaders.remove(p.getName());
		  f.getGangConfig().set("gangs." + this.name + ".leaders", leaders);
		  f.saveGangConfig();
	  }
	  public String getOwner(){
		  return this.owner;
	  }
	  public void setOwner(Player p){
		  this.owner = p.getName();
		  f.getGangConfig().set("gangs." + this.name + ".owner", owner);
		  f.saveGangConfig();
	  }
	  
//	public void msg(Gang c, String message){
//		  if(GangManager.getInstance().getClan(c.getName()) == null) return;
//		  for(String p : c.getMembers()){
//			 Player player = Bukkit.getServer().getPlayer(p);
//			 if(!(player == null)){
//				 player.sendMessage(message);
//			 }
//		  }
//		  for(String p : c.getTrusted()){
//				 Player player = Bukkit.getServer().getPlayer(p);
//				 if(!(player == null)){
//					 player.sendMessage(message);
//				 }
//			  }
//		  for(String p : c.getOfficers()){
//				 Player player = Bukkit.getServer().getPlayer(p);
//				 if(!(player == null)){
//					 player.sendMessage(message);
//				 }
//			  }
//		  for(String p : c.getLeaders()){
//				 Player player = Bukkit.getServer().getPlayer(p);
//				 if(!(player == null)){
//					 player.sendMessage(message);
//				 }
//			  }
//	  }
}