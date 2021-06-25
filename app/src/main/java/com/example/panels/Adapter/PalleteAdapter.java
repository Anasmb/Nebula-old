package com.example.panels.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.panels.Model.Pallete;
import com.example.panels.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;


public class PalleteAdapter extends RecyclerView.Adapter<PalleteAdapter.ViewHolder> {
    private ArrayList<Pallete> listData;
    private OnNoteListener mOnNoteListener;
    Context context;
    public PalleteAdapter(ArrayList<Pallete> listData, Context context , OnNoteListener onNoteListener) {
        this.listData = listData;
        this.context=context;
        this.mOnNoteListener = onNoteListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem,viewGroup,false);
        return new ViewHolder(view , mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {

        Pallete obje=listData.get(i);

        int num=getNumColors(obje);

        MaterialCardView[] cardViews=new MaterialCardView[]{holder.cardColor1,holder.cardColor2,holder.cardColor3,holder.cardColor4,holder.cardColor5,holder.cardColor6};
        LinearLayout [] linearLayouts=new LinearLayout[]{holder.layout1,holder.layout2,holder.layout3};

        int [] colors=new int[]{obje.getColor1(),obje.getColor2(),obje.getColor3(),obje.getColor4(),obje.getColor5(),obje.getColor6()};

        for(MaterialCardView textView:cardViews)
                textView.setVisibility(View.GONE);

        for(LinearLayout linearLayout:linearLayouts)
            linearLayout.setVisibility(View.GONE);


        setColors(num,cardViews,colors,linearLayouts);
        holder.txtPalleteName.setText(obje.getPalleteName());


    }

    private void setColors (int num , MaterialCardView[] cardViews , int[] colors , LinearLayout[] linearLayouts)
    {

        if(num==1)
        {
            cardViews[0].setVisibility(View.VISIBLE);
            cardViews[0].setCardForegroundColor(ColorStateList.valueOf(colors[0]));
            linearLayouts[0].setVisibility(View.VISIBLE);
        }
        if(num==2)
        {
            cardViews[0].setVisibility(View.VISIBLE);
            cardViews[0].setCardForegroundColor(ColorStateList.valueOf(colors[0]));

            cardViews[1].setVisibility(View.VISIBLE);
            cardViews[1].setCardForegroundColor(ColorStateList.valueOf(colors[1]));
            linearLayouts[0].setVisibility(View.VISIBLE);
            linearLayouts[1].setVisibility(View.VISIBLE);

        }
        if(num==3)
        {
            cardViews[0].setVisibility(View.VISIBLE);
            cardViews[0].setCardForegroundColor(ColorStateList.valueOf(colors[0]));

            cardViews[1].setVisibility(View.VISIBLE);
            cardViews[1].setCardForegroundColor(ColorStateList.valueOf(colors[1]));

            cardViews[2].setVisibility(View.VISIBLE);
            cardViews[2].setCardForegroundColor(ColorStateList.valueOf(colors[2]));

            linearLayouts[0].setVisibility(View.VISIBLE);
            linearLayouts[1].setVisibility(View.VISIBLE);
            linearLayouts[2].setVisibility(View.VISIBLE);
        }
        if(num==4)
        {
            cardViews[0].setVisibility(View.VISIBLE);
            cardViews[0].setCardForegroundColor(ColorStateList.valueOf(colors[0]));

            cardViews[1].setVisibility(View.VISIBLE);
            cardViews[1].setCardForegroundColor(ColorStateList.valueOf(colors[1]));

            cardViews[3].setVisibility(View.VISIBLE);
            cardViews[3].setCardForegroundColor(ColorStateList.valueOf(colors[2]));

            cardViews[4].setVisibility(View.VISIBLE);
            cardViews[4].setBackgroundColor(colors[3]);


            linearLayouts[0].setVisibility(View.VISIBLE);
            linearLayouts[1].setVisibility(View.VISIBLE);


        }
        if(num==5)
        {


            cardViews[0].setVisibility(View.VISIBLE);
            cardViews[0].setCardForegroundColor(ColorStateList.valueOf(colors[0]));

            cardViews[1].setVisibility(View.VISIBLE);
            cardViews[1].setCardForegroundColor(ColorStateList.valueOf(colors[1]));

            cardViews[2].setVisibility(View.VISIBLE);
            cardViews[2].setCardForegroundColor(ColorStateList.valueOf(colors[2]));

            cardViews[3].setVisibility(View.VISIBLE);
            cardViews[3].setCardForegroundColor(ColorStateList.valueOf(colors[3]));

            cardViews[5].setVisibility(View.VISIBLE);
            cardViews[5].setCardForegroundColor(ColorStateList.valueOf(colors[4]));


            linearLayouts[0].setVisibility(View.VISIBLE);
            linearLayouts[1].setVisibility(View.VISIBLE);
            linearLayouts[2].setVisibility(View.VISIBLE);

        }
        if(num==6)
        {
            cardViews[0].setVisibility(View.VISIBLE);
            cardViews[0].setCardForegroundColor(ColorStateList.valueOf(colors[0]));

            cardViews[1].setVisibility(View.VISIBLE);
            cardViews[1].setCardForegroundColor(ColorStateList.valueOf(colors[1]));

            cardViews[2].setVisibility(View.VISIBLE);
            cardViews[2].setCardForegroundColor(ColorStateList.valueOf(colors[2]));

            cardViews[3].setVisibility(View.VISIBLE);
            cardViews[3].setCardForegroundColor(ColorStateList.valueOf(colors[3]));

            cardViews[4].setVisibility(View.VISIBLE);
            cardViews[4].setCardForegroundColor(ColorStateList.valueOf(colors[4]));

            cardViews[5].setVisibility(View.VISIBLE);
            cardViews[5].setCardForegroundColor(ColorStateList.valueOf(colors[5]));


            linearLayouts[0].setVisibility(View.VISIBLE);
            linearLayouts[1].setVisibility(View.VISIBLE);
            linearLayouts[2].setVisibility(View.VISIBLE);

        }


    }

    private int getNumColors (Pallete obje)
    {
        if(obje.getColor1()==0)
            return 0;
        if(obje.getColor2()==0)
            return 1;
        if(obje.getColor3()==0)
            return 2;
        if(obje.getColor4()==0)
            return 3;
        if(obje.getColor5()==0)
            return 4;
        if(obje.getColor6()==0)
            return 5;

        return 6;
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnNoteListener onNoteListener;
        private TextView txtPalleteName;

        private  MaterialCardView cardColor1;
        private  MaterialCardView cardColor2;
        private  MaterialCardView cardColor3;
        private  MaterialCardView cardColor4;
        private  MaterialCardView cardColor5;
        private  MaterialCardView cardColor6;

        private LinearLayout layout1;
        private LinearLayout layout2;
        private LinearLayout layout3;


        public ViewHolder(View itemView ,OnNoteListener onNoteListener) {
            super(itemView);

            cardColor1=itemView.findViewById(R.id.cardColor1);
            cardColor2=itemView.findViewById(R.id.cardColor2);
            cardColor3=itemView.findViewById(R.id.cardColor3);
            cardColor4=itemView.findViewById(R.id.cardColor4);
            cardColor5=itemView.findViewById(R.id.cardColor5);
            cardColor6=itemView.findViewById(R.id.cardColor6);

            layout1=itemView.findViewById(R.id.layout1);
            layout2=itemView.findViewById(R.id.layout2);
            layout3=itemView.findViewById(R.id.layout3);


            txtPalleteName=itemView.findViewById(R.id.txtPanelName);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.OnNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void OnNoteClick(int position);
    }
}
