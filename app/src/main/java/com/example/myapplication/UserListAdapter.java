package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

public class UserListAdapter extends BaseAdapter {

    private final Context context;
    private final List<User> users;
    private final LayoutInflater inflater;

    public UserListAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(
            int position,
            View convertView,
            ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = inflater.inflate(
                    R.layout.item_user,
                    parent,
                    false
            );
        }

        User user = getItem(position);

        TextView nameTextView =
                view.findViewById(R.id.NameTextView);

        TextView stateTextView =
                view.findViewById(R.id.StateTextView);

        FrameLayout stateRound =
                view.findViewById(R.id.StateRound);

        nameTextView.setText(user.getName());
        stateTextView.setText(user.getState());

        switch (user.getStateSignal()) {

            case 1:
                stateRound.setBackgroundResource(
                        R.drawable.back_online
                );
                break;

            case 2:
                stateRound.setBackgroundResource(
                        R.drawable.back_departed
                );
                break;

            default:
                stateRound.setBackgroundResource(
                        R.drawable.back_offline
                );
                break;
        }

        return view;
    }
}