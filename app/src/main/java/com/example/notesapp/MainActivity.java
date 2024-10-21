package com.example.notesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText noteInput;
    private ListView notesListView;
    private ArrayList<String> notes;
    private ArrayAdapter<String> adapter;
    private Spinner categorySpinner;
    private ArrayList<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        noteInput = findViewById(R.id.noteInput);
        notesListView = findViewById(R.id.notesListView);
        categorySpinner = findViewById(R.id.categorySpinner);

        // Initialize notes list and adapter
        notes = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        notesListView.setAdapter(adapter);

        // Initialize categories
        categories = new ArrayList<>();
        categories.add("Personal");
        categories.add("Work");
        categories.add("Other");

        // Set up category spinner
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // Set up note click listener for deletion
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deleteNote(position);
            }
        });
    }

    // Add new note
    public void addNote(View view) {
        String noteText = noteInput.getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString();
        if (!noteText.isEmpty()) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            String noteWithTimestamp = "[" + category + "] " + noteText + " (" + timestamp + ")";
            notes.add(noteWithTimestamp);
            adapter.notifyDataSetChanged();
            noteInput.setText("");
        } else {
            Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show();
        }
    }

    // Delete note
    private void deleteNote(final int position) {
        notes.remove(position);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
    }
}
