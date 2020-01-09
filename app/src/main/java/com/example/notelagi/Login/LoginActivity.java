package com.example.notelagi.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notelagi.MainActivity;
import com.example.notelagi.R;
import com.example.notelagi.UtilsApi.BaseApiService;
import com.example.notelagi.UtilsApi.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText etnik;
    EditText etpassword;
    String nik, pass;
    ProgressDialog loading;
    Context mcontext;
    BaseApiService mapiservice;
    Button loginbutton;
    SharedPrefrenceManager sharedPrefrenceManager;

    private String TAG = LoginActivity.class.getSimpleName();
    ArrayList<LoginModel> loginModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etnik = findViewById(R.id.et_nik);
        etpassword = findViewById(R.id.et_password);
        loginbutton = findViewById(R.id.btn_login);
        sharedPrefrenceManager = new SharedPrefrenceManager(this);
        if (sharedPrefrenceManager.getsp_sudahlog()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }


        mcontext = this;
//        mapiservice = UtilsApi.getAPIBaseApiService();


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nik = etnik.getText().toString();
                pass = etpassword.getText().toString();
                if (nik.isEmpty()|pass.isEmpty()){
                    Toast.makeText(LoginActivity.this, "silahkan isi data",Toast.LENGTH_SHORT).show();
                }else {
                    getLogin(nik,pass);
                }
            }
        });

    }

    public  void getLogin(String nik, final String pass){
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        BaseApiService service = UtilsApi.getRetrofitInstance().create(BaseApiService.class);
        Call<ResponseBody> call = service.loginRequest(nik, pass,"123456");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String respon = response.body().string();
                        JSONObject jsonObj = new JSONObject(respon);
                        JSONObject c = jsonObj.getJSONObject("data");

                        Integer id = c.getInt("id");
                        String photo = c.getString("photo");
                        String nik = c.getString("nik");
                        String name = c.getString("name");
                        String password = c.getString("password");
                        String real_password = c.getString("real_password");
                        String latitude = c.getString("latitude");
                        String longitude = c.getString("longitude");
                        String created_at = c.getString("created_at");
                        String regid = c.getString("regid");
                        Integer status_login = c.getInt("status_login");
                        String last_login = c.getString("last_login");
                        String last_sincron = c.getString("last_sincron");
                        Object app_ver = c.getString("app_ver");

                        LoginModel p = new LoginModel();
                        p.setId(id);
                        p.setPhoto(photo);
                        p.setNik(nik);
                        p.setName(name);
                        p.setPassword(password);
                        p.setRealPassword(real_password);
                        p.setLatitude(latitude);
                        p.setLongitude(longitude);
                        p.setCreatedAt(created_at);
                        p.setRegid(regid);
                        p.setStatusLogin(status_login);
                        p.setLastLogin(last_login);
                        p.setLastSincron(last_sincron);
                        p.setAppVer(app_ver);
                        loginModels.add(p);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                        sharedPrefrenceManager.saveSpBoolean(SharedPrefrenceManager.sp_sudahlog, true);

                    } catch (JSONException e) {
                        Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(LoginActivity.this, "isi data", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "Souldn't get json from server.1");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void
                        run() {
                            Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LoCat for possible errors!1",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}
