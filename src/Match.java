import java.util.ArrayList;

public class Match {

    Team home, away;
    Team hasBall;
    Team def;
    Player withBall;
    Player toReceive;
    Player defending;
    Player lastPass;
    int pitchLocation = 4;
    int minute = 0;
    int count = 0;

    int homeGoals = 0, awayGoals = 0;
    int homePass = 0, awayPass = 0;

    String finalAnnouncement = "";



    Match() {
        this.home = new Team(80);
        this.away = new Team(80);
        this.hasBall = this.home;
        this.def = this.away;
        this.home.startingXI = this.home.getStartingXI();
        this.away.startingXI = this.away.getStartingXI();
    }

    Match(Team home, Team away) {
        this.home = home;
        this.away = away;
        this.hasBall = this.home;
        this.def = this.away;
        this.home.startingXI = this.home.getStartingXI();
        this.away.startingXI = this.away.getStartingXI();
    }

    void kickOff() {
        pitchLocation = 4;
        withBall = hasBall.startingXI.get(10);
        toReceive = this.setReceiver(0.25, 0.5, 0.25);
        pitchLocation-=2;
        withBall = toReceive;
        this.hasPossession();
    }

    void endGame() {
        home.gFor += homeGoals;
        away.gFor += awayGoals;
        home.gAgainst += awayGoals;
        away.gAgainst += homeGoals;
        this.home.changeGoalDifference(homeGoals - awayGoals);
        this.away.changeGoalDifference(awayGoals - homeGoals);
        home.endGame();
        away.endGame();
    }

    void hasPossession() {
        this.count += (int)(Math.random() * 2) + 1;
        if (this.minute >= 90) {
            this.endGame();
        } else {
            if (this.count > 18) {
                this.minute++;
                this.count = 0;
            }

            switch (withBall.makeDecision(pitchLocation)) {
                case 1: {
                    this.pass();
                    break;
                }
                case 2: {
                    this.dribble();
                    break;
                }
                case 3: {
                    this.shoot();
                    break;
                }
            }
        }
    }

    void pass() {
        double receivingPos = Math.random();
        int potentialLoc = pitchLocation;
        if (pitchLocation <= 1) { // def has ball
            // pass +2
            // defending = att
            toReceive = setReceiver(0.5, 0.5, 0);
            defending = setDefender(0, 0, 1);
            potentialLoc += 2;

        } else if (pitchLocation <= 3) { // def or mid has ball
            // pitchLocation = 4
            // defending = mid || att
            defending = setDefender(0, 0.5, 0.5);
            if (receivingPos < 0.9) {
                toReceive = setReceiver(0, 1, 0);
                potentialLoc = 4;
            } else {
                toReceive = setReceiver(1, 0, 0);
                potentialLoc -= 2;
            }

            // pass -2;
        } else if (pitchLocation < 5) { // mid has ball
            // pass +2 || pass +1
            // defending = mid
            defending = setDefender(0, 1, 0);
            if (receivingPos < 0.4) {
                toReceive = setReceiver(0, 0.5, 0.5);
                potentialLoc += (int) (Math.random() * 2) + 1;
            } else if (receivingPos < 0.6) {
                toReceive = setReceiver(0.5, 0.5, 0);
                potentialLoc--;
            } else {
                toReceive = setReceiver(0, 1, 0);
            }

            // pass -1
        } else if (pitchLocation <= 6) { // mid or att has ball
            // pass +1 || pass +2
            // defending = mid || def
            defending = setDefender(0, 0.5, 0.5);
            if (receivingPos < 0.7) {
                toReceive = setReceiver(0, 0.25, 0.75);
                potentialLoc += (int) (Math.random() * 2) + 1;
            } else {
                toReceive = setReceiver(0, 1, 0);
                potentialLoc = 4;
            }

            // pitchLocation == 4
        } else { // att has ball
            // pass = 7/8
            // defending = def
            defending = setDefender(1, 0, 0);
            if (receivingPos < 0.25) {
                toReceive = setReceiver(0, 0.25, 0.75);
                potentialLoc += (int) (Math.random() * 2) + 1;
            } else {
                toReceive = setReceiver(0, 1, 0);
                potentialLoc -= (int) (Math.random() * 2) + 1;
            }

            // pass = 5/6

        }
        this.attemptPass(potentialLoc);
    }

