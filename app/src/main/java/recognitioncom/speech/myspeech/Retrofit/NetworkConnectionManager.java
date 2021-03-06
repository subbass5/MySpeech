package recognitioncom.speech.myspeech.Retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import recognitioncom.speech.myspeech.MainActivity;
import recognitioncom.speech.myspeech.Pojo.CategoriesRes;
import recognitioncom.speech.myspeech.Pojo.LoginRes;
import recognitioncom.speech.myspeech.Pojo.QuestionRes;
import recognitioncom.speech.myspeech.Pojo.RegisterRes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkConnectionManager {
    private String TAG = "<NetworkConnectionManager>";
    public NetworkConnectionManager(){

    }

    public void callLogin(final CallbackLoginListener listener, String usr){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService git = retrofit.create(APIService.class);
        Call call = git.logIn(usr);


        call.enqueue(new Callback<LoginRes>() {

            @Override
            public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                System.out.println(response.code());
                try {

                    LoginRes loginRes = (LoginRes) response.body();

                    if (response.code() != 200) {
//                        Log.e("Network connected","Response code = "+response.code());

                        ResponseBody responseBody = response.errorBody();

                        if (responseBody != null) {
                            listener.onBodyError(responseBody);
                        } else if (responseBody == null) {
                            listener.onBodyErrorIsNull();
                        }

                    } else {
                        listener.onResponse(loginRes);
                    }


                }catch (Exception e){
                    listener.onFailure(e);
                }
            }
            @Override
            public void onFailure(Call<LoginRes> call, Throwable t) {

                listener.onFailure(t);

            }


        });



    }

    public void callRegister(final CallbackRegisterListener listener, String usr, String pwd,String numChk){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService git = retrofit.create(APIService.class);
        Call call = git.register(usr,pwd,numChk);


        call.enqueue(new Callback<RegisterRes>() {

            @Override
            public void onResponse(Call<RegisterRes> call, Response<RegisterRes> response) {

                try {

                    RegisterRes loginRes = (RegisterRes) response.body();

                    if (response.code() != 200) {
//                        Log.e("Network connected","Response code = "+response.code());

                        ResponseBody responseBody = response.errorBody();

                        if (responseBody != null) {
                            listener.onBodyError(responseBody);
                        } else if (responseBody == null) {
                            listener.onBodyErrorIsNull();
                        }

                    } else {
                        listener.onResponse(loginRes);
                    }


                }catch (Exception e){
                    listener.onFailure(e);
                }
            }
            @Override
            public void onFailure(Call<RegisterRes> call, Throwable t) {

                listener.onFailure(t);

            }


        });



    }

    public void callCategories(final CallbackCategoriesListener listener){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService git = retrofit.create(APIService.class);
        Call call = git.getCategory();


        call.enqueue(new Callback<List<CategoriesRes>>() {

            @Override
            public void onResponse(Call<List<CategoriesRes>> call, Response<List<CategoriesRes>> response) {

                try {

                    List<CategoriesRes> categoriesRes =  response.body();

                    if (response.code() != 200) {
//                        Log.e("Network connected","Response code = "+response.code());

                        ResponseBody responseBody = response.errorBody();

                        if (responseBody != null) {
                            listener.onBodyError(responseBody);
                        } else if (responseBody == null) {
                            listener.onBodyErrorIsNull();
                        }

                    } else {
                        listener.onResponse(categoriesRes);
                    }


                }catch (Exception e){
                    listener.onFailure(e);
                }
            }
            @Override
            public void onFailure(Call<List<CategoriesRes>> call, Throwable t) {

                listener.onFailure(t);

            }
            
        });



    }
    public void callQuestion(final CallbackQuestionListenner listener,String category){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService git = retrofit.create(APIService.class);
        Call call = git.justPlay(category);


        call.enqueue(new Callback<List<QuestionRes>>() {

            @Override
            public void onResponse(Call<List<QuestionRes>> call, Response<List<QuestionRes>> response) {

                try {

                    List<QuestionRes> categoriesRes =  response.body();

                    if (response.code() != 200) {
//                        Log.e("Network connected","Response code = "+response.code());

                        ResponseBody responseBody = response.errorBody();

                        if (responseBody != null) {
                            listener.onBodyError(responseBody);
                        } else if (responseBody == null) {
                            listener.onBodyErrorIsNull();
                        }

                    } else {
                        listener.onResponse(categoriesRes);
                    }


                }catch (Exception e){
                    listener.onFailure(e);
                }
            }
            @Override
            public void onFailure(Call<List<QuestionRes>> call, Throwable t) {

                listener.onFailure(t);

            }


        });



    }
}
