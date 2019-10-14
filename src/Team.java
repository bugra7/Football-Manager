import java.util.ArrayList;
import java.util.List;

public class Team {

	private String name;
	private int attack_power;
	private int defense_power;
	private int money;
	private List<Integer> tactic;
	private List<Player> squad;
	private int numbers_of_games_played;
	private int number_of_games_won;
	private int number_of_games_lost;
	private int number_of_games_drawn;
	private int points;

	public Team(String name, int money) {

		this.name = name;
		this.attack_power = 0;
		this.defense_power = 0;
		this.money = money;
		this.tactic = new ArrayList<Integer>();
		tactic.add(0);
		tactic.add(0);
		this.numbers_of_games_played = 0;
		this.number_of_games_won = 0;
		this.number_of_games_lost = 0;
		this.number_of_games_drawn = 0;
		this.points = 0;
		this.squad = new ArrayList<Player>();
		
	}
	

	public void winMatch() {
		
		this.numbers_of_games_played++;
		this.points += 3;
		this.number_of_games_won++;
		this.money+=1000000;
		
	}
	
	public void drawMatch() {
		
		this.numbers_of_games_played++;
		this.points += 1;
		this.number_of_games_drawn++;
		this.money+=500000;
	}
	
	public void loseMatch() {
		
		this.numbers_of_games_played++;
		this.number_of_games_lost++;
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void calculatePower() {
		int def_power = 0, att_power = 0, att_counter = 0, def_counter = 0;
		
		for(int i = 0; i < this.squad.size(); i++) {
			
			if(this.squad.get(i).getType().compareTo("Forward") == 0 || this.squad.get(i).getType().compareTo("Midfielder") == 0){

				att_counter++;
				att_power += this.squad.get(i).getPower();
				
			}
				
			else if(squad.get(i).getType().compareTo("Defender") == 0 || squad.get(i).getType().compareTo("Goalkeeper") == 0){

				def_counter++;
				def_power += this.squad.get(i).getPower();
				
			}
			
		}
		//System.out.println(att_power +"	"+ att_counter);
		this.attack_power = att_power / att_counter;
		this.defense_power = def_power / def_counter;
		this.tactic.set(0, defense_power);
		this.tactic.set(1, attack_power);
	}

	public List<Integer> getTactic() {
		return tactic;
	}


	public void setTactic(List<Integer> tactic) {
		this.tactic = tactic;
	}


	public int getAttack_power() {
		return attack_power;
	}

	public void setAttack_power(int attack_power) {
		this.attack_power = attack_power;
	}

	public int getDefense_power() {
		return defense_power;
	}

	public void setDefense_power(int defense_power) {
		this.defense_power = defense_power;
	}

	public int getMoney() {
		return money;
	}


	public void setMoney(int money) {
		this.money = money;
	}

	public List<Player> getSquad() {
		return squad;
	}


	public void setSquad(List<Player> squad) {
		this.squad = squad;
	}

	public void addSquad(Player player) {
		this.squad.add(player);		
	}

	public int getNumbers_of_games_played() {
		return numbers_of_games_played;
	}


	public void setNumbers_of_games_played(int numbers_of_games_played) {
		this.numbers_of_games_played = numbers_of_games_played;
	}


	public int getNumber_of_games_won() {
		return number_of_games_won;
	}


	public void setNumber_of_games_won(int number_of_games_won) {
		this.number_of_games_won = number_of_games_won;
	}


	public int getNumber_of_games_lost() {
		return number_of_games_lost;
	}


	public void setNumber_of_games_lost(int number_of_games_lost) {
		this.number_of_games_lost = number_of_games_lost;
	}


	public int getNumber_of_games_drawn() {
		return number_of_games_drawn;
	}


	public void setNumber_of_games_drawn(int number_of_games_drawn) {
		this.number_of_games_drawn = number_of_games_drawn;
	}


	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}
	
	
	
}
