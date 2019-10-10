package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {
		

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);
		
		showRouteMap(MARGIN + MAPYSIZE);

		//playRoute(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	public double x(GPSPoint gpspoint) {
		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		double diff = Math.abs(maxlon - minlon);
		double x = ((maxlon-gpspoint.getLongitude())/diff)*MAPXSIZE;
		return x;
	}

	// antall y-pixels per breddegrad
	public double y(GPSPoint gpspoint) {
		//ekstremt ueffektivt, aldri bruk dette i et ordentlig program
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		double diff = Math.abs(maxlat - minlat);
		double y = ((maxlat-gpspoint.getLatitude())/diff)*MAPYSIZE;
		return y;
	}

	public void showRouteMap(int ybase) {
		
		for(int i = 0; i < gpspoints.length; i++)
		{
			int x = (int)x(gpspoints[i]);
			int y = (int)y(gpspoints[i]);
			if (i > 0)
				drawLine(MARGIN+x, ybase-y, MARGIN+(int)x(gpspoints[i-1]), ybase-(int)y(gpspoints[i-1]));
			setColor(0, 255, 0);
			fillCircle(MARGIN+x, ybase-y, 3);
		}
	}

	public void showStatistics() {
		int TEXTDISTANCE = 20;

		setColor(0,0,0);
		setFont("Courier",12);
		
		int i = 0;
		for(String s : gpscomputer.getFormattedString())
			drawString(s, MARGIN, MARGIN+TEXTDISTANCE*i++);
	}

	public void playRoute(int ybase) {

		// TODO - START
		
		throw new UnsupportedOperationException(TODO.method());
		
		// TODO - SLUTT
	}

}
