public class Puzzle implements Cloneable{
    byte dimension;
    byte[][] tiles;
    byte[] tilesNumber;
    int totalEstimatedCost;
    int actualCost;
    Puzzle successor;

    public Puzzle(int dimension){
        this.dimension = (byte)dimension;
        tiles=new byte[dimension][dimension];
        tilesNumber=new byte[dimension*dimension];
        totalEstimatedCost=Integer.MAX_VALUE;
        actualCost=Integer.MAX_VALUE;
    }

    public void setTile(int x,int y,int number){
        tiles[x][y]= (byte) number;

        tilesNumber[number]= (byte) (x*CONSTANT.FACTOR+y);
    }

    public int getTile(int number){
        return tilesNumber[number];
    }

    public int getTile(int x,int y){
        return tiles[x][y];
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = (byte) dimension;
    }

    public byte[][] getTiles() {
        return tiles;
    }

    public void setTiles(byte[][] tiles) {
        this.tiles = tiles;
    }

    public int getVacantTileX() {
        return tilesNumber[0]/CONSTANT.FACTOR;
    }

    public int getVacantTileY() {
        return tilesNumber[0]%CONSTANT.FACTOR;
    }

    public void setVacantTile(int vacantTile) {
        this.tilesNumber[0] = (byte) vacantTile;
    }

    public int getTotalEstimatedCost() {
        return totalEstimatedCost;
    }

    public void setTotalEstimatedCost(int actualCost) {
        this.totalEstimatedCost = actualCost;
    }

    public int getActualCost() {
        return actualCost;
    }

    public void setActualCost(int actualCost) {
        this.actualCost = actualCost;
    }

    public Puzzle getSuccessor() {
        return successor;
    }

    public void setSuccessor(Puzzle successor) {
        this.successor = successor;
    }

    public boolean isSolvable(){
        if(dimension%2==0)
            return isSolvableEven();
        else
            return isSolvableOdd();
    }

    public boolean isSolvableOdd(){
        return getInversionCount()%2==0;
    }

    public boolean isSolvableEven(){
        int vacantPosition=getVacantTileX();
        int inversionCount=getInversionCount();

        //System.out.println(inversionCount);
        //System.out.println(vacantPosition);

        if(vacantPosition%2==0 && inversionCount%2!=0)
            return true;
        if(vacantPosition%2!=0 && inversionCount%2==0)
            return true;

        return false;
    }

    public int getInversionCount(){
        int inversion=0;
        for(int i=0;i<dimension;i++){
            for(int j=0;j<dimension;j++){
                int l=j+1;
                for(int k=i;k<dimension;k++){
                    for(;l<dimension;l++){
                        if(tiles[i][j]!=0 && tiles[k][l]!=0 && tiles[i][j]>tiles[k][l])
                            inversion++;
                    }
                    l=0;
                }
            }
        }
        //System.out.println(inversion);
        return inversion;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Puzzle){
            Puzzle puzzle=(Puzzle) obj;
            for(int i=0;i<dimension;i++){
                for(int j=0;j<dimension;j++){
                    if(tiles[i][j]!=puzzle.tiles[i][j])
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode=0;

        for(int i=0;i<dimension;i++){
            for(int j=0;j<dimension;j++){
                hashCode+=tiles[i][j]*tiles[i][j]*tiles[i][j]*(i*CONSTANT.FACTOR+j);
            }
        }
        if(Integer.MIN_VALUE==hashCode*7979*19)
            return Integer.MAX_VALUE;

        return Math.abs(hashCode*7979*19);
    }

    private Puzzle copy() {
        Puzzle copiedPuzzle=new Puzzle(dimension);
        for(int i=0;i<dimension;i++){
            for(int j=0;j<dimension;j++){
                copiedPuzzle.setTile(i,j,getTile(i,j));
            }
        }
        return copiedPuzzle;
    }

    public Puzzle moveLeft() {
        if(getVacantTileY()==0)
            return null;

        Puzzle copiedPuzzle=copy();
        copiedPuzzle.setTile(getVacantTileX(),getVacantTileY(),tiles[getVacantTileX()][getVacantTileY()-1]);
        copiedPuzzle.setTile(getVacantTileX(),getVacantTileY()-1,0);

        return copiedPuzzle;
    }

    public Puzzle moveRight() {
        if(getVacantTileY()==(dimension-1))
            return null;

        Puzzle copiedPuzzle=copy();
        copiedPuzzle.setTile(getVacantTileX(),getVacantTileY(),tiles[getVacantTileX()][getVacantTileY()+1]);
        copiedPuzzle.setTile(getVacantTileX(),getVacantTileY()+1,0);

        return copiedPuzzle;
    }

    public Puzzle moveUp() {
        if(getVacantTileX()==0)
            return null;

        Puzzle copiedPuzzle=copy();

        copiedPuzzle.setTile(getVacantTileX(),getVacantTileY(),tiles[getVacantTileX()-1][getVacantTileY()]);
        copiedPuzzle.setTile(getVacantTileX()-1,getVacantTileY(),0);

        return copiedPuzzle;
    }

    public Puzzle moveDown() {
        if(getVacantTileX()==(dimension-1))
            return null;

        Puzzle copiedPuzzle=copy();
        copiedPuzzle.setTile(getVacantTileX(),getVacantTileY(),tiles[getVacantTileX()+1][getVacantTileY()]);
        copiedPuzzle.setTile(getVacantTileX()+1,getVacantTileY(),0);

        return copiedPuzzle;
    }

    public String getBoardAsString(){
        String s="";
        for(int i=0;i<dimension;i++){
            for(int j=0;j<dimension;j++){
                s+=(char)(tiles[i][j]+'a');
            }
        }

        return s;
    }

    public void print(){
        for(int i=0;i<dimension;i++){
            for(int j=0;j<dimension;j++){
                System.out.printf("%3d ",tiles[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }
}

