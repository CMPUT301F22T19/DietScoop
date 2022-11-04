package com.example.dietscoop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;

/**
 * This class acts as a container for the IngredientInStorage Objects retrieved from Database.
 */
public class IngredientStorageAdapter extends RecyclerView.Adapter<IngredientStorageAdapter.ViewHolder> {

    private Context context;
    private ArrayList<IngredientInStorage> dataList;
    private RecyclerItemClickListener listener;

    public IngredientStorageAdapter(Context context, ArrayList<IngredientInStorage> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public IngredientStorageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_ingredient_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientStorageAdapter.ViewHolder holder, int position) {
        if (dataList != null && dataList.size() > 0) {
            IngredientInStorage ingredient = dataList.get(position);
            holder.unitTV.setText(ingredient.getMeasurementUnit());
            holder.nameTV.setText(ingredient.getDescription());
            holder.countTV.setText(String.valueOf(ingredient.getAmount()));
            holder.dateTV.setText(ingredient.getFormattedBestBefore());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setItemClickListener(RecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTV, countTV, unitTV, dateTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.description_text);
            countTV = itemView.findViewById(R.id.count_text);
            unitTV = itemView.findViewById(R.id.unit_text);
            dateTV = itemView.findViewById(R.id.Best_Before_Text);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            if(listener != null){
                listener.onClick(view, getAdapterPosition());
            }
        }
    }

    public void addIngredientStorage(IngredientInStorage ingredient) {
        dataList.add(ingredient);
    }
}
