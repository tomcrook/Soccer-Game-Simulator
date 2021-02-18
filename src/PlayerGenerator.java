
public class PlayerGenerator {

    String name;
    int overall, potential;
    NameGenerator nameGenerator = new NameGenerator();
    int age;
    String position;

    int PAC, pPAC;
    int SHO, pSHO;
    int PAS, pPAS;
    int DRI, pDRI;
    int DEF, pDEF;
    int PHY, pPHY;

    // WEIGHTS :
    // ST : SHO : 0.5, PAS : 0.25, DRI : 0.25, DEF : 0
    // WI : SHO : 0.3, PAS : 0.35, DRI : 0.35, DEF : 0
    // CM : SHO : 0.1, PAS : 0.4,  DRI : 0.35, DEF : 0.15
    // CB : SHO : 0,   PAS : 0.15, DRI : 0.15, DEF : 0.7
    // WB : SHO : 0,   PAS : 0.2,  DRI : 0.2,  DEF : 0.6
    // GK : SHO : 0,   PAS : 0.15, DRI : 0.05, DEF : 0.8

    PlayerGenerator(int playerAvg) {
        this.age = 16 + (int)(Math.random() * 20);
        this.name = nameGenerator.generateName();
        this.potential = this.realisticPotentialGenerator(playerAvg);
        this.overall = this.realisticOverallGenerator();
        this.position = nameGenerator.getPosition();
        this.realisticStatGenerator();
    }

    PlayerGenerator(int playerAvg, int age) {
        this.age = age;
        this.name = nameGenerator.generateName();
        this.potential = this.realisticPotentialGenerator(playerAvg);
        this.overall = this.realisticOverallGenerator();
        this.position = nameGenerator.getPosition();
        this.realisticStatGenerator();
    }

    PlayerGenerator() {
        this.age = 1000;
        this.name = "n/a";
        this.potential = -1;
        this.overall = -1;
        this.position = "NA";
        PAC = 0; pPAC = 0;
        SHO = 0; pSHO = 0;
        PAS = 0; pPAS = 0;
        DRI = 0; pDRI = 0;
        DEF = 0; pDEF = 0;
        PHY = 0; pPHY = 0;
    }

    PlayerGenerator(int playerAvg, String pos) {
        this.age = 16 + (int)(Math.random() * 20);
        this.name = nameGenerator.generateName();
        this.potential = this.realisticPotentialGenerator(playerAvg);
        this.overall = this.realisticOverallGenerator();
        this.position = pos;
        this.realisticStatGenerator();
    }

    // PLAYER GENERATION
    int realisticOverallGenerator() {
        int newAge = 28 - age;
        if (newAge < 0) {
            newAge = 0;
        }
        int add = (int)(Math.pow(newAge, 1.3));
        double percent = 1 - .01 * (Math.random() * add);
        return (int) (percent * (this.potential - 2*add/3));
    }

    int realisticPotentialGenerator(int standard) {
        int add = (38 - age);
        double percent = 1 - .01 * (Math.random() * add * Math.pow(-1, (int)(Math.random() * 2)));
        int pot =  (int) (percent * standard);
        if (pot > 99) {
            pot = 99;
        }
        return pot;
    }

    void realisticStatGenerator() {
        this.PAC = this.realisticPACGenerator();
        this.SHO = this.realisticSHOGenerator();
        this.PAS = this.realisticPASGenerator();
        this.DRI = this.realisticDRIGenerator();
        this.DEF = this.realisticDEFGenerator();
        this.PHY = this.realisticPHYGenerator();
    }

    int realisticPACGenerator() {
        int modifier = 0;
        if (this.position.equals("GK")) {
            modifier = 80;
        } else if (this.position.substring(0,1).equals("R") || this.position.substring(0,1).equals("L")) {
            modifier = 25;
        } else if (this.position.equals("ST")) {
            modifier = 30;
        } else {
            modifier = 40;
        }
        this.pPAC = 99 - (int) Math.sqrt(Math.random() * Math.pow(modifier, 2) + 1);
        double percent = 1 - .005 * (Math.random() * Math.pow((23 - age), 2));
        return (int) (percent * this.pPAC) ;
    }

