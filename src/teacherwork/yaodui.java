package teacherwork;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

//定义药类
 class Yao{
	String name1;
	String name2;
	public Yao(){
		
	}
	public Yao(String name1,String name2){
		this.name1 = name1;
		this.name2 = name2;
	}
	@Override
	public String toString() {
		return "Yao [name1=" + name1 + ", name2=" + name2 + "]";
	}
}

public class yaodui {
	//文本路径和电子表格的路径
	static String txtFilePath = "G:\\药对.txt";
	static String excelFilePath = "G:\\药对.xlsx";
	public static void main(String[] args) {
		//解析txt文件，得到数据保存在List中
		ArrayList<Yao> yao = getYao();
		//把List的数据写入到excelt中
		saveToExcel(yao);
	}
	
	private static ArrayList<Yao> getYao() {
		ArrayList<Yao> yao = new ArrayList<Yao>();
		BufferedReader br = null;
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(txtFilePath), "gbk");
			 br = new BufferedReader(isr);
			
			String hasRead = null;
			int index = 0;
			while((hasRead = br.readLine()) != null){
				//如果此行是空白行，那么就不进行解析，忽略此行
				if (hasRead.trim().equals("")) {
					continue;
				}
				System.out.println(hasRead);
				String[] temp = hasRead.trim().split("( )+");
				Yao y = new Yao(temp[0],temp.length>=2 ? temp[1] : "");
				yao.add(y);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(br != null){
				try{
					br.close();
				}catch (IOException e) {					
				}
			}
		}
		return yao;
	}
	
	private static void saveToExcel(ArrayList<Yao> yao) {
		WritableWorkbook book = null;
		try {
			//创建文件
			book = Workbook.createWorkbook(new File(excelFilePath));
			//生成名为“药对”的工作表，参数0表示第一页
			WritableSheet sheet = book.createSheet("药对", 0);
			
			for (int i = 0; i < yao.size(); i++){
				Yao y = yao.get(i);
				Label label1 = new Label(0,i,y.name1);
				//System.out.println(y.name1);
				Label label2 = new Label(1,i,y.name2);
				sheet.addCell(label1);
				sheet.addCell(label2);
			}
			book.write();
			book.close();
		}catch (Exception e) {
			System.out.println(e);
		}
		
	}

}
