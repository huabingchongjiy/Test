package teacherwork;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.xml.crypto.Data;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;


class person{
	
	public String user = "shenxiaoya3@163.com";//邮箱的用户名
	public String password = "1234554321";//邮箱的密码
	
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
	public int chaxuntime = 15;//设置时间为15；每15分钟查看一次邮件
	
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
		    
			IMAPStore store = (IMAPStore) session.getStore("imap");//使用imap会话机制，连接服务器
			store.connect(user,password);
			IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);
			//获取总邮件数
			total = folder.getMessageCount();
			System.out.println("共有邮件：" + total + "封");
			//得到收件箱文件夹信息，获取邮件列表
			System.out.println("未读邮件数：" + folder.getUnreadMessageCount());
			System.out.println("---------------------------------------------------------------------------");
			System.out.println("---------------------------------------------------------------------------");
			Message[] messages = folder.getMessages();
			int messageNumber = 0;
			for (Message message : messages){
				System.out.println("发送时间：" + message.getSentDate());
				System.out.println("主题：" + message.getSubject());
				//System.out.println("内容：" + message.getContent());
				Flags flags = message.getFlags();
				if (flags.contains(Flags.Flag.SEEN)) {
					System.out.println("这是一份已读邮件");

				}
				else {
					System.out.println("未读邮件");
					//判断未读邮件有没有超过30分钟
					int i = 30;	//设置未查看为30分钟
					Calendar c = Calendar.getInstance(); //获取当前时间
					int hour = c.get(Calendar.HOUR_OF_DAY); //获取当前时间的小时数
					int minute = c.get(Calendar.MINUTE);  //获取当前时间的分钟数
					int sendhour = message.getSentDate().getHours();//获取邮件发送时间的小时
					int sendtime = message.getSentDate().getMinutes();//获取邮件发送时间的分钟
					
					//如果当前时间超过邮件发送时间30分钟，启动转发功能
					if(((hour*60+minute)-(sendhour*60+sendtime)) > i){
						
						unlookSend unlookSend = new unlookSend();
						unlookSend.run();
					}
				}
				System.out.println("---------------------------------------------------------------------------");
				System.out.println("---------------------------------------------------------------------------");
				//通过邮件的MessageNumber在收件箱里面获取邮件
				messageNumber = message.getMessageNumber();
			}
			
			Message message = folder.getMessage(messageNumber);
			//释放资源
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
