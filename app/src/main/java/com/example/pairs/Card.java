package com.example.pairs;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Card extends AppCompatActivity implements View.OnClickListener {

    //Declaring variables
    private static final int ROWS = 4;
    private static final int COLS = 3;
    private CardView[][] cardViews = new CardView[ROWS][COLS];
    private ImageView[][] frontImageViews = new ImageView[ROWS][COLS];
    private ImageView[][] backImageViews = new ImageView[ROWS][COLS];

    private CardView firstCardFlipped = null;
    private CardView secondCardFlipped = null;
    private boolean isChecking = false;
    private Handler handler = new Handler(); //Para mag-create ng delay tuwing nag f-flip ng card

    private final Integer[] frontImageResources = { //Array para i-store yung mga resource id ng mga image na gagamitin sa card
            R.drawable.blaze,
            R.drawable.enderman,
            R.drawable.pigman,
            R.drawable.skeleton,
            R.drawable.steve,
            R.drawable.zombie
    };
    private final List<Integer> gameImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card);

        //Storing the cards to the array

        cardViews[0][0] = findViewById(R.id.card1);
        cardViews[0][1] = findViewById(R.id.card2);
        cardViews[0][2] = findViewById(R.id.card3);
        cardViews[1][0] = findViewById(R.id.card4);
        cardViews[1][1] = findViewById(R.id.card5);
        cardViews[1][2] = findViewById(R.id.card6);
        cardViews[2][0] = findViewById(R.id.card7);
        cardViews[2][1] = findViewById(R.id.card8);
        cardViews[2][2] = findViewById(R.id.card9);
        cardViews[3][0] = findViewById(R.id.card10);
        cardViews[3][1] = findViewById(R.id.card11);
        cardViews[3][2] = findViewById(R.id.card12);

        backImageViews[0][0] = findViewById(R.id.card1_back);
        backImageViews[0][1] = findViewById(R.id.card2_back);
        backImageViews[0][2] = findViewById(R.id.card3_back);
        backImageViews[1][0] = findViewById(R.id.card4_back);
        backImageViews[1][1] = findViewById(R.id.card5_back);
        backImageViews[1][2] = findViewById(R.id.card6_back);
        backImageViews[2][0] = findViewById(R.id.card7_back);
        backImageViews[2][1] = findViewById(R.id.card8_back);
        backImageViews[2][2] = findViewById(R.id.card9_back);
        backImageViews[3][0] = findViewById(R.id.card10_back);
        backImageViews[3][1] = findViewById(R.id.card11_back);
        backImageViews[3][2] = findViewById(R.id.card12_back);

        frontImageViews[0][0] = findViewById(R.id.card1_front);
        frontImageViews[0][1] = findViewById(R.id.card2_front);
        frontImageViews[0][2] = findViewById(R.id.card3_front);
        frontImageViews[1][0] = findViewById(R.id.card4_front);
        frontImageViews[1][1] = findViewById(R.id.card5_front);
        frontImageViews[1][2] = findViewById(R.id.card6_front);
        frontImageViews[2][0] = findViewById(R.id.card7_front);
        frontImageViews[2][1] = findViewById(R.id.card8_front);
        frontImageViews[2][2] = findViewById(R.id.card9_front);
        frontImageViews[3][0] = findViewById(R.id.card10_front);
        frontImageViews[3][1] = findViewById(R.id.card11_front);
        frontImageViews[3][2] = findViewById(R.id.card12_front);


        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                cardViews[i][j].setOnClickListener(this);
            }
        }

        setupGame();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupGame() {
        gameImages.clear();
        for (Integer resource : frontImageResources) {
            gameImages.add(resource);
            gameImages.add(resource);
        }
        Collections.shuffle(gameImages);

        int imageIndex = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                frontImageViews[i][j].setImageResource(gameImages.get(imageIndex++));
                frontImageViews[i][j].setVisibility(View.INVISIBLE);
                backImageViews[i][j].setVisibility(View.VISIBLE);
                cardViews[i][j].setRotationY(0f); // Ensure cards are not rotated at the start
                cardViews[i][j].setTag(null);
            }
        }

        firstCardFlipped = null;
        secondCardFlipped = null;
        isChecking = false;
    }

    @Override
    public void onClick(View view) { //Flips the card when clicked
        if (isChecking) {
            return;
        }

        CardView clickedCardView = (CardView) view;

        if (clickedCardView == firstCardFlipped || clickedCardView.getTag() != null) {
            return;
        }

        flipCard(clickedCardView);

        if (firstCardFlipped == null) {
            firstCardFlipped = clickedCardView;
        } else {
            secondCardFlipped = clickedCardView;
            isChecking = true;
            checkForMatch();
        }
    }

    private void flipCard(CardView cardView) {
        ObjectAnimator flipOutAnimator = ObjectAnimator.ofFloat(cardView, "rotationY", cardView.getRotationY(), 90f);
        ObjectAnimator flipInAnimator = ObjectAnimator.ofFloat(cardView, "rotationY", -90f, 0f);

        flipOutAnimator.setDuration(200);
        flipInAnimator.setDuration(200);

        flipOutAnimator.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                ImageView front = getFrontImageView(cardView);
                ImageView back = getBackImageView(cardView);
                if (front != null && back != null) {
                    back.setVisibility(View.INVISIBLE);
                    front.setVisibility(View.VISIBLE);
                    flipInAnimator.start();
                }
            }
        });

        flipOutAnimator.start();
    }

    private void flipBack(CardView cardView1, CardView cardView2) {
        ObjectAnimator flipOutAnimator1 = ObjectAnimator.ofFloat(cardView1, "rotationY", cardView1.getRotationY(), 90f);
        ObjectAnimator flipInAnimator1 = ObjectAnimator.ofFloat(cardView1, "rotationY", -90f, 0f);
        ObjectAnimator flipOutAnimator2 = ObjectAnimator.ofFloat(cardView2, "rotationY", cardView2.getRotationY(), 90f);
        ObjectAnimator flipInAnimator2 = ObjectAnimator.ofFloat(cardView2, "rotationY", -90f, 0f);

        flipOutAnimator1.setDuration(200);
        flipInAnimator1.setDuration(200);
        flipOutAnimator2.setDuration(200);
        flipInAnimator2.setDuration(200);

        flipOutAnimator1.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                ImageView front = getFrontImageView(cardView1);
                ImageView back = getBackImageView(cardView1);
                if (front != null && back != null) {
                    front.setVisibility(View.INVISIBLE);
                    back.setVisibility(View.VISIBLE);
                    flipInAnimator1.start();
                }
            }
        });

        flipOutAnimator2.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                ImageView front = getFrontImageView(cardView2);
                ImageView back = getBackImageView(cardView2);
                if (front != null && back != null) {
                    front.setVisibility(View.INVISIBLE);
                    back.setVisibility(View.VISIBLE);
                    flipInAnimator2.start();
                }
            }
        });

        flipOutAnimator1.start();
        flipOutAnimator2.start();
    }

    private void checkForMatch() {
        if (firstCardFlipped == null || secondCardFlipped == null) {
            isChecking = false;
            return;
        }

        ImageView firstFront = getFrontImageView(firstCardFlipped);
        ImageView secondFront = getFrontImageView(secondCardFlipped);

        if (firstFront != null && secondFront != null &&
                firstFront.getDrawable().getConstantState().equals(secondFront.getDrawable().getConstantState())) {
            firstCardFlipped.setTag("matched");
            secondCardFlipped.setTag("matched");
            firstCardFlipped = null;
            secondCardFlipped = null;
            isChecking = false;
            checkGameCompletion();
        } else {
            handler.postDelayed(() -> { //Naglalagay ng delay, hinihintay muna ma flip yung card bago makapindot uli ng bago
                flipBack(firstCardFlipped, secondCardFlipped);
                firstCardFlipped = null;
                secondCardFlipped = null;
                isChecking = false;
            }, 1000);
        }
    }

    private ImageView getFrontImageView(CardView cardView) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (cardViews[i][j] == cardView) {
                    return frontImageViews[i][j];
                }
            }
        }
        return null;
    }

    private ImageView getBackImageView(CardView cardView) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (cardViews[i][j] == cardView) {
                    return backImageViews[i][j];
                }
            }
        }
        return null;
    }

    private void checkGameCompletion() {
        int matchedPairs = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (cardViews[i][j].getTag() != null && cardViews[i][j].getTag().equals("matched")) {
                    matchedPairs++;
                }
            }
        }

        if (matchedPairs == ROWS * COLS) {
            android.widget.Toast.makeText(this, "Congratulations! You found all pairs!", android.widget.Toast.LENGTH_SHORT).show();
        }
    }

    public void retryGame(View view) {
        setupGame();
    }

    public void quitGame(View view) {
        finish();
    }
}