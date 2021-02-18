public class Main {


    /////// NOTES

    // Player Class:
    /*
       player growth / decay with age
       player growth / decay with form
       player retires
       different play-styles
    */
    // Match Class:
    /*
       injuries
       substitutions
       red/yellow cards
       fouls
       match ratings / motm
       goal kicks
       use physical attributes in match
    */
    // Team Class:
    /*
       youth intake
       team transfers
       different play-styles
    */
    // League Class:
    /*

    */






















    public static void main(String[] args) {
        Test t = new Test();
        t.testSimulateLeague();
    }

}

class Test {

    League league = new League();

    Test(){}

    void testPresentLeague(){
        league.presentLeague();
    }

    void testPresentAllPlayers() {
        league.presentAllPlayers();
    }

    void testSimulateLeague() {
        league.simulateSeason();
        league.presentLeague();
        league.presentAllGoalscorers();
    }
}
