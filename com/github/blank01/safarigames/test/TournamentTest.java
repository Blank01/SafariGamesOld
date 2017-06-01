package com.github.blank01.safarigames.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import com.github.blank01.safarigames.tournament.model.Match;
import com.github.blank01.safarigames.tournament.model.Round;
import com.github.blank01.safarigames.tournament.model.Tournament;
import com.pixelmonmod.pixelmon.api.events.PlayerBattleEndedEvent;

public class TournamentTest {
	public static void main(String args[]){
		for(int i=1;i<=9;i++){
			testTournament(i);
		}
	}
		
	public static void testTournament(int size){
		List<UUID> uuidlist = new ArrayList<UUID>();
		for(int i=0;i<size;i++)
			uuidlist.add(UUID.randomUUID());
		//System.out.println(uuidlist.toString());
		Random r= new Random();
		Round round= new Round(uuidlist, 15, 1);
		List<Match>matches;
		while(round.getMatches().size()>1){
				matches = round.getMatches();
				
				for(int i=0;i<matches.size();i++){
					System.out.println(matches.get(i).getP1() + " vs " + matches.get(i).getP2());
					if(matches.get(i).getP2()==null){
						round.setWinner(matches.get(i), matches.get(i).getP1());
					}else if(r.nextBoolean()){
						round.setWinner(matches.get(i), matches.get(i).getP1());
					}else{
						round.setWinner(matches.get(i), matches.get(i).getP2());
					}
				}
				System.out.println(round.getWinners());
				System.out.println(round.getWinners().size());
				Round temp = new Round(round.getWinners(), 15, round.getRoundNumber()+1);
				round=temp;
		}
		

		if(round.getMatches().size()==1){
			matches = round.getMatches();
			System.out.println(matches.get(0).getP1() + " vs " + matches.get(0).getP2());
			if(matches.get(0).getP2()==null){
				round.setWinner(matches.get(0), matches.get(0).getP1());
			}else if(r.nextBoolean()){
				round.setWinner(matches.get(0), matches.get(0).getP1());
			}else{
				round.setWinner(matches.get(0), matches.get(0).getP2());
			}
		}
		System.out.println(round.getWinners().get(0));
			System.out.println("===================================================================");
		
		
	}
}
