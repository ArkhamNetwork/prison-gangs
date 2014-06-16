package com.mydeblob.prisongangs;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Gang {
	private static FileManager f = FileManager.getFileManager();

	  private ArrayList<String> members = new ArrayList<String>(); 
	  private ArrayList<String> trusted = new ArrayList<String>(); 
	  private ArrayList<String> officers = new ArrayList<String>(); 
	  private ArrayList<String> leaders = new ArrayList<String>();
	  private ArrayList<String> membersUuid = new ArrayList<String>(); 
	  private ArrayList<String> trustedUuid = new ArrayList<String>(); 
	  private ArrayList<String> officersUuid = new ArrayList<String>(); 
	  private ArrayList<String> leadersUuid = new ArrayList<String>();
	  private static ArrayList<Gang> gangs = new ArrayList<Gang>();
	  private String owner = null;
	  private String name;

	public Gang(String name){
	    this.name = name;
	    for(String s:f.getGangConfig().getStringList("gangs." + name + ".members")){
	    	membersUuid.add(s);
	    	members.add(Bukkit.getPlayer(UUID.fromString(s)).getName());
	    }
	    for(String s:f.getGangConfig().getStringList("gangs." + name + ".trusted")){
	    	trustedUuid.add(s);
	    	trusted.add(Bukkit.getPlayer(UUID.fromString(s)).getName());
	    }
	    for(String s:f.getGangConfig().getStringList("gangs." + name + ".officers")){
	    	officersUuid.add(s);
	    	officers.add(Bukkit.getPlayer(UUID.fromString(s)).getName());
	    }
	    for(String s:f.getGangConfig().getStringList("gangs." + name + ".leaders")){
	    	leadersUuid.add(s);
	    	leaders.add(Bukkit.getPlayer(UUID.fromString(s)).getName());
	    }
	    owner = Bukkit.getPlayer(UUID.fromString(f.getGangConfig().getString("gangs." + name + ".owner"))).getName();
	    gangs.add(this);
	  }
	  public String getName(){
	    return this.name;
	  }
	  public ArrayList<String> getMembers(){
	    return this.members;
	  }
	  public void addMember(Player p){
	    this.membersUuid.add(p.getUniqueId().toString());
	    f.getGangConfig().set("gangs." + this.name + ".members", membersUuid);
	    f.saveGangConfig();
	  }
	  public void removeMember(Player p){
	    this.membersUuid.remove(p.getUniqueId().toString());
	    f.getGangConfig().set("gangs." + this.name + ".members", membersUuid);
	    f.saveGangConfig();
	  }
	  public ArrayList<String> getTrusted(){
	    return this.trusted;
	  }
	  public void addTrusted(Player p) {
	    this.trustedUuid.add(p.getUniqueId().toString());
	    f.getGangConfig().set("gangs." + this.name + ".trusted", trustedUuid);
	    f.saveGangConfig();
	  }
	  public void removeTrusted(Player p) {
		  this.trustedUuid.remove(p.getUniqueId().toString());
		  f.getGangConfig().set("gangs." + this.name + ".trusted", trustedUuid);
		  f.saveGangConfig();
	  }
	  public ArrayList<String> getOfficers(){
		  return this.officers;
	  }
	  public void addOfficer(Player p){
		  this.officersUuid.add(p.getUniqueId().toString());
		  f.getGangConfig().set("gangs." + this.name + ".officers", officersUuid);
		  f.saveGangConfig();
	  }
	  public void removeOfficer(Player p){
		  this.officersUuid.remove(p.getUniqueId().toString());
		  f.getGangConfig().set("gangs." + this.name + ".officers", officersUuid);
		  f.saveGangConfig();
	  }
	  public ArrayList<String> getLeaders(){
		  return this.leaders;
	  }
	  public void addLeader(Player p){
		  this.leadersUuid.add(p.getUniqueId().toString());
		  f.getGangConfig().set("gangs." + this.name + ".leaders", leadersUuid);
		  f.saveGangConfig();
	  }
	  public void removeLeader(Player p){
		  this.leadersUuid.remove(p.getUniqueId().toString());
		  f.getGangConfig().set("gangs." + this.name + ".leaders", leadersUuid);
		  f.saveGangConfig();
	  }
	  public String getOwner(){
		  return this.owner;
	  }
	  public void setOwner(Player p){
		  this.owner = p.getName();
		  f.getGangConfig().set("gangs." + this.name + ".owner", p.getUniqueId().toString());
		  f.saveGangConfig();
	  }
	  
	  public ArrayList<String> getAllPlayers(){
		  ArrayList<String> allPlayers = new ArrayList<String>();
		  for(String s:getMembers()){
			  allPlayers.add(s);
		  }
		  for(String s:getTrusted()){
			  allPlayers.add(s);
		  }
		  for(String s:getOfficers()){
			  allPlayers.add(s);
		  }
		  for(String s:getLeaders()){
			  allPlayers.add(s);
		  }
		  allPlayers.add(getOwner());
		  return allPlayers;
	  }
		public static void loadGangs(){
		    Gang.gangs.clear();
		    for(String s:f.getGangConfig().getStringList("gang-names")){
		    	Gang.gangs.add(new Gang(s));
		    }
		  }

		  public static ArrayList<Gang> getGangs(){
		    return Gang.gangs;
		  }
	  public static void removeGang(Gang g){
		  if(Gang.gangs.contains(g)){
			  Gang.gangs.remove(g);
		  }
	  }
	  
	public static Ranks getPlayerRank(String playerName, Gang g){
		  if(g.getMembers().contains(playerName)){
			  return Ranks.MEMBER;
		  }else if(g.getTrusted().contains(playerName)){
			  return Ranks.TRUSTED;
		  }else if(g.getOfficers().contains(playerName)){
			  return Ranks.OFFICER;
		  }else if(g.getLeaders().contains(playerName)){
			  return Ranks.LEADER;
		  }else if(g.getOwner().equalsIgnoreCase(playerName)){
			  return Ranks.OWNER;
		  }else{
			  return null;
		  }
	  }
}
