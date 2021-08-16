package com.example.panels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

//THE MOST TIME CONSUMING AND BRAINSTORMING CLASS IN THE WHOLE APPLICATION >:(
public class LayoutFragment extends Fragment{

    View view;
    RelativeLayout relativeLayout;

    private final String LOG_TAG =  "LayoutFragment";
    private FragmentLayoutListener listener;

    private ArrayList<Panel> panels ;
    private ImageView currentSelectedPanel;
    private ImageView previousSelectedPanel;
    private ImageView refreshButton;
    private Animation refreshAnimation;
    private MaterialButton clearButton;
    private MaterialButton selectAllButton;
    private boolean selectAllButtonToggle = false;
    private boolean startTimer = true;
    private Slider brightnessSlider;
    private ArrayList<Panel> horizontalPanels;


    float xDown = 0;
    float yDown = 0 ;

    public interface FragmentLayoutListener{ //USE THIS TO SEND DATA TO MainActivity
        void onInputLayoutSent(CharSequence input);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setStatusBarColor();

        Log.d(LOG_TAG, "Inside OnviewCreated");



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_layout,container,false);
        

        panels = new ArrayList<>(); ;
        relativeLayout = view.findViewById(R.id.panelsLayout);
        relativeLayout.setOnTouchListener(relativeListener);
        brightnessSlider = view.findViewById(R.id.layoutBrightnessSlider);
        brightnessSlider.addOnSliderTouchListener(brightnessSliderTouchListener);
        refreshButton = view.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(refreshClickListener);
        selectAllButton = view.findViewById(R.id.selectAllButton);
        clearButton = view.findViewById(R.id.clearButton);
        selectAllButton.setOnClickListener(selectAllClickListener);
        clearButton.setOnClickListener(clearButtonClickListener);

        brightnessSlider.setValue(MainActivity.brightnessValue);

               messageAnalyzer(); //FIRST LAYOUT MESSAGE MUST BE THE MAIN PANEL

