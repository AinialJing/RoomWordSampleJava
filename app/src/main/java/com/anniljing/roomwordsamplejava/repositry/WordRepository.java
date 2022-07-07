package com.anniljing.roomwordsamplejava.repositry;

import android.app.Application;

import com.anniljing.roomwordsamplejava.dao.WordDao;
import com.anniljing.roomwordsamplejava.dataBase.WordRoomDatabase;
import com.anniljing.roomwordsamplejava.entity.Word;

import java.util.List;

import androidx.lifecycle.LiveData;

public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    public WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Word word) {
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDao.insert(word);
        });
    }
}
