package no.hvl.dat100ptc.oppgave6;

import javax.swing.JOptionPane;

import easygraphics.*;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class CycleComputer extends EasyGraphics {

	private static int SPACE = 10;
	private static int MARGIN = 20;
	
	// FIXME: take into account number of measurements / gps points
	private static int ROUTEMAPXSIZE = 800; 
	private static int ROUTEMAPYSIZE = 400;
	private static int HEIGHTSIZE = 200;
	private static int TEXTWIDTH = 200;

	private GPSComputer gpscomp;
	private GPSPoint[] gpspoints;
	
	private int N = 0;

	private double minlon, minlat, maxlon, maxlat, minheight, maxheight;


	public CycleComputer() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");

		gpscomp = new GPSComputer(filename);
		gpspoints = gpscomp.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		N = gpspoints.length; // number of gps points

		minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		
		minheight = 0;
		maxheight = GPSUtils.findMax(GPSUtils.getElevations(gpspoints));
		
		makeWindow("Cycle Computer", 
				2 * MARGIN + ROUTEMAPXSIZE + 400,
				2 * MARGIN + ROUTEMAPYSIZE + HEIGHTSIZE + SPACE);
		
		
		//fillRectangle(MARGIN, MARGIN+HEIGHTSIZE+SPACE, 800, 400);
		
		bikeRoute();

	}

	
	public void bikeRoute() {
		
		//Speed
		
		System.out.println("Angi tidsskalering i tegnevinduet ...");
		int timescaling = Integer.parseInt(getText("Tidsskalering"));
		
		setColor(0, 255, 0);
		double average = gpscomp.averageSpeed();
		fillRectangle(MARGIN, HEIGHTSIZE+MARGIN-(int)average, gpspoints.length-1, 2);
		//Height
		
		// ybase indicates the position on the y-axis where the columns should start
		
		
		//Route
		int height = MARGIN + ROUTEMAPYSIZE + HEIGHTSIZE + SPACE;
		int blueid = 0;
		setSpeed(1);
		for(int i = 0; i < gpspoints.length; i++)
		{
			setColor(0, 0, 255);
			int x = (int)x(gpspoints[i]);
			int y = (int)y(gpspoints[i]);
			if (i == 0)
				blueid = fillCircle(MARGIN+x,height-y,4);
			if (i < gpspoints.length-1)
			{
				int nx = (int)x(gpspoints[i+1]);
				int ny = (int)y(gpspoints[i+1]);
				moveCircle(blueid, MARGIN+nx, height-ny);
				pause((int)(((gpspoints[i+1].getTime()-gpspoints[i].getTime())*1000d)/timescaling));
			}
			if (i > 0) {
				int speed =(int) GPSUtils.speed(gpspoints[i], gpspoints[i-1]);
				fillRectangle(MARGIN+i, (int)Math.max(0,HEIGHTSIZE+MARGIN-speed), 1, (int)speed);
				drawLine(MARGIN+x, height-y, MARGIN+(int)x(gpspoints[i-1]), height-(int)y(gpspoints[i-1]));
				
			}
			fillRectangle(MARGIN+i+gpspoints.length+SPACE, MARGIN+HEIGHTSIZE-(int)heighty(gpspoints[i].getElevation()), 1, (int)heighty(gpspoints[i].getElevation()));
			setColor(0, 255, 0);
			fillCircle(MARGIN+x, height-y, 3);
		}
	}

	public double x(GPSPoint gpspoint) {
		double diff = Math.abs(maxlon - minlon);
		double x = ((maxlon-gpspoint.getLongitude())/diff)*ROUTEMAPXSIZE;
		return x;
	}

	public double y(GPSPoint gpspoint) {
		double diff = Math.abs(maxlat - minlat);
		double y = ((maxlat-gpspoint.getLatitude())/diff)*ROUTEMAPYSIZE;
		return y;
	}
	
	public double heighty(double elevation) {
		double diff = Math.abs(maxheight-minheight);
		double y = ((maxheight-elevation)/diff)*HEIGHTSIZE;
		return y;
	}


}
