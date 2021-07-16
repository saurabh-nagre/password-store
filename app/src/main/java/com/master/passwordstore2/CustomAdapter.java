package com.master.passwordstore2;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;



public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.holder> implements Filterable{


    List<String> localDataSet1;
    List<String> localDataSet2;
    DBHelper dbHelper;
    Context context;

    ArrayList<Pair<String,String> > data;
    ArrayList<Pair<String,String> > backupData;


    public static class holder extends RecyclerView.ViewHolder{

        TextView name;
        TextView pass;
        ImageButton delete;
        ImageButton edit;
        public holder(View view){
            super(view);

            this.delete = view.findViewById(R.id.delete_button);
            this.edit = view.findViewById(R.id.edit_button);
            this.name = view.findViewById(R.id.appnametv);
            this.pass = view.findViewById(R.id.passwordtv);
        }


    }


        public CustomAdapter(Context con,List<String> dataSet1,List<String> dataset2) {

            try {
                this.context = con;
                this.localDataSet1 = dataSet1;
                this.localDataSet2 = dataset2;
                data = new ArrayList<>();
                backupData = new ArrayList<>();
                for (int i = 0; i < localDataSet1.size(); i++) {
                    data.add(Pair.create(localDataSet1.get(i), localDataSet2.get(i)));
                }
                backupData.addAll(data);
                dbHelper = new DBHelper(context);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                for(int i=0;i<10;i++){
                    System.out.println(i);
                }
            }
        }

        // Create new views (invoked by the layout manager)
        @NonNull
        @Override
        public holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.singlerow, viewGroup, false);
            return new holder(view);
        }


    @Override
    public void onBindViewHolder(@NonNull holder viewholder, int position) {

        viewholder.name.setText(data.get(position).first);
        viewholder.pass.setText(data.get(position).second);

        viewholder.delete.setOnClickListener(v->{


            dbHelper.delete(data.get(position).first);
            data.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,1);
        });

        viewholder.edit.setOnClickListener(v->{

                Intent intent = new Intent(context,UpdatePassword.class);
                intent.putExtra("appName",data.get(position).first);
                intent.putExtra("Password",data.get(position).second);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

        });

    }


    // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public Filter getFilter() {
            return  filter;
        }

        Filter filter = new Filter() {


            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                ArrayList<Pair<String,String> > filterdata = new ArrayList<>();

                if(constraint.toString().isEmpty()){
                    filterdata.addAll(backupData);
                }
                else{
                    for(Pair<String,String> i:data){
                        if(i.first.toLowerCase().contains(constraint.toString().toLowerCase()) || i.second.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            System.out.println(i.first + i.second);
                            filterdata.add(i);
                        }

                    }
                }

            FilterResults results = new FilterResults();
                results.values = filterdata;

                return  results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data.clear();
                data.addAll((ArrayList<Pair<String,String> >)results.values);
                notifyDataSetChanged();
            }

        };
}




