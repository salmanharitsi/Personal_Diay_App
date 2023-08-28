package id.co.salmanharitsi.personaldiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import id.co.salmanharitsi.personaldiary.adapters.DiaryRecyclerViewAdapter;
import id.co.salmanharitsi.personaldiary.database.DiaryRepository;
import id.co.salmanharitsi.personaldiary.models.Diary;

public class MainProgram extends AppCompatActivity implements DiaryRecyclerViewAdapter.onDiaryListener, View.OnClickListener{

    private static final String Tag = "MainActivity";
    private RecyclerView recyclerView;
    private ArrayList<Diary> al_diary = new ArrayList<>();
    private DiaryRecyclerViewAdapter adapter;
    private FloatingActionButton fab;
    private DiaryRepository diaryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_program);

        recyclerView = findViewById(R.id.diary_rv);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        diaryRepository = new DiaryRepository(this);

        initRecyclerView();
//        insertDummyData();
        retrievesDiaryData();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("My Personal Diary");
        setSupportActionBar(toolbar);
    }

    private void retrievesDiaryData(){
        diaryRepository.retrievDiaryTask().observe(this, new Observer<List<Diary>>() {
            @Override
            public void onChanged(List<Diary> diaries) {
                if (al_diary.size()>0){
                    al_diary.clear();
                }
                if (diaries!=null){
                    al_diary.addAll(diaries);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new DiaryRecyclerViewAdapter(al_diary, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }

    private void insertDummyData(){
        for (int i=1;i<100;i++){
            Diary diary = new Diary();
            diary.setTitle("Diary ke-"+i);
            diary.setTimestamp("Juni 2023");
            al_diary.add(diary);
        }
        adapter.notifyDataSetChanged();
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback =
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView,
                                      @NonNull RecyclerView.ViewHolder viewHolder,
                                      @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    deleteDiary(al_diary.get(viewHolder.getAdapterPosition()));
                }
            };

    private void deleteDiary(Diary diary){
        al_diary.remove(diary);
        adapter.notifyDataSetChanged();
        diaryRepository.deleteDiary(diary);
    }

    @Override
    public void onDiaryClick(int position) {
        Toast.makeText(this, "Anda memilih diary ke-"+(position+1), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainProgram.this, DiaryDetailsActivity.class);
        intent.putExtra("diary", al_diary.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainProgram.this, DiaryDetailsActivity.class);
        startActivity(intent);
    }
}