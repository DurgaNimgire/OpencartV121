package utilities;

import org.testng.annotations.DataProvider;
import java.io.IOException;

public class DataProviders {

		@DataProvider(name = "LoginData")
		public String [][] getData()throws IOException
		{
			String path=".\\testData\\Opencart_LoginData.xlsx"; //taking excel file from testData
			
			ExcelUtilities xlutil = new ExcelUtilities(path);//Creating the object for xlUtility
			int totalRows = xlutil.getRowCount("Sheet1");
			int totalcols = xlutil.getCellCount("Sheet1",1);
			
			String logindata [][] = new String[totalRows] [totalcols];//created for two dimensional array which can store 
			
			for(int i=1;i<totalRows; i++) //1 // Read the data from excel storing in two dimensional array
			{
				for(int j=0;j<totalcols;j++)
				{
					logindata[i-1][j]=xlutil.getCellData("Sheet1", i, j);  //1,0
				}
				
			}
			return logindata; //returning two dimensional array
		}
		
		//DataProvider 2
		
		//DataProvider 3
		
		//DataProvider 4
	}



