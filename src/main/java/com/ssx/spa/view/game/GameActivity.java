package com.ssx.spa.view.game;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import com.ssx.spa.R;
import com.ssx.spa.common.Myapplication;
import com.ssx.spa.javabean.App;
import com.ssx.spa.javabean.Infos;
import com.ssx.spa.util.AppUtil;
import com.ssx.spa.util.WebInstaller;
import com.ssx.spa.view.BaseActivity;
import com.ssx.spa.view.adapter.GameAdapter;
import java.util.List;

public class GameActivity extends BaseActivity implements OnTouchListener {
    private final int MAXSIZE = 8;
    private ImageButton back;
    private int current;
    private List<Infos> game;
    private GridView game_grid;
    private TextView game_top;
    private Myapplication myapplication;
    String packageName;
    private int position;
    private ImageButton turn_letf;
    private ImageButton turn_right;
    float x1 = 0.0f;
    float x2 = 0.0f;
    float y1 = 0.0f;
    float y2 = 0.0f;

    class Back implements OnClickListener {
        Back() {
        }

        public void onClick(View v) {
            GameActivity.this.finish();
        }
    }

    class Turn implements OnClickListener {
        Turn() {
        }

        public void onClick(View v) {
            GameActivity gameActivity;
            switch (v.getId()) {
                case R.id.turn_letf:
                    if (GameActivity.this.current > 0) {
                        gameActivity = GameActivity.this;
                        gameActivity.current = gameActivity.current - 1;
                        break;
                    }
                    break;
                case R.id.turn_right:
                    if (GameActivity.this.current < ((int) Math.ceil(((double) GameActivity.this.game.size()) / 8.0d)) - 1) {
                        gameActivity = GameActivity.this;
                        gameActivity.current = gameActivity.current + 1;
                        break;
                    }
                    break;
            }
            GameActivity.this.setvalue();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.myapplication = (Myapplication) getApplication();
        this.position = getIntent().getIntExtra("position", this.position);
        initview();
        setvalue();
    }

    private void setListener() {
        this.game_grid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                GameActivity.this.checkisinstall((Infos) GameActivity.this.game.get((GameActivity.this.current * 8) + position));
            }
        });
    }

    private void setvalue() {
        try {
            if ((this.current + 1) * 8 > this.game.size()) {
                this.game_grid.setAdapter(new GameAdapter(this, this.game.subList(this.current * 8, this.game.size())));
            } else {
                this.game_grid.setAdapter(new GameAdapter(this, this.game.subList(this.current * 8, (this.current + 1) * 8)));
            }
        } catch (Exception e) {
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == 2) {
            super.onTouchEvent(event);
            return true;
        }
        onTouchEvent(event);
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 0) {
            this.x1 = event.getX();
            this.y1 = event.getY();
        }
        if (event.getAction() == 1) {
            this.x2 = event.getX();
            this.y2 = event.getY();
            if (this.y1 - this.y2 <= 50.0f && this.y2 - this.y1 <= 50.0f) {
                if (this.x1 - this.x2 > 50.0f) {
                    if (this.current < ((int) Math.ceil(((double) this.game.size()) / 8.0d)) - 1) {
                        this.current++;
                    }
                    setvalue();
                } else if (this.x2 - this.x1 > 50.0f) {
                    if (this.current > 0) {
                        this.current--;
                    }
                    setvalue();
                }
            }
        }
        if (event.getAction() != 2) {
            return super.onTouchEvent(event);
        }
        super.onTouchEvent(event);
        return true;
    }

    private void initview() {
        this.game_top = (TextView) findViewById(R.id.game_top);
        this.game_grid = (GridView) findViewById(R.id.game_grid);
        this.game_grid.setOnTouchListener(this);
        this.back = (ImageButton) findViewById(R.id.back);
        this.back.setVisibility(0);
        this.back.setOnClickListener(new Back());
        this.turn_letf = (ImageButton) findViewById(R.id.turn_letf);
        this.turn_right = (ImageButton) findViewById(R.id.turn_right);
        this.turn_letf.setOnClickListener(new Turn());
        this.turn_right.setOnClickListener(new Turn());
        setListener();
        try {
            this.game = ((App) this.myapplication.getApps().get(this.position)).getInfos();
            this.game_top.setText("首页/安卓应用/" + ((App) this.myapplication.getApps().get(this.position)).getName());
        } catch (Exception e) {
        }
    }

    private void checkisinstall(final Infos apps) {
        if (AppUtil.checkAppIsExist(this, apps.getPackageName())) {
            BaseActivity.finishAll();
            AppUtil.startApp(this, apps.getPackageName());
            return;
        }
        new Builder(this).setTitle("警告").setMessage("该应用尚未安装,是否要下载安装?").setPositiveButton("是", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                new WebInstaller(GameActivity.this, apps.getPath()).downloadAndInstall();
            }
        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }
}
