package com.example.bookbrief;



import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.dialogplus.DialogPlus;

import java.util.ArrayList;

public class RecyclerAdapterLibrary extends RecyclerView.Adapter<RecyclerAdapterLibrary.ViewHolder> implements Filterable {
    FragmentLibrary context;

    ArrayList<ContentModel> arrDetails;
    ArrayList<ContentModel>backup;
    private Fragment fragment;


    public RecyclerAdapterLibrary(FragmentLibrary context, ArrayList<ContentModel> arrDetails){
        this.context=context;

        this.arrDetails=arrDetails;
        backup=new ArrayList<>(arrDetails);
        if (context == null) {
            Log.e("Adapter", "Context is null!");
        }
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context.getContext()).inflate(R.layout.librarycontent,parent,false);
        if (view.getContext() == null) {
            Log.e("onCreateViewHolder", "Context in onCreateViewHolder is null!");
        }
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

        holder.editpost.setOnClickListener(view -> {
            if(holder.editpost!=null) {
                final DialogPlus dialogplus = DialogPlus.newDialog(holder.txt1.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1200).create();

                EditText editTitle = (EditText) dialogplus.findViewById(R.id.editTitle);
                EditText editDescription = (EditText) dialogplus.findViewById(R.id.editDescription);
                ImageView editpost = (ImageView) dialogplus.findViewById(R.id.editpost);
                // Get the position of the clicked item
                int itemPosition = holder.getAdapterPosition();
                ContentModel contentModel = arrDetails.get(itemPosition);

                // Populate the EditText fields with existing data
                editTitle.setText(contentModel.getTitle());
                editDescription.setText(contentModel.getDescription());

                // Handle the editpost ImageView click here to save the edited data
//            editpost.setOnClickListener(view1 -> {
//
//            });

                dialogplus.show();
            }
        });

        holder.deletepost.setOnClickListener(view -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(holder.txt1.getContext());
            builder.setTitle("Are you sure you want to delete?");
            builder.setMessage("Deleted data can't be undo.");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(holder.txt1.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();


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
        ImageView editpost,deletepost,saveimage;
        public ViewHolder(View itemView){

            super(itemView);
            txt1=itemView.findViewById(R.id.txt1);
            txt2=itemView.findViewById(R.id.txt2);
            cardHome=itemView.findViewById(R.id.cardHome);
            editpost=itemView.findViewById(R.id.editpost);
            deletepost=itemView.findViewById(R.id.deletepost);
            saveimage=itemView.findViewById(R.id.saveImage);
        }

    }
}
