import java.util.ArrayList;

public class Team {

    // Utils
    NameGenerator nameGenerator = new NameGenerator();

    // Team Info
    String teamName;
    int teamAvg = 0;

    // Squad
    ArrayList<Player> squad = new ArrayList<Player>(25);
    ArrayList<Player> startingXI = new ArrayList<Player>(11);


    // Formation
    String[] formation  = {
            "GK", "LB", "CB", "CB", "RB", "LM", "CM", "CM", "RM", "ST", "ST"
    };
    int def = 4, mid = 4, att = 2;
    ArrayList<Player> defs = new ArrayList<Player>(def),
            mids = new ArrayList<Player>(mid),
            atts = new ArrayList<Player>(att);

    // Season Stats
    int points = 0;
    int goalDifference = 0;
    int gFor = 0, gAgainst = 0;
    int gamesPlayed = 0;
    int wins = 0, draws = 0, losses = 0;


    Team(int teamAvg) {
        this.teamName = nameGenerator.generateTeamName();
        this.squad = this.makeTeam(teamAvg);
        this.startingXI = this.getStartingXI();
        this.teamAvg = teamAvg;
        this.updateTeamAverage();
    }

    ArrayList<Player> makeTeam(int teamAvg) {
        ArrayList<Player> newTeam = new ArrayList<Player>(25);
        int count = 0;
        while (count < 25) {
            if (count == 0) {
                newTeam.add(new Player(teamAvg, "GK", this.teamName));
            } else if (count >= 1 && count <= 4) {
                newTeam.add(new Player(teamAvg, "CB", this.teamName));
            }
            else if (count > 4 && count <= 6) {
                newTeam.add(new Player(teamAvg, "RB", this.teamName));
            }
            else if (count > 6 && count <= 8) {
                newTeam.add(new Player(teamAvg, "LB", this.teamName));
            }
            else if (count > 8 && count <= 12) {
                newTeam.add(new Player(teamAvg, "CM", this.teamName));
            }
            else if (count > 12 && count <= 14) {
                newTeam.add(new Player(teamAvg, "RM", this.teamName));
            }
            else if (count > 14 && count <= 16) {
                newTeam.add(new Player(teamAvg, "LM", this.teamName));
            }
            else if (count > 16 && count <= 20) {
                newTeam.add(new Player(teamAvg, "ST", this.teamName));
            } else {
                newTeam.add(new Player(teamAvg, this.teamName));
            }
            count++;
        }
        return newTeam;
    }

    ArrayList<Player> getStartingXI() {
        defs = new ArrayList<Player>(def);
        mids = new ArrayList<Player>(mid);
        atts = new ArrayList<Player>(att);
        ArrayList<Player> team = new ArrayList<Player>(11);
        for (int i = 0; i < formation.length; i++) {
            ArrayList<Player> shortlist = new ArrayList<Player>();
            for (int j = 0; j < squad.size(); j++) {
                if (squad.get(j).position.equals(formation[i])) {
                    shortlist.add(squad.get(j));
                }
            }
            Player add = new Player();
            for (int j = 0; j < shortlist.size(); j++) {
                if (shortlist.get(j).overall > add.overall && !team.contains(shortlist.get(j))) {
                    add = shortlist.get(j);
                }
            }
            team.add(add);
            if (i <= def && i != 0) {
                defs.add(add);
            } else if (i <= def + mid && i != 0) {
                mids.add(add);
            } else if (i != 0) {
                atts.add(add);
            }
        }
        return team;
    }

    void changeFormation(int def, int mid, int att) {
        this.def = def;
        this.mid = mid;
        this.att = att;
        for (int i = 2; i < def; i++) {
            formation[i] = "CB";
            formation[i+1] = "RB";
        }
        formation[def + 1] = "LM";
        for (int i = def + 2; i < def + mid; i++) {
            formation[i] = "CM";
            formation[i+1] = "RM";
        }
        for (int i = def + mid + 1; i < 11; i++) {
            formation[i] = "ST";
        }
        this.startingXI = this.getStartingXI();
    }

    void updateTeamAverage() {
        int sum = 0;
        for (int i = 0; i < squad.size(); i++) {
            sum+= squad.get(i).overall;
        }
        this.teamAvg = (int) (sum / squad.size());
    }

    public String toString() {
        String toReturn = this.teamName + "\n";
        int count = 0;
        while (count < squad.size()) {
            toReturn += squad.get(count).present();
            count++;
        }
        return toReturn;
    }

    public String toStringStartingXI() {
        String toReturn = this.teamName + "\n";
        int count = 0;
        while (count < startingXI.size()) {
            toReturn += startingXI.get(count).toString();
            count++;
        }
        return toReturn;
    }

    // Presents the team nicely for a league view
    public void present(int position) {
        String toPresent = position + ". " + this.teamName + " (" + this.teamAvg + ")";
        int num = 50 - toPresent.length();
        for (int i = 0; i < num; i++) {
            toPresent = toPresent + " ";
        }

        String stats = "|  " + this.points + "pts";
        num = 9 - stats.length();
        for (int i = 0; i < num; i++) {
            stats =  stats + " ";
        }
        String goalD = " (" + this.goalDifference + "/" + gFor + "/" + gAgainst + ")";
        num = 14-goalD.length();
        for (int i = 0; i < num; i ++) {
            goalD = goalD + " ";
        }
        System.out.print(toPresent + stats + goalD
                + "|   " + this.gamesPlayed + "GP" +
                "   |   " + wins + " - " + draws + " - " + losses);
        System.out.println();
    }


    ////////// Post Game Functions

    void addWin() {
        this.points+=3;
        this.gamesPlayed++;
        this.wins++;
    }
    void addDraw(){
        this.points+=1;
        this.gamesPlayed++;
        this.draws++;
    }
    void addLoss() {
        this.gamesPlayed++;
        this.losses++;
    }

    void changeGoalDifference(int value) {
        this.goalDifference+=value;
        if (value > 0) {
            this.addWin();
        } else if (value == 0) {
            this.addDraw();
        } else {
            this.addLoss();
        }
    }

    void endGame() {
        for (int i = 0; i < startingXI.size(); i++) {
            startingXI.get(i).endGame();
        }
    }

}
