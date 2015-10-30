package com.lovejoy777.showcase.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonWriter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.app.AlertDialog;

import com.lovejoy777.showcase.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class SubmitActivity extends AppCompatActivity {
    public final int[] EditText = {R.id.title, R.id.description, R.id.author, R.id.link,
            R.id.backup_link, R.id.icon, R.id.promo, R.id.screenshot_1, R.id.screenshot_2, R.id.screenshot_3,
            R.id.googleplus, R.id.version, R.id.donate_link, R.id.donate_version, R.id.wallpaper, R.id.plugin_version,
            R.id.toolbar_background_color};
    public final int[] CheckBox = {R.id.for_L, R.id.for_M, R.id.basic, R.id.basic_m,
            R.id.type2, R.id.type3, R.id.type3_m, R.id.touchwiz, R.id.lg, R.id.sense,
            R.id.xperia, R.id.zenui, R.id.hdpi, R.id.mdpi, R.id.xhdpi, R.id.xxhdpi, R.id.xxxhdpi, R.id.free,
            R.id.donate, R.id.paid, R.id.needs_update, R.id.will_update, R.id.bootani, R.id.font, R.id.iconpack};
    Button generate;
    String[] strings = new String[]{"title", "description", "author", "link", "backup_link", "icon",
            "promo", "screenshot_1", "screenshot_2", "screenshot_3", "googleplus", "version", "donate_link",
            "donate_version", "wallpaper", "plugin_version", "toolbar_background_color", "for_L", "for_M",
            "basic", "basic_m", "type2", "type3", "type3_m", "touchwiz", "lg", "sense", "xperia", "zenui",
            "hdpi", "mdpi", "xhdpi", "xxhdpi", "xxxhdpi", "free", "donate", "paid", "needs_update", "will_update",
            "bootani", "font", "iconpack"};
    ArrayList<String> values = new ArrayList<String>(Arrays.asList(strings));
    ArrayList<String> values2 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(toolbar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        generate = (Button) findViewById(R.id.generate);
        Bundle extras = getIntent().getExtras();
        if (Build.VERSION.SDK_INT >= 23) {
            int hasWriteStorage = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWriteStorage != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    public void send(View v) {
        values2.clear();
        for (int id : EditText) {
            EditText t = (EditText) findViewById(id);
            values2.add((t.getText().toString().trim().length() > 0) ? t.getText().toString() : "false");
        }
        for (int id2 : CheckBox) {
            CheckBox c = (CheckBox) findViewById(id2);
            values2.add(String.valueOf(c.isChecked()));
        }
        String title = values2.get(0);
        title = title.replaceAll(" ", "_");
        String title2 = title;
        values2.set(0, title2);
        File file = new File(Environment.getExternalStorageDirectory(), title2 + ".json");
        try {
            FileOutputStream out = new FileOutputStream(file);
            writeJson(out);
            View coordinatorLayoutView = findViewById(R.id.snackbar);
            Snackbar snack = Snackbar.make(coordinatorLayoutView, "Created /sdcard/" + title2 + ".json", Snackbar.LENGTH_LONG);
            View view = snack.getView();
            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            view.setBackgroundColor(Color.parseColor("#F44336"));
            snack.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeJson(OutputStream out) throws IOException {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.setIndent("    ");
        jsonFinal(writer);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Permissions", "Permission Granted: " + permissions[0]);
                } else {
                    AlertDialog.Builder noPermissionDialog = new AlertDialog.Builder(this);
                    noPermissionDialog.setTitle(R.string.noPermission);
                    noPermissionDialog.setMessage(R.string.noPermissionDescription);
                    noPermissionDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    });
                    noPermissionDialog.show();
                }
                return;
            }
        }
    }

    public void jsonFinal(JsonWriter writer) throws IOException {
        writer.beginObject();
        for (int i = 0; i < values.size(); i++) {
            String json1 = values.get(i);
            String json2 = values2.get(i);
            writer.name(json1).value(json2);
        }
        writer.endObject();
        writer.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.back2, R.anim.back1);
    }
}