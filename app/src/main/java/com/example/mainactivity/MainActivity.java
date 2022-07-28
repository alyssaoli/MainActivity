package com.example.mainactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TODO> elements;
    private MyListAdapter myAdapter;
    TODO todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        EditText editText = (EditText) findViewById(R.id.editText);
        Switch swUrgernt = findViewById(R.id.switch2);
        Button addButton = findViewById(R.id.myButton);

        elements = new ArrayList<>();


        addButton.setOnClickListener(click -> {

            String listItem = editText.getText().toString();

            todo = new TODO();
            todo.setTodoText(listItem);
            todo.setUrgent(swUrgernt.isChecked());

            elements.add(todo);
            myAdapter.notifyDataSetChanged();

            editText.setText("");
            swUrgernt.setChecked(false);
        });

        ListView myList = findViewById(R.id.myList);
        myList.setAdapter(myAdapter = new MyListAdapter());

        myList.setOnItemClickListener((parent, view, pos, id) -> {
//            elements.remove(pos);
//            myAdapter.notifyDataSetChanged();
        });

        myList.setOnItemLongClickListener((p, b, pos, id) -> {

            View newView = getLayoutInflater().inflate(R.layout.todo, null);
            TextView tView = newView.findViewById(R.id.textGoesHere);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            //            tView.setText(elements.get(pos).getTodoText());

            alertDialogBuilder.setTitle("Do you want to delete this?")
                    //What is the message:
                    .setMessage("The selected row is: " + pos +
                            "\n " + elements.get(pos).todoText)

                    //what the Yes button does:
                    .setPositiveButton("Yes", (click, arg) -> {
                        elements.remove(elements.get(pos));
                        myAdapter.notifyDataSetChanged();
                    })

                    //What the No button does:
                    .setNegativeButton("No", (click, arg) -> {
                    })
                    .setView(newView)
                    //Show the dialog
                    .create().show();
            return true;
        });
    }

    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return elements.size();
        }

        public TODO getItem(int position) {
            return elements.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View old, ViewGroup parent) {

            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            //make a new row:
            if (newView == null) {
                newView = inflater.inflate(R.layout.todo, parent, false);
            }

            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.textGoesHere);
            tView.setText(getItem(position).todoText);

            if (getItem(position).isUrgent) {
                tView.setBackgroundColor(Color.RED);
                tView.setTextColor(Color.WHITE);
            } else {
                tView.setBackgroundColor(Color.WHITE);
                tView.setTextColor(Color.GRAY);
            }

            //return it to be put in the table
            return newView;
        }
    }
}

class TODO {

    String todoText;
    boolean isUrgent;

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean urgent) {
        isUrgent = urgent;
    }
}