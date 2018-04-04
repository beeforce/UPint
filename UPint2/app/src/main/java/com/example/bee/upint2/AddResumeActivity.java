package com.example.bee.upint2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bee.upint2.adapter.ListViewAdapterProvince;
import com.example.bee.upint2.adapter.ListViewAdapterUniversity;
import com.facebook.stetho.common.ArrayListAccumulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class AddResumeActivity extends AppCompatActivity {

    private ArrayList<String> provinceList = new ArrayList<>();
    private ArrayList<String> universityList = new ArrayList<>();
    private static final String TAG = "AddResumeActivity";
    private ListViewAdapterProvince adapter;
    private ListViewAdapterUniversity adapter2;
    private ListView listView,listView2;
    private EditText editText, editText2;
    private RelativeLayout relay1, relay2;
    private Button next1,save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resume2);

        TextView location = findViewById(R.id.location);
        float density = getResources().getDisplayMetrics().density;
        Drawable drawable_location = getResources().getDrawable(R.drawable.place_green);
        int width = Math.round(18 * density);
        int height = Math.round(22 * density);
        drawable_location.setBounds(0,0,width,height);
        location.setCompoundDrawables(null,null,drawable_location,null);
        location.setCompoundDrawablePadding(5);

        EditText search_university_edittextr = findViewById(R.id.search_university_edittext);
        float density_search = getResources().getDisplayMetrics().density;
        Drawable drawable_search = getResources().getDrawable(R.drawable.ic_search_black);
        int width_search = Math.round(28 * density_search);
        int height_search = Math.round(28 * density_search);
        drawable_search.setBounds(0,0,width_search,height_search);
        search_university_edittextr.setCompoundDrawables(drawable_search,null,null,null);
        search_university_edittextr.setCompoundDrawablePadding(5);

        relay1 = findViewById(R.id.student_resume_layout1);
        relay2 = findViewById(R.id.student_resume_layout2);

        next1 = findViewById(R.id.nextbtregion1);
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relay1.setVisibility(View.GONE);
                relay2.setVisibility(View.VISIBLE);
                editText2.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText2, InputMethodManager.SHOW_IMPLICIT);

            }
        });
        save = findViewById(R.id.resume_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddResumeActivity.this, Main2Activity.class);
                startActivity(i);
            }
        });

        listView = findViewById(R.id.province_list_view);
        listView2 = findViewById(R.id.university_list_view);

        editText = findViewById(R.id.location);
        editText2 = findViewById(R.id.search_university_edittext);

        editText.addTextChangedListener(mTextEditorWatcher);
        editText2.addTextChangedListener(mTextEditorWatcher2);

        provinceList.addAll(Arrays.asList("Amnat Charoen", "Ang Thong", "Bangkok", "Bueng Kan",
                "Buriram", "Chachoengsao", "Chai Nat", "Chaiyaphum",
                "Chanthaburi", "Chiang Mai", "Chiang Rai", "Chonburi",
                "Chumphon", "Kalasin", "Kamphaeng Phet", "Kanchanaburi",
                "Khon Kaen", "Krabi", "Lampang", "Lamphun",
                "Loei", "Lopburi", "Mae Hong Son", "Maha Sarakham",
                "Mukdahan", "Nakhon Nayok", "Nakhon Pathom", "Nakhon Phanom",
                "Nakhon Ratchasima", "Nakhon Sawan", "Nakhon Si Thammarat", "Nan",
                "Narathiwat", "Nong Bua Lam Phu", "Nong Khai", "Nonthaburi",
                "Pathum Thani", "Pattani", "Phang Nga", "Phatthalung",
                "Phayao", "Phetchabun", "Phetchaburi", "Phichit",
                "Phitsanulok", "Phra Nakhon Si Ayutthaya", "Phrae", "Phuket",
                "Prachinburi", "Prachuap Khiri Khan", "Ranong", "Ratchaburi",
                "Rayong", "Roi Et", "Sa Kaeo", "Sakon Nakhon",
                "Samut Prakan", "Samut Sakhon", "Samut Songkhram", "Saraburi",
                "Satun", "Sing Buri", "Sisaket", "Songkhla",
                "Sukhothai", "Suphan Buri", "Surat Thani", "Surin",
                "Tak", "Trang", "Trat", "Ubon Ratchathani",
                "Udon Thani", "Uthai Thani", "Uttaradit", "Yala", "Yasothon"));

        adapter = new ListViewAdapterProvince(this, provinceList);
        listView.setAdapter(adapter);

        universityList.addAll(Arrays.asList("Kalasin University", "Maejo University", "Mahasarakham University",
                "Nakhon Phanom University", "Naresuan University", "National Institute of Development Administration",
                "Pathumwan Institute of Technology", "Prince of Songkla University", "Princess of Naradhiwas University",
                "Ramkhamhaeng University", "Silpakorn University", "Srinakharinwirot University",
                "Sukhothai Thammathirat Open University", "Ubon Ratchathani University", "Burapha University",
                "Chiang Mai University", "Chulalongkorn University", "Chulabhorn Royal Academy of Science",
                "Kasetsart University", "Khon Kaen University", "King Mongkut's Institute of Technology Ladkrabang",
                "King Mongkut's University of Technology North Bangkok", "King Mongkut's University of Technology Thonburi", "Mae Fah Luang University",
                "Mahachulalongkornrajavidyalaya University", "Mahamakut Buddhist University", "Mahidol University",
                "Navamindradhiraj University", "University of Phayao", "Princess Galyani Vadhana Institute of Music",
                "Suan Dusit University", "Suranaree University of Technology", "Thaksin University",
                "Thammasat University", "Walailak University", "Bansomdejchaopraya Rajabhat University",
                "Buri Ram Rajabhat University", "Chaiyaphum Rajabhat University", "Chandrakasem Rajabhat University",
                "Chiang Mai Rajabhat University", "Chiang Rai Rajabhat University", "Dhonburi Rajabhat University",
                "Kamphaeng Phet Rajabhat University", "Kanchanaburi Rajabhat University", "Lampang Rajabhat University",
                "Loei Rajabhat University", "Maha Sarakham Rajabhat University", "Muban Chom Bung Rajabhat University",
                "Nakhon Pathom Rajabhat University", "Nakhon Ratchasima Rajabhat University", "Nakhon Sawan Rajabhat University",
                "Nakhon Si Thammarat Rajabhat University", "Valaya Alongkorn Rajabhat University", "Phetchabun Rajabhat University",
                "Phetchaburi Rajabhat University", "Phranakhon Rajabhat University", "Phranakhon Si Ayutthaya Rajabhat University",
                "Phuket Rajabhat University", "Pibulsongkram Rajabhat University", "Rajanagarindra Rajabhat University",
                "Rambhai Barni Rajabhat University", "Roi Et Rajabhat University", "Sakon Nakhon Rajabhat University",
                "Sisaket Rajabhat University", "Songkhla Rajabhat University", "Suan Sunandha Rajabhat University",
                "Suratthani Rajabhat University", "Surin Rajabhat University", "Thepsatri Rajabhat University",
                "Ubon Ratchathani Rajabhat University", "Udon Thani Rajabhat University", "Uttaradit Rajabhat University",
                "Yala Rajabhat University", "Rajamangala University of Technology Isan", "Rajamangala University of Technology Krungthep",
                "Rajamangala University of Technology Lanna", "Rajamangala University of Technology Phra Nakhon", "Rajamangala University of Technology Rattanakosin",
                "Rajamangala University of Technology Srivijaya", "Rajamangala University of Technology Suvarnabhumi", "Rajamangala University of Technology Tawan-ok",
                "Rajamangala University of Technology Thanyaburi", "Bunditpatanasilpa institute", "Civil Aviation Training Center",
                "Phramongkutklao College of Medicine", "Police Nursing College", "Praboromarajchanok Institute",
                "Royal Thai Army Nursing College", "Royal Thai Navy College of Nursing", "The Royal Thai Air Force Nursing College",
                "Chulachomklao Royal Military Academy", "Royal Thai Navy Academy", "Royal Thai Air Force Academy",
                "Royal Police Cadet Academy", "Asia-Pacific International University", "Asian University",
                "Assumption University", "Bangkok University", "Bangkokthonburi University",
                "Chaopraya University", "Christian University", "Dhurakij Pundit University",
                "E-sarn University", "Eastern Asia University", "Hatyai University",
                "Huachiew Chalermprakiet University", "Kasem Bundit University", "Krirk University",
                "Mahanakorn University of Technology", "North Eastern University", "North Chiang Mai University",
                "Pathumthani University", "Payap University", "The University of Central Thailand",
                "Ratchathani University", "Rattana Bundit University", "Saint John's University",
                "Shinawatra University", "Siam University", "South-East Asia University",
                "Sripatum University", "Stamford International University", "Rangsit University",
                "The Far Eastern University", "University of the Thai Chamber of Commerce", "Vongchavalitkul University",
                "Webster University Thailand", "Western University", "Fatoni University",
                "Nation University (Yonok University)", "The Eastern University of Management and Technology", "Thonburi University",
                "North Bangkok University", "Arsom Silp Institute of the Arts", "Bangkok School of Management",
                "Raffles International College(Bangkok)", "Chulabhorn Graduate Institute", "Institute of Technology Ayothaya",
                "Kantana Institute", "Learning Institute For Everyone", "Panyapiwat Institute of Management",
                "Rajapark Institute", "SAE Institute Bangkok", "Thai-Nichi Institute of Technology",
                "Bangkok Suvarnabhumi University", "Bundit Boriharnthurakit College", "Cambridge College (Thailand)",
                "Chalermkarnchana University", "Chiangrai College", "College of Asian Scholars",
                "Dusit Thani College", "International Buddhist College", "Lampang Inter-Tech College",
                "Lumnamping College", "Nakhonratchasima College", "Phanomwan College",
                "Phitsanulok University", "Raffles International College", "Saengtham College",
                "Saint Louis College", "Santapol College", "Siam Technology College",
                "Southeast Bangkok College", "Southern College of Technology", "St Theresa International College",
                "Tapee University", "Thongsook College", "Asian Institute of Technology"
                ));

        adapter2 = new ListViewAdapterUniversity(this, universityList);
        listView2.setAdapter(adapter2);

        Collections.sort(universityList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText(provinceList.get(position).toString());
                editText.clearComposingText();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                listView.setVisibility(View.INVISIBLE);

            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText2.setText(universityList.get(position).toString());
                editText2.clearComposingText();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                listView2.setVisibility(View.INVISIBLE);
            }
        });

        RelativeLayout activity_login_layout = findViewById(R.id.activity_Addresume_layout);

        activity_login_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                return true;
            }
        });

    }

    //textwatcher province
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            listView.setVisibility(View.VISIBLE);
        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }

        public void afterTextChanged(Editable s)
        {
            String who = editText.getText().toString().toLowerCase(Locale.getDefault());
            adapter.myFilter(who);
            listView.setAdapter(adapter);
        }
    };


    //textwatcher university
    private final TextWatcher mTextEditorWatcher2 = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
            listView2.setVisibility(View.VISIBLE);
            editText2.setTextColor(Color.parseColor("#98c428"));
        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }

        public void afterTextChanged(Editable s)
        {
            String who = editText2.getText().toString().toLowerCase(Locale.getDefault());
            adapter2.myFilter(who);
            listView2.setAdapter(adapter2);
        }
    };
}
