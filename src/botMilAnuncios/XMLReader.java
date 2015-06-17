package botMilAnuncios;
import java.io.File;
import java.io.FileInputStream;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XMLReader {

	private FileInputStream file;
	private XSSFRow row; 
	private XSSFCell cell;
	private Iterator<Row> rowIterator;
	private int rowCount=0;
	private int count=0;
	private Articulo articulo;
	private ArrayList<Articulo> articulosList;
	private static final double SEGS_HOUR= 3600;
	public XMLReader(String path,String name) throws IOException{
		articulosList=new ArrayList<Articulo>();
		file = new FileInputStream(new File(path+"\\"+name));
		//Get the workbook instance for XLS file 
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		 
		//Get first sheet from the workbook
		XSSFSheet sheet = workbook.getSheetAt(0);
		 
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
			if(rowCount!=0){
				row=(XSSFRow) rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();	
				
				/**
				 * LO MEJOR ES PASARLE UNA LISTA
				 * 
				 */			
				while(cellIterator.hasNext()){				
					cell = (XSSFCell) cellIterator.next();
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING){
						String cellValue = cell.getStringCellValue();					
	
						
						if(cellValue.equals("*")){				
						//	System.out.println(row.getCell(0).getStringCellValue());
							row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
							articulo=new Articulo();
						
							articulo.setAutoRenoveSegs(parseAutoRenoveSegs(row.getCell(3).getNumericCellValue()));
							articulo.setAutoRHour(parseAutoRenoveHours(row.getCell(3).getNumericCellValue())); 
							if(row.getCell(2).getCellType()==XSSFCell.CELL_TYPE_BLANK){
								articulo.setBorrar(false);
							}else{
								articulo.setBorrar(parseBorrar(row.getCell(2).getStringCellValue()));
							}							
							articulo.setID(parseId(row.getCell(0).getStringCellValue()));
							articulo.setTitulo(row.getCell(1).getStringCellValue());
							articulo.setFila(row.getRowNum());
							articulosList.add(articulo);
							count++;
							
						}
					}
				}
			}
		}
	}
	
	public String parseId(String ide){
		return ide.substring(1);
	}
	
	public String parseAutoRenoveHours(double hours){
		String segs = new Double(hours).toString();
		return segs.substring(0, segs.length()-2);
	}
	public String parseAutoRenoveSegs(double hours){
		String segs = new Double(hours*SEGS_HOUR).toString();
		return segs.substring(0, segs.length()-2);
	}
	public boolean parseBorrar(String borrar){
		if(borrar.equals("borrar"))
			return true;
		
		return false;
	}
	public ArrayList<Articulo> getArticulos(){
		return articulosList;
	}

}
