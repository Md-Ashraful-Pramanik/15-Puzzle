public abstract class Heuristics {
    public abstract int getHeuristics(int startTile,int goalTile);

    public int getHeuristicsCost(Puzzle startState,Puzzle goalState){
        int cost=0;
        for(int i=0;i<startState.getDimension();i++){
            for(int j=0;j<startState.getDimension();j++){
                if(startState.getTile(i,j)!=0)
                    cost+=getHeuristics(i* CONSTANT.FACTOR+j,
                            goalState.getTile(startState.getTile(i,j)));
            }
        }
        return cost;
    }
}

class DisplacementHeuristics extends Heuristics{

    @Override
    public int getHeuristics(int startTile, int goalTile) {
        if(startTile==goalTile)
            return 0;
        if(goalTile==0)
            return 0;

        return 1;
    }
}

class ManHattenHeuristics extends Heuristics{
    @Override
    public int getHeuristics(int startTile, int goalTile) {
        int i = Math.abs(startTile / CONSTANT.FACTOR - goalTile / CONSTANT.FACTOR) ;
        int j=Math.abs(startTile % CONSTANT.FACTOR - goalTile % CONSTANT.FACTOR);
        return i+j;
    }
}