package com.example.panels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.panels.Model.Pallete;
import com.example.panels.SharedPref.SharedPref;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

import java.util.ArrayList;
import java.util.UUID;

public class CreatePallete extends FragmentActivity {

    private EditText palleteEditText;
    private MaterialButton addBtn;
    private MaterialToolbar toolbar;


    androidx.gridlayout.widget.GridLayout gridLayout;
    boolean isAnyColorSelected=false;
    int PalleteColors [] =new int[]{0,0,0,0,0,0};

    int PalleteDefaultcolor=Color.parseColor("#212121");
    int gridIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pallete);

        toolbar = findViewById(R.id.createPalletetoolbar);
        gridLayout=findViewById(R.id.gridLayout);
        palleteEditText = findViewById(R.id.namePallete);
        addBtn = findViewById(R.id.addPalleteBtn);

        toolbar.setTitle("Create Pallete");
        addBtn.setBackgroundColor(getResources().getColor(R.color.greyish));
        addBtn.setClickable(false);

        palleteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                    if((charSequence.length() > 0 && charSequence.length() < 12) && isAnyColorSelected){
                        addBtn.setBackgroundColor(getResources().getColor(R.color.button_color));
                        addBtn.setClickable(true);
                    }
                    else
                    {
                        addBtn.setBackgroundColor(getResources().getColor(R.color.greyish));
                        addBtn.setClickable(false);
                    }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                if(isAnyColorSelected)
                    Save();
                else
                    Toast.makeText(CreatePallete.this,"Please Select Any color",Toast.LENGTH_SHORT).show();




            }
        });


        addItemInGrid();
    }

    public  void  addItemInGrid()
    {

       final View view1 = getLayoutInflater().inflate(R.layout.layout_panel_add_item ,gridLayout, false);

       View view2 = getLayoutInflater().inflate(R.layout.layout_panel_add_item_2 ,gridLayout, false);



        view1.setTag(gridIndex);


        View.OnClickListener onClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick (View v) {

                 invokeColorListener(((MaterialCardView)v), (Integer) v.getTag());
            }
        };



        if(gridIndex==0)
        {
            gridLayout.removeAllViews();
            gridLayout.addView(view1);
            gridLayout.addView(view2);

        }
        else if(gridIndex==4)
        {
            gridLayout.removeViewAt(gridIndex);
            gridLayout.addView(view1);

            View view3= getLayoutInflater().inflate(R.layout.layout_panel_add_item ,gridLayout, false);
            view3.setTag(gridIndex+1);
            view3.setOnClickListener(onClickListener);
            gridLayout.addView(view3);
        }
        else
        {
            gridLayout.removeViewAt(gridIndex);
            gridLayout.addView(view1);
            gridLayout.addView(view2);
        }



        if(gridIndex!=5)
        {
            view2.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v)
                {


                    addItemInGrid();

                }
            });

        }


        view1.setOnClickListener(onClickListener);
        gridIndex++;

    }
    private void Save()
    {
        String id= UUID.randomUUID().toString();
        Pallete obj=new Pallete();
        obj.setPalleteName(palleteEditText.getText().toString());
        obj.setPalleteUID(id);
        obj.setColor1(PalleteColors[0]);
        obj.setColor2(PalleteColors[1]);
        obj.setColor3(PalleteColors[2]);
        obj.setColor4(PalleteColors[3]);
        obj.setColor5(PalleteColors[4]);
        obj.setColor6(PalleteColors[5]);


        ArrayList<Pallete> palleteList = SharedPref.getPalleteList(this);
        palleteList.add(obj);
        SharedPref.SavePalletesList(this,palleteList);


        Toast.makeText(CreatePallete.this,"Pallete Saved Successfully",Toast.LENGTH_SHORT).show();




        Reset();



    }

    private void Reset()
    {
        finish();
    }

    public  void invokeColorListener(final MaterialCardView cardView, final int selectedBoxPosition) {


            final Dialog dialog=new Dialog(this);
            dialog.setContentView(R.layout.custom_dialog);
            dialog.show();
            final ColorPickerView colorPickerView=dialog.findViewById(R.id.colorPickerView);
            MaterialButton btnDone=dialog.findViewById(R.id.btnDone) ;
            btnDone.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v) {

                    int color = colorPickerView.getColorEnvelope().getColor();
                    // String hexColor = colorPickerView.getColorEnvelope().getHexCode();

                    PalleteColors[selectedBoxPosition] = color;
                   // cardView.setCardBackgroundColor(color);
                    cardView.setCardForegroundColor(ColorStateList.valueOf(color));
                    isAnyColorSelected=true;
                    dialog.dismiss();
                }
            });

            Window window = dialog.getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);




    }
    public void openPrevious(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
