package opencsv;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import utils.ZipUtil;

/**
 * 
 * @author yuezh2
 * @date 2020/03/12 17:55
 *
 */
public class CsvParseUtil {


	private static final Logger LOGGER = LoggerFactory.getLogger(CsvParseUtil.class);
	
    /**
     * Parse the CSV file and turn it into a bean
     *
     * @param inputStream csv file stream
     * @param clazz       type
     * @param <T>         T
     * @return list<T>
     */

    public static <T> List<T> getCsvData(InputStream inputStream, Class<T> clazz) {
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(inputStream, "gbk");
        } catch (Exception e) {
        	LOGGER.error("from csv data, the error is {}", e.getMessage());
        }

        HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(clazz);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in)
                .withSeparator(',')
                .withQuoteChar('\'')
                .withMappingStrategy(strategy).build();
        return csvToBean.parse();
    }
    
    
    /**
     * Write the data to CSV
     *
     * @param fileName filename
     * @param data     data
     * @param bean     bean
     * @param response response
     */
    public static <T> void writeCSV(String fileName,  List<T> data, Class<T> bean, HttpServletResponse response) {
        writeCSV(fileName, null , data, bean, response);
    }
    
    

    /**
     * Write the data to CSV
     *
     * @param fileName filename
     * @param data     data
     * @param bean     bean
     * @param response response
     */
    public static <T> void writeCSV(String fileName, String[] head, List<T> data, Class<T> bean, HttpServletResponse response) {
        try {
            response.reset();
            response.setContentType("APPLICATION/OCTET-STREAM");
            fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            response.setHeader("Content-disposition", "attachment;  filename=" + fileName + ".csv");
            response.setCharacterEncoding("gbk");
            CSVWriter writer = new CSVWriter(response.getWriter());
            /** for local test */
            //Writer writer = new FileWriter("D:/data/yourfile.csv");
            CsvBindByNameAndPositionMappingStrategy<T> mappingStrategy = new CsvBindByNameAndPositionMappingStrategy<>();
            mappingStrategy.setType(bean);
            StatefulBeanToCsvBuilder<T> builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv<T> beanToCsv = builder.withMappingStrategy(mappingStrategy)
                    .withSeparator(',').withApplyQuotesToAll(false).build();
            if(null != head && head.length>0) {
                writer.writeNext(head);
            }
            beanToCsv.write(data);
            writer.flush();
            writer.close();
        } catch (Exception e) {
        	LOGGER.error("transfer to csv file, the error is {}", e.getMessage());
        }
    }
    
    
    
    /**
     * Write the data to CSV
     *
     * @param fileName filename
     * @param data     data
     * @param bean     bean
     * @param response response
     */
    public static <T> void writeCSV2File(String fileName, String[] head, List<T> data, Class<T> bean) {
        try {
        	fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        	
        	Writer fileWriter = new FileWriter(fileName);
			// 手动加上BOM标识
        	fileWriter.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF }));
			
            CSVWriter writer = new CSVWriter(fileWriter);
            /** for local test */
            //Writer writer = new FileWriter("D:/data/yourfile.csv");
            CsvBindByNameAndPositionMappingStrategy<T> mappingStrategy = new CsvBindByNameAndPositionMappingStrategy<>();
            mappingStrategy.setType(bean);
            StatefulBeanToCsvBuilder<T> builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv<T> beanToCsv = builder.withMappingStrategy(mappingStrategy)
                    .withSeparator('\t').withApplyQuotesToAll(false).build();
            if(null != head && head.length>0) {
                writer.writeNext(head);
            }
            beanToCsv.write(data);
            writer.flush();
            writer.close();
        } catch (Exception e) {
        	LOGGER.error("transfer to csv file, the error is {}", e.getMessage());
        }
    }
    
    
    
    
    /**
     * Write the data to CSV
     *
     * @param fileName filename
     * @param data     data
     * @param bean     bean
     * @param response response
     */
    public static <T> String writeCSV2Str(String[] head, List<T> data, Class<T> bean) {
    	return writeCSV2Str(head, data, bean, CSVWriter.DEFAULT_SEPARATOR);
    }
    	
    	
    /**
     * Write the data to CSV
     *
     * @param fileName filename
     * @param data     data
     * @param bean     bean
     * @param response response
     */
    public static <T> String writeCSV2Str(String[] head, List<T> data, Class<T> bean , char separator) {
    	String content = "";
    	CSVWriter writer = null;
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        try {
            writer = new CSVWriter(new OutputStreamWriter(baos),separator,
            		CSVWriter.DEFAULT_QUOTE_CHARACTER,CSVWriter.DEFAULT_ESCAPE_CHARACTER,CSVWriter.DEFAULT_LINE_END);
            /** for local test */
            //Writer writer = new FileWriter("D:/data/yourfile.csv");
			// 手动加上BOM标识
            writer.writeNext(new String[] {new String(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF })});
        	
            CsvBindByNameAndPositionMappingStrategy<T> mappingStrategy = new CsvBindByNameAndPositionMappingStrategy<>();
            mappingStrategy.setType(bean);
            StatefulBeanToCsvBuilder<T> builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv<T> beanToCsv = builder.withMappingStrategy(mappingStrategy)
                    .withSeparator(separator).withApplyQuotesToAll(false).build();
            if(null != head && head.length>0) {
                writer.writeNext(head);
            }
            beanToCsv.write(data);
            baos.flush();
            writer.flush();
            content = baos.toString();
        } catch (Exception e) {
        	LOGGER.error("transfer to csv file, the error is {}", e.getMessage());
        }finally {
        	try {
	        	baos.close();
	        	if(writer!=null) {
		            writer.close();
	        	}
        	} catch (IOException e) {
        		LOGGER.error("close csv writer , the error is {}", e.getMessage());
			}
        }
        return content;
    }
    
    
    
    /**
     * Write the data to ZIP
     *
     * @param fileName filename
     * @param data     data
     * @param bean     bean
     * @param response response
     */
    public static <T> byte[] writeCSV2Zip(String[] head, List<T> data, Class<T> bean , char separator , String prefix) {
    	byte[] bytes = null;
    	CSVWriter writer = null;
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            writer = new CSVWriter(new OutputStreamWriter(baos),separator,
            		CSVWriter.DEFAULT_QUOTE_CHARACTER,CSVWriter.DEFAULT_ESCAPE_CHARACTER,CSVWriter.DEFAULT_LINE_END);
            CsvBindByNameAndPositionMappingStrategy<T> mappingStrategy = new CsvBindByNameAndPositionMappingStrategy<>();
            mappingStrategy.setType(bean);
            StatefulBeanToCsvBuilder<T> builder = new StatefulBeanToCsvBuilder<>(writer);
            StatefulBeanToCsv<T> beanToCsv = builder.withMappingStrategy(mappingStrategy)
                    .withSeparator(separator).withApplyQuotesToAll(false).build();
            if(null != head && head.length>0) {
                writer.writeNext(head);
            }
            beanToCsv.write(data);
            writer.flush();
            //zip
            bytes = ZipUtil.compressFile(prefix+ ".txt" , baos.toByteArray());
        } catch (Exception e) {
        	LOGGER.error("transfer to csv file, the error is {}", e.getMessage());
        }finally {
        	try {
	        	baos.close();
	        	if(writer!=null) {
		            writer.close();
	        	}
        	} catch (IOException e) {
        		LOGGER.error("close csv writer , the error is {}", e.getMessage());
			}
        }
        return bytes;
    }

}
