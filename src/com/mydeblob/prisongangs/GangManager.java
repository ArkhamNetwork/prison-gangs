package com.mydeblob.prisongangs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GangManager {
	private static GangManager instance = new GangManager();
	private FileManager f = FileManager.getFileManager();
	public static GangManager getGangManager(){
		return instance;
	}

	public Gang getGangByName(String name){
		for (Gang g: Gang.getGangs()){
			if (g.getName().equalsIgnoreCase(name)){
				return g;
			}
		}
		return null;
	}

	public boolean isInGang(Player p){
		if(getGangWithPlayer(p) == null){
			return false;
		}else{
			return true;
		}
	}
	public Gang getGangWithPlayer(Player p){
		for (Gang g: Gang.getGangs()){
			if(g.getAllPlayers().contains(p.getName())){
				return g;
			}
		}
		return null;
	}
	@SuppressWarnings("deprecation") //getPlayerExact is deprecated due to UUID's
	public void promotePlayer(Player sender, Player target, Gang gang){
		if(gang.getOfficers().contains(sender.getName()) || sender.hasPermission("gangs.admin") || sender.isOp()){
			if(gang.getMembers().contains(target.getName())){
				gang.addTrusted(target);
				gang.removeMember(target);
				sender.sendMessage(Lang.PREFIX.toString() + Lang.SENDER_SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.TRUSTED));
				target.sendMessage(Lang.PREFIX.toString() + Lang.TARGET_SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.TRUSTED));
				messageGang(gang, Lang.SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.TRUSTED));
				return;
			}else{
				sender.sendMessage(Lang.PREFIX.toString() + Lang.CANT_PROMOTE.toString(sender, target, gang, Ranks.TRUSTED));
				return;
			}
		}if(gang.getLeaders().contains(sender.getName())){
			if(gang.getTrusted().contains(target.getName())){
				gang.addOfficer(target);
				gang.removeTrusted(target);
				sender.sendMessage(Lang.PREFIX.toString() + Lang.SENDER_SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.OFFICER));
				target.sendMessage(Lang.PREFIX.toString() + Lang.TARGET_SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.OFFICER));
				messageGang(gang, Lang.SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.OFFICER));
				return;
			}else{
				sender.sendMessage(Lang.PREFIX.toString() + Lang.CANT_PROMOTE.toString(sender, target, gang, Ranks.OFFICER));
				return;
			}
		}if(gang.getOwner().equalsIgnoreCase(sender.getName())){
			if(gang.getOfficers().contains(target.getName())){
				gang.addLeader(target);
				gang.removeOfficer(target);
				sender.sendMessage(Lang.PREFIX.toString() + Lang.SENDER_SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.LEADER));
				target.sendMessage(Lang.PREFIX.toString() + Lang.TARGET_SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.LEADER));
				messageGang(gang, Lang.SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.LEADER));
				return;
			}else{
				sender.sendMessage(Lang.PREFIX.toString() + Lang.CANT_PROMOTE_OWNER.toString(sender, target, gang, Ranks.LEADER));
				return;
			}
		}if(sender.hasPermission("gang.admin") || sender.isOp()){
			if(gang.getMembers().contains(target.getName())){
				gang.addTrusted(target);
				gang.removeMember(target);
				sender.sendMessage(Lang.PREFIX.toString() + Lang.SENDER_SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.TRUSTED));
				target.sendMessage(Lang.PREFIX.toString() + Lang.TARGET_SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.TRUSTED));
				messageGang(gang, Lang.SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.TRUSTED));
				return;
			}if(gang.getTrusted().contains(target.getName())){
				gang.addOfficer(target);
				gang.removeTrusted(target);
				sender.sendMessage(Lang.PREFIX.toString() + Lang.SENDER_SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.OFFICER));
				target.sendMessage(Lang.PREFIX.toString() + Lang.TARGET_SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.OFFICER));
				messageGang(gang, Lang.SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.OFFICER));
				return;
			}if(gang.getOfficers().contains(target.getName())){
				gang.addLeader(target);
				gang.removeOfficer(target);
				sender.sendMessage(Lang.PREFIX.toString() + Lang.SENDER_SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.LEADER));
				target.sendMessage(Lang.PREFIX.toString() + Lang.TARGET_SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.LEADER));
				messageGang(gang, Lang.SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.LEADER));
				return;
			}if(gang.getLeaders().contains(target.getName())){
				String oldOwner = gang.getOwner();
				gang.setOwner(target);
				gang.addLeader(Bukkit.getPlayerExact(oldOwner));
				gang.removeOfficer(target);
				sender.sendMessage(Lang.PREFIX.toString() + Lang.SENDER_SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.OWNER));
				target.sendMessage(Lang.PREFIX.toString() + Lang.TARGET_SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.OWNER));
				messageGang(gang, Lang.SUCCESS_PROMOTE.toString(sender, target, gang, Ranks.OWNER));
				return;
			}
		}
		if(gang.getMembers().contains(sender.getName())){
			sender.sendMessage(Lang.PREFIX.toString() + Lang.NO_PERMS_PROMOTE.toString(sender, target, gang, Ranks.MEMBER));
			return;
		}else{
			sender.sendMessage(Lang.PREFIX.toString() + Lang.NO_PERMS_PROMOTE.toString(sender, target, gang, Ranks.TRUSTED));
			return;
		}
	}

	@SuppressWarnings("deprecation") //getPlayerExact is deprecated due to UUID's
	public void messageGang(Gang g, String message){
		for(String s:g.getAllPlayers()){
			Player p = Bukkit.getPlayerExact(s);
			p.sendMessage(message);
		}
	}

	public void demotePlayer(Player p, Gang g){

	}

	@SuppressWarnings("deprecation")
	public void kickPlayer(Player sender, Player target, Gang gang){ 
		if(gang.getOfficers().contains(sender.getName())){
			if(gang.getMembers().contains(target.getName()) || gang.getTrusted().contains(target.getName())){
				Ranks r = Gang.getPlayerRank(target.getName(), gang);
				if(r == Ranks.MEMBER){
					gang.removeMember(target);
					target.sendMessage(Lang.TARGET_SUCCESS_KICK.toString(sender, target, gang, Ranks.MEMBER));
					sender.sendMessage(Lang.SENDER_SUCCESS_KICK.toString(sender, target, gang, Ranks.MEMBER));
					messageGang(gang, Lang.SUCCESS_KICK.toString(sender, target, gang, Ranks.MEMBER));
					return;
				}else if(r == Ranks.TRUSTED){
					gang.removeTrusted(target);
					target.sendMessage(Lang.TARGET_SUCCESS_KICK.toString(sender, target, gang, Ranks.TRUSTED));
					sender.sendMessage(Lang.SENDER_SUCCESS_KICK.toString(sender, target, gang, Ranks.TRUSTED));
					messageGang(gang, Lang.SUCCESS_KICK.toString(sender, target, gang, Ranks.TRUSTED));
					return;
				}
			}else{
				sender.sendMessage(Lang.PREFIX.toString() + Lang.CANT_KICK.toString(sender, target, gang, Gang.getPlayerRank(target.getName(), gang)));
				return;
			}
		}if(gang.getLeaders().contains(sender.getName())){
			if(!gang.getLeaders().contains(target.getName()) || !(gang.getOwner().equals(target.getName()))){
				Ranks r = Gang.getPlayerRank(target.getName(), gang);
				if(r == Ranks.MEMBER){
					gang.removeMember(target);
					target.sendMessage(Lang.TARGET_SUCCESS_KICK.toString(sender, target, gang, Ranks.MEMBER));
					sender.sendMessage(Lang.SENDER_SUCCESS_KICK.toString(sender, target, gang, Ranks.MEMBER));
					messageGang(gang, Lang.SUCCESS_KICK.toString(sender, target, gang, Ranks.MEMBER));
					return;
				}else if(r == Ranks.TRUSTED){
					gang.removeTrusted(target);
					target.sendMessage(Lang.TARGET_SUCCESS_KICK.toString(sender, target, gang, Ranks.TRUSTED));
					sender.sendMessage(Lang.SENDER_SUCCESS_KICK.toString(sender, target, gang, Ranks.TRUSTED));
					messageGang(gang, Lang.SUCCESS_KICK.toString(sender, target, gang, Ranks.TRUSTED));
					return;
				}else if(r == Ranks.OFFICER){
					gang.removeOfficer(target);
					target.sendMessage(Lang.TARGET_SUCCESS_KICK.toString(sender, target, gang, Ranks.OFFICER));
					sender.sendMessage(Lang.SENDER_SUCCESS_KICK.toString(sender, target, gang, Ranks.OFFICER));
					messageGang(gang, Lang.SUCCESS_KICK.toString(sender, target, gang, Ranks.OFFICER));
					return;
				}
			}else{
				sender.sendMessage(Lang.PREFIX.toString() + Lang.CANT_KICK.toString(sender, target, gang, Gang.getPlayerRank(target.getName(), gang)));
				return;
			}
		}if(gang.getOwner().equals(sender.getName())){
			Ranks r = Gang.getPlayerRank(target.getName(), gang);
			if(r == Ranks.MEMBER){
				gang.removeMember(target);
				target.sendMessage(Lang.TARGET_SUCCESS_KICK.toString(sender, target, gang, Ranks.MEMBER));
				sender.sendMessage(Lang.SENDER_SUCCESS_KICK.toString(sender, target, gang, Ranks.MEMBER));
				messageGang(gang, Lang.SUCCESS_KICK.toString(sender, target, gang, Ranks.MEMBER));
				return;
			}else if(r == Ranks.TRUSTED){
				gang.removeTrusted(target);
				target.sendMessage(Lang.TARGET_SUCCESS_KICK.toString(sender, target, gang, Ranks.TRUSTED));
				sender.sendMessage(Lang.SENDER_SUCCESS_KICK.toString(sender, target, gang, Ranks.TRUSTED));
				messageGang(gang, Lang.SUCCESS_KICK.toString(sender, target, gang, Ranks.TRUSTED));
				return;
			}else if(r == Ranks.OFFICER){
				gang.removeOfficer(target);
				target.sendMessage(Lang.TARGET_SUCCESS_KICK.toString(sender, target, gang, Ranks.OFFICER));
				sender.sendMessage(Lang.SENDER_SUCCESS_KICK.toString(sender, target, gang, Ranks.OFFICER));
				messageGang(gang, Lang.SUCCESS_KICK.toString(sender, target, gang, Ranks.OFFICER));
				return;
			}else if(r == Ranks.LEADER){
				gang.removeLeader(target);
				target.sendMessage(Lang.TARGET_SUCCESS_KICK.toString(sender, target, gang, Ranks.LEADER));
				sender.sendMessage(Lang.SENDER_SUCCESS_KICK.toString(sender, target, gang, Ranks.LEADER));
				messageGang(gang, Lang.SUCCESS_KICK.toString(sender, target, gang, Ranks.LEADER));
				return;
			}
		}if(sender.hasPermission("gangs.admin") || sender.isOp()){
			Ranks r = Gang.getPlayerRank(target.getName(), gang);
			if(r == Ranks.MEMBER){
				gang.removeMember(target);
				target.sendMessage(Lang.TARGET_SUCCESS_KICK.toString(sender, target, gang, Ranks.MEMBER));
				sender.sendMessage(Lang.SENDER_SUCCESS_KICK.toString(sender, target, gang, Ranks.MEMBER));
				messageGang(gang, Lang.SUCCESS_KICK.toString(sender, target, gang, Ranks.MEMBER));
				return;
			}else if(r == Ranks.TRUSTED){
				gang.removeTrusted(target);
				target.sendMessage(Lang.TARGET_SUCCESS_KICK.toString(sender, target, gang, Ranks.TRUSTED));
				sender.sendMessage(Lang.SENDER_SUCCESS_KICK.toString(sender, target, gang, Ranks.TRUSTED));
				messageGang(gang, Lang.SUCCESS_KICK.toString(sender, target, gang, Ranks.TRUSTED));
				return;
			}else if(r == Ranks.OFFICER){
				gang.removeOfficer(target);
				target.sendMessage(Lang.TARGET_SUCCESS_KICK.toString(sender, target, gang, Ranks.OFFICER));
				sender.sendMessage(Lang.SENDER_SUCCESS_KICK.toString(sender, target, gang, Ranks.OFFICER));
				messageGang(gang, Lang.SUCCESS_KICK.toString(sender, target, gang, Ranks.OFFICER));
				return;
			}else if(r == Ranks.LEADER){
				gang.removeLeader(target);
				target.sendMessage(Lang.TARGET_SUCCESS_KICK.toString(sender, target, gang, Ranks.LEADER));
				sender.sendMessage(Lang.SENDER_SUCCESS_KICK.toString(sender, target, gang, Ranks.LEADER));
				messageGang(gang, Lang.SUCCESS_KICK.toString(sender, target, gang, Ranks.LEADER));
				return;
			}else if(r == Ranks.OWNER){
				Ranks rr = Gang.getPlayerRank(sender.getName(), gang);
				String oldOwner = gang.getOwner();
				gang.setOwner(sender);
				gang.addLeader(Bukkit.getPlayerExact(oldOwner));
				gang.removeLeader(target);
				if(rr == Ranks.MEMBER){
					gang.removeMember(sender);
				}else if(rr == Ranks.TRUSTED){
					gang.removeTrusted(sender);
				}else if(rr == Ranks.OFFICER){
					gang.removeOfficer(sender);
				}else if(rr == Ranks.LEADER){
					gang.removeLeader(sender);
				}
				target.sendMessage(ChatColor.GREEN + "You are now the new owner of the gang.");
				target.sendMessage(Lang.TARGET_SUCCESS_KICK.toString(sender, target, gang, Ranks.OWNER));
				sender.sendMessage(Lang.SENDER_SUCCESS_KICK.toString(sender, target, gang, Ranks.OWNER));
				messageGang(gang, Lang.SUCCESS_KICK.toString(sender, target, gang, Ranks.OWNER));
				return;
			}
		}
			
	}

	public void leave(Player p, Gang g){
		if(g.getMembers().contains(p.getName())){
			messageGang(g, Lang.SUCCESS_LEFT.toString().replaceAll("%s%", p.getName()).replaceAll("%r%", "member").replaceAll("%g%", g.getName()));
			p.sendMessage(Lang.PREFIX.toString() + Lang.SENDER_SUCCESS_LEFT.toString().replaceAll("%s%", p.getName()).replaceAll("%r%", "member").replaceAll("%g%", g.getName()));
			g.removeMember(p);
			return;
		}if(g.getTrusted().contains(p.getName())){
			messageGang(g, Lang.SUCCESS_LEFT.toString().replaceAll("%s%", p.getName()).replaceAll("%r%", "trusted").replaceAll("%g%", g.getName()));
			p.sendMessage(Lang.PREFIX.toString() + Lang.SENDER_SUCCESS_LEFT.toString().replaceAll("%s%", p.getName()).replaceAll("%r%", "trusted").replaceAll("%g%", g.getName()));
			g.removeTrusted(p);
			return;
		}if(g.getOfficers().contains(p.getName())){
			messageGang(g, Lang.SUCCESS_LEFT.toString().replaceAll("%s%", p.getName()).replaceAll("%r%", "officer").replaceAll("%g%", g.getName()));
			p.sendMessage(Lang.PREFIX.toString() + Lang.SENDER_SUCCESS_LEFT.toString().replaceAll("%s%", p.getName()).replaceAll("%r%", "officer").replaceAll("%g%", g.getName()));
			g.removeTrusted(p);
			return;
		}if(g.getLeaders().contains(p.getName())){
			messageGang(g, Lang.SUCCESS_LEFT.toString().replaceAll("%s%", p.getName()).replaceAll("%r%", "leader").replaceAll("%g%", g.getName()));
			p.sendMessage(Lang.PREFIX.toString() + Lang.SENDER_SUCCESS_LEFT.toString().replaceAll("%s%", p.getName()).replaceAll("%r%", "leader").replaceAll("%g%", g.getName()));
			g.removeTrusted(p);
			return;
		}if(g.getOwner().equalsIgnoreCase(p.getName())){
			messageGang(g, Lang.DISBAND_ABSENCE.toString().replaceAll("%s%", p.getName()).replaceAll("%r%", "owner").replaceAll("%g%", g.getName()));
			removeGang(g.getName());
			p.sendMessage(Lang.PREFIX.toString() + Lang.SENDER_SUCCESS_LEFT.toString().replaceAll("%s%", p.getName()).replaceAll("%r%", "owner").replaceAll("%g%", g.getName()));
			return;
		}
	}

	public void createGang(Player owner, String name){
		f.getGangConfig().set("gangs." + name + ".members", new ArrayList<String>());
		f.getGangConfig().set("gangs." + name + ".trusted", new ArrayList<String>());
		f.getGangConfig().set("gangs." + name + ".officers", new ArrayList<String>());
		f.getGangConfig().set("gangs." + name + ".leaders", new ArrayList<String>());
		f.getGangConfig().set("gangs." + name + ".owner", owner.getName());
		List<String> gangs = f.getGangConfig().getStringList("gang-names");
		gangs.add(name);
		f.getGangConfig().set("gang-names", gangs);
		f.saveGangConfig();
		@SuppressWarnings("unused")
		Gang g = new Gang(name);
		owner.sendMessage(Lang.PREFIX.toString() + Lang.SUCCESFULLY_CREATED_GANG.toString().replaceAll("%s%", owner.getName()).replaceAll("%g%", name));
	}

	public void removeGang(String name){
		Gang g = getGangByName(name);
		f.getGangConfig().set("gangs." + g.getName(), null);
		List<String> gangs = f.getGangConfig().getStringList("gang-names");
		gangs.remove(g.getName());
		f.getGangConfig().set("gang-names", gangs);
		f.saveGangConfig();
		Gang.removeGang(g);
	}

	public void disbandGang(Player p, String name){
		Gang g = getGangByName(name);
		String gname = g.getName();
		f.getGangConfig().set("gangs." + g.getName(), null);
		List<String> gangs = f.getGangConfig().getStringList("gang-names");
		gangs.remove(g.getName());
		f.getGangConfig().set("gang-names", gangs);
		f.saveGangConfig();
		Gang.removeGang(g);
		messageGang(g, Lang.SUCCESS_DISBAND.toString().replaceAll("%s%", p.getName()).replaceAll("%g%", gname));
		p.sendMessage(Lang.PREFIX.toString() + Lang.SENDER_SUCCESS_DISBAND.toString().replaceAll("%s%", p.getName()).replaceAll("%g%", gname));
	}
}
