package cn.bluetel.phone;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * 去掉RadioGroup，为了实现拨号模块，显示、隐藏拨号键盘的功能
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup {

	FrameLayout tabcontent;
	RadioGroup radio_tabs;
	LocationManager locationManager;
	
	int hasCheckedId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);

		tabcontent = (FrameLayout) findViewById(android.R.id.tabcontent);

		findViews();
		initFooter();
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	}
	private RadioButton[] mButtons;
	private RadioButton rbCall;
	private RadioButton rbContact;
	private RadioButton rbMessage;
	private RadioButton rbSetting;
	private void findViews() {
		rbCall = (RadioButton) findViewById(R.id.main_tab_1);
		rbContact = (RadioButton) findViewById(R.id.main_tab_2);
		rbMessage = (RadioButton) findViewById(R.id.main_tab_3);
		rbSetting = (RadioButton) findViewById(R.id.main_tab_4);
	}
	private void initFooter() {
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.tabs);
		int count = linearLayout.getChildCount();
		mButtons = new RadioButton[count];
		for (int i = 0; i < count; i++) {
			mButtons[i] = (RadioButton) linearLayout.getChildAt(i);
			mButtons[i].setTag(i);
			mButtons[i].setChecked(false);
			mButtons[i].setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					int checkedId = v.getId();
					 if (hasCheckedId == checkedId && hasCheckedId != R.id.main_tab_1) {
						 return;
					 }
					switch (checkedId) {
					case R.id.main_tab_1:
						if(hasCheckedId == checkedId) {
							Log.d("bluetel", "repeat....");
						} else {
							reset(rbCall);
							startViewByActivity(CallActivity.class, checkedId);
						}
						break;
					case R.id.main_tab_2:
						reset(rbContact);
						startViewByActivity(ContactsActivity.class, checkedId);
						break;
					case R.id.main_tab_3:
						reset(rbMessage);
						startViewByActivity(MessageActivity.class, checkedId);
						break;
					case R.id.main_tab_4:
						reset(rbSetting);
						startViewByActivity(SettingActivity.class, checkedId);
						break;
					}
					hasCheckedId = checkedId;
				}
			});
		}
	}
	private void reset(RadioButton checkedButton) {
		rbCall.setChecked(false);
		rbContact.setChecked(false);
		rbMessage.setChecked(false);
		rbSetting.setChecked(false);
		
		checkedButton.setChecked(true);
	}
	@Override
	protected void onResume() {
		super.onResume();
		rbCall.setChecked(true);
		rbContact.setChecked(false);
		rbMessage.setChecked(false);
		rbSetting.setChecked(false);
	}

	protected void startViewByActivity(Class<?> clazz, int checkid) {
		Intent intent = new Intent(this, clazz);
		startViewByActivity(clazz, intent);
	}

	public void startViewByActivity(Class<?> clazz, Intent intent) {
		View v = getLocalActivityManager().startActivity(clazz.getName(), intent).getDecorView();
		tabcontent.removeAllViews();
		tabcontent.addView(v);
	}
}
