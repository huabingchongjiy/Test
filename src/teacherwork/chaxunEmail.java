package teacherwork;

import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

//try2
class person{
	
	public String user = "shenxiaoya3@163.com";//閭鐨勭敤鎴峰悕
	public String password = "1234554321";//閭鐨勫瘑鐮�
	
	public person(){
		
	}
	public person(String user,String password) {
		this.user = user;
		this.password = password;
	}
}

public class chaxunEmail extends TimerTask{
	public String user ;
	public String password;
	public int chaxuntime = 15;//璁剧疆鏃堕棿涓�15锛涙瘡15鍒嗛挓鏌ョ湅涓�娆￠偖浠�
	
	public chaxunEmail() {
		// TODO Auto-generated constructor stub
		user = new person().user;
		password = new person().password;
	}
	public void run() {
		// TODO Auto-generated method stub
		try{			
			
			Properties prop = System.getProperties();
			prop.put("mail.store.protocol", "imap");
			prop.put("mail.imap.host", "imap.163.com");
			
			Session session = Session.getInstance(prop);
			
			int total = 0;
		    
			IMAPStore store = (IMAPStore) session.getStore("imap");//浣跨敤imap浼氳瘽鏈哄埗锛岃繛鎺ユ湇鍔″櫒
			store.connect(user,password);
			IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);
			//鑾峰彇鎬婚偖浠舵暟
			total = folder.getMessageCount();
			System.out.println("鍏辨湁閭欢锛�" + total + "灏�");
			//寰楀埌鏀朵欢绠辨枃浠跺す淇℃伅锛岃幏鍙栭偖浠跺垪琛�
			System.out.println("鏈閭欢鏁帮細" + folder.getUnreadMessageCount());
			System.out.println("---------------------------------------------------------------------------");
			System.out.println("---------------------------------------------------------------------------");
			Message[] messages = folder.getMessages();
			int messageNumber = 0;
			for (Message message : messages){
				System.out.println("鍙戦�佹椂闂达細" + message.getSentDate());
				System.out.println("涓婚锛�" + message.getSubject());
				//System.out.println("鍐呭锛�" + message.getContent());
				Flags flags = message.getFlags();
				if (flags.contains(Flags.Flag.SEEN)) {
					System.out.println("杩欐槸涓�浠藉凡璇婚偖浠�");
				}
				else {
					System.out.println("鏈閭欢");
				}
				System.out.println("---------------------------------------------------------------------------");
				System.out.println("---------------------------------------------------------------------------");
				//閫氳繃閭欢鐨凪essageNumber鍦ㄦ敹浠剁閲岄潰鑾峰彇閭欢
				messageNumber = message.getMessageNumber();
			}
			
			Message message = folder.getMessage(messageNumber);
			//閲婃斁璧勬簮
			if(folder != null){
				folder.close(true);
			}
			if (store != null) {
				store.close();
			}	
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Timer timer = new Timer();
		chaxunEmail chaxun = new chaxunEmail();
		timer.schedule(chaxun, 0, 1000*60*chaxun.chaxuntime);
	}

}
