package com.example.android.adarak;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> gearAmounts = new ArrayList<Integer>();
    ArrayList<String> editTextValues = new ArrayList<String>();
    ArrayList<String> sizeTextValues = new ArrayList<String>();
    ArrayList<String> sizeEditTextValues = new ArrayList<String>();

    boolean hasStoppers;
    boolean hasEtriers;
    String tripName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createRack(View view) {

        int[] editIds = new int[]{R.id.quickdraw_edit_view, R.id.rope70m_edit_view,
                R.id.rope60m_edit_view, R.id.rope50m_edit_view, R.id.comments_box};

        for (int id : editIds) {
            EditText input = (EditText) findViewById(id);
            editTextValues.add(input.getText().toString());
        }

        int[] sizeTextIds = new int[]{R.id.num_point3_text_view,
                R.id.num_point4_text_view, R.id.num_point5_text_view, R.id.num_point75_text_view,
                R.id.num_1_text_view, R.id.num_2_text_view, R.id.num_3_text_view,
                R.id.num_4_text_view, R.id.num_5_text_view, R.id.num_6_text_view};

        for (int id : sizeTextIds) {
            TextView theSizes = (TextView) findViewById(id);
            sizeTextValues.add(theSizes.getText().toString());
        }

        int[] sizeEditTextIds = new int[]{R.id.num_point3_edit_view,
                R.id.num_point4_edit_view, R.id.num_point5_edit_view, R.id.num_point75_edit_view,
                R.id.num_1_edit_view, R.id.num_2_edit_view, R.id.num_3_edit_view,
                R.id.num_4_edit_view, R.id.num_5_edit_view, R.id.num_6_edit_view};

        for (int id : sizeEditTextIds) {
            EditText input = (EditText) findViewById(id);
            sizeEditTextValues.add(input.getText().toString());
        }

        EditText theTripName = (EditText) findViewById(R.id.trip_edit_view);
        tripName = theTripName.getText().toString();

        CheckBox stoppers = (CheckBox) findViewById(R.id.stoppers_checkbox);
        hasStoppers = stoppers.isChecked();

        CheckBox etriers = (CheckBox) findViewById(R.id.etriers_checkbox);
        hasEtriers = etriers.isChecked();

        sendTo();
    }

    private void sendTo() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, editTextValues.get(0));
        intent.putExtra(Intent.EXTRA_TEXT, message());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage();
    }

    public void searchRoute(View view) {
        EditText theTripName = (EditText) findViewById(R.id.trip_edit_view);
        tripName = theTripName.getText().toString();

        String url = "http://www.google.com/#q=" + tripName + "mountain project";
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public String camMessage() {
        getAmount();
        String partialRack = "";
        for (int i = 0; i < sizeEditTextValues.size(); i++) {
            if (gearAmounts.get(i) > 1) {
                partialRack += sizeEditTextValues.get(i) + " " + "#" + sizeTextValues.get(i).replace
                        ("x", "") + "'s, ";
            } else if (gearAmounts.get(i) == 1) {
                partialRack += "1 " + "#" + sizeTextValues.get(i).replace("x", "") + ", ";
            } else {
                continue;
            }
        }
        return partialRack;
    }

    public String quickdrawMessage() {
        String partialRack = "";
        if (Integer.parseInt(editTextValues.get(1)) > 1) {
            partialRack += editTextValues.get(1) + " Quickdraws, ";
        }
        return partialRack;
    }

    public String checkboxMessage() {
        String partialRack = "";
        if (hasStoppers) {
            partialRack += "Stoppers, ";
        }

        if (hasEtriers) {
            partialRack += "Etriers, ";
        }
        return partialRack;
    }

    public String message() {
        String theRack = "We need " + camMessage() + quickdrawMessage() + checkboxMessage();
        return theRack;
    }
    private void displayMessage() {
        TextView sampleMessage = (TextView) findViewById(R.id.sample_output_view);
        sampleMessage.setText(message());
    }

    public void getAmount () {
        for(int i = 0; i < sizeEditTextValues.size(); i++){
            gearAmounts.add(Integer.parseInt(sizeEditTextValues.get(i)));
        }

    }
}


