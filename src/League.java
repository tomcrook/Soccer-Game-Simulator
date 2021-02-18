import java.util.ArrayList;
import java.util.Scanner;

public class League {

    ArrayList<Team> teams = new ArrayList<Team>();
    ArrayList<Player> players = new ArrayList<Player>();

    String title;
    Schedule schedule;
    int week;
    int season;
    Team[] relegated;
    Team[] promoted;
    int leagueRank;
    int numTeams = 20;


    League() {
        this.title = "Premier Division";
        this.teams = this.populateLeague(numTeams, -1);
        this.populatePlayers();
        this.week = 0;
        this.season = 1;
        this.leagueRank = 0;
        this.schedule = new Schedule(this.teams);
    }

    League(int leagueRank) {
        this.title = "Division " + (1 + leagueRank);
        this.teams = this.populateLeague(numTeams, leagueRank);
        this.populatePlayers();
        this.week = 0;
        this.season = 1;
        this.leagueRank = leagueRank;
        this.schedule = new Schedule(this.teams);
    }


    Team[] getTop3() {
        Team[] top3 = new Team[3];
        for (int i = 0; i < 3; i++) {
            top3[i] = this.teams.get(i);
        }
        return top3;
    }

    Team[] getBottom3() {
        Team[] bot3 = new Team[3];
        int count = 0;
        for (int i = teams.size() - 1; i > teams.size() - 4; i--) {
            bot3[count] = this.teams.get(i);
            count++;
        }
        return bot3;
    }

    // add 'num' teams to the league
    ArrayList<Team> populateLeague(int num, int averageModifier) {
        ArrayList<Team> t = new ArrayList<Team>();
        for (int i = 0; i < num; i++)
            t.add(new Team(80 - 10 * averageModifier));
        return t;
    }

    void populatePlayers() {
        this.players = new ArrayList<Player>();
        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.get(i).squad.size(); j++) {
                players.add(teams.get(i).squad.get(j));
            }
        }
    }

    void presentLeague() {
        this.sortLeague();
        System.out.println(this.title + ":");
        for (int i = 0; i < teams.size(); i++) {
            teams.get(i).present(i + 1);
        }
    }

    void sortLeague() {
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                if (teams.get(i).points < teams.get(j).points) {
                    this.swap(i, j);
                }
            }
        }
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                if (teams.get(i).points == teams.get(j).points
                        && teams.get(i).goalDifference < teams.get(j).goalDifference) {
                    this.swap(i, j);
                }
            }
        }

    }

    void swap(int first, int second) {
        Team team = teams.get(first);
        teams.set(first, teams.get(second));
        teams.set(second, team);
    }

    void swapPlayer(int first, int second) {
        Player team = players.get(first);
        players.set(first, players.get(second));
        players.set(second, team);
    }

    void presentAllPlayers() {
        for (int i = 0; i < teams.size(); i++) {
            System.out.println(teams.get(i).toString());
            System.out.println("_______________________________________");
        }
    }

    void presentAllGoalscorers() {
        for (int i = 0; i < players.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                if (players.get(i).ssnGoals < players.get(j).ssnGoals) {
                    this.swapPlayer(i, j);
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            Player p = players.get(i);
            System.out.println(p.position + " " + p.name + ": " + p.ssnGoals + " g | " + p.ssnApp + "apps ("
                    + p.teamName + ")");
        }
    }


    void simulateSeason() {
        for (int i = week; i < (teams.size() - 1) * 2; i++) {
            this.simulateGameWeek();
        }
    }

    void simulateGameWeek() {
        week++;
        for (int i = 0; i < teams.size(); i++) {
            if (schedule.contains(i, week)) {
                Match game = this.schedule.getMatch(i, week);
                game.kickOff();
            }
        }
    }




}