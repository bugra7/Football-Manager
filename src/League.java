import java.util.ArrayList;
import java.util.List;

public class League {

	private String name;
	private int current_week;
	private List<WeeklyFixture> fixture;
	private List<Team> teams;
	
	public League(String name) {
		
		this.name = name;
		this.current_week = 1;
		this.fixture = new ArrayList<WeeklyFixture>();
		this.teams = new ArrayList<Team>();
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCurrent_week() {
		return current_week;
	}

	public void setCurrent_week(int current_week) {
		this.current_week = current_week;
	}

	public List<WeeklyFixture> getFixture() {
		return fixture;
	}

	public void setFixture(List<WeeklyFixture> fixture) {
		this.fixture = fixture;
	}
	
	public void addFixture(WeeklyFixture weekly_fixture) {
		this.fixture.add(weekly_fixture);
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
	public void addTeam(Team team) {
		this.teams.add(team);
	}
	
	public void CalculatePowerofTeams(){
		
		for(int i=0; i<teams.size(); i++){
			
			teams.get(i).calculatePower();
		}
	}
}
