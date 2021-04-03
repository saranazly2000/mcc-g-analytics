package com.example.assignment2mcc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder>  {
    private List<Model> foodList;
    //private Context mcontext;
    private RecyclerViewClickListener listener;


    public myAdapter(List<Model> foodList, RecyclerViewClickListener listener) {
        this.foodList = foodList;
        this.listener = listener;
    }

    public myAdapter(List<Model> foodList) {
        this.foodList = foodList;
       // this.mcontext =  context;    OnCompleteListener<QuerySnapshot> context
    }



    @NonNull
    @Override
    public myAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model food =foodList.get(position);
        holder.name.setText(food.getName());
    }

    @Override
    public int getItemCount() {
            return foodList.size();
    }
    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name;
        public ConstraintLayout productItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.foodtv);
            productItem = itemView.findViewById(R.id.product_item);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View v ,int position);
    }
}