        return  view;
    }

    public void onAttach(@NonNull Context context) { //THIS IS USED WHEN IF THE ACTIVITY ATTACH THE INTERFACE
        super.onAttach(context);
        if(context instanceof LayoutFragment.FragmentLayoutListener){
            listener = (LayoutFragment.FragmentLayoutListener) context;
        }
        else {
            throw new RuntimeException(context.toString() + " Must implement FragmentLayoutListener");
        }
    }

    View.OnTouchListener relativeListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) { //determine which panel did the user clicked

        switch (motionEvent.getActionMasked()){

            case MotionEvent.ACTION_DOWN:
                Log.d("Debug" , "ACTION DOWN");
                xDown = motionEvent.getX();
                yDown = motionEvent.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d("Debug" , "ACTION MOVE");
                float moveX,moveY;
                moveX = motionEvent.getX();
                moveY = motionEvent.getY();

                float distX = moveX - xDown;
                float distY = moveY - yDown;

                relativeLayout.setX(relativeLayout.getX() + distX);
                relativeLayout.setY(relativeLayout.getY() + distY);

                break;
        }

            int x = (int) motionEvent.getX() - 80; //check clicked panel
            int y = (int) motionEvent.getY() -120;
            checkClickedPanel(x,y);

            return true;
        }
    };


    private void setStatusBarColor(){
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.background_color));
    }

    public void messageAnalyzer(){
        if (MainActivity.panelsLayout.size() > 0) {
            //    for(int i = 0 ; i < panelsLayout.size() ; i++){
            Log.d(LOG_TAG, "panelsLayout size = " + MainActivity.panelsLayout.size());
            String message = MainActivity.panelsLayout.get(0);
            int parent = message.substring(0, 3).equals("nul") ? 0 : Integer.parseInt(message.substring(0, 3));//parent address
            int leftChild = message.substring(4, 7).equals("nul") ? 0 : Integer.parseInt(message.substring(4, 7)); //left child address
            int rightChild = message.substring(8, 11).equals("nul") ? 0 : Integer.parseInt(message.substring(8, 11)); //right child address
            // Log.d("Debug" , "Message " + i + " Contain, Parent ID = " + parent + " , Left Child ID = " + leftChild + " , Right Child ID = " + rightChild );
            displayPanels(parent, leftChild, rightChild);

      //  }

        }
    }


    public void onMainReceive(String message){ //RECEIVE DATA FROM MainActivity

           Log.d(LOG_TAG, "Received From MainActivity: " + message);
           if (startTimer){
         //      time(startTimer);
               startTimer = false;
        }

    }

    private void time(boolean start) {
        if (start){
            new CountDownTimer(5000,1000){
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    Log.d(LOG_TAG, "Timer Finished");
                }
            }.start();
        }
    }
 /*  View.OnClickListener panelImageClick = new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           currentSelectedPanel = (ImageView) view;
           currentSelectedPanel.setImageResource(R.drawable.ic_tri_polygon_selected_);
           Toast.makeText(getView().getContext(),"ID = " + currentSelectedPanel.getTag() , Toast.LENGTH_SHORT).show();
       }
   };*/

    public void displayPanels(int parent , int leftChild , int rightChild){

           if(parent == 100) { //Enter if Main Panel
               int id = parent;
               float x = 500;
               float y = 500;
               int parentImageRotation = 0;
               panels.add(new Panel(id, x, y, 0 , parentImageRotation ));
               ImageView parentImage = new ImageView(view.getContext());
               parentImage.setImageResource(R.drawable.ic_tri_polygon);
               parentImage.setTag(id);
              // parentImage.setOnClickListener(panelImageClick);
               RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(150, 150);
               params.topMargin = (int) y;
               params.leftMargin = (int) x;
               parentImage.setRotation(parentImageRotation);
               relativeLayout.addView(parentImage, params);

               if(leftChild != 0){
                   int idChild = leftChild;
                  float xChild = x - 57;
                  float yChild = y - 33;
                  int childRealRotation = 300;
                  int childImageRotation = parentImageRotation == 0 ? 60 : 0;
                  panels.add(new Panel(idChild , xChild , yChild , childRealRotation , childImageRotation));
                  ImageView ChildImage = new ImageView(view.getContext());
                   ChildImage.setImageResource(R.drawable.ic_tri_polygon);
                   ChildImage.setTag(idChild);
                   //ChildImage.setOnClickListener(panelImageClick);
                   RelativeLayout.LayoutParams childParams = new RelativeLayout.LayoutParams(150, 150);
                   childParams.topMargin = (int) yChild;
                   childParams.leftMargin = (int) xChild;
                   ChildImage.setRotation(childImageRotation);
                   relativeLayout.addView(ChildImage, childParams);
                   Log.d("Debug" , "ID = " + idChild + " , X Location = " + xChild + " , Y Location = " + yChild + " , childRealRotation = " + childRealRotation);
                   Log.d("Debug" , "____________________________________________________________________");
                   addLeftChildToParent(idChild);
                   addRightChildToParent(idChild);
               }
               if(rightChild != 0){
                   int idChild = rightChild;
                   float xChild = x + 95; //original +100
                   float yChild = y - 33;  //original -33
                   int childRealRotation = 60;
                   int childImageRotation = parentImageRotation == 0 ? 60 : 0;
                   panels.add(new Panel(idChild , xChild , yChild , childRealRotation , childImageRotation));
                   ImageView ChildImage = new ImageView(view.getContext());
                   ChildImage.setImageResource(R.drawable.ic_tri_polygon);
                   ChildImage.setTag(idChild);
                 //  ChildImage.setOnClickListener(panelImageClick);
                   RelativeLayout.LayoutParams childParams = new RelativeLayout.LayoutParams(150, 150);
                   childParams.topMargin = (int) yChild;
                   childParams.leftMargin = (int) xChild;
                   ChildImage.setRotation(childImageRotation);
                   relativeLayout.addView(ChildImage, childParams);
                   Log.d("Debug" , "ID = " + idChild + " , X Location = " + xChild + " , Y Location = " + yChild + " , childRealRotation = " + childRealRotation);
                   Log.d("Debug" , "____________________________________________________________________");

                  addLeftChildToParent(idChild);
                   addRightChildToParent(idChild);

               }
           }
        else if(parent != 100){ //ENTER if NOT Main Panel
           //     addLeftChildToParent(parent);
           }

        Log.d("Debug" , "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
        Log.d("Debug" , "Total panels added to layout = " + panels.size());
        Log.d("Debug" , "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");

        //getCoordinate();

    }

    private void addLeftChildToParent(int parent){ //recursion
        String parentLayout = findMessageWithID(parent);
        Panel parentPanel = getPanelInfo(parent);
        Log.d("Debug" , "____________________________________________________________________");
        Log.d("Debug" , "<Method> addLeftChildToParent");

        if(parentLayout.substring(4,7).equals("nul")){ //if there is no left child
            Log.d("Debug" , parentPanel.getID() + " has no left Child to add, Returning...");
            return;
        }

        int idChild = Integer.parseInt(parentLayout.substring(4,7));
        float result[] = addLeftRule(parentPanel);
        float xChild = parentPanel.getxLocation() + result[0];
        float yChild = parentPanel.getyLocation() + result[1];
        int childImageRotation = parentPanel.getImageRotation() == 0 ? 60 : 0;
        int childRealRotation = rotationLeftRule(parentPanel);
        panels.add(new Panel(idChild , xChild , yChild , childRealRotation , childImageRotation));
        ImageView ChildImage = new ImageView(view.getContext());
        ChildImage.setImageResource(R.drawable.ic_tri_polygon);
        ChildImage.setTag(idChild);
        //ChildImage.setOnClickListener(panelImageClick);
        RelativeLayout.LayoutParams childParams = new RelativeLayout.LayoutParams(150, 150);
        childParams.topMargin = (int) yChild;
        childParams.leftMargin = (int) xChild;
        ChildImage.setRotation(childImageRotation);
        relativeLayout.addView(ChildImage, childParams);
        Log.d("Debug" , "ID = " + idChild + " , X Location = " + xChild + " , Y Location = " + yChild + " , childRealRotation = " + childRealRotation);
        Log.d("Debug" , "____________________________________________________________________");
        addLeftChildToParent(idChild);
        addRightChildToParent(idChild);
    }

    private void addRightChildToParent(int parent){ //recursion
        String parentLayout = findMessageWithID(parent);
        Panel parentPanel = getPanelInfo(parent);
        Log.d("Debug" , "____________________________________________________________________");
        Log.d("Debug" , "<Method> addRightChildToParent");

        if(parentLayout.substring(8,11).equals("nul")){ //if there is no left child
            Log.d("Debug" , parentPanel.getID() + " has no Right Child to add, Returning...");
            return;
        }

        int idChild = Integer.parseInt(parentLayout.substring(8,11));
        float result[] = addRightRule(parentPanel);
        float xChild = parentPanel.getxLocation() + result[0];
        float yChild = parentPanel.getyLocation() + result[1];
        int childImageRotation = parentPanel.getImageRotation() == 0 ? 60 : 0;
        int childRealRotation = rotationRightRule(parentPanel);
        panels.add(new Panel(idChild , xChild , yChild , childRealRotation , childImageRotation));
        ImageView ChildImage = new ImageView(view.getContext());
        ChildImage.setImageResource(R.drawable.ic_tri_polygon);
        ChildImage.setTag(idChild);
      //  ChildImage.setOnClickListener(panelImageClick);
        RelativeLayout.LayoutParams childParams = new RelativeLayout.LayoutParams(150, 150);
        childParams.topMargin = (int) yChild;
        childParams.leftMargin = (int) xChild;
        ChildImage.setRotation(childImageRotation);
        relativeLayout.addView(ChildImage, childParams);
        Log.d("Debug" , "ID = " + idChild + " , X Location = " + xChild + " , Y Location = " + yChild + " , childRealRotation = " + childRealRotation);
        Log.d("Debug" , "____________________________________________________________________");
        addLeftChildToParent(idChild);
        addRightChildToParent(idChild);
    }


    private float[] addLeftRule(Panel parentPanel){
        float xy[] = {0 , 0}; //Index 0 = X , 1 = Y

        Log.d("Debug" , "<Method> addToTheLeft" + " , parentPanel RealRotation = " + parentPanel.getRealRotation());
        switch (parentPanel.getRealRotation()){
            case 0: //NOT R
                xy[0] = -57;  xy[1] = -33;
                Log.d("Debug" , "L CASE 0");
                break;
            case 60: // R
                xy[0] = -19;  xy[1] = -98;
                Log.d("Debug" , "L CASE 60");
                break;
            case 120: //NOT R
                xy[0] = 95;  xy[1] = -33;
                Log.d("Debug" , "L CASE 120");
                break;
            case 180: //R
                xy[0] = 57;   xy[1] = 33;
                Log.d("Debug" , "L CASE 180");
                break;
            case 240: // NOT R
                xy[0] = 19;   xy[1] = 98;
                Log.d("Debug" , "L CASE 240");
                break;
            case 300: //R
                xy[0] = -95; xy[1] = 33;
                Log.d("Debug" , "L CASE 300");
                break;
        }
        return xy;
    }

    private float[] addRightRule(Panel parentPanel){
        float xy[] = {0 , 0}; //Index 0 = X , 1 = Y
        switch (parentPanel.getRealRotation()){
            case 0: //NOT R
                xy[0] = 95; xy[1] = -33;
                Log.d("Debug" , "R CASE 0");
                break;
            case 60: // R
                xy[0] = 57; xy[1] = 33;
                Log.d("Debug" , "R CASE 60");
                break;
            case 120: //NOT R
                xy[0] = 19; xy[1] = 98;
                Log.d("Debug" , "R CASE 120");
                break;
            case 180: //R
                xy[0] = -95; xy[1] = 33;
                Log.d("Debug" , "R CASE 180");
                break;
            case 240: // NOT R
                xy[0] = -57; xy[1] = -33;
                Log.d("Debug" , "R CASE 240");
                break;
            case 300: //R
                xy[0] = -19; xy[1] = -98;
                Log.d("Debug" , "R CASE 300");
                break;
        }
        return xy;
    }

    private int rotationLeftRule(Panel previousPanel){
        if(previousPanel.getRealRotation() == 0){
            return 300;
        }
        return Math.abs(previousPanel.getRealRotation() - 60);
    }

    private int rotationRightRule(Panel previousPanel){
        if(previousPanel.getRealRotation() == 300){
            return 0;
        }
        return Math.abs(previousPanel.getRealRotation() + 60);
    }

    private String findMessageWithID(int id){
        String message = null;
        for(int i = 0 ; i < MainActivity.panelsLayout.size() ; i++){
            message = MainActivity.panelsLayout.get(i);
            if(Integer.parseInt(message.substring(0,3)) == id){
                return message;
            }
        }
        return null;
    }

    private Panel getPanelInfo(int id){
        Panel panel;
        for(int i = 0 ; i < panels.size() ; i++){
            panel = panels.get(i);
            if(panel.getID() == id){
              return panel;
            }
        }
        return null;
    }

   /* private void getCoordinate(){
        for(int i = 0 ; i < panels.size() ; i++){
            Log.d("Coordinate", "ID = " + panels.get(i).getID() + " X = " + panels.get(i).getxLocation() + " Y = " + panels.get(i).getyLocation());
        }

    }*/

    private void checkClickedPanel(int x , int y){ //TODO NEEDS FIXES
        View view = null;
        Panel panelInfo = null;
        if(previousSelectedPanel != null) {
            previousSelectedPanel.setImageResource(R.drawable.ic_tri_polygon);
        }
        for(int i = 0 ; i < panels.size() ; i++) {
            panelInfo = panels.get(i);
          view = relativeLayout.getChildAt(i);
           if((panelInfo.getxLocation() <= (x + 30) && panelInfo.getxLocation() >= (x - 30) && (panelInfo.getyLocation() <= (y + 30) && panelInfo.getyLocation() >= (y - 30)))){
              // Toast.makeText(getView().getContext(), "Panel ID = " + view.getTag(), Toast.LENGTH_SHORT ).show();

               currentSelectedPanel = (ImageView) view;
               previousSelectedPanel = (ImageView) view;
               currentSelectedPanel.setImageResource(R.drawable.ic_tri_polygon_selected_);
               previousSelectedPanel = currentSelectedPanel;
           }
        }
    }

    Slider.OnSliderTouchListener brightnessSliderTouchListener = new Slider.OnSliderTouchListener() {
        @Override
        public void onStartTrackingTouch(@NonNull @NotNull Slider slider) {

        }

        @Override
        public void onStopTrackingTouch(@NonNull @NotNull Slider slider) {
                Log.d(LOG_TAG, "Brightness slider value = " + slider.getValue());
                MainActivity.brightnessValue = (int) slider.getValue();
                listener.onInputLayoutSent("bn ");
        }
    };

    View.OnClickListener refreshClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            refreshAnimation = new RotateAnimation(0.0f,360.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            refreshAnimation.setRepeatCount(-1);
            refreshAnimation.setDuration(1000);
            refreshButton.setAnimation(refreshAnimation);
            refreshButton.startAnimation(refreshAnimation);
            messageAnalyzer();

        }
    };

    View.OnClickListener clearButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

     View.OnClickListener selectAllClickListener = new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             selectAllButtonToggle = !selectAllButtonToggle;
             if(selectAllButtonToggle){
                 selectAllButton.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.button_color)));
                 selectAllPanels(true);
             }
             else{
                  selectAllButton.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                 selectAllPanels(false);
             }
         }
     };

    private void selectAllPanels(boolean checked){
        ImageView image = null;
        if(checked){
            for(int i = 0 ; i < panels.size() ; i++){
                image = (ImageView) relativeLayout.getChildAt(i);
                image.setImageResource(R.drawable.ic_tri_polygon_selected_);
            }
        }
        else {
            for(int i = 0 ; i < panels.size() ; i++){
                image = (ImageView) relativeLayout.getChildAt(i);
                image.setImageResource(R.drawable.ic_tri_polygon);
            }
        }
    }


    private void getHorizontalPanels(){
        Panel panelInfo = null;
        float yloc;
        for(int i = 0 ; i < panels.size() ; i++){
           yloc = panels.get(i).getyLocation();
          switch ((int) yloc){

          }


        }
    }

}


