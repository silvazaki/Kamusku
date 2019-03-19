package com.example.kamusku.ui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.kamusku.R;
import com.example.kamusku.data.database.KamusHelper;
import com.example.kamusku.data.model.Data;
import com.example.kamusku.data.prefs.AppPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PreloadActivity extends AppCompatActivity {

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preload);
        progressBar = findViewById(R.id.progress_bar);

        new LoadData().execute();
    }

    /*
    Asynctask untuk menjalankan preload data
     */
    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        KamusHelper kamusHelper;
        AppPreference appPreference;
        double progress;
        double maxprogress = 100;

        /*
        Persiapan sebelum proses dimulai
        Berjalan di Main Thread
         */
        @Override
        protected void onPreExecute() {

            kamusHelper = new KamusHelper(PreloadActivity.this);
            appPreference = new AppPreference(PreloadActivity.this);
        }

        /*
        Proses background terjadi di method doInBackground
         */
        @Override
        protected Void doInBackground(Void... params) {

            /*
            Panggil preference first run
             */
            Boolean firstRun = appPreference.getFirstRun();

            /*
            Jika first run true maka melakukan proses pre load,
            jika first run false maka akan langsung menuju home
             */
            if (firstRun) {
                /*
                Load raw kamusdari file txt ke dalam array kamusIndo
                Load raw kamusdari file txt ke dalam array kamusEnglish
                 */
                ArrayList<Data> kamusIndo = preLoadRaw(R.raw.indonesia_english);
                ArrayList<Data> kamusEnglish = preLoadRaw(R.raw.english_indonesia);

                kamusHelper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / kamusIndo.size()*2;

                kamusHelper.beginTransaction();

                try {
                    for (int i = 0; i < kamusIndo.size(); i++) {
                        Data indo = kamusIndo.get(i);
                        Data english = kamusEnglish.get(i);
                        kamusHelper.insertIndoEnglish(indo);
                        kamusHelper.insertEnglishIndo(english);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }

                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception");
                }

                // Jika semua proses telah di set success maka akan di commit ke database
                kamusHelper.setTransactionSuccess();
                kamusHelper.endTransaction();

                // Close helper ketika proses query sudah selesai
                kamusHelper.close();
                appPreference.setFirstRun(false);

                publishProgress((int) maxprogress);

            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);

                        this.wait(2000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }

        //Update prosesnya
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(PreloadActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<Data> preLoadRaw(int dataKamus) {
        ArrayList<Data> list = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(dataKamus);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                Data data;

                data = new Data(splitstr[0], splitstr[1]);
                list.add(data);
                count++;
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
