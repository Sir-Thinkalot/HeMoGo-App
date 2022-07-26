package com.biomedical.hemogo.Interfaces;

import androidx.cardview.widget.CardView;

import com.biomedical.hemogo.Models.Patient;

public interface PatientCardClickListener {
    void onClick(Patient patient);
    void onLongClick(Patient patient, CardView cardView);
}
