/**
 * 
 */
package rnd.amazon.ninja.meetingschedules;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * Given M busy-time slots of N people, You need to print all the available time
 * slots when all the N people can schedule a meeting for a duration of K
 * minutes. <br/>
 * Event time will be of form HH MM ( where 0 <= HH <= 23 and 0 <= MM <= 59 ), K
 * will be in the form minutes. <br/>
 * <br/>
 * 
 * Input Format: <br/>
 * <br/>
 * 
 * M K [ M number of busy time slots , K is the duration in minutes ] <br/>
 * Followed by M lines with 4 numbers on each line. <br/>
 * Each line will be of form StartHH StartMM EndHH EndMM [ Example 9Am-11Am time
 * slot will be given as 9 00 11 00 ] <br/>
 * An event time slot is of form [Start Time, End Time ) . Which means it
 * inclusive at start time but doesn’t include the end time. <br/>
 * So an event of form 10 00 11 00 => implies that the meeting start at 10:00
 * and ends at 11:00, so another meeting can start at 11:00. <br/>
 * <br/>
 * 
 * Sample Input: <br/>
 * 5 120 <br/>
 * 16 00 17 00 <br/>
 * 10 30 14 30 <br/>
 * 20 45 22 15 <br/>
 * 10 00 13 15 <br/>
 * 09 00 11 00 <br/>
 * <br/>
 * 
 * Sample Output: <br/>
 * 00 00 09 00 <br/>
 * 17 00 20 45 <br/>
 * <br/>
 * 
 * Sample Input: <br/>
 * 8 60 <br/>
 * 08 00 10 15 <br/>
 * 22 00 23 15 <br/>
 * 17 00 19 00 <br/>
 * 07 00 09 45 <br/>
 * 09 00 13 00 <br/>
 * 16 00 17 45 <br/>
 * 12 00 13 30 <br/>
 * 11 30 12 30 <br/>
 * <br/>
 * 
 * Sample Output: <br/>
 * 00 00 07 00 <br/>
 * 13 30 16 00 <br/>
 * 19 00 22 00 <br/>
 * <br/>
 * 
 * Constraints : <br/>
 * 1 <= M <= 100 <br/>
 * <br/>
 * 
 * Note: 24 00 has to be presented as 00 00.
 * 
 * @author Dev Naruka
 * 
 */
public class Solution {
	
	public static void main(String[] args) {
		final Scanner in = new Scanner(System.in);
		
		int M = in.nextInt();
		int K = in.nextInt();
		
		List<TimeSlot> busySlots = new ArrayList<TimeSlot>();
		for(int i = 0; i < M; i++) {
			int sHrs = in.nextInt();
			int sMin = in.nextInt();
			int eHrs = in.nextInt();
			int eMin = in.nextInt();
			
			busySlots.addAll(normalize(new TimeSlot(sHrs, sMin, eHrs, eMin)));
		}
		
		Set<TimeSlot> availableSlots = findAvailability(busySlots, K);
		
		if(availableSlots != null) {
			for(TimeSlot slot : availableSlots) {
				System.out.println(slot);
			}
		}
	}
	
	private static List<TimeSlot> normalize(TimeSlot slot) {
		List<TimeSlot> result = new ArrayList<TimeSlot>();
		if((slot.end_hrs < slot.start_hrs) || (slot.end_hrs == slot.start_hrs && slot.end_min < slot.start_min)) {
			TimeSlot one = new TimeSlot(slot.start_hrs, slot.start_min, 24, 00);
			TimeSlot two = new TimeSlot(00, 00, slot.end_hrs, slot.end_min);

			if(one.availableMinutes > 0) result.add(one);
			if(two.availableMinutes > 0) result.add(two);
		} else {
			if(slot.availableMinutes > 0) result.add(slot);
		}
		return result;
	}

	private static Set<TimeSlot> findAvailability(List<TimeSlot> busySlots,
			int k) {
		Set<TimeSlot> availability = new TreeSet<TimeSlot>();
		availability.add(new TimeSlot(0, 0, 24, 0));
		
		for(TimeSlot busy: busySlots) {
			availability = subtract(availability, busy);
		}
		
		return findFit(availability, k);
	}
	
	private static Set<TimeSlot> findFit(Set<TimeSlot> slots, int minutes) {
		Set<TimeSlot> fits = new TreeSet<TimeSlot>();
		
		if(slots != null) {
			for(TimeSlot slot : slots) {
				if(slot.availableMinutes >= minutes) {
					fits.add(slot);
				}
			}
		}
		return fits;
	}

