package com.hod.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

public class GraphicReportHelp {
	
	/*
	 * 柱形图字体设置
	 */
	public static void getCategoryFont(JFreeChart chart)
	{
		chart.getTitle().setFont(new Font("黑体", Font.BOLD, 20));
		CategoryPlot plot = (CategoryPlot) chart.getPlot();   
		CategoryAxis domainAxis = plot.getDomainAxis();
		// 设置水平底部列表
		domainAxis.setLabelFont(new Font("黑体", Font.BOLD, 14));
		// 设置水平底部标题
		domainAxis.setTickLabelFont(new Font("黑体", Font.BOLD, 12));
		// 设置锤子标题
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 18));
		// 设置说明的字体
		chart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 18));
		chart.getTitle().setFont(new Font("黑体", Font.BOLD, 20));
		plot.setBackgroundPaint(Color.white);//设置背景颜色
	    //plot.setDomainGridlinePaint(Color.gray);//设置网格竖线颜色
	    plot.setRangeGridlinePaint(Color.gray);//设置网格横线颜色
	  //显示每个柱的数值，并修改该数值的字体属性   
		BarRenderer3D renderer = new BarRenderer3D();   
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());   
		renderer.setBaseItemLabelsVisible(true);   
		//默认的数字显示在柱子中，通过如下两句可调整数字的显示   
		//注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题   
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));   
		renderer.setItemLabelAnchorOffset(10D);   
		//设置每个地区所包含的平行柱的之间距离   
		//renderer.setItemMargin(0.6);   
		plot.setRenderer(renderer);
	}
	
	/*
	 * 饼图字体设置
	 */
	public static void getPieFont(JFreeChart chart)
	{
		chart.getTitle().setFont(new Font("黑体", Font.BOLD, 15));
		PiePlot piePlot = (PiePlot) chart.getPlot();
		piePlot.setLabelFont(new Font("黑体",  Font.TRUETYPE_FONT, 12));
		chart.getLegend().setItemFont(new Font("隶书", Font.BOLD, 15));
		piePlot.setBackgroundPaint(Color.white);//设置饼图的背景颜色为空
		piePlot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}--{1}"));//在颜色label中显示百分比
		piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1}"));//在饼图label中显示百分比
		piePlot.setSectionPaint("已收费用户数", Color.YELLOW);        
		piePlot.setSectionPaint("未收费用户数", Color.GREEN);  
		piePlot.setSectionPaint("后付费用户数", Color.BLUE);   
		piePlot.setSectionPaint("已收款用户数", Color.YELLOW);        
		piePlot.setSectionPaint("未收款用户数", Color.GREEN);  
		piePlot.setSectionPaint("已收款金额", Color.YELLOW);        
		piePlot.setSectionPaint("未收款金额", Color.GREEN);  
	}
	
	/*
	 * 曲线图字体设置
	 */
	public static void getTimeSeriesFont(JFreeChart chart)
	{
		// 设置JFreeChart的显示属性,对图形外部部分进行调整
		chart.setBackgroundPaint(Color.WHITE);// 设置曲线图背景色
		// 设置字体大小，形状
		//设置标题字体
		chart.getTitle().setFont(new Font("黑体", Font.BOLD, 20));
		
		// 设置图示标题字符
		LegendTitle legengTitle = chart.getLegend();
		 if (legengTitle != null) {  
			 legengTitle.setItemFont(new Font("宋体", Font.BOLD, 20));  
	        }  

		XYPlot plot = (XYPlot) chart.getPlot();// 获取图形的画布
		plot.setBackgroundPaint(Color.WHITE);// 设置网格背景色
		plot.setDomainGridlinePaint(Color.green);// 设置网格竖线(Domain轴)颜色
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY);// 设置网格横线颜色
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));// 设置曲线图与xy轴的距离
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);
		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setShapesVisible(true);// 设置曲线是否显示数据点
			renderer.setSeriesPaint(0, Color.BLUE);
		}
		// 设置Y轴
		NumberAxis numAxis = (NumberAxis) plot.getRangeAxis();
		NumberFormat numFormater = NumberFormat.getNumberInstance();
		numFormater.setMinimumFractionDigits(2);
		numAxis.setNumberFormatOverride(numFormater);
		numAxis.setTickLabelFont(new Font("宋体",Font.BOLD,12));//设置y轴坐标上的字体   
		numAxis.setLabelFont(new Font("宋体",Font.BOLD,20));//设置y轴坐标上的标题的字体
		
		// 设置X轴（日期轴）
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setTickLabelFont(new Font("宋体",Font.BOLD,12));//设置x轴坐标上的字体   
		axis.setLabelFont(new Font("宋体",Font.BOLD,20));//设置x轴上的标题的字体
	}

}
