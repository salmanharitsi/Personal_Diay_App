package id.co.salmanharitsi.personaldiary.async;

import android.os.AsyncTask;

import id.co.salmanharitsi.personaldiary.database.DiaryDao;
import id.co.salmanharitsi.personaldiary.models.Diary;

public class DeleteAsyncTask extends AsyncTask<Diary, Void, Void> {

    private static final String TAG = "DeleteAsyncTask";
    private DiaryDao mDiaryDao;

    public DeleteAsyncTask (DiaryDao dao){
        mDiaryDao = dao;
    }

    @Override
    protected Void doInBackground(Diary... diaries) {
        mDiaryDao.delete(diaries);
        return null;
    }
}
