package com.example.peter.cardanimationtest.gui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peter.cardanimationtest.R;
import com.example.peter.cardanimationtest.gamelogic.Cards;
import com.example.peter.cardanimationtest.gamelogic.Player;
import com.example.peter.cardanimationtest.gamelogic.Schnapsen2er;
import com.example.peter.cardanimationtest.service.Schnapsen2erService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * Stirngs
 * Menü mit
 *  Score keeper
 *  Gestochene Karten sehen
 *  Restart
 *  Options
 * Animationen
 * GUI Säubern (Landscape)
 * CPU Logic
 * Datenbank mit Score, Players
 * Multiplayer
 */
public class FullscreenActivity extends AppCompatActivity {


    FrameLayout frameLayout;
    List<Fragment> fragments=new ArrayList<>();
    Schnapsen2erService schnapsen2erService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);


        Schnapsen2erFragment schnapsen2erFragment=new Schnapsen2erFragment();
        fragments.add(schnapsen2erFragment);

        changeFrameLayout(schnapsen2erFragment);

        Schnapsen2er schnapsen2er=new Schnapsen2er();

        schnapsen2erService=Schnapsen2erService.getInstance();
        schnapsen2erService.setSchnapsen2er(schnapsen2er);
        schnapsen2erService.setSchnapsen2erFragment(schnapsen2erFragment);



    }


    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        schnapsen2erService.setUp();

    }

    private void changeFrameLayout(Fragment fragment){
        FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_framelayout,fragment);
        fragmentTransaction.commit();
    }


    /*
    private Cards getCPUCardToPlay(){
        Cards ret=null;
        if(farbegeben){

        }
        System.out.println("CPU Hand:"+cpu.getHand().size());
        Cards help=null;

        //Trumpf Bub austauschen, wenn möglich

        for(Cards c: cpu.getHand()){
            if(c.getFarbe()==trump.getFarbe()&&c.getCard()=="Jack"&&cards.size()>0){
                help=c;
            }
        }
        if(help!=null){
            changeTrumpCPU(help,trumpLayout.findViewWithTag(trump));
        }
        /////////////////////////////////////////////////
        //Wenn Weniger als 4 Karten auf der Hand, dann Trumpf ausspielen, sonst nicht

        if(cards.size()<5){
            for(Cards c:cpu.getHand()){
                if(c.getFarbe()==trump.getFarbe()){
                    return c;
                }
            }
        }else{
            for(Cards c:cpu.getHand()){
                if(c.getFarbe()!=trump.getFarbe()){
                    return c;
                }
            }
        }
        ///////////////////////////////////////////////////
        //Erste Karte wird ausgespielt
        for(Cards c:cpu.getHand()){
            return c;
        }

        return ret;
    }

    public void playCard(View view) throws InterruptedException {


        System.out.println("Player Hand:"+player.getHand().size());

        cardPlayer =(Cards)view.getTag();
        if(playernext) {
            placeCardInCenter(cardPlayer,player);
            cardCPU = getCPUCardToPlay();
            placeCardInCenter(cardCPU,cpu);
        }else{
            placeCardInCenter(cardPlayer,player);
        }
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                points.setText(Integer.toString(player.getPoints()));
                centerLayout.removeAllViews();

                //
                if(!playernext&&cpu.getHand().size()!=0){
                    cardCPU = getCPUCardToPlay();
                    placeCardInCenter(cardCPU,cpu);
                }
                if(cards.size()==1){
                    trumpLayout.removeAllViews();
                    if(playernext){
                        cpuDrawCard();
                        playerDrawCard();
                    }else{
                        playerDrawCard();
                        cpuDrawCard();
                    }
                }else if(cards.size()>0){
                    playerDrawCard();
                    cpuDrawCard();
                }
                for(View v:l){
                    v.setEnabled(true);
                }
            }
        },2000);


        if(playerStich(cardPlayer, cardCPU)){
            if(player.getHand().size()==0&&!gameEnd){
                Toast t=Toast.makeText(this,"You win letzter stich",Toast.LENGTH_LONG);
                t.show();
                gameEnd=true;
                reset();

                return;
            }
            player.getStock().add(cardPlayer);
            player.getStock().add(cardCPU);
            playernext=true;
        }else{
            if(player.getHand().size()==0&&!gameEnd){
                Toast t=Toast.makeText(this,"You lose letzter stich",Toast.LENGTH_LONG);
                t.show();
                gameEnd=true;
                reset();

                return;
            }
            cpu.getStock().add(cardPlayer);
            cpu.getStock().add(cardCPU);
            playernext=false;
        }

        if(player.getPoints()>65&&!gameEnd){
            Toast t=Toast.makeText(this,"You win",Toast.LENGTH_LONG);
            t.show();
            gameEnd=true;
            reset();

            return;
        }else if(cpu.getPoints()>65&&!gameEnd){
            Toast t=Toast.makeText(this,"You lose",Toast.LENGTH_LONG);
            t.show();
            gameEnd=true;
            reset();
            return;
        }


        l=playerCardLayout.getTouchables();

        System.out.println(l.size());
        for(View v:l){
            v.setEnabled(false);
        }

    }
    private boolean playerStich(Cards cPl, Cards cCPU){
        if(cPl.getFarbe()==trump.getFarbe()&&cCPU.getFarbe()!=trump.getFarbe()){
            return true;
        }else if (cPl.getFarbe()!=trump.getFarbe()&&cCPU.getFarbe()==trump.getFarbe()){
            return false;
        }else if(cCPU.getFarbe()==cPl.getFarbe()){
            return cPl.getPoints()>cCPU.getPoints();
        }else if(playernext){
            return true;
        }else{
            return false;
        }
    }
    private void dealCPUCards(){
        for(int idx=0;idx<5;idx++){
            cpuDrawCard();

        }

    }

    private void dealPlayerCards(){

        for(int idx=0;idx<5;idx++){
            playerDrawCard();
        }

    }

    private void changeTrumpCPU(Cards c, View view){

        ((ImageView)view).setImageResource(c.getMipmap());

        cpu.getHand().add(trump);
        cpu.getHand().remove(c);
        System.out.println(cpu.getHand().toString());
        trump=c;
    }


    private void cpuDrawCard(){
        Cards card=drawCard();
        ImageView card1 =new ImageView(this);
        card1.setImageResource(R.mipmap.cardback);
        card1.setPadding(20 ,0,20,0);
        card1.setTag(card);
        cpu.getHand().add(card);
        topLayout.addView(card1);

    }

    private void addCard(){
        addCard(drawCard());
    }

    private void addCard(Cards card){
        addCard(card,playerCardLayout);
    }
    private void addCard(Cards card, LinearLayout linearLayout){
        ImageView card1 =new ImageView(this);
        card1.setImageResource(card.getMipmap());
        card1.setPadding(20 ,0,20,0);
        card1.setTag(card);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    playCard(view);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        linearLayout.addView(card1);

    }


    private void placeCardInCenter(Cards c, Player player){
        player.getLinearLayout().removeView(player.getLinearLayout().findViewWithTag(c));
        addCard(c, centerLayout);
        player.getHand().remove(c);
    }
    private void playerDrawCard(){
        Cards c=drawCard();
        addCard(c,playerCardLayout);
        player.getHand().add(c);
    }
*/
}