    int realisticSHOGenerator() {
        double modifier = 45;
        if (this.position.equals("RM") || this.position.equals("LM")) {
            modifier = 70 + (int) Math.sqrt(Math.random() * Math.pow(40, 2) + 1);
        } else if (this.position.equals("ST")) {
            modifier = 80 + (int) Math.sqrt(Math.random() * Math.pow(30, 2) + 1);
        } else if (this.position.equals("CM")){
            modifier = 60 + (int) Math.sqrt(Math.random() * Math.pow(50, 2) + 1);
        }
        this.pSHO = (int) (this.potential * 0.01 * modifier);
        if (this.pSHO > 99) {
            modifier = 99 / (potential * 0.01);
            this.pSHO = 99;
        }
        return (int) (0.01 * modifier * this.overall) ;
    }

    int realisticPASGenerator() {
        double modifier = 60 + (int) Math.sqrt(Math.random() * Math.pow(45, 2) + 1);
        if (this.position.equals("RM") || this.position.equals("LM")) {
            modifier = 70 + (int) Math.sqrt(Math.random() * Math.pow(40, 2) + 1);
        } else if (this.position.equals("ST")) {
            modifier = 70 + (int) Math.sqrt(Math.random() * Math.pow(30, 2) + 1);
        } else if (this.position.equals("CM")){
            modifier = 70 + (int) Math.sqrt(Math.random() * Math.pow(40, 2) + 1);
        }
        this.pPAS = (int) (this.potential * 0.01 * modifier);
        if (this.pPAS > 99) {
            modifier = 99 / (potential * 0.01);
            this.pPAS = 99;
        }
        return (int) (modifier * 0.01 * this.overall) ;
    }

    int realisticDRIGenerator() {
        double modifier = 50 + (int) Math.sqrt(Math.random() * Math.pow(50, 2) + 1);
        if (this.position.equals("RM") || this.position.equals("LM")) {
            modifier = 75 + (int) Math.sqrt(Math.random() * Math.pow(40, 2) + 1);
            if (((int)(modifier * 0.01 * overall) + PAS + SHO < overall * 2.9
                    || (int)(modifier * 0.01 * this.overall) + this.PAS + this.SHO > this.overall * 3.1)
                    && this.overall > 20) {
                this.realisticStatGenerator();
            }
        } else if (this.position.equals("ST")) {
            modifier = 70 + (int) Math.sqrt(Math.random() * Math.pow(35, 2) + 1);
            if (this.SHO < this.overall) {
                modifier = 80 + (int) Math.sqrt(Math.random() * Math.pow(35, 2) + 1);
            }
        } else if (this.position.equals("CM")){
            modifier = 70 + (int) Math.sqrt(Math.random() * Math.pow(40, 2) + 1);
        }
        this.pDRI = (int) (this.potential * 0.01 * modifier);
        if (this.pDRI > 99) {
            modifier = 99 / (potential * 0.01);
            this.pDRI = 99;
        }
        return (int) (modifier * 0.01 * this.overall) ;
    }

    int realisticDEFGenerator() {
        double modifier = 10 + (int) Math.sqrt(Math.random() * Math.pow(40, 2) + 1);
        if (this.position.equals("RB") || this.position.equals("LB")) {
            modifier = 80 + (int) Math.sqrt(Math.random() * Math.pow(30, 2) + 1);
        } else if (this.position.equals("CB")) {
            modifier = 90 + (int) Math.sqrt(Math.random() * Math.pow(22, 2) + 1);
        } else if (this.position.equals("CM")){
            modifier = 60 + (int) Math.sqrt(Math.random() * Math.pow(40, 2) + 1);
        } else if (this.position.equals("GK")) {
            modifier = 90 + (int) Math.sqrt(Math.random() * Math.pow(22, 2) + 1);
        }
        this.pDEF = (int) (this.potential * 0.01 * modifier);
        if (this.pDEF > 99) {
            modifier = 99 / (potential * 0.01);
            this.pDEF = 99;
        }
        return (int) (modifier * 0.01 * this.overall) ;
    }

    int realisticPHYGenerator() {
        int modifier = 0;
        if (this.position.equals("GK")) {
            modifier = 25;
        } else if (this.position.substring(0,1).equals("R") || this.position.substring(0,1).equals("L")) {
            modifier = 50;
        } else if (this.position.equals("ST")) {
            modifier = 30;
        } else {
            modifier = 40;
        }
        this.pPHY = 99 - (int) Math.sqrt(Math.random() * Math.pow(modifier, 2) + 1);
        double percent = 1 - .0025 * (Math.random() * Math.pow((28 - age), 2));
        return (int) (percent * this.pPHY) ;
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

}
