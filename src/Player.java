public class Player {

    PlayerGenerator playGen;

    String name;
    String teamName = "";

    int overall, potential;
    int age;
    String position;

    // Player Stats
    int PAC, pPAC;
    int SHO, pSHO;
    int PAS, pPAS;
    int DRI, pDRI;
    int DEF, pDEF;
    int PHY, pPHY;

    // Game Stats
    int gameGoals = 0;
    int gameAssists = 0;

    // Season Stats
    int ssnGoals = 0;
    int ssnAssists = 0;
    int ssnApp = 0;

    // LifeTime Stats
    int lftGoals = 0;
    int lftAssists = 0;
    int lftApp = 0;


                              // Pass, Dribble, Shoot
    double[] attDecModifiers = { 0.6,   0.2,  0.2};
    double[] STDecModifiers  = { 0.5,   0.4,  0.1};

    // WEIGHTS :
    // ST : SHO : 0.5, PAS : 0.25, DRI : 0.25, DEF : 0
    // WI : SHO : 0.3, PAS : 0.35, DRI : 0.35, DEF : 0
    // CM : SHO : 0.1, PAS : 0.4,  DRI : 0.35, DEF : 0.15
    // CB : SHO : 0,   PAS : 0.15, DRI : 0.15, DEF : 0.7
    // WB : SHO : 0,   PAS : 0.2,  DRI : 0.2,  DEF : 0.6
    // GK : SHO : 0,   PAS : 0.15, DRI : 0.05, DEF : 0.8

    Player(int playerAvg, String teamName) {
        playGen = new PlayerGenerator(playerAvg);
        this.age = playGen.age;
        this.name = playGen.name;
        this.potential = playGen.potential;
        this.overall = playGen.overall;
        this.position = playGen.position;
        this.PAC = playGen.PAC; this.pPAC = playGen.PAC;
        this.SHO = playGen.SHO; this.pSHO = playGen.SHO;
        this.PAS = playGen.PAS; this.pPAS = playGen.PAS;
        this.DRI = playGen.DRI; this.pDRI = playGen.DRI;
        this.DEF = playGen.DEF; this.pDEF = playGen.DEF;
        this.PHY = playGen.PHY; this.pPHY = playGen.PHY;
        if (this.position.equals("ST")) {
            this.attDecModifiers = STDecModifiers;
        }
        this.teamName = teamName;
    }

    Player(int playerAvg, int age, String teamName) {
        playGen = new PlayerGenerator(playerAvg, age);
        this.age = playGen.age;
        this.name = playGen.name;
        this.potential = playGen.potential;
        this.overall = playGen.overall;
        this.position = playGen.position;
        this.PAC = playGen.PAC; this.pPAC = playGen.PAC;
        this.SHO = playGen.SHO; this.pSHO = playGen.SHO;
        this.PAS = playGen.PAS; this.pPAS = playGen.PAS;
        this.DRI = playGen.DRI; this.pDRI = playGen.DRI;
        this.DEF = playGen.DEF; this.pDEF = playGen.DEF;
        this.PHY = playGen.PHY; this.pPHY = playGen.PHY;
        if (this.position.equals("ST")) {
            this.attDecModifiers = STDecModifiers;
        }
        this.teamName = teamName;
    }

    Player() {
        playGen = new PlayerGenerator();
        this.age = playGen.age;
        this.name = playGen.name;
        this.potential = playGen.potential;
        this.overall = playGen.overall;
        this.position = playGen.position;
        this.PAC = playGen.PAC; this.pPAC = playGen.PAC;
        this.SHO = playGen.SHO; this.pSHO = playGen.SHO;
        this.PAS = playGen.PAS; this.pPAS = playGen.PAS;
        this.DRI = playGen.DRI; this.pDRI = playGen.DRI;
        this.DEF = playGen.DEF; this.pDEF = playGen.DEF;
        this.PHY = playGen.PHY; this.pPHY = playGen.PHY;
    }

    Player(int playerAvg, String pos, String teamName) {
        playGen = new PlayerGenerator(playerAvg, pos);
        this.age = playGen.age;
        this.name = playGen.name;
        this.potential = playGen.potential;
        this.overall = playGen.overall;
        this.position = playGen.position;
        this.PAC = playGen.PAC; this.pPAC = playGen.PAC;
        this.SHO = playGen.SHO; this.pSHO = playGen.SHO;
        this.PAS = playGen.PAS; this.pPAS = playGen.PAS;
        this.DRI = playGen.DRI; this.pDRI = playGen.DRI;
        this.DEF = playGen.DEF; this.pDEF = playGen.DEF;
        this.PHY = playGen.PHY; this.pPHY = playGen.PHY;
        if (this.position.equals("ST")) {
            this.attDecModifiers = STDecModifiers;
        }
        this.teamName = teamName;
    }

    int makeDecision(int distanceFromGoal) {
        double decision = Math.random();
        if (distanceFromGoal >= 6) {
            if (decision < attDecModifiers[0]) { // Pass
                return 1;
            } else if (decision < attDecModifiers[1] + attDecModifiers[0]) { // Dribble
                return 2;
            } else {
                return 3;
            }
        } else {
            if (decision < attDecModifiers[0] / attDecModifiers[0] + attDecModifiers[1]) { // Pass
                return 1;
            } else { // Dribble
                return 2;
            }
        }

    }

    int pass() {
        double passType = Math.random();
        double passQuality = 0.1 * ((int)(Math.random() * 8) + 3);
        if (passType < 0.33) { // Hard Pass
            return (int) (passQuality * (this.PAS));
        } else if (passType < 0.66) { // Medium Pass
            return (int) (passQuality * 1.2 * (this.PAS));
        } else { // Easy Pass
            return (int) (passQuality * 1.4 * (this.PAS));
        }
    }

    int dribble() {
        double driType = Math.random();
        if (driType < 0.33) { // Hard Pass
            return (int) (Math.random() * this.DRI);
        } else if (driType < 0.66) { // Medium Pass
            return (int) (Math.random() * this.DRI + (1.2 * this.DRI));
        } else { // Easy Pass
            return (int) (Math.random() * this.DRI + (1.4 * this.DRI));
        }
    }

    int shoot(int oTA) {
        int diff = this.SHO - oTA;
        if (Math.random() > .475 + (diff * 0.005)) {
            return 0;
        } else {
            return (int) (Math.random() * this.SHO * 0.8);
        }
    }

    boolean defend(int attScore, int oTA) {
        int diff = this.DEF - oTA;
        if (Math.random() < .375 + (diff * 0.005)) {
            return false;
        }
        double defQuality = 0.2 * ((int)(Math.random() * 5) + 1);
        return (int) (defQuality * this.DEF) > attScore;
    }

    boolean saveShot(int shot) {
        int diff = this.DEF - shot;
        return (Math.random() > .125 + (diff * 0.00025));
    }

    public String toString() {
        return this.position + ": " + this.name + ", " + this.age + " : \n" + this.overall + " OVR | " + this.potential
                + " POT" + "\n -----------" + this.printStats() + "\n\n";

    }

    public String printStats() {
        return "\n" + this.PAC + " PAC | " + this.DRI + " DRI" +
                "\n" + this.SHO + " SHO | " + this.DEF + " DEF" +
                "\n" + this.PAS + " PAS | " + this.PHY + " PHY";
    }

    // presents basic player attributes
    public String present() {
        return this.position + ": " + this.name + ", " + this.age + " : " + this.overall + " OVR\n";
    }

    public void endGame() {
        // Season Stats
        ssnGoals += gameGoals;
        ssnAssists += gameAssists;
        ssnApp++;

        // Game Stats
        gameGoals = 0;
        gameAssists = 0;
    }

}
