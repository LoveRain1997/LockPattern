
package com.xbting.lp.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.xbting.lp.R;
import com.xbting.lp.widget.LockPatternPreView;
import com.xbting.lp.widget.LockPatternView;
import com.xbting.lp.widget.LockPatternView.Cell;
import com.xbting.lp.widget.LockPatternView.DisplayMode;
import com.xbting.lp.widget.LockPatternView.OnPatternListener;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * @author xbting
 * @version：
 */
public class LockPatternActivity extends Activity{
	LockPatternView mLockPatternView;
	LockPatternPreView mPreviewView;
	EditText mEditText;
	Button submitBtn;
	private String userName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		mPreviewView = (LockPatternPreView) findViewById(R.id.lock_preview);
		mLockPatternView = (LockPatternView) findViewById(R.id.lock_pattern_view);
		mEditText = (EditText) findViewById(R.id.account_edt);
		submitBtn = (Button) findViewById(R.id.submit_btn);
		submitBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userName = mEditText.getText().toString();
				SharedPreferences sp = LockPatternActivity.this.getSharedPreferences("lock_pattern", 0);
				String strp = sp.getString("u_"+userName, "");
				Log.i("TAG", "string pattern:"+strp);
				List<Cell> list = LockPatternView.stringToPattern(strp);
				mLockPatternView.setPattern(DisplayMode.Animate, list);
			}
		});
		List<LockPatternView.Cell> mAnimatePattern = new ArrayList<LockPatternView.Cell>();
		
		// 初始化演示动画
		mAnimatePattern.add(LockPatternView.Cell.of(0, 0));
		mAnimatePattern.add(LockPatternView.Cell.of(0, 2));
		mAnimatePattern.add(LockPatternView.Cell.of(1, 1));
		mAnimatePattern.add(LockPatternView.Cell.of(2, 2));
		mAnimatePattern.add(LockPatternView.Cell.of(2, 0));
		mAnimatePattern.add(LockPatternView.Cell.of(1, 0));
		mAnimatePattern.add(LockPatternView.Cell.of(0, 1));
		mAnimatePattern.add(LockPatternView.Cell.of(2, 1));
		mAnimatePattern.add(LockPatternView.Cell.of(1, 2));
//		mLockPatternView.setPattern(DisplayMode.Animate, mAnimatePattern);
		mLockPatternView.setTactileFeedbackEnabled(true);
		mLockPatternView.setOnPatternListener(new OnPatternListener() {
			
			@Override
			public void onPatternStart() {
				// TODO Auto-generated method stub
				Log.d("TAG", "onPatternStart-----");
			}
			
			@Override
			public void onPatternDetected(List<Cell> pattern) {
				// TODO Auto-generated method stub
				Log.d("TAG", "onPatternDetected-----");
				Log.i("TAG", "onPatternDetected---最终图案结果："+pattern.toString());
				Log.e("TAG", "onPatternDetected---patternToString："+LockPatternView.patternToString(pattern));
				mPreviewView.updateView(pattern);
//				
				
				SharedPreferences sp = getSharedPreferences("lock_pattern", 0);
				Editor editor = sp.edit();
				userName = mEditText.getText().toString();
				editor.putString("u_"+userName, LockPatternView.patternToString(pattern));
				String mw = null;
				try {
					mw = new String(LockPatternView.patternToString(pattern).getBytes(),"utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				editor.putString("mu_"+userName,mw );
				editor.commit();
//				mLockPatternView.setDisplayMode(DisplayMode.Wrong);
				mLockPatternView.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						mLockPatternView.clearPattern();
					}
				}, 1000);
				
			}
			
			@Override
			public void onPatternCleared() {
				// TODO Auto-generated method stub
				Log.d("TAG", "onPatternCleared-----");
				mPreviewView.updateView(null);
			}
			
			@Override
			public void onPatternCellAdded(List<Cell> pattern) {
				// TODO Auto-generated method stub
				Log.d("TAG", "onPatternCellAdded-----");
				Log.i("TAG", "onPatternCellAdded---pattern："+pattern.toString());
				
			}
		});
	}
}
