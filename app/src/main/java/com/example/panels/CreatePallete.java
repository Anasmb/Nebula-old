package com.example.panels;

import androidx.fragment.app.FragmentActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.panels.Model.Pallete;
import com.example.panels.SharedPref.SharedPref;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.skydoves.colorpickerview.ColorPickerView;

import java.util.ArrayList;
import java.util.UUID;

public class CreatePallete extends FragmentActivity {

    //FIXME a bug related to finish button and editText
    //TODO user can add multiple pallete but not selecting all the created color

    private EditText palleteEditText;
    private MaterialButton addBtn;
    private ImageView backImage;


    androidx.gridlayout.widget.GridLayout gridLayout;
    boolean isAnyColorSelected=false;
    int PalleteColors [] =new int[]{0,0,0,0,0,0};

    int PalleteDefaultcolor=Color.parseColor("#212121");
    int gridIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pallete);

        gridLayout=findViewById(R.id.gridLayout);
        palleteEditText = findViewById(R.id.namePallete);
        addBtn = findViewById(R.id.addPalleteBtn);
        backImage = findViewById(R.id.back_img_button);

        backImage.setColorFilter(getResources().getColor(R.color.back_button_color));
        backImage.setOnClickListener(backImageButtonListener);
        addBtn.setBackgroundColor(getResources().getColor(R.color.gray));
        addBtn.setClickable(false);

        palleteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                    if(checkPalleteName(charSequence) && isAnyColorSelected){
                        addBtn.setBackgroundColor(getResources().getColor(R.color.button_color));
                        addBtn.setClickable(true);
                    }
                    else
                    {
                        addBtn.setBackgroundColor(getResources().getColor(R.color.gray));
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

                if(isAnyColorSelected && checkPalleteName(palleteEditText.getText()))
                    Save();
                else
                    Toast.makeText(CreatePallete.this,"Please Select Any color",Toast.LENGTH_SHORT).show();

            }
        });


        addItemInGrid();
    }

    View.OnClickListener backImageButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };



    private boolean checkPalleteName(CharSequence charSequence){
        if(charSequence.length() > 0 && charSequence.length()< 19)
            return  true;

            return false;
    }

    public  void  addItemInGrid() {

        final View palleteCard = getLayoutInflater().inflate(R.layout.pallete_color_card,gridLayout, false);
       View newPalleteCard = getLayoutInflater().inflate(R.layout.add_new_pallete,gridLayout, false);

        palleteCard.setTag(gridIndex);

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
            gridLayout.addView(palleteCard);
            gridLayout.addView(newPalleteCard);

        }
        else if(gridIndex==4)
        {
            gridLayout.removeViewAt(gridIndex);
            gridLayout.addView(palleteCard);

            View view3= getLayoutInflater().inflate(R.layout.pallete_color_card,gridLayout, false);
            view3.setTag(gridIndex+1);
            view3.setOnClickListener(onClickListener);
            gridLayout.addView(view3);
        }
        else
        {
            gridLayout.removeViewAt(gridIndex);
            gridLayout.addView(palleteCard);
            gridLayout.addView(newPalleteCard);
        }



        if(gridIndex!=5)
        {
            newPalleteCard.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v)
                {
                    addItemInGrid();
                }
            });

        }


        palleteCard.setOnClickListener(onClickListener);
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
            final ColorPickerView colorPickerView = dialog.findViewById(R.id.colorPickerView);
            MaterialButton btnDone = dialog.findViewById(R.id.btnDone) ;
            btnDone.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v) {

                    int color = colorPickerView.getColorEnvelope().getColor();
                    // String hexColor = colorPickerView.getColorEnvelope().getHexCode();

                    PalleteColors[selectedBoxPosition] = color;
                    cardView.setCardBackgroundColor(color);
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
