package cn.bluetel.phone;

import android.os.Bundle;

public class CallActivity extends BaseActivity{

	private final String SUB_TITLE = "²¦ºÅ";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(SUB_TITLE);
	}

	@Override
	public int getLayoutId() {
		return R.layout.activity_call;
	}
}
