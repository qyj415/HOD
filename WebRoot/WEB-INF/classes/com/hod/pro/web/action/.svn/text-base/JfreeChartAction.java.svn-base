package com.hod.pro.web.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import com.hod.pojo.Hod2000Meter;
import com.hod.pojo.Hod2000MeterInfoFreeze;
import com.hod.pojo.Hod2000MeterInfoHistory;
import com.hod.pro.model.service.IHod2000MeterInfoFreezeService;
import com.hod.pro.model.service.IHod2000MeterInfoHistoryService;
import com.hod.pro.model.service.IHod2000MeterService;
import com.hod.util.Arith;
import com.hod.util.DateUtil;
import com.hod.util.GraphicReportHelp;
import com.hod.util.NetworkTimeUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Hod2000VillageAction 报表查询
 * 
 * @author yixiang
 */
public class JfreeChartAction extends ActionSupport {
	
	private JFreeChart chart;
	private HttpServletRequest request;
	private IHod2000MeterService hod2000MeterService;
	private IHod2000MeterInfoFreezeService hod2000MeterInfoFreezeService;
	private IHod2000MeterInfoHistoryService hod2000MeterInfoHistoryService;
	private PrintWriter out;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	public IHod2000MeterInfoFreezeService getHod2000MeterInfoFreezeService() {
		return hod2000MeterInfoFreezeService;
	}

	public void setHod2000MeterInfoFreezeService(
			IHod2000MeterInfoFreezeService hod2000MeterInfoFreezeService) {
		this.hod2000MeterInfoFreezeService = hod2000MeterInfoFreezeService;
	}

