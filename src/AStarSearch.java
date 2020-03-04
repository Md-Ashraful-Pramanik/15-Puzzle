import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class AStarSearch {
    Puzzle startState;
    Puzzle goalState;

    Heuristics heuristics;

    int pathCost;

    public AStarSearch(Puzzle startState, Puzzle goalState) {
        this.startState = startState;
        this.goalState = goalState;
        pathCost=Integer.MAX_VALUE;
    }

    public long displacementHeuristicsSearch(){
        heuristics=new DisplacementHeuristics();
        return search();
    }

    public long manHattenHeuristicsSearch(){
        heuristics=new ManHattenHeuristics();
        return search();
    }

    private long search(){

        if(startState.equals(goalState)){
            pathCost=0;
            return 0;
        }

        startState.setActualCost(0);
        startState.setTotalEstimatedCost(heuristics.getHeuristicsCost(startState,goalState));

        pathCost=Integer.MAX_VALUE;

        int actualCost,heuristicsCost,totalEstimatedCost;

        PriorityQueue<Puzzle> priorityQueue =
                new PriorityQueue<>(Comparator.comparing(Puzzle::getTotalEstimatedCost));
        priorityQueue.add(startState);

        HashSet<String> exploredStateTable=new HashSet<>(CONSTANT.HASH_SET_CAPACITY,CONSTANT.LOAD_FACTOR);

        Puzzle currentState,newState;

        while (!priorityQueue.isEmpty()){

            currentState = priorityQueue.poll();

            if(exploredStateTable.contains(currentState.getBoardAsString()))
                continue;

            exploredStateTable.add(currentState.getBoardAsString());

            if(currentState.getTotalEstimatedCost() >= pathCost)
                continue;

            actualCost=currentState.getActualCost() + 1;

            for(int i=0;i<4;i++){
                newState=getNewState(i,currentState);

                if(newState != null && newState.isSolvable()){

                    heuristicsCost = heuristics.getHeuristicsCost(newState,goalState);
                    totalEstimatedCost = actualCost + heuristicsCost;

                    newState.setActualCost(actualCost);
                    newState.setTotalEstimatedCost(totalEstimatedCost);

                    if(heuristicsCost == 0){
                        pathCost=actualCost;
                        goalState.setSuccessor(currentState);
                        return exploredStateTable.size();
                    }
                    else if(!exploredStateTable.contains(newState)){
                        newState.setSuccessor(currentState);
                        priorityQueue.add(newState);
                    }
                }
            }

            /*
            if((exploredStateTable.size()+priorityQueue.size())%100000==0){
                System.out.println("Total State: "+(exploredStateTable.size()+priorityQueue.size()));
            }
             */
        }
        return exploredStateTable.size();

    }

    public Puzzle getNewState(int index,Puzzle currentState) {
        switch (index){
            case 0:return currentState.moveLeft();
            case 1:return currentState.moveRight();
            case 2:return currentState.moveUp();
            case 3:return currentState.moveDown();
        }
        return null;
    }

    public Puzzle getStartState() {
        return startState;
    }

    public void setStartState(Puzzle startState) {
        this.startState = startState;
    }

    public Puzzle getGoalState() {
        return goalState;
    }

    public void setGoalState(Puzzle goalState) {
        this.goalState = goalState;
    }

    public Heuristics getHeuristics() {
        return heuristics;
    }

    public void setHeuristics(Heuristics heuristics) {
        this.heuristics = heuristics;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }
}
