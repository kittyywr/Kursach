package com.example.kursach.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.kursach.model.Cart;
import com.example.kursach.model.Category;
import com.example.kursach.model.Course;
import com.example.kursach.model.Levels;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper  {

    private SQLiteDatabase db;
    private final Context context;
    private static final String DATABASE_NAME = "db_courses";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 3);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS Categories(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS Levels(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS Courses(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "img TEXT NOT NULL," +
                "title TEXT NOT NULL," +
                "date TEXT NOT NULL," +
                "color TEXT NOT NULL," +
                "text TEXT NOT NULL," +
                "category_id INTEGER REFERENCES Categories(id)," +
                "level_id INTEGER REFERENCES Levels(id)" +
                ");");
        db.execSQL("CREATE TABLE IF NOT EXISTS Cart(" +
                "courses_id INTEGER PRIMARY KEY REFERENCES Courses(id)" +
                ");");


        db.execSQL("INSERT INTO 'Categories' (title)" +
                "VALUES" +
                "('Игры')," +
                "('Сайты')," +
                "('Языки')," +
                "('Прочее');");
        db.execSQL("INSERT INTO 'Levels' (title) " +
                "VALUES" +
                "('Начальный');");
        db.execSQL("INSERT INTO 'Courses' ('img', 'title', 'date', 'color', 'text', 'category_id', 'level_id') " +
                "VALUES" +
                "('java', 'Профессия Java\nразработчик','1 января','#424345', 'Программа обучения Джава – рассчитана \n" +
                "на новичков в данной сфере.\n" +
                "\n" +
                "Пройдите обучение на инженера-программиста на Java. Вы получите базовые знания алгоритмы, на практике освоите язык Java и сможете\n" +
                "создавать универсальные программы для большинства современных платформ: \n" +
                "сетевые чаты, приложения, мобильные игры и другие сервисы. После 9 месяцев обучения вы сможете трудоустроиться.', '3','1'),\n" +
                "\n" +
                "('python', 'Профессия Python\nразработчик','10 января','#9FA52D','Программа обучения Пайтон – рассчитана \n" +
                "на новичков в данной сфере.\n" +
                "\n" +
                "Python является самым популярным языком программирования из-за своей простоты и универсальности.\n" +
                "Его используют не только для разработки веб-приложений и сервисов, но и для автоматизации, тестирования,\n" +
                "анализа данных и машинного обучения. На Python написано множество библиотек для нейросетей и много другого.', '3','1'),\n" +
                "\n" +
                "('unity', 'Профессия Unity\nразработчик','12 января','#A63030', 'Программа обучения unity – рассчитана \n" +
                "на новичков в данной сфере.\n" +
                "\n" +
                "Разработчик на Unity отвечает за создание игровых механик, уровней и оптимизацию игрового процесса.\n" +
                "Он работает с языком программирования C# и визуальным редактором Unity Editor. На Unity создают игры для консолей,\n" +
                "мобильных устройств и ПК. Например, на этом движке реализованы игры Cities: Skylines, Pokemon Go, Genshin Impact, Hearthstone и другие хиты.', '1','1'),\n" +
                "\n" +
                "('front_end', 'Профессия Frontend\nразработчик','15 января','#EA7F67', 'Программа обучения front end – рассчитана \n" +
                "на новичков в данной сфере.\n" +
                "\n" +
                "Frontend-разработчик отвечает за внешний вид сайта или мобильного приложения: он верстает шаблоны, красиво оформляет текст и изображения,\n" +
                "улучшает взаимодействие пользователя с интерфейсом. \n" +
                "Без frontend-разработчика не получится создать удобный цифровой продукт.', '2','1'),\n" +
                "\n" +
                "('back_end', 'Профессия Backend\nразработчик','18 января','#1C3A75', 'Программа обучения back end – рассчитана \n" +
                "на новичков в данной сфере.\n" +
                "\n" +
                "Backend-разработчики отвечают за невидимую для глаз пользователей серверную часть платформ и программных продуктов.\n" +
                "Они создают внутренние процессы сайтов и приложений, выстраивают обмен данными, подбирают системы для хранения\n" +
                "и управления информацией, обеспечивают максимум производительности при минимуме сбоев.', '2','1'),\n" +
                "\n" +
                "('full_stack', 'Профессия Fullstack\nразработчик','20 января','#081052', 'Программа обучения full stack – рассчитана \n" +
                "на новичков в данной сфере.\n" +
                "\n" +
                "Fullstack-разработчики — универсальные программисты, которые разрабатывают и серверную, и клиентскую часть проекта.\n" +
                "Эти специалисты очень востребованы, так как могут заменить программистов разного профиля.\n" +
                "Fullstack-разработчики владеют широким стеком технологий, поэтому могут создать веб-проект в одиночку', '2','1');\n");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Cart");
        db.execSQL("DROP TABLE IF EXISTS Courses");
        db.execSQL("DROP TABLE IF EXISTS Levels");
        db.execSQL("DROP TABLE IF EXISTS Categories");
        onCreate(db);

    }

    public void insertCategory(String title) {

        db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("title", title);
        long result = db.insert("Categories", null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "New category create!", Toast.LENGTH_SHORT).show();
        }

        db.close();


    }

    public List<Category> readCategories() {
        String query = "SELECT * FROM Categories;";
        db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        List<Category> categoriesList = new ArrayList<>();
        if (cursor == null) return categoriesList;
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();

                category.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                category.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                categoriesList.add(category);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return categoriesList;

    }

    public void updateCategory(int id,String title) {

        db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("title", title);
        long result = db.update("Categories",cv,"id = ?",new String[]{String.valueOf(id)});
        if (result == -1) {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Category update!", Toast.LENGTH_SHORT).show();
        }

        db.close();

    }

    public void deleteCategory(int id) {
        db = this.getWritableDatabase();

        long result = db.delete("Categories", "id =?", new String[]{String.valueOf(id)});

        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public void insertLevels(String title) {

        db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("title", title);
        long result = db.insert("Levels", null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "New level create!", Toast.LENGTH_SHORT).show();
        }

        db.close();

    }


    public List<Levels> readLevels() {
        String query = "SELECT * FROM Levels;";
        db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        List<Levels> levelsList = new ArrayList<>();
        if (cursor == null) return levelsList;
        if (cursor.moveToFirst()) {
            do {
                Levels level = new Levels();

                level.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                level.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                levelsList.add(level);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return levelsList;

    }

    public void updateLevels(int id,String title) {

        db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("title", title);
        long result = db.update("Levels",cv,"id = ?",new String[]{String.valueOf(id)});
        if (result == -1) {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Level update!", Toast.LENGTH_SHORT).show();
        }

        db.close();

    }

    public void deleteLevels(int id) {
        db = this.getWritableDatabase();

        long result = db.delete("Levels", "id =?", new String[]{String.valueOf(id)});

        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }


    public void insertCourses(String title, String img,String date,String color,String text,int category_id,int level_id) {

        db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("img", img);
        cv.put("date", date);
        cv.put("color", color);
        cv.put("text", text);
        cv.put("category_id", category_id);
        cv.put("level_id", level_id);

        long result = db.insert("Courses", null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "New course create!", Toast.LENGTH_SHORT).show();
        }

        db.close();

    }

    public List<Course> readCourses() {
        String query = "SELECT Courses.id as id, img, Courses.title as title, date, color, text, category_id, level_id, Levels.title as level FROM Courses join Levels on level_id = Levels.id;";
        db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        List<Course> coursesList = new ArrayList<>();
        if (cursor == null) return coursesList;
        if (cursor.moveToFirst()) {
            do {
                Course course = new Course();

                course.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                course.setImg(cursor.getString(cursor.getColumnIndexOrThrow("img")));
                course.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                course.setColor(cursor.getString(cursor.getColumnIndexOrThrow("color")));
                course.setText(cursor.getString(cursor.getColumnIndexOrThrow("text")));
                course.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
                course.setLevel(cursor.getString(cursor.getColumnIndexOrThrow("level")));
                course.setCategoryId(cursor.getInt(cursor.getColumnIndexOrThrow("category_id")));
                course.setLevelId(cursor.getInt(cursor.getColumnIndexOrThrow("level_id")));
                coursesList.add(course);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return coursesList;

    }

    public void updateCourses(int id,String title, String img,String date,String color,String text,int category_id,int level_id) {

        db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("img", img);
        cv.put("date", date);
        cv.put("color", color);
        cv.put("text", text);
        cv.put("category_id", category_id);
        cv.put("level_id", level_id);

        long result = db.update("Courses",cv,"id = ?",new String[]{String.valueOf(id)});
        if (result == -1) {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Course update!", Toast.LENGTH_SHORT).show();
        }

        db.close();

    }

    public void deleteCourses(int id) {
        db = this.getWritableDatabase();

        long result = db.delete("Courses", "id =?", new String[]{String.valueOf(id)});

        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public void insertCart(int id) {

        db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("courses_id", id);
        long result = db.insert("Cart", null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Course added!", Toast.LENGTH_SHORT).show();
        }

        db.close();

    }

    public List<Cart> readCart() {
        String query = "SELECT * FROM Cart;";
        db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        List<Cart> cartList = new ArrayList<>();
        if (cursor == null) return cartList;
        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();

                cart.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                cartList.add(cart);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return cartList;

    }

    public void deleteCart(int id) {
        db = this.getWritableDatabase();

        long result = db.delete("Cart", "courses_id =?", new String[]{String.valueOf(id)});

        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

}
