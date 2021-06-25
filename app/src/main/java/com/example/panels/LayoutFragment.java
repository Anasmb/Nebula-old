package com.example.panels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.google.android.material.slider.Slider;

import java.net.Socket;
import java.util.ArrayList;

public class LayoutFragment extends Fragment{

    View view;
    RelativeLayout relativeLayout;

    private Thread Thread1 = null;
    private Socket socket;
    private String IPADDRESS ;
    private final int port  = 3636;

    private ArrayList<String> panelsLayout; //list messages from server
    private ArrayList<Panel> panels;
    private ImageView currentSelectedPanel;
    private ImageView previousSelectedPanel;
    private Slider brightness;
    private ToggleButton selectButton;

    private ArrayList<Panel> horizontalPanels;

    float xDown = 0;
    float yDown = 0 ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_layout,container,false);

        panels = new ArrayList<Panel>();
        panelsLayout = new ArrayList<String>();
        relativeLayout = view.findViewById(R.id.panelsLayout);
        relativeLayout.setOnTouchListener(relativeListener);
        brightness = view.findViewById(R.id.brightnessSlider);
        selectButton = view.findViewById(R.id.selectAllToggle);
        selectButton.setOnClickListener(selectAllPanels);
        
        //right part
        panelsLayout.add("100 201 101");
        panelsLayout.add("101 nul 102");
        panelsLayout.add("102 103 nul");
        panelsLayout.add("103 nul 104");
        panelsLayout.add("104 105 nul");
        panelsLayout.add("105 nul nul");
        panelsLayout.add("106 nul nul");

        //left part
        panelsLayout.add("201 202 nul");
        panelsLayout.add("202 nul 203");
        panelsLayout.add("203 204 nul");
        panelsLayout.add("204 nul 206");
        panelsLayout.add("206 207 nul");
        panelsLayout.add("207 nul nul");



        messageAnalyzer();

        // IPESP32 = PrefConfig.loadIP(this);
        //IPADDRESS = "192.168.100.88"; // IP ADDRESS
        //Thread1 = new Thread(new EditLayout.Thread1());
        // Thread1.start();



        return  view;
    }

    View.OnTouchListener relativeListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

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

    public void messageAnalyzer(){

    //    for(int i = 0 ; i < panelsLayout.size() ; i++){
          String message = panelsLayout.get(0);
          int parent = message.substring(0,3).equals("nul") ? 0 : Integer.parseInt(message.substring(0,3));//parent address
          int leftChild = message.substring(4,7).equals("nul") ? 0 : Integer.parseInt(message.substring(4,7)); //left child address
          int rightChild = message.substring(8,11).equals("nul") ? 0 : Integer.parseInt(message.substring(8,11)); //right child address
           // Log.d("Debug" , "Message " + i + " Contain, Parent ID = " + parent + " , Left Child ID = " + leftChild + " , Right Child ID = " + rightChild );
          displayPanels(parent , leftChild , rightChild);

      //  }
    }

    public void addLayout(String message){
      // panelsLayout[panelsLayoutIndex] = message.substring(7 , message.length()); //This remove the layout word
      // panelsLayoutIndex++;
    }

    public void refreshLayout(View view){
        //new Thread(new EditLayout.Thread3("refresh")).start();
    }

    private void dataServerChecker(String message){

        if(message.startsWith("layout")){
           addLayout(message);
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
        for(int i = 0 ; i < panelsLayout.size() ; i++){
            message = panelsLayout.get(i);
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
           if((panelInfo.getxLocation() <= x + 30 && panelInfo.getxLocation() >= x - 30) && (panelInfo.getyLocation() <= y + 30 && panelInfo.getyLocation() >= y - 30)){
              // Toast.makeText(getView().getContext(), "Panel ID = " + view.getTag(), Toast.LENGTH_SHORT ).show();

               currentSelectedPanel = (ImageView) view;
               previousSelectedPanel = (ImageView) view;
               currentSelectedPanel.setImageResource(R.drawable.ic_tri_polygon_selected_);
               previousSelectedPanel = currentSelectedPanel;
           }
        }
    }

     View.OnClickListener selectAllPanels = new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             ImageView image = null;
             if(selectButton.isChecked()){
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
     };


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


