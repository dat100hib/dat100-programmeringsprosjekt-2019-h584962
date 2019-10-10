package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double lowest = Double.MAX_VALUE;
		for(int i = 0; i < da.length; i++)
			if (da[i] < lowest)
				lowest = da[i];
		return lowest;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {
		double[] lats = new double[gpspoints.length];
		for(int i = 0; i < gpspoints.length; i++)
			lats[i] = gpspoints[i].getLatitude();
		return lats;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] longs = new double[gpspoints.length];
		for(int i = 0; i < gpspoints.length; i++)
			longs[i] = gpspoints[i].getLongitude();
		return longs;
	}
	
	public static double[] getElevations(GPSPoint[] gpspoints) {
		double[] elevs = new double[gpspoints.length];
		for(int i = 0; i < gpspoints.length; i++)
			elevs[i] = gpspoints[i].getElevation();
		return elevs;
	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double phi1 = gpspoint1.getLatitude()*Math.PI/180d;
		double phi2 = gpspoint2.getLatitude()*Math.PI/180d;
		double deltaphi = phi2 - phi1;
		double deltagamma = gpspoint1.getLongitude()*Math.PI/180d-gpspoint2.getLongitude()*Math.PI/180d;
		double a = pow(sin(deltaphi/2), 2)+cos(phi1)*cos(phi2)*pow(sin(deltagamma/2), 2);
		double c = 2*atan2(sqrt(a), sqrt(1-a));
		return R*c;

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {
		int secs = Math.abs(gpspoint1.getTime()-gpspoint2.getTime());
		return distance(gpspoint1, gpspoint2)*60*60/(1000d*secs);
	}

	private static int TEXTWIDTH = 10;
	
	public static String formatTime(int secs) {
		String TIMESEP = ":";
		String var = String.format("%s%s%s%s%s", hastwo(secs/60/60), TIMESEP, hastwo(secs/60%60), TIMESEP, hastwo(secs%60));
		String padding = "";
		for(int i = 0; i < TEXTWIDTH-var.length(); i++)
			padding += " ";
		return padding + var;
	}

	public static String formatDouble(double d) {
		String var = Double.toString(Math.round(d*100d)/100d);
		String padding = "";
		for(int i = 0; i < Math.max(0, TEXTWIDTH-var.length()); i++)
			padding += " ";
		return padding + var;
	}
	
	private static String hastwo(int num) {
		String number = Integer.toString(num);
		if (number.length() < 2)
			return "0" + number;
		return number;
	}
}
