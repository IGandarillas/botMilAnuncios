package botMilAnuncios;
import java.io.File;
import java.io.FileInputStream;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class XMLReader {

	private FileInputStream file;
	private HSSFRow row; 
	private HSSFCell cell;
	private Iterator<Row> rowIterator;
	private int rowCount=0;
	private int count=0;
	private Articulo articulo;
	private ArrayList<Articulo> articulosList;
	private static final double SEGS_HOUR= 3600;
	public XMLReader(String path) throws IOException{
		articulosList=new ArrayList<Articulo>();
		file = new FileInputStream(new File(path+"\\Hoja.xls"));
		//Get the workbook instance for XLS file 
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		 
		//Get first sheet from the workbook
		HSSFSheet sheet = workbook.getSheetAt(0);
		 
		//Get iterator to all the rows in current sheet
		rowIterator = sheet.rowIterator();		
		iterar();		
		workbook.close();		
	}
	public int[] getModCount(){
		int i[]= new int[2];
		i[0]=rowCount;
		i[1]=count;
		return i;
	}
	public void iterar(){
		while(rowIterator.hasNext()){
			rowCount++;
			row=(HSSFRow) rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();	
			
			/**
			 * LO MEJOR ES PASARLE UNA LISTA
			 * 
			 */			
			while(cellIterator.hasNext()){				
				cell = (HSSFCell) cellIterator.next();
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
					String cellValue = cell.getStringCellValue();					

					
					if(cellValue.equals("*")){				
					//	System.out.println(row.getCell(0).getStringCellValue());
						row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
						articulo=new Articulo();
						articulo.setAutoRenoveHours(parseAutoRenoveHours(row.getCell(2).getNumericCellValue()));
						articulo.setBorrar(parseBorrar(row.getCell(1).getStringCellValue()));
						articulo.setID(parseId(row.getCell(0).getStringCellValue()));
						
						articulo.setFila(rowCount);
						articulosList.add(articulo);
						count++;
						
					}
				}
			}
		}
	}
	
	public String parseArticuloId(String ipe) {
		String ip= ipe;
		int cola= ip.length()-4;
		ip=ip.substring(0, cola);
		StringBuilder sb = new StringBuilder();
		Stack<Character> stack = new Stack<Character>();
		for(int i=ip.length(); i>=ip.length()-12;i--){
			
			char c= ip.charAt(i-1);
			if(c>='0' && c<='9'){
				stack.push(c);
			}else{
				i=ip.length()-13;
			}
		}
		while(stack.size()>0){
			sb.append(stack.pop());
		}
	

		return sb.toString();
		
	}
	public String parseId(String ide){
		return ide.substring(1);
	}
	
	public String parseAutoRenoveHours(double hours){
		String segs = new Double(hours*SEGS_HOUR).toString();
		return segs.substring(0, segs.length()-2);
	}
	public boolean parseBorrar(String borrar){
		if(borrar.equals("d"))
			return true;
		return false;
	}
	public ArrayList<Articulo> getArticulos(){
		return articulosList;
	}

}
