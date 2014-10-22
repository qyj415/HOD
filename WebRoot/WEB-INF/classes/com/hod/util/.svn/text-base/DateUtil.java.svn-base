package com.hod.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.ParseException;

/**
 * 
 * 
 * @author gngyf18
 * 
 */

public class DateUtil {

	private static ArrayList<String> al = new ArrayList<String>();
//	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

	public static int getDay(String date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(5, 7)) -1);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return maxDay;
	}
	
	public static String startDateUtil(String dateStr) {

		if (dateStr == null) {
			return null;
		}

		String[] dates = dateStr.split("-");

		if (dates.length != 3) {
			return null;
		}
		
		String month_;
		String day_;

		int year = Integer.valueOf(dates[0]);
		int month = Integer.valueOf(dates[1]);
		int day = Integer.valueOf(dates[2]);

		switch (month) {
		case 1:
			month = 12;
			year -= 1;
			if (day == 31)
				day = 30;
			break;
		case 2:
			month = 1;
			if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
				if (day == 29)
					day = 31;
			} else {
				if (day == 28)
					day = 31;
			}
			break;
		case 3:
			month = 2;
			if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
				if (day == 31)
					day = 29;
			} else {
				if (day == 31)
					day = 28;
			}
			break;
		case 4:
			month = 3;
			if (day == 30)
				day = 31;
			break;
		case 5:
			month = 4;
			if (day == 31)
				day = 30;
			break;
		case 6:
			month = 5;
			if (day == 30)
				day = 31;
			break;
		case 7:
			month = 6;
			if (day == 31)
				day = 30;
			break;
		case 8:
			month = 7;
			break;
		case 9:
			month = 8;
			if (day == 30)
				day = 31;
			break;
		case 10:
			month = 9;
			if (day == 31)
				day = 30;
			break;
		case 11:
			month = 10;
			if (day == 30)
				day = 31;
			break;
		case 12:
			month = 11;
			if (day == 31)
				day = 30;
			break;
		}
		
		if(month <=9){
			month_ = "0" + month;
		}else{
			month_ = "" + month;
		}
		
		if(day <=9){
			day_ = "0" + day;
		}else{
			day_ = "" + day;
		}

		return year + "-" + month_ + "-" + day_;

	}
	
	public static String startDayUtil(String dateStr) {

		if (dateStr == null) {
			return null;
		}

		String[] dates = dateStr.split("-");

		if (dates.length != 3) {
			return null;
		}
		
		String month_;
		String day_;

		int year = Integer.valueOf(dates[0]);
		int month = Integer.valueOf(dates[1]);
		int day = Integer.valueOf(dates[2]);

		switch (month) {
		case 1:
			if (day == 1)
			{
				day = 31;
				month = 12;
				year = -1;
			}else
			{
				day--;
			}
			break;
		case 2:
			if(day == 1){
				day = 31;
				month = 1;
			}else
			{
				day--;
			}
			break;
		case 3:
			if(day == 1){
				if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
					day = 29;
					month = 2;
				}else{
					day = 28;
					month = 2;
				}
			}else
			{
				day--;
			}
			break;
		case 4:
			if(day == 1){
				day = 31;
				month = 3;
			}else
			{
				day--;
			}
			break;
		case 5:
			if(day == 1){
				day = 30;
				month = 4;
			}else
			{
				day--;
			}
			break;
		case 6:
			if(day == 1){
				day = 31;
				month = 5;
			}else
			{
				day--;
			}
			break;
		case 7:
			if(day == 1){
				day = 30;
				month = 6;
			}else
			{
				day--;
			}
			break;
		case 8:
			if(day == 1){
				day = 31;
				month = 7;
			}else
			{
				day--;
			}
			break;
		case 9:
			if(day == 1){
				day = 31;
				month = 8;
			}else
			{
				day--;
			}
			break;
		case 10:
			if(day == 1){
				day = 30;
				month = 9;
			}else
			{
				day--;
			}
			break;
		case 11:
			if(day == 1){
				day = 31;
				month = 10;
			}else
			{
				day--;
			}
			break;
		case 12:
			if(day == 1){
				day = 30;
				month = 11;
			}else
			{
				day--;
			}
			break;
		}
		
		if(month <=9){
			month_ = "0" + month;
		}else{
			month_ = "" + month;
		}
		
		if(day <=9){
			day_ = "0" + day;
		}else{
			day_ = "" + day;
		}

		return year + "-" + month_ + "-" + day_;

	}

	/**
	 * 获取起始时间和结束时间之间隔离的月份链表
	 * 
	 * @param startTime
	 *            起始时间
	 * @param endTime
	 *            结束时间
	 * @return 月份链表
	 */
	public static ArrayList<String> dataList(int dateType, String startTime,
			String endTime, SimpleDateFormat dateSdf) {

		al.clear();// 清空链表

		// 判断传入的dateSdf是否合法
		if (dateSdf == null) {
			return null;
		}

		// 判断传入的起始时间和结束时间是否合法
		if (startTime == null || "".equals(startTime) || endTime == null
				|| "" == endTime) {
			return null;
		}
		String[] start_ = startTime.split("-");
		String[] end_ = endTime.split("-");
		if (start_.length != 3 || end_.length != 3) {
			return null;
		}
		int start_year = Integer.valueOf(start_[0]);
		int start_month = Integer.valueOf(start_[1]);
		int start_date = 0;
		if(dateType != 0){
			start_date = Integer.valueOf(start_[2]);
		}

		int end_year = Integer.valueOf(end_[0]);
		int end_month = Integer.valueOf(end_[1]);
		int end_date = 0;
		if(dateType != 0){
			end_date = Integer.valueOf(end_[2]);
		}
		
		int diff_year = end_year - start_year;
		
		if (dateType == 0) {
			String[] startDayAndHours = start_[2].split(" ");
			String[] start_hours = startDayAndHours[1].split(":");
			start_date = Integer.valueOf(startDayAndHours[0]);
			int start_hour = Integer.valueOf(start_hours[0]);
			
			String[] endDayAndHours = end_[2].split(" ");
			String[] end_hours = endDayAndHours[1].split(":");
			end_date = Integer.valueOf(endDayAndHours[0]);
			int end_hour = Integer.valueOf(end_hours[0]);
			
			while (diff_year >= 0) {
				if (diff_year == 0) {
					for (; start_month <= end_month; start_month++) {
						if (start_month == end_month) {
							for(;start_date<=end_date;start_date++){
								if(start_date == end_date){
									dateIsHour(start_year, start_month,
											start_date, dateSdf, start_date,start_hour,end_hour+1);
								}else{
									dateIsHour(start_year, start_month,
											start_date, dateSdf, start_date,start_hour,24);
								}
								start_hour = 0;
							}
							start_date = 1;
							continue;
						}

						if (start_year % 4 == 0 && (start_year % 100 != 0
								|| start_year % 400 == 0)) {
							if (start_month == 2) {
								start_date = dateIsHour(start_year, start_month,
										start_date, dateSdf, 29,start_hour,24);
							}
						} else {
							if (start_month == 2) {
								start_date = dateIsHour(start_year, start_month,
										start_date, dateSdf, 28,start_hour,24);
							}
						}
						switch (start_month) {
						case 1:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 31,start_hour,24);
							break;
						case 3:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 31,start_hour,24);
							break;
						case 4:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 30,start_hour,24);
							break;
						case 5:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 31,start_hour,24);
							break;
						case 6:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 30,start_hour,24);
							break;
						case 7:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 31,start_hour,24);
							break;
						case 8:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 31,start_hour,24);
							break;
						case 9:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 30,start_hour,24);
							break;
						case 10:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 31,start_hour,24);
							break;
						case 11:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 30,start_hour,24);
							break;
						case 12:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 31,start_hour,24);
							break;
						}
						start_hour = 1;
					}
				} else {
					for (; start_month <= 12; start_month++) {
						if (start_year % 4 == 0 && (start_year % 100 != 0
								|| start_year % 400 == 0)) {
							if (start_month == 2) {
								start_date = dateIsHour(start_year, start_month,
										start_date, dateSdf, 29,start_hour,24);
							}
						} else {
							if (start_month == 2) {
								start_date = dateIsHour(start_year, start_month,
										start_date, dateSdf, 28,start_hour,24);
							}
						}
						switch (start_month) {
						case 1:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 31,start_hour,24);
							break;
						case 3:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 31,start_hour,24);
							break;
						case 4:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 30,start_hour,24);
							break;
						case 5:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 31,start_hour,24);
							break;
						case 6:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 30,start_hour,24);
							break;
						case 7:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 31,start_hour,24);
							break;
						case 8:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 31,start_hour,24);
							break;
						case 9:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 30,start_hour,24);
							break;
						case 10:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 31,start_hour,24);
							break;
						case 11:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 30,start_hour,24);
							break;
						case 12:
							start_date = dateIsHour(start_year, start_month,
									start_date, dateSdf, 31,start_hour,24);
							break;
						}
					}
					if (start_month == 13) {
						start_year += 1;
						start_month = 1;
					}
					start_hour = 1;
				}
				diff_year--;
			}
		}

		if (dateType == 1) {
			while (diff_year >= 0) {
				if (diff_year == 0) {
					for (; start_month <= end_month; start_month++) {
						if (start_month == end_month) {
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, end_date);
							continue;
						}

						if (start_year % 4 == 0 && (start_year % 100 != 0
								|| start_year % 400 == 0)) {
							if (start_month == 2) {
								start_date = dateIsDay(start_year, start_month,
										start_date, dateSdf, 29);
							}
						} else {
							if (start_month == 2) {
								start_date = dateIsDay(start_year, start_month,
										start_date, dateSdf, 28);
							}
						}
						switch (start_month) {
						case 1:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 31);
							break;
						case 3:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 31);
							break;
						case 4:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 30);
							break;
						case 5:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 31);
							break;
						case 6:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 30);
							break;
						case 7:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 31);
							break;
						case 8:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 31);
							break;
						case 9:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 30);
							break;
						case 10:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 31);
							break;
						case 11:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 30);
							break;
						case 12:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 31);
							break;
						}
					}
				} else {
					for (; start_month <= 12; start_month++) {
						if (start_year % 4 == 0 && (start_year % 100 != 0
								|| start_year % 400 == 0)) {
							if (start_month == 2) {
								start_date = dateIsDay(start_year, start_month,
										start_date, dateSdf, 29);
							}
						} else {
							if (start_month == 2) {
								start_date = dateIsDay(start_year, start_month,
										start_date, dateSdf, 28);
							}
						}
						switch (start_month) {
						case 1:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 31);
							break;
						case 3:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 31);
							break;
						case 4:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 30);
							break;
						case 5:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 31);
							break;
						case 6:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 30);
							break;
						case 7:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 31);
							break;
						case 8:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 31);
							break;
						case 9:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 30);
							break;
						case 10:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 31);
							break;
						case 11:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 30);
							break;
						case 12:
							start_date = dateIsDay(start_year, start_month,
									start_date, dateSdf, 31);
							break;
						}
					}
					if (start_month == 13) {
						start_year += 1;
						start_month = 1;
					}
				}
				diff_year--;
			}
		}

		if (dateType == 2) {
			while (diff_year >= 0) {
				if (diff_year == 0) {
					for (; start_month <= end_month; start_month++) {
						if (start_month == end_month) {
							start_date = dateIsMonth(start_year, start_month,
									start_date, end_date, dateSdf);
							continue;
						}

						if (start_year % 4 == 0 && (start_year % 100 != 0
								|| start_year % 400 == 0)) {
							if (start_month == 2) {
								start_date = dateIsMonth(start_year,
										start_month, start_date, 29, dateSdf);
							}
						} else {
							if (start_month == 2) {
								start_date = dateIsMonth(start_year,
										start_month, start_date, 28, dateSdf);
							}
						}
						switch (start_month) {
						case 1:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 31, dateSdf);
							break;
						case 3:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 31, dateSdf);
							break;
						case 4:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 30, dateSdf);
							break;
						case 5:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 31, dateSdf);
							break;
						case 6:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 30, dateSdf);
							break;
						case 7:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 31, dateSdf);
							break;
						case 8:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 31, dateSdf);
							break;
						case 9:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 30, dateSdf);
							break;
						case 10:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 31, dateSdf);
							break;
						case 11:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 30, dateSdf);
							break;
						case 12:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 31, dateSdf);
							break;
						}
					}
				} else {
					for (; start_month <= 12; start_month++) {
						if (start_year % 4 == 0 && (start_year % 100 != 0
								|| start_year % 400 == 0)) {
							if (start_month == 2) {
								start_date = dateIsMonth(start_year,
										start_month, start_date, 29, dateSdf);
							}
						} else {
							if (start_month == 2) {
								start_date = dateIsMonth(start_year,
										start_month, start_date, 28, dateSdf);
							}
						}
						switch (start_month) {
						case 1:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 31, dateSdf);
							break;
						case 3:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 31, dateSdf);
							break;
						case 4:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 30, dateSdf);
							break;
						case 5:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 31, dateSdf);
							break;
						case 6:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 30, dateSdf);
							break;
						case 7:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 31, dateSdf);
							break;
						case 8:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 31, dateSdf);
							break;
						case 9:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 30, dateSdf);
							break;
						case 10:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 31, dateSdf);
							break;
						case 11:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 30, dateSdf);
							break;
						case 12:
							start_date = dateIsMonth(start_year, start_month,
									start_date, 31, dateSdf);
							break;
						}
					}
					if (start_month == 13) {
						start_year += 1;
						start_month = 1;
					}
				}
				diff_year--;
			}
		}
		
		if (dateType == 3) {
			while (diff_year >= 0) {
				if(diff_year > 0){
					int temp = diff_year;
					for(int i=0;i<=temp;i++){
						start_date = dateIsYear(start_year, start_month,
								start_date, 12, 31, dateSdf);
						start_year++;
						start_month = 1;
						start_date = 1;
						diff_year--;
					}
				}
				
				if(diff_year == 0){
					start_date = dateIsYear(start_year, start_month,
							start_date, end_month, end_date, dateSdf);
					diff_year--;
				}
			}
		}
		return al;
	}
	
	private static int dateIsHour(int start_year, int start_month,
			int start_date, SimpleDateFormat dateSdf, int days,int start_hour,int end_hour) {
	
			for (int i = start_date; i <= days; i++) {
				for(int j=start_hour;j<end_hour;j++){
					String hour_str = "0";
					if(j<=9){
						hour_str = hour_str + j;
					}else{
						hour_str = "" + j;
					}
					String al_data = start_year
					+ "-"
					+ start_month
					+ "-"
					+ i
					+ " "
					+ hour_str
					+ ":00:00"
					+ ","
					+ start_year
					+ "-"
					+ start_month
					+ "-"
					+ i
					+ " "
					+ hour_str
					+ ":59:59"
					+ ","
					+ start_year + "-"
							+ start_month + "-" + i + " " + hour_str + ":00:00";
			al.add(al_data);
				}
			}
			start_date = 1;
		
		return start_date;
	}

	private static int dateIsDay(int start_year, int start_month,
			int start_date, SimpleDateFormat dateSdf, int days) {
		
			for (int i = start_date; i <= days; i++) {
				String al_data = start_year
						+ "-"
						+ start_month
						+ "-"
						+ i
						+ " 00:00:00"
						+ ","
						+ start_year
						+ "-"
						+ start_month
						+ "-"
						+ i
						+ " 23:59:59"
						+ ","
						+ start_year + "-"
								+ start_month + "-" + i;
				al.add(al_data);
			}
			start_date = 1;
		
		return start_date;
	}

	private static int dateIsMonth(int start_year, int start_month,
			int start_date, int end_date, SimpleDateFormat dateSdf) {

			String al_data = start_year
					+ "-"
					+ start_month
					+ "-"
					+ start_date
					+ " 00:00:00"
					+ ","
					+ start_year
					+ "-"
					+ start_month
					+ "-"
					+ end_date
					+ " 23:59:59"
					+ ","
					+ start_year + "-"
							+ start_month + "-" + start_date;

			al.add(al_data);
			start_date = 1;
		return start_date;
	}

	private static int dateIsYear(int start_year, int start_month,
			int start_date, int end_month, int end_date,
			SimpleDateFormat dateSdf) {

			String al_data = start_year
					+ "-"
					+ start_month
					+ "-"
					+ start_date
					+ " 00:00:00"
					+ ","
					+ start_year
					+ "-"
					+ end_month
					+ "-"
					+ end_date
					+ " 23:59:59"
					+ ","
					+ start_year + "-"
							+ start_month + "-" + start_date;

			al.add(al_data);
			start_date = 1;
		return start_date;
	}

}
