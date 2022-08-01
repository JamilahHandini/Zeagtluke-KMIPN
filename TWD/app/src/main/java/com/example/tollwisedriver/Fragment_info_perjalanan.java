package com.example.tollwisedriver;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tollwisedriver.databinding.FragmentInfoPerjalananBinding;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Fragment_info_perjalanan extends Fragment {

    FragmentInfoPerjalananBinding fragmentInfoPerjalananBinding;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private Drivers drivers;
    private  Info_perjalanans perjalanans = new Info_perjalanans();
    private E_tolls e_tolls = new E_tolls();
    private int jarak;
    private String saldo;
    private double sld;
    private int curr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentInfoPerjalananBinding = FragmentInfoPerjalananBinding.inflate(inflater, container, false);


        drivers = new Drivers();

        Bundle bundle = getArguments();
        if(bundle != null){
            drivers.setKey(bundle.getString("key"));
            perjalanans.setId(bundle.getString("key_p"));
        }

        database = FirebaseDatabase.getInstance().getReference().child("Drivers").child(drivers.getKey()).child("e_tolls");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String saldo = snapshot.child("saldo").getValue().toString();
                sld = Double.parseDouble(saldo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query = FirebaseDatabase.getInstance().getReference()
                .child("Info_perjalanans").orderByChild("status_perjalanan").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot : snapshot.getChildren()){
                    String sJarak = childSnapshot.child("jarak").getValue().toString();
                    jarak = Integer.valueOf(sJarak);
                    fragmentInfoPerjalananBinding.km.setText("Total bayar di KM " + sJarak);
                    fragmentInfoPerjalananBinding.totalHarga.setText("Rp. " + String.valueOf(jarak * 1300) );
                    sld = sld - (jarak * 1300);
                    curr = (int) sld;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fragmentInfoPerjalananBinding.bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saldo = String.valueOf(curr);
                HashMap e_toll = new HashMap();
                e_toll.put("saldo",saldo);
                database = FirebaseDatabase.getInstance().getReference().child("Drivers").child(drivers.getKey()).child("e_tolls");
                database.updateChildren(e_toll).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Toast.makeText(getContext(), "Transaksi berhasil", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return fragmentInfoPerjalananBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentInfoPerjalananBinding.chart.getDescription().setEnabled(true);
        fragmentInfoPerjalananBinding.chart.setTouchEnabled(true);

        fragmentInfoPerjalananBinding.chart.setDragEnabled(true);
        fragmentInfoPerjalananBinding.chart.setScaleEnabled(true);
        fragmentInfoPerjalananBinding.chart.setDrawGridBackground(false);

        fragmentInfoPerjalananBinding.chart.setPinchZoom(true);

        fragmentInfoPerjalananBinding.chart.setBackgroundColor(Color.TRANSPARENT);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        fragmentInfoPerjalananBinding.chart.setData(data);

        Legend l = fragmentInfoPerjalananBinding.chart.getLegend();

        l.setForm(Legend.LegendForm.LINE);
        //  l.setTypeface(tfLight);
        l.setTextColor(Color.WHITE);

        XAxis xl = fragmentInfoPerjalananBinding.chart.getXAxis();
        // xl.setTypeface(tfLight);
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = fragmentInfoPerjalananBinding.chart.getAxisLeft();
        //  leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = fragmentInfoPerjalananBinding.chart.getAxisRight();
        rightAxis.setEnabled(false);

    }

    private void addEntry() {
        LineData data = fragmentInfoPerjalananBinding.chart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }


            data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 40) + 30f), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            fragmentInfoPerjalananBinding.chart.notifyDataSetChanged();

            // limit the number of visible entries
            fragmentInfoPerjalananBinding.chart.setVisibleXRangeMaximum(300);
            // chart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            fragmentInfoPerjalananBinding.chart.moveViewToX(data.getEntryCount());

        }
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setFillAlpha(65);
        set.setColor(Color.WHITE);

        set.setHighlightEnabled(false);
        set.setDrawValues(false);
        set.setDrawCircles(false);

        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);

        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setCubicIntensity(0.2f);

        return set;
    }

    private Thread thread;




    @Override
    public void onPause() {
        super.onPause();

        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0 ; i < 100; i++){
                    if(getActivity()!=null){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addEntry();
                            }
                        });
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }



}
