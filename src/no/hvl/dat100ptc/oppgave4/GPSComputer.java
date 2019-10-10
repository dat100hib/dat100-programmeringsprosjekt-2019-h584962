package no.hvl.dat100ptc.oppgave4;

import java.util.ArrayList;
import java.util.Arrays;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {
		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	public double totalDistance() {
		double distance = 0;
		for(int i = 1; i < gpspoints.length;i++)
			if (gpspoints[i] != null && gpspoints[i-1] != null)
				distance += GPSUtils.distance(gpspoints[i], gpspoints[i-1]);
		return distance;

	}

	public double totalElevation() {

		double elevation = 0;
		for(int i = 0; i < gpspoints.length; i++)
			if (gpspoints[i] != null)
				if (gpspoints[i].getElevation() > elevation)
					elevation += gpspoints[i].getElevation()-elevation;
		return elevation;
	}
	
	public int totalTime() {
		int total = 0;
		for(int i = 1; i < gpspoints.length; i++)
			if (gpspoints[i] != null && gpspoints[i-1] != null)
			total += Math.abs(gpspoints[i].getTime()-gpspoints[i-1].getTime());
		return total;
	}

	public double[] speeds() {
		double[] speeds = new double[gpspoints.length-1];
		for(int i = 1; i < gpspoints.length; i++)
			if (gpspoints[i] != null && gpspoints[i-1] != null)
				speeds[i-1] = GPSUtils.speed(gpspoints[i], gpspoints[i-1]);
		return speeds;
	}
	
	public double maxSpeed() {
		double speed = Double.MIN_VALUE;
		for(int i = 1; i < gpspoints.length; ++i) {
			if (gpspoints[i] == null && gpspoints[i-1] == null)
				continue;
			double current = GPSUtils.speed(gpspoints[i-1], gpspoints[i]);
			if (current > speed)
				speed = current;
		}
		return speed;
	}

	public double averageSpeed() {
		return (totalDistance()/totalTime()*60*60)/1000;
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;		
		double mph = speed * MS;

		if (mph < 10)
			met = 4;
		else if (mph >= 10 && mph < 12)
			met = 6;
		else if (mph >= 12 && mph < 14)
			met = 8;
		else if (mph >= 14 && mph < 16)
			met = 10;
		else if (mph >= 16 && mph < 20)
			met = 12;
		else
			met = 16;
		return met*weight*(secs/60d/60d);
	}

	public double totalKcal(double weight) {
		double totalkcal = 0;
		for(int i = 1; i < gpspoints.length; i++)
			if (gpspoints[i] != null && gpspoints[i-1] != null)
				totalkcal += kcal(weight, Math.abs(gpspoints[i].getTime()-gpspoints[i-1].getTime()), GPSUtils.speed(gpspoints[i], gpspoints[i-1])/3.6d);
		return totalkcal;
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {

		System.out.println("==============================================");
		
		for(String s : getFormattedString())
		System.out.println(s);
		
		System.out.println("==============================================");
		
	}
	
	public Iterable<String> getFormattedString() {
		return Arrays.asList(String.format(
				"Total Time\t:\t%s"
				+ "\nTotal distance\t:\t%s\tkm"
				+ "\nTotal elevation\t:\t%s\tm"
				+ "\nMax speed\t:\t%s\tkm/t"
				+ "\nAverage speed\t:\t%s\tkm/t"
				+ "\nEnergy\t\t:\t%s\tkcal",
				GPSUtils.formatTime(totalTime()),
				GPSUtils.formatDouble(totalDistance()),
				GPSUtils.formatDouble(totalElevation()),
				GPSUtils.formatDouble(maxSpeed()),
				GPSUtils.formatDouble(averageSpeed()),
				GPSUtils.formatDouble(totalKcal(WEIGHT))
			).split("\n"));
	}

}
