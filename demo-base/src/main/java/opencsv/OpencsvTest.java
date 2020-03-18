package opencsv;

import java.util.ArrayList;

/**
 * 
 * @author yuezh2
 *
 * @date 2020年3月18日 下午7:27:21  
 *
 */
public class OpencsvTest {

	
	public static void main(String[] args) {
		//03
		String csv3 = CsvParseUtil.writeCSV2Str(new String[] {"1ヶ月分","作成日時： "}, new ArrayList<CMIFJS03>(), CMIFJS03.class);	
//		sftpService.putFileInString(csv3, CMIFJS_PATH_PREFIX+"03.csv");
		
		
		//zip
//		byte[] bytes2 = CsvParseUtil.writeCSV2Zip(null, points, LmdbSegVars.class, 
//				CommonConstant.TAB_SEPARATOR, String.format(FILENAME_SEG, date));
//		sftpService.putFileInBytes(bytes2, MARKETO_PATH_PREFIX+String.format(FILENAME_SEG, date)+CommonConstant.ZIP_SUFFIX);
//		sftpService.putFileInString(points.size()+"", MARKETO_PATH_PREFIX+String.format(FILENAME_SEG, date)+CommonConstant.CNT_SUFFIX);
	}
	
	
}
