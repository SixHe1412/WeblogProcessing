package functionSet;

import java.awt.BorderLayout;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import frameSet.MainFrame;

public class ChartShow {

	MainFrame mainFrame = null;
	DBCursor cursor = null;
	String Field;
	
	public ChartShow(MainFrame mainFrame,DBCursor cur,String field,String chart) {
		// TODO Auto-generated constructor stub
		this.mainFrame = mainFrame;
		this.cursor = cur;
		this.Field = field;
		if (chart.equals("饼状图")) 
			AddPieChart();
		else if (chart.equals("柱状图")) {
			AddBarChart();
		}			
	}
	
	private void AddPieChart() {
		// create a chart
		DefaultPieDataset dpd = new DefaultPieDataset();
		double otherval = 0;
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			String key = obj.get("_id").toString();
			double val = (double)obj.get("value");
			if(val>5000)
			   dpd.setValue(key, val);
			else {
				otherval+=val;
			}
		}
		dpd.setValue("other(<5000)", otherval);
		
		 
		JFreeChart pieChart = ChartFactory.createPieChart3D(Field+"分布饼状图",dpd,true,true,false);
	
		PiePlot3D pieplot3d = (PiePlot3D) pieChart.getPlot();    
	    //设置旋转角度    
	    pieplot3d.setStartAngle(90.0);    
	    //设置旋转方向，Rotation.CLOCKWISE)为顺时针。    
	    pieplot3d.setDirection(Rotation.CLOCKWISE);    
	    //设置图表透明图0.0~1.0范围。0.0为完全透明，1.0为完全不透明。    
	    pieplot3d.setForegroundAlpha(0.6F);  
		pieplot3d.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({2} percent)"));
		
		 PiePlot3D plot = (PiePlot3D) pieChart.getPlot();
	     plot.setLabelFont(new Font("黑体", Font.PLAIN, 12));
	     TextTitle textTitle = pieChart.getTitle();
	     textTitle.setFont(new Font("黑体", Font.PLAIN, 20));
	     pieChart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
		
		mainFrame.tabControl.tabChartShow.removeAll();
		mainFrame.tabControl.tabChartShow.add(new ChartPanel(pieChart),BorderLayout.CENTER);
		mainFrame.tabControl.tabChartShow.repaint();
		mainFrame.tabControl.middleTab.setSelectedComponent(mainFrame.tabControl.tabChartShow);
	}
	
	private void AddBarChart() {
		
		 DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		 double otherval = 0;
		 while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			String key = obj.get("_id").toString();
			double val = (double)obj.get("value");
			if(val>5000)
				dataset.addValue(val, "", key);
			else {
				otherval+=val;
			}
		}
		dataset.addValue(otherval,"","other(<5000)");
		
		JFreeChart barChart = ChartFactory.createBarChart3D(
				               Field+"分布柱状图",
				               Field,
				               "数量",
				               dataset,
				               PlotOrientation.VERTICAL,true,true,false);
		
		CategoryPlot categoryplot = (CategoryPlot) barChart.getPlot();
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();  
        CategoryAxis domainAxis = categoryplot.getDomainAxis();  
        TextTitle textTitle = barChart.getTitle();
        textTitle.setFont(new Font("黑体", Font.PLAIN, 20));      
        domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));  
        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));  
        numberaxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 12));  
        numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 12));  
        barChart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
	    
		mainFrame.tabControl.tabChartShow.removeAll();
		mainFrame.tabControl.tabChartShow.add(new ChartPanel(barChart),BorderLayout.CENTER);
		mainFrame.tabControl.tabChartShow.repaint();
		mainFrame.tabControl.middleTab.setSelectedComponent(mainFrame.tabControl.tabChartShow);
	}
	
}
