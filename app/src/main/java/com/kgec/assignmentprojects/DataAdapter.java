package com.kgec.assignmentprojects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private Context context;
    private List<Data> list;

    public DataAdapter(Context context, List<Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new DataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Data data=list.get(position);
        holder.name.setText(data.getStudentName());
        holder.marks.setText(data.getMarks());
        holder.id.setText(data.getId());
        if (data.getStatus()==null){
            holder.linearLayout.setWeightSum(3);
            holder.status_lay.setVisibility(View.GONE);
        }
        else{
            holder.linearLayout.setWeightSum(4);
            holder.status_lay.setVisibility(View.VISIBLE);
            holder.status.setText(data.getStatus());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView name,id,marks,status;
        LinearLayout linearLayout,status_lay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            marks=itemView.findViewById(R.id.marks);
            name=itemView.findViewById(R.id.name);
            id=itemView.findViewById(R.id.id);
            status=itemView.findViewById(R.id.status);
            linearLayout=itemView.findViewById(R.id.linearlayout);
            status_lay=itemView.findViewById(R.id.linearlayout_status);

        }
    }
}