	public void toChart() {
		
		try {
			
			request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			out = ServletActionContext.getResponse().getWriter();
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String utils = request.getParameter("utils");
			String meterPosition = request.getParameter("meterPosition");
			SimpleDateFormat sdf = null;
			
			SimpleDateFormat dateSdf = null;
			int timeUtils = 0;
			String x_name = null;
			
			if (utils == null || utils.equals("")) {
				utils = "2";
				timeUtils = DateTickUnit.MONTH;
				dateSdf = new SimpleDateFormat("yyyy-MM");
				x_name = "时间:月";
			} else {
				if (utils.equals("0")) {
					timeUtils = DateTickUnit.HOUR;
					dateSdf = new SimpleDateFormat("HH");
					x_name = "时间:时";
				}
				else if (utils.equals("1")) {
					timeUtils = DateTickUnit.DAY;
					dateSdf = new SimpleDateFormat("dd");
					x_name = "时间:日";
				}
				else if (utils.equals("2")) {
					timeUtils = DateTickUnit.MONTH;
					dateSdf = new SimpleDateFormat("yyyy-MM");
					x_name = "时间:月";
				}
				else if (utils.equals("3")) {
					timeUtils = DateTickUnit.YEAR;
					dateSdf = new SimpleDateFormat("yyyy");
					x_name = "时间:年";
				}
			}
			
			if(utils.equals("0")){
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}else{
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			}
			
			if (endTime == null || endTime.equals("")) {
				endTime=sdf.format(NetworkTimeUtil.getDate());
			}
			
			if (startTime == null || startTime.equals("")) {
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(sdf.parse(endTime));
				long time = cal.getTimeInMillis();
				
				if (utils.equals("0")) {
					time = time - 60*60*1000;
					startTime = sdf.format(new Date(time));
				}
				
				else if (utils.equals("1")) {
					time = time - 24*60*60*1000;
					startTime = sdf.format(new Date(time));
				}
				
				else if (utils.equals("2")) {
					String[] startTime_ = endTime.split(" ");
					startTime = DateUtil.startDateUtil(startTime_[0]);
				}
				
				else if (utils.equals("3")) {
					time = time - 732*24*60*60*1000;
					startTime = sdf.format(new Date(time));
				}
				
			}
			
			if(endTime != null){
				if(!utils.equals("0")){
					endTime = endTime.split(" ")[0];
				}
			}
			if(startTime != null){
				if(!utils.equals("0")){
					startTime = startTime.split(" ")[0];
				}
			}

			if (meterPosition == null || meterPosition.equals("")) {
				out.write("{success:false,data:error}");
				return;
			}
			
			ArrayList<String> al = DateUtil.dataList(Integer.valueOf(utils),startTime, endTime,dateSdf);
			TimeSeriesCollection dataset = new TimeSeriesCollection();// 时间曲线数据集合
			ArrayList<String> list_map = new ArrayList<String>();
			TimeSeries s1 = null;
			if(utils.equals("0")){
				s1 = new TimeSeries("负荷曲线", Hour.class);// 创建时间数据源，每一个//TimeSeries在图上是一条曲线
			}else{
				s1 = new TimeSeries("负荷曲线", Day.class);// 创建时间数据源，每一个//TimeSeries在图上是一条曲线
			}
			
			String current_energy = null;
			for (String s : al) {
				String[] datas = s.split(",");
				String hql = "from Hod2000MeterInfoHistory where meterName='"
						+ meterPosition + "' and readTime between '" + datas[0]
						+ "' and '" + datas[1] + "'" + " ORDER BY readTime";
				
				List list = hod2000MeterInfoHistoryService.findByHQL(hql);
				System.out.println(hql);
				double energy = 0;
				
				if (list.size() >= 1) {
					Hod2000MeterInfoHistory hod2000MeterInfoHistory_Start = (Hod2000MeterInfoHistory) list
							.get(0);
					Hod2000MeterInfoHistory hod2000MeterInfoHistory_End = (Hod2000MeterInfoHistory) list
							.get(list.size() - 1);
					double current_energy_start = Double
							.valueOf(hod2000MeterInfoHistory_Start.getCurrentEnergy()).doubleValue();
					double current_energy_end = Double
							.valueOf(hod2000MeterInfoHistory_End.getCurrentEnergy()).doubleValue();
					energy = current_energy_end - current_energy_start;
//					System.out.println(energy);
					if(current_energy == null){
						current_energy = hod2000MeterInfoHistory_Start.getCurrentEnergy();
					}
					energy = Arith.dataFormat(Double.valueOf(energy));
				}
				Date date=sdf.parse(datas[2]);
				if(utils.equals("0")){
					s1.add(new Hour(date),energy);
				}else{
					s1.add(new Day(date), energy);
				}
				String title = Double.valueOf(energy).toString();
				list_map.add(title);
			}
			dataset.addSeries(s1);
			
			// 建立JFreeChart
			JFreeChart chart = ChartFactory.createTimeSeriesChart(
					"负荷曲线报表", // title
					x_name, // x-axis label
					"消耗热能 [单位:KWh]", // y-axis label
					dataset, // data
					true, // create legend?
					true, // generate tooltips?
					false // generate URLs?
					);
			
			GraphicReportHelp.getTimeSeriesFont(chart);
			chart.getTitle().setText("负荷曲线报表");
			// 副标题
			TextTitle subtitle = new TextTitle(meterPosition+"表计负荷曲线", new Font("黑体", Font.BOLD, 12));
			chart.addSubtitle(subtitle);
			
			XYPlot plot = (XYPlot) chart.getPlot();// 获取图形的画布
			// 设置X轴（日期轴）
			DateAxis axis = (DateAxis) plot.getDomainAxis();
			
			axis.setDateFormatOverride(dateSdf);
			axis.setTickUnit(new DateTickUnit(timeUtils, 1,dateSdf));
			
			//在矩形框中显示信息
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			
			// 该工具类上面没有介绍，在鼠标移动到图片时显示提示信息是用Map实现的，这些Map是用该类生成的。
			String fileName = ServletUtilities.saveChartAsPNG(chart, 700, 400, info,
					session);// 生成图片
			
			String map_ = ChartUtilities.getImageMap(fileName,info);
			String[] maps_ = map_.split("\n");
			String newMapStr = "";
			for(int i=1;i<maps_.length-1;i++){
				maps_[i] = maps_[i].replace("\n", "");
				newMapStr += maps_[i].trim();
			}
			String[] mapStrs = newMapStr.split("\" ");
			StringBuilder sb = new StringBuilder();
			int index_ = list_map.size()-1;
			for(int i=0;i<mapStrs.length;i++){
		        if(mapStrs[i].startsWith("title=")) {
		        	mapStrs[i] = "title=\"" + list_map.get(index_);
		        	index_--;
		        }
		        sb.append(mapStrs[i]);
		        if(i != mapStrs.length-1){
		        	sb.append("\" ");
		        }
			}
			 String sendMapStr = sb.toString();
			 sendMapStr = sendMapStr.replace(",", "\\,");
			 sendMapStr = sendMapStr.replace(":", "\\:");
			 
			 out.write("{success:true,data:'" + fileName + "',mapStr:'" + sendMapStr + "'}");
		} catch (Exception e) {
			out.write("{success:false,data:error}");
			e.printStackTrace();
		}

	}

