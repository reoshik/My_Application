package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ListView listView;
    private View userPanel;
    private Button editButton;

    private UserListAdapter adapter;

    public static TextView NameTextView;
    public static TextView StateTextView;
    public static TextView AgeTextView;

    public static int positionActiveUser = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new UserStaticInfo();

        listView = findViewById(R.id.listview);
        userPanel = findViewById(R.id.userPanel);

        NameTextView = findViewById(R.id.NameTextView);
        StateTextView = findViewById(R.id.StateTextView);
        AgeTextView = findViewById(R.id.AgeTextView);

        editButton = findViewById(R.id.EditProfileButton);

        adapter = new UserListAdapter(
                this,
                UserStaticInfo.userList
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        positionActiveUser = position;
                        UserStaticInfo.selectedPosition = position;

                        InitPanel(
                                UserStaticInfo.userList.get(position)
                        );
                    }
                }
        );

        editButton.setOnClickListener(v -> OpenUserActivity());

        userPanel.setVisibility(View.GONE);
    }

    private void InitPanel(User user) {

        if (user == null) {
            return;
        }

        NameTextView.setText(user.getName());
        StateTextView.setText(user.getState());
        AgeTextView.setText("Возраст: " + user.getAge());

        userPanel.setVisibility(View.VISIBLE);
    }

    public void BackToList(View view) {
        userPanel.setVisibility(View.GONE);
    }

    private void OpenUserActivity() {

        if (positionActiveUser < 0 ||
                positionActiveUser >= UserStaticInfo.userList.size()) {

            Toast.makeText(
                    this,
                    "Сначала выберите пользователя",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        Intent intent = new Intent(
                MainActivity.this,
                UserActivity.class
        );

        intent.putExtra("userPosition", positionActiveUser);

        startActivity(intent);
    }

    public static void UpdateListAndUserPanel(User user) {

        if (user == null) {
            return;
        }

        if (NameTextView != null) {
            NameTextView.setText(user.getName());
        }

        if (StateTextView != null) {
            StateTextView.setText(user.getState());
        }

        if (AgeTextView != null) {
            AgeTextView.setText("Возраст: " + user.getAge());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        if (positionActiveUser >= 0 &&
                positionActiveUser < UserStaticInfo.userList.size() &&
                userPanel != null &&
                userPanel.getVisibility() == View.VISIBLE) {

            UpdateListAndUserPanel(
                    UserStaticInfo.userList.get(positionActiveUser)
            );
        }
    }
}