import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.System.gc;

public class Main {

    public static void main(String[] args) throws Exception {
        long startTime,endTime,displacementTime,manhattenTime,exploredNode;
        AStarSearch aStarSearch;
        Scanner scanner;
        try {
            scanner = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return;
        }

        int testCaseCount=scanner.nextInt()-1;

        Puzzle startState=new Puzzle(CONSTANT.DIMENSION);

        for(int i = 0; i< CONSTANT.DIMENSION; i++){
            for(int j = 0; j< CONSTANT.DIMENSION; j++){
                startState.setTile(i,j,scanner.nextInt());
            }
        }

        for(int k=0;k<testCaseCount;k++){

            Puzzle goalState=new Puzzle(CONSTANT.DIMENSION);

            for(int i = 0; i< CONSTANT.DIMENSION; i++){
                for(int j = 0; j< CONSTANT.DIMENSION; j++){
                    goalState.setTile(i,j,scanner.nextInt());
                }
            }

            //startState.print();
            //goalState.print();


            if(startState.isSolvable()==false || goalState.isSolvable()==false){
                System.out.println("\n\n-------------------------------------------------------");
                System.out.println("Puzzle is not solvable.");
                System.out.println("-------------------------------------------------------");
                System.out.println("-------------------------------------------------------\n");
                continue;
            }

            aStarSearch=new AStarSearch(startState,goalState);

            /*
            HashSet<Puzzle> exploredStateTable=new HashSet<>();

            Puzzle puzzle=aStarSearch.getNewState(0,startState);

            exploredStateTable.add(puzzle);
            exploredStateTable.add(startState);

            System.out.println(exploredStateTable.contains(puzzle.copyRight()));

            puzzle.copyRight().print();
            puzzle.print();
            startState.print();

             */

            System.out.println("Manhatten:");
            System.out.println("------------------------------------------------------\n\n");

            startTime=System.nanoTime();
            exploredNode=aStarSearch.manHattenHeuristicsSearch();
            endTime=System.nanoTime();
            manhattenTime=endTime-startTime;
            printPath(goalState);
            System.out.println("Manhatten:");
            System.out.println("Cost: "+aStarSearch.getPathCost());
            System.out.println("Explored node: "+exploredNode);
            System.out.println("Time: "+manhattenTime/1000000.0+" ms");
            System.out.println("Time: "+manhattenTime/1000000000.0+" s"+"\n\n\n");

            goalState.setSuccessor(null);
            gc();

            System.out.println("DisPlacement:");
            System.out.println("------------------------------------------------------\n\n");

            startTime=System.nanoTime();
            aStarSearch.displacementHeuristicsSearch();
            endTime=System.nanoTime();
            displacementTime=endTime-startTime;
            printPath(goalState);
            System.out.println("DisPlacement:");
            System.out.println("Cost: "+aStarSearch.getPathCost());
            System.out.println("Explored node: "+exploredNode);
            System.out.println("Time: "+displacementTime/1000000.0+" ms");
            System.out.println("Time: "+displacementTime/1000000000.0+" s"+"\n");
            System.out.println("-------------------------------------------------------------------------");
            System.out.println("Time difference (Displacement Time - Manhatten Time): "+
                    (displacementTime-manhattenTime)/1000000.0+" ms");
            System.out.println("-------------------------------------------------------------------------");

            System.out.println("\n\n-------------------------------------------------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("-------------------------------------------------------\n");
            gc();
        }
    }

    public static void printPath(Puzzle state) {
        if(state==null)
            return;

        printPath(state.getSuccessor());
        state.print();

        System.out.println("\t\t||\t");
        System.out.println("\t\t\\/\t");
        System.out.println("\n");
    }
}
