import javax.lang.model.type.ArrayType;
import java.util.ArrayList;

public class Schedule {

    ArrayList<Team> teams;
    int[][] matches = {
            {-1,28,16, 3, 5,25,27,29,18,17, 1,38,15, 7,30, 4,12,21,32,14},
            { 9,-1, 6,18,21,26, 4,13, 5,34,11,33, 3,27, 1,35,29,12,17,19},
            {35,25,-1,15, 8, 5,10,17,32, 3, 4,28,19,18,14,11,20,26, 2,31},
            {22,37,34,-1,36,28, 2, 1,11,23,12,10,26,33,32,38, 6,35, 8,24},
            {24, 2,27,17,-1,15,22,38,10,11,28,18,13, 6,35,12,33,23,26, 1},
            { 6, 7,24, 9,34,-1,12,30,21,10,33,16,36,13, 8, 1, 4,19,37, 3},
            { 8,23,29,21, 3,31,-1,35,38,33, 7,34,25,30,24,37,17,13,20, 9},
            {10,32,36,20,19,11,16,-1,22,21,27,25,14,31,15,28,24,37,23,26},
            {37,24,13,30,29, 2,19, 3,-1,16,17,27,31, 1,23,34,26, 9,14,25},
            {36,15,22, 4,30,29,14, 2,35,-1, 6, 1,18,38,31, 7, 9,27,24,32},
            {20,30,23,31, 9,14,26, 8,36,25,-1,32,10,34,37, 5, 2,22,19,16},
            {19,14, 9,29,37,35,15, 6, 8,20,13,-1, 5,23,26, 3,11,17,31, 2},
            {34,22,38, 7,32,17, 6,33,12,37,29,24,-1,35,28, 2,27,20,11, 4},
            {26, 8,37,14,25,32,11,12,20,19,15, 4,16,-1, 2,10, 3,24,28,36},
            {11,20,33,13,16,27, 5,34, 4,12,18, 7, 9,21,-1,17,38, 6, 3,10},
            {23,16,30,19,31,20,18, 9,15,26,24,22,21,29,36,-1,13,14,25,27},
            {31,10, 1,25,14,23,36, 5, 7,28,21,30, 8,22,19,32,-1,34,16,18},
            { 2,31, 7,16, 4,38,32,18,28, 8, 3,36, 1, 5,25,33,15,-1,10,30},
            {13,36,21,27, 7,18, 1, 4,33, 5,38,12,30, 9,22, 6,35,29,-1,34},
            {33,38,12, 5,20,22,28, 7, 6,13,35,21,23,17,29, 8,37,11,15,-1}
    };

    Schedule(ArrayList<Team> teams) {
        this.teams = teams;
    }

    void printSchedule() {
        for (int i = 0; i < matches.length; i++) {
            if (i == 0) {
                for (int j = 0; j < matches[0].length + 1; j++) {
                    if (j==0) {
                        System.out.print("    ");
                    } else if (j < 10){
                        System.out.print("  " + j + " ");
                    } else {
                        System.out.print(" " + j + " ");
                    }
                }
                System.out.println();
            }
            if (i + 1 > 9) {
                System.out.print((i + 1) + ": ");
            } else {
                System.out.print((i + 1) + ":  ");
            }

            for (int j = 0; j < matches[0].length; j++) {
                if (matches[i][j] < 10) {
                    System.out.print("[ " + matches[i][j] + "]");
                } else {
                    System.out.print("[" + matches[i][j] + "]");
                }
            }
            System.out.println();
        }
    }

    int getOpponent(int row, int week) {
        for (int i = 0 ; i < matches[row].length; i++) {
            if (matches[row][i] == week) {
                return i;
            }
        }
        return -1;
    }

    boolean contains(int row, int week) {
        for (int i = 0 ; i < matches[row].length; i++) {
            if (matches[row][i] == week) {
                return true;
            }
        }
        return false;
    }

    Match getMatch(int team, int week) {
        return new Match(this.teams.get(team), this.teams.get(this.getOpponent(team, week)));
    }

}
