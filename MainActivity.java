package com.example.bhathiyatk.puzzle;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int COLUMNS = 3;
    private static final int DIMENTIONS = COLUMNS * COLUMNS;

    private static String[] tileList;

    private static GestureDetectGridView mGridView;

    private static int mColumnWidth, mColumnHeight;

    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String LEFT = "left";
    public static final String RIGHT = "right";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        scramble();
        setDimentions();
    }

    private void setDimentions() {
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHeight = mGridView.getMeasuredHeight();

                int statusbarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight - statusbarHeight;

                mColumnWidth = displayWidth / COLUMNS;
                mColumnHeight = requiredHeight / COLUMNS;

                display(getApplicationContext());
            }
        });
    }

    private int getStatusBarHeight(Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0){
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    private static void display(Context context) {
        ArrayList<Button> buttons = new ArrayList<>();
        Button button;

        for (int i = 0; i < tileList.length; i++){
            button = new Button(context);

            if (tileList[i].equals("0"))
                button.setBackgroundResource(R.drawable.row_1_col_1);
            else if (tileList[i].equals("1"))
                button.setBackgroundResource(R.drawable.row_1_col_2);
            else if (tileList[i].equals("2"))
                button.setBackgroundResource(R.drawable.row_1_col_3);
            else if (tileList[i].equals("3"))
                button.setBackgroundResource(R.drawable.row_2_col_1);
            else if (tileList[i].equals("4"))
                button.setBackgroundResource(R.drawable.row_2_col_2);
            else if (tileList[i].equals("5"))
                button.setBackgroundResource(R.drawable.row_2_col_3);
            else if (tileList[i].equals("6"))
                button.setBackgroundResource(R.drawable.row_3_col_1);
            else if (tileList[i].equals("7"))
                button.setBackgroundResource(R.drawable.row_3_col_2);
            else if (tileList[i].equals("8"))
                button.setBackgroundResource(R.drawable.row_3_col_3);

            buttons.add(button);
        }

        mGridView.setAdapter(new CustomAdapter(buttons, mColumnWidth, mColumnHeight));
    }

    private void scramble() {
        int index;
        String temp;
        Random random = new Random();

        for (int i = tileList.length - 1; i > 0; i--){
            index = random.nextInt(i + 1);
            temp = tileList[index];
            tileList[index] = tileList[i];
            tileList[i] = temp;
        }
    }

    private void init() {
        mGridView = (GestureDetectGridView)findViewById(R.id.grid);
        mGridView.setNumColumns(COLUMNS);

        tileList = new String[DIMENTIONS];
        for (int i = 0; i < DIMENTIONS; i++){
            tileList[i] = String.valueOf(i);
        }
    }

     private static void swap(Context context, int position, int swap){
         String newPosition = tileList[position + swap];
         tileList[position + swap] = tileList[position];
         tileList[position] = newPosition;
         display(context);

         if (isSolved()){
             Toast.makeText(context, "YOU WIN! Congratulations!", Toast.LENGTH_SHORT).show();
         }
     }

    private static boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < tileList.length; i++){
            if (tileList[i].equals(String.valueOf(i))){
                solved = true;
            }else{
                solved = false;
                break;
            }
        }

        return solved;
    }

    public static void moveTiles(Context context, String direction, int position){
        //upper-left-corner-tile
         if (position == 0){
             if (direction.equals(RIGHT)){
                swap(context, position, 1);
             }else if(direction.equals(DOWN)){
                swap(context, position, COLUMNS);
             }else {
                 Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show();
             }
         //upper-center-tile
         }else if (position > 0 && position < COLUMNS - 1){
             if (direction.equals(LEFT)){
                 swap(context, position, -1);
             }else if (direction.equals(DOWN)){
                 swap(context, position, COLUMNS);
             }else if (direction.equals(RIGHT)){
                 swap(context, position, 1);
             }else {
                 Toast.makeText(context, "invalid Move", Toast.LENGTH_SHORT).show();
             }
         //upper-right-corner-tile
         }else if (position == COLUMNS - 1){
             if (direction.equals(LEFT)){
                 swap(context, position, -1);
             }else if (direction.equals(DOWN)){
                 swap(context, position, COLUMNS);
             }else {
                 Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show();
             }
         //left-side-tile
         }else if(position > COLUMNS - 1 && position < DIMENTIONS - COLUMNS && position % COLUMNS == 0) {
             if (direction.equals(UP)) {
                 swap(context, position, -COLUMNS);
             } else if (direction.equals(RIGHT)) {
                 swap(context, position, 1);
             } else if (direction.equals(DOWN)) {
                 swap(context, position, COLUMNS);
             } else {
                 Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show();
             }
         //Right-side and bottom-right-corner-tiles
         }else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1){
             if (direction.equals(UP)){
                 swap(context, position, -COLUMNS);
             }else if (direction.equals(LEFT)){
                 swap(context, position, -1);
             }else if (direction.equals(DOWN)){
                 //right-corner-tile
                 if (position <= DIMENTIONS - COLUMNS - 1){
                     swap(context, position, COLUMNS);
                 }else {
                     Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show();
                 }
             }else {
                 Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show();
             }
         //Bottom-left-corner-tile
         }else if (position == DIMENTIONS - COLUMNS){
             if (direction.equals(UP)){
                 swap(context, position, -COLUMNS);
             }else if (direction.equals(RIGHT)){
                 swap(context, position, 1);
             }else {
                 Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show();
             }
         //Bottom-center-tile
         }else if (position < DIMENTIONS - 1 && position > DIMENTIONS - COLUMNS){
             if (direction.equals(UP)){
                 swap(context, position, -COLUMNS);
             }else if (direction.equals(RIGHT)){
                 swap(context, position, 1);
             }else if (direction.equals(LEFT)){
                 swap(context, position, -1);
             }else {
                 Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show();
             }
         //center-tile
         }else{
             if (direction.equals(UP)){
                 swap(context, position, -COLUMNS);
             }else if (direction.equals(RIGHT)){
                 swap(context, position, 1);
             }else if (direction.equals(LEFT)){
                 swap(context, position, -1);
             }else {
                 swap(context, position, COLUMNS);
             }
         }
     }
}