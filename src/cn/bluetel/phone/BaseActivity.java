package cn.bluetel.phone;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivity extends Activity {

	TextView title, sub_title;

	Button back, save;
	
	public ProgressDialog pBar;
	
	public MyHandler handler = new MyHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		title = (TextView) findViewById(R.id.title);
		sub_title = (TextView) findViewById(R.id.sub_title);
		back = (Button) findViewById(R.id.back);
//		save = (Button) findViewById(R.id.save);
		if (back != null) {
			back.setOnClickListener(titleListener);
		}
		if (save != null) {
			save.setOnClickListener(titleListener);
		}
		pBar = new ProgressDialog(this);
		pBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pBar.setCanceledOnTouchOutside(false);
		pBar.setOnCancelListener(onConcelListener);
	}

	public abstract int getLayoutId();

	public void onBackButtonClick(View v) {
		finish();
	}

	public void onSaveButtonClick(View v) {

	}
	
	public void onProgressConcel(DialogInterface dialog){
		
	}
	
	DialogInterface.OnCancelListener onConcelListener = new DialogInterface.OnCancelListener(){

		@Override
		public void onCancel(DialogInterface dialog) {
			// TODO Auto-generated method stub
			onProgressConcel(dialog);
		}
		
	};

	public void setTitle(String s) {
		if (title != null) {
			title.setText(s);
		}
	}

	public void setTitle(int id) {
		title.setText(id);
	}

	public void setSubTitle(String s) {
		if (sub_title != null) {
			sub_title.setText(s);
		}
	}

	public void setSubTitle(int id) {
		if (sub_title != null) {
			sub_title.setText(id);
			sub_title.setVisibility(View.VISIBLE);
		}
	}
	
	public boolean isProgressShow(){
		return pBar.isShowing();
	}

	View.OnClickListener titleListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back:
				onBackButtonClick(v);
				break;
//			case R.id.save:
//				onSaveButtonClick(v);
//				break;

			default:
				break;
			}
		}
	};
	
	public class MyHandler extends Handler {

		public static final int SHOW_PROGRESS_DIALOG = 0x80001;

		public static final int HIDE_PROGRESS_DIALOG = 0x80002;

		public static final int SHOW_TOAST_DIALOG = 0x80003;

		public static final int SHOW_ERROR = 0x80004;

		public String toast_message = "";
		
		public String bar_title = "";
		
		public String bar_message = "";

		@Override
		public void handleMessage(Message msg) {
			if (!Thread.currentThread().isInterrupted()) {
				switch (msg.what) {
				case SHOW_PROGRESS_DIALOG:
					pBar.setTitle(bar_title);
					pBar.setMessage(bar_message);
					pBar.show();
					break;
				case HIDE_PROGRESS_DIALOG:
					if(pBar != null && pBar.isShowing())
						pBar.dismiss();
					break;
				case SHOW_TOAST_DIALOG:
					Toast.makeText(getBaseContext(), toast_message, 1).show();
					break;
				default:
					handleOtherMessage(msg.what);
				}
			}
		}
		protected void sendMessage(int flag) {
			Message msg = new Message();
			msg.what = flag;
			handler.sendMessage(msg);
		}
	}
	
	protected void showToast(String toast_message){
		handler.toast_message = toast_message;
		Message msg = new Message();
		msg.what = MyHandler.SHOW_TOAST_DIALOG;
		handler.sendMessage(msg);
	}

	protected void showProgressBar(String bar_title,String bar_message){
		handler.bar_title = bar_title;
		handler.bar_message = bar_message;
		handler.sendMessage(MyHandler.SHOW_PROGRESS_DIALOG);
	}
	protected void showProgressBar(){
		handler.bar_title = "";
		handler.bar_message = "正在加载请稍后...";
		handler.sendMessage(MyHandler.SHOW_PROGRESS_DIALOG);
	}
	
	protected void hideProgressBar(){
		handler.sendMessage(MyHandler.HIDE_PROGRESS_DIALOG);
		
	}
	
	public void handleOtherMessage(int what) {
		// TODO Auto-generated method stub
		
	}
}
