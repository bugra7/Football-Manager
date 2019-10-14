import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {

	private  League userLeague;
	private  Team userTeam;
	Scanner sc = new Scanner(System.in);
	
	public  void playAllMatches() {
		
		userLeague.CalculatePowerofTeams();
		userTeam.setAttack_power(userTeam.getTactic().get(0)-5);
		
		int option;
		
		List<WeeklyFixture> fixture = userLeague.getFixture();
		WeeklyFixture weekly_fixture;
		
		int current_week = userLeague.getCurrent_week();
		
		while(current_week != 35) {
			
			
			System.out.print("1 to play matches\n2 to my league\n3 to my team\n4 to transfer : ");
			option = sc.nextInt();
			
			if(option == 1) {
				
				weekly_fixture = fixture.get(current_week-1);
				playOneWeekMatches(weekly_fixture);
				current_week++;
				
			}else if(option == 2) {
				
				System.out.print("1 to display league table\n2 to view results of this week : ");
				option = sc.nextInt();
				
				if(option==1)	displayTable();	
					
				else if(option==2){
					
					if(current_week!=1){
						weekly_fixture = fixture.get(current_week-2);
						List<Team> home_teams = weekly_fixture.getHome_teams();
						List<Team> away_teams = weekly_fixture.getAway_teams();
						List<String> results = weekly_fixture.getResults();
						Team away_team, home_team;
						
						for(int i = 0; i < home_teams.size(); i++){
							home_team = home_teams.get(i);
							away_team = away_teams.get(i);
							System.out.println(home_team.getName() + " " + results.get(i) + " " + away_team.getName());
						}
					}
					else	System.out.println("No matches played yet!");
					
				}
				
			}else if(option == 3){
				
				System.out.print("1 to check money\n2 to change tactic\n3 to view team squad : ");
				option = sc.nextInt();
				
				if(option==1)	System.out.println("Your team has " +userTeam.getMoney() + " $");
				
				else if(option==2){
								
						System.out.print("1 for defensive tactic\n2 for attacking tactic : ");
						option = sc.nextInt();
						
						if(option == 1){
							
							userTeam.setAttack_power(userTeam.getTactic().get(0)-5);
							if(userTeam.getAttack_power()<50)	userTeam.setAttack_power(50);
							System.out.println("Defensive tactic chosen");
						}
						
						else if(option == 2){
							
							userTeam.setDefense_power(userTeam.getTactic().get(1)-10);
							if(userTeam.getDefense_power()<50)	userTeam.setDefense_power(50);
							System.out.println("Attacking tactic chosen");
						}
				}
				
				else if(option==3){
					
					List<Player> Squad = userTeam.getSquad();
					
					for(int i = 0; i < Squad.size(); i++) {
						
						System.out.println(i+1 + ". " + Squad.get(i).getName() + "	" + Squad.get(i).getPower());
						
					}
				}
				
			}
			
			else if(option==4){
				
				List<Team> teams = userLeague.getTeams();
				
				for(int i = 0; i < teams.size(); i++) {
					
					Team team = teams.get(i);
					System.out.println(i+1 + ". " + team.getName());
					
				}
				
				System.out.print("Enter team number : ");
				option = sc.nextInt();
				
				List<Player> Squad = userLeague.getTeams().get(option-1).getSquad();
				
				for(int i = 0; i < Squad.size(); i++) {
					
					System.out.println(i+1 + ". " + Squad.get(i).getName() + "	" + Squad.get(i).getValue());
					
				}
				
				System.out.print("Enter player number : ");
				option = sc.nextInt();
				
				Player player = Squad.get(option-1);
				
				if(userTeam.getMoney() >= player.getValue()){
					
					userTeam.addSquad(player);
					
					Squad.remove(player);
					
					System.out.println("Transfer succesful!");
					
					userTeam.setMoney(userTeam.getMoney()-player.getValue());
					
				}
				
				else{
					
					System.out.println("You don't have enough money!");
					
				}
				
			}
			
			if(current_week == 35){
				
				if((userTeam.getName().equals(userLeague.getTeams().get(0).getName())))		System.out.println("Congratulations, You have won the league");
				
				else {
					int count=1;
					while(!(userTeam.getName().equals(userLeague.getTeams().get(count).getName())))	count++;
					
					System.out.println("You have finished the league in " + count + ". position");
				}
			}
		}
		
	}
	
	public  void displayTable() {
		
		sortTeams();
		
		List<Team> teams = userLeague.getTeams();
		
		for(int i = 0; i < teams.size(); i++) {
			
			Team team = teams.get(i);
			System.out.println(i+1 + ". " + team.getName() + ":" + team.getNumbers_of_games_played() + " " + team.getNumber_of_games_won() + " " + team.getNumber_of_games_drawn() + " " + team.getNumber_of_games_lost() + " " + team.getPoints() );
			
		}
	}
	
	public  void sortTeams(){
		
		List<Team> teams = userLeague.getTeams();
		Team temp;
		int j;
		
		for(int i=0; i < teams.size() - 1; i++){
			
			for(j=1; j < teams.size() - i; j++){
				
				if(teams.get(j-1).getPoints() < teams.get(j).getPoints()){
					
					temp=teams.get(j-1);
					teams.set(j-1, teams.get(j));
					teams.set(j, temp);
				}
			}
		}
	}
	
	public  void playOneWeekMatches(WeeklyFixture weekly_fixture) {
		
		int option=0;
		List<Team> home_teams = weekly_fixture.getHome_teams();
		List<Team> away_teams = weekly_fixture.getAway_teams();
		List<String> results = weekly_fixture.getResults();
		Team away_team;
		Team home_team;
		
		Random rand = new Random();
		
		int away_teams_goal = 0;
		int home_teams_goal = 0;
		int counter=0;
		
		for(int i = 0; i < home_teams.size(); i++) {
			
			home_team = home_teams.get(i);
			away_team = away_teams.get(i);
			if(away_team.equals(userTeam) || home_team.equals(userTeam)) {
				
				int home_chance,away_chance;
				
				for(int minute=10; minute<=95; minute+=10,counter++){
					
					home_chance = home_team.getAttack_power() - away_team.getDefense_power()/2;
					away_chance = away_team.getAttack_power() - home_team.getDefense_power()/2;
					
					if(minute==90)	minute=85;
					else if(minute==95)	minute=90;
					
					if(counter%2==0){
						
						
						if(rand.nextInt(100)+1 <= home_chance){
							
							System.out.println(minute +". minute : " + home_team.getName() + " scored");
							home_teams_goal++;
						}
						
						else	System.out.println(minute +". minute : " + home_team.getName() + " missed a chance to score\n");
					}
					
					else{
						
						if(rand.nextInt(100)+1 <= away_chance){
							
							System.out.println(minute +". minute : " + away_team.getName() + " scored");
							away_teams_goal++;
						}
						
						else	System.out.println(minute +". minute : " + away_team.getName() + " missed a chance to score");
					}
					System.out.println(home_team.getName()+" "+ home_teams_goal + " - " + away_teams_goal +" "+ away_team.getName());
					
					System.out.print("\n1 to continue\n2 to change tactic :");
					option = sc.nextInt();
					
					if(option == 2)	{
						
						System.out.print("1 for defensive tactic\n2 for attacking tactic : ");
						option = sc.nextInt();
						
						if(option == 1){
							
							userTeam.setAttack_power(userTeam.getTactic().get(0)-5);
							if(userTeam.getAttack_power()<50)	userTeam.setAttack_power(50);
							System.out.println("Defensive tactic chosen");
						}
						
						else if(option == 2){
							
							userTeam.setDefense_power(userTeam.getTactic().get(1)-10);
							if(userTeam.getDefense_power()<50)	userTeam.setDefense_power(50);
							System.out.println("Attacking tactic chosen");
						}
					}
				}
				
				if(away_teams_goal > home_teams_goal) {
					
					away_team.winMatch();
					home_team.loseMatch();
					
				}else if(away_teams_goal < home_teams_goal){
					
					home_team.winMatch();
					away_team.loseMatch();
					
				}else{
					
					away_team.drawMatch();
					home_team.drawMatch();

				}
				
				results.add(home_teams_goal+" - "+away_teams_goal);
				
			}
			
			else	results.add(playMatch(home_team, away_team));
			
		}
		
	}
	
	
	public  String playMatch(Team home_team, Team away_team) {
		
		Random rand = new Random();
		
		int home_chance = home_team.getAttack_power() - away_team.getDefense_power()/2;
		int away_chance = away_team.getAttack_power() - home_team.getDefense_power()/2;
		int away_teams_goal = 0;
		int home_teams_goal = 0;
		
		for(int i=1; i<=5; i++){
			
			if(rand.nextInt(100)+1 <= home_chance)	home_teams_goal++;
			if(rand.nextInt(100)+1 <= away_chance)	away_teams_goal++;
			
		}
		
		
		if(away_teams_goal > home_teams_goal) {
			
			away_team.winMatch();
			home_team.loseMatch();
			
		}else if(away_teams_goal < home_teams_goal){
			
			home_team.winMatch();
			away_team.loseMatch();
			
		}else{
			
			away_team.drawMatch();
			home_team.drawMatch();

		}
		
		return String.format("%d - %d", home_teams_goal, away_teams_goal);
		
	}
	
	public  Team getUserTeam() {
		return userTeam;
	}

	public  void setUserTeam(Team userTeam) {
		this.userTeam = userTeam;
	}

	public  League getUserLeague() {
		return userLeague;
	}

	public  void setUserLeague(League userLeague) {
		this.userLeague = userLeague;
	}
	
	public  void createGermanLeague() {
		
		League germanLeague = new League("German League");
		
		Team bayernmunchen = new Team("Bayern Munich", 0);
		Team werderbremen = new Team("Werder Bremen", 0);
		Team koln = new Team("Koln", 0);
		Team darmstadt98 = new Team("Darmstadt", 0);
		Team augsburg = new Team("Augsburg", 0);
		Team wolfsburg = new Team("Wolfsburg", 0);
		Team borussiadortmund = new Team("Dortmund", 0);
		Team mainz05 = new Team("Mainz", 0);
		Team eintrachtfrankfurt = new Team("Frankfurt", 0);
		Team schalke04 = new Team("Schalke04", 0);
		Team hamburg = new Team("Hamburg", 0);
		Team ingolstadt = new Team("Ingolstadt", 0);
		Team monchengladbach = new Team("Monchengladbach", 0);
		Team bayerleverkusen = new Team("Leverkusen", 0);	
		Team herthaberlin = new Team("Hertha Berlin", 0);
		Team freiburg = new Team("Freiburg", 0);
		Team hoffenheim = new Team("Hoffenheim", 0);
		Team rbleipzig = new Team("Leipzig", 0);
		
		rbleipzig.addSquad(new Player("Naby Keita", 13000000, 81, "Midfielder"));
		rbleipzig.addSquad(new Player("Timo Werner", 8250000, 77, "Forward"));
		rbleipzig.addSquad(new Player("Kyriakos Papadopoulos", 7250000, 76, "Defense"));
		rbleipzig.addSquad(new Player("Yussuf Poulsen", 5500000, 74, "Forward"));
		rbleipzig.addSquad(new Player("Emil Forsberg", 5500000, 74, "Midfielder"));
		rbleipzig.addSquad(new Player("Oliver Burke", 4750000, 73, "Midfielder"));
		rbleipzig.addSquad(new Player("Marcel Sabitzer", 3750000, 71, "Midfielder"));
		rbleipzig.addSquad(new Player("Lukas Klostermann", 3500000, 71, "Defense"));
		rbleipzig.addSquad(new Player("Davie Selke", 3250000, 70, "Forward"));
		rbleipzig.addSquad(new Player("Marcel Halstenberg", 3000000, 70, "Defense"));
		rbleipzig.addSquad(new Player("Willi Orban", 2500000, 69, "Defense"));
		rbleipzig.addSquad(new Player("Stefan Ilsanker", 2250000, 69, "Midfielder"));
		rbleipzig.addSquad(new Player("Dominik Kaiser", 2250000, 69, "Midfielder"));
		rbleipzig.addSquad(new Player("Marius M?ller", 1250000, 66, "Goalkeeper"));
		rbleipzig.addSquad(new Player("Rani Khedira", 1000000, 66, "Midfielder"));
		rbleipzig.addSquad(new Player("Diego Demme", 1000000, 66, "Midfielder"));
		rbleipzig.addSquad(new Player("Benno Schmitz", 1000000, 66, "Defense"));
		rbleipzig.addSquad(new Player("Marvin Compper", 850000, 65, "Defense"));
		rbleipzig.addSquad(new Player("Peter Gulacsi", 550000, 64, "Goalkeeper"));
		rbleipzig.addSquad(new Player("Terrence Boyd", 500000, 64, "Forward"));
		rbleipzig.addSquad(new Player("Benjamin Bellot", 300000, 63, "Goalkeeper"));
		rbleipzig.addSquad(new Player("Idrissa Toure", 300000, 63, "Forward"));
		rbleipzig.addSquad(new Player("Ken Gipson", 250000, 63, "Defense"));
		bayernmunchen.addSquad(new Player("Thomas M?ller", 80000000, 99, "Forward"));
		bayernmunchen.addSquad(new Player("Robert Lewandowski", 70000000, 98, "Forward"));
		bayernmunchen.addSquad(new Player("Jerome Boateng", 49000000, 97, "Defense"));
		bayernmunchen.addSquad(new Player("Arturo Vidal", 44000000, 97, "Midfielder"));
		bayernmunchen.addSquad(new Player("David Alaba", 43000000, 97, "Defense"));
		bayernmunchen.addSquad(new Player("Manuel Neuer", 42000000, 97, "Goalkeeper"));
		bayernmunchen.addSquad(new Player("Mats Hummels", 37000000, 92, "Defense"));
		bayernmunchen.addSquad(new Player("Douglas Costa", 32000000, 92, "Midfielder"));
		bayernmunchen.addSquad(new Player("Renato Sanches", 29000000, 90, "Midfielder"));
		bayernmunchen.addSquad(new Player("Thiago Alcantara", 24000000, 89, "Midfielder"));
		bayernmunchen.addSquad(new Player("Javi Martinez", 23000000, 88, "Defense"));
		bayernmunchen.addSquad(new Player("Joshua Kimmich", 21000000, 87, "Midfielder"));
		bayernmunchen.addSquad(new Player("Kingsley Coman", 21000000, 87, "Midfielder"));
		bayernmunchen.addSquad(new Player("Juan Bernat", 17000000, 84, "Defense"));
		bayernmunchen.addSquad(new Player("Philipp Lahm", 15000000, 83, "Defense"));
		bayernmunchen.addSquad(new Player("Franck Ribery", 9750000, 78, "Midfielder"));
		bayernmunchen.addSquad(new Player("Arjen Robben", 9000000, 78, "Midfielder"));
		bayernmunchen.addSquad(new Player("Rafinha", 5250000, 73, "Defense"));
		bayernmunchen.addSquad(new Player("Sven Ulreich", 2000000, 68, "Goalkeeper"));
		bayernmunchen.addSquad(new Player("Tom Starke", 150000, 62, "Goalkeeper"));
		herthaberlin.addSquad(new Player("Vladimir Darida", 7750000, 76, "Midfielder"));
		herthaberlin.addSquad(new Player("John Anthony Brooks", 6500000, 75, "Defense"));
		herthaberlin.addSquad(new Player("Mitchell Weiser", 4750000, 73, "Midfielder"));
		herthaberlin.addSquad(new Player("Valentin Stocker", 4250000, 72, "Midfielder"));
		herthaberlin.addSquad(new Player("Per Ciljan Skjelbred", 4000000, 72, "Midfielder"));
		herthaberlin.addSquad(new Player("Salomon Kalou", 4000000, 72, "Forward"));
		herthaberlin.addSquad(new Player("Fabian Lustenberger", 3750000, 71, "Midfielder"));
		herthaberlin.addSquad(new Player("Ondrej Duda", 3750000, 71, "Midfielder"));
		herthaberlin.addSquad(new Player("Sebastian Langkamp", 3250000, 70, "Defense"));
		herthaberlin.addSquad(new Player("Genki Haraguchi", 3250000, 70, "Forward"));
		herthaberlin.addSquad(new Player("Alexander Esswein", 3000000, 70, "Midfielder"));
		herthaberlin.addSquad(new Player("Niklas Stark", 2750000, 69, "Defense"));
		herthaberlin.addSquad(new Player("Vedad Ibisevic", 2750000, 69, "Forward"));
		herthaberlin.addSquad(new Player("Thomas Kraft", 2750000, 69, "Goalkeeper"));
		herthaberlin.addSquad(new Player("Marvin Plattenhardt", 2750000, 69, "Defense"));
		herthaberlin.addSquad(new Player("Peter Pekarik", 2500000, 69, "Defense"));
		herthaberlin.addSquad(new Player("Julian Schieber", 2500000, 69, "Forward"));
		herthaberlin.addSquad(new Player("Rune Jarstein", 1250000, 66, "Goalkeeper"));
		herthaberlin.addSquad(new Player("Jens Hegeler", 1000000, 66, "Midfielder"));
		herthaberlin.addSquad(new Player("Sami Allagui", 850000, 65, "Forward"));
		herthaberlin.addSquad(new Player("Maximilian Mittelstadt", 150000, 62, "Defense"));
		herthaberlin.addSquad(new Player("Nils-Jonathan K?rber", 100000, 61, "Goalkeeper"));
		herthaberlin.addSquad(new Player("Florian Baak", 50000, 61, "Defense"));
		hoffenheim.addSquad(new Player("Niklas S?le", 14000000, 82, "Defense"));
		hoffenheim.addSquad(new Player("Andrej Kramaric", 8750000, 77, "Forward"));
		hoffenheim.addSquad(new Player("Eduardo Vargas", 6500000, 75, "Forward"));
		hoffenheim.addSquad(new Player("Lukas Rupp", 5250000, 73, "Midfielder"));
		hoffenheim.addSquad(new Player("Fabian Schar", 5250000, 73, "Defense"));
		hoffenheim.addSquad(new Player("Oliver Baumann", 5000000, 73, "Goalkeeper"));
		hoffenheim.addSquad(new Player("Nadiem Amiri", 4500000, 72, "Midfielder"));
		hoffenheim.addSquad(new Player("Sebastian Rudy", 4500000, 72, "Midfielder"));
		hoffenheim.addSquad(new Player("Pavel Kaderabek", 3000000, 70, "Defense"));
		hoffenheim.addSquad(new Player("Ermin Bicakcic", 3000000, 70, "Defense"));
		hoffenheim.addSquad(new Player("Mark Uth", 2750000, 69, "Forward"));
		hoffenheim.addSquad(new Player("Kevin Vogt", 2750000, 69, "Midfielder"));
		hoffenheim.addSquad(new Player("Eugen Polanski", 2750000, 69, "Midfielder"));
		hoffenheim.addSquad(new Player("Pirmin Schwegler", 2750000, 69, "Midfielder"));
		hoffenheim.addSquad(new Player("Sandro Wagner", 2500000, 69, "Forward"));
		hoffenheim.addSquad(new Player("Benjamin H?bner", 2500000, 69, "Defense"));
		hoffenheim.addSquad(new Player("Jeremy Toljan", 2250000, 69, "Defense"));
		hoffenheim.addSquad(new Player("Adam Szalai", 1750000, 67, "Forward"));
		hoffenheim.addSquad(new Player("Jin Su Kim", 1750000, 67, "Defense"));
		hoffenheim.addSquad(new Player("Kerem Demirbay", 1500000, 67, "Midfielder"));
		hoffenheim.addSquad(new Player("Jiloan Hamad", 700000, 65, "Midfielder"));
		hoffenheim.addSquad(new Player("Gregor Kobel", 50000, 61, "Goalkeeper"));
		hoffenheim.addSquad(new Player("Alexander Stolz", 50000, 61, "Goalkeeper"));
		borussiadortmund.addSquad(new Player("Marco Reus", 47000000, 97, "Midfielder"));
		borussiadortmund.addSquad(new Player("Pierre Aubameyang", 41000000, 97, "Forward"));
		borussiadortmund.addSquad(new Player("Mario G?tze", 36000000, 92, "Midfielder"));
		borussiadortmund.addSquad(new Player("Sokratis", 20000000, 86, "Defense"));
		borussiadortmund.addSquad(new Player("Gonzalo Castro", 17000000, 84, "Defense"));
		borussiadortmund.addSquad(new Player("Andre Sch?rrle", 16000000, 84, "Forward"));
		borussiadortmund.addSquad(new Player("Shinji Kagawa", 16000000, 84, "Midfielder"));
		borussiadortmund.addSquad(new Player("Matthias Ginter", 14000000, 82, "Defense"));
		borussiadortmund.addSquad(new Player("Marc Bartra", 13000000, 81, "Defense"));
		borussiadortmund.addSquad(new Player("Julian Weigl", 11000000, 79, "Midfielder"));
		borussiadortmund.addSquad(new Player("Neven Subotic", 10000000, 78, "Defense"));
		borussiadortmund.addSquad(new Player("Sven Bender", 9250000, 78, "Midfielder"));
		borussiadortmund.addSquad(new Player("Sebastian Rode", 8250000, 77, "Midfielder"));
		borussiadortmund.addSquad(new Player("Nuri Sahin", 8000000, 76, "Midfielder"));
		borussiadortmund.addSquad(new Player("Raphael Guerreiro", 8000000, 76, "Defense"));
		borussiadortmund.addSquad(new Player("Marcel Schmelzer", 7250000, 76, "Defense"));
		borussiadortmund.addSquad(new Player("Roman B?rki", 5750000, 74, "Goalkeeper"));
		borussiadortmund.addSquad(new Player("Adrian Ramos", 5250000, 73, "Forward"));
		borussiadortmund.addSquad(new Player("Emre Mor", 5000000, 73, "Midfielder"));
		borussiadortmund.addSquad(new Player("Ousmane Dembele", 3500000, 71, "Forward"));
		borussiadortmund.addSquad(new Player("Roman Weidenfeller", 1250000, 66, "Goalkeeper"));
		borussiadortmund.addSquad(new Player("Hendrik Bonmann", 100000, 61, "Goalkeeper"));
		eintrachtfrankfurt.addSquad(new Player("Marc Stendera", 7250000, 76, "Midfielder"));
		eintrachtfrankfurt.addSquad(new Player("Haris Seferovic", 6000000, 74, "Forward"));
		eintrachtfrankfurt.addSquad(new Player("Michael Hector", 4750000, 73, "Defense"));
		eintrachtfrankfurt.addSquad(new Player("Shani Tarashaj", 4500000, 72, "Forward"));
		eintrachtfrankfurt.addSquad(new Player("Marco Fabian", 4250000, 72, "Midfielder"));
		eintrachtfrankfurt.addSquad(new Player("David Abraham", 4000000, 72, "Defense"));
		eintrachtfrankfurt.addSquad(new Player("Lukas Hradecky", 3500000, 71, "Goalkeeper"));
		eintrachtfrankfurt.addSquad(new Player("Bastian Oczipka", 3000000, 70, "Defense"));
		eintrachtfrankfurt.addSquad(new Player("Jesus Vallejo", 3000000, 70, "Defense"));
		eintrachtfrankfurt.addSquad(new Player("Alexander Meier", 3000000, 70, "Forward"));
		eintrachtfrankfurt.addSquad(new Player("Marco Russ", 3000000, 70, "Defense"));
		eintrachtfrankfurt.addSquad(new Player("Timothy Chandler", 1750000, 67, "Defense"));
		eintrachtfrankfurt.addSquad(new Player("Omar Mascarell", 1750000, 67, "Midfielder"));
		eintrachtfrankfurt.addSquad(new Player("Makoto Hasebe", 1750000, 67, "Midfielder"));
		eintrachtfrankfurt.addSquad(new Player("Mijat Gacinovic", 1750000, 67, "Midfielder"));
		eintrachtfrankfurt.addSquad(new Player("Branimir Hrgota", 1500000, 67, "Forward"));
		eintrachtfrankfurt.addSquad(new Player("Taleb Tawatha", 1250000, 66, "Defense"));
		eintrachtfrankfurt.addSquad(new Player("Slobodan Medojevic", 1250000, 66, "Midfielder"));
		eintrachtfrankfurt.addSquad(new Player("Johannes Flum", 1250000, 66, "Midfielder"));
		eintrachtfrankfurt.addSquad(new Player("Heinz Lindner", 750000, 65, "Goalkeeper"));
		eintrachtfrankfurt.addSquad(new Player("Szabolcs Huszti", 750000, 65, "Midfielder"));
		eintrachtfrankfurt.addSquad(new Player("Ante Rebic", 750000, 65, "Forward"));
		eintrachtfrankfurt.addSquad(new Player("Leon Batge", 150000, 62, "Goalkeeper"));
		koln.addSquad(new Player("Jonas Hector", 13000000, 81, "Defense"));
		koln.addSquad(new Player("Timo Horn", 11000000, 79, "Goalkeeper"));
		koln.addSquad(new Player("Anthony Modeste", 6750000, 75, "Forward"));
		koln.addSquad(new Player("Dominique Heintz", 5750000, 74, "Defense"));
		koln.addSquad(new Player("Leonardo Bittencourt", 5250000, 73, "Midfielder"));
		koln.addSquad(new Player("Marcel Risse", 4250000, 72, "Midfielder"));
		koln.addSquad(new Player("Dominic Maroh", 3250000, 70, "Defense"));
		koln.addSquad(new Player("Simon Zoller", 3250000, 70, "Forward"));
		koln.addSquad(new Player("Frederik S?rensen", 2750000, 69, "Defense"));
		koln.addSquad(new Player("Marco H?ger", 2750000, 69, "Midfielder"));
		koln.addSquad(new Player("Konstantin Rausch", 2250000, 69, "Defense"));
		koln.addSquad(new Player("Milos Jojic", 1750000, 67, "Midfielder"));
		koln.addSquad(new Player("Pawel Olkowski", 1750000, 67, "Defense"));
		koln.addSquad(new Player("Sehrou Guirassy", 1750000, 67, "Forward"));
		koln.addSquad(new Player("Filip Mladenovic", 1750000, 67, "Defense"));
		koln.addSquad(new Player("Yuya Osako", 1500000, 67, "Forward"));
		koln.addSquad(new Player("Matthias Lehmann", 850000, 65, "Midfielder"));
		koln.addSquad(new Player("Artjoms Rudnevs", 750000, 65, "Forward"));
		koln.addSquad(new Player("Thomas Kessler", 350000, 63, "Goalkeeper"));
		koln.addSquad(new Player("Marcel Hartel", 350000, 63, "Midfielder"));
		koln.addSquad(new Player("Lukas Kl?nter", 250000, 63, "Midfielder"));
		koln.addSquad(new Player("Sven Bacher", 150000, 62, "Goalkeeper"));
		koln.addSquad(new Player("Salih ?zcan", 150000, 62, "Midfielder"));
		schalke04.addSquad(new Player("Yevhen Konoplyanka", 27000000, 90, "Midfielder"));
		schalke04.addSquad(new Player("Breel Embolo", 17000000, 84, "Forward"));
		schalke04.addSquad(new Player("Johannes Geis", 16000000, 84, "Defense"));
		schalke04.addSquad(new Player("Benedikt H?wedes", 16000000, 84, "Defense"));
		schalke04.addSquad(new Player("Maximilian Meyer", 15000000, 83, "Midfielder"));
		schalke04.addSquad(new Player("Matija Nastasic", 15000000, 83, "Defense"));
		schalke04.addSquad(new Player("Leon Goretzka", 14000000, 82, "Midfielder"));
		schalke04.addSquad(new Player("Nabil Bentaleb", 11000000, 79, "Midfielder"));
		schalke04.addSquad(new Player("Maxim Choupo-Moting", 11000000, 79, "Forward"));
		schalke04.addSquad(new Player("Rahman Baba", 10000000, 78, "Defense"));
		schalke04.addSquad(new Player("Ralf Fahrmann", 8000000, 76, "Goalkeeper"));
		schalke04.addSquad(new Player("Benjamin Stambouli", 7000000, 75, "Midfielder"));
		schalke04.addSquad(new Player("Sead Kolasinac", 6500000, 75, "Defense"));
		schalke04.addSquad(new Player("Atsuto Uchida", 6500000, 75, "Defense"));
		schalke04.addSquad(new Player("Klaas-Jan Huntelaar", 6250000, 75, "Forward"));
		schalke04.addSquad(new Player("Franco Di Santo", 5750000, 74, "Forward"));
		schalke04.addSquad(new Player("Junior Cai?ara", 5500000, 74, "Defense"));
		schalke04.addSquad(new Player("Alessandro Sch?pf", 4000000, 72, "Midfielder"));
		schalke04.addSquad(new Player("Donis Avdijaj", 2750000, 69, "Forward"));
		schalke04.addSquad(new Player("Sidney Sam", 2250000, 69, "Midfielder"));
		schalke04.addSquad(new Player("Fabian Giefer", 800000, 65, "Goalkeeper"));
		schalke04.addSquad(new Player("Alexander N?bel", 250000, 63, "Goalkeeper"));
		schalke04.addSquad(new Player("Christian Sivodedov", 100000, 61, "Midfielder"));
		bayerleverkusen.addSquad(new Player("Chicharito", 24000000, 89, "Forward"));
		bayerleverkusen.addSquad(new Player("Karim Bellarabi", 21000000, 87, "Midfielder"));
		bayerleverkusen.addSquad(new Player("Kevin Volland", 21000000, 87, "Forward"));
		bayerleverkusen.addSquad(new Player("Hakan ?alhanoglu", 19000000, 86, "Midfielder"));
		bayerleverkusen.addSquad(new Player("?mer Toprak", 19000000, 86, "Defense"));
		bayerleverkusen.addSquad(new Player("Julian Brandt", 17000000, 84, "Midfielder"));
		bayerleverkusen.addSquad(new Player("Aleksandar Dragovic", 16000000, 84, "Defense"));
		bayerleverkusen.addSquad(new Player("Lars Bender", 16000000, 84, "Midfielder"));
		bayerleverkusen.addSquad(new Player("Jonathan Tah", 15000000, 83, "Defense"));
		bayerleverkusen.addSquad(new Player("Bernd Leno", 15000000, 83, "Goalkeeper"));
		bayerleverkusen.addSquad(new Player("Wendell", 14000000, 82, "Defense"));
		bayerleverkusen.addSquad(new Player("Kevin Kampl", 13000000, 81, "Midfielder"));
		bayerleverkusen.addSquad(new Player("Charles Aranguiz", 11000000, 79, "Midfielder"));
		bayerleverkusen.addSquad(new Player("Tin Jedvaj", 8250000, 77, "Defense"));
		bayerleverkusen.addSquad(new Player("Julian Baumgartlinger", 6500000, 75, "Midfielder"));
		bayerleverkusen.addSquad(new Player("Admir Mehmedi", 4750000, 73, "Forward"));
		bayerleverkusen.addSquad(new Player("Stefan Kiessling", 4500000, 72, "Forward"));
		bayerleverkusen.addSquad(new Player("Roberto Hilbert", 1750000, 67, "Defense"));
		bayerleverkusen.addSquad(new Player("Danny da Costa", 1500000, 67, "Defense"));
		bayerleverkusen.addSquad(new Player("Ramazan ?zcan", 1500000, 67, "Goalkeeper"));
		bayerleverkusen.addSquad(new Player("Robbie Kruse", 950000, 65, "Forward"));
		bayerleverkusen.addSquad(new Player("Vladen Yurchenko", 600000, 64, "Midfielder"));
		bayerleverkusen.addSquad(new Player("Niklas Lomb", 450000, 64, "Goalkeeper"));
		mainz05.addSquad(new Player("Yunus Malli", 8000000, 76, "Midfielder"));
		mainz05.addSquad(new Player("Jairo Samperio", 5750000, 74, "Midfielder"));
		mainz05.addSquad(new Player("Stefan Bell", 5500000, 74, "Defense"));
		mainz05.addSquad(new Player("Jhon Cordoba", 5000000, 73, "Forward"));
		mainz05.addSquad(new Player("Muto Yoshinori", 5000000, 73, "Midfielder"));
		mainz05.addSquad(new Player("Danny Latza", 3500000, 71, "Midfielder"));
		mainz05.addSquad(new Player("Christian Clemens", 3500000, 71, "Midfielder"));
		mainz05.addSquad(new Player("Fabian Frei", 2750000, 69, "Midfielder"));
		mainz05.addSquad(new Player("Levin ?ztunali", 2750000, 69, "Midfielder"));
		mainz05.addSquad(new Player("Pablo de Blasis", 2750000, 69, "Midfielder"));
		mainz05.addSquad(new Player("Daniel Brosinski", 2250000, 69, "Defense"));
		mainz05.addSquad(new Player("Emil Berggreen", 2250000, 69, "Forward"));
		mainz05.addSquad(new Player("Andre Ramalho", 2250000, 69, "Defense"));
		mainz05.addSquad(new Player("Pierre Bengtsson", 2250000, 69, "Defense"));
		mainz05.addSquad(new Player("Jonas L?ssl", 2250000, 69, "Goalkeeper"));
		mainz05.addSquad(new Player("Gaetan Bussmann", 2000000, 68, "Defense"));
		mainz05.addSquad(new Player("Giulio Donati", 2000000, 68, "Defense"));
		mainz05.addSquad(new Player("Jean-Philippe Gbamin", 2000000, 68, "Defense"));
		mainz05.addSquad(new Player("Karim Onisiwo", 1750000, 67, "Forward"));
		mainz05.addSquad(new Player("Gianluca Curci", 600000, 64, "Goalkeeper"));
		mainz05.addSquad(new Player("Devante Parker", 450000, 64, "Forward"));
		mainz05.addSquad(new Player("Florian M?ller", 200000, 62, "Goalkeeper"));
		mainz05.addSquad(new Player("Aaron Seydel", 100000, 61, "Forward"));
		freiburg.addSquad(new Player("Nils Petersen", 3750000, 71, "Forward"));
		freiburg.addSquad(new Player("Oliver Kempf", 3250000, 70, "Defense"));
		freiburg.addSquad(new Player("Vincenzo Grifo", 3000000, 70, "Midfielder"));
		freiburg.addSquad(new Player("Maximilian Philipp", 2750000, 69, "Forward"));
		freiburg.addSquad(new Player("Amir Abrashi", 2500000, 69, "Midfielder"));
		freiburg.addSquad(new Player("?aglar S?y?nc?", 2500000, 69, "Defense"));
		freiburg.addSquad(new Player("Christian G?nter", 2250000, 69, "Defense"));
		freiburg.addSquad(new Player("Aleksandar Ignjovski", 1750000, 67, "Midfielder"));
		freiburg.addSquad(new Player("Nicolas H?fler", 1750000, 67, "Midfielder"));
		freiburg.addSquad(new Player("Manuel Gulde", 1750000, 67, "Defense"));
		freiburg.addSquad(new Player("Mike Frantz", 1750000, 67, "Midfielder"));
		freiburg.addSquad(new Player("Jonas Meffert", 1750000, 67, "Midfielder"));
		freiburg.addSquad(new Player("Janik Haberer", 1750000, 67, "Forward"));
		freiburg.addSquad(new Player("Florian Niederlechner", 1500000, 67, "Forward"));
		freiburg.addSquad(new Player("Havard Nielsen", 1500000, 67, "Forward"));
		freiburg.addSquad(new Player("Alexander Schwolow", 1250000, 66, "Goalkeeper"));
		freiburg.addSquad(new Player("Georg Niedermeier", 1250000, 66, "Defense"));
		freiburg.addSquad(new Player("Marc Torrejon", 1250000, 66, "Defense"));
		freiburg.addSquad(new Player("Pascal Stenzel", 1250000, 66, "Midfielder"));
		freiburg.addSquad(new Player("Mats M?ller Daehli", 1250000, 66, "Midfielder"));
		freiburg.addSquad(new Player("Rafal Gikiewicz", 950000, 65, "Goalkeeper"));
		freiburg.addSquad(new Player("Mensur Mujdza", 750000, 65, "Defense"));
		freiburg.addSquad(new Player("Patric Klandt", 350000, 63, "Goalkeeper"));
		augsburg.addSquad(new Player("Martin Hinteregger", 5750000, 74, "Defense"));
		augsburg.addSquad(new Player("Ja Cheol Koo", 5250000, 73, "Midfielder"));
		augsburg.addSquad(new Player("Alfred Finnbogason", 5250000, 73, "Forward"));
		augsburg.addSquad(new Player("Raul Bobadilla", 5250000, 73, "Forward"));
		augsburg.addSquad(new Player("Marwin Hitz", 4250000, 72, "Goalkeeper"));
		augsburg.addSquad(new Player("Philipp Max", 3750000, 71, "Defense"));
		augsburg.addSquad(new Player("Jeffrey Gouweleeuw", 3750000, 71, "Defense"));
		augsburg.addSquad(new Player("Jonathan Schmid", 3000000, 70, "Midfielder"));
		augsburg.addSquad(new Player("Dominik Kohr", 3000000, 70, "Midfielder"));
		augsburg.addSquad(new Player("Takashi Usami", 2500000, 69, "Midfielder"));
		augsburg.addSquad(new Player("Caiuby", 2250000, 69, "Midfielder"));
		augsburg.addSquad(new Player("Kostas Stafylidis", 2250000, 69, "Defense"));
		augsburg.addSquad(new Player("Daniel Baier", 1750000, 67, "Midfielder"));
		augsburg.addSquad(new Player("Paul Verhaegh", 1500000, 67, "Defense"));
		augsburg.addSquad(new Player("Daniel Opare", 1250000, 66, "Defense"));
		augsburg.addSquad(new Player("Jan-Ingwer Callsen-Bracker", 1250000, 66, "Defense"));
		augsburg.addSquad(new Player("Ji Dong Won", 1250000, 66, "Midfielder"));
		augsburg.addSquad(new Player("Gojko Kacar", 1000000, 66, "Midfielder"));
		augsburg.addSquad(new Player("Halil Altintop", 750000, 65, "Forward"));
		augsburg.addSquad(new Player("Andreas Luthe", 550000, 64, "Goalkeeper"));
		augsburg.addSquad(new Player("Ioannis Gelios", 50000, 61, "Goalkeeper"));
		augsburg.addSquad(new Player("Julian Schmidt", 50000, 61, "Forward"));
		monchengladbach.addSquad(new Player("Christoph Kramer", 13000000, 81, "Midfielder"));
		monchengladbach.addSquad(new Player("Yann Sommer", 11000000, 79, "Goalkeeper"));
		monchengladbach.addSquad(new Player("Patrick Herrmann", 10000000, 78, "Midfielder"));
		monchengladbach.addSquad(new Player("Lars Stindl", 9000000, 78, "Midfielder"));
		monchengladbach.addSquad(new Player("Mahmoud Dahoud", 8750000, 77, "Midfielder"));
		monchengladbach.addSquad(new Player("Alvaro Dominguez", 8500000, 77, "Defense"));
		monchengladbach.addSquad(new Player("Josip Drmic", 8500000, 77, "Forward"));
		monchengladbach.addSquad(new Player("Tony Jantschke", 8000000, 76, "Defense"));
		monchengladbach.addSquad(new Player("Thorgan Hazard", 7750000, 76, "Midfielder"));
		monchengladbach.addSquad(new Player("Raffael", 7250000, 76, "Forward"));
		monchengladbach.addSquad(new Player("Jannik Vestergaard", 7250000, 76, "Defense"));
		monchengladbach.addSquad(new Player("Fabian Johnson", 6750000, 75, "Midfielder"));
		monchengladbach.addSquad(new Player("Jonas Hofmann", 6250000, 75, "Midfielder"));
		monchengladbach.addSquad(new Player("Andreas Christensen", 6250000, 75, "Defense"));
		monchengladbach.addSquad(new Player("Ibrahima Traore", 5500000, 74, "Midfielder"));
		monchengladbach.addSquad(new Player("Julian Korb", 5500000, 74, "Defense"));
		monchengladbach.addSquad(new Player("Nico Schulz", 4000000, 72, "Defense"));
		monchengladbach.addSquad(new Player("Oscar Wendt", 3500000, 71, "Defense"));
		monchengladbach.addSquad(new Player("Tobias Sippel", 1250000, 66, "Goalkeeper"));
		monchengladbach.addSquad(new Player("Christofer Heimeroth", 250000, 63, "Goalkeeper"));
		werderbremen.addSquad(new Player("Max Kruse", 13000000, 81, "Forward"));
		werderbremen.addSquad(new Player("Zlatko Junuzovic", 6500000, 75, "Midfielder"));
		werderbremen.addSquad(new Player("Lamine Sane", 4500000, 72, "Defense"));
		werderbremen.addSquad(new Player("Niklas Moisander", 4250000, 72, "Defense"));
		werderbremen.addSquad(new Player("Aron Johannsson", 4000000, 72, "Forward"));
		werderbremen.addSquad(new Player("Santiago Garcia", 3250000, 70, "Defense"));
		werderbremen.addSquad(new Player("Serge Gnabry", 3000000, 70, "Midfielder"));
		werderbremen.addSquad(new Player("Felix Wiedwald", 3000000, 70, "Goalkeeper"));
		werderbremen.addSquad(new Player("Florian Kainz", 3000000, 70, "Midfielder"));
		werderbremen.addSquad(new Player("Izet Hajrovic", 3000000, 70, "Midfielder"));
		werderbremen.addSquad(new Player("Sambou Yatabare", 2750000, 69, "Midfielder"));
		werderbremen.addSquad(new Player("Fin Bartels", 2500000, 69, "Midfielder"));
		werderbremen.addSquad(new Player("Theodor Gebre Selassie", 2500000, 69, "Defense"));
		werderbremen.addSquad(new Player("Philipp Bargfrede", 2250000, 69, "Midfielder"));
		werderbremen.addSquad(new Player("Robert Bauer", 1750000, 67, "Defense"));
		werderbremen.addSquad(new Player("Fallou Diagne", 1750000, 67, "Defense"));
		werderbremen.addSquad(new Player("Florian Grillitsch", 1750000, 67, "Forward"));
		werderbremen.addSquad(new Player("Luca Caldirola", 1250000, 66, "Defense"));
		werderbremen.addSquad(new Player("Lennart Thy", 1250000, 66, "Forward"));
		werderbremen.addSquad(new Player("Thanos Petsos", 1250000, 66, "Midfielder"));
		werderbremen.addSquad(new Player("Jaroslav Drobny", 800000, 65, "Goalkeeper"));
		werderbremen.addSquad(new Player("Claudio Pizarro", 750000, 65, "Forward"));
		werderbremen.addSquad(new Player("Raphael Wolf", 600000, 64, "Goalkeeper"));
		wolfsburg.addSquad(new Player("Ricardo Rodriguez", 30000000, 92, "Defense"));
		wolfsburg.addSquad(new Player("Julian Draxler", 23000000, 88, "Midfielder"));
		wolfsburg.addSquad(new Player("Luiz Gustavo", 19000000, 86, "Midfielder"));
		wolfsburg.addSquad(new Player("Josuha Guilavogui", 13000000, 81, "Midfielder"));
		wolfsburg.addSquad(new Player("Mario Gomez", 12000000, 80, "Forward"));
		wolfsburg.addSquad(new Player("Maxi Arnold", 11000000, 79, "Forward"));
		wolfsburg.addSquad(new Player("Jeffrey Bruma", 9000000, 78, "Defense"));
		wolfsburg.addSquad(new Player("Kuba Blaszczykowski", 8250000, 77, "Midfielder"));
		wolfsburg.addSquad(new Player("Vieirinha", 8250000, 77, "Midfielder"));
		wolfsburg.addSquad(new Player("Daniel Caligiuri", 8250000, 77, "Midfielder"));
		wolfsburg.addSquad(new Player("Philipp Wollscheid", 8000000, 76, "Defense"));
		wolfsburg.addSquad(new Player("Daniel Didavi", 6750000, 75, "Midfielder"));
		wolfsburg.addSquad(new Player("Robin Knoche", 5500000, 74, "Defense"));
		wolfsburg.addSquad(new Player("Yannick Gerhardt", 3750000, 71, "Midfielder"));
		wolfsburg.addSquad(new Player("Christian Trasch", 3500000, 71, "Defense"));
		wolfsburg.addSquad(new Player("Sebastian Jung", 3250000, 70, "Defense"));
		wolfsburg.addSquad(new Player("Diego Benaglio", 3250000, 70, "Goalkeeper"));
		wolfsburg.addSquad(new Player("Koen Casteels", 1750000, 67, "Goalkeeper"));
		wolfsburg.addSquad(new Player("Bruno Henrique", 1750000, 67, "Forward"));
		wolfsburg.addSquad(new Player("Marcel Schafer", 1750000, 67, "Defense"));
		wolfsburg.addSquad(new Player("Max Gr?n", 450000, 64, "Goalkeeper"));
		wolfsburg.addSquad(new Player("Borja Mayoral", 400000, 63, "Forward"));
		darmstadt98.addSquad(new Player("Sven Schipplock", 2750000, 69, "Forward"));
		darmstadt98.addSquad(new Player("Roman Bezjak", 2250000, 69, "Forward"));
		darmstadt98.addSquad(new Player("Ayta? Sulu", 2250000, 69, "Defense"));
		darmstadt98.addSquad(new Player("Artem Fedetsky", 2250000, 69, "Defense"));
		darmstadt98.addSquad(new Player("Marcel Heller", 2000000, 68, "Midfielder"));
		darmstadt98.addSquad(new Player("Jerome Gondorf", 1750000, 67, "Midfielder"));
		darmstadt98.addSquad(new Player("Fabian Holland", 1250000, 66, "Defense"));
		darmstadt98.addSquad(new Player("Immanuel H?hn", 1250000, 66, "Defense"));
		darmstadt98.addSquad(new Player("Anis Ben-Hatira", 1000000, 66, "Midfielder"));
		darmstadt98.addSquad(new Player("Denys Oliynyk", 1000000, 66, "Midfielder"));
		darmstadt98.addSquad(new Player("Alexander Milosevic", 1000000, 66, "Defense"));
		darmstadt98.addSquad(new Player("Heuer Fernandes", 800000, 65, "Goalkeeper"));
		darmstadt98.addSquad(new Player("Michael Esser", 800000, 65, "Goalkeeper"));
		darmstadt98.addSquad(new Player("Laszlo Kleinheisler", 750000, 65, "Midfielder"));
		darmstadt98.addSquad(new Player("Mario Vrancic", 750000, 65, "Midfielder"));
		darmstadt98.addSquad(new Player("Jan Rosenthal", 750000, 65, "Midfielder"));
		darmstadt98.addSquad(new Player("Florian Jungwirth", 750000, 65, "Midfielder"));
		darmstadt98.addSquad(new Player("Sandro Sirigu", 450000, 64, "Defense"));
		darmstadt98.addSquad(new Player("Victor Obinna", 450000, 64, "Forward"));
		darmstadt98.addSquad(new Player("Dominik Stroh-Engel", 450000, 64, "Forward"));
		darmstadt98.addSquad(new Player("Antonio Colak", 450000, 64, "Forward"));
		darmstadt98.addSquad(new Player("Leon Guwara", 350000, 63, "Defense"));
		darmstadt98.addSquad(new Player("Igor Berezovskiy", 50000, 61, "Goalkeeper"));
		ingolstadt.addSquad(new Player("Pascal Gross", 4500000, 72, "Midfielder"));
		ingolstadt.addSquad(new Player("Dario Lezcano", 3250000, 70, "Forward"));
		ingolstadt.addSquad(new Player("Max Christiansen", 3000000, 70, "Midfielder"));
		ingolstadt.addSquad(new Player("Lukas Hinterseer", 2500000, 69, "Forward"));
		ingolstadt.addSquad(new Player("Florent Hadergjonaj", 2500000, 69, "Defense"));
		ingolstadt.addSquad(new Player("Marcel Tisserand", 2250000, 69, "Defense"));
		ingolstadt.addSquad(new Player("Alfredo Morales", 2250000, 69, "Midfielder"));
		ingolstadt.addSquad(new Player("Matthew Leckie", 2250000, 69, "Forward"));
		ingolstadt.addSquad(new Player("Marvin Matip", 2000000, 68, "Defense"));
		ingolstadt.addSquad(new Player("Moritz Hartmann", 1750000, 67, "Forward"));
		ingolstadt.addSquad(new Player("Robert Leipertz", 1750000, 67, "Midfielder"));
		ingolstadt.addSquad(new Player("?rjan Nyland", 1750000, 67, "Goalkeeper"));
		ingolstadt.addSquad(new Player("Robert Bauer", 1750000, 67, "Defense"));
		ingolstadt.addSquad(new Player("Martin Hansen", 1250000, 66, "Goalkeeper"));
		ingolstadt.addSquad(new Player("Markus Suttner", 1250000, 66, "Defense"));
		ingolstadt.addSquad(new Player("Bernardo Roger", 1000000, 66, "Midfielder"));
		ingolstadt.addSquad(new Player("Hauke Wahl", 900000, 65, "Defense"));
		ingolstadt.addSquad(new Player("Tobias Levels", 750000, 65, "Defense"));
		ingolstadt.addSquad(new Player("Almog Cohen", 700000, 65, "Midfielder"));
		ingolstadt.addSquad(new Player("Stefan Lex", 650000, 64, "Forward"));
		ingolstadt.addSquad(new Player("Sonny Kittel", 600000, 64, "Midfielder"));
		ingolstadt.addSquad(new Player("Nico Rinderknecht", 200000, 62, "Midfielder"));
		ingolstadt.addSquad(new Player("Fabijan Buntic", 150000, 62, "Goalkeeper"));
		hamburg.addSquad(new Player("Alen Halilovic", 8000000, 76, "Midfielder"));
		hamburg.addSquad(new Player("Filip Kostic", 7750000, 76, "Forward"));
		hamburg.addSquad(new Player("Douglas Santos", 6250000, 75, "Defense"));
		hamburg.addSquad(new Player("Pierre-Michel Lasogga", 5750000, 74, "Forward"));
		hamburg.addSquad(new Player("Lewis Holtby", 4500000, 72, "Midfielder"));
		hamburg.addSquad(new Player("Albin Ekdal", 3750000, 71, "Midfielder"));
		hamburg.addSquad(new Player("Nicolai M?ller", 3500000, 71, "Midfielder"));
		hamburg.addSquad(new Player("Cleber", 3000000, 70, "Defense"));
		hamburg.addSquad(new Player("Aaron Hunt", 3000000, 70, "Midfielder"));
		hamburg.addSquad(new Player("Matthias Ostrzolek", 2750000, 69, "Defense"));
		hamburg.addSquad(new Player("Michael Gregoritsch", 2500000, 69, "Forward"));
		hamburg.addSquad(new Player("Johan Djourou", 2500000, 69, "Defense"));
		hamburg.addSquad(new Player("Rene Adler", 2500000, 69, "Goalkeeper"));
		hamburg.addSquad(new Player("Gotoku Sakai", 2500000, 69, "Defense"));
		hamburg.addSquad(new Player("Christian Mathenia", 2500000, 69, "Goalkeeper"));
		hamburg.addSquad(new Player("Dennis Diekmeier", 1750000, 67, "Defense"));
		hamburg.addSquad(new Player("Emir Spahic", 1250000, 66, "Defense"));
		hamburg.addSquad(new Player("Luca Waldschmidt", 1250000, 66, "Forward"));
		hamburg.addSquad(new Player("Ashton G?tz", 750000, 65, "Midfielder"));
		hamburg.addSquad(new Player("Bobby Wood", 750000, 65, "Forward"));
		hamburg.addSquad(new Player("Gideon Jung", 450000, 64, "Midfielder"));
		hamburg.addSquad(new Player("Tom Mickel", 250000, 63, "Goalkeeper"));
		hamburg.addSquad(new Player("Finn-Dominik Porath", 150000, 62, "Midfielder"));
		
		List<Team> germanLeagueTeams = new ArrayList<Team>(){
			
			{
				add(bayernmunchen);
				add(werderbremen);
				add(koln);
				add(darmstadt98);
				add(augsburg);
				add(wolfsburg);
				add(borussiadortmund);
				add(mainz05);
				add(eintrachtfrankfurt);
				add(schalke04);
				add(hamburg);
				add(ingolstadt);
				add(monchengladbach);
				add(bayerleverkusen);
				add(herthaberlin);
				add(freiburg);
				add(hoffenheim);
				add(rbleipzig);
				
			}
			
		};
		
		
		germanLeague.setTeams(germanLeagueTeams);	

		WeeklyFixture w1 = new WeeklyFixture(1);
		w1.addAway_team(bayernmunchen);
		w1.addHome_team(werderbremen);
		w1.addAway_team(koln);
		w1.addHome_team(darmstadt98);
		w1.addAway_team(augsburg);
		w1.addHome_team(wolfsburg);
		w1.addAway_team(borussiadortmund);
		w1.addHome_team(mainz05);
		w1.addAway_team(eintrachtfrankfurt);
		w1.addHome_team(schalke04);
		w1.addAway_team(hamburg);
		w1.addHome_team(ingolstadt);
		w1.addAway_team(monchengladbach);
		w1.addHome_team(bayerleverkusen);
		w1.addAway_team(herthaberlin);
		w1.addHome_team(freiburg);
		w1.addAway_team(hoffenheim);
		w1.addHome_team(rbleipzig);
		
		WeeklyFixture w2 = new WeeklyFixture(2);
		w2.addAway_team(schalke04);
		w2.addHome_team(bayernmunchen);
		w2.addAway_team(wolfsburg);
		w2.addHome_team(koln);
		w2.addAway_team(darmstadt98);
		w2.addHome_team(eintrachtfrankfurt);
		w2.addAway_team(freiburg);
		w2.addHome_team(monchengladbach);
		w2.addAway_team(ingolstadt);
		w2.addHome_team(herthaberlin);
		w2.addAway_team(bayerleverkusen);
		w2.addHome_team(hamburg);
		w2.addAway_team(rbleipzig);
		w2.addHome_team(borussiadortmund);
		w2.addAway_team(werderbremen);
		w2.addHome_team(augsburg);
		w2.addAway_team(mainz05);
		w2.addHome_team(hoffenheim);
		
		WeeklyFixture w3 = new WeeklyFixture(3);
		w3.addAway_team(koln);
		w3.addHome_team(freiburg);
		w3.addAway_team(hoffenheim);
		w3.addHome_team(wolfsburg);
		w3.addAway_team(bayernmunchen);
		w3.addHome_team(ingolstadt);
		w3.addAway_team(eintrachtfrankfurt);
		w3.addHome_team(bayerleverkusen);
		w3.addAway_team(hamburg);
		w3.addHome_team(rbleipzig);
		w3.addAway_team(borussiadortmund);
		w3.addHome_team(darmstadt98);
		w3.addAway_team(monchengladbach);
		w3.addHome_team(werderbremen);
		w3.addAway_team(augsburg);
		w3.addHome_team(mainz05);
		w3.addAway_team(herthaberlin);
		w3.addHome_team(schalke04);
		
		WeeklyFixture w4 = new WeeklyFixture(4);
		w4.addAway_team(freiburg);
		w4.addHome_team(hamburg);
		w4.addAway_team(darmstadt98);
		w4.addHome_team(hoffenheim);
		w4.addAway_team(ingolstadt);
		w4.addHome_team(eintrachtfrankfurt);
		w4.addAway_team(wolfsburg);
		w4.addHome_team(borussiadortmund);
		w4.addAway_team(bayerleverkusen);
		w4.addHome_team(augsburg);
		w4.addAway_team(schalke04);
		w4.addHome_team(koln);
		w4.addAway_team(werderbremen);
		w4.addHome_team(mainz05);
		w4.addAway_team(rbleipzig);
		w4.addHome_team(monchengladbach);
		w4.addAway_team(bayernmunchen);
		w4.addHome_team(herthaberlin);
		
		WeeklyFixture w5 = new WeeklyFixture(5);
		w5.addAway_team(borussiadortmund);
		w5.addHome_team(freiburg);
		w5.addAway_team(augsburg);
		w5.addHome_team(darmstadt98);
		w5.addAway_team(eintrachtfrankfurt);
		w5.addHome_team(herthaberlin);
		w5.addAway_team(hamburg);
		w5.addHome_team(bayernmunchen);
		w5.addAway_team(mainz05);
		w5.addHome_team(bayerleverkusen);
		w5.addAway_team(monchengladbach);
		w5.addHome_team(ingolstadt);
		w5.addAway_team(werderbremen);
		w5.addHome_team(wolfsburg);
		w5.addAway_team(hoffenheim);
		w5.addHome_team(schalke04);
		w5.addAway_team(koln);
		w5.addHome_team(rbleipzig);
		
		WeeklyFixture w6 = new WeeklyFixture(6);
		w6.addAway_team(rbleipzig);
		w6.addHome_team(augsburg);
		w6.addAway_team(darmstadt98);
		w6.addHome_team(werderbremen);
		w6.addAway_team(freiburg);
		w6.addHome_team(eintrachtfrankfurt);
		w6.addAway_team(bayernmunchen);
		w6.addHome_team(koln);
		w6.addAway_team(ingolstadt);
		w6.addHome_team(hoffenheim);
		w6.addAway_team(herthaberlin);
		w6.addHome_team(hamburg);
		w6.addAway_team(bayerleverkusen);
		w6.addHome_team(borussiadortmund);
		w6.addAway_team(wolfsburg);
		w6.addHome_team(mainz05);
		w6.addAway_team(schalke04);
		w6.addHome_team(monchengladbach);
		
		WeeklyFixture w7 = new WeeklyFixture(7);
		w7.addAway_team(borussiadortmund);
		w7.addHome_team(herthaberlin);
		w7.addAway_team(eintrachtfrankfurt);
		w7.addHome_team(bayernmunchen);
		w7.addAway_team(augsburg);
		w7.addHome_team(schalke04);
		w7.addAway_team(hoffenheim);
		w7.addHome_team(freiburg);
		w7.addAway_team(koln);
		w7.addHome_team(ingolstadt);
		w7.addAway_team(monchengladbach);
		w7.addHome_team(hamburg);
		w7.addAway_team(werderbremen);
		w7.addHome_team(bayerleverkusen);
		w7.addAway_team(mainz05);
		w7.addHome_team(darmstadt98);
		w7.addAway_team(wolfsburg);
		w7.addHome_team(rbleipzig);
		
		WeeklyFixture w8 = new WeeklyFixture(8);
		w8.addAway_team(hamburg);
		w8.addHome_team(eintrachtfrankfurt);
		w8.addAway_team(bayerleverkusen);
		w8.addHome_team(hoffenheim);
		w8.addAway_team(darmstadt98);
		w8.addHome_team(wolfsburg);
		w8.addAway_team(freiburg);
		w8.addHome_team(augsburg);
		w8.addAway_team(herthaberlin);
		w8.addHome_team(koln);
		w8.addAway_team(ingolstadt);
		w8.addHome_team(borussiadortmund);
		w8.addAway_team(bayernmunchen);
		w8.addHome_team(monchengladbach);
		w8.addAway_team(rbleipzig);
		w8.addHome_team(werderbremen);
		w8.addAway_team(schalke04);
		w8.addHome_team(mainz05);
		
		WeeklyFixture w9 = new WeeklyFixture(9);
		w9.addAway_team(monchengladbach);
		w9.addHome_team(eintrachtfrankfurt);
		w9.addAway_team(augsburg);
		w9.addHome_team(bayernmunchen);
		w9.addAway_team(werderbremen);
		w9.addHome_team(freiburg);
		w9.addAway_team(mainz05);
		w9.addHome_team(ingolstadt);
		w9.addAway_team(wolfsburg);
		w9.addHome_team(bayerleverkusen);
		w9.addAway_team(darmstadt98);
		w9.addHome_team(rbleipzig);
		w9.addAway_team(borussiadortmund);
		w9.addHome_team(schalke04);
		w9.addAway_team(hoffenheim);
		w9.addHome_team(herthaberlin);
		w9.addAway_team(koln);
		w9.addHome_team(hamburg);
		
		WeeklyFixture w10 = new WeeklyFixture(10);
		w10.addAway_team(herthaberlin);
		w10.addHome_team(monchengladbach);
		w10.addAway_team(bayernmunchen);
		w10.addHome_team(hoffenheim);
		w10.addAway_team(freiburg);
		w10.addHome_team(wolfsburg);
		w10.addAway_team(hamburg);
		w10.addHome_team(borussiadortmund);
		w10.addAway_team(bayerleverkusen);
		w10.addHome_team(darmstadt98);
		w10.addAway_team(ingolstadt);
		w10.addHome_team(augsburg);
		w10.addAway_team(eintrachtfrankfurt);
		w10.addHome_team(koln);
		w10.addAway_team(rbleipzig);
		w10.addHome_team(mainz05);
		w10.addAway_team(schalke04);
		w10.addHome_team(werderbremen);
		
		WeeklyFixture w11 = new WeeklyFixture(11);
		w11.addAway_team(bayerleverkusen);
		w11.addHome_team(rbleipzig);
		w11.addAway_team(monchengladbach);
		w11.addHome_team(koln);
		w11.addAway_team(mainz05);
		w11.addHome_team(freiburg);
		w11.addAway_team(augsburg);
		w11.addHome_team(herthaberlin);
		w11.addAway_team(wolfsburg);
		w11.addHome_team(schalke04);
		w11.addAway_team(darmstadt98);
		w11.addHome_team(ingolstadt);
		w11.addAway_team(borussiadortmund);
		w11.addHome_team(bayernmunchen);
		w11.addAway_team(hoffenheim);
		w11.addHome_team(hamburg);
		w11.addAway_team(werderbremen);
		w11.addHome_team(eintrachtfrankfurt);
		
		WeeklyFixture w12 = new WeeklyFixture(12);
		w12.addAway_team(freiburg);
		w12.addHome_team(rbleipzig);
		w12.addAway_team(eintrachtfrankfurt);
		w12.addHome_team(borussiadortmund);
		w12.addAway_team(koln);
		w12.addHome_team(augsburg);
		w12.addAway_team(monchengladbach);
		w12.addHome_team(hoffenheim);
		w12.addAway_team(ingolstadt);
		w12.addHome_team(wolfsburg);
		w12.addAway_team(hamburg);
		w12.addHome_team(werderbremen);
		w12.addAway_team(bayernmunchen);
		w12.addHome_team(bayerleverkusen);
		w12.addAway_team(schalke04);
		w12.addHome_team(darmstadt98);
		w12.addAway_team(herthaberlin);
		w12.addHome_team(mainz05);
		
		WeeklyFixture w13 = new WeeklyFixture(13);
		w13.addAway_team(mainz05);
		w13.addHome_team(bayernmunchen);
		w13.addAway_team(bayerleverkusen);
		w13.addHome_team(freiburg);
		w13.addAway_team(hoffenheim);
		w13.addHome_team(koln);
		w13.addAway_team(werderbremen);
		w13.addHome_team(ingolstadt);
		w13.addAway_team(wolfsburg);
		w13.addHome_team(herthaberlin);
		w13.addAway_team(borussiadortmund);
		w13.addHome_team(monchengladbach);
		w13.addAway_team(rbleipzig);
		w13.addHome_team(schalke04);
		w13.addAway_team(darmstadt98);
		w13.addHome_team(hamburg);
		w13.addAway_team(augsburg);
		w13.addHome_team(eintrachtfrankfurt);
		
		WeeklyFixture w14 = new WeeklyFixture(14);
		w14.addAway_team(eintrachtfrankfurt);
		w14.addHome_team(hoffenheim);
		w14.addAway_team(bayernmunchen);
		w14.addHome_team(wolfsburg);
		w14.addAway_team(freiburg);
		w14.addHome_team(darmstadt98);
		w14.addAway_team(hamburg);
		w14.addHome_team(augsburg);
		w14.addAway_team(ingolstadt);
		w14.addHome_team(rbleipzig);
		w14.addAway_team(koln);
		w14.addHome_team(borussiadortmund);
		w14.addAway_team(herthaberlin);
		w14.addHome_team(werderbremen);
		w14.addAway_team(monchengladbach);
		w14.addHome_team(mainz05);
		w14.addAway_team(schalke04);
		w14.addHome_team(bayerleverkusen);
		
		WeeklyFixture w15 = new WeeklyFixture(15);
		w15.addAway_team(hoffenheim);
		w15.addHome_team(borussiadortmund);
		w15.addAway_team(augsburg);
		w15.addHome_team(monchengladbach);
		w15.addAway_team(mainz05);
		w15.addHome_team(hamburg);
		w15.addAway_team(rbleipzig);
		w15.addHome_team(herthaberlin);
		w15.addAway_team(schalke04);
		w15.addHome_team(freiburg);
		w15.addAway_team(werderbremen);
		w15.addHome_team(koln);
		w15.addAway_team(wolfsburg);
		w15.addHome_team(eintrachtfrankfurt);
		w15.addAway_team(darmstadt98);
		w15.addHome_team(bayernmunchen);
		w15.addAway_team(bayerleverkusen);
		w15.addHome_team(ingolstadt);
		
		WeeklyFixture w16 = new WeeklyFixture(16);
		w16.addAway_team(borussiadortmund);
		w16.addHome_team(augsburg);
		w16.addAway_team(eintrachtfrankfurt);
		w16.addHome_team(mainz05);
		w16.addAway_team(hamburg);
		w16.addHome_team(schalke04);
		w16.addAway_team(monchengladbach);
		w16.addHome_team(wolfsburg);
		w16.addAway_team(bayernmunchen);
		w16.addHome_team(rbleipzig);
		w16.addAway_team(herthaberlin);
		w16.addHome_team(darmstadt98);
		w16.addAway_team(hoffenheim);
		w16.addHome_team(werderbremen);
		w16.addAway_team(ingolstadt);
		w16.addHome_team(freiburg);
		w16.addAway_team(koln);
		w16.addHome_team(bayerleverkusen);
		
		WeeklyFixture w17 = new WeeklyFixture(17);
		w17.addAway_team(freiburg);
		w17.addHome_team(bayernmunchen);
		w17.addAway_team(augsburg);
		w17.addHome_team(hoffenheim);
		w17.addAway_team(darmstadt98);
		w17.addHome_team(monchengladbach);
		w17.addAway_team(schalke04);
		w17.addHome_team(ingolstadt);
		w17.addAway_team(werderbremen);
		w17.addHome_team(borussiadortmund);
		w17.addAway_team(wolfsburg);
		w17.addHome_team(hamburg);
		w17.addAway_team(rbleipzig);
		w17.addHome_team(eintrachtfrankfurt);
		w17.addAway_team(bayerleverkusen);
		w17.addHome_team(herthaberlin);
		w17.addAway_team(mainz05);
		w17.addHome_team(koln);
		
		WeeklyFixture w18 = new WeeklyFixture(18);
		w18.addAway_team(schalke04);
		w18.addHome_team(eintrachtfrankfurt);
		w18.addAway_team(darmstadt98);
		w18.addHome_team(koln);
		w18.addAway_team(ingolstadt);
		w18.addHome_team(hamburg);
		w18.addAway_team(rbleipzig);
		w18.addHome_team(hoffenheim);
		w18.addAway_team(werderbremen);
		w18.addHome_team(bayernmunchen);
		w18.addAway_team(wolfsburg);
		w18.addHome_team(augsburg);
		w18.addAway_team(bayerleverkusen);
		w18.addHome_team(monchengladbach);
		w18.addAway_team(freiburg);
		w18.addHome_team(herthaberlin);
		w18.addAway_team(mainz05);
		w18.addHome_team(borussiadortmund);
		
		WeeklyFixture w19 = new WeeklyFixture(19);
		w19.addAway_team(hamburg);
		w19.addHome_team(bayerleverkusen);
		w19.addAway_team(bayernmunchen);
		w19.addHome_team(schalke04);
		w19.addAway_team(herthaberlin);
		w19.addHome_team(ingolstadt);
		w19.addAway_team(hoffenheim);
		w19.addHome_team(mainz05);
		w19.addAway_team(koln);
		w19.addHome_team(wolfsburg);
		w19.addAway_team(monchengladbach);
		w19.addHome_team(freiburg);
		w19.addAway_team(borussiadortmund);
		w19.addHome_team(rbleipzig);
		w19.addAway_team(augsburg);
		w19.addHome_team(werderbremen);
		w19.addAway_team(eintrachtfrankfurt);
		w19.addHome_team(darmstadt98);
		
		WeeklyFixture w20 = new WeeklyFixture(20);
		w20.addAway_team(mainz05);
		w20.addHome_team(augsburg);
		w20.addAway_team(bayerleverkusen);
		w20.addHome_team(eintrachtfrankfurt);
		w20.addAway_team(darmstadt98);
		w20.addHome_team(borussiadortmund);
		w20.addAway_team(ingolstadt);
		w20.addHome_team(bayernmunchen);
		w20.addAway_team(rbleipzig);
		w20.addHome_team(hamburg);
		w20.addAway_team(werderbremen);
		w20.addHome_team(monchengladbach);
		w20.addAway_team(schalke04);
		w20.addHome_team(herthaberlin);
		w20.addAway_team(wolfsburg);
		w20.addHome_team(hoffenheim);
		w20.addAway_team(freiburg);
		w20.addHome_team(koln);
		
		WeeklyFixture w21 = new WeeklyFixture(21);
		w21.addAway_team(augsburg);
		w21.addHome_team(bayerleverkusen);
		w21.addAway_team(borussiadortmund);
		w21.addHome_team(wolfsburg);
		w21.addAway_team(eintrachtfrankfurt);
		w21.addHome_team(ingolstadt);
		w21.addAway_team(hamburg);
		w21.addHome_team(freiburg);
		w21.addAway_team(herthaberlin);
		w21.addHome_team(bayernmunchen);
		w21.addAway_team(hoffenheim);
		w21.addHome_team(darmstadt98);
		w21.addAway_team(koln);
		w21.addHome_team(schalke04);
		w21.addAway_team(mainz05);
		w21.addHome_team(werderbremen);
		w21.addAway_team(monchengladbach);
		w21.addHome_team(rbleipzig);
		
		WeeklyFixture w22 = new WeeklyFixture(22);
		w22.addAway_team(bayerleverkusen);
		w22.addHome_team(mainz05);
		w22.addAway_team(bayernmunchen);
		w22.addHome_team(hamburg);
		w22.addAway_team(darmstadt98);
		w22.addHome_team(augsburg);
		w22.addAway_team(freiburg);
		w22.addHome_team(borussiadortmund);
		w22.addAway_team(herthaberlin);
		w22.addHome_team(eintrachtfrankfurt);
		w22.addAway_team(ingolstadt);
		w22.addHome_team(monchengladbach);
		w22.addAway_team(rbleipzig);
		w22.addHome_team(koln);
		w22.addAway_team(schalke04);
		w22.addHome_team(hoffenheim);
		w22.addAway_team(wolfsburg);
		w22.addHome_team(werderbremen);
		
		WeeklyFixture w23 = new WeeklyFixture(23);
		w23.addAway_team(augsburg);
		w23.addHome_team(rbleipzig);
		w23.addAway_team(borussiadortmund);
		w23.addHome_team(bayerleverkusen);
		w23.addAway_team(eintrachtfrankfurt);
		w23.addHome_team(freiburg);
		w23.addAway_team(hamburg);
		w23.addHome_team(herthaberlin);
		w23.addAway_team(hoffenheim);
		w23.addHome_team(ingolstadt);
		w23.addAway_team(koln);
		w23.addHome_team(bayernmunchen);
		w23.addAway_team(mainz05);
		w23.addHome_team(wolfsburg);
		w23.addAway_team(monchengladbach);
		w23.addHome_team(schalke04);
		w23.addAway_team(werderbremen);
		w23.addHome_team(darmstadt98);
		
		WeeklyFixture w24 = new WeeklyFixture(24);
		w24.addAway_team(bayerleverkusen);
		w24.addHome_team(werderbremen);
		w24.addAway_team(bayernmunchen);
		w24.addHome_team(eintrachtfrankfurt);
		w24.addAway_team(darmstadt98);
		w24.addHome_team(mainz05);
		w24.addAway_team(freiburg);
		w24.addHome_team(hoffenheim);
		w24.addAway_team(hamburg);
		w24.addHome_team(monchengladbach);
		w24.addAway_team(herthaberlin);
		w24.addHome_team(borussiadortmund);
		w24.addAway_team(ingolstadt);
		w24.addHome_team(koln);
		w24.addAway_team(rbleipzig);
		w24.addHome_team(wolfsburg);
		w24.addAway_team(schalke04);
		w24.addHome_team(augsburg);
		
		WeeklyFixture w25 = new WeeklyFixture(25);
		w25.addAway_team(augsburg);
		w25.addHome_team(freiburg);
		w25.addAway_team(borussiadortmund);
		w25.addHome_team(ingolstadt);
		w25.addAway_team(eintrachtfrankfurt);
		w25.addHome_team(hamburg);
		w25.addAway_team(hoffenheim);
		w25.addHome_team(bayerleverkusen);
		w25.addAway_team(koln);
		w25.addHome_team(herthaberlin);
		w25.addAway_team(mainz05);
		w25.addHome_team(schalke04);
		w25.addAway_team(monchengladbach);
		w25.addHome_team(bayernmunchen);
		w25.addAway_team(werderbremen);
		w25.addHome_team(rbleipzig);
		w25.addAway_team(wolfsburg);
		w25.addHome_team(darmstadt98);
		
		WeeklyFixture w26 = new WeeklyFixture(26);
		w26.addAway_team(bayerleverkusen);
		w26.addHome_team(wolfsburg);
		w26.addAway_team(bayernmunchen);
		w26.addHome_team(augsburg);
		w26.addAway_team(eintrachtfrankfurt);
		w26.addHome_team(monchengladbach);
		w26.addAway_team(freiburg);
		w26.addHome_team(werderbremen);
		w26.addAway_team(hamburg);
		w26.addHome_team(koln);
		w26.addAway_team(herthaberlin);
		w26.addHome_team(hoffenheim);
		w26.addAway_team(ingolstadt);
		w26.addHome_team(mainz05);
		w26.addAway_team(rbleipzig);
		w26.addHome_team(darmstadt98);
		w26.addAway_team(schalke04);
		w26.addHome_team(borussiadortmund);
		
		WeeklyFixture w27 = new WeeklyFixture(27);
		w27.addAway_team(augsburg);
		w27.addHome_team(ingolstadt);
		w27.addAway_team(borussiadortmund);
		w27.addHome_team(hamburg);
		w27.addAway_team(darmstadt98);
		w27.addHome_team(bayerleverkusen);
		w27.addAway_team(hoffenheim);
		w27.addHome_team(bayernmunchen);
		w27.addAway_team(koln);
		w27.addHome_team(eintrachtfrankfurt);
		w27.addAway_team(mainz05);
		w27.addHome_team(rbleipzig);
		w27.addAway_team(monchengladbach);
		w27.addHome_team(herthaberlin);
		w27.addAway_team(werderbremen);
		w27.addHome_team(schalke04);
		w27.addAway_team(wolfsburg);
		w27.addHome_team(freiburg);
		
		WeeklyFixture w28 = new WeeklyFixture(28);
		w28.addAway_team(bayernmunchen);
		w28.addHome_team(borussiadortmund);
		w28.addAway_team(eintrachtfrankfurt);
		w28.addHome_team(werderbremen);
		w28.addAway_team(freiburg);
		w28.addHome_team(mainz05);
		w28.addAway_team(hamburg);
		w28.addHome_team(hoffenheim);
		w28.addAway_team(herthaberlin);
		w28.addHome_team(augsburg);
		w28.addAway_team(ingolstadt);
		w28.addHome_team(darmstadt98);
		w28.addAway_team(koln);
		w28.addHome_team(monchengladbach);
		w28.addAway_team(rbleipzig);
		w28.addHome_team(bayerleverkusen);
		w28.addAway_team(schalke04);
		w28.addHome_team(wolfsburg);
		
		WeeklyFixture w29 = new WeeklyFixture(29);
		w29.addAway_team(augsburg);
		w29.addHome_team(koln);
		w29.addAway_team(bayerleverkusen);
		w29.addHome_team(bayernmunchen);
		w29.addAway_team(borussiadortmund);
		w29.addHome_team(eintrachtfrankfurt);
		w29.addAway_team(darmstadt98);
		w29.addHome_team(schalke04);
		w29.addAway_team(hoffenheim);
		w29.addHome_team(monchengladbach);
		w29.addAway_team(mainz05);
		w29.addHome_team(herthaberlin);
		w29.addAway_team(rbleipzig);
		w29.addHome_team(freiburg);
		w29.addAway_team(werderbremen);
		w29.addHome_team(hamburg);
		w29.addAway_team(wolfsburg);
		w29.addHome_team(ingolstadt);
		
		WeeklyFixture w30 = new WeeklyFixture(30);
		w30.addAway_team(bayernmunchen);
		w30.addHome_team(mainz05);
		w30.addAway_team(eintrachtfrankfurt);
		w30.addHome_team(augsburg);
		w30.addAway_team(freiburg);
		w30.addHome_team(bayerleverkusen);
		w30.addAway_team(hamburg);
		w30.addHome_team(darmstadt98);
		w30.addAway_team(herthaberlin);
		w30.addHome_team(wolfsburg);
		w30.addAway_team(ingolstadt);
		w30.addHome_team(werderbremen);
		w30.addAway_team(koln);
		w30.addHome_team(hoffenheim);
		w30.addAway_team(monchengladbach);
		w30.addHome_team(borussiadortmund);
		w30.addAway_team(schalke04);
		w30.addHome_team(rbleipzig);
		
		WeeklyFixture w31 = new WeeklyFixture(31);
		w31.addAway_team(augsburg);
		w31.addHome_team(hamburg);
		w31.addAway_team(bayerleverkusen);
		w31.addHome_team(schalke04);
		w31.addAway_team(borussiadortmund);
		w31.addHome_team(koln);
		w31.addAway_team(darmstadt98);
		w31.addHome_team(freiburg);
		w31.addAway_team(hoffenheim);
		w31.addHome_team(eintrachtfrankfurt);
		w31.addAway_team(mainz05);
		w31.addHome_team(monchengladbach);
		w31.addAway_team(rbleipzig);
		w31.addHome_team(ingolstadt);
		w31.addAway_team(werderbremen);
		w31.addHome_team(herthaberlin);
		w31.addAway_team(wolfsburg);
		w31.addHome_team(bayernmunchen);
		
		WeeklyFixture w32 = new WeeklyFixture(32);
		w32.addAway_team(bayernmunchen);
		w32.addHome_team(darmstadt98);
		w32.addAway_team(borussiadortmund);
		w32.addHome_team(hoffenheim);
		w32.addAway_team(eintrachtfrankfurt);
		w32.addHome_team(wolfsburg);
		w32.addAway_team(freiburg);
		w32.addHome_team(schalke04);
		w32.addAway_team(hamburg);
		w32.addHome_team(mainz05);
		w32.addAway_team(herthaberlin);
		w32.addHome_team(rbleipzig);
		w32.addAway_team(ingolstadt);
		w32.addHome_team(bayerleverkusen);
		w32.addAway_team(koln);
		w32.addHome_team(werderbremen);
		w32.addAway_team(monchengladbach);
		w32.addHome_team(augsburg);
		
		WeeklyFixture w33 = new WeeklyFixture(33);
		w33.addAway_team(augsburg);
		w33.addHome_team(borussiadortmund);
		w33.addAway_team(bayerleverkusen);
		w33.addHome_team(koln);
		w33.addAway_team(darmstadt98);
		w33.addHome_team(herthaberlin);
		w33.addAway_team(freiburg);
		w33.addHome_team(ingolstadt);
		w33.addAway_team(mainz05);
		w33.addHome_team(eintrachtfrankfurt);
		w33.addAway_team(rbleipzig);
		w33.addHome_team(bayernmunchen);
		w33.addAway_team(schalke04);
		w33.addHome_team(hamburg);
		w33.addAway_team(werderbremen);
		w33.addHome_team(hoffenheim);
		w33.addAway_team(wolfsburg);
		w33.addHome_team(monchengladbach);
		
		WeeklyFixture w34 = new WeeklyFixture(34);
		w34.addAway_team(bayernmunchen);
		w34.addHome_team(freiburg);
		w34.addAway_team(borussiadortmund);
		w34.addHome_team(werderbremen);
		w34.addAway_team(eintrachtfrankfurt);
		w34.addHome_team(rbleipzig);
		w34.addAway_team(hamburg);
		w34.addHome_team(wolfsburg);
		w34.addAway_team(herthaberlin);
		w34.addHome_team(bayerleverkusen);
		w34.addAway_team(hoffenheim);
		w34.addHome_team(augsburg);
		w34.addAway_team(ingolstadt);
		w34.addHome_team(schalke04);
		w34.addAway_team(koln);
		w34.addHome_team(mainz05);
		w34.addAway_team(monchengladbach);
		w34.addHome_team(darmstadt98);

		germanLeague.addFixture(w1);
		germanLeague.addFixture(w2);
		germanLeague.addFixture(w3);
		germanLeague.addFixture(w4);
		germanLeague.addFixture(w5);
		germanLeague.addFixture(w6);
		germanLeague.addFixture(w7);
		germanLeague.addFixture(w8);
		germanLeague.addFixture(w9);
		germanLeague.addFixture(w10);
		germanLeague.addFixture(w11);
		germanLeague.addFixture(w12);
		germanLeague.addFixture(w13);
		germanLeague.addFixture(w14);
		germanLeague.addFixture(w15);
		germanLeague.addFixture(w16);
		germanLeague.addFixture(w17);
		germanLeague.addFixture(w18);
		germanLeague.addFixture(w19);
		germanLeague.addFixture(w20);
		germanLeague.addFixture(w21);
		germanLeague.addFixture(w22);
		germanLeague.addFixture(w23);
		germanLeague.addFixture(w24);
		germanLeague.addFixture(w25);
		germanLeague.addFixture(w26);
		germanLeague.addFixture(w27);
		germanLeague.addFixture(w28);
		germanLeague.addFixture(w29);
		germanLeague.addFixture(w30);
		germanLeague.addFixture(w31);
		germanLeague.addFixture(w32);
		germanLeague.addFixture(w33);
		germanLeague.addFixture(w34);
		
		setUserLeague(germanLeague);
		
		Scanner sc = new Scanner(System.in);
		int choice;
		
		System.out.print("1-bayernmunchen\n2-werderbremen\n3-koln\n4-darmstadt98\n5-augsburg\n6-wolfsburg\n7-borussiadortmund\n8-mainz05\n9-eintrachtfrankfurt\n10-schalke04\n11-hamburg\n12-ingolstadt\n13-monchengladbach\n14-bayerleverkusen\n15-herthaberlin\n16-freiburg\n17-hoffenheim\n18-rbleipzig\nSelect a team : ");
		choice=sc.nextInt();
		
		setUserTeam(germanLeagueTeams.get(choice-1));
		
	}
	
	public  void createTurkishLeague() {
		
		League turkishLeague = new League("Turkish League");
		
		Team medipolbasaksehir = new Team("Basaksehir", 0);
		Team besiktas = new Team("Besiktas", 0);
		Team fenerbahce = new Team("Fenerbah?e", 0);
		Team galatasaray = new Team("Galatasaray", 0);
		Team bursaspor = new Team("Bursaspor", 0);
		Team atikerkonyaspor = new Team("Konyaspor", 0);
		Team karabukspor = new Team("Karab?kspor", 0);
		Team osmanlisporfk = new Team("Osmanlispor", 0);
		Team antalyaspor = new Team("Antalyaspor", 0);
		Team genclerbirligi = new Team("Gen?lerbirligi", 0);
		Team aytemizalanyaspor = new Team("Alanyaspor", 0);
		Team akhisarbelediyespor = new Team("Akhisar", 0);
		Team kasimpasa = new Team("Kasimpasa", 0);
		Team trabzonspor = new Team("Trabzonspor", 0);
		Team gaziantepspor = new Team("Gaziantepspor", 0);
		Team caykurrizespor = new Team("Rizespor", 0);
		Team kayserispor = new Team("Kayserispor", 0);
		Team adanaspor = new Team("Adanaspor", 0);	

		medipolbasaksehir.addSquad(new Player("Volkan Babacan", 7750000, 76, "Goalkeeper"));
		medipolbasaksehir.addSquad(new Player("Edin Visca", 7500000, 76, "Midfielder"));
		medipolbasaksehir.addSquad(new Player("Mahmut Tekdemir", 4750000, 73, "Midfielder"));
		medipolbasaksehir.addSquad(new Player("Alexandru Epureanu", 4750000, 73, "Defense"));
		medipolbasaksehir.addSquad(new Player("Eren Albayrak", 3500000, 71, "Defense"));
		medipolbasaksehir.addSquad(new Player("Mustafa Pektemek", 3000000, 70, "Forward"));
		medipolbasaksehir.addSquad(new Player("Ugur U?ar", 2750000, 69, "Defense"));
		medipolbasaksehir.addSquad(new Player("Cengiz ?nder", 2250000, 69, "Midfielder"));
		medipolbasaksehir.addSquad(new Player("Sokol Cikalleshi", 2250000, 69, "Forward"));
		medipolbasaksehir.addSquad(new Player("Bekir Irteg?n", 2000000, 68, "Defense"));
		medipolbasaksehir.addSquad(new Player("Marcio Mossoro", 2000000, 68, "Midfielder"));
		medipolbasaksehir.addSquad(new Player("Mehmet Batdal", 2000000, 68, "Forward"));
		medipolbasaksehir.addSquad(new Player("Joseph Attamah", 1750000, 67, "Midfielder"));
		medipolbasaksehir.addSquad(new Player("Samuel Holmen", 1750000, 67, "Midfielder"));
		medipolbasaksehir.addSquad(new Player("Doka", 1750000, 67, "Midfielder"));
		medipolbasaksehir.addSquad(new Player("Ferhat ?ztorun", 1250000, 66, "Defense"));
		medipolbasaksehir.addSquad(new Player("Alparslan Erdem", 1250000, 66, "Defense"));
		medipolbasaksehir.addSquad(new Player("Yal?in Ayhan", 1250000, 66, "Defense"));
		medipolbasaksehir.addSquad(new Player("Stefano Napoleoni", 1250000, 66, "Forward"));
		medipolbasaksehir.addSquad(new Player("Cenk Ahmet Alkili?", 1000000, 66, "Midfielder"));
		medipolbasaksehir.addSquad(new Player("Ufuk Ceylan", 450000, 64, "Goalkeeper"));
		medipolbasaksehir.addSquad(new Player("Faruk ?akir", 100000, 61, "Goalkeeper"));
		besiktas.addSquad(new Player("Oguzhan ?zyakup", 14000000, 82, "Midfielder"));
		besiktas.addSquad(new Player("Vincent Aboubakar", 11000000, 79, "Forward"));
		besiktas.addSquad(new Player("Caner Erkin", 11000000, 79, "Defense"));
		besiktas.addSquad(new Player("Anderson Talisca", 9250000, 78, "Midfielder"));
		besiktas.addSquad(new Player("Cenk Tosun", 7750000, 76, "Forward"));
		besiktas.addSquad(new Player("G?khan G?n?l", 6500000, 75, "Defense"));
		besiktas.addSquad(new Player("G?khan Inler", 6500000, 75, "Midfielder"));
		besiktas.addSquad(new Player("Olcay Sahan", 5250000, 73, "Midfielder"));
		besiktas.addSquad(new Player("Veli Kavlak", 5250000, 73, "Midfielder"));
		besiktas.addSquad(new Player("Kerim Frei", 5000000, 73, "Midfielder"));
		besiktas.addSquad(new Player("Adriano", 4750000, 73, "Defense"));
		besiktas.addSquad(new Player("Necip Uysal", 4500000, 72, "Midfielder"));
		besiktas.addSquad(new Player("Andreas Beck", 4500000, 72, "Defense"));
		besiktas.addSquad(new Player("Rhodolfo", 4500000, 72, "Defense"));
		besiktas.addSquad(new Player("Ricardo Quaresma", 4000000, 72, "Midfielder"));
		besiktas.addSquad(new Player("Marcelo", 3750000, 71, "Defense"));
		besiktas.addSquad(new Player("?mer Sismanoglu", 3000000, 70, "Forward"));
		besiktas.addSquad(new Player("Tolga Zengin", 2750000, 69, "Goalkeeper"));
		besiktas.addSquad(new Player("Dusko Tosic", 2500000, 69, "Defense"));
		besiktas.addSquad(new Player("Fabri", 1250000, 66, "Goalkeeper"));
		besiktas.addSquad(new Player("Utku Yuvakuran", 150000, 62, "Goalkeeper"));
		besiktas.addSquad(new Player("Hamza K???kk?yl?", 100000, 61, "Forward"));
		fenerbahce.addSquad(new Player("Moussa Sow", 13000000, 81, "Forward"));
		fenerbahce.addSquad(new Player("Simon Kjaer", 11000000, 79, "Defense"));
		fenerbahce.addSquad(new Player("Jeremain Lens", 11000000, 79, "Forward"));
		fenerbahce.addSquad(new Player("Mehmet Topal", 10000000, 78, "Midfielder"));
		fenerbahce.addSquad(new Player("Robin van Persie", 9000000, 78, "Forward"));
		fenerbahce.addSquad(new Player("Emmanuel Emenike", 9000000, 78, "Forward"));
		fenerbahce.addSquad(new Player("Martin Skrtel", 8750000, 77, "Defense"));
		fenerbahce.addSquad(new Player("Josef de Souza", 8500000, 77, "Midfielder"));
		fenerbahce.addSquad(new Player("Fernandao", 8500000, 77, "Forward"));
		fenerbahce.addSquad(new Player("Volkan Sen", 8000000, 76, "Midfielder"));
		fenerbahce.addSquad(new Player("Ozan Tufan", 7500000, 76, "Midfielder"));
		fenerbahce.addSquad(new Player("Alper Potuk", 7500000, 76, "Midfielder"));
		fenerbahce.addSquad(new Player("Gregory van der Wiel", 7250000, 76, "Defense"));
		fenerbahce.addSquad(new Player("Roman Neustadter", 6750000, 75, "Defense"));
		fenerbahce.addSquad(new Player("Sener ?zbayrakli", 5500000, 74, "Defense"));
		fenerbahce.addSquad(new Player("Hasan Ali Kaldirim", 5250000, 73, "Defense"));
		fenerbahce.addSquad(new Player("Miroslav Stoch", 4250000, 72, "Midfielder"));
		fenerbahce.addSquad(new Player("Ismail K?ybasi", 4000000, 72, "Defense"));
		fenerbahce.addSquad(new Player("Fabiano", 3750000, 71, "Goalkeeper"));
		fenerbahce.addSquad(new Player("Aatif Chahechouhe", 3500000, 71, "Midfielder"));
		fenerbahce.addSquad(new Player("Salih U?an", 3500000, 71, "Midfielder"));
		fenerbahce.addSquad(new Player("Volkan Demirel", 2250000, 69, "Goalkeeper"));
		fenerbahce.addSquad(new Player("Ertugrul Taskiran", 1000000, 66, "Goalkeeper"));
		galatasaray.addSquad(new Player("Fernando Muslera", 15000000, 83, "Goalkeeper"));
		galatasaray.addSquad(new Player("Wesley Sneijder", 11000000, 79, "Midfielder"));
		galatasaray.addSquad(new Player("Lukas Podolski", 8000000, 76, "Forward"));
		galatasaray.addSquad(new Player("Aurelien Chedjou", 7500000, 76, "Defense"));
		galatasaray.addSquad(new Player("Sel?uk Inan", 7000000, 75, "Midfielder"));
		galatasaray.addSquad(new Player("Bruma", 6250000, 75, "Midfielder"));
		galatasaray.addSquad(new Player("Semih Kaya", 5500000, 74, "Defense"));
		galatasaray.addSquad(new Player("Serdar Aziz", 5250000, 73, "Defense"));
		galatasaray.addSquad(new Player("Josue", 5000000, 73, "Midfielder"));
		galatasaray.addSquad(new Player("Eren Derdiyok", 4500000, 72, "Forward"));
		galatasaray.addSquad(new Player("Yasin ?ztekin", 4500000, 72, "Midfielder"));
		galatasaray.addSquad(new Player("Luis Cavanda", 3500000, 71, "Defense"));
		galatasaray.addSquad(new Player("Kolbeinn Sigthorsson", 3250000, 70, "Forward"));
		galatasaray.addSquad(new Player("Nigel de Jong", 3250000, 70, "Midfielder"));
		galatasaray.addSquad(new Player("Sinan G?m?s", 3250000, 70, "Forward"));
		galatasaray.addSquad(new Player("Tolga Cigerci", 2750000, 69, "Midfielder"));
		galatasaray.addSquad(new Player("Lionel Carole", 2500000, 69, "Defense"));
		galatasaray.addSquad(new Player("Sabri Sarioglu", 2250000, 69, "Defense"));
		galatasaray.addSquad(new Player("Martin Linnes", 2250000, 69, "Defense"));
		galatasaray.addSquad(new Player("Cenk G?nen", 1250000, 66, "Goalkeeper"));
		galatasaray.addSquad(new Player("Hamit Altintop", 700000, 65, "Midfielder"));
		galatasaray.addSquad(new Player("Kerem ?aliskan", 650000, 64, "Forward"));
		galatasaray.addSquad(new Player("Eray Iscan", 400000, 63, "Goalkeeper"));
		bursaspor.addSquad(new Player("Tomas Necid", 4500000, 72, "Forward"));
		bursaspor.addSquad(new Player("Aziz Behich", 4250000, 72, "Defense"));
		bursaspor.addSquad(new Player("Pablo Batalla", 4000000, 72, "Midfielder"));
		bursaspor.addSquad(new Player("Yonathan del Valle", 3750000, 71, "Forward"));
		bursaspor.addSquad(new Player("Emre Tasdemir", 3250000, 70, "Defense"));
		bursaspor.addSquad(new Player("Tomas Sivok", 2750000, 69, "Defense"));
		bursaspor.addSquad(new Player("Furkan ?z?al", 2500000, 69, "Midfielder"));
		bursaspor.addSquad(new Player("Harun Tekin", 2500000, 69, "Goalkeeper"));
		bursaspor.addSquad(new Player("Mert G?nok", 2500000, 69, "Goalkeeper"));
		bursaspor.addSquad(new Player("Sercan Yildirim", 2250000, 69, "Forward"));
		bursaspor.addSquad(new Player("Deniz Yilmaz", 2000000, 68, "Forward"));
		bursaspor.addSquad(new Player("Cristobal Jorquera", 1500000, 67, "Midfielder"));
		bursaspor.addSquad(new Player("Ricardo Faty", 1250000, 66, "Midfielder"));
		bursaspor.addSquad(new Player("Sinan Bakis", 1250000, 66, "Forward"));
		bursaspor.addSquad(new Player("Bilal Kisa", 1250000, 66, "Midfielder"));
		bursaspor.addSquad(new Player("Ertugrul Ersoy", 1250000, 66, "Defense"));
		bursaspor.addSquad(new Player("Serdar Kurtulus", 1000000, 66, "Defense"));
		bursaspor.addSquad(new Player("Vieux Sane", 1000000, 66, "Defense"));
		bursaspor.addSquad(new Player("Samil Cinaz", 750000, 65, "Midfielder"));
		bursaspor.addSquad(new Player("Merter Y?ce", 700000, 65, "Midfielder"));
		bursaspor.addSquad(new Player("Ismail Konuk", 600000, 64, "Defense"));
		bursaspor.addSquad(new Player("Mert ?rnek", 350000, 63, "Midfielder"));
		bursaspor.addSquad(new Player("Ataberk Dadakdeniz", 150000, 62, "Goalkeeper"));
		atikerkonyaspor.addSquad(new Player("?mer Ali Sahiner", 5250000, 73, "Midfielder"));
		atikerkonyaspor.addSquad(new Player("Riad Bajic", 3500000, 71, "Forward"));
		atikerkonyaspor.addSquad(new Player("Jagos Vukovic", 2750000, 69, "Defense"));
		atikerkonyaspor.addSquad(new Player("Selim Ay", 2250000, 69, "Defense"));
		atikerkonyaspor.addSquad(new Player("Nejc Skubic", 1750000, 67, "Defense"));
		atikerkonyaspor.addSquad(new Player("Abd?lkerim Bardak?i", 1750000, 67, "Defense"));
		atikerkonyaspor.addSquad(new Player("Alban Meha", 1750000, 67, "Midfielder"));
		atikerkonyaspor.addSquad(new Player("Mehmet Uslu", 1500000, 67, "Defense"));
		atikerkonyaspor.addSquad(new Player("Ioan Hora", 1250000, 66, "Forward"));
		atikerkonyaspor.addSquad(new Player("Ali ?amdali", 1250000, 66, "Midfielder"));
		atikerkonyaspor.addSquad(new Player("Serkan Kirintili", 1000000, 66, "Goalkeeper"));
		atikerkonyaspor.addSquad(new Player("Barry Douglas", 1000000, 66, "Defense"));
		atikerkonyaspor.addSquad(new Player("Ibrahim Sissoko", 850000, 65, "Midfielder"));
		atikerkonyaspor.addSquad(new Player("Amir Hadziahmetovic", 800000, 65, "Midfielder"));
		atikerkonyaspor.addSquad(new Player("Kibong Mbamba", 750000, 65, "Midfielder"));
		atikerkonyaspor.addSquad(new Player("Halil Ibrahim S?nmez", 750000, 65, "Forward"));
		atikerkonyaspor.addSquad(new Player("Vedat Bora", 700000, 65, "Midfielder"));
		atikerkonyaspor.addSquad(new Player("Volkan Findikli", 700000, 65, "Midfielder"));
		atikerkonyaspor.addSquad(new Player("Abd?laziz Demircan", 550000, 64, "Goalkeeper"));
		atikerkonyaspor.addSquad(new Player("Ali Turan", 350000, 63, "Defense"));
		atikerkonyaspor.addSquad(new Player("Dimitar Rangelov", 300000, 63, "Forward"));
		atikerkonyaspor.addSquad(new Player("Ataberk G?rgen", 200000, 62, "Goalkeeper"));
		karabukspor.addSquad(new Player("Abdou Razack Traore", 3750000, 71, "Midfielder"));
		karabukspor.addSquad(new Player("Andre Poko", 3000000, 70, "Midfielder"));
		karabukspor.addSquad(new Player("Dejan Lazarevic", 2250000, 69, "Midfielder"));
		karabukspor.addSquad(new Player("Valerica Gaman", 2250000, 69, "Defense"));
		karabukspor.addSquad(new Player("Mustapha Yatabare", 2250000, 69, "Forward"));
		karabukspor.addSquad(new Player("Ermin Zec", 2000000, 68, "Midfielder"));
		karabukspor.addSquad(new Player("Elvis Kokalovic", 1750000, 67, "Defense"));
		karabukspor.addSquad(new Player("Marius Alexe", 1250000, 66, "Forward"));
		karabukspor.addSquad(new Player("Serdar Deliktas", 1250000, 66, "Forward"));
		karabukspor.addSquad(new Player("Dany Nounkeu", 1000000, 66, "Defense"));
		karabukspor.addSquad(new Player("Osman ?elik", 750000, 65, "Midfielder"));
		karabukspor.addSquad(new Player("Baris Basdas", 750000, 65, "Defense"));
		karabukspor.addSquad(new Player("Ceyhun G?lselam", 700000, 65, "Midfielder"));
		karabukspor.addSquad(new Player("Hakan Aslantas", 700000, 65, "Defense"));
		karabukspor.addSquad(new Player("Cristian Tanase", 650000, 64, "Midfielder"));
		karabukspor.addSquad(new Player("Vladimir Rodic", 600000, 64, "Midfielder"));
		karabukspor.addSquad(new Player("Iasmin Latovlevici", 450000, 64, "Defense"));
		karabukspor.addSquad(new Player("Adriano", 450000, 64, "Goalkeeper"));
		karabukspor.addSquad(new Player("Isaac Promise", 450000, 64, "Forward"));
		karabukspor.addSquad(new Player("Kerim Zengin", 250000, 63, "Defense"));
		karabukspor.addSquad(new Player("Ahmet Sahin", 150000, 62, "Goalkeeper"));
		karabukspor.addSquad(new Player("Erc?ment Kafkasyali", 50000, 61, "Goalkeeper"));
		osmanlisporfk.addSquad(new Player("Adam Maher", 7000000, 75, "Midfielder"));
		osmanlisporfk.addSquad(new Player("Cheick Diabate", 4500000, 72, "Forward"));
		osmanlisporfk.addSquad(new Player("Badou Ndiaye", 4250000, 72, "Midfielder"));
		osmanlisporfk.addSquad(new Player("Umar Aminu", 4250000, 72, "Midfielder"));
		osmanlisporfk.addSquad(new Player("Musa ?agiran", 3500000, 71, "Midfielder"));
		osmanlisporfk.addSquad(new Player("Aykut Demir", 3500000, 71, "Defense"));
		osmanlisporfk.addSquad(new Player("Raul Rusescu", 2750000, 69, "Forward"));
		osmanlisporfk.addSquad(new Player("Tiago Pinto", 2500000, 69, "Defense"));
		osmanlisporfk.addSquad(new Player("Koray Altinay", 2500000, 69, "Defense"));
		osmanlisporfk.addSquad(new Player("Raheem Lawal", 2500000, 69, "Midfielder"));
		osmanlisporfk.addSquad(new Player("Adrien Regattin", 2250000, 69, "Midfielder"));
		osmanlisporfk.addSquad(new Player("Mehmet G?ven", 2000000, 68, "Midfielder"));
		osmanlisporfk.addSquad(new Player("Zydrunas Karcemarskas", 1500000, 67, "Goalkeeper"));
		osmanlisporfk.addSquad(new Player("Tonia Tisdell", 1500000, 67, "Midfielder"));
		osmanlisporfk.addSquad(new Player("Avdija Vrsajevic", 1500000, 67, "Defense"));
		osmanlisporfk.addSquad(new Player("Lukasz Szukala", 1250000, 66, "Defense"));
		osmanlisporfk.addSquad(new Player("Muhammed Bayir", 1250000, 66, "Defense"));
		osmanlisporfk.addSquad(new Player("Dzon Delarge", 1000000, 66, "Forward"));
		osmanlisporfk.addSquad(new Player("Pierre Webo", 750000, 65, "Forward"));
		osmanlisporfk.addSquad(new Player("Vaclav Prochazka", 350000, 63, "Defense"));
		osmanlisporfk.addSquad(new Player("Hakan Arikan", 250000, 63, "Goalkeeper"));
		osmanlisporfk.addSquad(new Player("Ahmet Ey?p T?rkaslan", 200000, 62, "Goalkeeper"));
		antalyaspor.addSquad(new Player("Diego Angelo", 2500000, 69, "Defense"));
		antalyaspor.addSquad(new Player("M'Billa Etame", 2250000, 69, "Forward"));
		antalyaspor.addSquad(new Player("Ramon Motta", 2000000, 68, "Defense"));
		antalyaspor.addSquad(new Player("Samuel Eto'o", 2000000, 68, "Forward"));
		antalyaspor.addSquad(new Player("Deniz Kadah", 1750000, 67, "Forward"));
		antalyaspor.addSquad(new Player("Sakib Ayta?", 1750000, 67, "Defense"));
		antalyaspor.addSquad(new Player("Yekta Kurtulus", 1750000, 67, "Midfielder"));
		antalyaspor.addSquad(new Player("Serdar ?zkan", 1750000, 67, "Midfielder"));
		antalyaspor.addSquad(new Player("Danilo", 1250000, 66, "Midfielder"));
		antalyaspor.addSquad(new Player("Samuel Inkoom", 1250000, 66, "Defense"));
		antalyaspor.addSquad(new Player("Ondrej Celustka", 1250000, 66, "Defense"));
		antalyaspor.addSquad(new Player("Chico", 1250000, 66, "Midfielder"));
		antalyaspor.addSquad(new Player("Zeki Yildirim", 1250000, 66, "Midfielder"));
		antalyaspor.addSquad(new Player("Emre G?ral", 1000000, 66, "Forward"));
		antalyaspor.addSquad(new Player("Charles", 850000, 65, "Midfielder"));
		antalyaspor.addSquad(new Player("Kenan Horic", 800000, 65, "Midfielder"));
		antalyaspor.addSquad(new Player("Ridvan Simsek", 750000, 65, "Defense"));
		antalyaspor.addSquad(new Player("Saso Fornezzi", 700000, 65, "Goalkeeper"));
		antalyaspor.addSquad(new Player("Ferhat Kaplan", 650000, 64, "Goalkeeper"));
		antalyaspor.addSquad(new Player("Ozan ?zen?", 550000, 64, "Goalkeeper"));
		antalyaspor.addSquad(new Player("Milan Jevtovic", 500000, 64, "Midfielder"));
		antalyaspor.addSquad(new Player("Lamine Diarra", 450000, 64, "Forward"));
		antalyaspor.addSquad(new Player("Birkan ?ks?z", 350000, 63, "Defense"));
		genclerbirligi.addSquad(new Player("Ahmet ?alik", 5250000, 73, "Defense"));
		genclerbirligi.addSquad(new Player("Bogdan Stancu", 3750000, 71, "Forward"));
		genclerbirligi.addSquad(new Player("Ugur ?ift?i", 2750000, 69, "Defense"));
		genclerbirligi.addSquad(new Player("Irfan Can Kahveci", 2750000, 69, "Midfielder"));
		genclerbirligi.addSquad(new Player("Abdul Khalili", 2250000, 69, "Midfielder"));
		genclerbirligi.addSquad(new Player("Michel Landel", 1750000, 67, "Midfielder"));
		genclerbirligi.addSquad(new Player("Ante Kulusic", 1500000, 67, "Defense"));
		genclerbirligi.addSquad(new Player("Serdar G?rler", 1250000, 66, "Midfielder"));
		genclerbirligi.addSquad(new Player("Tokelo Rantie", 1250000, 66, "Forward"));
		genclerbirligi.addSquad(new Player("Johannes Hopf", 1250000, 66, "Goalkeeper"));
		genclerbirligi.addSquad(new Player("Ahmet Oguz", 1000000, 66, "Defense"));
		genclerbirligi.addSquad(new Player("Sergey Politevich", 950000, 65, "Defense"));
		genclerbirligi.addSquad(new Player("Vedat Muriqi", 850000, 65, "Forward"));
		genclerbirligi.addSquad(new Player("Cosmin Matei", 750000, 65, "Midfielder"));
		genclerbirligi.addSquad(new Player("Marko Milinkovic", 600000, 64, "Midfielder"));
		genclerbirligi.addSquad(new Player("Samuel Owusu", 600000, 64, "Midfielder"));
		genclerbirligi.addSquad(new Player("Aydin Karabulut", 450000, 64, "Midfielder"));
		genclerbirligi.addSquad(new Player("Halil Ibrahim Pehlivan", 450000, 64, "Defense"));
		genclerbirligi.addSquad(new Player("Nihat Sahin", 400000, 63, "Goalkeeper"));
		genclerbirligi.addSquad(new Player("Orhan Sam", 250000, 63, "Defense"));
		genclerbirligi.addSquad(new Player("Serdarcan Eralp", 200000, 62, "Forward"));
		genclerbirligi.addSquad(new Player("Taha Demirtas", 100000, 61, "Goalkeeper"));
		genclerbirligi.addSquad(new Player("Tugrul Baskan", 50000, 61, "Forward"));
		aytemizalanyaspor.addSquad(new Player("Ismail Aissati", 3750000, 71, "Midfielder"));
		aytemizalanyaspor.addSquad(new Player("Vagner Love", 3250000, 70, "Forward"));
		aytemizalanyaspor.addSquad(new Player("Sefa Yilmaz", 2750000, 69, "Midfielder"));
		aytemizalanyaspor.addSquad(new Player("Abdoulaye Ba", 2250000, 69, "Defense"));
		aytemizalanyaspor.addSquad(new Player("Emre Akbaba", 2250000, 69, "Midfielder"));
		aytemizalanyaspor.addSquad(new Player("Kenneth Omeruo", 2000000, 68, "Defense"));
		aytemizalanyaspor.addSquad(new Player("Carlos Garcia", 1250000, 66, "Defense"));
		aytemizalanyaspor.addSquad(new Player("Lamine Gassama", 1250000, 66, "Defense"));
		aytemizalanyaspor.addSquad(new Player("Deniz Vural", 1250000, 66, "Midfielder"));
		aytemizalanyaspor.addSquad(new Player("Fabrice N'Sakala", 1250000, 66, "Defense"));
		aytemizalanyaspor.addSquad(new Player("Birol Parlak", 1000000, 66, "Defense"));
		aytemizalanyaspor.addSquad(new Player("Erhan Kartal", 1000000, 66, "Defense"));
		aytemizalanyaspor.addSquad(new Player("Daniel Candeias", 1000000, 66, "Midfielder"));
		aytemizalanyaspor.addSquad(new Player("Sajjad Shahbazzadeh", 950000, 65, "Forward"));
		aytemizalanyaspor.addSquad(new Player("G?kay Iravul", 800000, 65, "Midfielder"));
		aytemizalanyaspor.addSquad(new Player("Donald Guerrier", 600000, 64, "Midfielder"));
		aytemizalanyaspor.addSquad(new Player("Isaac Sackey", 550000, 64, "Midfielder"));
		aytemizalanyaspor.addSquad(new Player("Zdenek Zlamal", 500000, 64, "Goalkeeper"));
		aytemizalanyaspor.addSquad(new Player("Jonathan Ayite", 500000, 64, "Forward"));
		aytemizalanyaspor.addSquad(new Player("Haydar Yilmaz", 150000, 62, "Goalkeeper"));
		aytemizalanyaspor.addSquad(new Player("Alisan Seker", 50000, 61, "Goalkeeper"));
		akhisarbelediyespor.addSquad(new Player("?zer Hurmaci", 4250000, 72, "Midfielder"));
		akhisarbelediyespor.addSquad(new Player("Hugo Rodallega", 4000000, 72, "Forward"));
		akhisarbelediyespor.addSquad(new Player("Douglao", 3750000, 71, "Defense"));
		akhisarbelediyespor.addSquad(new Player("?mer Bayram", 3000000, 70, "Midfielder"));
		akhisarbelediyespor.addSquad(new Player("Miguel Lopes", 2750000, 69, "Defense"));
		akhisarbelediyespor.addSquad(new Player("Sami", 2250000, 69, "Forward"));
		akhisarbelediyespor.addSquad(new Player("Ricardo Vaz Te", 1750000, 67, "Forward"));
		akhisarbelediyespor.addSquad(new Player("Abdoul Sissoko", 1750000, 67, "Midfielder"));
		akhisarbelediyespor.addSquad(new Player("Mervan ?elik", 1750000, 67, "Forward"));
		akhisarbelediyespor.addSquad(new Player("Landry N'Guemo", 1250000, 66, "Midfielder"));
		akhisarbelediyespor.addSquad(new Player("Kadir Keles", 1250000, 66, "Defense"));
		akhisarbelediyespor.addSquad(new Player("Tolga ?nl?", 1250000, 66, "Defense"));
		akhisarbelediyespor.addSquad(new Player("Caner Osmanpasa", 1250000, 66, "Defense"));
		akhisarbelediyespor.addSquad(new Player("Milan Lukac", 1250000, 66, "Goalkeeper"));
		akhisarbelediyespor.addSquad(new Player("Mugdat ?elik", 800000, 65, "Forward"));
		akhisarbelediyespor.addSquad(new Player("Custodio", 800000, 65, "Midfielder"));
		akhisarbelediyespor.addSquad(new Player("Aykut ?eviker", 800000, 65, "Midfielder"));
		akhisarbelediyespor.addSquad(new Player("Onur Ayik", 750000, 65, "Midfielder"));
		akhisarbelediyespor.addSquad(new Player("Soner Aydogdu", 750000, 65, "Midfielder"));
		akhisarbelediyespor.addSquad(new Player("Alper Uludag", 650000, 64, "Defense"));
		akhisarbelediyespor.addSquad(new Player("Fatih ?zt?rk", 600000, 64, "Goalkeeper"));
		akhisarbelediyespor.addSquad(new Player("Orhan Tasdelen", 550000, 64, "Defense"));
		akhisarbelediyespor.addSquad(new Player("Bora K?rk", 150000, 62, "Goalkeeper"));
		kasimpasa.addSquad(new Player("Andre Castro", 6000000, 74, "Midfielder"));
		kasimpasa.addSquad(new Player("Tunay Torun", 4500000, 72, "Midfielder"));
		kasimpasa.addSquad(new Player("Adem B?y?k", 3750000, 71, "Forward"));
		kasimpasa.addSquad(new Player("Loret Sadiku", 2750000, 69, "Defense"));
		kasimpasa.addSquad(new Player("Titi", 2250000, 69, "Defense"));
		kasimpasa.addSquad(new Player("Strahil Popov", 2250000, 69, "Defense"));
		kasimpasa.addSquad(new Player("Ramazan K?se", 2000000, 68, "Goalkeeper"));
		kasimpasa.addSquad(new Player("David Pavelka", 1750000, 67, "Midfielder"));
		kasimpasa.addSquad(new Player("Olivier Veigneau", 1750000, 67, "Defense"));
		kasimpasa.addSquad(new Player("Veysel Sari", 1750000, 67, "Defense"));
		kasimpasa.addSquad(new Player("Abdullah Durak", 1500000, 67, "Midfielder"));
		kasimpasa.addSquad(new Player("Herolind Shala", 1500000, 67, "Midfielder"));
		kasimpasa.addSquad(new Player("Kenny Otigba", 1500000, 67, "Defense"));
		kasimpasa.addSquad(new Player("Vasil Bozhikov", 1250000, 66, "Defense"));
		kasimpasa.addSquad(new Player("Ferhat Kiraz", 1250000, 66, "Midfielder"));
		kasimpasa.addSquad(new Player("Fode Koita", 650000, 64, "Forward"));
		kasimpasa.addSquad(new Player("Franck Etoundi", 550000, 64, "Forward"));
		kasimpasa.addSquad(new Player("Turgut Dogan Sahin", 450000, 64, "Midfielder"));
		kasimpasa.addSquad(new Player("Batuhan Altintas", 350000, 63, "Forward"));
		kasimpasa.addSquad(new Player("Ismail Ayaz", 350000, 63, "Midfielder"));
		kasimpasa.addSquad(new Player("Eray Birni?an", 200000, 62, "Goalkeeper"));
		kasimpasa.addSquad(new Player("Samuel Eduok", 200000, 62, "Forward"));
		kasimpasa.addSquad(new Player("Emirhan Emir", 150000, 62, "Goalkeeper"));
		trabzonspor.addSquad(new Player("Onur Kivrak", 7250000, 76, "Goalkeeper"));
		trabzonspor.addSquad(new Player("Mehmet Ekici", 7000000, 75, "Midfielder"));
		trabzonspor.addSquad(new Player("Ogenyi Onazi", 5250000, 73, "Midfielder"));
		trabzonspor.addSquad(new Player("Muhammet Demir", 4750000, 73, "Forward"));
		trabzonspor.addSquad(new Player("Yusuf Erdogan", 4500000, 72, "Midfielder"));
		trabzonspor.addSquad(new Player("Ugur Demirok", 4000000, 72, "Defense"));
		trabzonspor.addSquad(new Player("G?ray Vural", 3500000, 71, "Midfielder"));
		trabzonspor.addSquad(new Player("Okay Yokuslu", 3250000, 70, "Midfielder"));
		trabzonspor.addSquad(new Player("Dame N'Doye", 3250000, 70, "Forward"));
		trabzonspor.addSquad(new Player("Hyun-Jun Suk", 3000000, 70, "Forward"));
		trabzonspor.addSquad(new Player("Esteban", 2750000, 69, "Goalkeeper"));
		trabzonspor.addSquad(new Player("Luis Ibanez", 2500000, 69, "Defense"));
		trabzonspor.addSquad(new Player("Mustafa Yumlu", 2250000, 69, "Defense"));
		trabzonspor.addSquad(new Player("Zeki Yavru", 2250000, 69, "Defense"));
		trabzonspor.addSquad(new Player("Ayta? Kara", 2000000, 68, "Midfielder"));
		trabzonspor.addSquad(new Player("Mustafa Akbas", 1750000, 67, "Defense"));
		trabzonspor.addSquad(new Player("Serge Akakpo", 1500000, 67, "Defense"));
		trabzonspor.addSquad(new Player("Fabian Castillo", 1200000, 66, "Forward"));
		trabzonspor.addSquad(new Player("Matus Bero", 1000000, 66, "Midfielder"));
		trabzonspor.addSquad(new Player("Ramil Sheydaev", 750000, 65, "Forward"));
		trabzonspor.addSquad(new Player("Yusuf Yazici", 750000, 65, "Midfielder"));
		trabzonspor.addSquad(new Player("Ugurcan ?akir", 450000, 64, "Goalkeeper"));
		trabzonspor.addSquad(new Player("Jan Durica", 350000, 63, "Defense"));
		gaziantepspor.addSquad(new Player("Nabil Ghilas", 4000000, 72, "Forward"));
		gaziantepspor.addSquad(new Player("Baris Yardimci", 2000000, 68, "Defense"));
		gaziantepspor.addSquad(new Player("Daniel Kolar", 2000000, 68, "Midfielder"));
		gaziantepspor.addSquad(new Player("Orkan ?inar", 1750000, 67, "Midfielder"));
		gaziantepspor.addSquad(new Player("Daniel Larsson", 1750000, 67, "Forward"));
		gaziantepspor.addSquad(new Player("Frantisek Rajtoral", 1750000, 67, "Defense"));
		gaziantepspor.addSquad(new Player("Abd?lkadir Kayali", 1500000, 67, "Midfielder"));
		gaziantepspor.addSquad(new Player("Anton Putsila", 1250000, 66, "Midfielder"));
		gaziantepspor.addSquad(new Player("Charles Itandje", 1250000, 66, "Goalkeeper"));
		gaziantepspor.addSquad(new Player("Emre Nefiz", 1250000, 66, "Midfielder"));
		gaziantepspor.addSquad(new Player("Sergey Kislyak", 1250000, 66, "Midfielder"));
		gaziantepspor.addSquad(new Player("Muhammed Ildiz", 850000, 65, "Midfielder"));
		gaziantepspor.addSquad(new Player("Musa Nizam", 800000, 65, "Defense"));
		gaziantepspor.addSquad(new Player("Bart van Hintum", 700000, 65, "Defense"));
		gaziantepspor.addSquad(new Player("G?khan Degirmenci", 650000, 64, "Goalkeeper"));
		gaziantepspor.addSquad(new Player("Evans Kangwa", 650000, 64, "Forward"));
		gaziantepspor.addSquad(new Player("Alpay Ko?akli", 550000, 64, "Midfielder"));
		gaziantepspor.addSquad(new Player("Ilhan Parlak", 500000, 64, "Forward"));
		gaziantepspor.addSquad(new Player("Erten Ersu", 500000, 64, "Goalkeeper"));
		gaziantepspor.addSquad(new Player("Elyasa S?me", 450000, 64, "Defense"));
		gaziantepspor.addSquad(new Player("Davy Claude Angan", 450000, 64, "Forward"));
		gaziantepspor.addSquad(new Player("Senol Can", 250000, 63, "Defense"));
		gaziantepspor.addSquad(new Player("Ilker G?naslan", 100000, 61, "Defense"));
		caykurrizespor.addSquad(new Player("Emrah Bassan", 4500000, 72, "Midfielder"));
		caykurrizespor.addSquad(new Player("Leonard Kweuke", 3250000, 70, "Forward"));
		caykurrizespor.addSquad(new Player("Godfrey Oboabona", 2500000, 69, "Defense"));
		caykurrizespor.addSquad(new Player("Ogulcan ?aglayan", 2000000, 68, "Forward"));
		caykurrizespor.addSquad(new Player("Jakob Jantscher", 1750000, 67, "Midfielder"));
		caykurrizespor.addSquad(new Player("Ahmet Ilhan ?zek", 1750000, 67, "Midfielder"));
		caykurrizespor.addSquad(new Player("?mit Kurt", 1250000, 66, "Defense"));
		caykurrizespor.addSquad(new Player("Orhan Ovacikli", 1250000, 66, "Defense"));
		caykurrizespor.addSquad(new Player("Patryk Tuszynski", 1250000, 66, "Forward"));
		caykurrizespor.addSquad(new Player("Abdoulaye Diallo", 1250000, 66, "Goalkeeper"));
		caykurrizespor.addSquad(new Player("Recep Niyaz", 1250000, 66, "Midfielder"));
		caykurrizespor.addSquad(new Player("?zg?r ?ek", 1000000, 66, "Defense"));
		caykurrizespor.addSquad(new Player("Robin Yal?in", 950000, 65, "Midfielder"));
		caykurrizespor.addSquad(new Player("Dhurgham Ismail", 900000, 65, "Defense"));
		caykurrizespor.addSquad(new Player("G?khan Akkan", 850000, 65, "Goalkeeper"));
		caykurrizespor.addSquad(new Player("Mehmet Aky?z", 750000, 65, "Forward"));
		caykurrizespor.addSquad(new Player("Davide Petrucci", 650000, 64, "Midfielder"));
		caykurrizespor.addSquad(new Player("Oguz Han Aynaoglu", 550000, 64, "Midfielder"));
		caykurrizespor.addSquad(new Player("Volkan Pala", 500000, 64, "Forward"));
		caykurrizespor.addSquad(new Player("Ali Yacoubi", 450000, 64, "Defense"));
		caykurrizespor.addSquad(new Player("Furkan Simsek", 300000, 63, "Midfielder"));
		caykurrizespor.addSquad(new Player("Matic Fink", 250000, 63, "Defense"));
		caykurrizespor.addSquad(new Player("Alperen Uysal", 150000, 62, "Goalkeeper"));
		kayserispor.addSquad(new Player("Larrys Mabiala", 2750000, 69, "Defense"));
		kayserispor.addSquad(new Player("Srdjan Mijailovic", 2250000, 69, "Midfielder"));
		kayserispor.addSquad(new Player("Ali Ahamada", 2250000, 69, "Goalkeeper"));
		kayserispor.addSquad(new Player("Prejuce Nakoulma", 2250000, 69, "Forward"));
		kayserispor.addSquad(new Player("Welliton", 2250000, 69, "Forward"));
		kayserispor.addSquad(new Player("Jean Armel Kana-Biyik", 2000000, 68, "Defense"));
		kayserispor.addSquad(new Player("Murat Duruer", 1750000, 67, "Midfielder"));
		kayserispor.addSquad(new Player("Alain Traore", 1250000, 66, "Midfielder"));
		kayserispor.addSquad(new Player("Deniz T?r??", 1250000, 66, "Midfielder"));
		kayserispor.addSquad(new Player("Umut Bulut", 1250000, 66, "Forward"));
		kayserispor.addSquad(new Player("Samba Sow", 1000000, 66, "Midfielder"));
		kayserispor.addSquad(new Player("Levent G?len", 950000, 65, "Defense"));
		kayserispor.addSquad(new Player("Muammer Yildirim", 650000, 64, "Goalkeeper"));
		kayserispor.addSquad(new Player("Kubilay S?nmez", 600000, 64, "Midfielder"));
		kayserispor.addSquad(new Player("Anil Karaer", 350000, 63, "Defense"));
		kayserispor.addSquad(new Player("Erkam Resmen", 350000, 63, "Defense"));
		kayserispor.addSquad(new Player("Furkan Yaman", 250000, 63, "Forward"));
		kayserispor.addSquad(new Player("Ufuk Budak", 250000, 63, "Defense"));
		kayserispor.addSquad(new Player("Umut S?nmez", 150000, 62, "Midfielder"));
		kayserispor.addSquad(new Player("Mert Iliman", 150000, 62, "Midfielder"));
		kayserispor.addSquad(new Player("Vedat Karakus", 100000, 61, "Goalkeeper"));
		kayserispor.addSquad(new Player("Mert ?zyildirim", 100000, 61, "Defense"));
		adanaspor.addSquad(new Player("Bekir Yilmaz", 3000000, 70, "Midfielder"));
		adanaspor.addSquad(new Player("Edgar Silva", 2000000, 68, "Forward"));
		adanaspor.addSquad(new Player("Ousmane Viera", 1750000, 67, "Defense"));
		adanaspor.addSquad(new Player("Mauricio Ramos", 1000000, 66, "Defense"));
		adanaspor.addSquad(new Player("Mert Aky?z", 1000000, 66, "Goalkeeper"));
		adanaspor.addSquad(new Player("Magaye Gueye", 900000, 65, "Forward"));
		adanaspor.addSquad(new Player("Emre Uru?", 800000, 65, "Defense"));
		adanaspor.addSquad(new Player("Goran Karacic", 800000, 65, "Goalkeeper"));
		adanaspor.addSquad(new Player("Samican Keskin", 750000, 65, "Midfielder"));
		adanaspor.addSquad(new Player("Didi", 750000, 65, "Defense"));
		adanaspor.addSquad(new Player("Vladimir Koman", 650000, 64, "Midfielder"));
		adanaspor.addSquad(new Player("Roni", 600000, 64, "Midfielder"));
		adanaspor.addSquad(new Player("Canberk Dilaver", 550000, 64, "Defense"));
		adanaspor.addSquad(new Player("Tevfik Altindag", 550000, 64, "Midfielder"));
		adanaspor.addSquad(new Player("Renan Foguinho", 500000, 64, "Midfielder"));
		adanaspor.addSquad(new Player("Ethem P?lgir", 450000, 64, "Defense"));
		adanaspor.addSquad(new Player("Cem ?zdemir", 450000, 64, "Midfielder"));
		adanaspor.addSquad(new Player("Ahmet Dereli", 250000, 63, "Forward"));
		adanaspor.addSquad(new Player("Digao", 250000, 63, "Defense"));
		adanaspor.addSquad(new Player("Ahmet Bah?ivan", 200000, 62, "Midfielder"));
		adanaspor.addSquad(new Player("Irfan Can Egribayat", 50000, 61, "Goalkeeper"));
		
		List<Team> turkishLeagueTeams = new ArrayList<Team>(){
			
			{
				add(medipolbasaksehir);
				add(besiktas);
				add(fenerbahce);
				add(galatasaray);
				add(bursaspor);
				add(atikerkonyaspor);
				add(karabukspor);
				add(osmanlisporfk);
				add(antalyaspor);
				add(genclerbirligi);
				add(aytemizalanyaspor);
				add(akhisarbelediyespor);
				add(kasimpasa);
				add(trabzonspor);
				add(gaziantepspor);
				add(caykurrizespor);
				add(kayserispor);
				add(adanaspor);
				
			}
			
		};
		
		
		turkishLeague.setTeams(turkishLeagueTeams);
	
		WeeklyFixture w1 = new WeeklyFixture(1);
		w1.addAway_team(adanaspor);
		w1.addHome_team(bursaspor);
		w1.addAway_team(caykurrizespor);
		w1.addHome_team(atikerkonyaspor);
		w1.addAway_team(trabzonspor);
		w1.addHome_team(kasimpasa);
		w1.addAway_team(besiktas);
		w1.addHome_team(aytemizalanyaspor);
		w1.addAway_team(kayserispor);
		w1.addHome_team(akhisarbelediyespor);
		w1.addAway_team(genclerbirligi);
		w1.addHome_team(gaziantepspor);
		w1.addAway_team(antalyaspor);
		w1.addHome_team(osmanlisporfk);
		w1.addAway_team(medipolbasaksehir);
		w1.addHome_team(fenerbahce);
		w1.addAway_team(galatasaray);
		w1.addHome_team(karabukspor);
		
		WeeklyFixture w2 = new WeeklyFixture(2);
		w2.addAway_team(atikerkonyaspor);
		w2.addHome_team(besiktas);
		w2.addAway_team(aytemizalanyaspor);
		w2.addHome_team(antalyaspor);
		w2.addAway_team(kasimpasa);
		w2.addHome_team(adanaspor);
		w2.addAway_team(akhisarbelediyespor);
		w2.addHome_team(galatasaray);
		w2.addAway_team(gaziantepspor);
		w2.addHome_team(trabzonspor);
		w2.addAway_team(karabukspor);
		w2.addHome_team(caykurrizespor);
		w2.addAway_team(bursaspor);
		w2.addHome_team(medipolbasaksehir);
		w2.addAway_team(fenerbahce);
		w2.addHome_team(kayserispor);
		w2.addAway_team(osmanlisporfk);
		w2.addHome_team(genclerbirligi);
		
		WeeklyFixture w3 = new WeeklyFixture(3);
		w3.addAway_team(medipolbasaksehir);
		w3.addHome_team(kasimpasa);
		w3.addAway_team(kayserispor);
		w3.addHome_team(galatasaray);
		w3.addAway_team(adanaspor);
		w3.addHome_team(gaziantepspor);
		w3.addAway_team(genclerbirligi);
		w3.addHome_team(aytemizalanyaspor);
		w3.addAway_team(besiktas);
		w3.addHome_team(karabukspor);
		w3.addAway_team(caykurrizespor);
		w3.addHome_team(akhisarbelediyespor);
		w3.addAway_team(trabzonspor);
		w3.addHome_team(osmanlisporfk);
		w3.addAway_team(fenerbahce);
		w3.addHome_team(bursaspor);
		w3.addAway_team(antalyaspor);
		w3.addHome_team(atikerkonyaspor);
		
		WeeklyFixture w4 = new WeeklyFixture(4);
		w4.addAway_team(karabukspor);
		w4.addHome_team(antalyaspor);
		w4.addAway_team(bursaspor);
		w4.addHome_team(kayserispor);
		w4.addAway_team(aytemizalanyaspor);
		w4.addHome_team(trabzonspor);
		w4.addAway_team(galatasaray);
		w4.addHome_team(caykurrizespor);
		w4.addAway_team(osmanlisporfk);
		w4.addHome_team(adanaspor);
		w4.addAway_team(atikerkonyaspor);
		w4.addHome_team(genclerbirligi);
		w4.addAway_team(gaziantepspor);
		w4.addHome_team(medipolbasaksehir);
		w4.addAway_team(akhisarbelediyespor);
		w4.addHome_team(besiktas);
		w4.addAway_team(kasimpasa);
		w4.addHome_team(fenerbahce);
		
		WeeklyFixture w5 = new WeeklyFixture(5);
		w5.addAway_team(antalyaspor);
		w5.addHome_team(akhisarbelediyespor);
		w5.addAway_team(medipolbasaksehir);
		w5.addHome_team(osmanlisporfk);
		w5.addAway_team(trabzonspor);
		w5.addHome_team(atikerkonyaspor);
		w5.addAway_team(besiktas);
		w5.addHome_team(galatasaray);
		w5.addAway_team(kayserispor);
		w5.addHome_team(caykurrizespor);
		w5.addAway_team(bursaspor);
		w5.addHome_team(kasimpasa);
		w5.addAway_team(fenerbahce);
		w5.addHome_team(gaziantepspor);
		w5.addAway_team(genclerbirligi);
		w5.addHome_team(karabukspor);
		w5.addAway_team(adanaspor);
		w5.addHome_team(aytemizalanyaspor);
		
		WeeklyFixture w6 = new WeeklyFixture(6);
		w6.addAway_team(akhisarbelediyespor);
		w6.addHome_team(genclerbirligi);
		w6.addAway_team(karabukspor);
		w6.addHome_team(trabzonspor);
		w6.addAway_team(kasimpasa);
		w6.addHome_team(kayserispor);
		w6.addAway_team(gaziantepspor);
		w6.addHome_team(bursaspor);
		w6.addAway_team(caykurrizespor);
		w6.addHome_team(besiktas);
		w6.addAway_team(aytemizalanyaspor);
		w6.addHome_team(medipolbasaksehir);
		w6.addAway_team(galatasaray);
		w6.addHome_team(antalyaspor);
		w6.addAway_team(atikerkonyaspor);
		w6.addHome_team(adanaspor);
		w6.addAway_team(osmanlisporfk);
		w6.addHome_team(fenerbahce);
		
		WeeklyFixture w7 = new WeeklyFixture(7);
		w7.addAway_team(kasimpasa);
		w7.addHome_team(gaziantepspor);
		w7.addAway_team(kayserispor);
		w7.addHome_team(besiktas);
		w7.addAway_team(adanaspor);
		w7.addHome_team(karabukspor);
		w7.addAway_team(genclerbirligi);
		w7.addHome_team(galatasaray);
		w7.addAway_team(medipolbasaksehir);
		w7.addHome_team(atikerkonyaspor);
		w7.addAway_team(bursaspor);
		w7.addHome_team(osmanlisporfk);
		w7.addAway_team(fenerbahce);
		w7.addHome_team(aytemizalanyaspor);
		w7.addAway_team(trabzonspor);
		w7.addHome_team(akhisarbelediyespor);
		w7.addAway_team(antalyaspor);
		w7.addHome_team(caykurrizespor);
		
		WeeklyFixture w8 = new WeeklyFixture(8);
		w8.addAway_team(caykurrizespor);
		w8.addHome_team(genclerbirligi);
		w8.addAway_team(gaziantepspor);
		w8.addHome_team(kayserispor);
		w8.addAway_team(aytemizalanyaspor);
		w8.addHome_team(bursaspor);
		w8.addAway_team(akhisarbelediyespor);
		w8.addHome_team(adanaspor);
		w8.addAway_team(galatasaray);
		w8.addHome_team(trabzonspor);
		w8.addAway_team(karabukspor);
		w8.addHome_team(medipolbasaksehir);
		w8.addAway_team(osmanlisporfk);
		w8.addHome_team(kasimpasa);
		w8.addAway_team(besiktas);
		w8.addHome_team(antalyaspor);
		w8.addAway_team(atikerkonyaspor);
		w8.addHome_team(fenerbahce);
		
		WeeklyFixture w9 = new WeeklyFixture(9);
		w9.addAway_team(genclerbirligi);
		w9.addHome_team(besiktas);
		w9.addAway_team(gaziantepspor);
		w9.addHome_team(osmanlisporfk);
		w9.addAway_team(bursaspor);
		w9.addHome_team(atikerkonyaspor);
		w9.addAway_team(adanaspor);
		w9.addHome_team(galatasaray);
		w9.addAway_team(medipolbasaksehir);
		w9.addHome_team(akhisarbelediyespor);
		w9.addAway_team(trabzonspor);
		w9.addHome_team(caykurrizespor);
		w9.addAway_team(fenerbahce);
		w9.addHome_team(karabukspor);
		w9.addAway_team(kasimpasa);
		w9.addHome_team(aytemizalanyaspor);
		w9.addAway_team(kayserispor);
		w9.addHome_team(antalyaspor);
		
		WeeklyFixture w10 = new WeeklyFixture(10);
		w10.addAway_team(galatasaray);
		w10.addHome_team(medipolbasaksehir);
		w10.addAway_team(aytemizalanyaspor);
		w10.addHome_team(gaziantepspor);
		w10.addAway_team(karabukspor);
		w10.addHome_team(bursaspor);
		w10.addAway_team(besiktas);
		w10.addHome_team(trabzonspor);
		w10.addAway_team(antalyaspor);
		w10.addHome_team(genclerbirligi);
		w10.addAway_team(caykurrizespor);
		w10.addHome_team(adanaspor);
		w10.addAway_team(osmanlisporfk);
		w10.addHome_team(kayserispor);
		w10.addAway_team(akhisarbelediyespor);
		w10.addHome_team(fenerbahce);
		w10.addAway_team(atikerkonyaspor);
		w10.addHome_team(kasimpasa);
		
		WeeklyFixture w11 = new WeeklyFixture(11);
		w11.addAway_team(gaziantepspor);
		w11.addHome_team(atikerkonyaspor);
		w11.addAway_team(bursaspor);
		w11.addHome_team(akhisarbelediyespor);
		w11.addAway_team(osmanlisporfk);
		w11.addHome_team(aytemizalanyaspor);
		w11.addAway_team(adanaspor);
		w11.addHome_team(besiktas);
		w11.addAway_team(kasimpasa);
		w11.addHome_team(karabukspor);
		w11.addAway_team(kayserispor);
		w11.addHome_team(genclerbirligi);
		w11.addAway_team(fenerbahce);
		w11.addHome_team(galatasaray);
		w11.addAway_team(medipolbasaksehir);
		w11.addHome_team(caykurrizespor);
		w11.addAway_team(trabzonspor);
		w11.addHome_team(antalyaspor);
		
		WeeklyFixture w12 = new WeeklyFixture(12);
		w12.addAway_team(aytemizalanyaspor);
		w12.addHome_team(kayserispor);
		w12.addAway_team(galatasaray);
		w12.addHome_team(bursaspor);
		w12.addAway_team(karabukspor);
		w12.addHome_team(gaziantepspor);
		w12.addAway_team(akhisarbelediyespor);
		w12.addHome_team(kasimpasa);
		w12.addAway_team(besiktas);
		w12.addHome_team(medipolbasaksehir);
		w12.addAway_team(antalyaspor);
		w12.addHome_team(adanaspor);
		w12.addAway_team(genclerbirligi);
		w12.addHome_team(trabzonspor);
		w12.addAway_team(caykurrizespor);
		w12.addHome_team(fenerbahce);
		w12.addAway_team(atikerkonyaspor);
		w12.addHome_team(osmanlisporfk);
		
		WeeklyFixture w13 = new WeeklyFixture(13);
		w13.addAway_team(medipolbasaksehir);
		w13.addHome_team(antalyaspor);
		w13.addAway_team(fenerbahce);
		w13.addHome_team(besiktas);
		w13.addAway_team(aytemizalanyaspor);
		w13.addHome_team(atikerkonyaspor);
		w13.addAway_team(gaziantepspor);
		w13.addHome_team(akhisarbelediyespor);
		w13.addAway_team(osmanlisporfk);
		w13.addHome_team(karabukspor);
		w13.addAway_team(adanaspor);
		w13.addHome_team(genclerbirligi);
		w13.addAway_team(kasimpasa);
		w13.addHome_team(galatasaray);
		w13.addAway_team(bursaspor);
		w13.addHome_team(caykurrizespor);
		w13.addAway_team(kayserispor);
		w13.addHome_team(trabzonspor);
		
		WeeklyFixture w14 = new WeeklyFixture(14);
		w14.addAway_team(trabzonspor);
		w14.addHome_team(adanaspor);
		w14.addAway_team(caykurrizespor);
		w14.addHome_team(kasimpasa);
		w14.addAway_team(besiktas);
		w14.addHome_team(bursaspor);
		w14.addAway_team(genclerbirligi);
		w14.addHome_team(medipolbasaksehir);
		w14.addAway_team(karabukspor);
		w14.addHome_team(aytemizalanyaspor);
		w14.addAway_team(akhisarbelediyespor);
		w14.addHome_team(osmanlisporfk);
		w14.addAway_team(atikerkonyaspor);
		w14.addHome_team(kayserispor);
		w14.addAway_team(galatasaray);
		w14.addHome_team(gaziantepspor);
		w14.addAway_team(antalyaspor);
		w14.addHome_team(fenerbahce);
		
		WeeklyFixture w15 = new WeeklyFixture(15);
		w15.addAway_team(medipolbasaksehir);
		w15.addHome_team(trabzonspor);
		w15.addAway_team(kasimpasa);
		w15.addHome_team(besiktas);
		w15.addAway_team(gaziantepspor);
		w15.addHome_team(caykurrizespor);
		w15.addAway_team(karabukspor);
		w15.addHome_team(atikerkonyaspor);
		w15.addAway_team(adanaspor);
		w15.addHome_team(kayserispor);
		w15.addAway_team(aytemizalanyaspor);
		w15.addHome_team(akhisarbelediyespor);
		w15.addAway_team(osmanlisporfk);
		w15.addHome_team(galatasaray);
		w15.addAway_team(bursaspor);
		w15.addHome_team(antalyaspor);
		w15.addAway_team(fenerbahce);
		w15.addHome_team(genclerbirligi);
		
		WeeklyFixture w16 = new WeeklyFixture(16);
		w16.addAway_team(adanaspor);
		w16.addHome_team(medipolbasaksehir);
		w16.addAway_team(besiktas);
		w16.addHome_team(gaziantepspor);
		w16.addAway_team(caykurrizespor);
		w16.addHome_team(osmanlisporfk);
		w16.addAway_team(akhisarbelediyespor);
		w16.addHome_team(atikerkonyaspor);
		w16.addAway_team(kayserispor);
		w16.addHome_team(karabukspor);
		w16.addAway_team(antalyaspor);
		w16.addHome_team(kasimpasa);
		w16.addAway_team(galatasaray);
		w16.addHome_team(aytemizalanyaspor);
		w16.addAway_team(genclerbirligi);
		w16.addHome_team(bursaspor);
		w16.addAway_team(trabzonspor);
		w16.addHome_team(fenerbahce);
		
		WeeklyFixture w17 = new WeeklyFixture(17);
		w17.addAway_team(atikerkonyaspor);
		w17.addHome_team(galatasaray);
		w17.addAway_team(aytemizalanyaspor);
		w17.addHome_team(caykurrizespor);
		w17.addAway_team(bursaspor);
		w17.addHome_team(trabzonspor);
		w17.addAway_team(fenerbahce);
		w17.addHome_team(adanaspor);
		w17.addAway_team(gaziantepspor);
		w17.addHome_team(antalyaspor);
		w17.addAway_team(karabukspor);
		w17.addHome_team(akhisarbelediyespor);
		w17.addAway_team(kasimpasa);
		w17.addHome_team(genclerbirligi);
		w17.addAway_team(medipolbasaksehir);
		w17.addHome_team(kayserispor);
		w17.addAway_team(osmanlisporfk);
		w17.addHome_team(besiktas);
		
		WeeklyFixture w18 = new WeeklyFixture(18);
		w18.addAway_team(akhisarbelediyespor);
		w18.addHome_team(kayserispor);
		w18.addAway_team(atikerkonyaspor);
		w18.addHome_team(caykurrizespor);
		w18.addAway_team(aytemizalanyaspor);
		w18.addHome_team(besiktas);
		w18.addAway_team(bursaspor);
		w18.addHome_team(adanaspor);
		w18.addAway_team(fenerbahce);
		w18.addHome_team(medipolbasaksehir);
		w18.addAway_team(gaziantepspor);
		w18.addHome_team(genclerbirligi);
		w18.addAway_team(karabukspor);
		w18.addHome_team(galatasaray);
		w18.addAway_team(kasimpasa);
		w18.addHome_team(trabzonspor);
		w18.addAway_team(osmanlisporfk);
		w18.addHome_team(antalyaspor);
		
		WeeklyFixture w19 = new WeeklyFixture(19);
		w19.addAway_team(adanaspor);
		w19.addHome_team(kasimpasa);
		w19.addAway_team(antalyaspor);
		w19.addHome_team(aytemizalanyaspor);
		w19.addAway_team(besiktas);
		w19.addHome_team(atikerkonyaspor);
		w19.addAway_team(caykurrizespor);
		w19.addHome_team(karabukspor);
		w19.addAway_team(galatasaray);
		w19.addHome_team(akhisarbelediyespor);
		w19.addAway_team(genclerbirligi);
		w19.addHome_team(osmanlisporfk);
		w19.addAway_team(kayserispor);
		w19.addHome_team(fenerbahce);
		w19.addAway_team(medipolbasaksehir);
		w19.addHome_team(bursaspor);
		w19.addAway_team(trabzonspor);
		w19.addHome_team(gaziantepspor);
		
		WeeklyFixture w20 = new WeeklyFixture(20);
		w20.addAway_team(akhisarbelediyespor);
		w20.addHome_team(caykurrizespor);
		w20.addAway_team(atikerkonyaspor);
		w20.addHome_team(antalyaspor);
		w20.addAway_team(aytemizalanyaspor);
		w20.addHome_team(genclerbirligi);
		w20.addAway_team(bursaspor);
		w20.addHome_team(fenerbahce);
		w20.addAway_team(galatasaray);
		w20.addHome_team(kayserispor);
		w20.addAway_team(gaziantepspor);
		w20.addHome_team(adanaspor);
		w20.addAway_team(karabukspor);
		w20.addHome_team(besiktas);
		w20.addAway_team(kasimpasa);
		w20.addHome_team(medipolbasaksehir);
		w20.addAway_team(osmanlisporfk);
		w20.addHome_team(trabzonspor);
		
		WeeklyFixture w21 = new WeeklyFixture(21);
		w21.addAway_team(adanaspor);
		w21.addHome_team(osmanlisporfk);
		w21.addAway_team(antalyaspor);
		w21.addHome_team(karabukspor);
		w21.addAway_team(besiktas);
		w21.addHome_team(akhisarbelediyespor);
		w21.addAway_team(caykurrizespor);
		w21.addHome_team(galatasaray);
		w21.addAway_team(fenerbahce);
		w21.addHome_team(kasimpasa);
		w21.addAway_team(genclerbirligi);
		w21.addHome_team(atikerkonyaspor);
		w21.addAway_team(kayserispor);
		w21.addHome_team(bursaspor);
		w21.addAway_team(medipolbasaksehir);
		w21.addHome_team(gaziantepspor);
		w21.addAway_team(trabzonspor);
		w21.addHome_team(aytemizalanyaspor);
		
		WeeklyFixture w22 = new WeeklyFixture(22);
		w22.addAway_team(akhisarbelediyespor);
		w22.addHome_team(antalyaspor);
		w22.addAway_team(atikerkonyaspor);
		w22.addHome_team(trabzonspor);
		w22.addAway_team(aytemizalanyaspor);
		w22.addHome_team(adanaspor);
		w22.addAway_team(caykurrizespor);
		w22.addHome_team(kayserispor);
		w22.addAway_team(galatasaray);
		w22.addHome_team(besiktas);
		w22.addAway_team(gaziantepspor);
		w22.addHome_team(fenerbahce);
		w22.addAway_team(karabukspor);
		w22.addHome_team(genclerbirligi);
		w22.addAway_team(kasimpasa);
		w22.addHome_team(bursaspor);
		w22.addAway_team(osmanlisporfk);
		w22.addHome_team(medipolbasaksehir);
		
		WeeklyFixture w23 = new WeeklyFixture(23);
		w23.addAway_team(adanaspor);
		w23.addHome_team(atikerkonyaspor);
		w23.addAway_team(antalyaspor);
		w23.addHome_team(galatasaray);
		w23.addAway_team(besiktas);
		w23.addHome_team(caykurrizespor);
		w23.addAway_team(bursaspor);
		w23.addHome_team(gaziantepspor);
		w23.addAway_team(fenerbahce);
		w23.addHome_team(osmanlisporfk);
		w23.addAway_team(genclerbirligi);
		w23.addHome_team(akhisarbelediyespor);
		w23.addAway_team(kayserispor);
		w23.addHome_team(kasimpasa);
		w23.addAway_team(medipolbasaksehir);
		w23.addHome_team(aytemizalanyaspor);
		w23.addAway_team(trabzonspor);
		w23.addHome_team(karabukspor);
		
		WeeklyFixture w24 = new WeeklyFixture(24);
		w24.addAway_team(akhisarbelediyespor);
		w24.addHome_team(trabzonspor);
		w24.addAway_team(atikerkonyaspor);
		w24.addHome_team(medipolbasaksehir);
		w24.addAway_team(aytemizalanyaspor);
		w24.addHome_team(fenerbahce);
		w24.addAway_team(besiktas);
		w24.addHome_team(kayserispor);
		w24.addAway_team(caykurrizespor);
		w24.addHome_team(antalyaspor);
		w24.addAway_team(galatasaray);
		w24.addHome_team(genclerbirligi);
		w24.addAway_team(gaziantepspor);
		w24.addHome_team(kasimpasa);
		w24.addAway_team(karabukspor);
		w24.addHome_team(adanaspor);
		w24.addAway_team(osmanlisporfk);
		w24.addHome_team(bursaspor);
		
		WeeklyFixture w25 = new WeeklyFixture(25);
		w25.addAway_team(adanaspor);
		w25.addHome_team(akhisarbelediyespor);
		w25.addAway_team(antalyaspor);
		w25.addHome_team(besiktas);
		w25.addAway_team(bursaspor);
		w25.addHome_team(aytemizalanyaspor);
		w25.addAway_team(fenerbahce);
		w25.addHome_team(atikerkonyaspor);
		w25.addAway_team(genclerbirligi);
		w25.addHome_team(caykurrizespor);
		w25.addAway_team(kasimpasa);
		w25.addHome_team(osmanlisporfk);
		w25.addAway_team(kayserispor);
		w25.addHome_team(gaziantepspor);
		w25.addAway_team(medipolbasaksehir);
		w25.addHome_team(karabukspor);
		w25.addAway_team(trabzonspor);
		w25.addHome_team(galatasaray);
		
		WeeklyFixture w26 = new WeeklyFixture(26);
		w26.addAway_team(akhisarbelediyespor);
		w26.addHome_team(medipolbasaksehir);
		w26.addAway_team(antalyaspor);
		w26.addHome_team(kayserispor);
		w26.addAway_team(atikerkonyaspor);
		w26.addHome_team(bursaspor);
		w26.addAway_team(aytemizalanyaspor);
		w26.addHome_team(kasimpasa);
		w26.addAway_team(besiktas);
		w26.addHome_team(genclerbirligi);
		w26.addAway_team(caykurrizespor);
		w26.addHome_team(trabzonspor);
		w26.addAway_team(galatasaray);
		w26.addHome_team(adanaspor);
		w26.addAway_team(karabukspor);
		w26.addHome_team(fenerbahce);
		w26.addAway_team(osmanlisporfk);
		w26.addHome_team(gaziantepspor);
		
		WeeklyFixture w27 = new WeeklyFixture(27);
		w27.addAway_team(adanaspor);
		w27.addHome_team(caykurrizespor);
		w27.addAway_team(bursaspor);
		w27.addHome_team(karabukspor);
		w27.addAway_team(fenerbahce);
		w27.addHome_team(akhisarbelediyespor);
		w27.addAway_team(gaziantepspor);
		w27.addHome_team(aytemizalanyaspor);
		w27.addAway_team(genclerbirligi);
		w27.addHome_team(antalyaspor);
		w27.addAway_team(kasimpasa);
		w27.addHome_team(atikerkonyaspor);
		w27.addAway_team(kayserispor);
		w27.addHome_team(osmanlisporfk);
		w27.addAway_team(medipolbasaksehir);
		w27.addHome_team(galatasaray);
		w27.addAway_team(trabzonspor);
		w27.addHome_team(besiktas);
		
		WeeklyFixture w28 = new WeeklyFixture(28);
		w28.addAway_team(akhisarbelediyespor);
		w28.addHome_team(bursaspor);
		w28.addAway_team(antalyaspor);
		w28.addHome_team(trabzonspor);
		w28.addAway_team(atikerkonyaspor);
		w28.addHome_team(gaziantepspor);
		w28.addAway_team(aytemizalanyaspor);
		w28.addHome_team(osmanlisporfk);
		w28.addAway_team(besiktas);
		w28.addHome_team(adanaspor);
		w28.addAway_team(caykurrizespor);
		w28.addHome_team(medipolbasaksehir);
		w28.addAway_team(galatasaray);
		w28.addHome_team(fenerbahce);
		w28.addAway_team(genclerbirligi);
		w28.addHome_team(kayserispor);
		w28.addAway_team(karabukspor);
		w28.addHome_team(kasimpasa);
		
		WeeklyFixture w29 = new WeeklyFixture(29);
		w29.addAway_team(adanaspor);
		w29.addHome_team(antalyaspor);
		w29.addAway_team(bursaspor);
		w29.addHome_team(galatasaray);
		w29.addAway_team(fenerbahce);
		w29.addHome_team(caykurrizespor);
		w29.addAway_team(gaziantepspor);
		w29.addHome_team(karabukspor);
		w29.addAway_team(kasimpasa);
		w29.addHome_team(akhisarbelediyespor);
		w29.addAway_team(kayserispor);
		w29.addHome_team(aytemizalanyaspor);
		w29.addAway_team(medipolbasaksehir);
		w29.addHome_team(besiktas);
		w29.addAway_team(osmanlisporfk);
		w29.addHome_team(atikerkonyaspor);
		w29.addAway_team(trabzonspor);
		w29.addHome_team(genclerbirligi);
		
		WeeklyFixture w30 = new WeeklyFixture(30);
		w30.addAway_team(akhisarbelediyespor);
		w30.addHome_team(gaziantepspor);
		w30.addAway_team(antalyaspor);
		w30.addHome_team(medipolbasaksehir);
		w30.addAway_team(atikerkonyaspor);
		w30.addHome_team(aytemizalanyaspor);
		w30.addAway_team(besiktas);
		w30.addHome_team(fenerbahce);
		w30.addAway_team(caykurrizespor);
		w30.addHome_team(bursaspor);
		w30.addAway_team(galatasaray);
		w30.addHome_team(kasimpasa);
		w30.addAway_team(genclerbirligi);
		w30.addHome_team(adanaspor);
		w30.addAway_team(karabukspor);
		w30.addHome_team(osmanlisporfk);
		w30.addAway_team(trabzonspor);
		w30.addHome_team(kayserispor);
		
		WeeklyFixture w31 = new WeeklyFixture(31);
		w31.addAway_team(adanaspor);
		w31.addHome_team(trabzonspor);
		w31.addAway_team(aytemizalanyaspor);
		w31.addHome_team(karabukspor);
		w31.addAway_team(bursaspor);
		w31.addHome_team(besiktas);
		w31.addAway_team(fenerbahce);
		w31.addHome_team(antalyaspor);
		w31.addAway_team(gaziantepspor);
		w31.addHome_team(galatasaray);
		w31.addAway_team(kasimpasa);
		w31.addHome_team(caykurrizespor);
		w31.addAway_team(kayserispor);
		w31.addHome_team(atikerkonyaspor);
		w31.addAway_team(medipolbasaksehir);
		w31.addHome_team(genclerbirligi);
		w31.addAway_team(osmanlisporfk);
		w31.addHome_team(akhisarbelediyespor);
		
		WeeklyFixture w32 = new WeeklyFixture(32);
		w32.addAway_team(akhisarbelediyespor);
		w32.addHome_team(aytemizalanyaspor);
		w32.addAway_team(antalyaspor);
		w32.addHome_team(bursaspor);
		w32.addAway_team(atikerkonyaspor);
		w32.addHome_team(karabukspor);
		w32.addAway_team(besiktas);
		w32.addHome_team(kasimpasa);
		w32.addAway_team(caykurrizespor);
		w32.addHome_team(gaziantepspor);
		w32.addAway_team(galatasaray);
		w32.addHome_team(osmanlisporfk);
		w32.addAway_team(genclerbirligi);
		w32.addHome_team(fenerbahce);
		w32.addAway_team(kayserispor);
		w32.addHome_team(adanaspor);
		w32.addAway_team(trabzonspor);
		w32.addHome_team(medipolbasaksehir);
		
		WeeklyFixture w33 = new WeeklyFixture(33);
		w33.addAway_team(atikerkonyaspor);
		w33.addHome_team(akhisarbelediyespor);
		w33.addAway_team(aytemizalanyaspor);
		w33.addHome_team(galatasaray);
		w33.addAway_team(bursaspor);
		w33.addHome_team(genclerbirligi);
		w33.addAway_team(fenerbahce);
		w33.addHome_team(trabzonspor);
		w33.addAway_team(gaziantepspor);
		w33.addHome_team(besiktas);
		w33.addAway_team(karabukspor);
		w33.addHome_team(kayserispor);
		w33.addAway_team(kasimpasa);
		w33.addHome_team(antalyaspor);
		w33.addAway_team(medipolbasaksehir);
		w33.addHome_team(adanaspor);
		w33.addAway_team(osmanlisporfk);
		w33.addHome_team(caykurrizespor);
		
		WeeklyFixture w34 = new WeeklyFixture(34);
		w34.addAway_team(adanaspor);
		w34.addHome_team(fenerbahce);
		w34.addAway_team(akhisarbelediyespor);
		w34.addHome_team(karabukspor);
		w34.addAway_team(antalyaspor);
		w34.addHome_team(gaziantepspor);
		w34.addAway_team(besiktas);
		w34.addHome_team(osmanlisporfk);
		w34.addAway_team(caykurrizespor);
		w34.addHome_team(aytemizalanyaspor);
		w34.addAway_team(galatasaray);
		w34.addHome_team(atikerkonyaspor);
		w34.addAway_team(genclerbirligi);
		w34.addHome_team(kasimpasa);
		w34.addAway_team(kayserispor);
		w34.addHome_team(medipolbasaksehir);
		w34.addAway_team(trabzonspor);
		w34.addHome_team(bursaspor);
		
		turkishLeague.addFixture(w1);
		turkishLeague.addFixture(w2);
		turkishLeague.addFixture(w3);
		turkishLeague.addFixture(w4);
		turkishLeague.addFixture(w5);
		turkishLeague.addFixture(w6);
		turkishLeague.addFixture(w7);
		turkishLeague.addFixture(w8);
		turkishLeague.addFixture(w9);
		turkishLeague.addFixture(w10);
		turkishLeague.addFixture(w11);
		turkishLeague.addFixture(w12);
		turkishLeague.addFixture(w13);
		turkishLeague.addFixture(w14);
		turkishLeague.addFixture(w15);
		turkishLeague.addFixture(w16);
		turkishLeague.addFixture(w17);
		turkishLeague.addFixture(w18);
		turkishLeague.addFixture(w19);
		turkishLeague.addFixture(w20);
		turkishLeague.addFixture(w21);
		turkishLeague.addFixture(w22);
		turkishLeague.addFixture(w23);
		turkishLeague.addFixture(w24);
		turkishLeague.addFixture(w25);
		turkishLeague.addFixture(w26);
		turkishLeague.addFixture(w27);
		turkishLeague.addFixture(w28);
		turkishLeague.addFixture(w29);
		turkishLeague.addFixture(w30);
		turkishLeague.addFixture(w31);
		turkishLeague.addFixture(w32);
		turkishLeague.addFixture(w33);
		turkishLeague.addFixture(w34);
		
		setUserLeague(turkishLeague);
		Scanner sc = new Scanner(System.in);
		int choice;
		
		System.out.print("1-medipolbasaksehir\n2-besiktas\n3-fenerbahce\n4-galatasaray\n5-bursaspor\n6-atikerkonyaspor\n7-karabukspor\n8-osmanlisporfk\n9-antalyaspor\n10-genclerbirligi\n11-aytemizalanyaspor\n12-akhisarbelediyespor\n13-kasimpasa\n14-trabzonspor\n15-gaziantepspor\n16-caykurrizespor\n17-kayserispor\n18-adanaspor\nSelect a team : ");
		choice=sc.nextInt();
		
		setUserTeam(turkishLeagueTeams.get(choice-1));
		
	}
	
	
}
