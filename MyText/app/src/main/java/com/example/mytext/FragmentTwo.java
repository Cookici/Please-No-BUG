package com.example.mytext;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentTwo extends Fragment {
    private Button btn_save;
    private View view;
    private Button btn_get;
    private Button btn_edit;
    private TextView et_content;
    private TextView tv_date;
    private EditText et_mood;
    private TextView tv_week;
    private TextView tv_weather;
    private static String TAG = "nice";
    private int i = 0;
    private String savepath;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);





        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "成功保存", Toast.LENGTH_SHORT).show();

                et_content.setFocusable(false);//不可编辑
                et_content.setFocusableInTouchMode(false);//不可编辑
                et_mood.setFocusable(false);//不可编辑
                et_mood.setFocusableInTouchMode(false);//不可编辑

                getPermissionCamera(getActivity());
                popShotSrceenDialog();


                }


        });



        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "开始编辑你的日记吧:)", Toast.LENGTH_SHORT).show();
                et_content.setFocusable(true);//不可编辑
                et_content.setFocusableInTouchMode(true);//不可编辑
            }
        });


        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGetNetRequest("http://timor.tech/api/holiday/tts/tomorrow");
                sendGetNetRequest2("http://www.weather.com.cn/data/cityinfo/101042900.html");
                sendGetNetRequest1("http://api.k780.com:88/?app=life.time&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json");
                Toast.makeText(getActivity(), "今天的日记生成了哦:)  记得编辑心情哦", Toast.LENGTH_SHORT).show();
                et_content.setFocusable(false);//不可编辑
                et_content.setFocusableInTouchMode(false);//不可编辑
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_two, container,false);

        btn_get = (Button) view.findViewById(R.id.btn_get);
        btn_edit = (Button) view.findViewById(R.id.btn_edit);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        et_content = (EditText) view.findViewById(R.id.et_content);
        tv_week = (TextView) view.findViewById(R.id.tv_week);
        tv_weather = (TextView) view.findViewById(R.id.tv_weather);
        et_mood = (EditText) view.findViewById(R.id.et_mood);
        btn_save = (Button) view.findViewById(R.id.btn_save);

        return view;

    }




    private void sendGetNetRequest(String mUrl) {

        new Thread(
                () -> {
                    try {
                        URL url = new URL(mUrl);
                        HttpURLConnection connection = (HttpURLConnection)
                                url.openConnection();
                        connection.setRequestMethod("GET");//设置请求方式为GET
                        connection.setConnectTimeout(8000);//设置最大连接时间，单位为ms
                        connection.setReadTimeout(8000);//设置最大的读取时间，单位为ms
                        connection.setRequestProperty("Accept-Language", "zhCN,zh;q=0.9");
                        connection.setRequestProperty("AcceptEncoding", "gzip,deflate");
                        connection.connect();//正式连接
                        InputStream in = connection.getInputStream();//从接口处获取
                        String responseData = StreamToString(in);//这里就是服务器返回的
                        jsonDecodeTest(responseData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        ).start();

    }


    private void jsonDecodeTest(String responseData) {

        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String content = jsonObject.getString("tts");
            et_content.setText("\t\t"+content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private String StreamToString(InputStream in) {
        StringBuilder sb = new StringBuilder();//新建一个StringBuilder，用于一点一点
        String oneLine;//流转换为字符串的一行
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));//
        try {
            while ((oneLine = reader.readLine()) != null) {//readLine方法将读取一行
                sb.append(oneLine).append('\n');//拼接字符串并且增加换行，提高可读性
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();//关闭InputStream
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();//将拼接好的字符串返回出去
    }


    private void sendGetNetRequest1(String mUrl) {

        new Thread(
                () -> {
                    try {
                        URL url = new URL(mUrl);
                        HttpURLConnection connection = (HttpURLConnection)
                                url.openConnection();
                        connection.setRequestMethod("GET");//设置请求方式为GET
                        connection.setConnectTimeout(8000);//设置最大连接时间，单位为ms
                        connection.setReadTimeout(8000);//设置最大的读取时间，单位为ms
                        connection.setRequestProperty("Accept-Language", "zhCN,zh;q=0.9");
                        connection.setRequestProperty("AcceptEncoding", "gzip,deflate");
                        connection.connect();//正式连接
                        InputStream in = connection.getInputStream();//从接口处获取
                        String responseData = StreamToString(in);//这里就是服务器返回的
                        jsonDecodeTest1(responseData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        ).start();

    }


    private void jsonDecodeTest1(String responseData) {

        try {
            JSONObject jsonObject = new JSONObject(responseData);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String date = jsonResult.getString("datetime_1");
            tv_date.setText(date);
            String week = jsonResult.getString("week_2");
            tv_week.setText(week);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void sendGetNetRequest2(String mUrl) {

        new Thread(
                () -> {
                    try {
                        URL url = new URL(mUrl);
                        HttpURLConnection connection = (HttpURLConnection)
                                url.openConnection();
                        connection.setRequestMethod("GET");//设置请求方式为GET
                        connection.setConnectTimeout(8000);//设置最大连接时间，单位为ms
                        connection.setReadTimeout(8000);//设置最大的读取时间，单位为ms
                        connection.setRequestProperty("Accept-Language", "zhCN,zh;q=0.9");
                        connection.setRequestProperty("AcceptEncoding", "gzip,deflate");
                        connection.connect();//正式连接
                        InputStream in = connection.getInputStream();//从接口处获取
                        String responseData = StreamToString(in);//这里就是服务器返回的
                        jsonDecodeTest2(responseData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        ).start();

    }

    private void jsonDecodeTest2(String responseData) {

        try {
            JSONObject jsonObject = new JSONObject(responseData);
            JSONObject jsonWeather= jsonObject.getJSONObject("weatherinfo");
            String weather = jsonWeather.getString("weather");
            tv_weather.setText("璧山今天的天气是:"+weather);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //截屏需要的权限 储存权限 相机权限
    public static boolean getPermissionCamera(Activity activity) {
        int cameraPermissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int readPermissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePermissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED
                || readPermissionCheck != PackageManager.PERMISSION_GRANTED
                || writePermissionCheck != PackageManager.PERMISSION_GRANTED) {

            String[] permissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(activity, permissions, 0);

            return false;
        } else {
            return true;
        }
    }


    private void popShotSrceenDialog() {
        final AlertDialog cutDialog = new AlertDialog.Builder(getContext()).create();
        View dialogView = View.inflate(getContext(), R.layout.show_cut_screen_layout, null);
        ImageView showImg = (ImageView) dialogView.findViewById(R.id.show_cut_screen_img);
        //获取当前屏幕的大小
        int width = getActivity().getWindow().getDecorView().getRootView().getWidth();
        int height = getActivity().getWindow().getDecorView().getRootView().getHeight();
        //生成相同大小的图片
        Bitmap temBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //找到当前页面的根布局,可以直接使用某个view的，截取的就是当前View的内容
        View view = getActivity().getWindow().getDecorView().getRootView();//当前窗口布局
        //设置缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //从缓存中获取当前屏幕的图片
        temBitmap = view.getDrawingCache().copy(Bitmap.Config.ARGB_8888, false);
        showImg.setImageBitmap(temBitmap);
        saveImageToGallery2(getContext(),temBitmap);
        //销毁当前屏幕图片的缓存
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);

        cutDialog.setView(dialogView);
        Window window = cutDialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的0.6
        p.gravity = Gravity.CENTER;//设置弹出框位置
        window.setAttributes(p);
        cutDialog.show();
        Toast.makeText(getContext(), "图片已保存至相册", Toast.LENGTH_SHORT).show();
        //使用定时器，弹窗1秒后关闭，达到系统截屏效果
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                cutDialog.dismiss();
                timer.cancel();
            }
        }, 1000);
    }



    public static void saveImageToGallery2(Context context, Bitmap image){
        Long mImageTime = System.currentTimeMillis();
        String imageDate = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date(mImageTime));
        String SCREENSHOT_FILE_NAME_TEMPLATE = "winetalk_%s.png";//图片名称，以"winetalk"+时间戳命名
        String mImageFileName = String.format(SCREENSHOT_FILE_NAME_TEMPLATE, imageDate);

        final ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES
                + File.separator + "winetalk"); //Environment.DIRECTORY_SCREENSHOTS:截图,图库中显示的文件夹名。"dh"
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, mImageFileName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
        values.put(MediaStore.MediaColumns.DATE_ADDED, mImageTime / 1000);
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, mImageTime / 1000);
        values.put(MediaStore.MediaColumns.DATE_EXPIRES, (mImageTime + DateUtils.DAY_IN_MILLIS) / 1000);
        values.put(MediaStore.MediaColumns.IS_PENDING, 1);

        ContentResolver resolver = context.getContentResolver();
        final Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        try {


            try (OutputStream out = resolver.openOutputStream(uri)) {
                if (!image.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                    throw new IOException("Failed to compress");
                }
            }


            values.clear();
            values.put(MediaStore.MediaColumns.IS_PENDING, 0);
            values.putNull(MediaStore.MediaColumns.DATE_EXPIRES);
            resolver.update(uri, values, null, null);
        }catch (IOException e){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                resolver.delete(uri, null);
            }
        }
    }

}