	public void toAnalyze() {
		
		try {
			request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			out = ServletActionContext.getResponse().getWriter();
			String meterPosition = request.getParameter("meterPosition");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf_ser = new SimpleDateFormat("yyyy-MM");
			
			if (meterPosition == null || meterPosition.equals("")) {
				out.write("{success:false}");
			}
			
			TimeSeriesCollection collection = new TimeSeriesCollection();// 时间曲线数据集合
			TimeSeries series = new TimeSeries("18个月用量分析", Millisecond.class);// 创建时间数据源，每一个//TimeSeries在图上是一条曲线
			
			String hql = "from Hod2000MeterInfoFreeze where meterName='" + meterPosition + "' ORDER BY cleatDate";
			
			List list = hod2000MeterInfoFreezeService.findByHQL(hql);
			ArrayList<String> list_map = new ArrayList<String>();
			HashMap time_map = new HashMap();
			
			for (int i=0;i<list.size();i++) {
				
				Hod2000MeterInfoFreeze hod2000MeterInfoFreeze = (Hod2000MeterInfoFreeze) list
						.get(i);
				double clearEnergy = Double.valueOf(hod2000MeterInfoFreeze.getClearEnergy());
				Date time = hod2000MeterInfoFreeze.getCleatDate();
				String timeStr = sdf.format(time);
				clearEnergy = Arith.dataFormat(Double.valueOf(clearEnergy));
				
				if(!time_map.containsKey(time)){
					time_map.put(time, timeStr);
					series.add(new Millisecond(time), clearEnergy);
				}
				
				String title = " 表计编号:" + hod2000MeterInfoFreeze.getMeterName()
				+ "\\u000a 结 算 日 :" + timeStr
				+ "\\u000a 结算日热量:" + clearEnergy
				+ "\\u000a 结算日流量:" + hod2000MeterInfoFreeze.getClearFlow();
				list_map.add(title);
			}
			collection.addSeries(series);
			
			int show_x_num = 1;
			if(series.getItemCount() > 10){
				show_x_num = series.getItemCount() / 10 + 1;
			}
			
			// 建立JFreeChart
			JFreeChart chart = ChartFactory.createTimeSeriesChart(
					"18个月用量分析", // title
					"单位：月", // x-axis label
					"消耗热能 [单位:KWh]", // y-axis label
					collection, // data
					true, // create legend?
					true, // generate tooltips?
					false // generate URLs?
					);
			
			GraphicReportHelp.getTimeSeriesFont(chart);
			chart.getTitle().setText("18个月用量分析");
			// 副标题
			TextTitle subtitle = new TextTitle(meterPosition+"表计18个月用量分析", new Font("黑体", Font.BOLD, 12));
			chart.addSubtitle(subtitle);
			
			XYPlot plot = (XYPlot) chart.getPlot();// 获取图形的画布
			// 设置X轴（日期轴）
			DateAxis axis = (DateAxis) plot.getDomainAxis();
			axis.setDateFormatOverride(sdf_ser);
			axis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH, show_x_num,sdf_ser));

			//在矩形框中显示信息
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
			String fileName = ServletUtilities.saveChartAsPNG(chart, 700, 400, info,
					session);// 生成图片
			
			String map_ = ChartUtilities.getImageMap(fileName,info);
			String[] maps_ = map_.split("\n");
			String newMapStr = "";
			for(int i=1;i<maps_.length-1;i++){
				maps_[i] = maps_[i].replace("\n", "");
				newMapStr += maps_[i].trim();
			}
			String[] mapStrs = newMapStr.split("\" ");
			StringBuilder sb = new StringBuilder();
			int index_ = list_map.size()-1;
			for(int i=0;i<mapStrs.length;i++){
		        if(mapStrs[i].startsWith("title=")) {
		        	mapStrs[i] = "title=\"" + list_map.get(index_);
		        	index_--;
		        }
		        sb.append(mapStrs[i]);
		        if(i != mapStrs.length-1){
		        	sb.append("\" ");
		        }
			}
			 String sendMapStr = sb.toString();
			 sendMapStr = sendMapStr.replace(",", "\\,");
			 sendMapStr = sendMapStr.replace(":", "\\:");
			 
