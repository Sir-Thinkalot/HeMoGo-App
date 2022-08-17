package com.biomedical.hemogo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.biomedical.hemogo.Database.Entities.Patient;
import com.biomedical.hemogo.Interfaces.PatientCardClickListener;
import com.biomedical.hemogo.R;

import java.util.List;

public class PatientListAdapter extends RecyclerView.Adapter<PatientViewHolder> {
    Context context;
    List<Patient> list;
    PatientCardClickListener listener;

    public PatientListAdapter(Context context, List<Patient> list, PatientCardClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PatientViewHolder(LayoutInflater.from(context).inflate(R.layout.viewholder_patient_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        holder.patientName.setText(list.get(position).getPatientName());
        holder.patientName.setSelected(true);

        holder.machineNumber.setText(list.get(position).getMachineNumber());

        if(list.get(position).getConnection_status()){
            holder.connStatus.setText("Connected");
        }
        else{
            holder.connStatus.setText("Disconnected");
        }

        holder.patient_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.patient_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()),holder.patient_container);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class PatientViewHolder extends RecyclerView.ViewHolder {

    CardView patient_container;
    TextView patientName,machineNumber,connStatus;

    public PatientViewHolder(@NonNull View itemView) {
        super(itemView);
        patient_container = itemView.findViewById(R.id.patientContainer);
        patientName = itemView.findViewById(R.id.patientName);
        machineNumber = itemView.findViewById(R.id.machineNumber);
        connStatus = itemView.findViewById(R.id.connStatus);
    }

}