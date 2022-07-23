package com.example.tollwisedriver;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tollwisedriver.databinding.FragmentInfoPerjalananBinding;
import com.example.tollwisedriver.databinding.FragmentProfileBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

public class Fragment_info_perjalanan extends Fragment {

    FragmentInfoPerjalananBinding fragmentInfoPerjalananBinding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentInfoPerjalananBinding = FragmentInfoPerjalananBinding.inflate(inflater, container, false);


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

        return  fragmentInfoPerjalananBinding.getRoot();
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
