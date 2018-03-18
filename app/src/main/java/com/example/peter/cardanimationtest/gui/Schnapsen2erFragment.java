package com.example.peter.cardanimationtest.gui;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.peter.cardanimationtest.R;
import com.example.peter.cardanimationtest.gamelogic.Cards;
import com.example.peter.cardanimationtest.gamelogic.Player;
import com.example.peter.cardanimationtest.gamelogic.Schnapsen2er;
import com.example.peter.cardanimationtest.service.Schnapsen2erService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 06.02.2018.
 */

public class Schnapsen2erFragment extends Fragment {
    LinearLayout playerCardLayout;
    LinearLayout centerLayout;
    LinearLayout cardsInCenterLayout;
    LinearLayout topLayout;
    RelativeLayout trumpLayout;

    ImageView trumpImage;
    ImageView firstCardInMiddle;
    ImageView secondCardInMiddle;

    Context context;
    View view;

    TextView points;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        context=this.getActivity();
        return inflater.inflate(R.layout.schnapsen2er_layout,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        points=view.findViewById(R.id.points);

        playerCardLayout =view.findViewById(R.id.card_layout);
        centerLayout=view.findViewById(R.id.center_layout);
        cardsInCenterLayout=view.findViewById(R.id.center_card_layout);
        topLayout=view.findViewById(R.id.top_layout);
        this.view=view;

        Button restartButton=view.findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Schnapsen2erService.getInstance().setUp();
            }
        });

        Button zudrehenButton=view.findViewById(R.id.zudrehenButton);
        zudrehenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Schnapsen2erService.getInstance().zudrehen();
            }
        });


        Schnapsen2erService.getInstance().setUp();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem restart;
        MenuItem seeCards;
        MenuItem seeScore;
        MenuItem options;
        MenuItem backToMainMenu;
        restart.onC
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void clearAllViews(){
        playerCardLayout.removeAllViews();
        cardsInCenterLayout.removeAllViews();
        topLayout.removeAllViews();

        points.setText("0");
    }
    public void clearPlayerCardLayout(Player p){
        if(p.isNpc()){
            this.topLayout.removeAllViews();
        }
        else{
            this.playerCardLayout.removeAllViews();
        }
    }
    public void setTrump(Cards trump, View.OnClickListener listener){
        trumpLayout=view.findViewById(R.id.trump_layout);

        trumpImage =new ImageView(context);
        trumpImage.setImageResource(trump.getMipmap());
        int ids=View.generateViewId();
        trumpImage.setId(ids);
        trumpImage.setPadding(0 ,80,80,0);
        trumpImage.setRotation(90);
        trumpImage.setOnClickListener(listener);
        trumpImage.setTag(trump);

        ImageView cardback=new ImageView(context);
        cardback.setImageResource(R.mipmap.cardback);
        int id=View.generateViewId();
        cardback.setId(id);


        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
        cardback.setLayoutParams(params);

        cardback.setPadding(0,0,0,0);

        RelativeLayout.LayoutParams param=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.addRule(RelativeLayout.ALIGN_PARENT_END);
        trumpImage.setLayoutParams(param);

        trumpLayout.addView(trumpImage);
        trumpLayout.addView(cardback);
    }

    public void changeTrump(Cards cardInhand, Cards trump, View.OnClickListener playerCardListener){
        trumpImage.setImageResource(cardInhand.getMipmap());

        ImageView cardInHand= playerCardLayout.findViewWithTag(cardInhand);
        cardInHand.setImageResource(trump.getMipmap());
        cardInHand.setTag(trump);
        cardInHand.setOnClickListener(playerCardListener);


    }
    public void addCardToPlayerHand(Cards card, View.OnClickListener listener){
        addCard(card,this.playerCardLayout,listener);
    }
    public void addCardToPlayerHand(Cards card){
        addCardToPlayerHand(card,null);
    }
    public void addCardToCpuHand(Cards cards){
        addCardToCpuHand(cards,null);
    }
    public void addCardToHand(Cards c, Player p, View.OnClickListener listener){
        if(p.isNpc()){
            addCardToCpuHand(c);
        }else{
            addCardToPlayerHand(c,listener);
        }
    }
    public void addCardToCpuHand(Cards card, View.OnClickListener listener){
        addCard(card,this.topLayout,listener);
    }

    public void playCard(Cards card,Player p) {

        if(p.isNpc()) {
            ImageView cardView=topLayout.findViewWithTag(card);
            
            topLayout.removeView(cardView);
        }else {
            playerCardLayout.removeView(playerCardLayout.findViewWithTag(card));
        }
        addCard(card, cardsInCenterLayout);
    }
    public void addCard(Cards card, LinearLayout linearLayout){
        addCard(card,linearLayout,null);
    }
    public void addCard(Cards card, LinearLayout linearLayout,View.OnClickListener listener){
       /* RelativeLayout buttonLayout=new RelativeLayout(context);
        RelativeLayout.LayoutParams relativeParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        buttonLayout.setLayoutParams(relativeParams);
*/

        ImageView card1 =new ImageView(context);
        card1.setImageResource(card.getMipmap());
        card1.setPadding(20 ,0,20,0);
        card1.setTag(card);
        if(listener!=null) {
            card1.setOnClickListener(listener);
        }
        //buttonLayout.addView(card1);
        //linearLayout.addView(buttonLayout);
        linearLayout.addView(card1);
    }

    public void clearMiddleLayout() {
        this.cardsInCenterLayout.removeAllViews();
    }

    public void removeTrump() {
        trumpLayout.removeAllViews();
    }

    public void updatePoints(Schnapsen2er schnapsen2er) {
        points.setText(Integer.toString(schnapsen2er.getPlayer().getPoints()));

    }

    public void showGameEndDialog(String s) {
        GameEndDialog gameEndDialog=new GameEndDialog();
        gameEndDialog.setEndMessage(s);
        gameEndDialog.show(getFragmentManager(),"end");
    }

    public void add20erButton(ArrayList<String> zwanziger) {
        LinearLayout zwanzigerButtonLayout=this.getView().findViewById(R.id.zwanziger_button_layout);
        zwanzigerButtonLayout.removeAllViews();

        if(zwanziger.isEmpty()){
            return;
        }
        ArrayList<View> views=playerCardLayout.getTouchables();
        System.out.println(zwanziger.toString());
        for(String s:zwanziger){
            for(int idx=0;idx<views.size();idx++){
                Cards c=((Cards)views.get(idx).getTag());
                if(c.getCard()=="King"&&c.getFarbe()==s){
                    add20erButtonToCard(c, views.get(idx),views.get(idx+1));
                    break;
                }
            }
        }
    }

    private void add20erButtonToCard(Cards c, View v,View v2)  {
/*
        ImageView imageView=(ImageView) v;
        Button button=new Button(context);
        int[] location=new int[2];

        imageView.getLocationOnScreen(location);
        System.out.println(location[0]+", "+location[1]+", "+imageView.getTag().toString());
        LinearLayout zwanzigerButtonLayout=this.getView().findViewById(R.id.zwanziger_button_layout);

        ViewGroup.MarginLayoutParams buttonMargin=new ViewGroup.MarginLayoutParams(59,35);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        buttonMargin.leftMargin=0;
        button.setText("20er");

        button.setLayoutParams(buttonMargin);
        zwanzigerButtonLayout.addView(button);
        System.out.println("Added button at: "+location[0]);
        */
        ImageView imageView=(ImageView) v;

      //  int[] location=new int[2];
      //  imageView.getLocationOnScreen(location);
        LinearLayout zwanzigerButtonLayout=this.getView().findViewById(R.id.zwanziger_button_layout);
        Button button=new Button(context);
        //ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        button.setText(Schnapsen2erService.getInstance().get20erButtonText(c));
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP,12);
        button.setOnClickListener(Schnapsen2erService.getInstance().get20erButtonListener(c));

        //ViewGroup.MarginLayoutParams marginLayoutParams=new ViewGroup.MarginLayoutParams(params);
        //marginLayoutParams.leftMargin=location[0]+85;
        //button.setLayoutParams(marginLayoutParams);


       /* RelativeLayout buttonLayout=wrapCardsInRelativeLayout(v,v2);
        RelativeLayout.LayoutParams relativeParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParams.addRule(RelativeLayout.ALIGN_BOTTOM);

        buttonLayout.addView(v);
        buttonLayout.addView(v2);
*/


        zwanzigerButtonLayout.addView(button);


        //zwanzigerButtonLayout.addView(button);
    }

    private RelativeLayout wrapCardsInRelativeLayout(View v, View v2) {
        RelativeLayout buttonLayout=new RelativeLayout(context);
        RelativeLayout.LayoutParams relativeParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParams.addRule(RelativeLayout.ALIGN_BOTTOM);

        buttonLayout.addView(v);
        buttonLayout.addView(v2);

        LinearLayout cardlayout=(LinearLayout) v.getParent();
        cardlayout.removeView(v);
        cardlayout.removeView(v2);
        return null;

    }

    public void disableCardsExept(Cards c, List<Cards> hand) {
        hand.stream().filter(card->!(card.getFarbe()==c.getFarbe())&&(card.getCard()=="King"||card.getCard()=="Queen")).forEach(card->playerCardLayout.findViewWithTag(card).setEnabled(false));

    }

    public void enableAllCards() {
        playerCardLayout.getTouchables().stream().forEach(v->v.setEnabled(true));
    }
}
