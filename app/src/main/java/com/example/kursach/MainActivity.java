package com.example.kursach;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.kursach.adapter.CategoryAdapter;
import com.example.kursach.adapter.CourseAdapter;
import com.example.kursach.model.Category;
import com.example.kursach.model.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecyclerView categoryRecycler, courseRecycler;
CategoryAdapter categoryAdapter;
CourseAdapter courseAdapter;
    private List<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1 , "Игры"));
        categoryList.add(new Category(2 , "Сайты"));
        categoryList.add(new Category(3 , "Языки"));
        categoryList.add(new Category(4 , "Прочее"));

        setCategoryRecycler(categoryList);

        courseList = new ArrayList<>();
        courseList.add(new Course(1 , "java", "Профессия Java\nразработчик", "1 января", "начальный", "#424345"));
        courseList.add(new Course(2 , "python", "Профессия Python\nразработчик", "10 января", "начальный", "#9FA52D"));


        setCourseRecycler(categoryList);

    }

    private void setCourseRecycler(List<Category> categoryList) {

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
}