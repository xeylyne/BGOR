package com.example.xeylyne.project.Activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.xeylyne.project.Api.ApiPackage;
import com.example.xeylyne.project.ListData.ListGOR;
import com.example.xeylyne.project.ListData.ListInsertGOR;
import com.example.xeylyne.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateDataGORActivity extends AppCompatActivity {

    @BindView(R.id.nama_gor)
    AutoCompleteTextView namagor;

    @BindView(R.id.alt)
    AutoCompleteTextView alamatgor;

    @BindView(R.id.jam_buka)
    AutoCompleteTextView jambuka;

    @BindView(R.id.jam_tutup)
    AutoCompleteTextView jamtutup;

    String id_gor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data_gor);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        id_gor = intent.getStringExtra("id_gor");

    }

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://irvanlyne.000webhostapp.com/")
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();

    ApiPackage apiPackage= retrofit.create(ApiPackage.class);

    private void saveData() {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);
        dialog.show();

        String nama_gor = namagor.getText().toString();
        String alamat_gor = alamatgor.getText().toString();
        String jam_buka = jambuka.getText().toString();
        String jam_tutup = jamtutup.getText().toString();

        Call<ListGOR> call = apiPackage.update_data(id_gor,nama_gor,alamat_gor,jam_buka,jam_tutup);

        call.enqueue(new Callback<ListGOR>() {
            @Override
            public void onResponse(Call<ListGOR> call, Response<ListGOR> response) {
                if (response.isSuccessful()) {
                    dialog.hide();
                    Toast.makeText(UpdateDataGORActivity.this, "Data Berhasil di Update", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.hide();
                    Toast.makeText(UpdateDataGORActivity.this, "Try it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListGOR> call, Throwable t) {
                dialog.hide();
                Toast.makeText(UpdateDataGORActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.tambah1)
    public void registerUserToko() {
        if(namagor.getText().toString().length() == 0){
            namagor.setError("Masukkan Nama GOR");
        } else if (alamatgor.getText().toString().length() == 0) {
            alamatgor.setError("Masukkan Alamat GOR");
        } else if (jambuka.getText().toString().length() == 0) {
            jambuka.setError("Masukkan Jam Buka");
        } else if (jamtutup.getText().toString().length() == 0){
            jamtutup.setError("Masukkan Jam Tutup");
        } else {
            saveData();
        }
    }

}
