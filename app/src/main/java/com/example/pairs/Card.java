package com.example.pairs;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Card extends AppCompatActivity {

    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6,
            cardView7, cardView8, cardView9, cardView10, cardView11, cardView12;
    ImageView card1Back, card1Front, card2Back, card2Front, card3Back, card3Front,
            card4Back, card4Front, card5Back, card5Front, card6Back, card6Front,
            card7Back, card7Front, card8Back, card8Front, card9Back, card9Front,
            card10Back, card10Front, card11Back, card11Front, card12Back, card12Front;
    Button btnRetry, btnQuit;

    CardView firstCardFlipped = null;
    CardView secondCardFlipped = null;
    boolean isChecking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card);

        // Get references to all CardViews
        cardView1 = findViewById(R.id.card1);
        cardView2 = findViewById(R.id.card2);
        cardView3 = findViewById(R.id.card3);
        cardView4 = findViewById(R.id.card4);
        cardView5 = findViewById(R.id.card5);
        cardView6 = findViewById(R.id.card6);
        cardView7 = findViewById(R.id.card7);
        cardView8 = findViewById(R.id.card8);
        cardView9 = findViewById(R.id.card9);
        cardView10 = findViewById(R.id.card10);
        cardView11 = findViewById(R.id.card11);
        cardView12 = findViewById(R.id.card12);

        // Get references to all back ImageViews
        card1Back = findViewById(R.id.card1_back);
        card2Back = findViewById(R.id.card2_back);
        card3Back = findViewById(R.id.card3_back);
        card4Back = findViewById(R.id.card4_back);
        card5Back = findViewById(R.id.card5_back);
        card6Back = findViewById(R.id.card6_back);
        card7Back = findViewById(R.id.card7_back);
        card8Back = findViewById(R.id.card8_back);
        card9Back = findViewById(R.id.card9_back);
        card10Back = findViewById(R.id.card10_back);
        card11Back = findViewById(R.id.card11_back);
        card12Back = findViewById(R.id.card12_back);

        // Get references to all front ImageViews
        card1Front = findViewById(R.id.card1_front);
        card2Front = findViewById(R.id.card2_front);
        card3Front = findViewById(R.id.card3_front);
        card4Front = findViewById(R.id.card4_front);
        card5Front = findViewById(R.id.card5_front);
        card6Front = findViewById(R.id.card6_front);
        card7Front = findViewById(R.id.card7_front);
        card8Front = findViewById(R.id.card8_front);
        card9Front = findViewById(R.id.card9_front);
        card10Front = findViewById(R.id.card10_front);
        card11Front = findViewById(R.id.card11_front);
        card12Front = findViewById(R.id.card12_front);

        // Get references to buttons
        btnRetry = findViewById(R.id.btn_retry);
        btnQuit = findViewById(R.id.btn_quit);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


}