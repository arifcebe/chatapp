package com.arifcebe.chatgroup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arifcebe.chatgroup.model.User;

import java.util.List;

/**
 * Created by arifcebe on 30/10/15.
 */
public class UserAdapter extends BaseAdapter {

    private Context context;
    private List<User> listUser;
    private UserViewHolder holder;

    public UserAdapter(Context context, List<User> listUser) {
        this.context = context;
        this.listUser = listUser;
    }

    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public Object getItem(int position) {
        return listUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.user_item,null);
            holder = new UserViewHolder();
            holder.no = (TextView) convertView.findViewById(R.id.item_no);
            holder.name = (TextView) convertView.findViewById(R.id.item_name);
            holder.user = (TextView) convertView.findViewById(R.id.item_user);
            holder.year = (TextView) convertView.findViewById(R.id.item_birthDay);

            convertView.setTag(holder);
        }else{
            holder = (UserViewHolder) convertView.getTag();
        }

        User user = listUser.get(position);

        int no = 1 + position;
        holder.no.setText(""+(no+1));
        holder.name.setText(user.getFullName());
        holder.year.setText(String.valueOf(user.getBirthYear()));
        Log.d("adapter","year "+user.getBirthYear()+" | "+user.getFullName());

        return convertView;
    }

    static class UserViewHolder{
        TextView no,user,name,year;
    }
}
