package sortie;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;

import kMeans.Center;
import kMeans.Point;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Permet de sortir les résultats sous forme d'un graphe 2D
 * 
 * @author Denis
 *
 */
public class Graphe extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Graphe(){	
		
	}
	
	public void dessine(List<Center> centerList){
		

		XYSeriesCollection dataset = new XYSeriesCollection();
		int i = 0;
		for (Center center : centerList){
			XYSeries serie = new XYSeries("serie de points "+i);
			ajoutePointADessiner(center, serie);
			for (Point point : center.getPointList()){
				ajoutePointADessiner(point, serie);
			}
			i++;
			dataset.addSeries(serie);
		}
		
		JFreeChart chart = ChartFactory.createScatterPlot("titre", "x", "y", dataset, PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(chart); 
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 330)); 
		setContentPane(chartPanel);
		setSize(500, 330);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(final WindowEvent e) {
				System.exit(0);
			}
		});
		setVisible(true);
	}
	
	/**
	 * Ajoute des points 2D au dessin
	 * @param point
	 * @param serie
	 */
	public void ajoutePointADessiner(Point point, XYSeries serie){
		if (point.getCoord().length == 2){
			serie.add((double)point.getCoord()[0], (double)point.getCoord()[1]);
		} else {
			System.out.println("La dimension des points n'est pas compatible avec l'outil de représentation graphique");
		}
	}
	
}
