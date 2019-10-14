import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Game g1= new Game();
		
	    
		Scanner sc = new Scanner(System.in);
		int choice;
		
	    
		System.out.print("1-German League\n2-Turkish League\nSelect a league: \n");
		choice=sc.nextInt();
		
		if (choice==1)	g1.createGermanLeague();
		else if(choice==2)	g1.createTurkishLeague();
		
		g1.playAllMatches();

		
	}

}