	private static Set<TimeSlot> subtract(Set<TimeSlot> available, TimeSlot busy) {
		Set<TimeSlot> result = new TreeSet<TimeSlot>();
		
		if(available != null) {
			for(TimeSlot slot : available) {
				boolean start_le_start = slot.start_hrs < busy.start_hrs || (slot.start_hrs == busy.start_hrs && slot.start_min <= busy.start_min);
				boolean end_le_end = slot.end_hrs < busy.end_hrs || (slot.end_hrs == busy.end_hrs && slot.end_min <= busy.end_min);

				boolean start_ge_start = slot.start_hrs > busy.start_hrs || (slot.start_hrs == busy.start_hrs && slot.start_min >= busy.start_min);
				boolean end_ge_end = slot.end_hrs > busy.end_hrs || (slot.end_hrs == busy.end_hrs && slot.end_min >= busy.end_min);
				
				boolean start_le_end = slot.start_hrs < busy.end_hrs || (slot.start_hrs == busy.end_hrs && slot.start_min < slot.end_min);
				boolean end_le_start = slot.end_hrs < busy.start_hrs || (slot.end_hrs == busy.start_hrs && slot.end_min <= busy.start_min);

				boolean start_ge_end = slot.start_hrs > busy.end_hrs || (slot.start_hrs == busy.end_hrs && slot.start_min > busy.end_min);
				boolean end_ge_start = slot.end_hrs > busy.start_hrs || (slot.end_hrs == busy.start_hrs && slot.end_min >= busy.start_min);
				
				// Case 1: [04:00 to 06:00] - [02:30 to 03:30]
				if(start_ge_start && start_ge_end && end_ge_start && end_ge_end) {
					if(slot.availableMinutes > 0) result.add(slot);					
				}
				// Case 2: [04:00 to 06:00] - [07:30 to 08:30]
				else if(start_le_start && end_le_start) {
					if(slot.availableMinutes > 0) result.add(slot);
				}
				// Case 3: [04:00 to 06:00] - [03:30 to 06:30]
				else if(start_ge_start && end_le_end) {
					 continue;
				}
				// Case 4: [04:00 to 06:00] - [04:30 to 05:30]
				else if(start_le_start && end_ge_end) {
					TimeSlot one = new TimeSlot(slot.start_hrs, slot.start_min, busy.start_hrs, busy.start_min);
					TimeSlot two = new TimeSlot(busy.end_hrs, busy.end_min, slot.end_hrs, slot.end_min);
					
					if(one.availableMinutes > 0) result.add(one);
					if(two.availableMinutes > 0) result.add(two);
				}
				// Case 5: [04:00 to 06:00] - [03:30 to 04:30]
				else if(start_ge_start && end_ge_end) {
					TimeSlot one = new TimeSlot(busy.end_hrs, busy.end_min, slot.end_hrs, slot.end_min);
					
					if(one.availableMinutes > 0) result.add(one);
				}
				// Case 6: [04:00 to 06:00] - [04:30 to 06:30]
				else if(start_le_start && end_le_end) {
					TimeSlot one = new TimeSlot(slot.start_hrs, slot.start_min, busy.start_hrs, busy.start_min);
					if(one.availableMinutes > 0) result.add(one);
				}
			}
		}
		
		return result;
	}
	
	private static class TimeSlot implements Comparable<TimeSlot> {
		public final int start_hrs;
		public final int start_min;
		public final int end_hrs;
		public final int end_min;
		public final int availableMinutes;

		public TimeSlot(int start_hrs, int start_min, int end_hrs, int end_min) {
			super();
			this.start_hrs = start_hrs;
			this.start_min = start_min;
			this.end_hrs = end_hrs;
			this.end_min = end_min;
			this.availableMinutes = calculate(start_hrs, start_min, end_hrs, end_min);
		}
		
		public static int calculate(int sHrs, int sMin, int eHrs, int eMin) {
			int start_in_min = (sHrs * 60) + sMin;
			int end_in_min = (eHrs * 60) + eMin;
			
			return end_in_min - start_in_min;
		}
		
		@Override
		public String toString() {
			return String.format("%02d %02d %02d %02d", start_hrs == 24 ? 0 : start_hrs, start_min, end_hrs == 24 ? 0 : end_hrs, end_min);
		}
		
		@Override
		public int compareTo(TimeSlot o) {
			if(o == this) return 0;
			if(o == null) return -1;
			
			if(start_hrs != o.start_hrs) return Integer.compare(start_hrs, o.start_hrs);
			if(start_min != o.start_min) return Integer.compare(start_min, o.start_min);
			if(availableMinutes != o.availableMinutes) return Integer.compare(availableMinutes, o.availableMinutes);

			return 0;
		}
	}
}
