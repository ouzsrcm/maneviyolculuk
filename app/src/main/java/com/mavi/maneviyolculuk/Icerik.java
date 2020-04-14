package com.mavi.maneviyolculuk;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class Icerik extends Activity {
	private String htmlText;
	AlertDialog levelDialog;
	String str = "";
	int yazi_boyut = 0;
	String yaziBoyutuStr;
	String[] valuesFromXML;
	int yaziBoyutuForMenuItem;
	////////////////////////////////
	
	final static float STEP = 70;
	private TextView mtxtRatio1, textview;
	float mRatio = 1.0f;
	int mBaseDist;
	float mBaseRatio;
	float fontsize = 13;
	
	////////////////////////////////
	final Context context = this;

	

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		mtxtRatio1.setTextSize(mRatio + 13);


	}

		public boolean onTouchEvent(MotionEvent event) {
			
			//max de�er atama
		
			if(mtxtRatio1.getTextSize()<=30){
				if (event.getPointerCount() == 2) {
					int action = event.getAction();
					int pureaction = action & MotionEvent.ACTION_MASK;
					if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
						mBaseDist = getDistance(event);
						mBaseRatio = mRatio;
					} else {
						float delta = (getDistance(event) - mBaseDist) / STEP;
						float multi = (float) Math.pow(2, delta);
						mRatio = Math.min(1024.0f, Math.max(0.1f, mBaseRatio * multi));
						mtxtRatio1.setTextSize(mRatio + 13);
					}	
			}
		

		}

		return true;

	}

	int getDistance(MotionEvent event) {
		int dx = (int) (event.getX(0) - event.getX(1));
		int dy = (int) (event.getY(0) - event.getY(1));
		return (int) (Math.sqrt(dx * dx + dy * dy));
	}

	public boolean onTouch(View v, MotionEvent event) {

		return false;
	}
	
}
