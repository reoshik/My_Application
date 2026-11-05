package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class UserActivity extends Activity {

    private EditText NameTextView;
    private EditText StateTextView;
    private EditText AgeTextView;

    private User activeUser;

    private int userPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);

        new UserStaticInfo();

        NameTextView = findViewById(R.id.NameTextView);
        StateTextView = findViewById(R.id.StateTextView);
        AgeTextView = findViewById(R.id.AgeTextView);

        userPosition = getIntent().getIntExtra(
                "userPosition",
                -1
        );

        if (userPosition < 0 ||
                userPosition >= UserStaticInfo.userList.size()) {

            Toast.makeText(
                    this,
                    "Пользователь не найден",
                    Toast.LENGTH_SHORT
            ).show();

            finish();
            return;
        }

        activeUser = UserStaticInfo.userList.get(userPosition);

        Init();
    }

    private void Init() {
        setUserInfo(activeUser);
    }

    private void setUserInfo(User user) {

        NameTextView.setText(user.getName());

        StateTextView.setText(user.getState());

        AgeTextView.setText(
                String.valueOf(user.getAge())
        );
    }

    public void Back(View view) {
        finish();
    }

    public void Save(View view) {

        String name = NameTextView.getText()
                .toString().trim();

        String state = StateTextView.getText()
                .toString().trim();

        String ageString = AgeTextView.getText()
                .toString().trim();

        if (name.isEmpty()) {

            NameTextView.setError("Введите имя");
            return;
        }

        if (ageString.isEmpty()) {

            AgeTextView.setError("Введите возраст");
            return;
        }

        int age = Transform.parseIntOrDefault(
                ageString,
                -1
        );

        if (age < 0) {

            AgeTextView.setError(
                    "Введите корректный возраст"
            );

            return;
        }

        activeUser.setName(name);
        activeUser.setState(state);
        activeUser.setAge(age);

        activeUser.setStateSignal(
                getStateSignal(state)
        );

        UserStaticInfo.userList.set(
                userPosition,
                activeUser
        );

        MainActivity.positionActiveUser = userPosition;
        UserStaticInfo.selectedPosition = userPosition;

        MainActivity.UpdateListAndUserPanel(activeUser);

        Toast.makeText(
                this,
                "Данные сохранены",
                Toast.LENGTH_SHORT
        ).show();

        finish();
    }

    private int getStateSignal(String state) {

        String normalized = state.toLowerCase(Locale.ROOT);

        if (normalized.contains("не в сети") ||
                normalized.contains("офлайн")) {

            return 0;
        }

        if (normalized.contains("в сети") ||
                normalized.contains("онлайн")) {

            return 1;
        }

        if (normalized.contains("отош")) {
            return 2;
        }

        return activeUser.getStateSignal();
    }
}