package com.example.bee.upint2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bee.upint2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapterUniversity extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> universitylist;
    private ArrayList<String> universityArrayList = new ArrayList<>();

    public ListViewAdapterUniversity(Context context, List<String> provincelist){
        this.context = context;
        this.universitylist = provincelist;
        this.universityArrayList.addAll(provincelist);
        layoutInflater = LayoutInflater.from(context);

    }

    public void myFilter(String name){

        name = name.toLowerCase(Locale.getDefault());
        universitylist.clear();
        if (name.length() == 0){
            universitylist.addAll(universityArrayList);
        }else{
            for (String each : universityArrayList){
                if (each.toString().toLowerCase(Locale.getDefault()).contains(name)){
                    universitylist.add(each);
                }
            }
        }

    }

    @Override
    public int getCount() {
        return universitylist.size();
    }

    @Override
    public String getItem(int position) {
        return universitylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListViewAdapterUniversity.ViewHolder holder;
        if (convertView == null){
            holder = new ListViewAdapterUniversity.ViewHolder();
            convertView = layoutInflater.inflate(R.layout.model_item2,null);
            holder.textView = (TextView) convertView.findViewById(R.id.nameList);
            convertView.setTag(holder);
        }else {
            holder = (ListViewAdapterUniversity.ViewHolder) convertView.getTag();
        }
        holder.textView.setText(universitylist.get(position).toString());

            if (universitylist.get(position).toString() == "Chiang Mai University" ||
                    universitylist.get(position).toString() == "Mahidol University" ||
                    universitylist.get(position).toString() == "Kasetsart University" ||
                    universitylist.get(position).toString() == "Chulalongkorn University" ||
                    universitylist.get(position).toString() == "Prince of Songkla University" ||
                    universitylist.get(position).toString() == "Thammasat University" ||
                    universitylist.get(position).toString() == "Khon Kaen University" ||
                    universitylist.get(position).toString() == "Burapha University" ||
                    universitylist.get(position).toString() == "Sripatum University" ||
                    universitylist.get(position).toString() == "King Mongkut's University of Technology Thonburi" ||
                    universitylist.get(position).toString() == "Asian Institute of Technology" ||
                    universitylist.get(position).toString() == "Rajamangala University of Technology Thanyaburi" ||
                    universitylist.get(position).toString() == "Naresuan University" ||
                    universitylist.get(position).toString() == "Silpakorn University" ||
                    universitylist.get(position).toString() == "King Mongkut's Institute of Technology Ladkrabang" ||
                    universitylist.get(position).toString() == "Srinakharinwirot University" ||
                    universitylist.get(position).toString() == "Mahasarakham University" ||
                    universitylist.get(position).toString() == "Assumption University" ||
                    universitylist.get(position).toString() == "National Institute of Development Administration" ||
                    universitylist.get(position).toString() == "Suranaree University of Technology" ||
                    universitylist.get(position).toString() == "Ramkhamhaeng University" ||
                    universitylist.get(position).toString() == "King Mongkut's University of Technology North Bangkok" ||
                    universitylist.get(position).toString() == "Maejo University" ||
                    universitylist.get(position).toString() == "Huachiew Chalermprakiet University" ||
                    universitylist.get(position).toString() == "Ubon Ratchathani University" ||
                    universitylist.get(position).toString() == "Bangkok University" ||
                    universitylist.get(position).toString() == "Suan Sunandha Rajabhat University" ||
                    universitylist.get(position).toString() == "Rajamangala University of Technology Lanna" ||
                    universitylist.get(position).toString() == "University of the Thai Chamber of Commerce" ||
                    universitylist.get(position).toString() == "Walailak University" ||
                    universitylist.get(position).toString() == "Nakhon Pathom Rajabhat University" ||
                    universitylist.get(position).toString() == "Rangsit University" ||
                    universitylist.get(position).toString() == "Payap University" ||
                    universitylist.get(position).toString() == "Nakhon Ratchasima Rajabhat University" ||
                    universitylist.get(position).toString() == "Suan Dusit University" ||
                    universitylist.get(position).toString() == "Loei Rajabhat University" ||
                    universitylist.get(position).toString() == "Vongchavalitkul University" ||
                    universitylist.get(position).toString() == "Chiang Mai Rajabhat University" ||
                    universitylist.get(position).toString() == "Mahachulalongkornrajavidyalaya University" ||
                    universitylist.get(position).toString() == "Rajamangala University of Technology Phra Nakhon" ||
                    universitylist.get(position).toString() == "Mae Fah Luang University" ||
                    universitylist.get(position).toString() == "Buriram Rajabhat University" ||
                    universitylist.get(position).toString() == "Chiang Rai Rajabhat University" ||
                    universitylist.get(position).toString() == "Rajamangala University of Technology Isarn" ||
                    universitylist.get(position).toString() == "Siam University" ||
                    universitylist.get(position).toString() == "Pibulsongkram Rajabhat University" ||
                    universitylist.get(position).toString() == "Shinawatra University" ||
                    universitylist.get(position).toString() == "Dhurakij Pundit University" ||
                    universitylist.get(position).toString() == "Thaksin University" ||
                    universitylist.get(position).toString() == "Rajamangala University of Technology Srivijaya"
                    ){
                holder.textView.setTextColor(Color.parseColor("#98c428"));
            }else {
                holder.textView.setTextColor(Color.parseColor("#8a8888"));
            }

        return convertView;
    }


    public class ViewHolder{
        TextView textView;
    }
}