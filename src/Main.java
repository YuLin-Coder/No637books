import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.rain.util.CommonUtil;

public class Main {

	public static void main(String[] args) throws ParseException {

		Date currentTime = new Date();  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");  
        String dateString = formatter.format(currentTime);  
		System.out.println(dateString);
		System.out.println(CommonUtil.differentDays("2021-04-07 00:00:00", "2021-04-06 00:00:00"));

	}

}
