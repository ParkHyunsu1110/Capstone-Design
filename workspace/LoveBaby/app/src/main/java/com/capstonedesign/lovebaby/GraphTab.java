package com.capstonedesign.lovebaby;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class GraphTab extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphtab);

        TabHost tabhost = getTabHost();
        TabHost.TabSpec spec;

        //첫 번째 탭
        Intent intent = new Intent().setClass(this, Input.class);
        spec = tabhost.newTabSpec("Input").setIndicator("입력").setContent(intent);
        tabhost.addTab(spec);

        //두 번째 탭
        intent = new Intent().setClass(this, Graph.class);
        spec = tabhost.newTabSpec("Graph").setIndicator("그래프").setContent(intent);
        tabhost.addTab(spec);

        //세 번째 탭
        intent = new Intent().setClass(this, Table.class);
        spec = tabhost.newTabSpec("Table.").setIndicator("표").setContent(intent);
        tabhost.addTab(spec);

        //처음 앱 실행시 탭 활성화 지정
        tabhost.setCurrentTab(0);
    }
}