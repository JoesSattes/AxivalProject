package com.axival.game;

import com.axival.game.MapPlay.MapScreen;
import com.axival.game.screen.ScreenPlay;

import java.util.Arrays;

public class StatusAxival {
    private int[] allStatus;
    public static int[] statusPhase; //server
    public static int[][] statusPlayer; //local
    public static int[][] playerDict;
    public static int[][] playerDictUseAp;
    public static int myClass, myClassPosition;
    public static int[][] statusPlayerEquip;
    public static int chaneEnable; //can chain ? if use min ap < num ap
    public static int cardCanChain; //chain card in hand amount
    //public static int
    public StatusAxival(){
        statusPlayer = new int[4][5]; //[ho, ap, range, atk, def]
        statusPhase = new int[13];
        playerDict = new int[3][5]; //[ho, ap, range, atk, def]
        playerDictUseAp = new int[3][3]; //[range, atk, def] to use
        statusPlayerEquip = new int[4][2];
        statusPhase[0] = 0; //Amount turn
        statusPhase[1] = 1; //character class
        statusPhase[2] = 5; //character class
        statusPhase[3] = 1; //character class
        statusPhase[4] = 3; //character class
        statusPhase[5] = 0; //who's in turn
        statusPhase[6] = 0; //turn 0=draw, 1,3=action, 2=travel, 4=end
        statusPhase[7] = 0; //action start player default=0
        statusPhase[8] = -1; //action attacker default=0
        statusPhase[9] = 0; //action target default=0
        statusPhase[10] = 0; //Travel phase who default= -1
        statusPhase[11] = 0; //Travel phase to col default= -1
        statusPhase[12] = 0; //Travel phase to row default= -1

        //my class
        //statusPhase[SelectHeroScreen.playerNo] += 3;


        //DT status
        playerDict[0][0] = 35;
        playerDict[0][1] = 35;
        playerDict[0][2] = 1;
        playerDict[0][3] = 4;
        playerDict[0][4] = 3;

        //Wizard status
        playerDict[1][0] = 30;
        playerDict[1][1] = 35;
        playerDict[1][2] = 1;
        playerDict[1][3] = 6;
        playerDict[1][4] = 1;

        //Priest status
        playerDict[2][0] = 25;
        playerDict[2][1] = 35;
        playerDict[2][2] = 1;
        playerDict[2][3] = 3;
        playerDict[2][4] = 2;

        //DT status use ap
        playerDictUseAp[0][0] = 2;
        playerDictUseAp[0][1] = 2;
        playerDictUseAp[0][2] = 2;

        //Wizard status use ap
        playerDictUseAp[1][0] = 1;
        playerDictUseAp[1][1] = 3;
        playerDictUseAp[1][2] = 2;


        //Priest status use ap
        playerDictUseAp[2][0] = 1;
        playerDictUseAp[2][1] = 3;
        playerDictUseAp[2][2] = 2;

        //set default equip
        statusPlayerEquip[0][0] = 0; //atk equip
        statusPlayerEquip[0][1] = 0; //def equip
        statusPlayerEquip[1][0] = 0; //atk equip
        statusPlayerEquip[1][1] = 0; //def equip
        statusPlayerEquip[2][0] = 0; //atk equip
        statusPlayerEquip[2][1] = 0; //def equip
        statusPlayerEquip[3][0] = 0; //atk equip
        statusPlayerEquip[3][1] = 0; //def equip

        //genClass();

        //genToStatusPlayer(0);
        //genToStatusPlayer(1);
        //genToStatusPlayer(2);
        //genToStatusPlayer(3);

        setApInPhase(0);
        setApInPhase(1);
        setApInPhase(2);
        setApInPhase(3);

        System.out.println("myClass : "+myClass+", myPos : "+myClassPosition);
        System.out.println("StatusPlay: "+ Arrays.toString(statusPlayer[2]));
    }

    public static void setApInPhase(int heroIndex){
        if (statusPhase[0]==0){
            statusPlayer[heroIndex][1] = 3;
        }
        else if (statusPhase[0]<10){
            statusPlayer[heroIndex][1] += 1;
        }
        else {
            statusPlayer[heroIndex][1] = 10;
        }
    }

    public static void genClass(){
        for (int i=1;i<=4;i++){
            if(statusPhase[i]>3){
                myClass = statusPhase[i]-3;
                myClassPosition = i-1;
                break;
            }
        }
    }

    public int[] getAllStatus() {
        return allStatus;
    }

    public void setAllStatus(int[] allStatus) {
        this.allStatus = allStatus;
    }

    public void calculateStatus(int select, int index, int value){
        //0=+, 1=-, 2=*, 3=/, 4=change value
        if(select==0){
            allStatus[index] += value;
        }
        else if(select==1){
            allStatus[index] -= value;
        }
        else if(select==2){
            allStatus[index] *= value;
        }
        else if(select==3){
            allStatus[index] /= value;
        }
        else if(select==4){
            allStatus[index] = value;
        }
    }

    public static void genToStatusPlayer(int i){
        if(statusPhase[i+1]>3){
            statusPlayer[i][0] = playerDict[statusPhase[i+1]-4][0];
            statusPlayer[i][1] = playerDict[statusPhase[i+1]-4][1];
            statusPlayer[i][2] = playerDict[statusPhase[i+1]-4][2];
            statusPlayer[i][3] = playerDict[statusPhase[i+1]-4][3];
            statusPlayer[i][4] = playerDict[statusPhase[i+1]-4][4];
        }
        else{
            statusPlayer[i][0] = playerDict[statusPhase[i+1]-1][0];
            statusPlayer[i][1] = playerDict[statusPhase[i+1]-1][1];
            statusPlayer[i][2] = playerDict[statusPhase[i+1]-1][2];
            statusPlayer[i][3] = playerDict[statusPhase[i+1]-1][3];
            statusPlayer[i][4] = playerDict[statusPhase[i+1]-1][4];
        }
    }

    public static void updateEquip(){
        for (int i=0; i<4; i++) {
            statusPlayer[i][3] = playerDict[myClass-1][3] + statusPlayerEquip[i][0];
            statusPlayer[i][4] = playerDict[myClass-1][4] + statusPlayerEquip[i][1];
        }
    }

}