    void dribble() {
        int potLocation = pitchLocation + (int) (Math.random() * 3) - 1;
        if (potLocation > 8) {
            potLocation = 8;
        } else if (potLocation < 0) {
            potLocation = 0;
        }
        if (pitchLocation < 3) {
            this.defending = setDefender(0, 0.5, 0.5);
        } else if (pitchLocation < 6) {
            this.defending = setDefender(0.1, 0.8,0.1);
        } else {
            this.defending = setDefender(0.8, 0.2, 0);
        }
        this.attemptDri(potLocation);
    }

    void shoot() {
        int shot = this.withBall.shoot(def.teamAvg);
        defending = def.startingXI.get(0);
        if (shot == 0) {
            this.changePossession();
            this.withBall = defending;
            pitchLocation = 0;
            this.hasPossession();
        } else if (!this.defending.saveShot(shot)) {
            this.goalScored();
        } else {
            if (Math.random() > 0.6) {
                this.cornerKick();
            } else {
                this.changePossession();
                this.withBall = defending;
                pitchLocation = 0;
                this.hasPossession();
            }
        }
    }

    Player setReceiver(double def, double mid, double att) {
        Player returner = withBall;
        while (returner.equals(withBall)) {
            double decider = Math.random();
            if (decider < def) {
                returner = hasBall.defs.get((int) (Math.random() * hasBall.defs.size()));
            } else if (decider < def + mid) {
                returner = hasBall.mids.get((int) (Math.random() * hasBall.mids.size()));
            } else {
                if (hasBall.atts.size() == 1) {
                    returner = hasBall.mids.get((int) (Math.random() * hasBall.mids.size()));
                } else {
                    returner = hasBall.atts.get((int) (Math.random() * hasBall.atts.size()));
                }
            }
        }
        return returner;
    }

    Player setDefender(double def, double mid, double att) {
        double decider = Math.random();
        if (decider < def) {
            return this.def.defs.get((int) (Math.random() * this.def.defs.size()));
        } else if (decider < def + mid) {
            return this.def.mids.get((int) (Math.random() * this.def.mids.size()));
        } else {
            return this.def.atts.get((int) (Math.random() * this.def.atts.size()));
        }
    }

    void attemptPass(int potLocation) {
        if (!this.defending.defend(this.withBall.pass(), hasBall.teamAvg)) {
            this.passWorked();
            this.lastPass = this.withBall;
            this.withBall = this.toReceive;
            this.pitchLocation = potLocation;
        } else {
            this.withBall = this.defending;
            this.changePossession();
        }
        this.hasPossession();
    }

    void attemptDri(int potLocation) {
        if (!this.defending.defend(this.withBall.dribble(), hasBall.teamAvg)) {
            this.pitchLocation = potLocation;
        } else {
            this.withBall = this.defending;
            this.changePossession();
        }
        this.hasPossession();
    }

    void changePossession() {
        Team store = this.hasBall;
        this.hasBall = this.def;
        this.def = store;
        this.pitchLocation = 8 - this.pitchLocation;
    }

    void goalScored() {
        if (hasBall.equals(home)) {
            homeGoals++;
        } else {
            awayGoals++;
        }
        if (hasBall.startingXI.contains(lastPass)) {
            lastPass.gameAssists++;
        }
        withBall.gameGoals++;
        finalAnnouncement += this.minute + "': " + withBall.name + "\n";
        this.changePossession();
        this.kickOff();
    }

    void passWorked() {
        if (hasBall.equals(home)) {
            homePass++;
        } else {
            awayPass++;
        }
    }

    void cornerKick() {
        this.withBall = setReceiver(0,1,0);
        this.toReceive = setReceiver(0.6, 0.2, 0.2);
        this.defending = setDefender(0.6, 0.35, 0.05);
        if (Math.random() > 0.5) {
            defending = def.startingXI.get(0);
        }
        if (!this.defending.defend(this.withBall.pass(), hasBall.teamAvg)) {
            this.lastPass = this.withBall;
            this.withBall = this.toReceive;
            this.shoot();
        } else {
            this.withBall = this.defending;
            this.changePossession();
            this.hasPossession();
        }
    }

    void printStats() {
        printPlayers(home);
        printPlayers(away);
    }

    private void printPlayers(Team team) {
        System.out.println("\n" + team.teamName + ":\n");
        for (int i = 0; i < 11; i++) {
            Player toPrint = team.startingXI.get(i);
            System.out.println(toPrint.name + ": \n" + toPrint.gameGoals + " goals || "
                    + toPrint.gameAssists + " assists\n");
        }
    }


    ///// BUGS / FAULTS :


    ///// TO DO:
    // B :
    // goal kicks

    // C :
    // fouls
    // injury
    // substitutions
    // yellow/red cards
    // better commentary
    // team mentality / attacking - defending
    // player play-styles
    // team play-styles







}
