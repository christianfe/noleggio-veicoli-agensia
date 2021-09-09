package it.univaq.disim.oop.bhertz.business.impl.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.bhertz.business.BusinessException;
import it.univaq.disim.oop.bhertz.view.ViewDispatcher;

public class FileUtility {

	public static final String COLUMN_SEPARATOR = ";";

	public static final String[] trim(String[] s) {
		for (int i = 0; i < s.length; i++)
			s[i] = s[i].trim();
		return s;
	}

	public static FileData readAllRows(String filename) throws IOException {
		FileData result = new FileData();

		try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
			List<String[]> rows = new ArrayList<>();
			long count = Long.parseLong(in.readLine());
			result.setCount(count);
			String line = null;
			while ((line = in.readLine()) != null)
				rows.add(trim(line.split(COLUMN_SEPARATOR)));
			result.setRows(rows);
		}
		return result;
	}

	public List<String[]> getAllByFile(String fileName) throws BusinessException {
		FileData fileData = null;
		try {
			fileData = FileUtility.readAllRows(fileName);
		} catch (IOException e) {
			ViewDispatcher.getInstance().renderError(e);
		}
		return fileData.getRows();
	}

	public void setAllByFile (String fileName, List<String[]> list) throws BusinessException{

		try {
			FileData fileData = FileUtility.readAllRows(fileName);
			try (PrintWriter writer = new PrintWriter(new File(fileName))) {
				long cont = fileData.getCount();
				writer.println((cont + 1));
				for (String[] row : list)
					writer.println(String.join(FileUtility.COLUMN_SEPARATOR, row));
			}
		} catch (IOException e) {
			ViewDispatcher.getInstance().renderError(e);
			throw new BusinessException(e);
		}
		
		
	}


}
