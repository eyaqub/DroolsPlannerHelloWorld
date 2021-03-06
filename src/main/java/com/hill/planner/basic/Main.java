package com.hill.planner.basic;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Super simple sequencer.
 * 
 * Each flight has a TOBT and has a lead time of n minutes (varies).
 * 
 * Hard constraints
 * 
 * No other TSAT can fall within that window.
 * 
 * Soft constraints
 * 
 * Minimise overall delay cost
 * 
 * Hint: the output is in CSV - copy the line that starts ID TOBT TSAT ... to
 * the end of the results - you can use ctrl-shift V to paste this into
 * spreadsheet columns (Excel/Calc/etc)
 * 
 * To run just start the program - no parameters needed
 */
public class Main {
	public static DateTimeFormatter fmt = DateTimeFormat
			.forPattern("yyyy/MM/dd HH:mm");

	public static void main(String[] args) throws FileNotFoundException,
			ParseException {

		// one off initialisation (slow as it reads config from disk)
		DroolsRunner dRunner = new DroolsRunner(generateProblem());
		
		// track how long it took to solve the problem
		Instant startTime = new Instant();

		// solve it!
		AirportSolution bestSolution = dRunner.solve();

		Instant endTime = new Instant();

		// print some infos on the solution
		System.out.println(bestSolution.toString());
		System.out.println("Start: " + startTime);
		System.out.println("End: " + endTime);
		System.out.println("Took: "
				+ new Interval(startTime, endTime).toDurationMillis() + "ms");
	}

	/**
	 * Statically generate a starting situation. Here, the planning value (the
	 * TSATs) could be anything - but if you have a fast way of initialising the
	 * values to something reasonable, it will help planner as the path to the
	 * optimal solution will be shorter.
	 * 
	 * At the moment TSAT is initialised to be equal to the TOBT - could you
	 * improve this programatically? Hint: Are most flights delayed? if so, by
	 * how much on average?
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static AirportSolution generateProblem() throws ParseException {

		List<Plane> p = new ArrayList<Plane>();

		Plane plane = null;

		long oneMinute = 6000;

		plane = new Plane("a", fmt.parseDateTime("2013/01/01 01:00")
				.toInstant(), new Duration(oneMinute * 10));
		p.add(plane);
		plane = new Plane("b", fmt.parseDateTime("2013/01/01 01:01")
				.toInstant(), new Duration(oneMinute * 12));
		p.add(plane);
		plane = new Plane("c", fmt.parseDateTime("2013/01/01 01:10")
				.toInstant(), new Duration(oneMinute * 2));
		p.add(plane);
		plane = new Plane("d", fmt.parseDateTime("2013/01/01 01:20")
				.toInstant(), new Duration(oneMinute * 13));
		p.add(plane);
		plane = new Plane("e", fmt.parseDateTime("2013/01/01 01:30")
				.toInstant(), new Duration(oneMinute * 3));
		p.add(plane);
		plane = new Plane("f", fmt.parseDateTime("2013/01/01 01:30")
				.toInstant(), new Duration(oneMinute * 5));
		p.add(plane);
		plane = new Plane("g", fmt.parseDateTime("2013/01/01 01:30")
				.toInstant(), new Duration(oneMinute * 6));
		p.add(plane);
		plane = new Plane("h", fmt.parseDateTime("2013/01/01 01:30")
				.toInstant(), new Duration(oneMinute * 10));
		p.add(plane);
		plane = new Plane("i", fmt.parseDateTime("2013/01/01 01:30")
				.toInstant(), new Duration(oneMinute * 7));
		p.add(plane);
		plane = new Plane("j", fmt.parseDateTime("2013/01/01 01:30")
				.toInstant(), new Duration(oneMinute * 4));
		p.add(plane);

		AirportSolution a = new AirportSolution(p);

		return a;
	}
}
