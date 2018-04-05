package br.com.brotolegal.sav700;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.com.brotolegal.savdatabase.app.App;

/**
 * Created by a7med on 28/06/2015.
 */
public class CalendarView extends LinearLayout
{
	// for logging
	private static final String LOGTAG = "Calendar View";

	// how many days to show, defaults to six weeks, 42 days
	private static final int DAYS_COUNT = 42;

	// default date format
	private static final String DATE_FORMAT = "MMM yyyy";

	// date format
	private String dateFormat;

	// current displayed month
	private Calendar currentDate = Calendar.getInstance();

	//event handling
	private EventHandler eventHandler = null;

	Map<String,objCalendarAge> agendamentos    = new TreeMap<String, objCalendarAge >();

	// internal components
	private LinearLayout header;
	private ImageView btnPrev;
	private ImageView btnNext;
	private TextView txtDate;
	private GridView grid;

	// seasons' rainbow
	int[] rainbow = new int[] {
			R.color.summer,
			R.color.fall,
			R.color.winter,
			R.color.spring
	};

	// month-season association (northern hemisphere, sorry australia :)
	int[] monthSeason = new int[] {2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};

	public CalendarView(Context context)
	{
		super(context);
	}

	public CalendarView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initControl(context, attrs);
	}

	public CalendarView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		initControl(context, attrs);
	}

	/**
	 * Load control xml layout
	 */
	private void initControl(Context context, AttributeSet attrs)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.control_calendar, this);

		loadDateFormat(attrs);
		assignUiElements();
		assignClickHandlers();

		updateCalendar();
	}

	private void loadDateFormat(AttributeSet attrs)
	{
		TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

		try
		{
			// try to load provided date format, and fallback to default otherwise
			dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
			if (dateFormat == null)
				dateFormat = DATE_FORMAT;
		}
		finally
		{
			ta.recycle();
		}
	}
	private void assignUiElements()
	{
		// layout is inflated, assign local variables to components
		header = (LinearLayout)findViewById(R.id.calendar_header);
		btnPrev = (ImageView)findViewById(R.id.calendar_prev_button);
		btnNext = (ImageView)findViewById(R.id.calendar_next_button);
		txtDate = (TextView)findViewById(R.id.calendar_date_display);
		grid    = (GridView)findViewById(R.id.calendar_grid);
	}

	private void assignClickHandlers() {

		// add one month and refresh UI
		btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentDate.add(Calendar.MONTH, 1);
				updateCalendar();
			}
		});

		// subtract one month and refresh UI
		btnPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentDate.add(Calendar.MONTH, -1);
				updateCalendar();

			}
		});

		// long-pressing a day
		grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id) {
				// handle long-press
				if (eventHandler == null)
					return false;

				eventHandler.onDayLongPress((Date) view.getItemAtPosition(position));
				return true;
			}
		});

		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				if (eventHandler == null)
					return ;

				eventHandler.onDayPress((Date) parent.getItemAtPosition(position));

			}
		});

	}


	/**
	 * Display dates correctly in grid
	 */
	public void updateCalendar()
	{
		updateCalendar(null);
	}

	/**
	 * Display dates correctly in grid
	 */
	public void updateCalendar(HashSet<Date> events)
	{
		ArrayList<Date> cells = new ArrayList<>();
		Calendar calendar = (Calendar)currentDate.clone();

		// determine the cell for current month's beginning
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		// move calendar backwards to the beginning of the week
		calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

		// fill cells
		while (cells.size() < DAYS_COUNT)
		{
			cells.add(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}

		// update grid
		grid.setAdapter(new CalendarAdapter(getContext(), cells, events, this.agendamentos));

		// update title
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		txtDate.setText(sdf.format(currentDate.getTime()));

		// set header color according to current season
		int month = currentDate.get(Calendar.MONTH);
		int season = monthSeason[month];
		int color = rainbow[season];

		header.setBackgroundColor(getResources().getColor(color));
	}


	public void setAgendamentos(List<objCalendarAge> agendamentos){

		this.agendamentos = new TreeMap<String, objCalendarAge >();

		for(objCalendarAge obj : agendamentos){

			this.agendamentos.put(obj.getData(),obj);

		}

	}

	private class CalendarAdapter extends ArrayAdapter<Date>
	{
		// days with events
		private HashSet<Date> eventDays;

		// for view inflation
		private LayoutInflater inflater;

		// Agendamentos

		Map<String,objCalendarAge> agendamentos = new TreeMap<String, objCalendarAge >();

		public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays,Map<String,objCalendarAge> agendamentos )
		{
			super(context, R.layout.control_calendar_day, days);
			this.eventDays = eventDays;
			inflater = LayoutInflater.from(context);
			this.agendamentos = agendamentos;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent)
		{
			TextView lbNro;
			TextView lbAg1;
			TextView lbAg2;
			// day in question
			Date date = getItem(position);

			Calendar calendar = Calendar.getInstance();

			calendar.setTime(date);

			int year  = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day   = calendar.get(Calendar.DAY_OF_MONTH);
			Boolean IsSunDay = (calendar.get(Calendar.DAY_OF_WEEK) == 1);


			// today
			Date today = new Date();

			// inflate item if it does not exist yet
			if (view == null) {
				view = inflater.inflate(R.layout.control_calendar_day2, parent, false);
			}

			lbNro = (TextView) view.findViewById(R.id.cal_day);
			lbAg1 = (TextView) view.findViewById(R.id.cal_day_ag1);
			lbAg2 = (TextView) view.findViewById(R.id.cal_day_ag2);

			lbAg1.setText("");
			lbAg2.setText("");

			// if this day has an event, specify event image
			lbNro.setBackgroundResource(0);
			if (eventDays != null)
			{
				for (Date eventDate : eventDays)
				{
					calendar.setTime(eventDate);

					if (    calendar.get(Calendar.DAY_OF_MONTH) == day &&
							calendar.get(Calendar.MONTH) == month &&
							calendar.get(Calendar.YEAR) == year)
					{
						// mark this day for event
						lbNro.setBackgroundResource(R.drawable.reminder);
						break;
					}
				}
			}

			// clear styling
			lbNro.setTypeface(null, Typeface.NORMAL);
			lbNro.setTextColor(Color.BLACK);

			calendar.setTime(today);

			if (month != calendar.get(Calendar.MONTH) || year != calendar.get(Calendar.YEAR))
			{
				// if this day is outside current month, grey it out
				lbNro.setTextColor(getResources().getColor(R.color.greyed_out));
			}
			else if (day == calendar.get(Calendar.DAY_OF_YEAR))
			{
				lbNro.setTypeface(null, Typeface.BOLD);
				lbNro.setTextColor(getResources().getColor(R.color.today));
			}
			else if (IsSunDay){

					lbNro.setTypeface(null, Typeface.BOLD);
					lbNro.setTextColor(getResources().getColor(R.color.red));

				}

			// set text
			calendar.setTime(date);
			lbNro.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

			objCalendarAge age = agendamentos.get(App.getDateToDtos(date));

			if (age != null){

				lbAg1.setText(String.valueOf(age.ag1));

				lbAg2.setText(String.valueOf(age.ag2));


			}

			return view;
		}
	}

	/**
	 * Assign event handler to be passed needed events
	 */
	public void setEventHandler(EventHandler eventHandler)
	{

		this.eventHandler = eventHandler;
	}


	/**
	 * This interface defines what events to be reported to
	 * the outside world
	 */
	public interface EventHandler
	{
		void onDayLongPress(Date date);

		void onDayPress(Date date);
	}

	public static class objCalendarAge {

		private String   data;
		private Integer  ag1;
		private Integer  ag2;

		public objCalendarAge(String data, Integer ag1, Integer ag2) {
			this.data = data;
			this.ag1 = ag1;
			this.ag2 = ag2;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public Integer getAg1() {
			return ag1;
		}

		public void setAg1(Integer ag1) {
			this.ag1 = ag1;
		}

		public Integer getAg2() {
			return ag2;
		}

		public void setAg2(Integer ag2) {
			this.ag2 = ag2;
		}
	}
}
