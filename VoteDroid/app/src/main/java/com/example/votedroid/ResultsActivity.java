package com.example.votedroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.votedroid.bd.BD;
import com.example.votedroid.databinding.ActivityResultsBinding;
import com.example.votedroid.databinding.ActivityVoteBinding;
import com.example.votedroid.modele.VDVote;
import com.example.votedroid.service.ServiceImplementation;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ResultsActivity extends AppCompatActivity {

    BarChart chart;
    List<VDVote> votes;
    private ServiceImplementation service;
    private BD maBD;
    private ActivityResultsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setTitle("Résultats");

        votes = maBD.monDao().tousLesVotesPourUneQuestion((long) (getIntent().getIntExtra("idposition",-1) + 1));


        chart = findViewById(R.id.chart);


        /* Settings for the graph - Change me if you want*/
        chart.setMaxVisibleValueCount(6);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new DefaultAxisValueFormatter(0));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setGranularity(1);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setValueFormatter(new DefaultAxisValueFormatter(0));
        chart.getDescription().setEnabled(false);
        chart.getAxisRight().setEnabled(false);



        /* Data and function call to bind the data to the graph */
        Map<Integer, Integer> dataGraph = new HashMap<Integer, Integer>();

            int montant = 0;
            for(VDVote unvote : votes)
            {
                dataGraph.put(montant,unvote.nbreVote);
                montant++;
            }
            setData(dataGraph);

            binding.LaQuestion.setText(maBD.monDao().toutesLesQuestions().get(getIntent().getIntExtra("idposition",-1)).texteQuestion);
            binding.LaMoyenne.setText(Float.toString(service.moyenneVotes(maBD.monDao().toutesLesQuestions().get(getIntent().getIntExtra("idposition",-1)))));
            binding.EcartType.setText(Float.toString(service.ecartTypeVotes(maBD.monDao().toutesLesQuestions().get(getIntent().getIntExtra("idposition",-1)))));

    }
    private void setData(Map<Integer, Integer> datas) {

        ArrayList<BarEntry> values = new ArrayList<>();

        /* Every bar entry is a bar in the graphic */
        for (Map.Entry<Integer, Integer> key : datas.entrySet()){
            values.add(new BarEntry(key.getKey() , key.getValue()));
        }

        BarDataSet set1;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Notes");

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(.9f);
            chart.setData(data);
        }
    }
}