			out.write("{success:true,data:'" + fileName + "',mapStr:'" + sendMapStr + "'}");
			
		} catch (Exception e) {
			e.printStackTrace();
			out.write("{success:false}");
		}
		
	}

	public void doAnalyze() {
	
		try {
			request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			out = ServletActionContext.getResponse().getWriter();
			String utils = request.getParameter("utils");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String meterPosition = request.getParameter("meterPosition");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			SimpleDateFormat dateSdf = null;
			int timeUtils = 0;
			String x_name = null;

			if (utils == null || utils.equals("")) {
				utils = "2";
				timeUtils = DateTickUnit.MONTH;
				dateSdf = new SimpleDateFormat("yyyy-MM");
				x_name = "时间:月";
			} else {
				if (utils.equals("0")) {
					timeUtils = DateTickUnit.HOUR;
					dateSdf = new SimpleDateFormat("HH");
					x_name = "时间:时";
				}
				else if (utils.equals("1")) {
					timeUtils = DateTickUnit.DAY;
					dateSdf = new SimpleDateFormat("dd");
					x_name = "时间:日";
				}
				else if (utils.equals("2")) {
					timeUtils = DateTickUnit.MONTH;
					dateSdf = new SimpleDateFormat("yyyy-MM");
					x_name = "时间:月";
				}
				else if (utils.equals("3")) {
					timeUtils = DateTickUnit.YEAR;
					dateSdf = new SimpleDateFormat("yyyy");
					x_name = "时间:年";
				}
			}
			
			if (endTime == null || endTime.equals("")) {
				//endTime = sdf.format(new Date());
				endTime=sdf.format(NetworkTimeUtil.getDate());
			}
			
			if (startTime == null || startTime.equals("")) {
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(sdf.parse(endTime));
				long time = cal.getTimeInMillis();
				
				if (utils.equals("0")) {
					time = time - 60*60*1000;
					startTime = sdf.format(new Date(time));
				}
				
				else if (utils.equals("1")) {
					time = time - 24*60*60*1000;
					startTime = sdf.format(new Date(time));
				}
				
				else if (utils.equals("2")) {
					String[] startTime_ = endTime.split(" ");
					startTime = DateUtil.startDateUtil(startTime_[0]) + " " + startTime_[1];
				}
				
				else if (utils.equals("3")) {
					time = time - 732*24*60*60*1000;
					startTime = sdf.format(new Date(time));
				}
				
			}
			
			if (meterPosition == null || meterPosition.equals("")) {
				out.write("{success:false,data:error}");
			}
			
			TimeSeriesCollection collection = new TimeSeriesCollection();// 时间曲线数据集合
			TimeSeries series = new TimeSeries("正常用量分析", Millisecond.class);// 创建时间数据源，每一个//TimeSeries在图上是一条曲线

			String hql = "from Hod2000MeterInfoHistory where meterName='"
				+ meterPosition + "' and readTime between '" + startTime
				+ "' and '" + endTime + "'" + " ORDER BY readTime";
			
			List list = hod2000MeterInfoHistoryService.findByHQL(hql);
			HashMap<String,String> map = new HashMap<String,String>();
			ArrayList<String> list_map = new ArrayList<String>();
			
			for (int i=0;i<list.size();i++) {
				
				Hod2000MeterInfoHistory hod2000MeterInfoHistory = (Hod2000MeterInfoHistory) list.get(i);
				Date time = hod2000MeterInfoHistory.getReadTime();
				
				String current_energy = hod2000MeterInfoHistory.getCurrentEnergy();
				double current_energy_ = Double.valueOf(current_energy);
				current_energy_ = Arith.dataFormat(Double.valueOf(current_energy_));
				
				String timeStr = sdf.format(time);
				if(!map.containsKey(timeStr)){
					
					String meterhql = "from Hod2000Meter where meterName='"+ meterPosition +"'";
					List meterlist = hod2000MeterService.findByHQL(meterhql);
					
					Hod2000Meter hod2000Meter = null;
					if(meterlist.size() > 0){
						hod2000Meter = (Hod2000Meter)meterlist.get(0);
					}
					
					map.put(timeStr, timeStr);
					series.add(new Millisecond(time), current_energy_);
					
					String title = " 表计编号:" + hod2000MeterInfoHistory.getMeterName() 
						+ "\\u000a 抄表时间:" + hod2000MeterInfoHistory.getReadTime()
						+ "\\u000a 当前热量:" + current_energy_
						+ "\\u000a 当前累计流量:" + hod2000MeterInfoHistory.getAccumulateFlow()
						+ "\\u000a 当前瞬时流量:" + hod2000MeterInfoHistory.getMeterFlow()
						+ "\\u000a 累计工作时间:" + hod2000MeterInfoHistory.getAccumulateTime()
						+ "\\u000a 供水温度:" + hod2000MeterInfoHistory.getSupplyTemper()
						+ "\\u000a 回水温度:" + hod2000MeterInfoHistory.getBackTemper();
					
					if(hod2000Meter != null){
					
						if(hod2000Meter.getValveStatus() == 0){
							title += "\\u000a 阀门状态:开";
						}
						else if(hod2000Meter.getValveStatus() == 1){
							title += "\\u000a 阀门状态:关";
						}
						else if(hod2000Meter.getValveStatus() == 2){
							title += "\\u000a 阀门状态:异常";
						}
						else{
							title += "\\u000a 阀门状态:未知";
						}
						
						if(hod2000Meter.getBatteryStatus() == 0){
							title += "\\u000a 电池电压:正常";
						}
						else if(hod2000Meter.getBatteryStatus() == 1){
							title += "\\u000a 电池电压:欠压";
						}
						else{
							title += "\\u000a 电池电压:未知";
						}
						
					}else{
						title += "\\u000a 阀门状态:未知\\u000a 电池电压:未知";
					}
					list_map.add(title);
				}
			}
			
			collection.addSeries(series);
			
			// 建立JFreeChart
			JFreeChart chart = ChartFactory.createTimeSeriesChart(
					"正常用量分析", // title
					x_name, // x-axis label
					"消耗热能 [单位:KWh]", // y-axis label
					collection, // data
					false, // create legend?
					true, // generate tooltips?
					false // generate URLs?
					);
			
			GraphicReportHelp.getTimeSeriesFont(chart);
			chart.getTitle().setText("正常用量分析");
			// 副标题
			TextTitle subtitle = new TextTitle(meterPosition+"表计用量分析", new Font("黑体", Font.BOLD, 12));
			chart.addSubtitle(subtitle);
			
			XYPlot plot = (XYPlot) chart.getPlot();// 获取图形的画布
			// 设置X轴（日期轴）
			DateAxis axis = (DateAxis) plot.getDomainAxis();
			axis.setDateFormatOverride(dateSdf);
			axis.setTickUnit(new DateTickUnit(timeUtils, 1,dateSdf));
			
			//在矩形框中显示信息
			ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());

			String fileName = ServletUtilities.saveChartAsPNG(chart, 700, 400, info,
					session);// 生成图片
			String map_ = ChartUtilities.getImageMap(fileName,info);
			String[] maps_ = map_.split("\n");
			String newMapStr = "";
			for(int i=1;i<maps_.length-1;i++){
				maps_[i] = maps_[i].replace("\n", "");
				newMapStr += maps_[i].trim();
			}
			String[] mapStrs = newMapStr.split("\" ");
			StringBuilder sb = new StringBuilder();
			int index_ = list_map.size()-1;
			for(int i=0;i<mapStrs.length;i++){
		        if(mapStrs[i].startsWith("title=")) {
		        	mapStrs[i] = "title=\"" + list_map.get(index_);
		        	index_--;
		        }
		        sb.append(mapStrs[i]);
		        if(i != mapStrs.length-1){
		        	sb.append("\" ");
		        }
			}
			 String sendMapStr = sb.toString();
			 sendMapStr = sendMapStr.replace(",", "\\,");
			 sendMapStr = sendMapStr.replace(":", "\\:");
			
			out.write("{success:true,data:'" + fileName + "',mapStr:'" + sendMapStr + "'}");
	        
		}catch (Exception e) {
			out.write("{success:false,data:error}");
			e.printStackTrace();
		}
		
	}

	public void toCategory() {
		
		try {
			
			request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			out = ServletActionContext.getResponse().getWriter();
			String utils = request.getParameter("utils");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String meterName = request.getParameter("meterName");

			SimpleDateFormat dateSdf = null;
			int timeUtils;
			String x_name = null;

			if (utils == null || utils.equals("")) {
				utils = "1";
				timeUtils = DateTickUnit.MONTH;
				dateSdf = new SimpleDateFormat("yyyy-MM");
				x_name = "年-月";
			} else {
				if (utils.equals("1")) {
					timeUtils = DateTickUnit.DAY;
					dateSdf = new SimpleDateFormat("MM-dd");
					x_name = "月-日";
				}
				else if (utils.equals("2")) {
					timeUtils = DateTickUnit.MONTH;
					dateSdf = new SimpleDateFormat("yyyy-MM");
					x_name = "年-月";
				}
				else if (utils.equals("3")) {
					timeUtils = DateTickUnit.YEAR;
					dateSdf = new SimpleDateFormat("yyyy");
					x_name = "年";
				}
			}

			if (startTime == null || startTime.equals("")) {
				//startTime = sdf.format(new Date());
				startTime=sdf.format(NetworkTimeUtil.getDate());
				startTime = DateUtil.startDateUtil(startTime);
			}
			if (endTime == null || endTime.equals("")) {
				//endTime = sdf.format(new Date());
				endTime=sdf.format(NetworkTimeUtil.getDate());
			}

			if (meterName == null || meterName.equals("")) {
				out.write("{success:false,data:error}");
			}

			ArrayList<String> al = DateUtil.dataList(Integer.valueOf(utils),
					startTime, endTime, dateSdf);
			
			String hql = "from Hod2000Meter where meterName='"
					+ meterName + "'";
			List list_meter = hod2000MeterService.findByHQL(hql);
			
			List parent_meter = null;
			
			if(list_meter.size() > 0){
				Hod2000Meter meter = (Hod2000Meter) list_meter.get(0);
				String meterPosition = meter.getMeterPosition();
				
				String hql_parent = "from Hod2000Meter where meterParent='"
					+ meterPosition + "'";
				parent_meter = hod2000MeterService.findByHQL(hql_parent);
				
			}

			DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();

			for (String s : al) {
				double energy = 0;
				String[] datas = s.split(",");
				String x_ = dateSdf.format(sdf.parse(datas[2]));
				// 查找上级表的消耗热能
				double parent_energy = 0;
				String hql_parent = "from Hod2000MeterInfoHistory where meterName='"
						+ meterName
						+ "' and readTime between '"
						+ datas[0]
						+ "' and '"
						+ datas[1]
						+ "'"
						+ " ORDER BY readTime";
				List list_parent = hod2000MeterInfoHistoryService
						.findByHQL(hql_parent);
				if (list_parent.size() >= 1) {
					Hod2000MeterInfoHistory hod2000MeterInfoHistory_Start = (Hod2000MeterInfoHistory) list_parent
							.get(0);
					Hod2000MeterInfoHistory hod2000MeterInfoHistory_End = (Hod2000MeterInfoHistory) list_parent
							.get(list_parent.size() - 1);
					double current_energy_start = Double
							.valueOf(hod2000MeterInfoHistory_Start
									.getCurrentEnergy());
					double current_energy_end = Double
							.valueOf(hod2000MeterInfoHistory_End
									.getCurrentEnergy());
					parent_energy = current_energy_end - current_energy_start;
				}

				for (int i = 0; i < parent_meter.size(); i++) {
					Hod2000Meter meter = (Hod2000Meter) parent_meter.get(i);
					String meter_name = meter.getMeterName();
					String hql_his = "from Hod2000MeterInfoHistory where meterName='"
							+ meter_name
							+ "' and readTime between '"
							+ datas[0]
							+ "' and '"
							+ datas[1]
							+ "'"
							+ " ORDER BY readTime";
					List list_history = hod2000MeterInfoHistoryService
							.findByHQL(hql_his);

					if (list_history.size() >= 1) {
						Hod2000MeterInfoHistory hod2000MeterInfoHistory_Start = (Hod2000MeterInfoHistory) list_history
								.get(0);
						Hod2000MeterInfoHistory hod2000MeterInfoHistory_End = (Hod2000MeterInfoHistory) list_history
								.get(list_history.size() - 1);
						double current_energy_start = Double
								.valueOf(hod2000MeterInfoHistory_Start
										.getCurrentEnergy());
						double current_energy_end = Double
								.valueOf(hod2000MeterInfoHistory_End
										.getCurrentEnergy());
						energy += current_energy_end - current_energy_start;
					}
				}
				
				if(parent_energy == 0){
					categoryDataset.setValue(null, "总表损耗能量累计", x_);
				}else{
					categoryDataset.setValue(Arith.dataFormat(Double.valueOf(parent_energy)), "总表损耗能量累计", x_);
				}
				
				if(energy == 0){
					categoryDataset.setValue(null, "分表损耗能量累计和", x_);
				}else{
					categoryDataset.setValue(Arith.dataFormat(Double.valueOf(energy)), "分表损耗能量累计和", x_);
				}
				
			}

			JFreeChart chart = ChartFactory.createBarChart("管网损耗",// 图表标题
					"时间（" + x_name + "）",// 目录轴的显示标签
					"单位时间内损耗的能量 [单位:KWh]", // 数值轴的显示标签
					categoryDataset, // 数据集
					PlotOrientation.VERTICAL,// 图表方向：水平、垂直
					true, // 是否显示图例(对于简单的柱状图必须是false)
					true,// 是否生成工具
					false// 是否生成URL链接
					);

			// 副标题
			TextTitle subtitle = new TextTitle(meterName + "表计管网损耗",
					new Font("黑体", Font.BOLD, 12));
			chart.addSubtitle(subtitle);
			chart.setTitle(chart.getTitle()); // 标题
			
			CategoryPlot plot = chart.getCategoryPlot();// 获得图表区域对象
			// 设置图表的纵轴和横轴org.jfree.chart.axis.CategoryAxis
			CategoryAxis domainAxis = plot.getDomainAxis();
			NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
			
			// 解决中文乱码问题
			TextTitle textTitle = chart.getTitle();
			textTitle.setFont(new Font("黑体", Font.PLAIN, 20));
			domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));
			domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 20));
			numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));
			numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 16));
			chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));

			domainAxis.setLowerMargin(0.02);// 设置距离图片左端距离此时为10%
			domainAxis.setUpperMargin(0.02);// 设置距离图片右端距离此时为百分之10
			domainAxis.setCategoryLabelPositionOffset(10);// 图表横轴与标签的距离(10像素)
			domainAxis.setCategoryMargin(0.2);// 横轴标签之间的距离20%
			
			// 设定柱子的属性
			org.jfree.chart.axis.ValueAxis rangeAxis = plot.getRangeAxis();
			rangeAxis.setUpperMargin(0.1);// 设置最高的一个柱与图片顶端的距离(最高柱的10%)

			// 设置图表的颜色
	        GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, Color.blue,  
	                0.0F, 0.0F, new Color(0, 0, 64));
	    	GradientPaint gradientpaint1 = new GradientPaint(0.0F, 0.0F,   
	                Color.green, 0.0F, 0.0F, new Color(0, 64, 0));  
	    	BarRenderer  renderer = (BarRenderer) plot.getRenderer(); 
			renderer.setBaseOutlinePaint(Color.red);
			renderer.setSeriesPaint(0, gradientpaint); 
			renderer.setSeriesOutlinePaint(0, Color.BLACK);// 边框为黑色
			renderer.setSeriesPaint(1, gradientpaint1);
			renderer.setSeriesOutlinePaint(1, Color.red);// 边框为红色
			//renderer还可以控制每个种类中直方条形图之间的间距。因此我们可以在同一个种类中将空间完全去掉
			renderer.setItemMargin(0.0);// 组内柱子间隔为组宽的10%
		    //设置bar的最小宽度，以保证能显示数值
		    renderer.setMinimumBarLength(0.02);
		    //最大宽度
		    renderer.setMaximumBarWidth(0.1);
			// 显示每个柱的数值，并修改该数值的字体属性
			renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setItemLabelsVisible(true);
			
			plot.setRenderer(renderer);// 使用我们设计的效果
			plot.setNoDataMessage("没有数据！");//当没有数据时
			
	        //设置种类标签旋转的角度，逆时针旋转   
	        CategoryAxis categoryaxis = plot.getDomainAxis();   
	        categoryaxis.setCategoryLabelPositions(CategoryLabelPositions   
	                .createUpRotationLabelPositions(Math.PI / 6));  

			//设置图像区域背景色   
			plot.setBackgroundPaint(Color.WHITE);// 设置网格背景色
			plot.setDomainGridlinePaint(Color.green);// 设置网格竖线(Domain轴)颜色
			plot.setRangeGridlinePaint(Color.LIGHT_GRAY);// 设置网格横线颜色
			
			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());
			String fileName = ServletUtilities.saveChartAsPNG(chart, 700, 400,
					info, session);// 生成图片
			out.write("{success:true,data:'" + fileName + "'}");
		} catch (Exception e) {
			out.write("{success:false,data:error}");
			e.printStackTrace();
		}
	}

	
	public void setHod2000MeterService(IHod2000MeterService hod2000MeterService) {
		this.hod2000MeterService = hod2000MeterService;
	}

	public void setHod2000MeterInfoHistoryService(
			IHod2000MeterInfoHistoryService hod2000MeterInfoHistoryService) {
		this.hod2000MeterInfoHistoryService = hod2000MeterInfoHistoryService;
	}

	public String getLineXYChart(HttpSession session,XYDataset dataSet,String charttitle,String y_axia,String name,String current_energy,SimpleDateFormat dateSdf,int dateType,String x_axia) {
		String fileName = null;
		
		// 建立JFreeChart
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"负荷曲线报表", // title
				x_axia, // x-axis label
				"消耗热能 [单位:KWh]", // y-axis label
				dataSet, // data
				true, // create legend?
				true, // generate tooltips?
				true // generate URLs?
				);
		
		// 设置JFreeChart的显示属性,对图形外部部分进行调整
		chart.setBackgroundPaint(Color.WHITE);// 设置曲线图背景色
		// 设置字体大小，形状
		Font font = new Font("宋体", Font.BOLD, 16);
		TextTitle title = new TextTitle("负荷曲线报表", font);
		chart.setTitle(title);
		// 副标题
		TextTitle subtitle = new TextTitle(name+"表计负荷曲线", new Font("黑体", Font.BOLD, 12));
		chart.addSubtitle(subtitle);
		chart.setTitle(title); // 标题
		
		// 设置图示标题字符
		LegendTitle legengTitle = chart.getLegend();
		 if (legengTitle != null) {  
			 legengTitle.setItemFont(new Font("宋体", Font.BOLD, 20));  
	        }  

		XYPlot plot = (XYPlot) chart.getPlot();// 获取图形的画布
		plot.setBackgroundPaint(Color.LIGHT_GRAY);// 设置网格背景色
		plot.setDomainGridlinePaint(Color.green);// 设置网格竖线(Domain轴)颜色
		plot.setRangeGridlinePaint(Color.white);// 设置网格横线颜色
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));// 设置曲线图与xy轴的距离
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);
		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setShapesVisible(true);// 设置曲线是否显示数据点
		}
		// 设置Y轴
		NumberAxis numAxis = (NumberAxis) plot.getRangeAxis();
		NumberFormat numFormater = NumberFormat.getNumberInstance();
		numFormater.setMinimumFractionDigits(2);
		numAxis.setNumberFormatOverride(numFormater);
		numAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));//设置y轴坐标上的字体   
		numAxis.setLabelFont(new Font("宋体",Font.BOLD,20));//设置y轴坐标上的标题的字体
		// 设置提示信息
		StandardXYToolTipGenerator tipGenerator = new StandardXYToolTipGenerator(
				"历史信息{1} 16:00,{2})", dateSdf,
				numFormater);
		r.setToolTipGenerator(tipGenerator);
		
		// 设置X轴（日期轴）
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(dateSdf);
		axis.setTickUnit(new DateTickUnit(dateType, 1,dateSdf));
		axis.setTickLabelFont(new Font("宋体",Font.BOLD,12));//设置x轴坐标上的字体   
		axis.setLabelFont(new Font("宋体",Font.BOLD,20));//设置x轴上的标题的字体
		
		//在矩形框中显示信息
	    Shape shape = new Rectangle(20, 10);
	    ChartEntity entity = new ChartEntity(shape);
	    StandardEntityCollection coll = new StandardEntityCollection();
	    coll.add(entity);
	    ChartRenderingInfo info = new ChartRenderingInfo(coll);
//	    PrintWriter pw = new PrintWriter(out,true);
	    //该工具类上面没有介绍，在鼠标移动到图片时显示提示信息是用Map实现的，这些Map是用该类生成的。
		try {
			fileName = ServletUtilities.saveChartAsPNG(chart, 700, 400, info,
					session);// 生成图片
			// Write the image map to the PrintWriter
//			ChartUtilities.writeImageMap(pw, fileName, info, false);
//			System.out.println(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return fileName;// 返回生成图片的文件名
	}
	
}
