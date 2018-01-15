package br.com.brotolegal.sav700;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class ViewAgendamentosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_agendamentos);

        HashSet<Date> events = new HashSet<>();
        events.add(new Date());

        CalendarView cv = ((CalendarView)findViewById(R.id.calendar_view));
        cv.updateCalendar(events);

        // assign event handler
        cv.setEventHandler(new CalendarView.EventHandler()
        {
            @Override
            public void onDayLongPress(Date date)
            {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(ViewAgendamentosActivity.this, df.format(date), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
