package Configuration;

public class jsonTrack {
    public int variation = 0;
    public int x = 0;
    public int y = 0;
    public int tunnelConnectNum = 0;
    public int tunnelConnectExit = 0;
    public boolean isExit = false;

    jsonTrack(int variation, int x, int y, int tunnelConnectNum, int tunnelConnectExit, boolean isExit){
        this.variation = variation;
        this.x = x;
        this.y = y;
        this.tunnelConnectNum = tunnelConnectNum;
        this.tunnelConnectExit = tunnelConnectExit;
        this.isExit = isExit;
    }

    public int getX(){
        return this.x;
    }

    public boolean isExit(){
        return this.isExit;
    }
}
