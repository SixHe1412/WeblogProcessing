package frameSet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.swing.JPanel;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapPane;

public class TabMapShow extends JPanel {
	private static final long serialVersionUID = 1L;

    public MapContent map = null;
	
	public TabMapShow() {
		// TODO Auto-generated constructor stub
		
		map = new MapContent();
		FileDataStore store = null;
	    SimpleFeatureSource featureSource = null;
	    try {
		     store = FileDataStoreFinder.getDataStore(new File("China\\CHN_adm1.shp"));
			 featureSource = store.getFeatureSource();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    //Style style = SLD.createSimpleStyle(featureSource.getSchema());
	    Style style = SLD.createPolygonStyle(Color.RED, Color.green, 0.4F);
	   
        Layer layer = new FeatureLayer(featureSource, style);
        
        map.addLayer(layer);
        JMapPane mapPane = new JMapPane(map);
        
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        this.add(mapPane,BorderLayout.CENTER);
        
        SimpleFeatureCollection sfc = null;
        try {
			 sfc = featureSource.getFeatures();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        /*SimpleFeatureIterator sfIterator = sfc.features();
        while (sfIterator.hasNext()) {
			System.out.println(sfIterator.next().getAttribute("NAME_1"));
			
		}*/
        
       
        
        
	}
	
}
