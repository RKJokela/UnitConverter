package com.rjokela.unitconverter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Spannable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;


/**
 * A placeholder fragment containing a simple view.
 */
public class UnitConverterFragment extends Fragment {

    public static final String TAG = "UnitConverter";

    private String[] unitsImperial;
    private String[] unitsMetric;

    private int selectedIndex;

    private TextView textViewUnitsImperial;
    private TextView textViewUnitsMetric;
    private TextView textViewValueImperial;
    private TextView textViewValueMetric;
    private Spinner  spinner;

    public UnitConverterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_unit_converter, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // get names of units
        unitsImperial = getResources().getStringArray(R.array.units_imperial);
        unitsMetric = getResources().getStringArray(R.array.units_metric);

        // initialize TextViews
        textViewUnitsImperial = (TextView) getActivity().findViewById(R.id.textView_unitsImperial);
        textViewUnitsMetric = (TextView) getActivity().findViewById(R.id.textView_unitsMetric);
        textViewValueImperial = (TextView) getActivity().findViewById(R.id.editText_numberImperial);
        textViewValueMetric = (TextView) getActivity().findViewById(R.id.editText_numberMetric);

        textViewValueImperial.setSelectAllOnFocus(true);
        textViewValueImperial.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // this is called when user clicks the "Enter" symbol on lower right of keypad
                imperialToMetric();
                return false;
            }
        });

        textViewValueMetric.setSelectAllOnFocus(true);
        textViewValueMetric.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // this is called when user clicks the "Enter" symbol on lower right of keypad
                metricToImperial();
                return false;
            }
        });

        // initialize spinner
        spinner = (Spinner) getActivity().findViewById(R.id.spinner_conversionType);
        spinner.setSelection(0); // start in kilograms/pounds mode
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != selectedIndex) {
                    selectedIndex = position;
                    updateUnits();
                    clearInputs();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        // put the initial text in the units TextViews
        updateUnits();
    }

    private void updateUnits() {
        Log.d(TAG, "updateUnits: selected index = " + selectedIndex);
        textViewUnitsImperial.setText(unitsImperial[selectedIndex]);
        textViewUnitsMetric.setText(unitsMetric[selectedIndex]);
    }

    private void imperialToMetric() {
        double imperial = Double.parseDouble(textViewValueImperial.getText().toString());
        double metric = 0.0;
        switch (selectedIndex) {
            case 0:
                Log.d(TAG, "Converting " + imperial + " pounds to kilograms");
                metric = Convert.poundsToKilograms(imperial);
                break;
            case 1:
                Log.d(TAG, "Converting " + imperial + " miles to kilometers");
                metric = Convert.milesToKilometers(imperial);
                break;
            case 2:
                Log.d(TAG, "Converting " + imperial + " degrees F to C");
                metric = Convert.fahrenheitToCelsius(imperial);
                break;
            default: break;
        }
        DecimalFormat df = new DecimalFormat("####.##");
        textViewValueMetric.setText(df.format(metric));
    }

    private void metricToImperial() {
        double metric = Double.parseDouble(textViewValueMetric.getText().toString());
        double imperial = 0.0;
        switch (selectedIndex) {
            case 0:
                Log.d(TAG, "Converting " + metric + " kilograms to pounds");
                imperial = Convert.kilogramsToPounds(metric);
                break;
            case 1:
                Log.d(TAG, "Converting " + metric + " kilometers to miles");
                imperial = Convert.kilometersToMiles(metric);
                break;
            case 2:
                Log.d(TAG, "Converting " + metric + " degrees C to F");
                imperial = Convert.celsiusToFahrenheit(metric);
                break;
            default: break;
        }
        DecimalFormat df = new DecimalFormat("####.##");
        textViewValueImperial.setText(df.format(imperial));
    }

    private void clearInputs() {
        textViewValueMetric.setText(null);
        textViewValueImperial.setText(null);
    }
}
