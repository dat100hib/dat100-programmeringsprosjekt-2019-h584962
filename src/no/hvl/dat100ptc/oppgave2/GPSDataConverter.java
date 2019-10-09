package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSDataConverter {

	// konverter tidsinformasjon i gps data punkt til antall sekunder fra midnatt
	// dvs. ignorer information om dato og omregn tidspunkt til sekunder
	// Eksempel - tidsinformasjon (som String): 2017-08-13T08:52:26.000Z
    // skal omregnes til sekunder (som int): 8 * 60 * 60 + 52 * 60 + 26 
	
	private static int TIME_STARTINDEX = 11; // startindex for tidspunkt i timestr

	public static int toSeconds(String timestr) {
		
		int secs;
		int hr, min, sec;
		String timeString = timestr.substring(timestr.indexOf('T')+1, timestr.indexOf('Z', timestr.indexOf('T')));
		int last = timeString.indexOf(':');
		hr = Integer.parseInt(timeString.substring(0, last));
		min = Integer.parseInt(timeString.substring(last+1, (last=timeString.indexOf(':'))));
		sec = Integer.parseInt(timeString.substring(last+1, (last=timeString.indexOf('.'))));
		
		return 60*60*hr+60*min+sec;
	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {
		return new GPSPoint(toSeconds(timeStr), Double.parseDouble(latitudeStr), Double.parseDouble(longitudeStr), Double.parseDouble(elevationStr));
	}
	
}
