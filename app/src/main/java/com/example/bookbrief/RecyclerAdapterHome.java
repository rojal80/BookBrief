package com.example.bookbrief;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterHome extends RecyclerView.Adapter<RecyclerAdapterHome.ViewHolder> implements Filterable {
    FragmentHome context;

    ArrayList<ContentModel> arrDetails;
    ArrayList<ContentModel>backup;


    RecyclerAdapterHome(FragmentHome context, ArrayList<ContentModel>arrDetails){
        this.context=context;
        this.arrDetails=arrDetails;
        backup=new ArrayList<>(arrDetails);
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context.getContext()).inflate(R.layout.home_content,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.txt1.setText(arrDetails.get(position).getTitle());


        holder.cardHome.setOnClickListener(view -> {
            Intent intent=new Intent(view.getContext(),HomeDescription.class);

            intent .putExtra("Title",arrDetails.get(position).getTitle());
            intent .putExtra("Description",arrDetails.get(position).getDescription());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            view.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrDetails.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<ContentModel>filtereddata=new ArrayList<>();
            if(keyword.toString().isEmpty()){
                filtereddata.addAll(backup);
            }
            else{
                for(ContentModel obj:backup){
                    if(obj.getTitle().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filtereddata.add(obj);
                }
            }
            FilterResults results=new FilterResults();
            results.values=filtereddata;
            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            arrDetails.clear();
            arrDetails.addAll((ArrayList<ContentModel>)results.values);
            notifyDataSetChanged();

        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt1,txt2;

        CardView cardHome;
        public ViewHolder(View itemView){

            super(itemView);
            txt1=itemView.findViewById(R.id.txt1);
            txt2=itemView.findViewById(R.id.txt2);

            cardHome=itemView.findViewById(R.id.cardHome);
        }

    }
}
