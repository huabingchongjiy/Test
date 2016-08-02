package teacherwork;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Timer;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.swing.tree.TreePath;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

 class MyThread extends Thread{
	
public void run() {
	for(int i = 1;i<10;i++){
		System.out.println("i");
		try{
			String user = "shenxiaoya3@163.com";//邮箱的用户名
			String password = "1234554321";//邮箱的密码
			
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
					System.err.println("未读邮件");
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
			Thread.sleep(1000*60*30);	
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
		
}
	
}
public class Email {

	
	public static void main(String[] args) throws Exception{
		MyThread m = new MyThread();
		m.start();
		System.out.println("test chngne!");
	}
	
	
	
}

