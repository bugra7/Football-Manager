import java.util.ArrayList;
import java.util.List;

public class WeeklyFixture {

	private int week_number;
	private List<Team> home_teams;
	private List<Team> away_teams;
	private List<String> results;
	
	public List<String> getResults() {
		return results;
	}

	public void setResults(List<String> results) {
		this.results = results;
	}

	public WeeklyFixture(int week_number) {
		
		this.week_number = week_number;
		this.home_teams = new ArrayList<Team>();
		this.away_teams = new ArrayList<Team>();
		this.results = new ArrayList<String>();
		
	}

	public int getWeek_number() {
		return week_number;
	}

	public void setWeek_number(int week_number) {
		this.week_number = week_number;
	}

	public List<Team> getHome_teams() {
		return home_teams;
	}

	public void setHome_teams(List<Team> home_teams) {
		this.home_teams = home_teams;
	}
	
	public void addHome_team(Team home_team) {
		this.home_teams.add(home_team);
	}

	public List<Team> getAway_teams() {
		return away_teams;
	}

	public void setAway_teams(List<Team> away_teams) {
		this.away_teams = away_teams;
	}
	
	public void addAway_team(Team away_team) {
		this.away_teams.add(away_team);
	}
	
}
