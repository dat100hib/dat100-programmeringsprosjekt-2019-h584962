package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;
	private int max;

	public GPSData(int n) {
		 gpspoints = new GPSPoint[n];
		 max = n;
		 antall = 0;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	protected boolean insertGPS(GPSPoint gpspoint) {
		if (antall < max)  {
			gpspoints[antall++] = gpspoint;
			return true;
		} else
			return false;
		
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {

		GPSPoint gpspoint = GPSDataConverter.convert(time, latitude, longitude, elevation);
		return insertGPS(gpspoint);
		
	}

	public void print() {

		System.out.println("====== Konvertert GPS Data - START ======");

		for(int i = 0; i < gpspoints.length; i++)
			System.out.println(String.format("%d %s", i, gpspoints[i]));
		
		System.out.println("====== Konvertert GPS Data - SLUTT ======");

	}
}
