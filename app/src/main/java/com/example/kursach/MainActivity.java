package com.example.kursach;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.kursach.adapter.CategoryAdapter;
import com.example.kursach.adapter.CourseAdapter;
import com.example.kursach.model.Category;
import com.example.kursach.model.Course;
import com.example.kursach.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecyclerView categoryRecycler, courseRecycler;
CategoryAdapter categoryAdapter;
ImageButton imageButton;
static CourseAdapter courseAdapter;
static List<Course> courseList = new ArrayList<>();
    static List<Course> fullCourseList = new ArrayList<>();

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(MainActivity.this);
        List<Category> categoryList = db.readCategories();

        setCategoryRecycler(categoryList);

       courseList = db.readCourses();


fullCourseList.addAll(courseList);

        setCourseRecycler(courseList);

        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrderPage.class);
                startActivity(intent);
            }
        });

    }

    public void openShoppingCart(View view) {

        Intent intent = new Intent(this, OrderPage.class);
        startActivity(intent);

    }


    private void setCourseRecycler(List<Course> courseList) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        courseRecycler = findViewById(R.id.courseRecycler);
        courseRecycler.setLayoutManager(layoutManager);

        courseAdapter = new CourseAdapter(this, courseList);
        courseRecycler.setAdapter(courseAdapter);

    }

    private void setCategoryRecycler(List<Category> categoryList) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        categoryRecycler = findViewById(R.id.categoryRecycler);
        categoryRecycler.setLayoutManager(layoutManager);

categoryAdapter = new CategoryAdapter(this, categoryList);
categoryRecycler.setAdapter(categoryAdapter);

}

public static void showCoursesByCategory(int category) {

    courseList.clear();
    courseList.addAll(fullCourseList);

List<Course> filterCourses = new ArrayList<>();

for(Course c : courseList) {
    if(c.getCategoryId() == category)
        filterCourses.add(c);

}

courseList.clear();
courseList.addAll(filterCourses);

courseAdapter.notifyDataSetChanged();

}

    public void onResume() {
        super.onResume();
        courseList = db.readCourses();
        courseAdapter = new CourseAdapter(this, courseList);
        courseRecycler.setAdapter(courseAdapter);
        courseAdapter.notifyDataSetChanged();
    }

}